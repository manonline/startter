package com.manonline.examples.socket.nio;

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
 * Created by davidqi on 2/3/17.
 * http://www.iteye.com/magazines/132-Java-NIO
 * 1. 所有方法均是非阻塞(设置),即使没有数据，没有连接，均立即返回。
 * 2. 这样会导致读取的数据需要重新组织。不停的去轮询，直到读渠道所有的数据。或者，
 * 3. 由通道(Channel) 去通知，也即Selector，再由Selector处理准备好的请求。
 * 4. 单个线程处理所有请求，节省了线程切换的开销；耗时的处理可以继续用其他的线程处理，以免阻塞其他的处理请求。
 * ========================================
 * 1. Java IO的各种流是阻塞的。这意味着，当一个线程调用read() 或 write()时，该线程被阻塞，直到有一些数据被读取，或数据完全写入。
 * 该线程在此期间不能再干任何事情了。 Java NIO的非阻塞模式，使一个线程从某通道发送请求读取数据，但是它仅能得到目前可用的数据，
 * 如果目前没有数据可用时，就什么都不会获取。而不是保持线程阻塞，所以直至数据变的可以读取之前，该线程可以继续做其他的事情。
 * 2. Java NIO的选择器允许一个单独的线程来监视多个输入通道，你可以注册多个通道使用一个选择器，然后使用一个单独的线程来“选择”通道：
 * 这些通道里已经有可以处理的输入，或者选择已准备写入的通道。这种选择机制，使得一个单独的线程很容易来管理多个通道。
 * 3. NIO可让您只使用一个（或几个）单线程管理多个通道（网络连接或文件），但付出的代价是解析数据可能会比从一个阻塞流中读取数据更复杂。
 * ========================================
 * 通道触发了一个事件意思是该事件已经就绪。所以，某个channel成功连接到另一个服务器称为“连接就绪”。
 * 一个server socket channel准备好接收新进入的连接称为“接收就绪”。一个有数据可读的通道可以说是“读就绪”。
 * 等待写数据的通道可以说是“写就绪”。这四种事件用SelectionKey的四个常量来表示：
 * SelectionKey.OP_CONNECT
 * SelectionKey.OP_ACCEPT
 * SelectionKey.OP_READ
 * SelectionKey.OP_WRITE
 * ========================================
 * SelectionKey 记录Channel和Selector的绑定关系
 * SelectionKey key = channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
 * ========================================
 * 从SelectionKey访问Channel和Selector
 * Channel  channel  = selectionKey.channel();
 * Selector selector = selectionKey.selector();
 * ========================================
 * 从SelectionKey中获取关系
 * int interestSet = selectionKey.interestOps();
 * boolean isInterestedInAccept  = (interestSet & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT；
 * boolean isInterestedInConnect = interestSet & SelectionKey.OP_CONNECT;
 * boolean isInterestedInRead    = interestSet & SelectionKey.OP_READ;
 * boolean isInterestedInWrite   = interestSet & SelectionKey.OP_WRITE;
 * ========================================
 * 从SelectionKey中获取事件：可以用像检测interest集合那样的方法，来检测channel中什么事件或操作已经就绪。或者
 * int readySet = selectionKey.readyOps();
 * selectionKey.isAcceptable();
 * selectionKey.isConnectable();
 * selectionKey.isReadable();
 * selectionKey.isWritable();
 * ========================================
 */
public class NIOServer {
    //通道管理器
    private Selector selector;

    /**
     * 获得一个ServerSocket通道，并对该通道做一些初始化的工作
     *
     * @param port 绑定的端口号
     * @throws IOException
     */
    public void initServer(int port) throws IOException {
        // 获得一个ServerSocket通道
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        // 设置通道为非阻塞
        serverChannel.configureBlocking(false);
        // 将该通道对应的ServerSocket绑定到port端口
        serverChannel.socket().bind(new InetSocketAddress(port));
        // 获得一个通道管理器
        this.selector = Selector.open();
        // 将通道管理器和该通道绑定，并为该通道注册SelectionKey.OP_ACCEPT事件,注册该事件后，
        // 当该事件到达时，selector.select()会返回，如果该事件没到达selector.select()会一直阻塞。
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    /**
     * 采用轮询的方式监听selector上是否有需要处理的事件，如果有，则进行处理
     *
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public void listen() throws IOException {
        System.out.println("服务端启动成功！");
        // 轮询访问selector
        while (true) {
            // 当注册的事件到达时，方法返回；否则,该方法会一直阻塞
            int num = selector.select();
            // 访问“已选择键集（selected key set）”中的就绪通道
            Set selectedKeys = selector.selectedKeys();
            // 获得selector中选中的项的迭代器，选中的项为注册的事件
            Iterator ite = selectedKeys.iterator();
            while (ite.hasNext()) {
                // 返回一个键
                SelectionKey key = (SelectionKey) ite.next();
                // 删除已选的key,以防重复处理
                ite.remove();
                // 客户端请求连接事件
                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    // 获得和客户端连接的通道, 此处为非阻塞所以不论accept()有没有成功建立连接，均会返回
                    SocketChannel channel = server.accept();
                    // 此处已经不需要做判断，因为是时间通知过来，所以必然能建立
                    // if(channel != null){
                    // //do something with socketChannel...
                    // }

                    // 设置成非阻塞
                    channel.configureBlocking(false);

                    //在这里可以给客户端发送信息哦
                    channel.write(ByteBuffer.wrap(new String("向客户端发送了一条信息").getBytes()));
                    //在和客户端连接成功之后，为了可以接收到客户端的信息，需要给通道设置读的权限。
                    channel.register(this.selector, SelectionKey.OP_READ);

                    // 获得了可读的事件
                } else if (key.isReadable()) {
                    read(key);
                }
            }
        }
    }

    /**
     * 处理读取客户端发来的信息 的事件
     *
     * @param key
     * @throws IOException
     */
    public void read(SelectionKey key) throws IOException {
        // 服务器可读取消息:得到事件发生的Socket通道
        SocketChannel channel = (SocketChannel) key.channel();
        // 创建读取的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(10);
        channel.read(buffer);
        byte[] data = buffer.array();
        String msg = new String(data).trim();
        System.out.println("服务端收到信息：" + msg);
        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
        // 将消息回送给客户端
        channel.write(outBuffer);
    }

    /**
     * 启动服务端测试
     *
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        NIOServer server = new NIOServer();
        server.initServer(8000);
        server.listen();
    }

}