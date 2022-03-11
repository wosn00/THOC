package com.hex.file.mmap;

import com.hex.file.FileUtil;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * @author guohs
 * @date 2022/3/11
 */
public class FileMmapDemo {

    public static final String FILE_PATH = "E:\\mmapTest.txt";

    public static final int length = 1024;

    public static void main(String[] args) throws Exception {
        //创建测试文本文件
        FileUtil.createFile(FILE_PATH);

        //mmap方式写数据
        mmapWrite();

        //mmap方式读数据
        mmapRead();
    }

    private static void mmapWrite() throws Exception {
        try (FileChannel channel = new RandomAccessFile(FILE_PATH, "rw").getChannel()) {

            //调用系统底层的mmap函数,将文件直接映射到用户空间,位置从0-1024
            MappedByteBuffer mapBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, length);

            //直接把数据从用户空间拷贝到了磁盘，只进行了一次拷贝，效率比常规write高，适用于写大文件
            for (int i = 0; i < length; i++) {
                // 顺序放入1024个字母
                mapBuffer.put((byte) ((int) 'a' + i % 26));
            }
            //保证数据会被写入文件
            mapBuffer.force();
        }

        System.out.println("---------write finish---------");
    }

    private static void mmapRead() throws Exception {
        try (FileChannel channel = new RandomAccessFile(FILE_PATH, "rw").getChannel()) {

            MappedByteBuffer mapBuffer = channel.map(FileChannel.MapMode.PRIVATE, 0, length);

            for (int i = 0; i < length; i++) {
                System.out.print((char) mapBuffer.get(i));
            }
        }
        System.out.println();
        System.out.println("---------read finish---------");
    }
}
