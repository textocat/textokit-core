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

import org.apache.uima.fit.component.Resource_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;
import org.springframework.core.io.ClassPathResource;

import java.util.Map;

/**
 * @author Rinat Gareev
 */
public abstract class ClasspathResourceBase extends Resource_ImplBase {
    public static final String PARAM_RESOURCE_CLASSPATH = "resourceClassPath";

    @ConfigurationParameter(name = PARAM_RESOURCE_CLASSPATH, mandatory = false)
    private String resourceClassPath;
    //
    protected ClassPathResource resource;

    @Override
    public boolean initialize(ResourceSpecifier aSpecifier, Map<String, Object> aAdditionalParams)
            throws ResourceInitializationException {
        if (!super.initialize(aSpecifier, aAdditionalParams))
            return false;
        if (resourceClassPath == null) {
            resourceClassPath = locateDefaultResourceClassPath();
        }
        resource = new ClassPathResource(resourceClassPath);
        return true;
    }

    protected abstract String locateDefaultResourceClassPath();
}
