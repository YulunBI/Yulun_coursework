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

import commands.Remove;
import exceptions.InvalidParamLengthException;
import exceptions.InvalidPathException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import system.FileSystem;
import system.JFileSystem;
import system.JPath;
import system.Path;

public class RemoveTest {
  
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
   * Test that InvalidParamLengthException is thrown when given wrong number of args.
   */
  @Test
  public void testInvalidParamLengthExecuteCommand() {
    Remove rm = new Remove();
    ArrayList<String> args = new ArrayList<String>();
    
    try {
      rm.executeCommand(new MockFileSystem(), args);
      fail("InvalidParamLengthException not thrown");
    } catch (InvalidParamLengthException e) {
      assertTrue(true);
    } catch (Exception e) {
      fail(e.getMessage());
    }
    
    try {
      args.add("foo");
      args.add("bar");
      rm.executeCommand(new MockFileSystem(), args);
      fail("InvalidParamLengthException not thrown");
    } catch (InvalidParamLengthException e) {
      assertTrue(true);
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Test that command does not execute when given non-existent directory to remove.
   */
  @Test
  public void testInvalidExecuteCommand() {
    Remove rm = new Remove();
    ArrayList<String> args = new ArrayList<String>();
    FileSystem fs = new MockFileSystem();
    Path p = new MockPath("/Valid Path/");
    
    try {
      fs.addDirectory(p);
      args.add("foo");
      rm.executeCommand(fs, args);
      assertEquals("Valid Path", fs.findDirectory(p).getPath()); // nothing was removed
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  /**
   * Test that command does not remove working directory or root.
   */
  @Test
  public void testRootWorkingDirExecuteCommand() {
    Remove rm = new Remove();
    ArrayList<String> args = new ArrayList<String>();
    Path p = new JPath("/foo/");
    
    try { // check removal of root
      args.add(".");
      rm.executeCommand(fs, args);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    } catch (Exception e) {
      fail(e.getMessage());
    }
    
    try { // check removal of working directory
      args.clear();
      args.add("/foo");
      fs.addDirectory(p); // add directory to fs
      fs.changeDirectory(p);
      
      rm.executeCommand(fs, args);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Test that command does not remove files.
   */
  @Test
  public void testFileExecuteCommand() {
    Remove rm = new Remove();
    ArrayList<String> args = new ArrayList<String>();
    Path p = new JPath("/bar");
    
    try {
      fs.addFile(p);
      args.add("/bar");
      
      rm.executeCommand(fs,  args);
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Test that command can remove empty directory.
   */
  @Test
  public void testValidExecuteCommand() {
    Remove rm = new Remove();
    ArrayList<String> args = new ArrayList<String>();
    Path p = new JPath("/");
    
    try {
      fs.changeDirectory(p);
      args.add("foo"); // should still be in fs from previous tests
      
      rm.executeCommand(fs, args);
      p = new JPath("/foo");
      fs.findDirectory(p);
      fail("Directory was not removed");
    } catch (InvalidPathException e) {
      assertTrue(true);
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Test that command can remove directory and its contents.
   */
  @Test
  public void testValidRecursiveExecuteCommand() {
    Remove rm = new Remove();
    ArrayList<String> args = new ArrayList<String>();
    Path p = new JPath("/foo/");
    
    try {
      args.add("/foo");
      fs.addDirectory(p); // add directory to fs
      p = new JPath("/foo/bar/");
      fs.addDirectory(p); // add subdirectory
      p = new JPath("/foo/file1");
      fs.addFile(p); // add file
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    try {      
      rm.executeCommand(fs, args);
      fs.findFile(p); //check /foo/file1 is present
      fail("File was not removed");
    } catch (InvalidPathException e) {
      assertTrue(true);
    } catch (Exception e) {
      fail(e.getMessage());
    }
    
    try {
      p = new JPath("/foo/bar");
      fs.findDirectory(p); //check /foo/bar/ is present
      fail("Directory was not removed");
    } catch (InvalidPathException e) {
      assertTrue(true);
    } catch (Exception e) {
      fail(e.getMessage());
    }
    
    try {
      p = new JPath("/foo");
      fs.findDirectory(p); //check /foo/ is present
      fail("Directory was not removed");
    } catch (InvalidPathException e) {
      assertTrue(true);
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
}
