package com.hex.file.mmap;

import com.hex.file.FileUtil;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * @author guohs
 * @date 2022/3/11
 */
public class FileCopyDemo {

    public static final String FILE_SOUR = "E:\\FileSource.txt";

    public static final String FILE_DEST = "E:\\FileDestination.txt";

    public static void main(String[] args) throws Exception {
        //创建测试文本文件
        FileUtil.createFile(FILE_SOUR);
        FileUtil.createFile(FILE_DEST);

        FileUtil.writeSomething(FILE_SOUR, "I think therefore I am");
        //零拷贝
        fileZeroCopy(new File(FILE_SOUR), new File(FILE_DEST));

        FileUtil.writeSomething(FILE_SOUR, "Cogito, ergo sum");
        //常规拷贝
        fileCopy(new File(FILE_SOUR), new File(FILE_DEST));
    }

    /**
     * 零拷贝复制方式
     */
    private static void fileZeroCopy(File fromFile, File toFile) {

        try (FileChannel fromChannel = new FileInputStream(fromFile).getChannel();
             FileChannel toChannel = new FileOutputStream(toFile).getChannel()) {

            // 将fileChannelInput通道的数据，写入到fileChannelOutput通道
            // position 起始位置，相当于seek
            // 更高效的文件拷贝方式FileChannel，零拷贝方式
            // from the filesystem cache to the target channel without actually copying them.
            fromChannel.transferTo(0, fromChannel.size(), toChannel);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("------ file zero copy finish ------");
    }

    /**
     * 常规复制方式，多了两次拷贝
     */
    public static void fileCopy(File fromFile, File toFile) throws Exception {
        // 文件复制
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(fromFile));
             BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(toFile))) {

            byte[] bytes = new byte[1024];
            int len;
            while (-1 != (len = inputStream.read(bytes, 0, bytes.length))) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }

            System.out.println("------ file copy finish ------");
        }
    }
}
