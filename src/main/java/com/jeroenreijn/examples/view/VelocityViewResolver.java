package com.jeroenreijn.examples.view;

import org.springframework.context.MessageSource;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import java.util.HashMap;
import java.util.Map;

public class VelocityViewResolver extends AbstractTemplateViewResolver {
    private String layoutUrl;
    private MessageSource messageSource;

    public VelocityViewResolver() {
        this.setViewClass(this.requiredViewClass());
        this.setAttributesMap(new HashMap<>());
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void setLayoutUrl(String layoutUrl) {
        this.layoutUrl = layoutUrl;
        Map<String, Object> attributes = this.getAttributesMap();
        attributes.put("layoutUrl", layoutUrl);
    }

    @Override
    protected Class<?> requiredViewClass() {
        return VelocityView.class;
    }

    @Override
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        AbstractUrlBasedView view = super.buildView(viewName);
        if (view instanceof VelocityView) {
            VelocityView velocityView = (VelocityView) view;
            if (layoutUrl != null) {
                velocityView.setLayoutUrl(layoutUrl);
            }
        }
        return view;
    }
}
