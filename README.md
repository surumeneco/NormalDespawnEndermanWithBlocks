# NormalDespawnEndermanWithBlocks

Paper 26.2 plugin that allows endermen carrying a block to despawn under normal hostile-mob-style rules. It does not alter block pickup/placement, combat, teleportation, or vanilla despawning of other mobs.

## Behavior

Unprotected block-carrying endermen despawn immediately when farther than 128 blocks from all active non-spectator players (or if no such player exists), and may despawn after 600 far-distance ticks beyond 32 blocks (per-tick probability 1/800). Peaceful worlds remove eligible carriers. Named, explicitly persistent, vehicle/passenger, and remove-when-far-away-disabled entities are protected.

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

Project design Draft: Google Drive forGPT/XPlayServer/OriginalPlugins/NormalDespawnEndermanWithBlocks/仕様.md.
Java 25 build/test, CAT normative checks, and live-server acceptance are separate completion criteria.
