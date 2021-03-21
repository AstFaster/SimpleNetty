package fr.astfaster.simplenetty.client;

import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.TimerTask;

public class Client {

    public static void main(String[] args) {
        final SimpleNettyClient client = new SimpleNettyClient();
        client.connect(new InetSocketAddress("localhost", 8080));
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                client.sendString("Just a simple string to test if my application is correctly working.");
            }
        }, 5000);
    }

}
