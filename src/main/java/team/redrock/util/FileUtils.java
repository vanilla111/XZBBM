package team.redrock.util;

import team.redrock.common.FileType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
    /**
     * 将文件头转换成16进制字符串
     *
     * @param
     * @return 16进制字符串
     */
    private static String bytesToHexString(byte[] src){

        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    private static String getFileContent(File file) throws IOException {
        byte[] b = new byte[28];

        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(file);
            inputStream.read(b, 0, 28);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        return bytesToHexString(b);
    }

    private static String getFileContent(InputStream inputStream) throws IOException {
        byte[] b = new byte[28];
        inputStream.read(b, 0, 28);
        return bytesToHexString(b);
    }

    private static String getFileContent(String filePath) throws IOException {
        return getFileContent(new File(filePath));
    }

    /**
     * 判断文件类型
     * @return 文件类型
     */
    public static FileType getType(String filePath) throws IOException {
        return getType(new File(filePath));
    }

    public static FileType getType(InputStream inputStream) throws IOException {
        String fileHead = getFileContent(inputStream);
        return fileHeadToType(fileHead);
    }

    public static FileType getType(File file) throws IOException {
        String fileHead = getFileContent(file);
        return fileHeadToType(fileHead);
    }

    private static FileType fileHeadToType(String fileHead) {
        if (fileHead == null || fileHead.length() == 0) {
            return null;
        }

        fileHead = fileHead.toUpperCase();

        FileType[] fileTypes = FileType.values();

        for (FileType type : fileTypes) {
            if (fileHead.startsWith(type.getValue())) {
                return type;
            }
        }

        return null;
    }
}
