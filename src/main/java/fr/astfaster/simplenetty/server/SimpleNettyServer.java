package fr.astfaster.simplenetty.server;

import fr.astfaster.simplenetty.server.connection.ServerConnectionManager;

import java.net.InetSocketAddress;

public class SimpleNettyServer {

    private final ServerConnectionManager connectionManager;

    public SimpleNettyServer() {
        this.connectionManager = new ServerConnectionManager();
    }

    public void start() {
        System.out.println("Starting server...");

        this.connectionManager.bind(new InetSocketAddress("0.0.0.0", 8080));
    }

    public void shutdown() {
        System.out.println("Shutting down server...");

        this.connectionManager.shutdown();
    }

    public ServerConnectionManager getConnectionManager() {
        return connectionManager;
    }
}
