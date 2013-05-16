package me.justinp.avena.entity;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.Serializable;

import me.justinp.avena.Game;

public class Player implements Entity, Serializable {

	private static final long serialVersionUID = 1L;
	
	int x, y, health = 10, bsize;
	Inventory i = new Inventory();
	boolean debug = false;
	
	public Player(int x, int y, int bsize, boolean debug) {
		this.x = x;
		this.y = y;
		this.bsize = bsize;
		this.debug = debug;
	}
	
	@Override
	public void move(int x, int y) {
		if(y > Game.height) {
			y = Game.height;
			setHealth(0);
		} else if(y == 0 - bsize) {
			setHealth(0);
		}else {
			this.x = x;
			this.y = y;
			draw();
		}
		System.out.println(y);
	}

	@Override
	public void draw() {
		glDisable(GL_TEXTURE_2D);
		//top
		glBegin(GL_QUADS);
			glVertex2f(x, y);
			glVertex2f(x + bsize, y);
			glVertex2f(x + bsize, y + bsize);
			glVertex2f(x, y + bsize);
		glEnd();
		//botom
		glBegin(GL_QUADS);
			glVertex2f(x, y + bsize);
			glVertex2f(x + bsize, y + bsize);
			glVertex2f(x + bsize, y + bsize * 2);
			glVertex2f(x, y + bsize * 2);
		glEnd();

		glEnable(GL_TEXTURE_2D);
	}

	public int getX() {
		return x;
	}

	public void setX(int x, boolean move) {
		this.x = x;
		if (move) draw();
	}

	public int getY() {
		return y;
	}

	public void setY(int y, boolean move) {
		this.y = y;
		if(move) draw();
	}

	@Override
	public Inventory getInventory() {
		return i;
	}
	
	public void getInventory(Inventory i) {
		this.i = i;
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public void setHealth(int health) {
		this.health = health;
	}

	@Override
	public void hitBy(Entity e, Weapon w) {
		if(!debug) {
			
		}
	}

	@Override
	public void hit(Entity e, Weapon w) {
	
		
	}
	
	public boolean isDead() {
		if(health <= 0)
			return true;
		else
			return false;
	}

}
