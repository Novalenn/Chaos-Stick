package com.chaosstick.entity;

import com.chaosstick.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/** Slow, tough, and hates light. Snuffs out every torch and lantern near it. */
public class HollowEntity extends HorrorEntity {
	public HollowEntity(EntityType<? extends Zombie> type, Level level) {
		super(type, level);
	}

	@Override
	protected void addHorrorGoals() {
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, false));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.7));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 24.0F));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level().isClientSide() || this.tickCount % 20 != 0) {
			return;
		}
		BlockPos center = this.blockPosition();
		for (int dx = -5; dx <= 5; dx++) {
			for (int dy = -3; dy <= 3; dy++) {
				for (int dz = -5; dz <= 5; dz++) {
					BlockPos pos = center.offset(dx, dy, dz);
					BlockState state = this.level().getBlockState(pos);
					if (state.is(Blocks.TORCH) || state.is(Blocks.WALL_TORCH)
						|| state.is(Blocks.SOUL_TORCH) || state.is(Blocks.SOUL_WALL_TORCH)
						|| state.is(Blocks.REDSTONE_TORCH) || state.is(Blocks.REDSTONE_WALL_TORCH)
						|| state.is(Blocks.LANTERN) || state.is(Blocks.SOUL_LANTERN)) {
						this.level().destroyBlock(pos, false);
					}
				}
			}
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSounds.HOLLOW_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSounds.HOLLOW_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSounds.HOLLOW_DEATH;
	}
}
