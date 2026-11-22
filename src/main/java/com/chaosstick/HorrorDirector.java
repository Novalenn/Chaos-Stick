package com.chaosstick;

import com.chaosstick.entity.HorrorEntity;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;

/** Occasionally spawns one creature near a player at night or in the dark. */
public class HorrorDirector {
	public static void init() {
		ServerTickEvents.END_WORLD_TICK.register(HorrorDirector::tick);
	}

	private static EntityType<?> pick(ServerLevel world) {
		int roll = world.getRandom().nextInt(100);
		if (roll < 20) return ModEntities.HEROBRINE;
		if (roll < 35) return ModEntities.NULL;
		if (roll < 60) return ModEntities.STALKER;
		if (roll < 75) return ModEntities.BOILED_ONE;
		if (roll < 90) return ModEntities.HOLLOW;
		return ModEntities.WEEPER;
	}

	private static void tick(ServerLevel world) {
		if (world.dimension() != Level.OVERWORLD || world.getGameTime() % 100 != 0) {
			return;
		}
		for (ServerPlayer player : world.players()) {
			if (player.isSpectator() || world.getRandom().nextInt(60) != 0) {
				continue;
			}
			long time = world.getDayTime() % 24000L;
			boolean night = time > 13000L && time < 23000L;
			boolean dark = world.getMaxLocalRawBrightness(player.blockPosition()) < 7;
			if (!night && !dark) {
				continue;
			}
			if (!world.getEntitiesOfClass(HorrorEntity.class, player.getBoundingBox().inflate(80.0)).isEmpty()) {
				continue;
			}
			double angle = world.getRandom().nextDouble() * Math.PI * 2.0;
			double dist = 24.0 + world.getRandom().nextDouble() * 12.0;
			int x = (int) Math.floor(player.getX() + Math.cos(angle) * dist);
			int z = (int) Math.floor(player.getZ() + Math.sin(angle) * dist);
			if (!world.hasChunkAt(new BlockPos(x, 64, z))) {
				continue;
			}
			int y = world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
			Entity entity = pick(world).create(world, EntitySpawnReason.EVENT);
			if (entity != null) {
				entity.setPos(x + 0.5, y, z + 0.5);
				world.addFreshEntity(entity);
			}
		}
	}
}
