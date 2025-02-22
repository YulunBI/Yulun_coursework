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

package system;

import exceptions.AlreadyExistsException;
import java.util.ArrayList;
import javax.naming.InvalidNameException;

/**
 * Interface which represents a Directory.
 */
public interface Directory {

  /**
   * Returns a string containing the name of this directory.
   * 
   * @return name A string containing the name of this directory.
   */
  public String getName();


  /**
   * Returns a list of all the child directories contained in this directory.
   * 
   * @return subDirectory Containing all this directory's children.
   */
  public ArrayList<Directory> getChildren();


  /**
   * Returns a list of all the files contained in this directory.
   * 
   * @return subFile Containing all this directory's files.
   */
  public ArrayList<File> getFile();

  
  /**
   * Removes a file from this directory's children files.
   * 
   * @param f Target File to be removed.
   */
  public void removeChild(File f);

  /**
   * Removes a directory from this directory's children directories.
   * 
   * @param d Target Directory to be removed.
   */
  public void removeChild(Directory d);
  
  
  /**
   * Makes this directory a copy of newDir, including recursively copying all its children.
   * 
   * @param newDir The directory to copy to this directory.
   */
  public void replaceThis(Directory newDir);

  /**
   * Adds a Directory to the list of this Directory's subdirectories, named subDictName. Throws
   * InvalidNameException if subDictName contains invalid characters or formatting for a directory
   * name. Throws AlreadyExistsException if this directory already has a subdirectory named
   * subDictName.
   * 
   * @param subDictName A string representing the name of the proposed subdirectory.
   * @throws AlreadyExistsException If this directory already has a subdirectory named subDictName.
   * @throws InvalidNameException If subDictName contains invalid characters or 
   *        formatting for a directory name.
   */
  public void addSubDictionary(String subDictName)
      throws InvalidNameException, AlreadyExistsException;

  /**
   * Adds a File to the list of this Directory's subfiles, named subFileName.
   * Throws AlreadyExistsException if this directory already has a subfile named subFileName.
   * Throws InvalidNameException if subFileName contains invalid characters or formatting for a
   * file name.
   * 
   * @param subFileName A string representing the name of the proposed subfile.
   * @throws AlreadyExistsException If this directory already has a subfile named subFileName.
   * @throws InvalidNameException If subFileName contains invalid characters or 
   *        formatting for a file name.
   */
  public void addSubFile(String subFileName) throws AlreadyExistsException, InvalidNameException;

  /**
   * Returns a Directory representing this directory's parent.
   * 
   * @return superDirectory A Directory object representing this directory's parent.
   */
  public Directory getParent();

  /**
   * Returns a String containing the full path of this directory.
   * 
   * @return location.toString() A String containing the full path of this directory.
   */
  public String getPath();

  /**
   * Returns a specific Directory from this directory's subdirectories, as specified by name. If no
   * subdirectory is found with a matching name, returns null.
   * 
   * @param name A String representing the name of the subdirectory to find.
   * @return d The specific Directory with a matching name to be returned.
   */
  public Directory getSubDirectory(String name);
 
    
}