package fr.astfaster.simplenetty.api.connection;

import io.netty.channel.Channel;

public interface StringHandler {

    void connected(Channel channel);

    void disconnected(Channel channel);

    void handle(String msg);

    void exception(Throwable cause);

}
