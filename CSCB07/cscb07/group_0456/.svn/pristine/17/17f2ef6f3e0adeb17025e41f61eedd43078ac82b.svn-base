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

import commands.PushDirectory;
import exceptions.InvalidParamLengthException;
import java.util.ArrayList;
import org.junit.Test;
import system.Directory;
import system.FileSystem;
import system.Path;

public class PushDirectoryTest {

  /**
   * Test that InvalidParamLengthException is thrown when the wrong number of args is given.
   */
  @Test
  public void testInvalidParamLengthExecuteCommand() {
    PushDirectory pushd = new PushDirectory();
    ArrayList<String> args = new ArrayList<String>();
    
    try {
      pushd.executeCommand(new MockFileSystem(), args);
      fail("InvalidParamLengthException not thrown");
    } catch (InvalidParamLengthException e) {
      assertTrue(true);
    } catch (Exception e) {
      fail(e.getMessage());
    }
    
    try {      
      args.add("foo");
      args.add("bar");
      pushd.executeCommand(new MockFileSystem(), args);
      fail("InvalidParamLengthException not thrown");
    } catch (InvalidParamLengthException e) {
      assertTrue(true);
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Test that the command does not execute when an invalid arg is given.
   */
  @Test
  public void testInvalidExecuteCommand() {
    PushDirectory pushd = new PushDirectory();
    ArrayList<String> args = new ArrayList<String>();
    FileSystem fs = new MockFileSystem();
    
    try {
      
      args.add("/Invalid Path/");
      pushd.executeCommand(fs, args);
      assertEquals("CD Path", fs.getDirectory().getPath()); //working directory has not changed
      
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Test that the command changes the working directory and adds to the stack on valid input.
   */
  @Test
  public void testValidExecuteCommand() {
    PushDirectory pushd = new PushDirectory();
    ArrayList<String> args = new ArrayList<String>();
    FileSystem fs = new MockFileSystem();
    Path p = new MockPath("/Valid Path/");
    
    try {
      fs.addDirectory(p); // add the dir to the file system      
      args.add("/Valid Path/");
      pushd.executeCommand(fs, args);
      assertEquals("Valid Path", fs.getDirectory().getPath()); //working directory has changed
      
      Directory d = fs.stackPop();
      assertEquals("CD Path", d.getPath()); //check that CD Path was put into stack
      
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

}
