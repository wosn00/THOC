package client;

import server.NioServer;

import java.util.Scanner;

/**
 * @author: hs
 */
public class NioClient {

    private static NioClientHandle nioClientHandle;
    private static String CONNECT_HOST = "127.0.0.1";
    private static int CONNECT_PORT = NioServer.DEFAULT_PORT;


    public static void start() {
        if (nioClientHandle != null)
            nioClientHandle.stop();
        nioClientHandle = new NioClientHandle(CONNECT_HOST, CONNECT_PORT);
        new Thread(nioClientHandle, "Server").start();
    }

    //向服务器发送消息
    public static boolean sendMsg(String msg) throws Exception {
        nioClientHandle.sendMsg(msg);
        return true;
    }

    public static void main(String[] args) throws Exception {
        start();
        Scanner scanner = new Scanner(System.in);
        while (NioClient.sendMsg(scanner.next())) ;

    }

}
