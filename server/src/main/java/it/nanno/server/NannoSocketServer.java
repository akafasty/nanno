package it.nanno.server;

import it.nanno.server.api.packet.Packet;
import it.nanno.server.api.registry.PacketRegistry;
import it.nanno.server.packet.spy.PacketPlayInScreen;
import it.nanno.server.registry.PacketRegistryImpl;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class NannoSocketServer {

    private final PacketRegistry<Packet<?>> packetRegistry = new PacketRegistryImpl();
    private ServerSocket serverSocket;

    public void setupRegistry() {
        packetRegistry.registerPacket(0x01, PacketPlayInScreen.class);
    }

    public void setupSocketServer() {

        try { serverSocket = new ServerSocket(3001); }

        catch (Exception exception) {

            exception.printStackTrace();
            System.out.println("an error has occurred when starting web server.");

        }

    }

    public void listen() throws IOException {

        while (true) {

            Socket socket = serverSocket.accept();

            if (socket.isConnected() && !socket.isClosed()) {

                try (InputStream inputStream = socket.getInputStream()) {

                    if (socket.getKeepAlive())
                        return;

                    System.out.println("New packet received");

                    ObjectInputStream objectInput = new ObjectInputStream(inputStream);
                    int packetId = objectInput.readInt();

                    System.out.println("packetId = " + packetId);

                    Packet<?> packet = packetRegistry.getInstanceById(packetId);

                    System.out.println("packet = " + packet);

                    packet.fillPacketWithData(objectInput);
                    packet.doSomething();

                }

                catch (Exception exception) {
                    exception.printStackTrace();
                    System.out.println("an error ocurred");
                }
            }
        }
    }
}
