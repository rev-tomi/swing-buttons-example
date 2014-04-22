package com.example.swingdemo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.ButtonModel;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToggleButton.ToggleButtonModel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class InternalFrameFactory {
	
	static abstract class CheckerInternalFrame extends JInternalFrame {
		
		private static final long serialVersionUID = 4030440002012563184L;
		
		private JCheckBoxMenuItem checker;
		private String title;
		
		private boolean initialized;
		
		public CheckerInternalFrame(String title) {
			super(title, true, true, true, true);
			this.title = title;
			setSize(320, 240);
			setVisible(true);
		}
		
		protected final void init(JCheckBoxMenuItem checker) {
			if (initialized) {
				throw new IllegalStateException("Internal frame already initialized");
			}
			this.checker = checker;
			checker.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					updateTitle();
				}
			});
			initMenuBar();
			updateTitle();
			
			addInternalFrameListener(new InternalFrameAdapter() {
				public void internalFrameClosing(InternalFrameEvent e) {
					JCheckBoxMenuItem checker = CheckerInternalFrame.this.checker;
					if (checker != null) {
						checker.setAction(null);
						checker.setModel(null);
					}
				}
			});
			
			initialized = true;
		}
		
		private void initMenuBar() {
			JMenuBar menuBar = new JMenuBar();
			JMenu menu = new JMenu("File");
			menu.add(checker);
			menuBar.add(menu);
			setJMenuBar(menuBar);
		}
		
		private void updateTitle() {
			ToggleButtonModel model = (ToggleButtonModel) checker.getModel();
			String actualTitle = title + " - " + (model.isSelected() ? "Checked" : "Unchecked"); 
			setTitle(actualTitle);
		}
	}
	
	static class DoubleCheckerInternalFrame extends CheckerInternalFrame {
		
		private static final long serialVersionUID = 7764296274182376110L;

		public DoubleCheckerInternalFrame(ButtonModel model, Action action, String checkItText) {
			super("Double checker");
			
			JCheckBoxMenuItem checkIt = new JCheckBoxMenuItem(checkItText);
			checkIt.setAction(action);
			checkIt.setModel(model);
			
			init(checkIt);
		}
	}
	
	static class SingleCheckerInternalFrame extends CheckerInternalFrame {
		
		private static final long serialVersionUID = 8380545232445039770L;

		public SingleCheckerInternalFrame(ButtonModel model, String checkItText) {
			super("Single checker");
			
			JCheckBoxMenuItem checkIt = new JCheckBoxMenuItem(checkItText);
			checkIt.setModel(model);
			
			init(checkIt);
		}
		
	}
	
	static class DifferentModelInternalFrame extends CheckerInternalFrame {
		
		private static final long serialVersionUID = -5768769546908502695L;

		public DifferentModelInternalFrame(Action action) {
			super("Same action");
			
			JCheckBoxMenuItem checkIt = new JCheckBoxMenuItem(action);
			init(checkIt);
		}
		
	}
	
	static class SameModelInternalFrame extends CheckerInternalFrame {
		
		private static final long serialVersionUID = -7958841262971958339L;

		public SameModelInternalFrame(ButtonModel model) {
			super("Same model");
			
			JCheckBoxMenuItem checkIt = new JCheckBoxMenuItem();
			checkIt.setModel(model);
			init(checkIt);
		}
	}

}
