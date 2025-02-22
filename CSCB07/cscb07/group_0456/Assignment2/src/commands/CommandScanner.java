// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: khanyus7
// UT Student #: 1006330329
// Author: Yusuf Khan
//
// Student2:
// UTORID user_name: wuyulu10
// UT Student #: 1004912785
// Author: Yulun Wu
//
// Student3:
// UTORID user_name: lujia34
// UT Student #: 1006173196
// Author: Jia (Arthur) Lu
//
// Student4:
// UTORID user_name:
// UT Student #:
// Author:
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************

package commands;

import exceptions.InvalidCommandNameException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Scans and Parses command from user input in Shell.
 * 
 */
public class CommandScanner {

  /**
   * Represents the contents inputed by user.
   */
  private String inputString = "";

  /**
   * Represents the command the user desires to execute.
   */
  private String command = "";

  /**
   * Represents the list of contents inputed by user.
   */
  private String[] inputStringList;

  /**
   * Represents the list of parameter(s) user inputed to execute command.
   */
  private ArrayList<String> parameter;

  /**
   * Represents the list of commands which allow quotes.
   */
  private ArrayList<String> quotesAllowed;


  /**
   * Constructs a new CommandScanner instance.
   */
  public CommandScanner() {
    parameter = new ArrayList<String>();
    quotesAllowed = new ArrayList<String>();
    quotesAllowed.add("echo");
  }

  /**
   * Scans the user input from console and return suer input.
   * 
   * @return inputString the String contains user input
   */
  public String scanUserInput() {
    Scanner userInput = new Scanner(System.in);
    inputString = userInput.nextLine();
    return inputString;
  }

  /**
   * Splits inputString into inputStringList with delimiter "\\s+".
   */
  private void divideInput() {
    String regex = "\\s+";

    for (String command : quotesAllowed) {
      if (inputString.startsWith(command)) {
        // only select the spaces that aren't in quotes
        regex = "\\s+(?=(?:\"[^\"]*\"|[^\"])*$)";
        break;
      }
    }
    inputStringList = inputString.split(regex);
  }

  /**
   * Removes any leading and trailing space in inputString.
   */
  private void removeSpacesInInput() {
    String regex = "^\\s+";

    // remove all spaces from the front
    inputString = inputString.replaceAll("^\\s+", "");

    inputString = inputString.replaceAll("\\s*>>\\s*(?=(?:\"[^\"]*\"|[^\"])*$)", " >> ");
    inputString = inputString.replaceAll("\\s*(?<!>)>(?![>])\\s*(?=(?:\"[^\"]*\"|[^\"])*$)", " > ");

    for (String command : quotesAllowed) {
      if (inputString.startsWith(command)) {
        // only select the spaces that aren't in quotes
        regex = "\\s+(?=(?:\"[^\"]*\"|[^\"])*$)";
        break;
      }
    }
    inputString = inputString.replaceAll(regex, " ");
  }

  /**
   * Parses command and parameter(s) from user input.
   */
  public void parseCommandAndParameter() {
    removeSpacesInInput();
    divideInput();
    ArrayList<String> commandAndParameter = new ArrayList<String>(Arrays.asList(inputStringList));
    command = commandAndParameter.get(0);
    commandAndParameter.remove(0);
    parameter = commandAndParameter;
  }

  /**
   * Returns a new Command given by name if it exists. InvalidCommandNameException is thrown if the
   * user provides an invalid command name.
   * 
   * 
   * @param hashTable the CommandHashTable
   * @return the command found in CommandHashTable
   * @throws InvalidCommandNameException If the user provides an invalid command name.
   */
  public Command getCommand(CommandHashTable hashTable) throws InvalidCommandNameException {
    return hashTable.findCommand(this.command);
  }

  /**
   * Returns a ArrayList contains Strings of parameter(s).
   * 
   * @return parameter the ArrayList contains Strings of parameter(s)
   */
  public ArrayList<String> getParameter() {
    return parameter;
  }

  /**
   * Returns a String represents a command name.
   * 
   * @return command the String represents a command name
   */
  public String getName() {
    return command;
  }

}
