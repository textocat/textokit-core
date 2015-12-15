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

import org.apache.commons.lang3.SerializationUtils;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.factory.ExternalResourceFactory;
import org.apache.uima.resource.ExternalResourceDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;

import java.io.BufferedInputStream;
import java.util.Map;

import static java.lang.String.format;

/**
 * @author Rinat Gareev
 */
public class SerializedResourceLocator extends SpringResourceLocator {

    public static ExternalResourceDescription createDescription(
            String serializedDictPath, Class<?> expectedResourceClass) {
        return ExternalResourceFactory.createExternalResourceDescription(
                SerializedResourceLocator.class,
                PARAM_RESOURCE_LOCATION, serializedDictPath,
                PARAM_EXPECTED_CLASS, expectedResourceClass);
    }

    public static final String PARAM_EXPECTED_CLASS = "expectedResourceClass";
    // config fields
    @ConfigurationParameter(name = PARAM_EXPECTED_CLASS, mandatory = false)
    private Class<?> expectedClass;
    // state fields
    protected Object resourceObject;

    @Override
    public boolean initialize(ResourceSpecifier aSpecifier, Map<String, Object> aAdditionalParams)
            throws ResourceInitializationException {
        if (!super.initialize(aSpecifier, aAdditionalParams)) return false;
        //
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
