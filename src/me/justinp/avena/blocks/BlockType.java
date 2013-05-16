package me.justinp.avena.blocks;

import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public enum BlockType {
	
	Grass, Dirt, Stone, Sand, Air;
	
	static Texture grass;
	static Texture dirt;
	static Texture stone;
	static Texture air;
	static Texture sand;
	
	static boolean areSet = false;
	
	public static Texture getTextureByType(BlockType bt){
		if(areSet) {
			if(bt == Grass) return grass;
			if(bt == Stone) return stone;
			if(bt == Dirt) return dirt;
			if(bt == Air) return air;
			if(bt == Sand) return sand;
		}else {
			try {
				grass = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/grass.png"));
				dirt = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/dirt.png"));
				stone = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/stone.png"));
				air = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/air.png"));
				sand = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/sand.png"));
			}catch(IOException e) {
				e.printStackTrace();
				Display.destroy();
				System.exit(1);
			}
			areSet = true;
		}
		
		return getTextureByType(bt);
	}
}
