package zepsizola.me.chargedcreeperspawn;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
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

        // Register the event listener
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        // Check if the creature is a creeper
        if (event.getEntity() instanceof Creeper) {
            // Generate a random double between 0.0 and 1.0
            double randomChance = random.nextDouble();
            // Check if the chance is less than or equal to 1%
            if (randomChance <= chance) {
                // Cast the entity to a Creeper and set it to be powered (i.e., charged)
                ((Creeper) event.getEntity()).setPowered(true);
            }
        }
    }
}
