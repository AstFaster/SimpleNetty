package fr.astfaster.simplenetty.server.connection;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class ServerConnectionManager {

    private Channel channel;

    public void bind(InetSocketAddress address) {
        final EventLoopGroup bossGroup = new NioEventLoopGroup();
        final EventLoopGroup workerGroup = new NioEventLoopGroup();


        final ServerBootstrap b = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerInitializer())
                .childOption(ChannelOption.TCP_NODELAY, true)
                .localAddress(address);

        b.bind().addListener((ChannelFutureListener) f -> {
           final Channel channel = f.channel();
           if (f.isSuccess()) {
               this.channel = channel;
               System.out.printf("Listening on %s.\n", address);
           }
           else
               System.out.printf("Couldn't bind to host %s. Cause: %s\n", address, f.cause());
        });
    }

    public void shutdown() {
        try {
            System.out.println("Stopping channel.");
            this.channel.close().sync();
        } catch (InterruptedException e) {
            System.out.printf("Couldn't close channel due to an interruption: %s\n", e);
            Thread.currentThread().interrupt();
        }
    }

}
