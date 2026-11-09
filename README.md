# NormalDespawnEndermanWithBlocks

Paper 26.2 plugin that allows endermen carrying a block to despawn under normal hostile-mob-style rules. It does not alter block pickup/placement, combat, teleportation, or vanilla despawning of other mobs.

## Behavior

Unprotected block-carrying endermen despawn immediately when farther than 128 blocks from all active non-spectator players (only when at least one eligible player exists; empty or spectator-only worlds do not trigger distance despawn), and may despawn after 600 far-distance ticks beyond 32 blocks (per-tick probability 1/800). Peaceful worlds remove eligible carriers. Named, vehicle/passenger, and remove-when-far-away-disabled entities are protected. Bukkit Entity.isPersistent() is a save-to-disk flag and is not used for despawn protection.

This is an API-level approximation of vanilla despawn behavior: every 20 ticks it samples distances and aggregates the intervening probability. Timing within a scan interval can differ from vanilla. Only already-loaded entities are inspected.

## Requirements / build

- Minecraft/Paper 26.2 (server-side only), Java 25.
- Gradle 9.2.1 and Java 25: run gradle clean test jar.
- Resulting JAR: build/libs/NormalDespawnEndermanWithBlocks-1.0.0.jar.
- A Java 25 GitHub Actions build runs on the feature and develop branches.

## Install / migration

1. **Remove** the old normal-enderman-despawn-1.0.0.jar from the server's plugins folder. Do not run both.
2. Install the newly built JAR.
3. Start the server, then review plugins/NormalDespawnEndermanWithBlocks/config.yml.
4. Match distances to Paper's monster despawn ranges if Paper settings have changed (the current server defaults to 32/128).
5. Validate behavior and MSPT on a test server before production release.

The plugin does not change the mob cap or prevent endermen from picking up blocks. Blocks are not dropped on despawn.

## Design source

Project design source: [Google Drive specification](https://drive.google.com/file/d/1CISIkE1HL_k_zpsgDf3KEEJfM-soT7wl/view).
The finite natural-despawn decision semantics are **Confirmed** under the user's explicit 2026-11-09 approval, recorded in [the decision provenance](docs/cat/decisions/2026-11-09-user-confirmed-authorization.md) and [confirmed CAT artifacts](docs/cat/confirmed/).

CAT Process Closure and Model Conformance passed without draft overrides. **Implementation Conformance remains inconclusive** pending Java-specific obligations and formal execution evidence. Java 25 build/test and live-server acceptance are separate completion criteria; the branch is not a production release.
