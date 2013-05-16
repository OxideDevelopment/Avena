package me.justinp.avena.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Inventory implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<InventoryEntity> items = new ArrayList<InventoryEntity>();
	
	InventoryEntity selectedIE;
	
	public void addItem(InventoryEntity ie) {
		items.add(ie);
	}
	
	public void removeItem(InventoryEntity ie) {
		items.remove(ie);
	}
	
	public void flush() {
		items.clear();
	}
	
	public void setSelectedItem(InventoryEntity ie) {
		selectedIE = ie;
	}
	
	public InventoryEntity getSelectedItem() {
		if(items.size() == 0) {
			return null;
		}else {
			return selectedIE;
		}
	}
	
	
}
