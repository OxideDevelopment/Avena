package me.justinp.avena.world;

import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.justinp.avena.Game;
import me.justinp.avena.blocks.Block;
import me.justinp.avena.blocks.BlockType;
import me.justinp.avena.entity.Entity;
import me.justinp.avena.entity.Player;

import org.lwjgl.input.Mouse;

public class World implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public final int block_size = 32;
	public final int block_width = 32;
	public final int block_height = 32;

	private List<Block> alBlocks = new ArrayList<Block>();
	private HashMap<Block, Rectangle> btr = new HashMap<Block, Rectangle>();
	private Player thePlayer;
	
	private World theWorld = this;
	public List<Block> getAllBlocks(){
		return alBlocks;
	}
	
	public World(boolean debug) {
		for(int x = 0; x < block_width; x++) {
			for(int y = 0; y < block_height; y++) {
				Block newBlock = new Block(BlockType.Air, x * block_size, y * block_size);
				alBlocks.add(newBlock);
			}
		}
		
		thePlayer = new Player(0, (Game.height - (5 * block_size)) - (block_size * 2), block_size, debug);
		
	}
	
	public void addBlock(Block b) {
		Block b2 = getBlockAtCoordinates(b.getX(), b.getY());
		if(b2.getBt() == BlockType.Air) {
			alBlocks.remove(b2);
			btr.remove(b2);
		}
		/* The block of code above removes the block of air
		 * so that the getBlockAtCordninates method works..
		 * This should always work because the block of air
		 * was added before this block.
		 */
		
		alBlocks.add(b);
		Rectangle space = new Rectangle(b.getX(), b.getY(), block_size, block_size);
		btr.put(b, space);
	}
	
	public void removeBlock(Block b) {
		alBlocks.remove(b);
	}
	
	public Block getBlockAtCoordinates(int x, int y) {
		for(Block b: alBlocks) {
			if(b.getX() == x && b.getY() == y) {
				return b;
			}
		}
		return null;
	}
	
	public void drawAllBlocks() {
		for(Block b: alBlocks) {
			b.draw(this);
		}
	}
	

	public void flushBlocks() { //Set all blocks to air
		alBlocks = new ArrayList<Block>();
		for(int x = 0; x < block_width; x++) {
			for(int y = 0; y < block_height; y++) {
				Block newBlock = new Block(BlockType.Air, x * block_size, y * block_size);
				alBlocks.add(newBlock);
			}
		}
	}
	
	public Player getPlayer() {
		return thePlayer;
	}
	
	public boolean blockExists(int x, int y) {
		for(Block b: alBlocks) {
			if(b.getX() == x && b.getY() == y) {
				return true;
			}
		}
		return false;
	}
	
	public boolean blockExists(Block b) {
		for(Block block: alBlocks) {
			if(block.getX() == b.getX() && block.getY() == b.getY()) {
				return true;
			}
		}
		return false;
	}

	public Block getBlockOnMouse() {
		int mx = Mouse.getX();
		int my = Game.getTrueMY();
		
		int x = (mx / theWorld.block_size) * theWorld.block_size;
		int y = (my / theWorld.block_size) * theWorld.block_size;
		
		Block onMouse = theWorld.getBlockAtCoordinates(x, y);
		return onMouse;
	}
	
	/**
	 * Gets the distance between an Entity and Block
	 * 
	 * @param e The entity(Player, Mob, etc.)
	 * @param b The block(Grass, dirt, etc.)
	 * @return The distance between the block and entity in blocks.
	 */
	
	public int getDistanceFromFeet(Entity e, Block b) {
		int beX = e.getX() / block_size;
		int beY = (e.getY() + block_size) / block_size;
		
		int bbX = b.getX() / block_size;
		int bbY = b.getY() / block_size;
		
		int tX = beX > bbX ? beX - bbX : bbX - beX;
		int tY = beY > bbY ? beY - bbY : bbY - beY;
		
		return tX + tY;
		
	}
	
	public int getDistanceFromEyes(Entity e, Block b) {
		int beX = e.getX() / block_size;
		int beY = e.getY() / block_size;
		
		int bbX = b.getX() / block_size;
		int bbY = b.getY() / block_size;
		
		int tX = beX > bbX ? beX - bbX : bbX - beX;
		int tY = beY > bbY ? beY - bbY : bbY - beY;
		
		return tX + tY;
		
	}
	
	public void checkCollisions(Entity e) {
		int x = e.getX();
		int y = e.getY();
		
		if(blockExists(x, y)){
		}
	}
	
	public boolean applyGravity(Entity e) {
		int x = e.getX();
		int y = e.getY();
		
		//Move character up
		Block tb = getBlockAtCoordinates(x, y + block_size);
		Block atb = getBlockAtCoordinates(x, y);
		if(tb.getBt() != BlockType.Air && atb.getBt() == BlockType.Air) {
			e.move(x, y - block_size);
			return true;
		}
		
		//Move Character down
		Block below = getBlockAtCoordinates(x, y + (block_size * 2));
		if(below.getBt() == BlockType.Air) {
			e.move(x, y + block_size);
			return true;
		}
		
		return false;
		
	}
}
