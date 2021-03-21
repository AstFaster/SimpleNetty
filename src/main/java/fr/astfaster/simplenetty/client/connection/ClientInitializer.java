package fr.astfaster.simplenetty.client.connection;

import fr.astfaster.simplenetty.api.connection.ConnectionHandler;
import fr.astfaster.simplenetty.client.SimpleNettyClient;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class ClientInitializer extends ChannelInitializer<SocketChannel> {

    private final SimpleNettyClient client;

    public ClientInitializer(SimpleNettyClient client) {
        this.client = client;
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
        ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
        ch.pipeline().addLast(new ConnectionHandler(new ClientHandler(this.client)));
    }
}
