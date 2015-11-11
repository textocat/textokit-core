/**
 *
 */
package ru.kfu.itis.cll.uima.wfstore;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.io.*;
import java.util.Map;

import static org.apache.commons.io.FileUtils.openInputStream;
import static org.apache.commons.io.FileUtils.openOutputStream;
import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.apache.commons.io.IOUtils.toBufferedInputStream;
import static org.apache.commons.lang3.SerializationUtils.deserialize;

/**
 * @author Rinat Gareev
 */
@Parameters(separators = " =")
public class DefaultWordformStorePrinter {

    @Parameter(names = "-f", required = true)
    private File serFile;
    @Parameter(names = "-t")
    private File outFile;

    public static void main(String[] args) throws Exception {
        DefaultWordformStorePrinter printer = new DefaultWordformStorePrinter();
        new JCommander(printer).parse(args);
        printer.run();
    }

    private static final String escapeTabs(String src) {
        return src.replaceAll("\t", "\\t");
    }

    private void run() throws Exception {
        // deserialize
        DefaultWordformStore<?> ws = (DefaultWordformStore<?>) deserialize(toBufferedInputStream(
                openInputStream(serFile)));
        // print
        PrintWriter out;
        boolean closeOut;
        if (outFile == null) {
            out = new PrintWriter(System.out, true);
            closeOut = false;
        } else {
            OutputStream os = openOutputStream(outFile);
            out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(os, "utf-8")), true);
            closeOut = true;
        }
        try {
            for (Map.Entry<String, ?> e : ws.strKeyMap.entrySet()) {
                out.print(escapeTabs(e.getKey()));
                out.print('\t');
                out.print(e.getValue());
                out.println();
            }
        } finally {
            if (closeOut)
                closeQuietly(out);
        }
    }
}