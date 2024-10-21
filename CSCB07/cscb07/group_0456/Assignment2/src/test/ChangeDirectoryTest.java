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
import static org.junit.Assert.fail;

import commands.ChangeDirectory;
import exceptions.InvalidParamLengthException;
import java.util.ArrayList;
import org.junit.Test;
import system.FileSystem;

public class ChangeDirectoryTest {

  /**
   * Checks for InvalidParamLengthException when param length is invalid.
   */
  @Test
  public void testInvalidParamLengthExecuteCommand() {
    try {

      ChangeDirectory cd = new ChangeDirectory();
      ArrayList<String> arg = new ArrayList<>();
      arg.add("1");
      arg.add("2");

      cd.executeCommand(new MockFileSystem(), arg);
      fail("InvalidParamLengthException not caught");

    } catch (InvalidParamLengthException e) {
      return;
    }

  }

  /**
   * Changing the directory with an valid path.
   */
  @Test
  public void testValidPathExecuteCommand() {
    ChangeDirectory cd = new ChangeDirectory();
    ArrayList<String> arg = new ArrayList<>();
    FileSystem mfs = new MockFileSystem();

    arg.add("Valid Path");
    try {
      cd.executeCommand(mfs, arg);
    } catch (InvalidParamLengthException e) {
      fail(e.getMessage());
    }

    assertEquals("Changed Directory", mfs.getDirectory().getName());
  }

  /**
   * Changing the directory with an invalid path.
   */
  @Test
  public void testInvalidPathExecuteCommand() {
    ChangeDirectory cd = new ChangeDirectory();
    ArrayList<String> arg = new ArrayList<>();
    FileSystem mfs = new MockFileSystem();

    arg.add("Invalid Path");
    try {
      cd.executeCommand(mfs, arg);
    } catch (InvalidParamLengthException e) {
      fail(e.getMessage());
    }

    assertEquals("Old CD", mfs.getDirectory().getName());
  }


}
