package me.justinp.avena.InventoryEntities.Weapons;

import org.newdawn.slick.opengl.Texture;

import me.justinp.avena.blocks.Block;
import me.justinp.avena.entity.Entity;
import me.justinp.avena.entity.Player;
import me.justinp.avena.entity.Weapon;

public class BronzeAxe extends Weapon {

	@Override
	public int damageModifier() {
		return 0;
	}

	@Override
	public int weaponHealth() {
		return 0;
	}

	@Override
	public float getID() {
		return 0;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public int getSlotNum() {
		return 0;
	}

	@Override
	public Texture getTexture() {
		return null;
	}

	@Override
	public void onUse(Player p, Entity e) {
		
	}

	@Override
	public void onUser(Player p, Block b) {
		
	}

}
