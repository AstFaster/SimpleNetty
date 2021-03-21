package fr.astfaster.simplenetty.server.connection;

import fr.astfaster.simplenetty.api.connection.IConnection;
import fr.astfaster.simplenetty.api.connection.StringHandler;
import io.netty.channel.Channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class ServerHandler implements StringHandler, IConnection {

    private boolean isValid = false;
    private Channel channel;

    @Override
    public void connected(Channel channel) {
        this.channel = channel;

        System.out.printf("[%s] <-> has connected.\n", this.getAddress().toString());
    }

    @Override
    public void disconnected(Channel channel) {
        System.out.printf("[%s] <-> has disconnected.\n", this.getAddress().toString());
    }

    @Override
    public void handle(String msg) {
        if (!this.isValid && msg.equalsIgnoreCase("handshake")) {
            this.initChannel();
            return;
        }

        if (!this.isValid) {
            this.disconnect("Channel not initialized !");
            return;
        }
        System.out.printf("[%s] <-> received: %s\n", this.getAddress().toString(), msg);
    }

    private void initChannel() {
        this.isValid = true;

        System.out.printf("[%s] <-> initialized.\n", this.getAddress().toString());
    }

    @Override
    public void exception(Throwable cause) {
        if (cause instanceof IOException)
            System.out.printf("[%s] - Encountered IOException: %s.", this.getAddress().toString(), cause.getMessage());
        else
            System.out.printf("[%s] - Encountered exception: %s.", this.getAddress().toString(), cause.getMessage());
    }

    @Override
    public void sendString(String msg) {
        if (this.channel == null || !this.channel.isActive()) return;

        this.channel.writeAndFlush(msg);
    }

    @Override
    public void disconnect(String reason) {
        System.err.printf("[%s] <-> kick by reason: %s\n", this.getAddress().toString(), reason);

        if (this.channel.isActive())
            this.channel.eventLoop().schedule(() -> this.channel.close(), 500, TimeUnit.MILLISECONDS);
    }

    @Override
    public InetSocketAddress getAddress() {
        return (InetSocketAddress) channel.remoteAddress();
    }
}
