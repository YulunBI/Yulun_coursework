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
 * Command for printing contents of entire file system.
 * 
 */
public class Tree extends Command {

  /**
   * The number of arguments required to execute this command.
   */
  private static final int EXACT_ARG_LENGTH = 0;

  /**
   * Creates a new Tree instance and initializes the command name and docs.
   */
  public Tree() {
    this.setName("tree");
    this.setDocs("tree \n\t Prints the entire file system as a tree");
  }


  /**
   * Returns a tree representation of the entire file system.
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

    String output = "";

    Path root = new JPath("/");
    Directory dir;
    try {
      dir = fs.findDirectory(root);
      output = fs.recursiveList(dir, 0, false);
    } catch (InvalidPathException e) {
      System.err.println(e.getMessage());
    }

    return output;
  }

}
