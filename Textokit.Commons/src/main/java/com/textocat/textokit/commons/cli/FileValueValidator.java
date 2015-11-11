/**
 *
 */
package com.textocat.textokit.commons.cli;

import com.beust.jcommander.IValueValidator;
import com.beust.jcommander.ParameterException;

import java.io.File;

/**
 * @author Rinat Gareev
 */
public class FileValueValidator implements IValueValidator<File> {

    @Override
    public void validate(String name, File value) throws ParameterException {
        if (!value.isFile()) {
            throw new ParameterException(String.format(
                    "%s is not an existing file", value));
        }
    }

}
