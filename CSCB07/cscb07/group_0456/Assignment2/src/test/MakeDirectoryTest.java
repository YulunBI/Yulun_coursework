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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import commands.MakeDirectory;
import exceptions.InvalidParamLengthException;
import java.util.ArrayList;
import org.junit.Test;
import system.FileSystem;

public class MakeDirectoryTest {

  /**
   * Checks for InvalidParamLengthException when param length is invalid.
   */
  @Test
  public void testInvalidParamLengthExecuteCommand() {
    try {

      MakeDirectory mkdir = new MakeDirectory();
      ArrayList<String> arg = new ArrayList<>();

      mkdir.executeCommand(new MockFileSystem(), arg);
      fail("InvalidParamLengthException not caught");

    } catch (InvalidParamLengthException e) {
      return;
    }

  }

  /**
   * Make a single Directory with a valid path.
   */
  @Test
  public void testValidPathExecuteCommand() throws InvalidParamLengthException {


    MakeDirectory mkdir = new MakeDirectory();
    ArrayList<String> arg = new ArrayList<>();
    FileSystem mfs = new MockFileSystem();
    arg.add("Valid Path");

    String output = mkdir.executeCommand(mfs, arg);

    assertEquals("", output);
    assertEquals(1, mfs.getDirectory().getChildren().size());
    assertEquals("Valid Path", mfs.getDirectory().getChildren().get(0).getName());


  }

  /**
   * Make a single Directory with a invalid path.
   */
  @Test
  public void testInvalidPathExecuteCommand() throws InvalidParamLengthException {


    MakeDirectory mkdir = new MakeDirectory();
    ArrayList<String> arg = new ArrayList<>();
    FileSystem mfs = new MockFileSystem();
    arg.add("Invalid Path");

    String output = mkdir.executeCommand(mfs, arg);

    assertEquals("", output);
    assertTrue(mfs.getDirectory().getChildren().isEmpty());


  }

  /**
   * Make a single Directory with a invalid name.
   */
  @Test
  public void testInvalidNameExecuteCommand() throws InvalidParamLengthException {


    MakeDirectory mkdir = new MakeDirectory();
    ArrayList<String> arg = new ArrayList<>();
    FileSystem mfs = new MockFileSystem();
    arg.add("Path With Invalid Name");
    String output = mkdir.executeCommand(mfs, arg);

    assertEquals("", output);
    assertTrue(mfs.getDirectory().getChildren().isEmpty());



  }

  /**
   * Make a single Directory that already exists.
   */
  @Test
  public void testAlreadyExistsExecuteCommand() throws InvalidParamLengthException {


    MakeDirectory mkdir = new MakeDirectory();
    ArrayList<String> arg = new ArrayList<>();
    FileSystem mfs = new MockFileSystem();
    arg.add("Path That Already Exists");
    String output = mkdir.executeCommand(mfs, arg);

    assertEquals("", output);
    assertTrue(mfs.getDirectory().getChildren().isEmpty());



  }

  /**
   * Make a multiple Directories with valid paths.
   */
  @Test
  public void testMultipleValidPathExecuteCommand() throws InvalidParamLengthException {


    MakeDirectory mkdir = new MakeDirectory();
    ArrayList<String> arg = new ArrayList<>();
    FileSystem mfs = new MockFileSystem();

    arg.add("Valid Path");
    arg.add("Valid Path");
    arg.add("Valid Path");

    String output = mkdir.executeCommand(mfs, arg);
    assertEquals("", output);
    assertEquals(3, mfs.getDirectory().getChildren().size());
    assertEquals("Valid Path", mfs.getDirectory().getChildren().get(0).getName());
    assertEquals("Valid Path", mfs.getDirectory().getChildren().get(1).getName());
    assertEquals("Valid Path", mfs.getDirectory().getChildren().get(2).getName());

  }

  /**
   * Make a multiple Directories with an invalid path in between.
   */
  @Test
  public void testInvalidPathInMiddleExecuteCommand() throws InvalidParamLengthException {


    MakeDirectory mkdir = new MakeDirectory();
    ArrayList<String> arg = new ArrayList<>();
    FileSystem mfs = new MockFileSystem();

    arg.add("Valid Path");
    arg.add("Invalid Path");
    arg.add("Valid Path");

    String output = mkdir.executeCommand(mfs, arg);
    assertEquals("", output);
    assertEquals(1, mfs.getDirectory().getChildren().size());
    assertEquals("Valid Path", mfs.getDirectory().getChildren().get(0).getName());
  }



}
