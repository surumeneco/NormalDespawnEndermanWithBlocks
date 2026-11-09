package dev.surumeneco.normaldespawn;

/** Matches Paper's non-spectator, alive, affects-spawning player selection. */
final class DespawnPlayerEligibility {
    private DespawnPlayerEligibility() {
    }

    static boolean isEligible(
            boolean valid, boolean dead, boolean spectator, boolean affectsSpawning
    ) {
        return valid && !dead && !spectator && affectsSpawning;
    }
}
