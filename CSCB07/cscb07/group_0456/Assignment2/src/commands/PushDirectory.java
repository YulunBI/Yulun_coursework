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
import system.Directory;
import system.FileSystem;
import system.JPath;
import system.Path;


/**
 * A command for pushing the current directory on top of the directory stack, then switching to a
 * new directory specified by the user.
 * 
 */
public class PushDirectory extends Command {

  /**
   * The number of arguments required to execute this command.
   */
  private static final int EXACT_ARG_LENGTH = 1;

  /**
   * Creates an instance of the PushDirectory object and initializes the command name and docs.
   */
  public PushDirectory() {
    this.setName("pushd");
    this.setDocs("pushd DIR\n\tPushes current working directory to top of stack, then changes "
        + "working directory to DIR.");
  }

  /**
   * Sends the current directory of fs to the top of the stack stored in fs and changes the new
   * current directory of fs to the directory provided by arguments. InvalidParamLengthException is
   * thrown if there are not exactly EXACT_ARG_LENGTH arguments provided. InvalidPathException is
   * thrown if the proposed new current directory is not valid for fs.
   * 
   * @param fs The FileSystem which will have its current directory changed.
   * @param arguments List of strings provided by user input. Should include only a single path.
   * @return The output of the command
   * @throws InvalidParamLengthException If there are not exactly EXACT_ARG_LENGTH arguments
   *        provided.
   * @throws InvalidPathException If the proposed new current directory is not valid for fs.
   */
  public String executeCommand(FileSystem fs, ArrayList<String> arguments)
      throws InvalidParamLengthException {
    if (arguments.size() != EXACT_ARG_LENGTH) {
      throw new InvalidParamLengthException("Invalid parameter length");
    }

    Directory d = fs.getDirectory(); // store current dir

    Path p = new JPath(arguments.get(0));
    try {
      fs.changeDirectory(p);
      fs.stackPush(d);
    } catch (InvalidPathException e) {
      System.err.println(e.getMessage());
    }

    return "";
  }
}
