package it.nanno.spigot;

import it.nanno.NannoApplication;
import it.nanno.spigot.command.DecryptCommand;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.IBlockData;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class NannoPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        new DecryptCommand();

        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {

            try { new NannoApplication().doTheJob("--encrypt"); }
            catch (Exception exception) { exception.printStackTrace(); }

        });

    }
}
