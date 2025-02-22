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

import exceptions.InvalidParamLengthException;
import java.util.ArrayList;
import system.FileSystem;

/**
 * Command for returning the history of user's attempted command inputs in the shell.
 * 
 */
public class History extends Command {

  /**
   * The maximum number of arguments required to execute this command.
   */
  private static final int MAX_ARG_LENGTH = 1;

  /**
   * Creates an instance of the History object and initializes the command name and docs.
   */
  public History() {
    this.setName("history");
    this.setDocs("history [number]\n\tPrints out history of last [number] commands."
        + "\n\tIf [number] is not given, print entire history of commands.");
  }

  /**
   * Prints the previous inputs by the user back into the console, with the most recent one
   * displayed last. Prints all previous inputs if no argument is given, or the last n inputs where
   * the given argument is an integer n. InvalidParamLengthException is thrown if there are more
   * than MAX_ARG_LENGTH arguments provided. NumberFormatException is thrown if the provided
   * argument is not an integer.
   * 
   * @param fs The FileSystem which this command will get the history of.
   * @param arguments List of arguments provided by the user. Should be a single integer.
   * @return The output of the command
   * @throws InvalidParamLengthException If there are more than MAX_ARG_LENGTH arguments 
   *        provided.
   * @throws NumberFormatException If the provided argument is not an integer.
   */
  public String executeCommand(FileSystem fs, ArrayList<String> arguments)
      throws InvalidParamLengthException {
    if (arguments.size() > MAX_ARG_LENGTH) {
      throw new InvalidParamLengthException("Invalid parameter length");
    }

    String output = "";

    // by default print all history, otherwise print the number indicated by count
    int count = fs.getHistory().size();
    if (arguments.size() == 1) {
      try {
        count = Math.min(Integer.parseInt(arguments.get(0)), fs.getHistory().size());
      } catch (NumberFormatException e) {
        System.err.println("Invalid input type");
        return "";
      }
    }

    for (int i = fs.getHistory().size() - count; i < fs.getHistory().size(); i++) {
      output += (i + 1) + ".\t " + fs.getHistory().get(i) + "\n";
    }

    return output;
  }

}
