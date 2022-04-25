package byuntil.backend.common.factory;

import org.apache.http.entity.ContentType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Objects;

public class MockFileFactory {
    private static final String FILE_KEY = "file";
    private static final ClassLoader CLASS_LOADER = MockFileFactory.class.getClassLoader();

    public static MockMultipartFile createMultipartFileImage() {
        final File file = createFile("testImage.png");
        return imageToMultipart(file);
    }

    public static MockMultipartFile createMultipartFileText() {
        final File file = createFile("testText.txt");
        return textToMultipart(file);
    }

    public static File createImageFile() {
        return createFile("testImage.png");
    }

    public static File createTextFile() {
        return createFile("testText.txt");
    }

    public static File invalidFile() {
        return createFile("invalidFile.invalid");
    }

    private static MockMultipartFile textToMultipart(final File file) {
        try {
            return new MockMultipartFile(
                    FILE_KEY,
                    file.getName(),
                    ContentType.TEXT_PLAIN.getMimeType(),
                    Files.readAllBytes(file.toPath())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static MockMultipartFile imageToMultipart(final File file) {
        try {
            return new MockMultipartFile(
                    FILE_KEY,
                    file.getName(),
                    ContentType.IMAGE_JPEG.getMimeType(),
                    Files.readAllBytes(file.toPath())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static File createFile(final String s) {
        final URL resource = CLASS_LOADER.getResource(s);
        Objects.requireNonNull(resource);
        return new File(resource.getFile());
    }
}
