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
import exceptions.InvalidPathException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;
import javax.naming.InvalidNameException;
import system.Directory;
import system.File;
import system.FileSystem;
import system.Path;

public class MockFileSystem implements java.io.Serializable, FileSystem {

  /**
   * serialVersionUID for MockFileSystem.
   */
  private static final long serialVersionUID = 7747436255203834095L;

  /**
   * Represents the current Directory.
   */
  private Directory currentDir;

  private static FileSystem fs = null;
  /**
   * A list to store command history.
   */
  private ArrayList<String> history;
  
  /**
   * Represents the directory stack.
   */
  private Stack<Directory> dirStack;
  
  /**
   * Represents the root Directory.
   */
  private Directory root;
  
  public MockFileSystem() {
    currentDir = new MockDirectory("Old CD", "CD Path");
    root = currentDir;
    history = new ArrayList<String>();
    dirStack = new Stack<Directory>();
  }


  public FileSystem createFileSystemInstance(FileSystem nfs, int i) {
    if (fs == null && i == 0) {
      fs = new MockFileSystem();
    } else if (fs == null && i == 1 && nfs != null) {
      fs = nfs;
    }
    return fs;
  }
  
  @Override
  public String recursiveList(Directory dir, int counter, Boolean printPath) {
    if (printPath) {
      return "Tree With Paths";
    }

    return "Normal Tree";
  }

  @Override
  public void addFile(Path path)
      throws InvalidPathException, InvalidNameException, AlreadyExistsException {
    return;
  }

  @Override
  public void addDirectory(Path path)
      throws InvalidPathException, InvalidNameException, AlreadyExistsException {
    if (path.getList().get(1).equals("Invalid Path")) {
      throw new InvalidPathException("Invalid Path");
    }

    if (path.getList().get(1).startsWith("Valid Path")) {
      currentDir.addSubDictionary(path.getList().get(1));
    }

    if (path.getList().get(1).startsWith("Path With Invalid Name")) {
      throw new InvalidNameException("Invalid Name");
    }

    if (path.getList().get(1).startsWith("Path That Already Exists")) {
      throw new InvalidNameException("Already Exists");
    }



  }

  @Override
  public File findFile(Path path) throws InvalidPathException {
    if (path.getList().get(1).equals("Invalid Path")) {
      throw new InvalidPathException("Invalid Path");
    }

    if (path.getList().get(1).equals("Valid Path")) {
      File f = new MockFile("Valid File", "Valid Path");
      f.setText("Hello World");
      return f;
    }

    return null;
  }

  @Override
  public Directory findDirectory(Path path) throws InvalidPathException {
    if (path.getList().get(0).isBlank()) {
      Directory d = new MockDirectory("Valid Directory", "Valid Path");
      return d;
    }
    if (path.getList().get(1).equals("Invalid Path")) {
      throw new InvalidPathException("Invalid Path");
    }

    if (path.getList().get(1).equals("Valid Path")) {
      Directory d = new MockDirectory("Valid Directory", "Valid Path");
      return d;
    }

    if (path.getList().get(1).equals("Non Empty Valid Path")) {
      Directory d = new MockDirectory("Valid Directory", "Valid Path");
      try {
        d.addSubDictionary("Child 1");
        d.addSubDictionary("Child 1");
        d.addSubDictionary("Child 2");
        d.addSubDictionary("Child 3");
        d.addSubFile("File 1");
      } catch (InvalidNameException | AlreadyExistsException e) {
        return null;
      }
      return d;
    }
    return null;
  }

  @Override
  public void changeDirectory(Path path) throws InvalidPathException {

    if (path.getList().get(1).equals("CD Path")) {
      currentDir = root;
    }
    if (path.getList().get(1).equals("Invalid Path")) {
      throw new InvalidPathException("Invalid Path");
    }

    if (path.getList().get(1).equals("Valid Path")) {
      currentDir = new MockDirectory("Changed Directory", "Valid Path");
    }

  }

  @Override
  public Directory getDirectory() {
    return currentDir;
  }

  @Override
  public ArrayList<String> getHistory() {    
    return history;
  }

  @Override
  public void addHistory(String s) {
    history.add(s);
  }

  @Override
  public void stackPush(Directory d) {
    dirStack.push(d);
  }

  @Override
  public Directory stackPop() {
    Directory d = null;
    try {
      d = dirStack.pop();
    } catch (EmptyStackException e) {
      System.err.println("No directory currently in stack");
    }
    return d;
  }

  @Override
  public void redirect(String specifier, String text, String file) throws InvalidNameException,
      InvalidPathException, AlreadyExistsException, IllegalArgumentException {
    if (file.startsWith("Invalid File Name")) {
      throw new InvalidNameException();
    }

    if (file.startsWith("Invalid Parent Path")) {
      throw new InvalidPathException("");
    }

    return;

  }

  @Override
  public Directory getRoot() {
    // TODO Auto-generated method stub
    return root;
  }

}
