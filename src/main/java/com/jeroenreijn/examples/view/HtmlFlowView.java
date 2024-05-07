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
		try (PrintWriter writer = response.getWriter()) {
			Appendable appendable = new StringBuilder();
			HtmlFlowIndexView.templatePresentations(appendable, model);
			response.setContentLength(appendable.toString().getBytes().length);
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			writer.write(appendable.toString());
			writer.flush();
		}
	}
}
