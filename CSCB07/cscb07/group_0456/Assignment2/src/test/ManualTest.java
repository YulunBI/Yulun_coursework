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

import commands.Manual;
import exceptions.InvalidParamLengthException;
import java.util.ArrayList;
import org.junit.Test;

public class ManualTest {

  /**
   * Test that InvalidParamLengthException is thrown when the wrong number of args is given.
   */
  @Test
  public void testInvalidParamLengthExecuteCommand() {
    Manual man = new Manual();
    ArrayList<String> args = new ArrayList<String>();
    
    try {
      man.executeCommand(new MockFileSystem(), args);
      fail("InvalidParamLengthException not thrown");
      
      args.add("ls");
      args.add("cat");
      man.executeCommand(new MockFileSystem(), args);
      fail("InvalidParamLengthException not thrown");
    } catch (InvalidParamLengthException e) {
      assertTrue(true);
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Test that man does not execute when given an invalid command name input.
   */
  @Test
  public void testInvalidExecuteCommand() {
    Manual man = new Manual();
    ArrayList<String> args = new ArrayList<String>();
    
    try {
      args.add("foo");
      assertEquals("", man.executeCommand(new MockFileSystem(), args));
      
      args.clear();
      args.add("commands.Manual");
      assertEquals("", man.executeCommand(new MockFileSystem(), args));
      
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Test that man executes for a valid input.
   */
  @Test
  public void testValidExecuteCommand() {
    Manual man = new Manual();
    ArrayList<String> args = new ArrayList<String>();
    
    try {
      args.add("man");
      assertEquals("man CMD\n\tPrint documentation for CMD.\n", 
          man.executeCommand(new MockFileSystem(), args));
      
      args.clear();
      args.add("ls");
      assertEquals("ls [PATH ...]\n\tIf no paths are given, print the contents of current directory"
          + ",\n\twith a new line following each file/directory." + "\n\tOtherwise, for each path p"
          + ":\n\t\tIf p specifies a file, print p."
          + "\n\t\tIf p specifies directory, print p + ':' + contents of directory + newline.\n",
          man.executeCommand(new MockFileSystem(), args));
      
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }  
}
