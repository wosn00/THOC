package server;

/**
 * @author: hs
 */
public class NioServer {
    private static NioServerHandle nioServerHandle;
    public static int DEFAULT_PORT = 8888;

    public static void start() {
        if (nioServerHandle != null) {
            nioServerHandle.stop();
        }
        nioServerHandle = new NioServerHandle(DEFAULT_PORT);
        new Thread(nioServerHandle, "Server").start();
    }

    public static void main(String[] args) {
        start();
    }
}
