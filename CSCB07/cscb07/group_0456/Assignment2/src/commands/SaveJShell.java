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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import system.FileSystem;
import system.JPath;
import system.Path;

/**
 * Command for restoring the saved session of the JShell in file given by argument 
 * in real file system to JShell.
 * 
 */
public class SaveJShell extends Command {

  /**
   * The exact number of arguments required to execute this command.
   */
  private static final int EXACT_ARG_LENGTH = 1;
  
  /**
   * string containing all characters forbidden in file name.
   */
  private static final String FORB_CHAR = ".*[/.!@#$%^&*(){}~|<>?].*";
  
  /**
   * Creates a new SaveJShell instance and initializes the command name and docs.
   */
  public SaveJShell() {
    this.setName("saveJShell");
    this.setDocs("saveJShell FileName\n\tSave the session of the JShell"
        + "in FileName.");
  }
  
  /**
   * Create a file with name given by arguments in real file system. Save the session 
   * of the JShell in it. InvalidParamLengthException is thrown if there are not 
   * exactly EXACT_ARG_LENGTH arguments provided. IOException is thrown if file path
   * is invalid. InvalidPathException is thrown if the path of the file is invalid.
   * 
   * @param fs the FileSystem associated with the shell this command is running in
   * @param arguments the list of arguments provided by the user
   * @return The output of the command
   * @throws InvalidParamLengthException If there are not exactly EXACT_ARG_LENGTH arguments
   *         provided.
   * @throws IOException If file path is invalid.
   * @throws InvalidPathException If the path of the file is invalid.
   */
  public String executeCommand(FileSystem fs, ArrayList<String> arguments)
      throws InvalidParamLengthException {
    if (arguments.size() != EXACT_ARG_LENGTH) {
      throw new InvalidParamLengthException("Invalid parameter length");
    } 
    try {
      checkValidName(arguments.get(0));
      Path root = new JPath("");
      fs.changeDirectory(root);
      String realCurrDir = System.getProperty("user.dir");
      FileOutputStream fileOut = new FileOutputStream(realCurrDir 
          + arguments + ".ser");
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(fs);
      out.close();
      fileOut.close();
    } catch (IOException e) {
      System.err.println(e.getMessage());
    } catch (InvalidPathException e) {
      System.err.println(e.getMessage());
    }
    return "";
  }
  
  /**
   * Check if fileNmae is valid. InvalidPathException is thrown if file name
   * is invalid.
   * 
   * @param fileName file name to check
   * @throws InvalidPathException If file name is invalid.
   */
  private void checkValidName(String fileName) throws InvalidPathException {
    if (fileName.matches(FORB_CHAR)) {
      throw new InvalidPathException("Invalid File Name");
    }
  }
}
