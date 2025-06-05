package com.jeroenreijn.examples.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ModelMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PresentationsControllerTest {

    @Autowired
    private PresentationsController controller;
    private ModelMap modelMap;

    @BeforeEach
    public void setUp() throws Exception {
        modelMap = new ModelMap();
    }

    @Test
    public void should_return_jsp_view() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("localhost");
        request.setRequestURI("/");

        String view = controller.home(request, modelMap);
        assertEquals("index-jsp", view);
    }

    @Test
    public void should_return_other_view() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("localhost");
        request.setRequestURI("/test");

        final String view = controller.showList(request, "test", modelMap);
        assertEquals("index-test", view);
    }

    @Test
    public void should_return_velocity_view() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("localhost");
        request.setRequestURI("/velocity");

        String view = controller.showList(request, "velocity", modelMap);
        assertEquals("index-velocity", view);
    }
}
