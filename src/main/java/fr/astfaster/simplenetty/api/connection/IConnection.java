package fr.astfaster.simplenetty.api.connection;

import java.net.InetSocketAddress;

public interface IConnection {

    void sendString(String msg);

    void disconnect(String reason);

    InetSocketAddress getAddress();

}
