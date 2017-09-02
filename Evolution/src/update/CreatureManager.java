package update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import creature.Creature;
import gui.Painter;
import stuff.Const;

public class CreatureManager implements Runnable {

	private Map<Creature, CalcThread> creatures 
			= new HashMap<Creature, CalcThread>();
	private Map<Integer, List<CalcThread>> calcThreads 
			= new HashMap<Integer, List<CalcThread>>();
	private Thread th;
	private long sleepTime = Const.SLEEP_TIME_PER_ACT;
	private Painter painter;
	
	public CreatureManager(Painter painter) {
		this.painter = painter;
		for (int i = 0; i <= Const.MAX_CREATURE_PER_THREAD; i++) {
			calcThreads.put(i, new ArrayList<CalcThread>());
		}
		createNewThreads();
		for (int i = 0; i < 100; i++) {
			createCreature();
		}
		th = new Thread(this);
		th.start();
	}
	
	private void update() {
		for (int i = 0; i <= Const.MAX_CREATURE_PER_THREAD; i++) {
			for (CalcThread th : new ArrayList<CalcThread>(calcThreads.get(i))) {
				th.update();
			}
		}
	}
	
	public void addCreature(Creature creature) {
		creatures.put(creature, getFreeThread(creature));
	}
	
	public void removeCreature(Creature creature) {
		CalcThread ct = creatures.remove(creature);
		if (ct != null) ct.removeCreature(creature);
	}
	
	public void moveCalcThread(CalcThread ct, int newAmount) {
		calcThreads.get(newAmount + 1).remove(ct);
		calcThreads.get(newAmount).add(ct);
	}
	
	private void createNewThreads() {
		for (int i = 0; i < Const.NEW_THREAD_AMOUNT; i++) {
			calcThreads.get(0).add(new CalcThread());
		}
	}
	
	private CalcThread getFreeThread(Creature creature) {
		for (int i = 0; i < Const.MAX_CREATURE_PER_THREAD; i++) {
			if (calcThreads.get(i).size() > 0) {
				CalcThread ct = calcThreads.get(i).get(0);
				calcThreads.get(i).remove(ct);
				calcThreads.get(i + 1).add(ct);
				ct.addCreature(creature);
				return ct;
			}
		}
		createNewThreads();
		CalcThread th = getFreeThread(creature);
		th.addCreature(creature);
		return th;
	}
	
	public List<Creature> getCreatures() {
		return new ArrayList<Creature>(creatures.keySet());
	}
	
	public int getCreatureAmount() {
		return creatures.size();
	}

	public long getSleepTime() {
		return sleepTime;
	}
	
	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}
	
	private void createCreature() {
		addCreature(new Creature(this));
	}
	
	public void paint() {
		for (int i = 0; i <= Const.MAX_CREATURE_PER_THREAD; i++) {
			for (CalcThread th : new ArrayList<CalcThread>(calcThreads.get(i))) {
				if (th != null && painter != null) th.paintCreatures(painter);
			}
		}
	}
	
	@Override
	public void run() {
		long lastUpdate;
		while (true) {
			lastUpdate = System.currentTimeMillis();
			update();
			if (creatures.size() < 100) {
				createCreature();
			}
			while (System.currentTimeMillis() - lastUpdate < sleepTime) {}
		}
	}
	
}
