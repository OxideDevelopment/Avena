package me.justinp.avena.save;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import me.justinp.avena.world.World;

public class SaveHandler {

	public static void save(World w) {
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("world.awf")));
			oos.writeObject(new SaveFile(w));
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static World loadWorld() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("world.awf")));
			SaveFile save = (SaveFile) ois.readObject();
			ois.close();
			return save.getWorld();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
