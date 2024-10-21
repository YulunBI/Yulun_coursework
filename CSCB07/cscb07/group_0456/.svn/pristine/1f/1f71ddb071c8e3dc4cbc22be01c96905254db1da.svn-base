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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import commands.Command;
import commands.CommandHashTable;
import exceptions.InvalidCommandNameException;
import org.junit.Test;

public class CommandHashTableTest {

  @Test
  public void testInvalidCommandNameFindCommand() {
    try {
      CommandHashTable hashTable = new CommandHashTable();
      hashTable.findCommand("1");
      fail("InvalidCommandNameException not caught");
    } catch (InvalidCommandNameException e) {
      return;
    }
  }

  @Test
  public void testValidCommandNameFindCommand() throws InvalidCommandNameException {
    CommandHashTable hashTable = new CommandHashTable();
    Command c = hashTable.findCommand("curl");
    assertEquals("curl", c.getName());
  }
}
