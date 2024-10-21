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

import commands.LoadJShell;
import exceptions.InvalidParamLengthException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import system.FileSystem;
import system.JFileSystem;

public class LoadJShellTest {
  
  private FileSystem fst;
  
  /**
   * Checks for InvalidParamLengthException when param length is invalid.
   */
  @Test
  public void testInvalidParamLengthExecuteCommand() {
    try {

      LoadJShell load = new LoadJShell();
      ArrayList<String> arg = new ArrayList<>();

      load.executeCommand(new MockFileSystem(), arg);
      fail("InvalidParamLengthException not caught");

    } catch (InvalidParamLengthException e) {
      return;
    }
  }
  
  @Test
  public void testInvalidFileNameExecuteCommand() throws InvalidParamLengthException {
    ArrayList<String> arg = new ArrayList<>();
    arg.add("$#!)");
    MockSaveJShell save = new MockSaveJShell();
    FileSystem fs = new MockFileSystem();
    save.executeCommand(fs, arg);
    LoadJShell load = new LoadJShell();
    MockFileSystem nfs = null;
    load.executeCommand(nfs, arg);
    assertEquals(null, nfs);
  }
  
  @Before
  public void setUp() throws Exception {
    fst = JFileSystem.createFileSystemInstance(null, 0);
  }

  @After
  public void tearDown() throws Exception {
    Field field = (fst.getClass()).getDeclaredField("fs");
    field.setAccessible(true);
    field.set(null, null);
  }
  
  @Test
  public void testValidFileNameExecuteCommand() throws InvalidParamLengthException,
      NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    ArrayList<String> arg = new ArrayList<>();
    arg.add("1");
    MockSaveJShell save = new MockSaveJShell();
    fst.addHistory("testing load");
    save.executeCommand(fst, arg);
    LoadJShell load = new LoadJShell();
    Field fields = (fst.getClass()).getDeclaredField("fs");
    fields.setAccessible(true);
    fields.set(null, null);
    this.fst = null;
    load.executeCommand(fst, arg);
    fst = JFileSystem.createFileSystemInstance(null, -1);
    assertEquals("testing load", fst.getHistory().get(0));
  }
}
