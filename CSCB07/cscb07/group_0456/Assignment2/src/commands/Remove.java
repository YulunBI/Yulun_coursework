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
 * Command for removing files or directories from the filesystem.
 */
public class Remove extends Command {
  
  /**
   * The exact number of arguments required to execute this command.
   */
  private static final int EXACT_ARG_LENGTH = 1;
   
  /**
   * Creates a new instance of a Remove object with initial name and docs.
   */
  public Remove() {
    this.setName("rm");
    this.setDocs("rm DIR\n\tRemoves directory DIR from the file system, along with\n\t"
         + "any other files or directories inside DIR.");
  }
   
  /**
   * Removes file or directory specified by first argument from the filesystem.
   * Also removes the contents of directory recursively.
   * Can't remove the current working directory or the root directory.
   * 
   * @param fs The FileSystem associated with the shell this command is running in
   * @param arguments The list of arguments provided by the user
   * @throws InvalidParamLengthException if EXACT_ARG_LENGTH args are not given.
   * @throws IllegalArgumentException if directory is not valid to be removed.
   */
  public String executeCommand(FileSystem fs, ArrayList<String> arguments)
      throws InvalidParamLengthException, IllegalArgumentException {
    if (arguments.size() != EXACT_ARG_LENGTH) {
      throw new InvalidParamLengthException("Invalid parameter length");       
    }
   
    try {
      Path p = new JPath(arguments.get(0));
      if (fs.findFile(p) != null) {
        throw new IllegalArgumentException("Can't remove a file");
      } else if (fs.findDirectory(p) != null) {
       
        Directory d = fs.findDirectory(p);
        if (fs.getDirectory().getPath().toString().contains(p.toString())) {
          throw new IllegalArgumentException("Can't remove current working directory");
        } else if (d.equals(fs.getRoot())) {
          throw new IllegalArgumentException("Can't remove the root directory");
        }
       
        //d.removeChildren(); // remove target's children
        Directory par = d.getParent();
        par.removeChild(d); // remove target directory
        // children should get removed by garbage collector eventually
       
      }
    } catch (InvalidPathException e) {
      System.err.println(e.getMessage());
    }
   
    return "";
  }
}
