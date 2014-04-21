package com.example.swingdemo;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

	public static void main(String... args) {
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override public void run() {
				ExampleFrame frame = new ExampleFrame();
				frame.init();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
			
		});
		
	}
}
