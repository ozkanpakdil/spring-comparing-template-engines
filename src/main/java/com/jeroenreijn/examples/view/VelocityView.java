package com.jeroenreijn.examples.view;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.generic.LinkTool;
import org.springframework.web.servlet.view.AbstractTemplateView;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class VelocityView extends AbstractTemplateView {

    private VelocityEngine velocityEngine;
    private String layoutUrl;

    private boolean velocityEngineInitialized = false;

    private void initVelocityEngine() {
        if (!velocityEngineInitialized) {
            velocityEngine = new VelocityEngine();
            Properties properties = new Properties();
            properties.setProperty("resource.loaders", "file");
            properties.setProperty("resource.loader.file.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
            properties.setProperty("resource.loader.file.cache", "false");

            // Get the real path to the webapp directory
            String webappPath = getServletContext().getRealPath("/");
            properties.setProperty("resource.loader.file.path", webappPath);

            properties.setProperty("resource.default_encoding", "UTF-8");
            properties.setProperty("output.encoding", "UTF-8");
            velocityEngine.init(properties);
            velocityEngineInitialized = true;
        }
    }

    public void setLayoutUrl(String layoutUrl) {
        this.layoutUrl = layoutUrl;
    }

    @Override
    protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Initialize the VelocityEngine if not already done
        initVelocityEngine();

        Context context = new VelocityContext();

        // Add all model attributes to the Velocity context
        for (Map.Entry<String, Object> entry : model.entrySet()) {
            context.put(entry.getKey(), entry.getValue());
        }

        // Add request context attributes
        context.put("request", request);
        context.put("response", response);
        context.put("contextPath", request.getContextPath());

        // Add LinkTool for URL generation
        context.put("link", new LinkTool());

        // Use the URL directly instead of the absolute path
        String templateUrl = getUrl();

        // Create a template from the URL
        Template template = velocityEngine.getTemplate(templateUrl, "UTF-8");

        if (layoutUrl != null) {
            // If layout is specified, render the content into a variable and then render the layout
            Context layoutContext = new VelocityContext(context);

            // Render the content template to a string
            java.io.StringWriter contentWriter = new java.io.StringWriter();
            template.merge(context, contentWriter);

            // Add the rendered content to the layout context
            layoutContext.put("screen_content", contentWriter.toString());

            // Render the layout template
            Template layoutTemplate = velocityEngine.getTemplate(layoutUrl, "UTF-8");
            layoutTemplate.merge(layoutContext, response.getWriter());
        } else {
            // No layout, just render the template directly
            template.merge(context, response.getWriter());
        }
    }

    @Override
    public boolean checkResource(Locale locale) throws Exception {
        // Initialize the VelocityEngine if not already done
        initVelocityEngine();

        try {
            // Use the URL directly instead of the absolute path
            String templateUrl = getUrl();

            // Try to load the template to verify it's valid
            velocityEngine.getTemplate(templateUrl, "UTF-8");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
