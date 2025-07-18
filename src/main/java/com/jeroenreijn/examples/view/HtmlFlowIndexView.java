package com.jeroenreijn.examples.view;

import com.jeroenreijn.examples.model.Presentation;
import htmlflow.HtmlFlow;
import org.xmlet.htmlapifaster.EnumMediaType;
import org.xmlet.htmlapifaster.EnumRelType;

import java.io.PrintWriter;
import java.util.Map;

public class HtmlFlowIndexView {

    static void templatePresentations(Appendable writer, Map<String, Object> map) {
        Iterable<Presentation> presentations = (Iterable<Presentation>) map.get("presentations");

        HtmlFlow.view(writer, page -> page
                .html()
                .head()
                .meta().attrCharset("UTF-8").__()
                .meta().attrName("viewport").attrContent("width=device-width, initial-scale=1.0").__()
                .meta()
                .addAttr("http-equiv", "X-UA-Compatible")
                .attrContent("IE=Edge")
                .__() // meta
                .title().text("JFall 2013 Presentations - HtmlFlow").__()
                .link()
                .attrRel(EnumRelType.STYLESHEET)
                .attrHref("/webjars/bootstrap/css/bootstrap.min.css")
                .attrMedia(EnumMediaType.SCREEN)
                .__() // link
                .__() // head
                .body()
                .div().attrClass("container")
                .div().attrClass("pb-2 mt-4 mb-3 border-bottom")
                .h1().text("JFall 2013 Presentations - HtmlFlow").__()
                .__() // div
                .dynamic((container, pet) ->
                        presentations.forEach(presentation ->
                                container
                                        .div().attrClass("card mb-3 shadow-sm rounded")
                                        .div().attrClass("card-header")
                                        .h5()
                                        .of(h5 -> h5
                                                .attrClass("card-title")
                                                .text(presentation.getTitle() + " - " + presentation.getSpeakerName())
                                        )
                                        .__() // h5
                                        .__() // div
                                        .div()
                                        .of(d -> d
                                                .attrClass("card-body")
                                                .text(presentation.getSummary())
                                        )
                                        .__() // div
                                        .__() // div
                        ) // foreach
                )
                .__() // container
                .script().attrSrc("/webjars/jquery/jquery.min.js").__()
                .script().attrSrc("/webjars/bootstrap/js/bootstrap.min.js").__()
                .__() // body
                .__()); // html
    }
}
