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
import system.Directory;
import system.File;
import system.FileSystem;
import system.JPath;
import system.Path;

/**
 * Command for copying files or directories to new or existing locations.
 */
public class Copy extends Command {
  
  /**
   * The exact number of arguments required to execute this command.
   */
  private static final int EXACT_ARG_LENGTH = 2;

  /**
   * Create new instance of a Copy object with initial name and docs.
   */
  public Copy() {
    this.setName("cp");
    this.setDocs("cp OLDPATH NEWPATH\n\tCopy file/directory at OLDPATH to NEWPATH."
        + "\n\tOLDPATH and NEWPATH can be either relative to the current directory or full paths."
        + "\n\tIf NEWPATH is a directory, copy OLDPATH into that directory.");
  }
  
  /**
   * Copy the file/directory specified by first argument to location specified at second argument.
   * Can rename copied file/directory by specifying a non-existent file/directory name at target
   * location, though only one directory in the path can be non-existent.
   * If copying file and file exists at target, overwrite existing file with copied file.
   * Will also copy the contents of a directory recursively.
   * Throws InvalidParamLengthException when number of args is given is not EXACT_ARG_LENGTH.
   * 
   * @param fs The FileSystem associated with the shell this command is running in
   * @param arguments The list of arguments provided by the user
   * @return Returns empty string as this command has no text output
   * @throws InvalidParamLengthException if there are not EXACT_ARG_LENGTH arguments provided.
   */
  public String executeCommand(FileSystem fs, ArrayList<String> arguments)
      throws InvalidParamLengthException {
    if (arguments.size() != EXACT_ARG_LENGTH) {
      throw new InvalidParamLengthException("Invalid parameter length");   
    }
    try {
      Path oldPath = new JPath(arguments.get(0)); 
      if (fs.findFile(oldPath) != null) { //copying a file        
        copyFile(fs, arguments);    
      } else if (fs.findDirectory(oldPath) != null) { //copying a directory        
        copyDirectory(fs, arguments);           
      }
    } catch (InvalidPathException e) {
      System.err.println(e.getMessage());
    }    
    return "";
  }
  
  /**
   * Copy file specified by first argument to location specified at second argument.
   * If target is a directory, copy file into that directory.
   * If target is a file, overwrite target with copied file.
   * If target does not exist, treat the last member of target path as a new name to give copied
   * file. Will only work if the rest of the path is valid.
   * 
   * @param fs The FileSystem associated with the shell this command is running in
   * @param arguments The list of arguments provided by the user
   */
  private void copyFile(FileSystem fs, ArrayList<String> arguments) {
    Path oldPath = new JPath(arguments.get(0));
    Path newPath = new JPath(arguments.get(1));
    File f = null;
    try {
      try {
        f = fs.findFile(oldPath);
        if (fs.findFile(newPath) != null) { // case 6: overwrite target with f
          overwriteThis(fs, newPath, f);            
        } else if (fs.findDirectory(newPath) != null) { // case 1: move f into newPath
          newPath = new JPath("./" + arguments.get(1) + "/" + f.getName());
          copyThis(fs, newPath, f);
        }          
      } catch (InvalidPathException e1) { // when no file/dir exists at newpath          
        copyThis(fs, newPath, f); // case 5: rename f if possible
      }
    } catch (AlreadyExistsException e) {
      System.err.println(e.getMessage());
    } catch (InvalidNameException e) {
      System.err.println(e.getMessage());
    } catch (InvalidPathException e) {
      System.err.println(e.getMessage());
    }
  }
  
  /**
   * Copy directory specified by first argument to location specified at second argument.
   * If target is a directory, copy directory into that directory.
   * If target is a file, sends an error message.
   * If target does not exist, treat the last member of target path as a new name to give copied
   * directory. Will only work if the rest of the path is valid.
   * 
   * @param fs The FileSystem associated with the shell this command is running in
   * @param arguments The list of arguments provided by the user
   */
  private void copyDirectory(FileSystem fs, ArrayList<String> arguments) {
    Path oldPath = new JPath(arguments.get(0));
    Path newPath = new JPath(arguments.get(1));
    Directory d = null;
    try {
      checkIllegalDirectory(fs, arguments);
      try {
        d = fs.findDirectory(oldPath);
        if (fs.findFile(newPath) != null) {
          throw new IllegalArgumentException("Can't move directory into a file");
        } else if (fs.findDirectory(newPath) != null) { // case 2: move d into newPath
          newPath = new JPath("./" + arguments.get(1) + "/" + d.getName());
          copyThis(fs, newPath, d);
        }
      } catch (InvalidPathException e1) { // when no file/dir exists at newpath              
        copyThis(fs, newPath, d); // case 3: rename d if possible
      }
    } catch (InvalidPathException e) {
      System.err.println(e.getMessage());
    } catch (IllegalArgumentException e) {
      System.err.println(e.getMessage());
    } catch (AlreadyExistsException e) {
      System.err.println(e.getMessage());      
    } catch (InvalidNameException e) {
      System.err.println(e.getMessage());
    }
  }
  
  /**
   * Checks if the directory specified by the first argument is valid to be copied.
   * A directory is invalid if it's the root or
   * the copy action involves copying a parent into its child.
   * 
   * @param fs The FileSystem associated with the shell this command is running in
   * @param arguments The list of arguments provided by the user
   * @throws InvalidPathException if directory cannot be found.
   * @throws IllegalArgumentException if directory is not valid to be copied.
   */
  private void checkIllegalDirectory(FileSystem fs, ArrayList<String> arguments)
      throws InvalidPathException, IllegalArgumentException {
    Path oldPath = new JPath(arguments.get(0));
    Path newPath = new JPath(arguments.get(1));
    Directory d = fs.findDirectory(oldPath);
    /*if (fs.getDirectory().getPath().toString().contains(oldPath.toString()))
      throw new IllegalArgumentException("Can't copy current working directory");*///this seems ok
    if (d.equals(fs.getRoot())) {
      throw new IllegalArgumentException("Can't copy the root directory");  
    } else if (newPath.toString().contains(oldPath.toString())) {
      throw new IllegalArgumentException("Can't copy parent to child directory");  
    }    
  }
  
  /**
   * Overwrites contents of file at targetPath with contents of oldFile.
   * 
   * @param fs The FileSystem associated with the shell this command is running in
   * @param targetPath Path leading to the file to be overwritten.
   * @param oldFile Original file to be copied to file at targetPath.
   * @throws InvalidPathException if file is not found at targetPath.
   */
  private void overwriteThis(FileSystem fs, Path targetPath, File oldFile)
      throws InvalidPathException {
    File target = fs.findFile(targetPath);
    target.setText(oldFile.getText());
  }
  
  /**
   * Copies file oldFile to location specified by targetPath.
   * 
   * @param fs The FileSystem associated with the shell this command is running in
   * @param targetPath Path leading to the location of the copied file.
   * @param oldFile Original file to be copied to location at targetPath.
   * @throws InvalidPathException if file cannot be created at targetPath.
   * @throws InvalidNameException if name for copied file is not valid.
   * @throws AlreadyExistsException if file already exists at targetPath.
   */
  private void copyThis(FileSystem fs, Path targetPath, File oldFile) 
      throws InvalidPathException, InvalidNameException, AlreadyExistsException {    
    fs.addFile(targetPath);
    File target = fs.findFile(targetPath);
    target.setText(oldFile.getText());
  }
  
  /**
   * Copies directory oldDir to location specified by targetPath.
   * 
   * @param fs The FileSystem associated with the shell this command is running in
   * @param targetPath Path leading to the location of the copied directory.
   * @param oldDir Original directory to be copied to location at targetPath.
   * @throws InvalidPathException if directory cannot be created at targetPath.
   * @throws InvalidNameException if name for copied directory is not valid.
   * @throws AlreadyExistsException if directory already exists at targetPath.
   */
  private void copyThis(FileSystem fs, Path targetPath, Directory oldDir) 
      throws InvalidPathException, InvalidNameException, AlreadyExistsException {
    fs.addDirectory(targetPath);
    Directory target = fs.findDirectory(targetPath);
    target.replaceThis(oldDir);
  }

}
