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

import commands.PopDirectory;
import exceptions.InvalidParamLengthException;
import java.util.ArrayList;
import org.junit.Test;
import system.Directory;
import system.FileSystem;
import system.Path;


public class PopDirectoryTest {

  /**
   * Test that InvalidParamLengthException is thrown when the wrong number of args is given.
   */
  @Test
  public void testInvalidParamLengthExecuteCommand() {
    PopDirectory popd = new PopDirectory();
    ArrayList<String> args = new ArrayList<String>();
    
    try {
      args.add("foo");
      popd.executeCommand(new MockFileSystem(), args);
      fail("InvalidParamLengthException not thrown");
    } catch (InvalidParamLengthException e) {
      assertTrue(true);
    } catch (Exception e) {
      fail(e.getMessage());
    }    
    
    try {
      args.add("cat");
      popd.executeCommand(new MockFileSystem(), args);
      fail("InvalidParamLengthException not thrown");
    } catch (InvalidParamLengthException e) {
      assertTrue(true);
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Test that the command does not execute when the stack is empty.
   */
  @Test
  public void testEmptyExecuteCommand() {
    PopDirectory popd = new PopDirectory();
    ArrayList<String> args = new ArrayList<String>();
    FileSystem fs = new MockFileSystem();
    
    try {
      popd.executeCommand(fs, args);
      assertEquals("CD Path", fs.getDirectory().getPath()); //working directory has not changed
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  /**
   * Test that the command pops a directory from the top of a small stack.
   */
  @Test
  public void testValidExecuteCommand() {
    PopDirectory popd = new PopDirectory();
    ArrayList<String> args = new ArrayList<String>();
    FileSystem fs = new MockFileSystem();
    Path p = new MockPath("/Valid Path/");
    
    try {
      fs.addDirectory(p); // add the dir to the file system
      Directory d = fs.findDirectory(p);
      fs.stackPush(d); // add it to the stack
      popd.executeCommand(fs, args);
      assertEquals("Valid Path", fs.getDirectory().getPath());      

      popd.executeCommand(fs, args);
      assertEquals("Valid Path", fs.getDirectory().getPath()); //working directory has not changed
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Test that the command can pop multiple directories in a row.
   */
  @Test
  public void testMultipleExecuteCommand() {
    PopDirectory popd = new PopDirectory();
    ArrayList<String> args = new ArrayList<String>();
    FileSystem fs = new MockFileSystem();
    Path p = new MockPath("/Valid Path/foo");
    
    try {
      fs.addDirectory(p); // add the dir to the file system
      Directory d = fs.findDirectory(p);
      fs.stackPush(d); // add it to the stack
      
      p = new MockPath("/Valid Path/bar/");
      fs.addDirectory(p); // add the dir to the file system
      d = fs.findDirectory(p);
      fs.stackPush(d); // add it to the stack
      
      p = new MockPath("/Valid Path/this/");
      fs.addDirectory(p); // add the dir to the file system
      d = fs.findDirectory(p);
      fs.stackPush(d); // add it to the stack
      
      popd.executeCommand(fs, args);
      assertEquals("Valid Path", fs.getDirectory().getPath());      

      popd.executeCommand(fs, args);
      assertEquals("Valid Path", fs.getDirectory().getPath());
      
      popd.executeCommand(fs, args);
      assertEquals("Valid Path", fs.getDirectory().getPath());
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
}
