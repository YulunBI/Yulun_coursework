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

import exceptions.AlreadyExistsException;
import exceptions.InvalidParamLengthException;
import exceptions.InvalidPathException;
import java.util.ArrayList;
import system.FileSystem;

/**
 * Command for printing input String to Shell or overwriting the contents of the given file with
 * input String or appending the given Sting to the given file.
 * 
 */
public class Echo extends Command {
  /**
   * Creates a new Echo instance and initializes the command name and docs.
   */
  public Echo() {
    this.setName("echo");
    this.setDocs("echo STRING [> OUTFILE]\n\tIf OUTFILE is not provided, print STRING in shell."
        + "\n\tOtherwise, overwrites content of file OUTFILE with STRING."
        + "\n\tCreates OUTFILE if it does not already exist."
        + "\n\tSTRING is a string of characters surrounded by double quotation marks.");
  }

  /**
   * If the argument(s) given by user is a valid String, String surrounded by quotation marks and no
   * quotation mark inside String, prints this String without quotation marks to console. if the
   * user gives a valid String and specifier ">" followed by a valid path of a file, the relative or
   * absolute path of a existing file or the relative or absolute path of a non-existing but is able
   * to create, overwrites this file with given String without quotation marks. If the user gives a
   * valid String and specifier ">>" followed by a valid path of a file, appends the String without
   * quotation marks to this file. 
   * 
   * @param fs the FileSystem associated with the shell this command is running in
   * @param arguments the list of arguments provided by the user
   * @return The output of the command
   * @throws AlreadyExistsException If file to create already exist.
   * @throws IllegalArgumentException If the given String is invalid.
   * @throws InvalidParamLengthException If the length of arguments is zero or two.
   * @throws InvalidPathException If the given path is invalid.
   */
  public String executeCommand(FileSystem fs, ArrayList<String> arguments)
      throws InvalidParamLengthException, IllegalArgumentException {

    int size = arguments.size();

    if (size != 1) {
      throw new InvalidParamLengthException("Invalid parameter length");
    }

    String text = arguments.get(0);

    if (!isString(text)) {
      throw new IllegalArgumentException(text + " is not a valid String!");
    }

    return text.substring(1, text.length() - 1);
  }

  /**
   * Returns true if this text starts with "\"", ends with "\"", and doesn't contain "\"" in middle.
   * Otherwise return false.
   * 
   * @param text the String to be determined if it is a valid String
   * @return Boolean If text is a valid string.
   */
  private Boolean isString(String text) {
    String subString;

    if (text.startsWith("\"") && text.endsWith("\"")) {
      subString = text.substring(1, text.length() - 1);
      if (subString.contains("\"")) {
        return false;
      }
      return true;
    }
    return false;
  }

}
