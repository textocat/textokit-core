package com.textocat.textokit.commons.io;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * @author Rinat Gareev
 */
public class IoUtilsTest {

    @Test
    public void testAddExtension() {
        Assert.assertEquals(Paths.get("/home/user.ext"), IoUtils.addExtension(Paths.get("/home/user"), "ext"));
        Assert.assertEquals(Paths.get("home/user.ext"), IoUtils.addExtension(Paths.get("home/user"), "ext"));
        Assert.assertEquals(Paths.get("home/user.ext"), IoUtils.addExtension(Paths.get("home/user/"), "ext"));
        Assert.assertEquals(Paths.get("user.ext"), IoUtils.addExtension(Paths.get("user"), "ext"));
        Assert.assertEquals(Paths.get("user.ext"), IoUtils.addExtension(Paths.get("user/"), "ext"));
    }
}
