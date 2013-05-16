package me.justinp.avena;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.justinp.avena.blocks.Block;
import me.justinp.avena.blocks.BlockType;
import me.justinp.avena.entity.Player;
import me.justinp.avena.graphics.GDrawer;
import me.justinp.avena.graphics.GDrawer.GameState;
import me.justinp.avena.helpers.Timer;
import me.justinp.avena.hud.HudDrawer;
import me.justinp.avena.world.World;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Game {

	public static final int width = (Math.round(700 / 32)) * 32;// 700
	public static final int height = (Math.round(500 / 32)) * 32;// 500
	private World theWorld;
	private Player p;
	private HudDrawer hd;
	private Timer timer;
	GDrawer gd;

	public static final boolean beta = true;

	public void start(boolean debug) {

		theWorld = new World(debug);
		p = theWorld.getPlayer();

		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setVSyncEnabled(true);
			Display.setTitle("Avena");
			Display.create();
			Texture heart = TextureLoader.getTexture("PNG",
					ResourceLoader.getResourceAsStream("res/heart.png"));
			hd = new HudDrawer(p, theWorld, heart, debug);
			gd = new GDrawer(theWorld, hd);
		} catch (LWJGLException | IOException e) {
			e.printStackTrace();
		}

		initGL();
		getInfo();
		generateWorld();
		timer = new Timer();

		while (!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			gd.draw();

			if (!Display.isActive())
				gd.switchGameState(GameState.Paused);

			if (theWorld.applyGravity(p))
				System.out.println("true");

			int delta = timer.getDelta();
			Display.setTitle("Avena" + "(" + delta + ")");
			pollKeyboard(debug);

			Display.update();
			Display.sync(60);

		}

		Display.destroy();
	}

	private void generateWorld() {// les than 14

		Block lba = new Block(BlockType.Grass, 0, height - (5 * 32));
		theWorld.addBlock(lba);
		Random r = new Random(Sys.getTime() * System.currentTimeMillis()
				/ System.nanoTime());
		List<Block> blocks = new ArrayList<Block>();
		blocks.add(lba);

		for (int x = theWorld.block_size; x < width; x += theWorld.block_size) {
			int rn = r.nextInt(3);
			if (rn == 0) { // down
				if ((lba.getY() + theWorld.block_size) >= height) {// off the
																	// screen
					Block nb2 = new Block(BlockType.Air, x, lba.getY());
					lba = nb2;
					theWorld.addBlock(lba);
				} else {
					Block nb = new Block(BlockType.Grass, x, lba.getY()
							+ theWorld.block_size);
					lba = nb;
					theWorld.addBlock(lba);
					blocks.add(lba);
				}
			} else if (rn == 1) {// stay
				Block nb = new Block(BlockType.Grass, x, lba.getY());
				lba = nb;
				theWorld.addBlock(lba);
				blocks.add(nb);
			} else if (rn == 2) { // up
				Block nb = new Block(BlockType.Grass, x, lba.getY()
						- theWorld.block_size);
				if (nb.getY() < 64) {// off the screen above 16
					Block nb2 = new Block(BlockType.Air, x, lba.getY());
					lba = nb2;
					theWorld.addBlock(lba);
				} else {
					lba = nb;
					theWorld.addBlock(lba);
					blocks.add(lba);
				}
			} else {
				theWorld.flushBlocks();
				generateWorld();
			}
		}

		if (blocks.size() != 21) {
			theWorld.flushBlocks();
			generateWorld();
		}

		for (Block b : blocks) {
			int blocksBelow = ((height / theWorld.block_size) - (b.getY() / theWorld.block_size)) - 1;
			for (int i = 1; i <= blocksBelow; i++) {
				int curBlockY = height - (i * theWorld.block_size);
				if (curBlockY / 32 == 1 || curBlockY / 32 == 2) {
					theWorld.addBlock(new Block(BlockType.Dirt, b.getX(),
							curBlockY));
				} else {
					int dORs = r.nextInt(2);
					if (dORs == 0) {
						theWorld.addBlock(new Block(BlockType.Dirt, b.getX(),
								curBlockY));
					} else if (dORs == 1) {
						theWorld.addBlock(new Block(BlockType.Stone, b.getX(),
								curBlockY));
					} else if (dORs == 2) {// Texture being worked on, its a bit abnormal
						theWorld.addBlock(new Block(BlockType.Sand, b.getX(),
								curBlockY));
					}
				}
			}
		}

	}

	public static int getTrueMY() {
		return height - Mouse.getY() - 1;
	}

	private void pollKeyboard(boolean debug) {
		if (Mouse.isInsideWindow()) {
			if (Mouse.isButtonDown(0) && isGameState(GameState.Menu))
				gd.switchGameState(GDrawer.GameState.Game);

			if (Mouse.isButtonDown(0) && isGameState(GameState.Game)) {
				int distance = theWorld.getDistanceFromFeet(p,
						theWorld.getBlockOnMouse());
				if (distance <= 3) {
					theWorld.getBlockOnMouse().setBt(BlockType.Air);
					theWorld.applyGravity(p);
				} else {
					// Try try get distance from top block(eyes)
					distance = theWorld.getDistanceFromEyes(p,
							theWorld.getBlockOnMouse());
					if (distance <= 3) {
						theWorld.getBlockOnMouse().setBt(BlockType.Air);
						theWorld.applyGravity(p);
					}
				}
				System.out.println(distance);
			}

			if (Mouse.isButtonDown(1) && isGameState(GameState.Game)) {
				theWorld.getBlockOnMouse().setBt(BlockType.Stone);
			}
		}

		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (debug) {
					if (Keyboard.getEventKey() == Keyboard.KEY_G) {
						theWorld.flushBlocks();
						generateWorld();
						p.setX(0, true);
						p.setY((Game.height - (5 * theWorld.block_size))
								- (theWorld.block_size * 2), true);
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_H) {
						p.setHealth(p.getHealth() - 1);
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_R) {
						glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
						theWorld = new World(true);
						p = theWorld.getPlayer();
						generateWorld();
					}
				}

				if (!p.isDead()) {
					if (Keyboard.getEventKey() == Keyboard.KEY_LEFT
							|| Keyboard.getEventKey() == Keyboard.KEY_A) {
						if ((!(p.getX() - theWorld.block_size < 0))
								&& theWorld.getBlockAtCoordinates(
										p.getX() - theWorld.block_size,
										p.getY()).getBt() == BlockType.Air) {// Player
																				// can
																				// not
																				// go
																				// out
																				// of
																				// map
							p.move(p.getX() - theWorld.block_size, p.getY());
							theWorld.applyGravity(p);
						}
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT
							|| Keyboard.getEventKey() == Keyboard.KEY_D) {
						if ((!(p.getX() + 3 > width - theWorld.block_size))
								&& theWorld.getBlockAtCoordinates(
										p.getX() + theWorld.block_size,
										p.getY()).getBt() == BlockType.Air) { // Player
																				// can
																				// not
																				// go
																				// out
																				// of
																				// map
							p.move(p.getX() + theWorld.block_size, p.getY());
							theWorld.applyGravity(p);
						}
					}
				}
			}
		}
	}

	private void initGL() {
		glEnable(GL_TEXTURE_2D);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, height, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glDisable(GL_DEPTH_TEST);
	}

	public void getInfo() {
		System.out.println("W: " + width + " H: " + height);
		System.out.println("MX: " + Mouse.getX() + " MY: " + getTrueMY());
	}

	public static float getVersion() {
		return 0.1f;
	}

	public boolean isGameState(GameState gs) {
		if (gs == gd.getGameState())
			return true;
		else
			return false;
	}

	public static void main(String[] args) {
		if (args.length != 0 && args[0].equalsIgnoreCase("debug"))
			new Game().start(true);
		else
			new Game().start(false);
	}
}
