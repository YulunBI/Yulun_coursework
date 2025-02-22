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

import system.File;
import system.Path;

public class MockFile implements java.io.Serializable, File {

  /**
   * serialVersionUID for MockFile.
   */
  private static final long serialVersionUID = 8281734942336259858L;

  /**
   * Name of File.
   */

  String name;

  /**
   * Path of File.
   */

  Path location;

  /**
   * Content of File.
   */

  String text;

  public MockFile(String name, String path) {
    this.name = name;
    this.location = new MockPath(path);
    text = new String();
  }

  @Override
  public Path getLocation() {
    return location;
  }

  @Override
  public void setText(String text) {
    this.text = text;

  }

  @Override
  public void appendText(String text) {
    this.text += text;

  }

  @Override
  public String getText() {
    return text;
  }

  @Override
  public String getName() {
    return name;
  }

}
