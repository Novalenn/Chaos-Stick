package com.chaosstick;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class ModSounds {
	public static final SoundEvent HEROBRINE_AMBIENT = reg("herobrine.ambient");
	public static final SoundEvent HEROBRINE_VANISH = reg("herobrine.vanish");
	public static final SoundEvent NULL_AMBIENT = reg("null.ambient");
	public static final SoundEvent NULL_HURT = reg("null.hurt");
	public static final SoundEvent NULL_DEATH = reg("null.death");
	public static final SoundEvent STALKER_AMBIENT = reg("stalker.ambient");
	public static final SoundEvent STALKER_HURT = reg("stalker.hurt");
	public static final SoundEvent STALKER_DEATH = reg("stalker.death");
	public static final SoundEvent BOILED_AMBIENT = reg("boiled.ambient");
	public static final SoundEvent BOILED_HURT = reg("boiled.hurt");
	public static final SoundEvent BOILED_DEATH = reg("boiled.death");
	public static final SoundEvent HOLLOW_AMBIENT = reg("hollow.ambient");
	public static final SoundEvent HOLLOW_HURT = reg("hollow.hurt");
	public static final SoundEvent HOLLOW_DEATH = reg("hollow.death");
	public static final SoundEvent WEEPER_AMBIENT = reg("weeper.ambient");
	public static final SoundEvent WEEPER_HURT = reg("weeper.hurt");
	public static final SoundEvent WEEPER_DEATH = reg("weeper.death");

	private static SoundEvent reg(String name) {
		Identifier id = Identifier.fromNamespaceAndPath(ChaosStickMod.MOD_ID, name);
		return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
	}

	public static void init() {
	}
}
