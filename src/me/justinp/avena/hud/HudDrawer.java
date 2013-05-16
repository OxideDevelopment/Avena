package me.justinp.avena.hud;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRecti;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;

import me.justinp.avena.Game;
import me.justinp.avena.entity.Player;
import me.justinp.avena.world.World;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.opengl.Texture;

public class HudDrawer {

	private Player player;
	private World world;
	private Texture heart;
	private boolean debug;

	private UnicodeFont uf = new UnicodeFont(
			new Font("Segoe UI", Font.BOLD, 16));
	private DecimalFormat df = new DecimalFormat("#.##");

	public HudDrawer(Player player, World world, Texture heart, boolean debug) {
		this.player = player;
		this.world = world;
		this.heart = heart;
		this.debug = debug;

		loadFont();
	}

	@SuppressWarnings("unchecked")
	private void loadFont() {
		try {
			uf.getEffects().add(new ColorEffect(Color.white));
			uf.addAsciiGlyphs();
			uf.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void drawHUD() {
		drawHealth();
		renderText(debug);
	}

	public void setFont(Font font) {
		uf = new UnicodeFont(font);
		loadFont();
	}

	private void renderText(boolean dbg) {
		if (player.isDead()) {
			glColor4f(1f, 0f, 0f, 0.5f);
			glRecti(0, 0, Game.width, Game.height);
			uf.drawString(Game.width / 2 - 25, 0f, "DEAD",
					org.newdawn.slick.Color.red);
		} else {
			if (debug) {
				uf.drawString(5f, 0f,
						"Avena Version " + df.format(Game.getVersion())
								+ (Game.beta ? " Beta" : ""));
				uf.drawString(Game.width - 103, 0, "Debug Mode");
			} else {
				uf.drawString(5f, 0f,
						"Avena Version " + df.format(Game.getVersion())
								+ (Game.beta ? " Beta" : ""));
			}
		}
	}

	private void drawHealth() {
		for (int i = 1; i <= player.getHealth(); i++) {
			heart.bind();

			float y = 30;
			float x = 35 * i;

			glPushMatrix();
			glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex2f(x, y);
			glTexCoord2f(1, 0);
			glVertex2f(x + world.block_size, y);
			glTexCoord2f(1, 1);
			glVertex2f(x + world.block_size, y + world.block_size);
			glTexCoord2f(0, 1);
			glVertex2f(x, y + world.block_size);
			glEnd();
			glPopMatrix();
		}
	}
}
