package test;

import static org.junit.Assert.fail;
import java.util.ArrayList;
import org.junit.Test;
import commands.Exit;
import exceptions.InvalidParamLengthException;

public class ExitTest {

  /**
   * Checks for InvalidParamLengthException when param length is invalid.
   */
  @Test
  public void testInvalidParamLengthExecuteCommand() {
    try {

      Exit exit = new Exit();
      ArrayList<String> arg = new ArrayList<>();
      arg.add("1");

      exit.executeCommand(new MockFileSystem(), arg);
      fail("InvalidParamLengthException not caught");

    } catch (InvalidParamLengthException e) {
      return;
    }
  }


}
