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

import exceptions.AlreadyExistsException;
import exceptions.InvalidParamLengthException;
import exceptions.InvalidPathException;
import java.util.ArrayList;
import javax.naming.InvalidNameException;
import system.FileSystem;
import system.JPath;
import system.Path;

/**
 * Command for creating new directories in FileSystem.
 * 
 */
public class MakeDirectory extends Command {

  /** 
    * The number of arguments required to execute this command.
    */
  private static final int MIN_ARG_LENGTH = 1;

  /**
   * Creates a new MakeDirectory instance and initializes the command name and docs.
   */
  public MakeDirectory() {
    this.setName("mkdir");
    this.setDocs("mkdir DIR1 DIR2 ...\n\tCreate directories. May be specified with full paths "
        + "or relative paths.\n\t"
        + "Will not create subsequent directories if any DIR creation fails.");
  }

  /** 
   * Creates new Directories in fs given by arguments. If the Directory being created 
   * has an invalid name, already exist or the path specified by the argument is invalid,
   * the Directory isn't created and the method terminates. InvalidParamLengthException is 
   * thrown if there are less than MIN_ARG_LENGTH arguments provided.
   * 
   * @param fs the FileSystem associated with the shell this command is running in
   * @param arguments list of arguments provided by the user, should be more than
   *        MIN_ARG_LENGTH
   * @return The output of the command
   * @throws InvalidParamLengthException If there are not more than MIN_ARG_LENGTH arguments
   *        provided.
   */
  public String executeCommand(FileSystem fs, ArrayList<String> arguments)
      throws InvalidParamLengthException {
    if (arguments.size() < MIN_ARG_LENGTH) {
      throw new InvalidParamLengthException("Invalid parameter length");
    }

    this.setArguments(arguments);
    try {
      for (String s : arguments) {
        Path p = new JPath(s);
        fs.addDirectory(p);
      }
    } catch (InvalidPathException | InvalidNameException | AlreadyExistsException e) {
      System.err.println(e.getMessage());
    }

    return "";
  }

}
