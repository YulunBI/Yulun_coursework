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

import commands.SaveJShell;
import exceptions.InvalidParamLengthException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import system.FileSystem;
import system.JFileSystem;

public class SaveJShellTest {
  
  private FileSystem fs;

  @Before
  public void setUp() throws Exception {
    fs = JFileSystem.createFileSystemInstance(null, 0);
  }

  @After
  public void tearDown() throws Exception {
    Field field = (fs.getClass()).getDeclaredField("fs");
    field.setAccessible(true);
    field.set(null, null);
  }
  
  /**
   * Checks for InvalidParamLengthException when param length is invalid.
   */
  @Test
  public void testInvalidParamLengthExecuteCommand() {
    try {

      SaveJShell save = new SaveJShell();
      ArrayList<String> arg = new ArrayList<>();

      save.executeCommand(new MockFileSystem(), arg);
      fail("InvalidParamLengthException not caught");

    } catch (InvalidParamLengthException e) {
      return;
    }
  }

  @Test
  public void testInvalidSaveNameExecuteCommand() throws InvalidParamLengthException {
    SaveJShell save = new SaveJShell();
    ArrayList<String> arg = new ArrayList<>();
    arg.add("%$@!");

    save.executeCommand(fs, arg);
    MockLoadJShell load = new MockLoadJShell();
    String output = load.executeCommand(fs, arg);
    assertEquals("Fail", output);
  }
  
  @Test
  public void testValidSaveNameExecuteCommand() throws InvalidParamLengthException {
    SaveJShell save = new SaveJShell();
    ArrayList<String> arg = new ArrayList<>();
    arg.add("1");

    save.executeCommand(fs, arg);
    MockLoadJShell load = new MockLoadJShell();
    String output = load.executeCommand(fs, arg);
    assertEquals("Executed command", output);
  }
}
