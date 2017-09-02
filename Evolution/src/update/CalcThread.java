package update;

import java.util.ArrayList;
import java.util.List;

import creature.Creature;
import gui.Painter;
import stuff.Const;

public class CalcThread implements Runnable {

	private List<Creature> creatures = new ArrayList<Creature>();
	//private boolean update = false;
	private long lastUpdate;

	public CalcThread() {
		Thread th = new Thread(this);
		th.start();
		lastUpdate = System.currentTimeMillis();
	}
	
	public void addCreature(Creature creature) {
		creatures.add(creature);
	}
	
	public void removeCreature(Creature creature) {
		creatures.remove(creature);
	}
	
	public int getCreatureAmount() {
		return creatures.size();
	}
	
	public void update() {
		//this.update = true;
		long deltaTime = System.currentTimeMillis() - lastUpdate;
		for (Creature c : new ArrayList<Creature>(creatures)) {
			c.act((float)(deltaTime * 1.0 / Const.MS_PER_DELTA_TIME));
		}
		lastUpdate = System.currentTimeMillis();
		//update = false;
	}
	
	public void paintCreatures(Painter p) {
		for (Creature c : new ArrayList<Creature>(creatures)) {
			p.paintCreature(c);
		}
	}
	
	@Override
	public void run() {
		// do nothing
	}
	
	
}
