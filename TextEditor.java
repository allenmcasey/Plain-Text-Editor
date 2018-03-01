package texteditor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;

//TODO: add undo feature
//TODO: Glam up UI

public class TextEditor extends JFrame{

	//Text window declarations
	private static final long serialVersionUID = 1L;
	JFrame window = new JFrame();
	JTextArea text = new JTextArea(25, 40);
	private JPanel panel = new JPanel();
	
	//Save menu declarations
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem save;
	JMenuItem saveAs;
	
	//Name that user wishes to save file as
	String filename = null;
	
	public TextEditor() {
		
		add(panel);
		setTitle("Text Editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setLayout(new BorderLayout());
		panel.add(text, BorderLayout.SOUTH);
		add(panel);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menu= new JMenu("Save");
		menuBar.add(menu);
		
		save = new JMenuItem("Save");
		menu.add(save);
		save.setActionCommand("save");
		save.addActionListener(new SaveListener());
		
		saveAs = new JMenuItem("Save As");
		menu.add(saveAs);
		saveAs.setActionCommand("saveAs");
		saveAs.addActionListener(new SaveListener());
	
		pack();
		setVisible(true);
		
	}
	
	private class SaveListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("save")) {
				if (filename == null)
					filename = JOptionPane.showInputDialog("What would you like to name the file?");
				File file = new File("C:\\Users\\Allen\\Desktop\\" + filename + ".txt");
				FileWriter fileWriter;
				try {
					fileWriter = new FileWriter(file);
					PrintWriter printWriter = new PrintWriter(fileWriter);
					printWriter.print(text.getText());
					printWriter.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (e.getActionCommand().equals("saveAs")) {
				filename = JOptionPane.showInputDialog("What would you like to name the file?");
				File file = new File("C:\\Users\\Allen\\Desktop\\" + filename + ".txt");
				FileWriter fileWriter;
				try {
					fileWriter = new FileWriter(file);
					PrintWriter printWriter = new PrintWriter(fileWriter);
					printWriter.print(text.getText());
					printWriter.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new TextEditor();
	}

}
