import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileReaderAndWriter 
{
	private BufferedWriter writer;
	private BufferedReader reader;
	private File brailleFile = new File("BrailleFile.txt");
	private File txtFile = new File("TextFile.txt");

	FileReaderAndWriter() { };
	
	FileReaderAndWriter(BufferedReader reader, BufferedWriter writer) 
	{
		 this.writer = writer;
		 this.reader = reader;
	}
	
	public String writeBrailleToFile(String s) throws IOException
	{
		writer = new BufferedWriter(new FileWriter(brailleFile));
		
		if(!brailleFile.exists()) { return "BrailleFile not found!"; }
		
		writer.write(s);
		writer.flush();
		return s;
	}

	public String writeTextToFile(String s) throws IOException
	{
		writer = new BufferedWriter(new FileWriter(new File("TextFile.txt")));
		if(!txtFile.exists()) { return "TextFile not found!"; }
		
		writer.write(s);
		writer.flush();
		return s;
	}
	

	public String getTextLine() throws IOException
	{
		if(!txtFile.exists()) { return "TextFile not found!"; }
		reader = new BufferedReader(new FileReader(new File("TextFile.txt")));
		String line = reader.readLine();
		reader.close();
		return line;
	}
	
	public String getBrailleLine() throws IOException
	{
		if(!brailleFile.exists()) { return "BrailleFile not found!"; }
		reader = new BufferedReader(new FileReader(brailleFile));
		String line = reader.readLine();
		reader.close();
		return line;
	}
}
