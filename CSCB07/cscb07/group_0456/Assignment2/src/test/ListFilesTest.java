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

import commands.ListFiles;
import java.util.ArrayList;
import org.junit.Test;
import system.FileSystem;
import system.JFileSystem;

public class ListFilesTest {

  /**
   * Checks for InvalidPathException when param length is invalid.
   */
  @Test
  public void testInvalidPathExecuteCommand() {
    ListFiles ls = new ListFiles();
    ArrayList<String> arg = new ArrayList<>();
    arg.add("Invalid Path");

    String output = ls.executeCommand(new MockFileSystem(), arg);
    assertEquals("", output);
  }
  
  @Test
  public void testEmptyPathExecuteCommand() {
    ListFiles ls = new ListFiles();
    ArrayList<String> arg = new ArrayList<>();
    FileSystem fs = JFileSystem.createFileSystemInstance(null, 0);

    String output = ls.executeCommand(fs, arg);
    assertEquals("/:\n\n", output);
  }
  
  @Test
  public void testValidFilePathExecuteCommand() {
    ListFiles ls = new ListFiles();
    ArrayList<String> arg = new ArrayList<>();
    arg.add("Valid Path");

    String output = ls.executeCommand(new MockFileSystem(), arg);
    assertEquals("Valid Path\n", output);
  }
  
  @Test
  public void testMultiValidFilePathExecuteCommand() {
    ListFiles ls = new ListFiles();
    ArrayList<String> arg = new ArrayList<>();
    arg.add("Valid Path");
    arg.add("Valid Path");

    String output = ls.executeCommand(new MockFileSystem(), arg);
    assertEquals("Valid Path\nValid Path\n", output);
  }
  
  @Test
  public void testInvalidFilePathExecuteCommand() {
    ListFiles ls = new ListFiles();
    ArrayList<String> arg = new ArrayList<>();
    arg.add("Invalid Path");

    String output = ls.executeCommand(new MockFileSystem(), arg);
    assertEquals("", output);
  }
  
  @Test
  public void testInvalidFilePathMidExecuteCommand() {
    ListFiles ls = new ListFiles();
    ArrayList<String> arg = new ArrayList<>();
    arg.add("Valid Path");
    arg.add("Invalid Path");
    arg.add("Valid Path");

    String output = ls.executeCommand(new MockFileSystem(), arg);
    assertEquals("Valid Path\n", output);
  }
  
  @Test
  public void testValidDirPathExecuteCommand() {
    ListFiles ls = new ListFiles();
    ArrayList<String> arg = new ArrayList<>();
    arg.add("Non Empty Valid Path");

    String output = ls.executeCommand(new MockFileSystem(), arg);
    assertEquals("\nValid Path:\nChild 1\nChild 1\nChild 2\nChild 3" 
          + "\nFile 1\n\n", output);
  }
  
  @Test
  public void testMultiValidDirPathExecuteCommand() {
    ListFiles ls = new ListFiles();
    ArrayList<String> arg = new ArrayList<>();
    arg.add("Non Empty Valid Path");
    arg.add("Non Empty Valid Path");

    String output = ls.executeCommand(new MockFileSystem(), arg);
    assertEquals("\nValid Path:\nChild 1\nChild 1\nChild 2\nChild 3" 
        + "\nFile 1\n\n\nValid Path:\nChild 1\nChild 1\nChild 2\nChild" 
        + " 3\nFile 1\n\n", output);
  }
  
  @Test
  public void testInvalidDirPathExecuteCommand() {
    ListFiles ls = new ListFiles();
    ArrayList<String> arg = new ArrayList<>();
    arg.add("Invalid Path");

    String output = ls.executeCommand(new MockFileSystem(), arg);
    assertEquals("", output);
  }
  
  @Test
  public void testInvalidDirPathMidExecuteCommand() {
    ListFiles ls = new ListFiles();
    ArrayList<String> arg = new ArrayList<>();
    arg.add("Valid Path");
    arg.add("Invalid Path");
    arg.add("Non Empty Valid Path");

    String output = ls.executeCommand(new MockFileSystem(), arg);
    assertEquals("Valid Path\n", output);
  }
  
  @Test
  public void testValidDirPathWithRExecuteCommand() {
    ListFiles ls = new ListFiles();
    ArrayList<String> arg = new ArrayList<>();
    arg.add("-R");
    arg.add("Non Empty Valid Path");

    String output = ls.executeCommand(new MockFileSystem(), arg);
    assertEquals("\nTree With Paths", output);
  }
  
  @Test
  public void testEmptyPathWithRExecuteCommand() {
    ListFiles ls = new ListFiles();
    ArrayList<String> arg = new ArrayList<>();
    arg.add("-R");

    String output = ls.executeCommand(new MockFileSystem(), arg);
    assertEquals("Tree With Paths", output);
  }
}
