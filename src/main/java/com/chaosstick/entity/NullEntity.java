package com.chaosstick.entity;

import com.chaosstick.ModSounds;
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

/** A glitch in the world. Teleports erratically and warps your vision when close. */
public class NullEntity extends HorrorEntity {
	public NullEntity(EntityType<? extends Zombie> type, Level level) {
		super(type, level);
	}

	@Override
	protected void addHorrorGoals() {
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.1, false));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 32.0F));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level().isClientSide()) {
			return;
		}
		LivingEntity target = this.getTarget();
		if (target == null) {
			return;
		}
		if (this.tickCount % 30 == 0 && this.random.nextInt(3) == 0 && this.distanceTo(target) > 3.0F) {
			double x = target.getX() + (this.random.nextDouble() - 0.5) * 10.0;
			double z = target.getZ() + (this.random.nextDouble() - 0.5) * 10.0;
			this.randomTeleport(x, target.getY(), z, true);
		}
		if (this.tickCount % 40 == 0 && this.distanceToSqr(target) < 9.0) {
			target.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 200, 0));
			target.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 100, 0));
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSounds.NULL_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSounds.NULL_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSounds.NULL_DEATH;
	}
}
