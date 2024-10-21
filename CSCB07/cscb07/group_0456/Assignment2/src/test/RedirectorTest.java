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

import java.util.ArrayList;
import org.junit.Test;
import system.Redirector;

public class RedirectorTest {

  /*
   ** Test when there is no symbol in the arguments
   */
  @Test
  public void testNoSymbolRemoveSymbol() {
    Redirector r = new Redirector();
    ArrayList<String> args = new ArrayList<>();
    args.add("Test Arg 1");
    args.add("Test Arg 2");
    args.add("Test Arg 3");

    r.removeSymbol(args);

    assertEquals("Test Arg 1", args.get(0));
    assertEquals("Test Arg 2", args.get(1));
    assertEquals("Test Arg 3", args.get(2));
  }

  /*
   ** Test when there is the overwrite symbol in the arguments
   */
  @Test
  public void testOvewriteRemoveSymbol() {
    Redirector r = new Redirector();
    ArrayList<String> args = new ArrayList<>();
    args.add(">");
    args.add("File 1");

    r.removeSymbol(args);

    assertTrue(args.size() == 0);
  }

  /*
   ** Test when there is the append symbol in the arguments
   */
  @Test
  public void testAppendRemoveSymbol() {
    Redirector r = new Redirector();
    ArrayList<String> args = new ArrayList<>();
    args.add(">>");
    args.add("File 1");

    r.removeSymbol(args);

    assertTrue(args.size() == 0);
  }

  /*
   ** Test when there is no redirection
   */
  @Test
  public void testNoOutputRedirectOutput() {
    Redirector r = new Redirector();

    String output = r.redirectOutput("Some random output", new MockFileSystem());

    assertEquals("Some random output", output);


  }

  /*
   ** Test when there is overwrite redirection
   */
  @Test
  public void testOverwriteOutputRedirectOutput() {
    Redirector r = new Redirector();
    ArrayList<String> args = new ArrayList<>();
    args.add(">");
    args.add("File 1");

    r.removeSymbol(args);
    String output = r.redirectOutput("Some random output", new MockFileSystem());

    assertEquals("", output);


  }

  /*
   ** Test when there is the append redirection
   */
  @Test
  public void testAppendOutputRedirectOutput() {
    Redirector r = new Redirector();
    ArrayList<String> args = new ArrayList<>();
    args.add(">>");
    args.add("File 1");

    r.removeSymbol(args);
    String output = r.redirectOutput("Some random output", new MockFileSystem());

    assertEquals("", output);


  }

  /*
   ** Test when the path of the File provided doesn't have a valid parent path
   */
  @Test
  public void testInvalidPathRedirectOutput() {
    Redirector r = new Redirector();
    ArrayList<String> args = new ArrayList<>();
    args.add(">>");
    args.add("Invalid Parent Path");

    r.removeSymbol(args);
    String output = r.redirectOutput("Some random output", new MockFileSystem());

    assertEquals("", output);


  }


}
