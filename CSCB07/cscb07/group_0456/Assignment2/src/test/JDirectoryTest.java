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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import exceptions.AlreadyExistsException;
import javax.naming.InvalidNameException;
import org.junit.Before;
import org.junit.Test;
import system.Directory;
import system.JDirectory;
import system.JPath;
import system.Path;

public class JDirectoryTest {

  private Directory root;
  
  @Before 
  public void setUp() {
    Path p = new JPath("Valid Path for root");
    root = new JDirectory("root", p, null);
  }
  
  @Test
  public void getNameTest() {
    assertEquals("root", root.getName());
  }
  
  @Test
  public void addSubDictionaryWithValidArg() throws InvalidNameException, AlreadyExistsException {
    String d1 = "childDir1";
    root.addSubDictionary(d1);
    assertEquals(d1, root.getChildren().get(0).getName());
  }
  
  @Test
  public void addSubDictionaryWithInvalidName() throws AlreadyExistsException {
    try {
      String d1 = "%#";
      root.addSubDictionary(d1);
      fail("InvalidNameException not caught");
    } catch (InvalidNameException e) {
      return;
    }
  }
  
  @Test
  public void addDuplicateSubDir() throws InvalidNameException {
    try {
      String d1 = "childDir1";
      root.addSubDictionary(d1);
      root.addSubDictionary(d1);
      fail("AlreadyExistsException not caught");
    } catch (AlreadyExistsException e) {
      return;
    }
  }
  
  @Test
  public void addSubFileWithValidName() throws InvalidNameException, AlreadyExistsException {
    String f1 = "f1";
    root.addSubFile(f1);
    assertEquals(f1, root.getFile().get(0).getName());
  }
  
  @Test
  public void addSubFileWithInvalidName() throws AlreadyExistsException {
    try {
      String f1 = "@!&*()";
      root.addSubFile(f1);
      fail("InvalidNameException not caught");
    } catch (InvalidNameException e) {
      return;
    }
  }
  
  @Test
  public void addDuplicateSubFile() throws InvalidNameException, AlreadyExistsException {
    try {
      String f1 = "f1";
      root.addSubFile(f1);
      root.addSubFile(f1);
      fail("AlreadyExistsException not caught");
    } catch (AlreadyExistsException e) {
      return;
    }
  }
  
  @Test
  public void getChildrenTest() throws InvalidNameException, AlreadyExistsException {
    String d1 = "childDir1";
    String d2 = "childDir2";
    root.addSubDictionary(d1);
    root.addSubDictionary(d2);
    assertEquals(d1, root.getChildren().get(0).getName());
    assertEquals(d2, root.getChildren().get(1).getName());
    assertTrue(root.getChildren().size() == 2);
  }
  
  @Test
  public void getFileTest() throws InvalidNameException, AlreadyExistsException {
    String f1 = "f1";
    String f2 = "f2";
    root.addSubFile(f1);
    root.addSubFile(f2);
    assertEquals(f1, root.getFile().get(0).getName());
    assertEquals(f2, root.getFile().get(1).getName());
    assertTrue(root.getFile().size() == 2);
  }
  
  @Test
  public void removeChildDirTest() throws InvalidNameException, AlreadyExistsException {
    String d1 = "childDir1";
    root.addSubDictionary(d1);
    root.removeChild(root.getChildren().get(0));
    assertTrue(root.getChildren().isEmpty());
  }
  
  @Test
  public void removeChildFileTest() throws InvalidNameException, AlreadyExistsException {
    String f1 = "f1";
    root.addSubFile(f1);
    root.removeChild(root.getFile().get(0));
    assertTrue(root.getFile().isEmpty());
  }
  
  @Test
  public void getPathTest() {
    assertEquals("./Valid Path for root/", root.getPath());
  }
  
  @Test
  public void getSubDirectoryTest() throws InvalidNameException, AlreadyExistsException {
    String d1 = "childDir1";
    root.addSubDictionary(d1);
    Directory d = root.getSubDirectory("childDir1");
    assertEquals("childDir1", d.getName());
    assertEquals("./Valid Path for root/childDir1/", d.getPath());
  }
  
  @Test
  public void replaceThisWithInvalidDirTest() throws InvalidNameException, AlreadyExistsException {
    String d1 = "childDir1";
    root.addSubDictionary(d1);
    Directory dir1 = root.getSubDirectory("childDir1");
    String f1 = "f1";
    dir1.addSubFile(f1);
    dir1.getFile().get(0).setText("1");
    String d3 = "chileOfChildDir1";
    dir1.addSubDictionary(d3);
    dir1.replaceThis(dir1);
    assertEquals(1, dir1.getChildren().size());
    assertEquals(1, dir1.getFile().size());
  }
  
  @Test
  public void replaceThisTest() throws InvalidNameException, AlreadyExistsException {
    String d1 = "childDir1";
    root.addSubDictionary(d1);
    Directory dir1 = root.getSubDirectory("childDir1");
    String d2 = "childDir2";
    root.addSubDictionary(d2);
    Directory dir2 = root.getSubDirectory("childDir2");
    String f1 = "f1";
    dir1.addSubFile(f1);
    dir1.getFile().get(0).setText("1");
    String d3 = "chileOfChildDir1";
    dir1.addSubDictionary(d3);
    dir2.replaceThis(dir1);
    assertEquals("chileOfChildDir1", dir2.getChildren().get(0).getName());
    assertEquals("f1", dir2.getFile().get(0).getName());
    assertEquals("1", dir2.getFile().get(0).getText());
  }
  
  @Test
  public void getParentTest() throws InvalidNameException, AlreadyExistsException {
    String d1 = "childDir1";
    root.addSubDictionary(d1);
    Directory d = root.getSubDirectory("childDir1");
    assertEquals(root, d.getParent());
  }
   
}
