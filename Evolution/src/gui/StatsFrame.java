package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

import creature.Creature;
import stuff.Helper;

public class StatsFrame extends JDialog implements Runnable {

	private static final long serialVersionUID = 5305362458875856720L;
	private Creature creature;
	private JLabel age;
	private JLabel generation;
	private JLabel energy;

	public StatsFrame(Creature creature) {
		this.creature = creature;
		init();
		Thread th = new Thread(this);
		th.start();
	}
	
	private void init() {
		setTitle("Creature " + creature.getId());
		setLayout(new GridLayout(5, 1, 5, 5));
		JLabel col = new JLabel();
		setBackground(creature.getColor());
		col.setOpaque(true);
		col.setBackground(creature.getColor());
		age = new JLabel("Age: " + creature.getAge());
		generation = new JLabel("Generation: " + creature.getGeneration());
		energy = new JLabel("Energy: " + creature.getEnergy());
		add(col);
		add(age);
		add(generation);
		add(energy);
		setSize(200, 400);
		setLocation(1200, 100);
		setVisible(true);
	}

	@Override
	public void run() {
		while(true) {
			if (creature.isDead()) {
				this.dispose();
			} else {
				age.setText("Age: " + creature.getAge());
				energy.setText("Energy: " + creature.getEnergy());
				this.repaint();
				Helper.sleep(200);
			}
		}
	}
	
	
	
}
