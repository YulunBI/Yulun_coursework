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
import java.util.Arrays;

/**
 * Represents a Path of File or Directory names delimited by /.
 */
public class JPath implements java.io.Serializable, Path {

  /**
   * serialVersionUID for JPath.
   */
  private static final long serialVersionUID = -5795780238265247606L;

  /**
   * The location of the path, in the form of an ArrayList.
   */
  private ArrayList<String> location;

  /**
   * If the path ends with a forward slash.
   */
  private Boolean endsWithFowardSlash;

  /**
   * Constructs a new Path using the string path.
   * 
   * @param path The string format of a path
   */
  public JPath(String path) {

    if (path.endsWith("/")) {
      endsWithFowardSlash = true;
    } else {
      endsWithFowardSlash = false;
    }

    String reg = "(?<=[^/])(/)(?=[^/]*)|(^/)";

    location = new ArrayList<String>(Arrays.asList(path.split(reg)));

    // empty string represents root
    if (location.isEmpty()) {
      location.add("");
    }

    // if the array list only has one word, then we're looking in current directory
    if (location.size() == 1 && !(location.get(0).isBlank())) {
      location.add(0, ".");
    }

  }


  /**
   * Returns a Path in the format of a String.
   * 
   * @return The path as a string.
   */
  public String toString() {
    String completePath = "";
    for (String dir : location) {
      completePath += dir + "/";
    }

    return completePath;
  }


  /**
   * Returns the parent Path.
   * 
   * @return The parent path.
   */
  public Path getParentPath() {
    String parentPath = "";
    for (int i = 0; i < location.size() - 1; i++) {
      parentPath += location.get(i) + "/";
    }

    return new JPath(parentPath);
  }


  /**
   * Returns the value of location.
   * 
   * @return The list format of this path.
   */
  public ArrayList<String> getList() {
    return location;
  }


  /**
   * Returns the value of the last element of location.
   * 
   * @return The last element of this path.
   */
  public String getLastElement() {
    return location.get(location.size() - 1);
  }

  /**
   * Returns the value of endsWithFowardSlash.
   * 
   * @return If the path ends with a forward slash.
   */
  public Boolean endsWithFowardSlash() {
    return endsWithFowardSlash;
  }

}
