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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import system.FileSystem;
import system.JFileSystem;

/**
 * Command for saving the session of the JShell in file given by argument 
 * in real file system.
 * 
 */
public class LoadJShell extends Command {

  /**
   * The exact number of arguments required to execute this command.
   */
  private static final int EXACT_ARG_LENGTH = 1;
  
  /**
   * string containing all characters forbidden in file name.
   */
  private static final String FORB_CHAR = ".*[/.!@#$%^&*(){}~|<>?].*";
  
  /**
   * Creates a new LoadJShell instance and initializes the command name and docs.
   */
  public LoadJShell() {
    this.setName("loadJShell");
    this.setDocs("loadJShell FileName\n\tLoad the contents of the FileName.\n"
        + "Reinitialize everything that was saved in FileName.\n" 
        + "Only work when no command haven been typed in JShell.");
  }
  
  /**
   * Load the contents in the arguments. Reinitialize everything that was saved 
   * in arguments. Only work when no command haven been typed in JShell. 
   * InvalidParamLengthException is thrown if there are not exactly EXACT_ARG_LENGTH 
   * arguments provided. IOException is thrown if file path is invalid. 
   * ClassNotFoundException is thrown if the file to restore session can't be process. 
   * InvalidPathException is thrown if the path of the file is invalid.
   * 
   * @param fs the FileSystem associated with the shell this command is running in
   * @param arguments the list of arguments provided by the user
   * @return The output of the command
   * @throws InvalidParamLengthException If there are not exactly EXACT_ARG_LENGTH arguments
   *         provided.
   * @throws IOException If file path is invalid.
   * @throws ClassNotFoundException If the file to restore session can't be process.
   * @throws InvalidPathException If the path of the file is invalid.
   */
  public String executeCommand(FileSystem fs, ArrayList<String> arguments)
      throws InvalidParamLengthException {
    if (arguments.size() != EXACT_ARG_LENGTH) {
      throw new InvalidParamLengthException("Invalid parameter length");
    }
    try {
      checkIllegalArgument(fs);
      checkValidName(arguments.get(0));
      String realCurrDir = System.getProperty("user.dir");
      FileInputStream fileIn = new FileInputStream(realCurrDir 
          + arguments + ".ser");
      ObjectInputStream in = new ObjectInputStream(fileIn);
      fs = JFileSystem.createFileSystemInstance((JFileSystem) in.readObject(), 1);
      in.close();
      fileIn.close();
    } catch (IOException e) {
      System.err.println(e.getMessage());
    } catch (ClassNotFoundException e) {
      System.err.println(e.getMessage());
    } catch (InvalidPathException e) {
      System.err.println(e.getMessage());
    }
    return "";
  }
  
  /**
   * Check if fs already initialized. IllegalArgumentException is thrown if fs
   * already initialized.
   * 
   * @param fs the FileSystem associated with the shell this command is running in
   * @throws IllegalArgumentException If fs already initialized.
   */
  private void checkIllegalArgument(FileSystem fs) throws IllegalArgumentException {
    if (fs != null) {
      throw new IllegalArgumentException("FileSystem already initialized");
    }
  }
  
  /**
   * Check if fileName is valid. InvalidPathException is thrown if fileName 
   * is invalid.
   * 
   * @param fileName name of file to check
   * @throws InvalidPathException If fileName is invalid.
   */
  private void checkValidName(String fileName) throws InvalidPathException {
    if (fileName.matches(FORB_CHAR)) {
      throw new InvalidPathException("Invalid File Name");
    }
  }
}
