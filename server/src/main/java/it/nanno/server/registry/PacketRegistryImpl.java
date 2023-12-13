package it.nanno.server.registry;

import it.nanno.server.api.packet.Packet;
import it.nanno.server.api.registry.PacketRegistry;

import java.util.HashMap;
import java.util.Map;

public class PacketRegistryImpl implements PacketRegistry<Packet<?>> {

    private static PacketRegistry<Packet<?>> instance;

    private final Map<Integer, Class<?>> registryMap = new HashMap<>();

    public static PacketRegistry<Packet<?>> getRegistry() {

        if (PacketRegistryImpl.instance == null) PacketRegistryImpl.instance = new PacketRegistryImpl();

        return PacketRegistryImpl.instance;

    }

    @Override
    public Map<Integer, Class<?>> getRegistryMap() {
        return registryMap;
    }

    @Override
    public void registerPacket(int packetId, Class<?> packetClazz) {
        getRegistryMap().put(packetId, packetClazz);
    }

    @Override
    public Packet<?> getInstanceById(int packetId) {

        try { return (Packet<?>) getRegistryMap().get(packetId).newInstance(); }
        catch (Exception exception) { exception.printStackTrace(); }

        return null;

    }
}
