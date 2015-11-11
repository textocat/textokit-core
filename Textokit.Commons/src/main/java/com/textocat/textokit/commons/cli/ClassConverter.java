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


package com.textocat.textokit.commons.cli;

import com.beust.jcommander.ParameterException;
import com.beust.jcommander.converters.BaseConverter;

/**
 * @author Rinat Gareev
 */
public class ClassConverter extends BaseConverter<Class<?>> {

    public ClassConverter(String optionName) {
        super(optionName);
    }

    @Override
    public Class<?> convert(String value) {
        try {
            return Class.forName(value);
        } catch (ClassNotFoundException e) {
            throw new ParameterException(String.format(
                    "Class %s does not exist", value));
        }
    }

}
