package dev.surumeneco.normaldespawn;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

/**
 * Supplemental, handwritten verification of all eight witnesses in the
 * confirmed CAT decision model. This does not impersonate generated CAT
 * Queue tests or prove Paper runtime integration.
 */
class ConfirmedDecisionWitnessTest {
    private static final DespawnPolicy POLICY =
            new DespawnPolicy(32, 128, 600, 800, 20);

    record Witness(
            String rule, boolean carrying, boolean protectedEntity, boolean peaceful,
            double distanceSquared, long farTicks, double randomSample,
            DespawnPolicy.Action action, long resultingFarTicks
    ) { }

    @TestFactory
    Stream<DynamicTest> confirmedDecisionBranches() {
        final double noPlayer = Double.POSITIVE_INFINITY;
        final double middle = 40.0 * 40.0;
        return Stream.of(
                new Witness("non-carrier", false, false, false, noPlayer, 700, 0.0,
                        DespawnPolicy.Action.KEEP, 0),
                new Witness("protected", true, true, false, noPlayer, 700, 0.0,
                        DespawnPolicy.Action.KEEP, 0),
                new Witness("peaceful", true, false, true, noPlayer, 700, 0.999,
                        DespawnPolicy.Action.REMOVE, 0),
                new Witness("no-player", true, false, false, noPlayer, 700, 0.0,
                        DespawnPolicy.Action.KEEP, 0),
                new Witness("beyond-hard", true, false, false, 129.0 * 129.0, 0, 0.999,
                        DespawnPolicy.Action.REMOVE, 0),
                new Witness("within-soft", true, false, false, 16.0 * 16.0, 700, 0.0,
                        DespawnPolicy.Action.KEEP, 0),
                new Witness("middle-success", true, false, false, middle, 600, 0.0,
                        DespawnPolicy.Action.REMOVE, 0),
                new Witness("middle-failure", true, false, false, middle, 600, 0.999,
                        DespawnPolicy.Action.KEEP, 620)
        ).map(w -> dynamicTest(w.rule, () -> {
            var actual = POLICY.evaluate(
                    w.carrying, w.protectedEntity, w.peaceful, w.distanceSquared,
                    w.farTicks, w.randomSample);
            assertEquals(w.action, actual.action(), w.rule);
            assertEquals(w.resultingFarTicks, actual.farTicks(), w.rule);
        }));
    }
}
