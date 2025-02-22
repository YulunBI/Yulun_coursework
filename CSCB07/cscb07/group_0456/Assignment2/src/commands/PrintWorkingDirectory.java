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
 * Command for printing full path of current working directory to Shell.
 * 
 */
public class PrintWorkingDirectory extends Command {
  /**
   * The number of arguments required to execute this command.
   */
  private static final int EXACT_ARG_LENGTH = 0;

  /**
   * Creates an instance of the PrintWorkingDirectory object and initializes the command name and
   * docs.
   */
  public PrintWorkingDirectory() {
    this.setName("pwd");
    this.setDocs("pwd\n\tPrints full path of current working directory.");
  }



  /**
   * Prints the current working directory of fs if arguments is of size EXACT_ARG_LENGTH. Otherwise
   * InvalidParamLengthException is thrown.
   * 
   * @param fs The FileSystem associated with the shell this command is running in.
   * @param arguments The list of arguments provided by the user.
   * @return The output of the command
   * @throws InvalidParamLengthException If arguments is not of size EXACT_ARG_LENGTH.
   */
  public String executeCommand(FileSystem fs, ArrayList<String> arguments)
      throws InvalidParamLengthException {
    if (arguments.size() != EXACT_ARG_LENGTH) {
      throw new InvalidParamLengthException("Invalid parameter length");
    }


    return fs.getDirectory().getPath() + "\n";

  }

}
