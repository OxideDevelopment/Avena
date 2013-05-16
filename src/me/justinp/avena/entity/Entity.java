package me.justinp.avena.entity;

public interface Entity{
	
	public void move(int x, int y);
	public void draw();
	public int getX();
	public void setX(int x, boolean move);
	public int getY();
	public void setY(int y, boolean move);
	public Inventory getInventory();
	public int getHealth();
	public void setHealth(int health);
	public void hitBy(Entity e, Weapon w);
	public void hit(Entity e, Weapon w);
	public boolean isDead();
	
	
}
