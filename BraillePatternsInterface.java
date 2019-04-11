import java.io.IOException;

public interface BraillePatternsInterface 
{
	boolean wordMightBeContractions(String word);
	boolean wordMightBeNumber(String word);
	boolean isCharacterPunctuation(char c);
	boolean isCharacterUppercase(char c);
	boolean isCharacterNumeric(char c);
	boolean isCharacterSpecial(char c);
	boolean getPunctuationCriteria(char c1, char c2);
	String getContractionKey(char c);
	
	// Methods for getting text or char to/from braille/text
	String fromTextToBrailleFile() throws IOException;
	String fromBrailleToTextFile() throws IOException;
	String fromTextToBraille(String s) throws IOException;
	String fromBrailleToText(String s) throws IOException;
	char getBrailleFromChar(char key);
	char getCharFromBraille(char value);
}
