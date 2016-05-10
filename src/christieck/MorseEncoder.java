package christieck;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * The main driver class that encodes the specified file into the output file
 */
public class MorseEncoder
{
    private static Scanner input;
    private static Map<Character, String> morseChars;

    private static final String MORSE_CHARS_FILE = "morsecode.txt";

    public MorseEncoder()
    {
        input = new Scanner(System.in);
        morseChars = loadMorseCharacters(MORSE_CHARS_FILE);

        File inputFile = new File(getInput("Enter an input file name"));
        File outputFile = new File(getInput("Enter an output file name"));

        morseEncodeFile(inputFile, outputFile);
    }

    /**
     * Encodes the specified input file into the specified output file
     *
     * @param inputFile The input file to read from
     * @param outputFile The output file to write to
     */
    private void morseEncodeFile(File inputFile, File outputFile)
    {
        try (Scanner in = new Scanner(inputFile))
        {
            List<String> lines = new ArrayList<>();

            while (in.hasNext())
            {
                String line = in.nextLine();
                String encodedLine = encodeLine(line);

                lines.add(encodedLine);
                lines.add("");
            }

            Files.write(outputFile.toPath(), lines);
        } catch (FileNotFoundException e)
        {
            System.err.println("The input file specified could not be found.");
        } catch (IOException e)
        {
            System.err.println("Unable to write to the output file.");
        }
    }

    /**
     * Encodes a line of words into morse code
     *
     * @param line A line of words the be encoded
     * @return The morse encoded line
     */
    private String encodeLine(String line)
    {
        String encodedLine = "";
        String[] words = line.split(" ");

        for (String word : words)
        {
            encodedLine += encodeWord(word) + "| ";
        }

        return encodedLine;
    }

    /**
     * Encodes a word into morse code
     *
     * @param word The word to encode
     * @return The morse encoded word
     */
    private String encodeWord(String word)
    {
        String encodedWord = "";

        for (char character : word.toUpperCase().toCharArray())
        {
            String encodedChar = morseChars.get(new Character(character));

            if (encodedChar == null)
            {
                System.out.println("Warning: skipping: " + character);
            } else
            {
                encodedWord += encodedChar + " ";
            }
        }

        return encodedWord;
    }

    /**
     * Loads all of the morse characters from the file
     *
     * @param fileName The filename of the morse character file
     * @return The map of morse characters
     */
    private static Map<Character, String> loadMorseCharacters(String fileName)
    {
        Map<Character, String> morseChars = new LookupTable<>();

        try (Scanner in = new Scanner(new File(fileName)))
        {
            while (in.hasNext())
            {
                String character = in.next();
                String morse = in.next();

                morseChars.put(character.charAt(0), morse);
            }
        } catch (FileNotFoundException e)
        {
            System.err.println("The file specified could not be found.");
        }

        return morseChars;
    }

    /**
     * Gets the input from a user
     *
     * @param question The question to ask the user
     * @return The response from the user
     */
    private static String getInput(String question)
    {
        System.out.print(question + ": ");

        return input.next();
    }

    public static void main(String[] args)
    {
        new MorseEncoder();
    }
}
