package it.nanno.spigot.command.registry;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;

import java.lang.reflect.Field;
import java.util.Map;

public class CommandRegistry {

    public static void registerCommand(Command customCommand) {

        final CraftServer craftServer = (CraftServer) Bukkit.getServer();
        final SimpleCommandMap simpleCommandMap = craftServer.getCommandMap();

        try {

            final Field commandMapField = SimpleCommandMap.class.getDeclaredField("knownCommands");
            commandMapField.setAccessible(true);

            final Field knowCommandsField = simpleCommandMap.getClass().getDeclaredField("knownCommands");
            knowCommandsField.setAccessible(true);

            final Map<String, Command> commands = (Map<String, Command>) knowCommandsField.get(simpleCommandMap);

            Lists.newArrayList(simpleCommandMap.getCommands()).stream()
                    .filter(command -> command.getName().equalsIgnoreCase(customCommand.getName()))
                    .forEach(command -> {
                        command.unregister(simpleCommandMap);
                        commands.remove(command.getName());
                        command.getAliases().forEach(commands::remove);
                    });

        } catch (Exception exception) { exception.printStackTrace(); }

        simpleCommandMap.register(customCommand.getName(), customCommand);
    }
}