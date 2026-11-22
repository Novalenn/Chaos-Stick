package com.chaosstick.client;

import com.chaosstick.ChaosStickMod;
import com.chaosstick.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.resources.Identifier;

public class ChaosStickClient implements ClientModInitializer {
	private static Identifier tex(String name) {
		return Identifier.fromNamespaceAndPath(ChaosStickMod.MOD_ID, "textures/entity/" + name + ".png");
	}

	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(ModEntities.HEROBRINE, ctx -> new HorrorRenderer(ctx, tex("herobrine")));
		EntityRendererRegistry.register(ModEntities.NULL, ctx -> new HorrorRenderer(ctx, tex("null")));
		EntityRendererRegistry.register(ModEntities.STALKER, ctx -> new HorrorRenderer(ctx, tex("stalker")));
		EntityRendererRegistry.register(ModEntities.BOILED_ONE, ctx -> new HorrorRenderer(ctx, tex("boiled_one")));
		EntityRendererRegistry.register(ModEntities.HOLLOW, ctx -> new HorrorRenderer(ctx, tex("hollow")));
		EntityRendererRegistry.register(ModEntities.WEEPER, ctx -> new HorrorRenderer(ctx, tex("weeper")));
	}
}
