import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.swing.DefaultListModel;
import javax.swing.SwingWorker;

public class processVowels extends SwingWorker<String, Void>
{
	private String currentLine, fileVowels;
	private BufferedReader br;
	private File file;
	private DefaultListModel<String> showListModel = new DefaultListModel<String>();
	
	public processVowels(DefaultListModel<String> l, File f)
	{
		showListModel = l;
		file = f;
	}
	protected String doInBackground() throws Exception
	{
			try
			{
				int count = 0;
				br = new BufferedReader(new FileReader(file));//Pass file from method
				while((currentLine = br.readLine()) != null)
				{
					for(int i = 0; i < currentLine.length(); i++)
					{
						char letter = currentLine.charAt(i);
						if(letter == 'a' || letter == 'e'|| letter == 'o'|| letter == 'i'|| letter == 'u'||letter == 'A'||letter == 'E'||letter == 'I'|| letter == 'O'|| letter == 'U')
						{
							count++;
						}
					}
				}
				fileVowels = file.getAbsolutePath() + " -- Vowels: " + count;
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			return fileVowels;
	}
	
	protected void done()
	{
		try
		{
			String result = get();
			showListModel.addElement(result);
		}
		catch(InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
	}
	
}
