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
import exceptions.InvalidParamLengthException;
import java.util.ArrayList;
import system.FileSystem;

/**
 * Command for printing out documentation for a command or list of commands.
 * 
 */
public class Manual extends Command {

  /**
   * The exact number of arguments required to execute this command.
   */
  private static final int EXACT_ARG_LENGTH = 1;
  private CommandHashTable comTable;

  /**
   * Creates an instance of the Manual object and initializes the HashTable, command name, and docs.
   */
  public Manual() {
    this.setName("man");
    this.setDocs("man CMD\n\tPrint documentation for CMD.");
    comTable = new CommandHashTable();
  }

  /**
   * Prints out the documentation for a command into the console as specified by arguments.
   * InvalidCommandNameException is thrown if the user provides an invalid command name.
   * InvalidParamLengthException is thrown if there are not exactly EXACT_ARG_LENGTH
   * arguments provided.
   * 
   * @param fs The FileSystem associated with the shell this command is running in.
   * @param arguments List of arguments provided by the user. Should be a single command name.
   * @return The output of the command
   * @throws InvalidCommandNameException If the user provides an invalid command name.
   * @throws InvalidParamLengthException If there are not exactly EXACT_ARG_LENGTH arguments.
   */
  public String executeCommand(FileSystem fs, ArrayList<String> arguments)
      throws InvalidParamLengthException {

    String output = "";

    if (arguments.size() != EXACT_ARG_LENGTH) {
      throw new InvalidParamLengthException("Invalid parameter length");
    }
    try {
      for (String s : arguments) {
        Command c = comTable.findCommand(s); // create temp command object, this is probably bad
        output += c.getDocs() + "\n";
      }
    } catch (InvalidCommandNameException e) {
      System.err.println(e.getMessage());
    }

    return output;
  }


}
