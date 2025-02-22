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

package system;

import java.util.ArrayList;

/**
 * Interface which represents a Path.
 */

public interface Path {
  /**
   * Returns a Path in the format of a String.
   * 
   * @return The path as a string.
   */
  public String toString();


  /**
   * Returns the parent Path.
   * 
   * @return The parent path.
   */
  public Path getParentPath();


  /**
   * Returns the value of location.
   * 
   * @return The list format of this path.
   */
  public ArrayList<String> getList();

  /**
   * Returns the value of the last element of location.
   * 
   * @return The last element of this path.
   */
  public String getLastElement();

  /**
   * Returns the value of endsWithFowardSlash.
   * 
   * @return If the path ends with a forward slash.
   */
  public Boolean endsWithFowardSlash();

}
