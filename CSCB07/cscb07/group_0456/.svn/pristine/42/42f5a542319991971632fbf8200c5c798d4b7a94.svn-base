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

import commands.Echo;
import exceptions.InvalidParamLengthException;
import java.util.ArrayList;
import org.junit.Test;

public class EchoTest {

  /**
   * Checks for InvalidParamLengthException when param length is invalid.
   */
  @Test
  public void testInvalidParamLengthExecuteCommand() {
    try {

      Echo echo = new Echo();
      ArrayList<String> arg = new ArrayList<>();

      echo.executeCommand(new MockFileSystem(), arg);
      fail("InvalidParamLengthException not caught");

    } catch (InvalidParamLengthException e) {
      return;
    }
  }
  
  /**
   * Checks for IllegalArgumentException when param is invalid format.
   */
  @Test
  public void testIllegalArgumentExecuteCommand() throws InvalidParamLengthException {
    try {
      Echo echo = new Echo();
      ArrayList<String> arg = new ArrayList<>();
      arg.add("");

      echo.executeCommand(new MockFileSystem(), arg);
      fail("IllegalArgumentException not caught");
      
    } catch (IllegalArgumentException e) {
      return;
    }
  }
  
  /**
   * Print input with valid param format.
   */
  @Test
  public void testIegalArgumentExecuteCommand() throws InvalidParamLengthException {
    Echo echo = new Echo();
    ArrayList<String> arg = new ArrayList<>();
    arg.add("\"Legal input\"");

    String output = echo.executeCommand(new MockFileSystem(), arg);
    assertEquals("Legal input", output);

  }
  
}
