package com.chaosstick.entity;

import com.chaosstick.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/** A small crying figure that follows you around and never attacks. Hurt it and it screams. */
public class WeeperEntity extends HorrorEntity {
	public WeeperEntity(EntityType<? extends Zombie> type, Level level) {
		super(type, level);
		this.setBaby(true);
	}

	@Override
	protected void addHorrorGoals() {
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 32.0F));
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level().isClientSide()) {
			return;
		}
		Player player = this.level().getNearestPlayer(this, 32.0);
		if (player != null) {
			if (this.distanceTo(player) > 5.0F) {
				this.getNavigation().moveTo(player, 1.0);
			} else {
				this.getNavigation().stop();
			}
		}
		if (this.hurtTime > 0 && this.tickCount % 5 == 0) {
			for (Player nearby : this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(8.0))) {
				nearby.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0));
			}
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSounds.WEEPER_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSounds.WEEPER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSounds.WEEPER_DEATH;
	}
}
