package fr.astfaster.simplenetty.client;

import fr.astfaster.simplenetty.api.connection.IConnection;
import fr.astfaster.simplenetty.client.connection.ClientConnectionManager;

import java.net.InetSocketAddress;

public class SimpleNettyClient {

    private IConnection connection;

    private final ClientConnectionManager connectionManager;

    public SimpleNettyClient() {
        this.connectionManager = new ClientConnectionManager(this);
    }

    public void connect(InetSocketAddress address) {
        this.connectionManager.connect(address);
    }

    public void sendString(String msg) {
        this.connection.sendString(msg);
    }

    public ClientConnectionManager getConnectionManager() {
        return this.connectionManager;
    }

    public void setConnection(IConnection connection) {
        this.connection = connection;
    }
}
