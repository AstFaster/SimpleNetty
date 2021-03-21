package fr.astfaster.simplenetty.client.connection;

import fr.astfaster.simplenetty.client.SimpleNettyClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class ClientConnectionManager {

    private final SimpleNettyClient client;

    private Channel channel;

    public ClientConnectionManager(SimpleNettyClient client) {
        this.client = client;
    }

    public void connect(InetSocketAddress address) {
        if (this.channel != null && this.channel.isActive()) return;

        final EventLoopGroup workerGroup = new NioEventLoopGroup();

        final Bootstrap b = new Bootstrap()
                .group(workerGroup)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .channel(NioSocketChannel.class)
                .handler(new ClientInitializer(this.client));

        final ChannelFuture f = b.connect(address);
        f.awaitUninterruptibly();

        if (f.isSuccess()) System.out.println("Client connected");
        this.channel = f.channel();

        this.client.sendString("handshake");
    }

}
