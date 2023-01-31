package tech.adelemphii.pickpocket;

import org.bukkit.plugin.java.JavaPlugin;
import tech.adelemphii.pickpocket.listeners.PickpocketListener;

public final class Pickpocket extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PickpocketListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
