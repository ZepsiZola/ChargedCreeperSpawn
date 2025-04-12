package zepsizola.me.chargedcreeperspawn;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public final class ChargedCreeperSpawn extends JavaPlugin implements Listener {

    private double chance; // 1% chance
    private final Random random = new Random();

    @Override
    public void onEnable() {
        // Save default config if it doesn't exist
        saveDefaultConfig();

        // Load the chance value from the config
        FileConfiguration config = getConfig();
        chance = config.getDouble("charged-creeper-chance", 0.01); // Default to 1% if not set
        if (chance < 0 || chance > 1) {
            getLogger().warning("Invalid chance value in config. It should be between 0 and 1. Defaulting to 0.01");
            chance = 0.01; // Default to 1% if invalid
        }

        // Register the event listener
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        // Using EntityType directly avoids instanceof check
        if (event.getEntityType() == EntityType.CREEPER && random.nextDouble() <= chance) {
            ((Creeper) event.getEntity()).setPowered(true);
        }
    }
}
