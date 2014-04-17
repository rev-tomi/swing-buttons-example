package com.example.swingdemo;

import javax.swing.JFrame;

public class Main {

	public static void main(String... args) {
		ExampleFrame frame = new ExampleFrame();
		frame.init();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
