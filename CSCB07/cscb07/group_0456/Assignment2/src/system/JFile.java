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


/** 
  * Representation of a File containing text.
  */
public class JFile implements java.io.Serializable, File {
  
  /**
   * serialVersionUID for JFile.
   */
  private static final long serialVersionUID = 5264316017242892395L;

  /**
   * Represents the name of this file.
   */
  private String name;

  /**
   * Represents the text stored within this file.
   */
  private String text;

  /**
   * Represents the full path of this file.
   */
  private Path location;

  /**
   * Creates an instance of the JFile object with a specified name and path, and creates an empty
   * String variable text to hold the contents of the file.
   * 
   * @param name The specified name for this file.
   * @param location The full specified path for this file.
   */
  public JFile(String name, String location) {
    this.location = new JPath(location);
    text = new String();
    this.name = name;
  }
  
  /**
   * Returns location representing the path of this file.
   * 
   * @return location A Path representing the path of this file.
   */
  public Path getLocation() {
    return location;
  }
  
  /**
   * Changes the text stored within this file to the String specified by text.
   * 
   * @param text The string that will become the new contents of this file.
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * Adds the String specified by text to the end of this file's current contents.
   * 
   * @param text The string that will be appended to the end of this file's contents.
   */
  public void appendText(String text) {
    this.text += text;
  }

  /**
   * Returns a String representing the contents of this file.
   * 
   * @return text A String representing the contents of this file.
   */
  public String getText() {
    return text;
  }

  /**
   * Returns a string representing the name of this file.
   * 
   * @return name A String representing the name of this file.
   */
  public String getName() {
    return name;
  }
}
