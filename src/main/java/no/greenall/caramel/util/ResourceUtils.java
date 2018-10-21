package no.greenall.caramel.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ResourceUtils {

    private static final String EOL = System.lineSeparator();

    private static String replaceLineSeparators(String string) {
        return string.replaceAll("\\r\\n?", EOL);
    }

    public static String readString(String filename, boolean reformatLineEndings) throws IOException {
        String fileAsString = FileUtils.readFileToString(getFile(filename), StandardCharsets.UTF_8);
        return (reformatLineEndings) ? replaceLineSeparators(fileAsString) : fileAsString;
    }

    private static File getFile(String filename) {
        return new File(Objects.requireNonNull(ResourceUtils.class.getClassLoader().getResource(filename)).getFile());
    }


}
