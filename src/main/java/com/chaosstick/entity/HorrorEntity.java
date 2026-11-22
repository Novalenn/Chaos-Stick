package com.chaosstick.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/** Base class: a zombie-bodied creature with no daylight burning and no vanilla zombie goals. */
public abstract class HorrorEntity extends Zombie {
	protected HorrorEntity(EntityType<? extends Zombie> type, Level level) {
		super(type, level);
	}

	@Override
	protected boolean isSunSensitive() {
		return false;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		addHorrorGoals();
	}

	protected void addHorrorGoals() {
	}

	/** True if the player is looking roughly at this entity with a clear line of sight. */
	public static boolean isLookedAt(Player player, Entity entity) {
		Vec3 view = player.getViewVector(1.0F).normalize();
		Vec3 to = new Vec3(entity.getX() - player.getX(), entity.getEyeY() - player.getEyeY(), entity.getZ() - player.getZ());
		double dist = to.length();
		if (dist < 0.001) {
			return true;
		}
		to = to.scale(1.0 / dist);
		return view.dot(to) > 0.96 && player.hasLineOfSight(entity);
	}
}
