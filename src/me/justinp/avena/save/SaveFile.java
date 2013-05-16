package me.justinp.avena.save;

import java.io.Serializable;

import me.justinp.avena.world.World;

public class SaveFile implements Serializable{

	private static final long serialVersionUID = 1L;
	private World theWorld;
	
	public SaveFile(World currentWorld) {
		theWorld = currentWorld;
	}
	
	public World getWorld() {
		return theWorld;
	}
	
}
