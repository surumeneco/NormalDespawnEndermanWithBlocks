package dev.surumeneco.normaldespawn;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DespawnPlayerEligibilityTest {
    @Test
    void validSurvivalOrCreativePlayerAffectingSpawnsIsEligible() {
        assertTrue(DespawnPlayerEligibility.isEligible(true, false, false, true));
    }

    @Test
    void nonSpawningPlayerDoesNotPreventVanillaLikeDespawn() {
        assertFalse(DespawnPlayerEligibility.isEligible(true, false, false, false));
    }

    @Test
    void spectatorDeadOrInvalidPlayersAreIgnored() {
        assertFalse(DespawnPlayerEligibility.isEligible(true, false, true, true));
        assertFalse(DespawnPlayerEligibility.isEligible(true, true, false, true));
        assertFalse(DespawnPlayerEligibility.isEligible(false, false, false, true));
    }
}
