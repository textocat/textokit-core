/**
 *
 */
package ru.kfu.itis.cll.uima.cpe;

import com.google.common.base.Function;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;

/**
 * Package-private utils.
 *
 * @author Rinat Gareev
 */
class PUtils {
    static final Function<File, Resource> file2Resource = new Function<File, Resource>() {
        @Override
        public Resource apply(File f) {
            return new FileSystemResource(f);
        }
    };
}
