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

import commands.Concatenate;
import exceptions.InvalidParamLengthException;
import java.util.ArrayList;
import org.junit.Test;

public class ConcatenateTest {

  /**
   * Checks for InvalidParamLengthException when param length is invalid.
   */
  @Test
  public void testInvalidParamLengthExecuteCommand() {
    try {

      Concatenate cat = new Concatenate();
      ArrayList<String> arg = new ArrayList<>();

      cat.executeCommand(new MockFileSystem(), arg);
      fail("InvalidParamLengthException not caught");

    } catch (InvalidParamLengthException e) {
      return;
    }

  }

  /**
   * Concatenate a single File with a valid path.
   */
  @Test
  public void testValidPathExecuteCommand() throws InvalidParamLengthException {


    Concatenate cat = new Concatenate();
    ArrayList<String> arg = new ArrayList<>();

    arg.add("Valid Path");

    String output = cat.executeCommand(new MockFileSystem(), arg);

    assertEquals("Hello World\n\n\n", output);


  }

  /**
   * Concatenate a single File with a invalid path.
   */
  @Test
  public void testInvalidPathExecuteCommand() throws InvalidParamLengthException {


    Concatenate cat = new Concatenate();
    ArrayList<String> arg = new ArrayList<>();

    arg.add("Invalid Path");

    String output = cat.executeCommand(new MockFileSystem(), arg);

    System.out.println(output);
    assertEquals("", output);

  }

  /**
   * Concatenate multiple Files, all with valid paths.
   */
  @Test
  public void testMultipleValidPathExecuteCommand() throws InvalidParamLengthException {


    Concatenate cat = new Concatenate();
    ArrayList<String> arg = new ArrayList<>();

    arg.add("Valid Path");
    arg.add("Valid Path");
    arg.add("Valid Path");

    String output = cat.executeCommand(new MockFileSystem(), arg);
    assertEquals("Hello World\n\n\nHello World\n\n\nHello World\n\n\n", output);

  }

  /**
   * Concatenate multiple Files, with an invalid path in between.
   */
  @Test
  public void testInvalidPathInMiddleExecuteCommand() throws InvalidParamLengthException {


    Concatenate cat = new Concatenate();
    ArrayList<String> arg = new ArrayList<>();

    arg.add("Valid Path");
    arg.add("Invalid Path");
    arg.add("Valid Path");

    String output = cat.executeCommand(new MockFileSystem(), arg);
    assertEquals("Hello World\n\n\n", output);

  }

}
