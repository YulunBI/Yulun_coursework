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

import commands.History;
import exceptions.InvalidParamLengthException;
import java.util.ArrayList;
import org.junit.Test;
import system.FileSystem;



public class HistoryTest {

  /**
   * Test that InvalidParamLengthException is thrown when the wrong number of args is given.
   */
  @Test
  public void testInvalidParamLengthExecuteCommand() {
    History his = new History();
    ArrayList<String> args = new ArrayList<String>();
    args.add("1");
    args.add("2");
    
    try {
      his.executeCommand(new MockFileSystem(), args);
      fail("InvalidParamLengthException not thrown");
    } catch (InvalidParamLengthException e) {
      assertTrue(true);
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Test that the command does not execute when given invalid input.
   */
  @Test
  public void testInvalidExecuteCommand() {    
    try {
      History his = new History();
      ArrayList<String> args = new ArrayList<String>();
      args.add("a"); // non-number input
      assertEquals("", his.executeCommand(new MockFileSystem(), args));
      
      args.clear();
      args.add("1.1"); // non-integer input
      assertEquals("", his.executeCommand(new MockFileSystem(), args));
      
      args.clear();
      args.add("0"); // invalid integer input
      assertEquals("", his.executeCommand(new MockFileSystem(), args));
      
      args.clear();
      args.add("-5"); // invalid integer input
      assertEquals("", his.executeCommand(new MockFileSystem(), args));
    } catch (InvalidParamLengthException e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Test output of history with empty input.
   */
  @Test
  public void testEmptyExecuteCommand() {
    try {
      History his = new History();
      ArrayList<String> args = new ArrayList<String>();
      FileSystem fs = new MockFileSystem();
      fs.addHistory("foo");
      fs.addHistory("bar");
      fs.addHistory("test");
      fs.addHistory("history");
      
      assertEquals("1.\t foo\n2.\t bar\n3.\t test\n4.\t history\n", his.executeCommand(fs, args));
      
    } catch (InvalidParamLengthException e) {
      fail(e.getMessage());
    }
  }
  
  /**
   * Test output of history for last n commands.
   */
  @Test
  public void testGivenExecuteCommand() {
    try {
      History his = new History();
      ArrayList<String> args = new ArrayList<String>();
      args.add("2"); // print last 2 commands
      FileSystem fs = new MockFileSystem();
      fs.addHistory("foo");
      fs.addHistory("bar");
      fs.addHistory("test");
      fs.addHistory("history");
      
      assertEquals("3.\t test\n4.\t history\n", his.executeCommand(fs, args));
      
      args.clear();
      args.add("10"); // 10 > 4 commands in history
      assertEquals("1.\t foo\n2.\t bar\n3.\t test\n4.\t history\n", his.executeCommand(fs, args));
      
    } catch (InvalidParamLengthException e) {
      fail(e.getMessage());
    }
  }
  
}
