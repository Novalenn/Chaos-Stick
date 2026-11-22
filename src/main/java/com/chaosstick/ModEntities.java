package com.chaosstick;

import com.chaosstick.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;

public class ModEntities {
	public static final EntityType<HerobrineEntity> HEROBRINE = reg("herobrine", HerobrineEntity::new, 0.6F, 1.95F);
	public static final EntityType<NullEntity> NULL = reg("null", NullEntity::new, 0.6F, 1.95F);
	public static final EntityType<StalkerEntity> STALKER = reg("stalker", StalkerEntity::new, 0.6F, 1.95F);
	public static final EntityType<BoiledOneEntity> BOILED_ONE = reg("boiled_one", BoiledOneEntity::new, 0.6F, 1.95F);
	public static final EntityType<HollowEntity> HOLLOW = reg("hollow", HollowEntity::new, 0.7F, 2.0F);
	public static final EntityType<WeeperEntity> WEEPER = reg("weeper", WeeperEntity::new, 0.6F, 1.95F);

	private static <T extends Zombie> EntityType<T> reg(String name, EntityType.EntityFactory<T> factory, float width, float height) {
		ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(ChaosStickMod.MOD_ID, name));
		EntityType<T> type = EntityType.Builder.of(factory, MobCategory.MONSTER).sized(width, height).build(key);
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, type);
	}

	private static AttributeSupplier.Builder stats(double health, double speed, double damage, double follow) {
		return Monster.createMonsterAttributes()
			.add(Attributes.MAX_HEALTH, health)
			.add(Attributes.MOVEMENT_SPEED, speed)
			.add(Attributes.ATTACK_DAMAGE, damage)
			.add(Attributes.FOLLOW_RANGE, follow)
			.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0);
	}

	public static void init() {
		FabricDefaultAttributeRegistry.register(HEROBRINE, stats(100, 0.25, 0, 64));
		FabricDefaultAttributeRegistry.register(NULL, stats(60, 0.28, 6, 48));
		FabricDefaultAttributeRegistry.register(STALKER, stats(50, 0.30, 8, 64));
		FabricDefaultAttributeRegistry.register(BOILED_ONE, stats(30, 0.33, 4, 40));
		FabricDefaultAttributeRegistry.register(HOLLOW, stats(80, 0.20, 7, 32));
		FabricDefaultAttributeRegistry.register(WEEPER, stats(20, 0.26, 0, 32));
	}
}
