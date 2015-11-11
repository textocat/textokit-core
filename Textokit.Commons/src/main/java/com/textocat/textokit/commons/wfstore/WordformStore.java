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


package com.textocat.textokit.commons.wfstore;

import java.io.File;

/**
 * @author Rinat Gareev
 */
public interface WordformStore<TagType> {

    TagType getTag(String wf);

    <T> T getProperty(String key, Class<T> valueClass);

    void setProperty(String key, Object value);

    void persist(File outFile) throws Exception;
}