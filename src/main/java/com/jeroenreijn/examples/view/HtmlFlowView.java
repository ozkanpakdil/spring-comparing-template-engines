package com.jeroenreijn.examples.view;

import java.io.PrintWriter;
import java.util.Map;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.AbstractTemplateView;

public class HtmlFlowView extends AbstractTemplateView {

	@Override
	protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String html = HtmlFlowIndexView.templatePresentations(model).threadSafe().toString();

		response.setContentLength(html.length());
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		try (PrintWriter writer = response.getWriter()) {
			writer.write(html);
			writer.flush();
		}
	}
}
