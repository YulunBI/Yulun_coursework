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
import exceptions.InvalidPathException;
import java.util.ArrayList;
import system.FileSystem;
import system.JPath;
import system.Path;

/**
 * Command for changing the current working directory in the FileSystem.
 * 
 */
public class ChangeDirectory extends Command {

  /**
   * The number of arguments required to execute this command.
   */
  private static final int EXACT_ARG_LENGTH = 1;

  /**
   * Constructs a new ChangeDirectory instance.
   */
  public ChangeDirectory() {
    this.setName("cd");
    this.setDocs("cd DIR\n\tChange current directory to DIR. Can be full or relative path."
        + "\n\t'..' refers to parent directory.\n\t'.' refers to current directory.");
  }

  /**
   * Changes the current Directory of fs if the path specified by the first element of arguments is
   * valid. InvalidParamLengthException is thrown if there are not exactly EXACT_ARG_LENGTH
   * arguments provided.
   * 
   * @param fs the FileSystem associated with the shell this command is running in
   * @param arguments the list of arguments provided by the user
   * @return The output of the command
   * @throws InvalidParamLengthException If there are not exactly EXACT_ARG_LENGTH arguments
   *         provided.
   */
  public String executeCommand(FileSystem fs, ArrayList<String> arguments)
      throws InvalidParamLengthException {
    if (arguments.size() != EXACT_ARG_LENGTH) {
      throw new InvalidParamLengthException("Invalid parameter length");
    }

    Path changedPath = new JPath(arguments.get(0));

    try {
      fs.changeDirectory(changedPath);
    } catch (InvalidPathException e) {
      System.err.println(e.getMessage());
    }

    return "";
  }

}
