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
import system.File;
import system.FileSystem;
import system.JPath;
import system.Path;

/**
 * Command for printing contents of file(s) to Shell.
 * 
 */
public class Concatenate extends Command {

  /**
   * The minimum number of arguments required to execute this command.
   */
  private static final int MIN_ARG_LENGTH = 1;

  /**
   * Creates a new Concatenate instance and initializes the command name and docs.
   */
  public Concatenate() {
    this.setName("cat");
    this.setDocs("cat FILE1 [FILE2 ...]\n\tDisplay the contents of FILE1 and other files "
        + "concatenated in the shell. Each file's contents is separated by three line breaks.");
  }

  /**
   * Prints the contents of the files with path specified by arguments into the console. The user
   * can enter any number of file path, each separated by space, to have the contents of the file
   * printed out in the console and with each file separated by three newlines.
   * 
   * @param fs the FileSystem associated with the shell this command is running in
   * @param arguments the list of arguments provided by the user
   * @return The output of the command
   * @throws InvalidParamLengthException If there are less than MIN_ARG_LENGTH arguments provided.
   * @throws InvalidPathException If path specified by argument is invalid.
   */
  public String executeCommand(FileSystem fs, ArrayList<String> arguments)
      throws InvalidParamLengthException {
    if (arguments.size() < MIN_ARG_LENGTH) {
      throw new InvalidParamLengthException("Invalid parameter length");
    }

    String output = "";
    try {

      for (String path : arguments) {

        Path p = new JPath(path);
        File f = fs.findFile(p);

        if (f == null) {
          throw new InvalidPathException("Path is invalid.");
        }

        output += f.getText() + "\n\n\n";
      }

    } catch (InvalidPathException e) {
      System.err.println(e.getMessage());
    }
    
    return output;
  }

}

