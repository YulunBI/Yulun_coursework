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

import commands.Command;
import exceptions.InvalidParamLengthException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import system.FileSystem;

public class MockLoadJShell extends Command {

  public String executeCommand(FileSystem fs, ArrayList<String> arguments)
      throws InvalidParamLengthException {
    try {
      String realCurrDir = System.getProperty("user.dir");
      FileInputStream fileIn = new FileInputStream(realCurrDir + arguments + ".ser");
    } catch (IOException e) {
      return ("Fail");
    }
    return "Executed command";
  }
}
