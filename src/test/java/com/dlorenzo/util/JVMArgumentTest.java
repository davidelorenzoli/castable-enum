package com.dlorenzo.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JVMArgumentTest {
    @Before
    public void before() {
        System.setProperty(JVMArgument.SOURCE_ID.getKey(), "123");
        System.setProperty(JVMArgument.WEB_SERVER.getKey(), "http://webserver.com");
        System.setProperty(JVMArgument.FEATURES.getKey(), "logDebug;disCache;sso");
    }

    @After
    public void after() {
        System.clearProperty(JVMArgument.SOURCE_ID.getKey());
        System.clearProperty(JVMArgument.WEB_SERVER.getKey());
        System.clearProperty(JVMArgument.FEATURES.getKey());
    }

    @Test
    public void asURL() {
        URL url = JVMArgument.WEB_SERVER.asURL();

        assertNotNull(url);
        assertEquals("http://webserver.com", url.toString());
    }

    @Test
    public void asArray() {
        List<String> list = JVMArgument.FEATURES.asList(";", String.class);

        assertNotNull(list);
        assertEquals(3, list.size());
    }

    @Test
    public void asArrayNotDefinedArgument() {
        System.clearProperty(JVMArgument.FEATURES.getKey());

        List<String> list = JVMArgument.FEATURES.asList(";", String.class);

        assertNotNull(list);
        assertEquals(0, list.size());
    }
}
