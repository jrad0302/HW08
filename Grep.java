// I worked on the homework assignment alone, using only course materials.

import java.util.Scanner;
import java.io.File;
import java.io.IOException;

/**
* This class acts as a Grep tool.
* @author Johnathan Radcliff
* @version 1.0
*/
public class Grep {
    private static String finalString = new String("");
    /**
    * This method runs the Grep method.
    * @param args the command-line arguments the user inputs to the program.
    */
    public static void main(String[] args) {
        // if there are more than 3 args
        if (args.length > 3) {
            throw new IllegalArgumentException("Number of arguments exceeded maximum allowed.");
        // if there are only two args
        } else if (args.length == 2) {
            // try running grep, ready to catch the two exceptions
            try {

                // fix the directory to be absolute
                if (!args[1].contains("C:")) {
                    String dir = new File("").getAbsolutePath();
                    String temp = args[1];
                    dir += temp;
                    args[1] = temp;
                }
                File f = new File(args[1]);
                System.out.print(grep(f, args[0], true));
            } catch (InvalidSearchStringException iss) {
                System.out.println("Search string cannot have a newline character");
            } catch (IOException io) {
                System.out.println("Uh oh! Looks like one of the directories in the folder didn't exist. Goodbye!");
            }
        // if there are three args
        } else if (args.length == 3) {
            // if the first one is the correct argument
            if (args[0].equals("-i")) {
                // try running grep, ready to catch the two exceptions
                try {

                    // fix the directory to be absolute
                    if (!args[2].contains("C:")) {
                        String dir = new File("").getAbsolutePath();
                        String temp = args[2];
                        dir += temp;
                        args[2] = temp;
                    }

                    File f = new File(args[2]);
                    System.out.print(grep(f, args[1], false));
                } catch (InvalidSearchStringException iss) {
                    System.out.println("Search string cannot have a newline character");
                } catch (IOException io) {
                    System.out.println("Uh oh! Looks like one of the directories in the folder didn't exist. Goodbye!");
                }

            // throw an exception for illegal arguments
            } else {
                throw new IllegalArgumentException("Please use the correct arguments.");
            }
        }
    }


    /**
    * This method recursively checks all the directories and files for the searched String
    * @param f a File denoting the path of the file/directory
    * @param s a String for which all files are searched
    * @param cs a Boolean denoting case sensitivity
    * @return a String of all the hits for the search
    */
    public static String grep(File f, String s, Boolean cs) throws InvalidSearchStringException, IOException {
        String returned = new String("");
        // catch the newline character
        if (s.indexOf('\n') >= 0) {
            throw new InvalidSearchStringException("Search string cannot have a newline character");
        } else {
            // if the file is a directory, recursive call another grep
            if (f.isDirectory()) {
                File[] files = f.listFiles();
                for (File file : files) {
                    grep(file, s, cs);
                }
                // checking if its a file or a directory checks that it exists as well
            } else if (f.isFile()) {
                // search
                int i = 0;
                String current = "";
                Scanner scan = new Scanner(f);
                while (scan.hasNextLine()) {
                    i++;
                    // if cs is true, make each line and s lowercase
                    if (cs) {
                        current = scan.nextLine().toString();
                    } else {
                        current = scan.nextLine().toLowerCase().toString();
                        s = s.toLowerCase();
                    }

                    // if the current line contains the substring, return the line and number
                    if (current.contains(s)) {
                        String num = Integer.toString(i);
                        finalString += f.getCanonicalPath() + ":" + num + ":" + current + '\n';
                    }
                }
            } else {
                throw new IOException("This file has an error or does not exist");
            }
            return finalString.trim();
        }
    }
}