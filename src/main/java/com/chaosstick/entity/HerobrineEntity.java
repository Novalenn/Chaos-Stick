package com.chaosstick.entity;

import com.chaosstick.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/** Stands and stares. Vanishes if you look at him for too long, or get too close. */
public class HerobrineEntity extends HorrorEntity {
	private int stareTicks = 0;

	public HerobrineEntity(EntityType<? extends Zombie> type, Level level) {
		super(type, level);
	}

	@Override
	protected void addHorrorGoals() {
		this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 64.0F));
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level().isClientSide()) {
			return;
		}
		Player player = this.level().getNearestPlayer(this, 64.0);
		if (player == null) {
			return;
		}
		boolean seen = isLookedAt(player, this);
		if (seen) {
			stareTicks++;
		} else if (stareTicks > 0) {
			stareTicks--;
		}
		if (stareTicks > 70 || this.distanceTo(player) < 6.0F) {
			this.playSound(ModSounds.HEROBRINE_VANISH, 2.0F, 1.0F);
			this.discard();
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSounds.HEROBRINE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSounds.HEROBRINE_VANISH;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSounds.HEROBRINE_VANISH;
	}
}
