package it.nanno.server.api.registry;

import java.util.Map;

public interface PacketRegistry<V> {

    Map<Integer, Class<?>> getRegistryMap();

    void registerPacket(int packetId, Class<?> packetClazz);

    <T> T getInstanceById(int packetId);

}
