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

package com.textocat.textokit.resource;

import com.textocat.textokit.commons.util.UimaResourceUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.uima.fit.component.Resource_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.ExternalResourceLocator;
import org.apache.uima.fit.factory.ExternalResourceFactory;
import org.apache.uima.resource.ExternalResourceDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.BufferedInputStream;
import java.io.File;
import java.util.Map;

import static java.lang.String.format;

/**
 * @author Rinat Gareev
 */
public class SerializedResourceLocator extends Resource_ImplBase implements ExternalResourceLocator {

    public static ExternalResourceDescription createDescription(
            String serializedDictPath, Class<?> expectedResourceClass) {
        return ExternalResourceFactory.createExternalResourceDescription(
                SerializedResourceLocator.class,
                PARAM_SERIALIZED_PATH, serializedDictPath,
                PARAM_EXPECTED_CLASS, expectedResourceClass);
    }

    private static final String PARAM_SERIALIZED_PATH = "serializedResourcePath";
    private static final String PARAM_EXPECTED_CLASS = "expectedResourceClass";
    // config fields
    @ConfigurationParameter(name = PARAM_SERIALIZED_PATH)
    private String serializedPath;
    @ConfigurationParameter(name = PARAM_EXPECTED_CLASS, mandatory = false)
    private Class<?> expectedClass;
    private DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
    // state fields
    private Resource resourceMeta;
    private Object resourceObject;

    @Override
    public boolean initialize(ResourceSpecifier aSpecifier, Map<String, Object> aAdditionalParams)
            throws ResourceInitializationException {
        if (!super.initialize(aSpecifier, aAdditionalParams)) return false;
        //
        resourceMeta = resourceLoader.getResource(serializedPath);
        if (resourceMeta == null) {
            throw new IllegalStateException(format("Can't wrap path %s into Resource", serializedPath));
        }
        if (!resourceMeta.exists()) {
            // try to resolve against UIMA datapath
            try {
                File resourceFile = resourceMeta.getFile();
                resourceFile = UimaResourceUtils.resolveFile(resourceFile.getPath(), getResourceManager());
                resourceMeta = new FileSystemResource(resourceFile);
            } catch (Exception e) {
                throw new ResourceInitializationException(e);
            }
        }
        BufferedInputStream in;
        try {
            in = new BufferedInputStream(resourceMeta.getInputStream());
        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
        // closes stream
        resourceObject = SerializationUtils.deserialize(in);
        if (expectedClass != null && !expectedClass.isInstance(resourceObject)) {
            throw new IllegalStateException(format("Unexpected type of object in %s, expected - %s, actual - %s",
                    resourceMeta, expectedClass.getName(), resourceObject.getClass().getName()));
        }
        return true;
    }

    @Override
    public Object getResource() {
        return resourceObject;
    }
}
