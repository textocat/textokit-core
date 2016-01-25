/*
 *    Copyright 2015 Textocat
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.textocat.textokit.commons.io;

import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * @author Rinat Gareev
 */
public class IoUtilsTest {

    @Test
    public void testAddExtension() {
        assertEquals(Paths.get("/home/user.ext"), IoUtils.addExtension(Paths.get("/home/user"), "ext"));
        assertEquals(Paths.get("home/user.ext"), IoUtils.addExtension(Paths.get("home/user"), "ext"));
        assertEquals(Paths.get("home/user.ext"), IoUtils.addExtension(Paths.get("home/user/"), "ext"));
        assertEquals(Paths.get("user.ext"), IoUtils.addExtension(Paths.get("user"), "ext"));
        assertEquals(Paths.get("user.ext"), IoUtils.addExtension(Paths.get("user/"), "ext"));
    }

    @Test
    public void testExtractPathFromURI() {
        assertEquals(Paths.get("1376"), IoUtils.extractPathFromURI("1376"));
        assertEquals(Paths.get("folder/doc1.txt"), IoUtils.extractPathFromURI("folder/doc1.txt"));
        assertEquals(Paths.get("/folder/doc1.txt"), IoUtils.extractPathFromURI("http://example.org/folder/doc1.txt"));
    }
}
