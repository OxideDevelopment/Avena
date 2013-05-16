package me.justinp.avena.blocks;

import static org.lwjgl.opengl.GL11.*;

import java.io.Serializable;

import me.justinp.avena.world.World;

import org.newdawn.slick.opengl.Texture;

public class Block implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private BlockType bt;
	private int x;
	private int y;
	
	public Block(BlockType type, int x, int y) {
		this.bt = type;
		this.x = x;
		this.y = y;
	}

	public void draw(World World) {
		
		Texture t = BlockType.getTextureByType(bt);
		t.bind();
		glPushMatrix();
		glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex2f(x, y);
			glTexCoord2f(1, 0);
			glVertex2f(x + World.block_width , y);
			glTexCoord2f(1, 1);
			glVertex2f(x + World.block_width, y + World.block_height);
			glTexCoord2f(0, 1);
			glVertex2f(x, y + World.block_height);
		glEnd();
		glPopMatrix();
	}
	
	public BlockType getBt() {
		return bt;
	}

	public void setBt(BlockType bt) {
		this.bt = bt;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
}
