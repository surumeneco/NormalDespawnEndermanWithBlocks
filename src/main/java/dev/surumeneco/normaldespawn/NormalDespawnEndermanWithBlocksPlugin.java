package dev.surumeneco.normaldespawn;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class NormalDespawnEndermanWithBlocksPlugin extends JavaPlugin {
    private EndermanDespawnTask task;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        try {
            DespawnPolicy policy = loadPolicy(getConfig());
            task = new EndermanDespawnTask(this, policy);
            getServer().getScheduler().runTaskTimer(
                    this, task, policy.scanIntervalTicks(), policy.scanIntervalTicks());
            getLogger().info("Block-carrying endermen now use distance-based despawning (scan interval "
                    + policy.scanIntervalTicks() + " ticks).");
        } catch (IllegalArgumentException exception) {
            getLogger().severe("Invalid config.yml: " + exception.getMessage());
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        if (task != null) {
            task.clear();
            task = null;
        }
    }

    private static DespawnPolicy loadPolicy(FileConfiguration config) {
        return new DespawnPolicy(
                number(config, "soft-distance").doubleValue(),
                number(config, "hard-distance").doubleValue(),
                exactInt(config, "minimum-far-ticks"),
                exactInt(config, "random-chance-denominator"),
                exactInt(config, "scan-interval-ticks")
        );
    }

    private static Number number(FileConfiguration config, String key) {
        Object value = config.get(key);
        if (!(value instanceof Number number)) {
            throw new IllegalArgumentException(key + " must be numeric");
        }
        return number;
    }

    private static int exactInt(FileConfiguration config, String key) {
        double numeric = number(config, key).doubleValue();
        if (!Double.isFinite(numeric)
                || numeric < Integer.MIN_VALUE
                || numeric > Integer.MAX_VALUE
                || numeric != Math.rint(numeric)) {
            throw new IllegalArgumentException(key + " must be an integer");
        }
        return (int) numeric;
    }
}
