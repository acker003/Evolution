package main;

import creature.Creature;
import data.Map;
import data.Tile;
import gui.CreaturePicker;
import gui.Frame;
import gui.Painter;
import stuff.Const;
import stuff.Helper;
import stuff.Position;
import update.CreatureManager;

public class Simulation implements Runnable {

	public static Creature oldestCreatureEver = null;
	public static Creature oldestCreatureNow = null;
	
	private static Painter painter = new Painter();
	private static Map map = new Map(painter);
	private static CreatureManager manager = new CreatureManager(painter);
	private long sleepTime = Const.SLEEP_TIME_PER_GUI_UPDATE;
	private long sleepTime2 = Const.SLEEP_TIME_PER_MAP_UPDATE;	// TODO
	private static Frame frame = new Frame(painter.getImage());
	
	public Simulation() {
		frame.addKeyListener(painter);
		frame.addMouseListener(new CreaturePicker(manager));
		Thread th = new Thread(this);
		th.start();
	}
	
	public static Tile getTileAtWorldPosition(Position pos) {
		return map.getTileAtWorldPosition(pos);
	}
	
	public static void main(String[] args) {
		new Simulation();
	}

	@Override
	public void run() {
		while (true) {
			map.update();
			painter.clear();
			map.paint();
			manager.paint();
			frame.update();
			Helper.sleep(sleepTime);
		}
	}
}
