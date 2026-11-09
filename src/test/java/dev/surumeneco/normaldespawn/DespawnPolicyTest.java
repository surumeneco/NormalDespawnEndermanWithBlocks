package dev.surumeneco.normaldespawn;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DespawnPolicyTest {
    private static final DespawnPolicy POLICY = new DespawnPolicy(32, 128, 600, 800, 20);

    private static DespawnPolicy.Result evaluate(
            boolean carrying, boolean protectedEntity, boolean peaceful,
            double distance, long previous, double sample
    ) {
        return POLICY.evaluate(carrying, protectedEntity, peaceful,
                distance * distance, previous, sample);
    }

    @Test
    void onlyCarryingAndUnprotectedEndermenAreAffected() {
        assertEquals(DespawnPolicy.Action.KEEP,
                evaluate(false, false, false, 200, 1000, 0).action());
        assertEquals(DespawnPolicy.Action.KEEP,
                evaluate(true, true, false, 200, 1000, 0).action());
    }

    @Test
    void peacefulRemovesUnprotectedCarriers() {
        assertEquals(DespawnPolicy.Action.REMOVE,
                evaluate(true, false, true, 0, 0, 0.99).action());
    }

    @Test
    void hardBoundaryIsStrictlyGreaterAndNoPlayerIsImmediateRemoval() {
        assertEquals(DespawnPolicy.Action.REMOVE,
                evaluate(true, false, false, 128.01, 0, 0.99).action());
        assertEquals(DespawnPolicy.Action.KEEP,
                evaluate(true, false, false, 128, 0, 0.99).action());
        assertEquals(DespawnPolicy.Action.REMOVE,
                POLICY.evaluate(true, false, false, Double.POSITIVE_INFINITY, 0, 0.99).action());
    }

    @Test
    void insideSoftRangeResetsAccumulation() {
        assertEquals(0, evaluate(true, false, false, 32, 700, 0).farTicks());
        assertEquals(0, evaluate(false, false, false, 60, 700, 0).farTicks());
        assertEquals(0, evaluate(true, true, false, 60, 700, 0).farTicks());
    }

    @Test
    void belowMinimumDoesNotRollButAccumulates() {
        DespawnPolicy.Result result = evaluate(true, false, false, 40, 560, 0);
        assertEquals(DespawnPolicy.Action.KEEP, result.action());
        assertEquals(580, result.farTicks());
    }

    @Test
    void firstChanceIsExactlyAtTick600AndOnlyForOneEligibleTick() {
        assertEquals(DespawnPolicy.Action.KEEP,
                evaluate(true, false, false, 40, 580, 0.002).action());
        assertEquals(DespawnPolicy.Action.REMOVE,
                evaluate(true, false, false, 40, 580, 0.001).action());
    }

    @Test
    void chanceIsAggregatedOverTwentyTicksAfterTheThreshold() {
        double p = 1 - Math.pow(799.0 / 800.0, 20);
        assertTrue(p > 0.024 && p < 0.025);
        assertEquals(DespawnPolicy.Action.REMOVE,
                evaluate(true, false, false, 40, 600, p - 1e-5).action());
        assertEquals(DespawnPolicy.Action.KEEP,
                evaluate(true, false, false, 40, 600, p + 1e-5).action());
    }

    @Test
    void denominatorOneAlwaysRemovesOnceEligible() {
        DespawnPolicy immediate = new DespawnPolicy(32, 128, 0, 1, 20);
        assertEquals(DespawnPolicy.Action.REMOVE,
                immediate.evaluate(true, false, false, 40 * 40, 0, 0.999).action());
    }

    @Test
    void invalidConfigurationFailsClosed() {
        assertThrows(IllegalArgumentException.class,
                () -> new DespawnPolicy(33, 32, 600, 800, 20));
        assertThrows(IllegalArgumentException.class,
                () -> new DespawnPolicy(Double.NaN, 128, 600, 800, 20));
        assertThrows(IllegalArgumentException.class,
                () -> new DespawnPolicy(32, 128, -1, 800, 20));
        assertThrows(IllegalArgumentException.class,
                () -> new DespawnPolicy(32, 128, 600, 0, 20));
        assertThrows(IllegalArgumentException.class,
                () -> new DespawnPolicy(32, 128, 600, 800, 0));
    }
}
