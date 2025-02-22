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

/**
 * Interface which represents a File.
 */
public interface File {

  /**
   * Returns location representing the path of this file.
   * 
   * @return location A Path representing the path of this file.
   */
  public Path getLocation();
  
  /**
   * Changes the text stored within this file to the String specified by text.
   * 
   * @param text The string that will become the new contents of this file.
   */
  public void setText(String text);

  /**
   * Adds the String specified by text to the end of this file's current contents.
   * 
   * @param text The string that will be appended to the end of this file's contents.
   */
  public void appendText(String text);

  /**
   * Returns a String representing the contents of this file.
   * 
   * @return text A String representing the contents of this file.
   */
  public String getText();

  /**
   * Returns a string representing the name of this file.
   * 
   * @return name A String representing the name of this file.
   */
  public String getName();
    
}
