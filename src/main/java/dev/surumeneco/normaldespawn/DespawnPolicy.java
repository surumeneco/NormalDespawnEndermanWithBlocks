package dev.surumeneco.normaldespawn;

/**
 * Pure, deterministic portion of the block-carrying enderman despawn rule.
 * The adapter is responsible for selecting eligible entities and distance.
 */
public record DespawnPolicy(
        double softDistance,
        double hardDistance,
        int minimumFarTicks,
        int randomChanceDenominator,
        int scanIntervalTicks
) {
    public DespawnPolicy {
        if (!Double.isFinite(softDistance) || softDistance < 0) {
            throw new IllegalArgumentException("soft-distance must be finite and >= 0");
        }
        if (!Double.isFinite(hardDistance) || hardDistance <= softDistance) {
            throw new IllegalArgumentException("hard-distance must be finite and > soft-distance");
        }
        if (minimumFarTicks < 0) {
            throw new IllegalArgumentException("minimum-far-ticks must be >= 0");
        }
        if (randomChanceDenominator < 1) {
            throw new IllegalArgumentException("random-chance-denominator must be >= 1");
        }
        if (scanIntervalTicks < 1 || scanIntervalTicks > 1200) {
            throw new IllegalArgumentException("scan-interval-ticks must be between 1 and 1200");
        }
    }

    public enum Action {
        KEEP,
        REMOVE
    }

    public record Result(Action action, long farTicks) {
    }

    /**
     * @param protectedEntity true for a named, explicitly persistent, or ridden entity
     * @param nearestDistanceSquared nearest alive non-spectator player; infinity if none
     * @param previousFarTicks accumulated far-distance ticks across scans
     * @param randomSample a uniform sample in [0, 1)
     */
    public Result evaluate(
            boolean carriesBlock,
            boolean protectedEntity,
            boolean peaceful,
            double nearestDistanceSquared,
            long previousFarTicks,
            double randomSample
    ) {
        if (!carriesBlock || protectedEntity) {
            return new Result(Action.KEEP, 0);
        }
        if (peaceful) {
            return new Result(Action.REMOVE, 0);
        }
        // Without a nearby eligible player, vanilla distance despawning does not run.
        if (!Double.isFinite(nearestDistanceSquared)) {
            return new Result(Action.KEEP, 0);
        }

        if (nearestDistanceSquared > hardDistance * hardDistance) {
            return new Result(Action.REMOVE, 0);
        }
        if (nearestDistanceSquared <= softDistance * softDistance) {
            return new Result(Action.KEEP, 0);
        }

        long previous = Math.max(0L, previousFarTicks);
        long after = previous > Long.MAX_VALUE - scanIntervalTicks
                ? Long.MAX_VALUE : previous + scanIntervalTicks;
        long eligibleStartExclusive = Math.max(previous, (long) minimumFarTicks - 1L);
        long eligibleTicks = Math.max(0L, after - eligibleStartExclusive);

        if (eligibleTicks > 0) {
            double probability = 1.0 - Math.pow(
                    1.0 - (1.0 / randomChanceDenominator), eligibleTicks);
            if (randomSample < probability) {
                return new Result(Action.REMOVE, 0);
            }
        }
        return new Result(Action.KEEP, after);
    }
}
