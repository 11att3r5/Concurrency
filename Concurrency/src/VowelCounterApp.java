import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class VowelCounterApp
{
	
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				CountVowels theApp = new CountVowels();
				theApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				theApp.setVisible(true);
				theApp.pack();
				theApp.setLocationRelativeTo(null);
			}
		});
	}
}

class CountVowels extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static File[] files;
	private static ArrayList<File> addedFiles = new ArrayList<File>();
	private int openFile;
	private JFileChooser fileChooser = new JFileChooser();
	private DefaultListModel<String> setListModel = new DefaultListModel<String>();
	private DefaultListModel<String> showListModel = new DefaultListModel<String>();
	private String path, pathToString;
	
	private JList<String> selectList =  new JList<String>();
	private JList<String> showList = new JList<String>();
	
	private JPanel East = new JPanel();
	private JPanel West = new JPanel();
	private JPanel North = new JPanel();
	private JPanel South = new JPanel();
	
	private JScrollPane scrollPane = new JScrollPane();
	
	private JLabel label = new JLabel("Count Your Vowels!!!");
	
	private JButton addFiles = new JButton("Add Files");
	private JButton process = new JButton("Process");
	private JButton clear = new JButton("Clear");
	private JButton help = new JButton("Help");
	
	public CountVowels()
	{
		super("Vowel Counter");
		this.setLayout(new BorderLayout());
		this.setLocationByPlatform(true);
		this.setResizable(false);
		
		//Add Panels
		this.add(West, BorderLayout.WEST);
		this.add(East, BorderLayout.EAST);
		this.add(North, BorderLayout.NORTH);
		this.add(South, BorderLayout.SOUTH);
        
        //Add JList with JscrollPane to Panels
        West.add(new JScrollPane(selectList));
        East.add(new JScrollPane(showList));
        
        label.setFont(new Font("Monotype Corsiva" ,Font.BOLD, 60));
        
        North.add(label);
        South.add(addFiles);
        South.add(process);
        South.add(clear);
        South.add(help);
        
        clear.setEnabled(false);
        
        //Set JList to be 15 rows high and set blank text
        selectList.setVisibleRowCount(15);
        showList.setVisibleRowCount(15);
        selectList.setPrototypeCellValue(String.format("%200s", " "));
        showList.setPrototypeCellValue(String.format("%200s", " "));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        addFiles.addActionListener(new ActionListener()
        {
			@Override
        	public void actionPerformed(ActionEvent e)
        	{
        		if(e.getSource() == addFiles)
        		{
        			fileChooser = new JFileChooser();
        			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Document", "txt");
        			fileChooser.setFileFilter(filter);
        			fileChooser.setMultiSelectionEnabled(true);
        			openFile = fileChooser.showOpenDialog(null);
        			if(openFile == JFileChooser.APPROVE_OPTION)
        			{
        				files = fileChooser.getSelectedFiles();
        				addedFiles.addAll(Arrays.asList(files));
        				clear.setEnabled(true);
        				for(File f : files)
        				{
        					path = f.getAbsolutePath();
        					pathToString = path.toString();
        					setListModel.addElement(pathToString);
        				}
        				//print path to JList
        				selectList.setModel(setListModel);
 
        			}
        		}
        	}
        });
        
        process.addActionListener(new ActionListener()
        {
        	@Override
        	public void actionPerformed(ActionEvent e)
        	{
        		if(e.getSource() == process)
        		{
        			process.setEnabled(false);
	        		
        			if(addedFiles.isEmpty())
        			{
        				JOptionPane.showMessageDialog(null,
        				"Please add files to be processed",
        				"Process error",
        				JOptionPane.ERROR_MESSAGE);
        				process.setEnabled(true);
        			}
        			else
        			{
	        			for(File f : addedFiles)
	        			{
	        			processVowels task = new processVowels(showListModel, f);
	        			task.execute();
	        			showList.setModel(showListModel);
	        			process.setEnabled(true);
	        			}
        			}

        		}
        	}
        });
        
        clear.addActionListener(new ActionListener()
        {
        	@Override
        	public void actionPerformed(ActionEvent e)
        	{
        		if(e.getSource() == clear)
        		{
        			addedFiles.removeAll(Arrays.asList(files));
        			showListModel.removeAllElements();
        			showList.setModel(showListModel);
        			setListModel.removeAllElements();
        			selectList.setModel(setListModel);
        			clear.setEnabled(false);
        		}
        	}
        });
        
        help.addActionListener(new ActionListener()
        {
        	@Override
        	public void actionPerformed(ActionEvent e)
        	{
        		if(e.getSource() == help)
        		{
        			JOptionPane.showMessageDialog(null, "To process your text files:\n"
        					+ "First, click the add files button to select files\n"
        					+ "Second, click the process button to count the vowels in the selected files(Large files may take longer, please be patient).\n"
        					+ "Use the clear button to clear all files from the list."
        					,"Help", JOptionPane.QUESTION_MESSAGE);
        		}
        	}
        });
        
    }
}


