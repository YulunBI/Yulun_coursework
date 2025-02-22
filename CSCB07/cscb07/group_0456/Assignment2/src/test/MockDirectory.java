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

package test;

import exceptions.AlreadyExistsException;
import java.util.ArrayList;
import javax.naming.InvalidNameException;
import system.Directory;
import system.File;

public class MockDirectory implements java.io.Serializable, Directory {

  /**
   * serialVersionUID for MockDirectory.
   */
  private static final long serialVersionUID = 1144749084345404516L;

  /**
   * The name of the directory.
   */

  private String name;

  /**
   * The path of the directory.
   */
  private String path;

  /**
   * The children of the directory.
   */
  private ArrayList<Directory> children;

  /**
   * The Files of the directory.
   */
  private ArrayList<File> file;

  public MockDirectory(String name, String path) {
    this.name = name;
    this.path = path;
    children = new ArrayList<Directory>();
    file = new ArrayList<File>();
  }


  @Override
  public String getName() {
    return name;
  }

  @Override
  public ArrayList<Directory> getChildren() {
    return children;
  }

  @Override
  public ArrayList<File> getFile() {
    return file;
  }

  @Override
  public void removeChild(File f) {
    // TODO Auto-generated method stub

  }

  @Override
  public void removeChild(Directory d) {
    // TODO Auto-generated method stub

  }

  @Override
  public void addSubDictionary(String subDictName)
      throws InvalidNameException, AlreadyExistsException {

    Directory mock = new MockDirectory(subDictName, "Valid Path");
    this.children.add(mock);

  }

  @Override
  public void addSubFile(String subFileName) throws AlreadyExistsException, InvalidNameException {
    file.add(new MockFile(subFileName, "Valid Path"));

  }

  @Override
  public Directory getParent() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getPath() {
    return path;
  }

  @Override
  public Directory getSubDirectory(String name) {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public void replaceThis(Directory newDir) {
    // TODO Auto-generated method stub
    
  }

}
