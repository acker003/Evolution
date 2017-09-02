package gui;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import stuff.Const;

public class Frame extends JFrame {
	
	private static final long serialVersionUID = -4754712266727627236L;
	
	private JLabel pane = new JLabel();
	private Image image;

	public Frame(Image image) {
		super("Evolution");
		this.image = image;
		setSize(Const.WORLD_WIDTH * Const.TILE_SIZE, 
				Const.WORLD_HEIGHT * Const.TILE_SIZE);
		setLayout(new GridLayout(1, 1));
		add(pane);
		pane.setIcon(new ImageIcon(image));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void update() {
		pane.setIcon(new ImageIcon(image));
	}
}
