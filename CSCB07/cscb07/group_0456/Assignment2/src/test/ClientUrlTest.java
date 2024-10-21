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

import commands.ClientUrl;
import exceptions.AlreadyExistsException;
import exceptions.InvalidParamLengthException;
import exceptions.InvalidPathException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import javax.naming.InvalidNameException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import system.FileSystem;
import system.JFileSystem;
import system.JPath;
import system.Path;

public class ClientUrlTest {

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

      ClientUrl curl = new ClientUrl();
      ArrayList<String> arg = new ArrayList<>();

      curl.executeCommand(new MockFileSystem(), arg);
      fail("InvalidParamLengthException not caught");

    } catch (InvalidParamLengthException e) {
      return;
    }
  }

  @Test
  public void testValidNameForFileAndValidUrl() throws InvalidParamLengthException {
    ClientUrl curl = new ClientUrl();
    ArrayList<String> arg = new ArrayList<>();
    arg.add("http://www.cs.cmu.edu/~spok/grimmtmp/073.txt");

    curl.executeCommand(fs, arg);
    assertEquals("073txt", fs.getDirectory().getFile().get(0).getName());
  }

  /**
   * Checks for InvalidNameException when file name is invalid.
   */
  @Test
  public void testInvalidNameForFile() throws InvalidParamLengthException {
    ClientUrl curl = new ClientUrl();
    ArrayList<String> arg = new ArrayList<>();
    arg.add("http://www.cs.cmu.edu/~spok/grimmtmp/073.t.xt");

    curl.executeCommand(new MockFileSystem(), arg);
    assertTrue(fs.getDirectory().getFile().isEmpty());

  }
  
  /**
   * Checks for InvalidNameException when file name is invalid.
   */
  @Test
  public void testInvalidUrlExecuteCommand() throws InvalidParamLengthException {
    ClientUrl curl = new ClientUrl();
    ArrayList<String> arg = new ArrayList<>();
    arg.add("http://www.ub.edu/gilcub/SIMPLE/simple.html");

    curl.executeCommand(fs, arg);
    assertTrue(fs.getDirectory().getFile().isEmpty());
  }
  
  
  @Test
  public void testAlreadyExistsFileExecuteCommand() throws InvalidParamLengthException,
      AlreadyExistsException, InvalidNameException, InvalidPathException {
    ClientUrl curl = new ClientUrl();
    ArrayList<String> arg = new ArrayList<>();
    arg.add("http://www.cs.cmu.edu/~spok/grimmtmp/073.txt");
    Path p = new JPath("073txt");
    fs.addFile(p);
    curl.executeCommand(fs, arg);
    assertEquals("", fs.getDirectory().getFile().get(0).getText());
  }
}
