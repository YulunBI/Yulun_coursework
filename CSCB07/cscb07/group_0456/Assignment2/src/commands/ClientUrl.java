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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import javax.naming.InvalidNameException;
import system.FileSystem;
import system.JPath;
import system.Path;

/**
 * Command for retrieving the content in Url and add it to current working directory.
 * 
 */
public class ClientUrl extends Command {
  
  /**
   * The exact number of arguments required to execute this command.
   */
  private static final int EXACT_ARG_LENGTH = 1;

  /**
   * Creates a new ClientUrl instance and initializes the command name and docs.
   */
  public ClientUrl() {
    this.setName("curl");
    this.setDocs("curl URL\n\tRetrieve the file at URL"
        + "and add it to the current working directory.");
  }

  /**
   * Retrieve the content in given URL add it to the current working directory. 
   * Will not add or modify file if it already exist. InvalidParamLengthException
   * is thrown if there are not exactly EXACT_ARG_LENGTH arguments provided.
   * IOException is thrown if URL is invalid. InvalidNameException is thrown if 
   * name of file is invalid. InvalidPathException is thrown if the path to save 
   * file is invalid. AlreadyExistsException is thrown if the file already exist.
   * 
   * @param fs the FileSystem associated with the shell this command is running in
   * @param arguments the list of arguments provided by the user
   * @return The output of the command
   * @throws InvalidParamLengthException If there are not exactly EXACT_ARG_LENGTH arguments
   *         provided.
   * @throws IOException If URL is invalid.
   * @throws InvalidNameException If name of file is invalid.
   * @throws InvalidPathException If the path to save file is invalid.
   * @throws AlreadyExistsException If the file already exist.
   */
  public String executeCommand(FileSystem fs, ArrayList<String> arguments)
      throws InvalidParamLengthException {
    if (arguments.size() != EXACT_ARG_LENGTH) {
      throw new InvalidParamLengthException("Invalid parameter length");
    }
    try {
      String fileName = parseFileName(arguments);
      URL url = new URL(arguments.get(0));
      BufferedReader in = new BufferedReader(new InputStreamReader(
          url.openStream()));
      String text;
      Path p = new JPath(fs.getDirectory().getPath() + fileName);
      fs.addFile(p);
      while ((text = in.readLine()) != null) {
        fs.redirect(">>", text, fileName);
      }
      in.close();
    } catch (IOException e) {
      System.err.println(arguments + "Invalid url");
    } catch (InvalidNameException e) {
      System.err.println("Invalid file name");
    } catch (InvalidPathException e) {
      System.err.println("Invalid path");
    } catch (AlreadyExistsException e) {
      System.err.println("File already exist");
    } 
    return "";
  }
  
  /**
   * Parse fileName from arguments. InvalidNameException is thrown 
   * if fileName is invalid.
   * 
   * @param arguments the URL contains file name
   * @return fileName the file name parsed from arguments
   * @throws InvalidNameException If fileName is invalid.
   */
  private String parseFileName(ArrayList<String> arguments) 
      throws InvalidNameException {
    String url = arguments.get(0);
    String[] urlList = url.split("/");
    String fileName;
    int lenL = urlList.length;
    if (url.endsWith("/")) {
      fileName = urlList[lenL - 2];
    } else {
      fileName = urlList[lenL - 1];
    }
    if (fileName.indexOf(".") != fileName.lastIndexOf(".")) {
      throw new InvalidNameException("Invalid file name");
    }
    fileName = fileName.replace(".", "");
    return fileName;
  }
}
