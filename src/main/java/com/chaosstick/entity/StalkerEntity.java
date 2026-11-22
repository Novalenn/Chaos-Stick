package com.chaosstick.entity;

import com.chaosstick.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/** Follows you from a distance. Freezes when you look at it. Look away too long and it comes for you. */
public class StalkerEntity extends HorrorEntity {
	private int unseenTicks = 0;

	public StalkerEntity(EntityType<? extends Zombie> type, Level level) {
		super(type, level);
	}

	@Override
	protected void addHorrorGoals() {
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.3, false));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 48.0F));
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level().isClientSide()) {
			return;
		}
		Player player = this.level().getNearestPlayer(this, 64.0);
		if (player == null) {
			this.setTarget(null);
			unseenTicks = 0;
			return;
		}
		boolean seen = isLookedAt(player, this);
		float dist = this.distanceTo(player);
		if (seen) {
			unseenTicks = 0;
			this.setTarget(null);
			this.getNavigation().stop();
		} else {
			unseenTicks++;
			if (dist > 16.0F) {
				this.getNavigation().moveTo(player, 1.0);
			} else if (unseenTicks > 120) {
				this.setTarget(player);
			} else {
				this.getNavigation().stop();
			}
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSounds.STALKER_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSounds.STALKER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSounds.STALKER_DEATH;
	}
}
