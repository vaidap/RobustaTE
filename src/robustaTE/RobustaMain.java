package robustaTE;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileSystemView;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class RobustaMain extends JFrame implements ActionListener {
	
	private static JTextArea area;
	private static JFrame frame;
	private static int returnValue = 0;
	private static String appName = "RobustaTE Editor - ";
    private boolean unsavedChanges = false;
	
	 public RobustaMain() { 
		 run();
		 }

	 public static void main(String[] args) {

			RobustaMain runner = new RobustaMain();
			
		}
	 
	  public void run() {
	    // Set the look-and-feel 
		// Tries to default to whatever the host system prefers
	    try {
	    	System.setProperty("apple.laf.useScreenMenuBar", "true"); // use macOS global menu bar instead of in app menu bar if on macOS
	    	System.setProperty( "apple.awt.application.name", "RobustaTE" ); // is meant to set macOS global menu bar app name, doesn't seem to work
	    	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
	      Logger.getLogger(RobustaMain.class.getName()).log(Level.SEVERE, null, ex);
	    }
	    
	    frame = new JFrame("RobustaTE"); //create the window 
		
	    // Build the menu bar
	    JMenuBar menu_main = new JMenuBar(); // creates the menu bar

		JMenu menu_file = new JMenu("File"); // file menu
		JMenu menu_edit = new JMenu("Edit"); // edit menu

		JMenuItem menuitem_new = new JMenuItem("New"); // next 4 lines create options in file menu
	    JMenuItem menuitem_open = new JMenuItem("Open");
	    JMenuItem menuitem_save = new JMenuItem("Save");
	    JMenuItem menuitem_quit = new JMenuItem("Quit");

		menuitem_new.addActionListener(this); // event listeners for file menu options
	    menuitem_open.addActionListener(this);
	    menuitem_save.addActionListener(this);
	    menuitem_quit.addActionListener(this);

		menu_main.add(menu_file);  // adds file menu to the menu bar so it's visible/usable

		menu_file.add(menuitem_new); // next 4 lines add file menu options to the file menu
	    menu_file.add(menuitem_open);
	    menu_file.add(menuitem_save);
	    menu_file.add(menuitem_quit);
	    
	    menu_main.add(menu_edit); // adds edit menu to the menu bar so it's visible/usable

		frame.setJMenuBar(menu_main);
		//JPanel panel = new JPanel();
		//Create a scrollbar using JScrollPane and add panel into it's viewport
		//Set vertical and horizontal scrollbar always show

		// Set attributes of the app window
	
		area = new JTextArea();

		area.addKeyListener(new KeyListener()

		{
		    @Override
		    public void keyPressed(KeyEvent e) {
		        // Handle key pressed actions here
		    }

			@Override
			public void keyTyped(KeyEvent e) {

				// on first key typed, add [*] to window title bar once
				// won't trigger again until unsavedChanges is set to false (on open or save)
				if (unsavedChanges == false) {
					frame.setTitle(frame.getTitle() + " [*]");
				}
				unsavedChanges = true;

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		}
				
				);
		
		JScrollPane scrollBar= new JScrollPane(area,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollBar.setPreferredSize(new Dimension(300, 200));
		
	    //scrollBar.setBounds(5, 5, 100, 100);
	    //add(scrollBar, BorderLayout.CENTER);
	    //frame.add(area);
	    frame.setSize(800, 600); // window size in pixels
	    frame.add(scrollBar);
		//panel.add(scrollBar);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(appName + "untitled file"); // set default window title of main editor window
	    }

	@Override
	public void actionPerformed(ActionEvent e) {
	    String ingest = ""; // was originally = null, but that inserts "null" into first line of any file opened
	    JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
	    jfc.setDialogTitle("Choose destination.");
	    jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

	    String ae = e.getActionCommand();
	    //OPEN
	    if (ae.equals("Open")) {
	    	if (unsavedChanges == false) {
	    		returnValue = jfc.showOpenDialog(null);
	    		if (returnValue == JFileChooser.APPROVE_OPTION) {
	    			File f = new File(jfc.getSelectedFile().getAbsolutePath());
	    			frame.setTitle(appName + f.getPath() ); // set window title to path of the currently open file
	    			unsavedChanges = false;
	    			try {
	    				FileReader read = new FileReader(f);
	    				Scanner scan = new Scanner(read);
	    				while(scan.hasNextLine()){
	    					String line = scan.nextLine() + "\n";
	    					ingest = ingest + line;
	    				}
	    				area.setText(ingest);
	    			}
	    			catch ( FileNotFoundException ex) { ex.printStackTrace(); }
    			}
    		}
	    	else {
	    		JOptionPane.showMessageDialog(null, "Please save your changes first!");
	    	}
	    } 
	    // SAVE
	    else if (ae.equals("Save")) {
	        returnValue = jfc.showSaveDialog(null);
	        try {
	            File f = new File(jfc.getSelectedFile().getAbsolutePath());
	            FileWriter out = new FileWriter(f);
	            out.write(area.getText());
	            out.close();
		        frame.setTitle(appName + f.getPath() ); // set window title to path of the newly created file
		        unsavedChanges = false;
	        } catch (FileNotFoundException ex) {
	            Component f = null;
	            JOptionPane.showMessageDialog(f,"File not found.");
	        } catch (IOException ex) {
	            Component f = null;
	            JOptionPane.showMessageDialog(f,"Error.");
	        }
	    } else if (ae.equals("New")) {
	        area.setText("");
	    } else if (ae.equals("Quit")) { System.exit(0); }
	  }
	
	

}
