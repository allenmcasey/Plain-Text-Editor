import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;

public class TextEditor{

	//Text window declarations
	JFrame window = new JFrame();
	JTextArea text = new JTextArea(20, 60);
	boolean lineWrapped = true;
	JScrollPane jsp = new JScrollPane(text, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	private JPanel panel = new JPanel();
	
	//Search window declarations
	private JFrame searchFrame = new JFrame();
	private JPanel searchPanel = new JPanel();
	JLabel searchWhat = new JLabel("What would you like to search for?");
	JTextArea search = new JTextArea(1, 10);
	boolean matchingCase = true;
	
	//Variables that store the current font, style, and text size
	String fontName = "SansSerif";
	char fontStyle = 'P';
	int fontSize = 12;
	
	//Variables used in searching the text
	boolean firstSearch = true;
	String searchString = null;
	String textString = null;
	int indexOfResult = -1;
	int startSearch = 0;
	
	//Menu declarations
	JMenuBar menuBar; 
	JMenu fileMenu, editMenu, formatMenu, sizeMenu, fontMenu, styleMenu;
	JMenuItem save, saveAs, find, replace, selectAll, lineWrap;
	JMenuItem size12, size14, size16, courierFont, sansSerifFont, arialFont, plain, bold, italic;
	JCheckBox matchCase;
	
	//Editor constructor
	public TextEditor() {
		
		//builds JPanel and adds to JFrame
		window.setTitle("Text Editor");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.add(jsp);
		window.add(panel);
		Font font = new Font(fontName, Font.PLAIN, fontSize);
		text.setFont(font);
		
		//Builds the save menu
		menuBar = new JMenuBar();
		window.setJMenuBar(menuBar);
		
		//Builds the file menu
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		//Builds the edit menu
		editMenu = new JMenu("Edit");
		menuBar.add(editMenu);
		
		//Builds the format menu
		formatMenu = new JMenu("Format");
		menuBar.add(formatMenu);
		
		//Adds 'save' option
		save = new JMenuItem("Save");
		fileMenu.add(save);
		save.setActionCommand("save");
		save.addActionListener(new MenuListener());
		
		//Adds 'save as' option
		saveAs = new JMenuItem("Save As");
		fileMenu.add(saveAs);
		saveAs.setActionCommand("saveAs");
		saveAs.addActionListener(new MenuListener());
		
		//Adds 'open' option
		save = new JMenuItem("Open");
		fileMenu.add(save);
		save.setActionCommand("open");
		save.addActionListener(new MenuListener());
		
		//Adds 'find' option
		find = new JMenuItem("Find");
		editMenu.add(find);
		find.setActionCommand("find");
		find.addActionListener(new MenuListener());
		
		//Adds 'replace' option
		replace = new JMenuItem("Replace");
		editMenu.add(replace);
		replace.setActionCommand("replace");
		replace.addActionListener(new MenuListener());
		
		//Adds 'select all' option
		selectAll = new JMenuItem("Select All");
		editMenu.add(selectAll);
		selectAll.setActionCommand("selectAll");
		selectAll.addActionListener(new MenuListener());
		
		//Adds size menu
		sizeMenu = new JMenu("Size");
		formatMenu.add(sizeMenu);
		
		//Adds font sizes
		size12 = new JMenuItem("12");
		sizeMenu.add(size12);
		size12.setActionCommand("size12");
		size12.addActionListener(new MenuListener());
		size14 = new JMenuItem("14");
		sizeMenu.add(size14);
		size14.setActionCommand("size14");
		size14.addActionListener(new MenuListener());
		size16 = new JMenuItem("16");
		sizeMenu.add(size16);
		size16.setActionCommand("size16");
		size16.addActionListener(new MenuListener());
		
		//Adds font menu
		fontMenu = new JMenu("Font");
		formatMenu.add(fontMenu);
		
		//Adds courier font
		courierFont = new JMenuItem("Courier");
		fontMenu.add(courierFont);
		courierFont.setActionCommand("Courier");
		courierFont.addActionListener(new MenuListener());
		
		//Adds sans serif font
		sansSerifFont = new JMenuItem("Sans Serif");
		fontMenu.add(sansSerifFont);
		sansSerifFont.setActionCommand("Sans Serif");
		sansSerifFont.addActionListener(new MenuListener());
	
		//Adds arial font
		arialFont = new JMenuItem("Arial");
		fontMenu.add(arialFont);
		arialFont.setActionCommand("Arial");
		arialFont.addActionListener(new MenuListener());
		
		//Adds style menu
		styleMenu = new JMenu("Style");
		formatMenu.add(styleMenu);
		
		//Adds plain style
		plain = new JMenuItem("Plain");
		styleMenu.add(plain);
		plain.setActionCommand("Plain");
		plain.addActionListener(new MenuListener());
		
		//Adds bold style
		bold = new JMenuItem("Bold");
		styleMenu.add(bold);
		bold.setActionCommand("Bold");
		bold.addActionListener(new MenuListener());
		
		//Adds italic style
		italic = new JMenuItem("Italic");
		styleMenu.add(italic);
		italic.setActionCommand("Italic");
		italic.addActionListener(new MenuListener());
		
		//Adds line wrap
		lineWrap = new JMenuItem("Line Wrap");
		formatMenu.add(lineWrap);
		lineWrap.setActionCommand("Line Wrap");
		lineWrap.addActionListener(new MenuListener());
				
		text.setLineWrap(true);
		window.pack();
		window.setVisible(true);
		
		//Creates search window
		searchFrame.add(searchPanel);
		searchPanel.add(searchWhat);
		searchPanel.add(search);
		
		JButton findNext = new JButton("Find");
		findNext.setActionCommand("find next");
		findNext.addActionListener(new MenuListener());
		searchPanel.add(findNext);
		
		JButton cancel = new JButton("Cancel");
		cancel.setActionCommand("cancel");
		cancel.addActionListener(new MenuListener());
		searchPanel.add(cancel);
		
		matchCase = new JCheckBox("Match Case");
		matchCase.setSelected(true);
		matchCase.setActionCommand("match case");
		matchCase.addActionListener(new MenuListener());
		searchPanel.add(matchCase);
		
		searchFrame.setTitle("Find");
		searchFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		searchFrame.pack();
		
	}
	
	
	//Responds to clicks on save menu
	private class MenuListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			//Checks if a file has been created, then writes current text to file if so
			if (e.getActionCommand().equals("save")) {
				try {
					saveFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
			//Allows the user to create and name a file then save current text to it
			if (e.getActionCommand().equals("saveAs")) {
				try {
					saveFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
			//Allows user to open and edit existing text files
			if (e.getActionCommand().equals("open")) {
				openFile();
			}
			
			//Allows user to enter a string then uses searchText() to highlight it
			if (e.getActionCommand().equals("find")) {
				searchFrame.setVisible(true);
				if (firstSearch) {
					startSearch = 0;
					textString = text.getText();
				}
			}
			
			//Finds next instance of search string in text area
			if (e.getActionCommand().equals("find next")) {
				textString = text.getText();
				firstSearch = false;
				searchString = search.getText();
				searchText();
				searchFrame.setVisible(true);
		
			}
			
			//Closes out search window
			if (e.getActionCommand().equals("cancel")) {
				firstSearch = true;
				search.setText(null);
				searchFrame.dispose();
			}
			
			//Allows user to toggle the match case option
			if (e.getActionCommand().equals("match case")) {
				matchingCase = !matchingCase;
			}
			
			//Allows user to replace string with another string
			if (e.getActionCommand().equals("replace")) {
				replace();
			}
			
			//Selects all text in editor
			if (e.getActionCommand().equals("selectAll")) {
				text.requestFocus();
				text.select(0, text.getText().length());
			}
			
			//Allows user to toggle line wrap
			if (e.getActionCommand().equals("Line Wrap")) {
				if (lineWrapped) {
					lineWrapped = false;
				}
				else {
					lineWrapped = true;
				}
				text.setLineWrap(lineWrapped);
			}
			
			//Changes font size to 12
			if (e.getActionCommand().equals("size12")) {
				text.setFont(text.getFont().deriveFont(12f));
				fontSize = 12;
			}
			
			//Changes font size to 14
			if (e.getActionCommand().equals("size14")) {
				text.setFont(text.getFont().deriveFont(14f));
				fontSize = 14;
			}
			
			//Changes font size to 16
			if (e.getActionCommand().equals("size16")) {
				text.setFont(text.getFont().deriveFont(16f));
				fontSize = 16;
			}
			
			//Listener that changes font to courier
			if (e.getActionCommand().equals("Courier")) {
				fontName = "Courier";
				switch (fontStyle) {
				case 'P':	Font font = new Font(fontName, Font.PLAIN, fontSize);
							text.setFont(font);
							break;
				case 'B':	font = new Font(fontName, Font.BOLD, fontSize);
							text.setFont(font);
							break;
				case 'I': 	font = new Font(fontName, Font.ITALIC, fontSize);
							text.setFont(font);
							break;
				}
			}
			
			//Listener that changes font to sans serif
			if (e.getActionCommand().equals("Sans Serif")) {
				fontName = "SansSerif";
				switch (fontStyle) {
				case 'P':	Font font = new Font(fontName, Font.PLAIN, fontSize);
							text.setFont(font);
							break;
				case 'B':	font = new Font(fontName, Font.BOLD, fontSize);
							text.setFont(font);
							break;
				case 'I': 	font = new Font(fontName, Font.ITALIC, fontSize);
							text.setFont(font);
							break;
				}
			}
			
			//Listener that changes font to arial
			if (e.getActionCommand().equals("Arial")) {
				fontName = "Arial";
				switch (fontStyle) {
					case 'P':	Font font = new Font(fontName, Font.PLAIN, fontSize);
								text.setFont(font);
								break;
					case 'B':	font = new Font(fontName, Font.BOLD, fontSize);
								text.setFont(font);
								break;
					case 'I': 	font = new Font(fontName, Font.ITALIC, fontSize);
								text.setFont(font);
								break;
				}
			}
			
			//Listener that changes style to bold
			if (e.getActionCommand().equals("Bold")) {
				fontStyle = 'B';
				Font font = new Font(fontName, Font.BOLD, fontSize);
				text.setFont(font);
			}
			
			//Listener that changes style to plain
			if (e.getActionCommand().equals("Plain")) {
				fontStyle = 'P';
				Font font = new Font(fontName, Font.PLAIN, fontSize);
				text.setFont(font);
			}
			
			//Listener that changes style to italic
			if (e.getActionCommand().equals("Italic")) {
				fontStyle = 'I';
				Font font = new Font(fontName, Font.ITALIC, fontSize);
				text.setFont(font);
			}
		}
	}
	
	//Method that allows user to save current text
	public void saveFile() throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showSaveDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
		  File file = fileChooser.getSelectedFile();
		  FileWriter writer = new FileWriter(file);
		  text.write(writer);
		}
	}
	
	//Method that allows user to open a file
	public void openFile() {
		JFileChooser fc = new JFileChooser();
		StringBuffer buffer = new StringBuffer();
		int result = fc.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				File file = fc.getSelectedFile();
				FileReader reader = new FileReader(file);
				int i = 1;
				while (i != -1) {
					i = reader.read();
					char ch = (char)i;
					buffer.append(ch);
				}
				reader.close();
				String fileText = buffer.toString().substring(0, buffer.toString().length() - 1);
				text.setText(fileText);
				
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	//Searches text for user inputed string and highlights if found
	public void searchText() {
		
		if (searchString != null) {
			//if ignoring case, bring everything to lowercase
			if (!matchingCase) {
				textString = textString.toLowerCase();
				searchString = searchString.toLowerCase();
			}
			indexOfResult = textString.indexOf(searchString, startSearch);
			if (indexOfResult == -1)
				JOptionPane.showMessageDialog(null, "String not found in text.");
			else {
				text.requestFocus();
				text.select(indexOfResult, indexOfResult + searchString.length());
			}
			startSearch = (indexOfResult + 1);
		}
	}
	
	//Method that allows user to replace string with another string
	public void replace() {
		String toBeReplaced = JOptionPane.showInputDialog("What would you like to replace?");
		String toReplace = JOptionPane.showInputDialog("What would you like to replace it with?");
		String result = text.getText().replaceAll(toBeReplaced, toReplace);
		text.setText(result);
	}
	
	//Main method that instantiates the editor
	public static void main(String[] args) {
		
		try {
            		// Set System L&F
			UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());
			
    		} 
		catch (UnsupportedLookAndFeelException e) {
		    }
		    catch (ClassNotFoundException e) {
		    }
		    catch (InstantiationException e) {
		    }
		    catch (IllegalAccessException e) {
		    }
		
		new TextEditor();
	}
}
