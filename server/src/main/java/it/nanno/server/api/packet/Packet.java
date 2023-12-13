package it.nanno.server.api.packet;

import java.io.ObjectInputStream;

public interface Packet<T> {

    void fillPacketWithData(ObjectInputStream objectInputStream);

    void doSomething();

}
