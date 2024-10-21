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

import org.junit.Test;
import system.JPath;
import system.Path;

public class JPathTest {

  /*
   ** Test getting the parent of a absolute path
   */
  @Test
  public void testAbsoluteGetParentPath() {
    Path p = new JPath("/some/random/path");
    Path parent = p.getParentPath();

    assertEquals("", parent.getList().get(0));
    assertEquals("some", parent.getList().get(1));
    assertEquals("random", parent.getList().get(2));

  }

  /*
   ** Test getting the parent of a relative path
   */
  @Test
  public void testRelativeGetParentPath() {
    Path p = new JPath("./some/relative/path");
    Path parent = p.getParentPath();

    assertEquals(".", parent.getList().get(0));
    assertEquals("some", parent.getList().get(1));
    assertEquals("relative", parent.getList().get(2));

  }

  @Test
  /*
   ** Test if the path ends with a forward slash
   */
  public void testEndsWithForwardSlash() {
    Path p = new JPath("/foward/slash/path/");
    assertTrue(p.endsWithFowardSlash());
  }

  @Test
  /*
   ** Test getting a list version of the path
   */
  public void testGetList() {
    Path p = new JPath("/foward/slash/path/");

    assertEquals("", p.getList().get(0));
    assertEquals("foward", p.getList().get(1));
    assertEquals("slash", p.getList().get(2));
    assertEquals("path", p.getList().get(3));
    assertTrue(p.getList().size() == 4);
  }


  @Test
  /*
   ** Test getting a string version of the path
   */
  public void testToString() {
    Path p = new JPath("/foward/slash/path/");

    String path = p.toString();

    assertEquals("/foward/slash/path/", path);
  }

}
