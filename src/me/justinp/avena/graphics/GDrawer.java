package me.justinp.avena.graphics;

import java.io.IOException;

import me.justinp.avena.Game;
import me.justinp.avena.hud.HudDrawer;
import me.justinp.avena.world.World;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import static org.lwjgl.opengl.GL11.*;

public class GDrawer {

	public static enum GameState{
		Menu, Game, Paused;
	}
	
	private GameState gameState;
	private Texture bg;
	private Texture sBtn;
	
	private World world;
	private HudDrawer hud;
	
	public GDrawer(World world, HudDrawer hud) {
		gameState = GameState.Menu;
		this.world = world;
		this.hud = hud;
		
		try {
			bg = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/background.png"));
			sBtn = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/start.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void switchGameState(GameState newState){
		gameState = newState;
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public static void drawTexture(float x, float y, float width, float height, Texture texture) {
		glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex2f(x, y);
			
			glTexCoord2f(1, 0);
			glVertex2f(x + width, y);
			
			glTexCoord2f(1, 1);
			glVertex2f(x + width, y + height);
			
			glTexCoord2f(0, 1);
			glVertex2f(x, y + height);
		glEnd();
	}
	
	public void draw() {
		switch (gameState) {
		
		case Menu:
			drawTexture(0, 0, Game.width, Game.height, bg);
			drawTexture(200, 300 ,sBtn.getImageWidth(), sBtn.getImageHeight(), sBtn);
			break;
			
			
		//Game
		case Game:
			world.getPlayer().draw();
			world.drawAllBlocks();
			world.getPlayer().draw();
			hud.drawHUD();
			break;
			
			
			
		case Paused:
			break;
		}
	}
}
