package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import creature.Creature;
import stuff.Position;
import update.CreatureManager;

public class CreaturePicker implements MouseListener {
	
	private CreatureManager manager;
	private Painter painter;
	
	public CreaturePicker(CreatureManager manager) {
		this.manager = manager;
		this.painter = manager.getPainter();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Creature nearestCreature = manager.getCreatures().size() > 0 ? manager.getCreatures().get(0) : null;
		float minDistance = Float.MAX_VALUE;
		Position mousePos = new Position((float)e.getPoint().getX(), (float)e.getPoint().getY() - 25);
		mousePos.move(-(float)painter.getTopLeftCorner().getX(), -(float)painter.getTopLeftCorner().getY());
		mousePos = new Position(mousePos.getX() / painter.getZoomFactor(), mousePos.getY() / painter.getZoomFactor());
		System.out.println(mousePos.getX() + " " + mousePos.getY());
		for (Creature c : manager.getCreatures()) {
			float dist = mousePos.distance2(c.getPos());
			if (dist < minDistance) {
				minDistance = dist;
				nearestCreature = c;
			}
		}
		
		new StatsFrame(nearestCreature);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
}
