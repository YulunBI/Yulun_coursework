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

import commands.Move;
import exceptions.InvalidParamLengthException;
import exceptions.InvalidPathException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import system.File;
import system.FileSystem;
import system.JFileSystem;
import system.JPath;
import system.Path;

public class MoveTest {
  
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
   * Test that InvalidParamLengthException is thrown when wrong number of args is given.
   */
  @Test
  public void testInvalidParamLengthExecuteCommand() {
    Move mv = new Move();
    ArrayList<String> args = new ArrayList<String>();
    
    try {
      mv.executeCommand(new MockFileSystem(), args); // too few args
      fail("InvalidParamLengthException was not thrown");
    } catch (InvalidParamLengthException e) {
      assertTrue(true);
    } catch (Exception e) {
      fail(e.getMessage());
    }
    
    args.add("foo");
    args.add("foobar");
    args.add("bar");
    try {
      mv.executeCommand(new MockFileSystem(), args); // too many args
      fail("InvalidParamLengthException was not thrown");
    } catch (InvalidParamLengthException e) {
      assertTrue(true);
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Test that command does not execute when given nonexistent targets.
   */
  @Test
  public void testInvalidExecuteCommand() {
    Move mv = new Move();
    ArrayList<String> args = new ArrayList<String>();
    
    args.add("foo");
    args.add("bar");
    try {
      mv.executeCommand(fs, args);
      assertTrue(true); // nothing broke
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Test that command can move existing file into existing directory (case 1).
   */
  @Test
  public void testValidFileIntoDirExecuteCommand() {
    Move mv = new Move();
    ArrayList<String> args = new ArrayList<String>();
    try {
      fs.addDirectory(new JPath("/foo/")); // add directory /foo
      fs.addFile(new JPath("/file1")); // add file file1
      
      args.add("file1");
      args.add("foo");
      mv.executeCommand(fs, args);
      
      if (fs.findFile(new JPath("/foo/file1")) != null) {
        assertTrue(true); // check if file exists at /foo/file1
      } else {
        fail("Did not move file");
      }
      
      try {
        if (fs.findFile(new JPath("/file1")) != null) {
          fail("Did not remove original file"); // check if file exists at /file1
        }
      } catch (InvalidPathException e) {
        assertTrue(true);
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }    
  }
  
  /**
   * Test that command can move existing dir into existing dir (case 2) recursively.
   */
  @Test
  public void testValidDirIntoDirExecuteCommand() {
    Move mv = new Move();
    ArrayList<String> args = new ArrayList<String>();
    try {
      fs.addDirectory(new JPath("/bar/")); // add directory /bar
      fs.addFile(new JPath("/bar/file2")); // add file /bar/file2
      fs.addDirectory(new JPath("/foobar/"));

      args.add("bar");
      args.add("foobar");
      mv.executeCommand(fs, args);
      
      if (fs.findDirectory(new JPath("/foobar/bar/")) != null 
          && fs.findFile(new JPath("/foobar/bar/file2")) != null) {
        assertTrue(true); //folder and contained file was copied
      } else {
        fail("File/directory was not copied");
      }
      
      try {
        if (fs.findFile(new JPath("/bar/file2")) != null 
            || fs.findDirectory(new JPath("/bar/")) != null) {
          fail("Did not remove original file/directory"); // check if original dir/file still exist
        }
      } catch (InvalidPathException e) {
        assertTrue(true);
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Test that command can move and rename existing dir (case 3) recursively.
   */
  @Test
  public void testValidDirNewDirExecuteCommand() {
    Move mv = new Move();
    ArrayList<String> args = new ArrayList<String>();
    try {
      fs.addDirectory(new JPath("/redir/"));
      fs.addFile(new JPath("/redir/refile"));
      fs.addDirectory(new JPath("/dirre/"));
      
      args.add("redir");
      args.add("/dirre/rename/");
      mv.executeCommand(fs, args);
      
      if (fs.findDirectory(new JPath("/dirre/rename/")) != null  
          && fs.findFile(new JPath("/dirre/rename/refile")) != null) {
        assertTrue(true); //folder and contained file was copied and renamed
      } else {
        fail("File/directory was not copied");
      }
      
      try {
        if (fs.findFile(new JPath("/redir/file2")) != null 
            || fs.findDirectory(new JPath("/redir/")) != null) {
          fail("Did not remove original file/directory"); // check if original dir/file still exist
        }    
      } catch (InvalidPathException e) {
        assertTrue(true);
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Test that command does not move existing file into nonexistent dir (case 4).
   */
  @Test
  public void testValidFileNewDirExecuteCommand() {
    Move mv = new Move();
    ArrayList<String> args = new ArrayList<String>();
    try {
      fs.addFile(new JPath("textfile"));
      
      args.add("textfile");
      args.add("/fakedir/textfile");
      mv.executeCommand(fs, args);
      
      if (fs.findFile(new JPath("textfile")) == null) {
        fail("Deleted original file");
      }        
      try {
        if (fs.findFile(new JPath("/fakedir/textfile")) != null) {
          fail("Created new directory to move file into");
        }          
      } catch (InvalidPathException e) {
        assertTrue(true);
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Test that command can move and rename existing file (case 5).
   */
  @Test
  public void testValidFileNewFileExecuteCommand() {
    Move mv = new Move();
    ArrayList<String> args = new ArrayList<String>();
    Path p = new JPath("/exists");
    try {
      fs.addFile(p);
      File f = fs.findFile(p);
      f.setText("foobar"); // add file exists with contents 'foobar'
      
      args.add("exists");
      args.add("dne");
      mv.executeCommand(fs, args);
      
      p = new JPath("/dne");
      f = fs.findFile(p);
      assertEquals("foobar", f.getText()); // check that new file dne exists with contents 'foobar'
      
      try {
        if (fs.findFile(new JPath("exists")) != null) { // check if original file remains
          fail("Did not remove original file");
        }
      } catch (InvalidPathException e) {
        assertTrue(true);
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Test that command can overwrite existing file (case 6).
   */
  @Test
  public void testValidFileIntoFileExecuteCommand() {
    Move mv = new Move();
    ArrayList<String> args = new ArrayList<String>();
    Path p = new JPath("/oldfile");
    try {
      fs.addFile(p);
      File f = fs.findFile(p);
      f.setText("hello world"); // add file oldfile with contents 'hello world'
      p = new JPath("/newfile");
      fs.addFile(p);
      f = fs.findFile(p);
      f.setText("goodbye"); // add file newfile with contents 'goodbye'
      
      args.add("oldfile");
      args.add("newfile");
      mv.executeCommand(fs, args);
      
      assertEquals("hello world", f.getText());
      
      try {
        if (fs.findFile(new JPath("oldfile")) != null) { // check if original file remains
          fail("Did not remove original file");
        }
      } catch (InvalidPathException e) {
        assertTrue(true);
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }    
  }
}
