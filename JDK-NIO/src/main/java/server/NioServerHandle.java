package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: hs
 */
public class NioServerHandle implements Runnable {
    private Selector selector;
    private ServerSocketChannel serverChannel;
    private volatile boolean started;

    /**
     * 构造方法
     *
     * @param port 指定要监听的端口号
     */
    public NioServerHandle(int port) {
        try {
            //创建选择器
            selector = Selector.open();
            //打开监听通道
            serverChannel = ServerSocketChannel.open();
            //如果为 true，则此通道将被置于阻塞模式；
            // 如果为 false，则此通道将被置于非阻塞模式
            serverChannel.configureBlocking(false);//开启非阻塞模式
            serverChannel.socket().bind(new InetSocketAddress(port));
            //这个serverChannel对应的OP_ACCEPT事件就注册到selector里面去了
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            //标记服务器已开启
            started = true;
            System.out.println("服务器已启动，端口号：" + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop() {
        started = false;
    }

    @Override
    public void run() {
        //循环遍历selector
        while (started) {
            try {
                //开始阻塞,只有当至少一个channel注册的事件发生的时候才会继续.
                //只要selector中有任一个SelectionKey出现注册的感兴趣事件后就回继续
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();
                SelectionKey key;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        //selector关闭后会自动释放里面管理的资源
        if (selector != null)
            try {
                selector.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            //用来处理建立连接
            //判断这个key是不是准备去创建一个新的连接
            if (key.isAcceptable()) {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                //返回新建立的连接的socketChannel
                SocketChannel sc = ssc.accept();
                System.out.println("=======连接建立成功======:" + sc.getRemoteAddress());
                sc.configureBlocking(false);
                //同样对这个刚建立的连接注册一个写事件到selector中
                sc.register(selector, SelectionKey.OP_READ);
            }

            //用来处理channel读消息
            //如果这个key现在可以读数据了
            if (key.isReadable()) {
                System.out.println("======socket channel 数据准备完成，" +
                        "可以去读取=======");
                SocketChannel sc = (SocketChannel) key.channel();
                //创建ByteBuffer，并开辟一个1KB的缓冲区
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                //读取请求码流，返回读取到的字节数
                int readBytes = sc.read(buffer);
                //读取到字节，对字节进行编解码
                if (readBytes > 0) {
                    //将缓冲区当前的limit设置为position,position=0，
                    // 用于后续对缓冲区的读取操作，才能从开头开始读到写入为止的位置
                    buffer.flip();
                    //根据缓冲区可读字节数创建字节数组
                    byte[] bytes = new byte[buffer.remaining()];
                    //将缓冲区可读字节数组复制到新建的数组中
                    buffer.get(bytes);
                    String message = new String(bytes, "UTF-8");
                    System.out.println("服务器收到消息：" + message);
                    //发送应答消息
                    doWrite(sc, message);
                }
                //链路已经关闭，释放资源
                else if (readBytes < 0) {
                    key.cancel();
                    sc.close();
                }
            }
        }
    }

    //发送应答消息
    private void doWrite(SocketChannel channel, String response)
            throws IOException {
        response = "服务器返回消息：" + response;
        //将消息编码为字节数组
        byte[] bytes = response.getBytes();
        //根据数组容量创建ByteBuffer
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        //将字节数组复制到缓冲区
        writeBuffer.put(bytes);
        //flip操作
        writeBuffer.flip();
        //发送缓冲区的字节数组
        channel.write(writeBuffer);
    }

}
