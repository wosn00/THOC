package com.hex.file;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author guohs
 * @date 2022/3/11
 */
public class FileUtil {

    public static void createFile(String filePath) throws Exception {
        File file = new File(filePath);
        if (file.exists()) {
            boolean delete = file.delete();
            if (!delete) throw new RuntimeException("delete file failed");
        }
        boolean newFile = file.createNewFile();
        if (!newFile) throw new RuntimeException("create file failed");
    }

    public static void writeSomething(String filePath, String text) throws Exception {
        FileOutputStream outputStream = new FileOutputStream(new File(filePath));
        outputStream.write(text.getBytes());
    }
}
