package me.justinp.avena.entity;

import me.justinp.avena.blocks.Block;

import org.newdawn.slick.opengl.Texture;

public abstract class InventoryEntity {

	public abstract float getID();
	public abstract String getName();
	public abstract int getSlotNum();
	public abstract Texture getTexture();
	public abstract void onUse(Player p, Entity e);
	public abstract void onUser(Player p, Block b);
	
	
}
