package fr.astfaster.simplenetty.api.connection;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ConnectionHandler extends SimpleChannelInboundHandler<String> {

    private final StringHandler handler;

    public ConnectionHandler(StringHandler handler) {
        this.handler = handler;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        if (this.handler != null) this.handler.connected(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (this.handler != null) this.handler.disconnected(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        if (this.handler != null) this.handler.handle(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (ctx.channel().isActive()) {
            if (this.handler != null) this.handler.exception(cause);
            ctx.channel().close();
            ctx.close();
        }
    }
}
