package com.chaosstick.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;

/** Zombie-shaped body, custom skin. */
public class HorrorRenderer extends ZombieRenderer {
	private final Identifier texture;

	public HorrorRenderer(EntityRendererProvider.Context context, Identifier texture) {
		super(context);
		this.texture = texture;
	}

	@Override
	public Identifier getTextureLocation(ZombieRenderState state) {
		return texture;
	}
}
