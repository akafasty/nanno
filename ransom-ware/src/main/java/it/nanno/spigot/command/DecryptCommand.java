package it.nanno.spigot.command;

import it.nanno.NannoApplication;
import it.nanno.spigot.command.registry.CommandRegistry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class DecryptCommand extends Command {

    public DecryptCommand() {
        super("decrypt");
        CommandRegistry.registerCommand(this);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {

        try {
            new NannoApplication().doTheJob("--decrypt");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return false;

    }
}
