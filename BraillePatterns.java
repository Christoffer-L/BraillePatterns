import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class BraillePatterns implements BraillePatternsInterface
{
	// String to hold the word, either alphabetical or braille
	private Map<Character, Character> characterMap = new HashMap<Character, Character>();
	private Map<Integer, Character> numberMap = new HashMap<Integer, Character>();
	private Map<String, Character> contractionMap = new HashMap<String, Character>();
	private Map<Character, ArrayList<Character>> punctuationMap = new HashMap<Character, ArrayList<Character>>();
	
	// special character to indicate that the next character(s) are numbers
	private char numberSign = 10300;
	
	// special character to indicate that the next character should be capitalized 
	private char capitalLetterSign = 10272;
	
	// Constructors for BraillePatterns
	public BraillePatterns() 
	{
		_init_();
	}
	
	private void _init_()
	{
		// Map the different characters from the alphabet to characterMap
		characterMap.put('a', (char)10241);
		characterMap.put('b', (char)10243);
		characterMap.put('c', (char)10249);
		characterMap.put('d', (char)10265);
		characterMap.put('e', (char)10257);
		characterMap.put('f', (char)10251);
		characterMap.put('g', (char)10267);
		characterMap.put('h', (char)10259);
		characterMap.put('i', (char)10250);
		characterMap.put('j', (char)10266);
		characterMap.put('k', (char)10245);
		characterMap.put('l', (char)10247);
		characterMap.put('m', (char)10253);
		characterMap.put('n', (char)10269);
		characterMap.put('o', (char)10261);
		characterMap.put('p', (char)10255);
		characterMap.put('q', (char)10271);
		characterMap.put('r', (char)10263);
		characterMap.put('s', (char)10254);
		characterMap.put('t', (char)10270);
		characterMap.put('u', (char)10277);
		characterMap.put('v', (char)10279);
		characterMap.put('w', (char)10298);
		characterMap.put('x', (char)10285);
		characterMap.put('y', (char)10301);
		characterMap.put('z', (char)10293);
		
		// Map numbers from 0 - 9 to the numberMap
		numberMap.put(0, (char) 10266);
		numberMap.put(1, (char) 10241);
		numberMap.put(2, (char) 10243);
		numberMap.put(3, (char) 10249);
		numberMap.put(4, (char) 10265);
		numberMap.put(5, (char) 10257);
		numberMap.put(6, (char) 10251);
		numberMap.put(7, (char) 10267);
		numberMap.put(8, (char) 10259);
		numberMap.put(9, (char) 10250);
		
		// Map punctuation to the punctuationMap
		punctuationMap.put(',', new ArrayList<Character>() {{ add((char) 10242); }} );
		punctuationMap.put(';', new ArrayList<Character>() {{ add((char) 10246); }} );
		punctuationMap.put(':', new ArrayList<Character>() {{ add((char) 10258); }} ); 
		punctuationMap.put('.', new ArrayList<Character>() {{ add((char) 10290); }} ); 
		punctuationMap.put('!', new ArrayList<Character>() {{ add((char) 10262); }} ); 
		punctuationMap.put('(', new ArrayList<Character>() {{ add((char) 10256); add((char) 10275); }} );
		punctuationMap.put(')', new ArrayList<Character>() {{ add((char) 10256); add((char) 10268); }} );
		punctuationMap.put('?', new ArrayList<Character>() {{ add((char) 10278); }} );
		punctuationMap.put('/', new ArrayList<Character>() {{ add((char) 10296); add((char) 10252); }} );
		punctuationMap.put('#', new ArrayList<Character>() {{ add('#'); }} ); // ???
		punctuationMap.put('\\', new ArrayList<Character>() {{ add((char) 10296); add((char) 10273); }} );
		
		// Map the different contractions to the contractionsMap
		contractionMap.put("but", (char) 10243);
		contractionMap.put("can", (char) 10249);
		contractionMap.put("do", (char) 10265);
		contractionMap.put("every", (char) 10257);
		contractionMap.put("from", (char) 10251);
		contractionMap.put("go", (char)10267);
		contractionMap.put("have", (char)10259);
		contractionMap.put("just", (char)10266);
		contractionMap.put("knowledge", (char)10280);
		contractionMap.put("like", (char)10296);
		contractionMap.put("more", (char)10253);
		contractionMap.put("not", (char)10269);
		contractionMap.put("people", (char)10255);
		contractionMap.put("quite", (char)10271);
		contractionMap.put("rather", (char)10263);
		contractionMap.put("so", (char)10254);
		contractionMap.put("that", (char)10270);
		contractionMap.put("us", (char)10277);
		contractionMap.put("very", (char)10279);
		contractionMap.put("it", (char)10285);
		contractionMap.put("you", (char)10301);
		contractionMap.put("as", (char)10293);
		contractionMap.put("and", (char)10287);
		contractionMap.put("for", (char)10303);
		contractionMap.put("of", (char)10295);
		contractionMap.put("the", (char)10286);
		contractionMap.put("with", (char)10302);
		contractionMap.put("will", (char)10298);
		contractionMap.put("his", (char)10278);
		contractionMap.put("in", (char)10260);
		contractionMap.put("was", (char)10292);
		contractionMap.put("to", (char)10262);
		
		// When creating a TreeMap the Map will automatically be sorted in ascending order, all we have to do after is assign the TreeMap to the original map
		Map<String,Character> treeMap = new TreeMap<String,Character>(contractionMap);
		contractionMap = treeMap;
	}

	@Override
	public String fromBrailleToText(String s) throws IOException 
	{
		String line = "";
		String temp = "";
		
		final String[] lines = s.split(System.getProperty("line.separator"));
		final int MAX_LINES = lines.length - 1;
		int currentLine = 0;
		
		
		while(currentLine <= MAX_LINES)
		{
			line = lines[currentLine];
			String[] words = line.split("\\s+"); 
			for(String w : words)
			{
				for(int x = 0; x < w.length(); x++)
				{
					int max = (x+1 == w.length() ? x : x+1); // hold the index for getPunctuationCriteria
					if (w.charAt(x) == numberSign)
					{
						++x; // increment x so that we get the number and not the numeric symbol
						for(Entry<Integer, Character> entry : numberMap.entrySet())
						{
							if(entry.getValue().equals(w.charAt(x)))
							{
								temp += entry.getKey();
							}
						}
					}
					else if(getPunctuationCriteria(w.charAt(x), w.charAt(max))) // checks for "potentially" two chars
					{
						for(Entry<Character, ArrayList<Character>> entry : punctuationMap.entrySet())
						{
							ArrayList<Character> tempAL = entry.getValue();
							int ALlength = tempAL.size();
							if (ALlength == 1)
							{
								if(tempAL.get(0) == w.charAt(x))
								{
									temp += entry.getKey();
								}
							}
							else
							{
								if(tempAL.get(0) == w.charAt(x) && tempAL.get(1).equals(w.charAt(max)))
								{
									temp += entry.getKey();
								}
							}
						}
					}
					else if((w.length() == 1) && (contractionMap.containsValue(w.charAt(x)))) // If word is single letter length, it might be contraction
					{
						temp += getContractionKey(w.charAt(x));
					}
					else if(w.charAt(x) == capitalLetterSign)
					{
						++x; // increment x so that we get the capitalized letter
						for(Entry<Character, Character> entry : characterMap.entrySet())
						{
							if(entry.getValue().equals(w.charAt(x)))
							{
								temp += Character.toUpperCase(entry.getKey());
							}
						}
					}
					else // else it has too "bee" a word, right?
					{
						for(Entry<Character, Character> entry : characterMap.entrySet())
						{
							if(entry.getValue().equals(w.charAt(x)))
							{
								temp += entry.getKey();
							}
						}
					}
				}
				temp += " ";
				++currentLine;
			}
		}
		temp = temp.trim();
		return temp;
	}

	@Override
	public String fromBrailleToTextFile() throws IOException 
	{
		String line = "";
		String temp = "";
		BufferedReader br = new BufferedReader(new FileReader("BrailleFile.txt"));
		
		while((line = br.readLine()) != null)
		{
			String[] words = line.split("\\s+"); 
			for(String w : words)
			{
				for(int x = 0; x < w.length(); x++)
				{
					int max = (x+1 == w.length() ? x : x+1); // hold the index for getPunctuationCriteria
					if (w.charAt(x) == numberSign)
					{
						++x; // increment x so that we get the number and not the numeric symbol
						for(Entry<Integer, Character> entry : numberMap.entrySet())
						{
							if(entry.getValue().equals(w.charAt(x)))
							{
								temp += entry.getKey();
							}
						}
					}
					else if(getPunctuationCriteria(w.charAt(x), w.charAt(max))) // checks for "potentially" two chars
					{
						for(Entry<Character, ArrayList<Character>> entry : punctuationMap.entrySet())
						{
							ArrayList<Character> tempAL = entry.getValue();
							int ALlength = tempAL.size();
							if (ALlength == 1)
							{
								if(tempAL.get(0) == w.charAt(x))
								{
									temp += entry.getKey();
								}
							}
							else
							{
								if(tempAL.get(0) == w.charAt(x) && tempAL.get(1).equals(w.charAt(max)))
								{
									temp += entry.getKey();
								}
							}
						}
					}
					else if((w.length() == 1) && (contractionMap.containsValue(w.charAt(x)))) // If word is single letter length, it might be contraction
					{
						temp += getContractionKey(w.charAt(x));
					}
					else if(w.charAt(x) == capitalLetterSign)
					{
						++x; // increment x so that we get the capitalized letter
						for(Entry<Character, Character> entry : characterMap.entrySet())
						{
							if(entry.getValue().equals(w.charAt(x)))
							{
								temp += Character.toUpperCase(entry.getKey());
							}
						}
					}
					else // else it has too "bee" a word, right?
					{
						for(Entry<Character, Character> entry : characterMap.entrySet())
						{
							if(entry.getValue().equals(w.charAt(x)))
							{
								temp += entry.getKey();
							}
						}
					}
				}
				temp += " ";
			}
		}
		temp = temp.trim();
		br.close();
		return temp;
	}
	
	@Override
	public String fromTextToBraille(String s) throws IOException
	{
		String line = "";
		String temp = "";

		final String[] lines = s.split(System.getProperty("line.separator"));
		final int MAX_LINES = lines.length - 1;
		int currentLine = 0;
		
		while(currentLine <= MAX_LINES)
		{
			line = lines[currentLine];
			String[] words = line.split("\\s+"); 
			for(String w : words)
			{
				if (wordMightBeContractions(w)) // if contraction find the correct braille in contractionMap
				{
					temp += contractionMap.get(w);
				}
				else
				{
					for(char c : w.toCharArray()) // if not contraction look through each letter
					{
						if (isCharacterNumeric(c))
						{
							temp += numberSign;
							temp += numberMap.get(Character.getNumericValue(c));
						}
						else
						{
							if(isCharacterPunctuation(c))
							{
								ArrayList<Character> tempAL = punctuationMap.get(c);
								for(Character ch : tempAL)
								{
									temp += ch;
								}
							}
							else if(isCharacterUppercase(c))
							{
								temp += capitalLetterSign;
								temp += characterMap.get(Character.toLowerCase(c));
							}
							else
							{
								temp += characterMap.get(c);
							}
						}
					}
				}
				temp += " ";
				++currentLine;
			}
		}
		temp = temp.trim();
		return temp;
	}
	
	@Override
	public String fromTextToBrailleFile() throws IOException
	{
		String line = "";
		String temp = "";
		BufferedReader br = new BufferedReader(new FileReader("TextFile.txt"));

		while((line = br.readLine()) != null)
		{
			String[] words = line.split("\\s+"); 
			for(String w : words)
			{
				if (wordMightBeContractions(w)) // if contraction find the correct braille in contractionMap
				{
					temp += contractionMap.get(w);
				}
				else
				{
					for(char c : w.toCharArray()) // if not contraction look through each letter
					{
						if (isCharacterNumeric(c))
						{
							temp += numberSign;
							temp += numberMap.get(Character.getNumericValue(c));
						}
						else
						{
							if(isCharacterPunctuation(c))
							{
								ArrayList<Character> tempAL = punctuationMap.get(c);
								for(Character ch : tempAL)
								{
									temp += ch;
								}
							}
							else if(isCharacterUppercase(c))
							{
								temp += capitalLetterSign;
								temp += characterMap.get(Character.toLowerCase(c));
							}
							else
							{
								temp += characterMap.get(c);
							}
						}
					}
				}
				temp += " ";
			}
		}
		temp = temp.trim();
		br.close();
		return temp;
	}
	
	@Override
	public boolean wordMightBeContractions(String word)
	{
		return contractionMap.containsKey(word);
	}
	
	@Override
	public boolean wordMightBeNumber(String word) 
	{
		return numberMap.containsKey(Character.getNumericValue(word.charAt(0)));
	}
	
	@Override
	public boolean isCharacterNumeric(char c) 
	{
		return numberMap.containsKey(Character.getNumericValue(c));
	}
	
	@Override
	public boolean isCharacterPunctuation(char c) 
	{
		return punctuationMap.containsKey(c);
	}

	@Override
	public boolean isCharacterUppercase(char c) 
	{
		return Character.isUpperCase(c);
	}
	
	@Override
	public boolean isCharacterSpecial(char c) 
	{
		return ((c == (char)10300) || (c == (char)10272));
	}
	
	@Override
	public boolean getPunctuationCriteria(char c1, char c2) 
	{
		for(Entry<Character, ArrayList<Character>> entry : punctuationMap.entrySet())
		{
			ArrayList<Character> tempAL = entry.getValue();
			final int ALlength = tempAL.size();
			if (ALlength == 1)
			{
				if(c1 == tempAL.get(0))
				{
					return true;
				}
				else
				{
					continue;
				}
			}
			else
			{
				if(c1 == tempAL.get(0) && c2 == tempAL.get(1))
				{
					return true;
				}
				else
				{
					continue;
				}
			}
		}
		return false;
	}
	
	@Override
	public String getContractionKey(char c) 
	{
		for(Entry<String, Character> entry : contractionMap.entrySet())
		{
			if(entry.getValue().equals(c))
			{
				return entry.getKey();
			}
		}
		return "NO KEY FOUND";
	}
	
	@Override
	public char getBrailleFromChar(char key)
	{
		if (characterMap.containsKey(key))
		{
			return characterMap.get(key);
		}
		return (Character) null;
	}
	
	@Override
	public char getCharFromBraille(char value)
	{
		if (characterMap.containsValue(value))
		{
			for(Entry<Character, Character> entry : characterMap.entrySet())
			{
				if(entry.getValue().equals(value))
				{
					return entry.getKey();
				}
			}
		}
		return (Character) null;
	}
	
	public static void main(String[] args)
	{
		try 
		{
			BraillePatterns bp = new BraillePatterns();
			FileReaderAndWriter fileReaderAndWriter = new FileReaderAndWriter();
	
			fileReaderAndWriter.writeTextToFile("Hello, world!");
			fileReaderAndWriter.writeBrailleToFile("⠠⠓⠑⠇⠇⠕⠂ ⠺⠕⠗⠇⠙⠖");
			
			System.out.println(fileReaderAndWriter.getTextLine());
			System.out.println(fileReaderAndWriter.getBrailleLine()+"\n");
			
			System.out.println(bp.fromBrailleToText("⠠⠺⠕⠗⠇⠙⠂ ⠠⠓⠑⠇⠇⠕⠖"));
			System.out.println(bp.fromTextToBraille("World, hello!")); 
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}