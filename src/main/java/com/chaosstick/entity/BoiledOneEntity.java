package com.chaosstick.entity;

import com.chaosstick.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/** Fast, screaming, steaming. Sickens whatever it gets close to. */
public class BoiledOneEntity extends HorrorEntity {
	public BoiledOneEntity(EntityType<? extends Zombie> type, Level level) {
		super(type, level);
	}

	@Override
	protected void addHorrorGoals() {
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, false));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.9));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 24.0F));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level() instanceof ServerLevel serverLevel) {
			if (this.tickCount % 4 == 0) {
				serverLevel.sendParticles(ParticleTypes.CLOUD, this.getX(), this.getY() + 1.2, this.getZ(), 2, 0.3, 0.5, 0.3, 0.01);
			}
			LivingEntity target = this.getTarget();
			if (target != null && this.tickCount % 30 == 0 && this.distanceToSqr(target) < 9.0) {
				target.addEffect(new MobEffectInstance(MobEffects.HUNGER, 100, 1));
				target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 0));
			}
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSounds.BOILED_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSounds.BOILED_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSounds.BOILED_DEATH;
	}
}
