package com.chaosstick;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.core.Registry;

import java.util.List;

public class ChaosStickMod implements ModInitializer {
	public static final String MOD_ID = "chaosstick";

	private static final List<Holder<MobEffect>> EFFECTS = List.of(
		MobEffects.SPEED, MobEffects.JUMP_BOOST, MobEffects.LEVITATION,
		MobEffects.SLOW_FALLING, MobEffects.NIGHT_VISION, MobEffects.BLINDNESS,
		MobEffects.NAUSEA, MobEffects.REGENERATION, MobEffects.STRENGTH,
		MobEffects.GLOWING, MobEffects.INVISIBILITY, MobEffects.SLOWNESS,
		MobEffects.HASTE, MobEffects.WEAKNESS
	);

	public static Item CHAOS_STICK;

	@Override
	public void onInitialize() {
		ModSounds.init();
		ModEntities.init();
		HorrorDirector.init();
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MOD_ID, "chaos_stick"));
		CHAOS_STICK = Registry.register(BuiltInRegistries.ITEM, key,
			new ChaosStickItem(new Item.Properties().setId(key).stacksTo(1)));

		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
			.register(entries -> entries.accept(CHAOS_STICK));
	}

	public static class ChaosStickItem extends Item {
		public ChaosStickItem(Properties properties) {
			super(properties);
		}

		@Override
		public InteractionResult use(Level level, Player player, InteractionHand hand) {
			if (!level.isClientSide()) {
				Holder<MobEffect> effect = EFFECTS.get(level.getRandom().nextInt(EFFECTS.size()));
				player.addEffect(new MobEffectInstance(effect, 200, 1));
				player.getCooldowns().addCooldown(player.getItemInHand(hand), 40);
				player.displayClientMessage(Component.translatable(effect.value().getDescriptionId()), true);
			}
			return InteractionResult.SUCCESS;
		}
	}
}
