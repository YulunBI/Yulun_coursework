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

import java.util.ArrayList;
import java.util.Arrays;
import system.Path;

public class MockPath implements java.io.Serializable, Path {

  /**
   * serialVersionUID for MockPath.
   */
  private static final long serialVersionUID = 3225631047810329102L;
  /**
   * The location of the path, in the form of an ArrayList.
   */
  private String location;

  /**
   * Constructs a new Path using the string path.
   * 
   * @param path The string format of a path
   */
  public MockPath(String path) {
    location = path;

  }

  @Override
  public Path getParentPath() {
    return new MockPath("Parent Path");
  }

  @Override
  public String toString() {
    return location;
  }

  @Override
  public ArrayList<String> getList() {
    return new ArrayList<String>(Arrays.asList(location.split("/")));
  }

  @Override
  public String getLastElement() {
    return "Last Element";
  }

  @Override
  public Boolean endsWithFowardSlash() {
    // TODO Auto-generated method stub
    return null;
  }

}
