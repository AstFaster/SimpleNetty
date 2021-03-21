package fr.astfaster.simplenetty.client.connection;

import fr.astfaster.simplenetty.api.connection.IConnection;
import fr.astfaster.simplenetty.api.connection.StringHandler;
import fr.astfaster.simplenetty.client.SimpleNettyClient;
import io.netty.channel.Channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class ClientHandler implements StringHandler, IConnection {

    private Channel channel;

    private final SimpleNettyClient client;

    public ClientHandler(SimpleNettyClient client) {
        this.client = client;
    }

    @Override
    public void connected(Channel channel) {
        this.channel = channel;

        this.client.setConnection(this);

        System.out.println("Channel connected.");
    }

    @Override
    public void disconnected(Channel channel) {
        System.out.println("Channel disconnected.");
    }

    @Override
    public void handle(String msg) {
        System.out.printf("Received from server: %s", msg);
    }

    @Override
    public void exception(Throwable cause) {
        if (cause instanceof IOException)
            System.out.println("Encountered IOException, the server may be down.");
        else
            System.out.printf("Encountered exception: %s", cause.getMessage());
    }

    @Override
    public void sendString(String msg) {
        if (this.channel == null || !this.channel.isActive()) return;

        this.channel.writeAndFlush(msg);
    }

    @Override
    public void disconnect(String reason) {
        System.err.printf("Disconnected. The reason is %s\n", reason);

        if (this.channel.isActive())
            this.channel.eventLoop().schedule(() -> this.channel.close(), 500, TimeUnit.MILLISECONDS);
    }

    @Override
    public InetSocketAddress getAddress() {
        return (InetSocketAddress) channel.remoteAddress();
    }
}
