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

import commands.Search;
import exceptions.InvalidParamLengthException;
import java.util.ArrayList;
import org.junit.Test;
import system.FileSystem;

public class SearchTest {

  /**
   * Checks for InvalidParamLengthException when parameter length is invalid.
   */
  @Test
  public void testInvalidParamLengthExecuteCommand() {
    try {

      Search s = new Search();
      ArrayList<String> arg = new ArrayList<>();

      s.executeCommand(new MockFileSystem(), arg);
      fail("InvalidParamLengthException not caught");

    } catch (InvalidParamLengthException e) {
      return;
    }

  }

  /**
   * Checks for IllegalArgumentException when some arguments are invalid.
   * 
   * @throws InvalidParamLengthException If param length is invalid.
   */
  @Test
  public void testIllegalArgumentExceptionCommand() throws InvalidParamLengthException {
    try {

      Search s = new Search();
      ArrayList<String> arg = new ArrayList<>();
      arg.add("Valid Path");
      arg.add("-tyyyype");
      arg.add("c");
      arg.add("-naaaame");
      arg.add("\"valid name\"");

      s.executeCommand(new MockFileSystem(), arg);
      fail("IllegalArgumentException was not caught");

    } catch (IllegalArgumentException e) {
      return;
    }

  }

  /**
   * Search for a Directory with a valid path.
   */
  @Test
  public void testValidDirPathExecuteCommand() throws InvalidParamLengthException {


    Search s = new Search();
    ArrayList<String> arg = new ArrayList<>();
    FileSystem mfs = new MockFileSystem();
    arg.add("Non Empty Valid Path");
    arg.add("-type");
    arg.add("d");
    arg.add("-name");
    arg.add("\"Child 1\"");

    String output = s.executeCommand(mfs, arg);
    assertEquals("Valid Path\nValid Path\n", output);


  }

  /**
   * Search for a File with a valid path.
   */
  @Test
  public void testValidFilePathExecuteCommand() throws InvalidParamLengthException {


    Search s = new Search();
    ArrayList<String> arg = new ArrayList<>();
    FileSystem mfs = new MockFileSystem();
    arg.add("Non Empty Valid Path");
    arg.add("-type");
    arg.add("f");
    arg.add("-name");
    arg.add("\"File 1\"");

    String output = s.executeCommand(mfs, arg);
    assertEquals("Valid PathFile 1\n", output);


  }

  /**
   * Search for a File with a invalid path.
   */
  @Test
  public void testInvalidFilePathExecuteCommand() throws InvalidParamLengthException {


    Search s = new Search();
    ArrayList<String> arg = new ArrayList<>();
    FileSystem mfs = new MockFileSystem();
    arg.add("Invalid Path");
    arg.add("-type");
    arg.add("f");
    arg.add("-name");
    arg.add("\"File 1\"");

    String output = s.executeCommand(mfs, arg);
    assertEquals("", output);


  }

  /**
   * Search for a Directory with a invalid path.
   */
  @Test
  public void testInvalidDirPathExecuteCommand() throws InvalidParamLengthException {


    Search s = new Search();
    ArrayList<String> arg = new ArrayList<>();
    FileSystem mfs = new MockFileSystem();
    arg.add("Invalid Path");
    arg.add("-type");
    arg.add("d");
    arg.add("-name");
    arg.add("\"Child 1\"");

    String output = s.executeCommand(mfs, arg);

    assertEquals("", output);


  }

  /**
   * Search for a File with a invalid path in the middle.
   */
  @Test
  public void testInvalidFilePathInMiddleExecuteCommand() throws InvalidParamLengthException {


    Search s = new Search();
    ArrayList<String> arg = new ArrayList<>();
    FileSystem mfs = new MockFileSystem();
    arg.add("Non Empty Valid Path");
    arg.add("Invalid Path");
    arg.add("Non Empty Valid Path");
    arg.add("-type");
    arg.add("f");
    arg.add("-name");
    arg.add("\"File 1\"");

    String output = s.executeCommand(mfs, arg);
    assertEquals("Valid PathFile 1\n", output);


  }

  /**
   * Search for a Directory with a invalid path in the middle.
   */
  @Test
  public void testInvalidDirPathInMiddleExecuteCommand() throws InvalidParamLengthException {


    Search s = new Search();
    ArrayList<String> arg = new ArrayList<>();
    FileSystem mfs = new MockFileSystem();
    arg.add("Non Empty Valid Path");
    arg.add("Invalid Path");
    arg.add("Non Empty Valid Path");
    arg.add("-type");
    arg.add("d");
    arg.add("-name");
    arg.add("\"Child 1\"");

    String output = s.executeCommand(mfs, arg);

    assertEquals("Valid Path\nValid Path\n", output);


  }


}
