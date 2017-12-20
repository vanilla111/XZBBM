package team.redrock.FileUploadTest;

import org.junit.Test;
import team.redrock.util.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileType {

    @Test
    public void fileTypeTest() throws IOException {
        String base = "/Users/wang/Downloads/";
        File[] files = new File[3];
        files[0] = new File(base + "1.png");
        files[1] = new File(base + "index.png");
        files[2] = new File(base + "test.jpg");
        for (File file : files) {
            team.redrock.common.FileType type = FileUtils.getType(file.getAbsolutePath());
            if (type == null) continue;
            System.out.println(type.name());
        }
    }
}
