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
	
	 public RobustaMain() { 
		 run(); 
		 }

	 public static void main(String[] args) {

			RobustaMain runner = new RobustaMain();
		}
	 
	  public void run() {
	    frame = new JFrame("RobustaTE");

	    // Set the look-and-feel (LNF) of the application
		// Try to default to whatever the host system prefers
	    try {
	      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
	      Logger.getLogger(RobustaMain.class.getName()).log(Level.SEVERE, null, ex);
	    }

		// Build the menu
	    JMenuBar menu_main = new JMenuBar();

		JMenu menu_file = new JMenu("File");

		JMenuItem menuitem_new = new JMenuItem("New");
	    JMenuItem menuitem_open = new JMenuItem("Open");
	    JMenuItem menuitem_save = new JMenuItem("Save");
	    JMenuItem menuitem_quit = new JMenuItem("Quit");

		menuitem_new.addActionListener(this);
	    menuitem_open.addActionListener(this);
	    menuitem_save.addActionListener(this);
	    menuitem_quit.addActionListener(this);

		menu_main.add(menu_file);

		menu_file.add(menuitem_new);
	    menu_file.add(menuitem_open);
	    menu_file.add(menuitem_save);
	    menu_file.add(menuitem_quit);

		frame.setJMenuBar(menu_main);
		//JPanel panel = new JPanel();
		//Create a scrollbar using JScrollPane and add panel into it's viewport
		//Set vertical and horizontal scrollbar always show

		// Set attributes of the app window
		
	
		area = new JTextArea();
		JScrollPane scrollBar= new JScrollPane(area,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollBar.setPreferredSize(new Dimension(300, 200));
		
	    //scrollBar.setBounds(5, 5, 100, 100);
	    //add(scrollBar, BorderLayout.CENTER);
	    //frame.add(area);
	    frame.setSize(640, 480);
	    frame.add(scrollBar);
		//panel.add(scrollBar);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    }

	@Override
	public void actionPerformed(ActionEvent e) {
	    String ingest = null;
	    JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
	    jfc.setDialogTitle("Choose destination.");
	    jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

	    String ae = e.getActionCommand();
	    if (ae.equals("Open")) {
	        returnValue = jfc.showOpenDialog(null);
	        if (returnValue == JFileChooser.APPROVE_OPTION) {
	        File f = new File(jfc.getSelectedFile().getAbsolutePath());
	        try{
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
	    // SAVE
	    } else if (ae.equals("Save")) {
	        returnValue = jfc.showSaveDialog(null);
	        try {
	            File f = new File(jfc.getSelectedFile().getAbsolutePath());
	            FileWriter out = new FileWriter(f);
	            out.write(area.getText());
	            out.close();
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
