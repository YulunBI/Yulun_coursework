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
import exceptions.InvalidPathException;
import java.util.ArrayList;
import system.Directory;
import system.File;
import system.FileSystem;
import system.JPath;
import system.Path;

/**
 * Command for searching for a file or directory by name in the file system.
 * 
 */
public class Search extends Command {

  /**
   * The number of arguments required to execute this command.
   */
  private static final int MIN_ARG_LENGTH = 5;

  /**
   * Creates a new Search instance and initializes the command name and docs.
   */
  public Search() {
    this.setName("search");
    this.setDocs("search path ... -type [f|d] -name \"name\" \n\t"
        + "Recursively searches paths for files[f] or directories[d] called name");
  }

  /**
   * Searches all the paths in arguments and returns the paths of the Files or Directories given by
   * the name provided.
   * 
   * @param fs the FileSystem associated with the shell this command is running in
   * @param arguments the list of arguments provided by the user
   * @return The output of the command
   * @throws InvalidParamLengthException If there are not at least MIN_ARG_LENGTH arguments
   *         provided.
   */
  public String executeCommand(FileSystem fs, ArrayList<String> arguments)
      throws InvalidParamLengthException {

    if (arguments.size() < MIN_ARG_LENGTH) {
      throw new InvalidParamLengthException("Invalid parameter length");
    }

    int typeIndex = arguments.size() - 4;
    String name = arguments.get(arguments.size() - 1);
    String type = arguments.get(typeIndex + 1);
    String output = "";

    try {
      checkForIllegalArgs(arguments);
      name = name.substring(1, name.length() - 1);

      for (int i = 0; i < typeIndex; i++) {
        Path p = new JPath(arguments.get(i));
        Directory d = fs.findDirectory(p);
        if (type.equals("f")) {
          output += searchForFiles(d, name);
        } else if (type.equals("d")) {
          output += searchForDirs(d, name);
        }
      }

    } catch (InvalidPathException e) {
      System.err.println(e.getMessage());
    }

    return output;
  }

  /**
   * Returns a String with all the files in dir which are called name.
   * 
   * @param dir The directory that we're searching
   * @param name The file name we are searching for
   * @return A string listing the files in dir called name
   */
  private String searchForFiles(Directory dir, String name) {

    String files = "";

    for (File f : dir.getFile()) {
      if (f.getName().matches(name)) {
        files += f.getLocation().toString() + name + "\n";
      }
    }

    for (Directory d : dir.getChildren()) {
      files += searchForFiles(d, name);
    }

    return files;
  }

  /**
   * Returns a String with all the directories in dir which are called name.
   * 
   * @param dir The directory that we're searching
   * @param name The file name we are searching for
   * @return A string listing the directories in dir called name
   */
  private String searchForDirs(Directory dir, String name) {

    String dirs = "";

    for (Directory d : dir.getChildren()) {
      if (d.getName().matches(name)) {
        dirs += d.getPath() + "\n";
      }
    }

    for (Directory d : dir.getChildren()) {
      dirs += searchForDirs(d, name);
    }

    return dirs;
  }

  /**
   * Checks if arguments are valid for this command. IllegalArgumentException is thrown if the
   * arguments are invalid.
   * 
   * @param arguments The arguments that are being checked
   * @throws IllegalArgumentException If the arguments are invalid
   */
  private void checkForIllegalArgs(ArrayList<String> arguments) throws IllegalArgumentException {

    int typeIndex = arguments.size() - 4;
    String type = arguments.get(typeIndex + 1);
    int nameIndex = arguments.size() - 2;
    String name = arguments.get(arguments.size() - 1);

    if (!arguments.get(typeIndex).equals("-type") || !arguments.get(nameIndex).equals("-name")
        || !name.startsWith("\"") || !name.endsWith("\"")
        || !(type.equals("d") || type.equals("f"))) {
      throw new IllegalArgumentException("Invalid Parameters were provided.");
    }
  }

}
