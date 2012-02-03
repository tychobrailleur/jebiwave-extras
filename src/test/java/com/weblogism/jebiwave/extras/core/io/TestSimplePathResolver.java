package com.weblogism.jebiwave.extras.core.io;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.weblogism.jebiwave.extras.core.io.SimplePathResolver;

/**
 * 
 * @author Sébastien Le Callonnec
 * 
 */
public class TestSimplePathResolver {
    @Test
    public void testResolve() {
        String path = "some/path/to/a.story";
        assertEquals(path, new SimplePathResolver(path).resolve(null));
    }
}
