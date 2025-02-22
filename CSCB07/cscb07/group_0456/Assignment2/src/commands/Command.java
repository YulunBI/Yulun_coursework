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

package commands;

import exceptions.InvalidParamLengthException;
import java.util.ArrayList;
import system.FileSystem;

/**
 * Commands that the user can use in Shell.
 * 
 */
public abstract class Command {
  /**
   * The name of the command.
   */
  private String name;

  /**
   * The arguments of the command.
   */
  private ArrayList<String> arguments;

  /**
   * The documentation of the command.
   */
  private String docs;


  /**
   * Executes the command with the given arguments on the FileSystem fs. If the parameter length is
   * invalid, InvalidParamLengthException is thrown.
   * 
   * @param fs An instance of the FileSystem.
   * @param arguments The list of arguments provided by the user.
   * @return The output of the command
   * @throws InvalidParamLengthException If the parameter length is invalid.
   */
  public abstract String executeCommand(FileSystem fs, ArrayList<String> arguments)
      throws InvalidParamLengthException;


  /**
   * Sets this Command's docs to the value of docs.
   * 
   * @param docs The docs of the command
   */
  public void setDocs(String docs) {
    this.docs = docs;
  }


  /**
   * Returns the value of this Command's docs.
   * 
   * @return The docs of command
   */
  public String getDocs() {
    return docs;
  }


  /**
   * Returns the value of this Command's name.
   * 
   * @return The name of this command
   */
  public String getName() {
    return name;
  }


  /**
   * Sets this Command's name to the value of name.
   * 
   * @param name The name thats command is taking
   */
  protected void setName(String name) {
    this.name = name;
  }


  /**
   * Sets this Command's arguments to the value of arguments.
   * 
   * @param arguments The arguments given by the user
   */
  protected void setArguments(ArrayList<String> arguments) {
    this.arguments = arguments;
  }


  /**
   * Returns the value of this Command's arguments.
   * 
   * @return The arguments given by the user
   */
  protected ArrayList<String> getArguments() {
    return this.arguments;
  }

}

