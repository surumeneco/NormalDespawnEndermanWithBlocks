package dev.surumeneco.normaldespawn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Synchronous low-frequency scan of already-loaded endermen.
 * No chunk loads, no background Bukkit calls, no persistent state.
 */
final class EndermanDespawnTask implements Runnable {
    private final JavaPlugin plugin;
    private final DespawnPolicy policy;
    private final Map<UUID, Long> farTicks = new HashMap<>();

    EndermanDespawnTask(JavaPlugin plugin, DespawnPolicy policy) {
        this.plugin = plugin;
        this.policy = policy;
    }

    @Override
    public void run() {
        Set<UUID> seen = new HashSet<>();
        for (World world : plugin.getServer().getWorlds()) {
            List<Location> playerPositions = activePlayerPositions(world);
            boolean peaceful = world.getDifficulty() == Difficulty.PEACEFUL;
            for (Enderman enderman : world.getEntitiesByClass(Enderman.class)) {
                UUID id = enderman.getUniqueId();
                if (!enderman.isValid() || enderman.isDead() || enderman.getCarriedBlock() == null) {
                    farTicks.remove(id);
                    continue;
                }
                if (isProtected(enderman)) {
                    farTicks.remove(id);
                    continue;
                }

                seen.add(id);
                double nearest = nearestDistanceSquared(enderman.getLocation(), playerPositions);
                long previous = farTicks.getOrDefault(id, 0L);
                DespawnPolicy.Result result = policy.evaluate(
                        true, false, peaceful, nearest, previous,
                        ThreadLocalRandom.current().nextDouble());
                if (result.action() == DespawnPolicy.Action.REMOVE) {
                    enderman.remove();
                    farTicks.remove(id);
                } else if (result.farTicks() == 0L) {
                    farTicks.remove(id);
                } else {
                    farTicks.put(id, result.farTicks());
                }
            }
        }
        farTicks.keySet().retainAll(seen);
    }

    private static boolean isProtected(Enderman enderman) {
        return !enderman.getRemoveWhenFarAway()
                || enderman.getCustomName() != null
                || enderman.isInsideVehicle()
                || !enderman.getPassengers().isEmpty();
    }

    private static List<Location> activePlayerPositions(World world) {
        List<Location> positions = new ArrayList<>();
        for (Player player : world.getPlayers()) {
            if (player.isValid() && !player.isDead() && player.getGameMode() != GameMode.SPECTATOR) {
                positions.add(player.getLocation());
            }
        }
        return positions;
    }

    private static double nearestDistanceSquared(Location location, List<Location> players) {
        double nearest = Double.POSITIVE_INFINITY;
        for (Location player : players) {
            double dx = location.getX() - player.getX();
            double dy = location.getY() - player.getY();
            double dz = location.getZ() - player.getZ();
            double squared = dx * dx + dy * dy + dz * dz;
            if (squared < nearest) {
                nearest = squared;
            }
        }
        return nearest;
    }

    void clear() {
        farTicks.clear();
    }
}
