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

import commands.Command;
import exceptions.InvalidParamLengthException;
import java.util.ArrayList;
import org.junit.Test;


public class CommandTest {

  /**
   * Test getting command name.
   */
  @Test
  public void testCommandName() {
    ArrayList<String> a = new ArrayList<String>();
    Command c = new MockCommand("foo", "bar", a);
    
    assertEquals("foo", c.getName());
  }
  
  /**
   * Test getting and setting command docs.
   */
  @Test
  public void testCommandDocs() {
    ArrayList<String> a = new ArrayList<String>();
    Command c = new MockCommand("foo", "bar", a);
    
    assertEquals("bar", c.getDocs());
    
    c.setDocs("new docs");
    
    assertEquals("new docs", c.getDocs());
  }
  
  /**
   * Test execution of command.
   */
  @Test
  public void testCommandExecute() {
    ArrayList<String> a = new ArrayList<String>();
    Command c = new MockCommand("foo", "bar", a);
    
    try {
      assertEquals("Executed command", c.executeCommand(new MockFileSystem(), a));
    } catch (InvalidParamLengthException e) {
      e.printStackTrace();
    }
  }

}
