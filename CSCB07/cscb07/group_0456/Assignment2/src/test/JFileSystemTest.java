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

import exceptions.AlreadyExistsException;
import exceptions.InvalidPathException;
import java.lang.reflect.Field;
import javax.naming.InvalidNameException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import system.Directory;
import system.File;
import system.FileSystem;
import system.JFileSystem;
import system.JPath;
import system.Path;

public class JFileSystemTest {


  private FileSystem fs;

  @Before
  public void setUp() throws Exception {
    fs = JFileSystem.createFileSystemInstance(null, 0);
  }

  @After
  public void tearDown() throws Exception {
    Field field = (fs.getClass()).getDeclaredField("fs");
    field.setAccessible(true);
    field.set(null, null);
  }

  /*
   ** Test getting a recursive list with directory and file names listed
   */
  @Test
  public void testDontPrintPathRecursiveList()
      throws InvalidNameException, InvalidPathException, AlreadyExistsException {
    fs.addDirectory(new JPath("/some"));
    fs.addDirectory(new JPath("/some/valid"));
    fs.addDirectory(new JPath("/some/valid/path"));
    fs.addDirectory(new JPath("/some/valid/path/dir1"));
    fs.addDirectory(new JPath("/some/valid/path/dir1/dir2"));
    fs.addDirectory(new JPath("/some/valid/path/dir1/dir2/dir3"));
    fs.addFile(new JPath("/some/valid/path/dir1/dir2/dir3/file1"));

    Directory d = fs.findDirectory(new JPath("/some"));


    String list = fs.recursiveList(d, 0, false);
    assertEquals(
        "some\n\tvalid\n\t\tpath\n\t\t\tdir1\n\t\t\t\tdir2\n\t\t\t\t\tdir3\n\t\t\t\t\t\tfile1\n",
        list);
  }

  /*
   ** Test getting a recursive list with directory and file paths listed
   */
  @Test
  public void testPrintPathRecursiveList()
      throws InvalidNameException, InvalidPathException, AlreadyExistsException {
    fs.addDirectory(new JPath("/some"));
    fs.addDirectory(new JPath("/some/valid"));
    fs.addDirectory(new JPath("/some/valid/path"));
    fs.addDirectory(new JPath("/some/valid/path/dir1"));

    Directory d = fs.findDirectory(new JPath("/some"));


    String list = fs.recursiveList(d, 0, true);
    assertEquals(
        "/some/:\n\t/some/valid/:\n\t\t/some/valid/path/:\n\t\t\t/some/valid/path/dir1/:\n", list);
  }

  /*
   ** Add a file with a relative path to the file system
   */
  @Test
  public void testRelativePathAddFile()
      throws InvalidPathException, InvalidNameException, AlreadyExistsException {

    fs.addFile(new JPath("./file1"));
    File found = fs.findFile(new JPath("./file1"));

    assertEquals("file1", found.getName());

  }

  /*
   ** Add a file with a absolute path to the file system
   */
  @Test
  public void testAbsolutePathAddFile()
      throws InvalidPathException, InvalidNameException, AlreadyExistsException {

    fs.addFile(new JPath("/file1"));
    File found = fs.findFile(new JPath("/file1"));

    assertEquals("file1", found.getName());

  }

  /*
   ** Add a file with a parent path to the file system
   */
  @Test
  public void testParentPathAddFile()
      throws InvalidPathException, InvalidNameException, AlreadyExistsException {

    fs.addDirectory(new JPath("/dir1"));
    fs.addFile(new JPath("/dir1/../file1"));
    File found = fs.findFile(new JPath("/file1"));

    assertEquals("file1", found.getName());

  }

  /*
   ** Try adding file with a invalid path to the file system
   */
  @Test
  public void testInvalidPathAddFile() throws InvalidNameException, AlreadyExistsException {

    try {
      fs.addFile(new JPath("/invalid/path/file1"));
      fail("InvalidPathException was not caught");
    } catch (InvalidPathException e) {
      return;
    }

  }

  /*
   ** Try adding a file with a invalid name to the file system
   */
  @Test
  public void testInvalidNameAddFile() throws InvalidPathException, AlreadyExistsException {

    try {
      fs.addFile(new JPath("/file1*&#"));
      fail("InvalidNameException was not caught");
    } catch (InvalidNameException e) {
      return;
    }

  }

  /*
   ** Try adding a file that already exists to the file system
   */
  @Test
  public void testAlreadyExistsAddFile() throws InvalidPathException, InvalidNameException {

    try {
      fs.addFile(new JPath("/file1"));
      fs.addFile(new JPath("/file1"));
      fail("AlreadyExistsException was not caught");
    } catch (AlreadyExistsException e) {
      return;
    }

  }


  /*
   ** Add a directory with a relative path to the file system
   */
  @Test
  public void testRelativePathAddDirectory()
      throws InvalidPathException, InvalidNameException, AlreadyExistsException {

    fs.addDirectory(new JPath("./dir1"));
    Directory found = fs.findDirectory(new JPath("./dir1"));

    assertEquals("dir1", found.getName());

  }

  /*
   ** Add a directory with a absolute path to the file system
   */
  @Test
  public void testAbsolutePathAddDirectory()
      throws InvalidPathException, InvalidNameException, AlreadyExistsException {

    fs.addDirectory(new JPath("/dir1"));
    Directory found = fs.findDirectory(new JPath("/dir1"));

    assertEquals("dir1", found.getName());

  }

  /*
   ** Add a directory with a parent path to the file system
   */
  @Test
  public void testParentPathAddDirectory()
      throws InvalidPathException, InvalidNameException, AlreadyExistsException {

    fs.addDirectory(new JPath("/dir1"));
    fs.addDirectory(new JPath("/dir1/../dir2"));
    Directory found = fs.findDirectory(new JPath("/dir2"));

    assertEquals("dir2", found.getName());

  }

  /*
   ** Try adding directory with a invalid path to the file system
   */
  @Test
  public void testInvalidPathAddDirectory() throws InvalidNameException, AlreadyExistsException {

    try {
      fs.addDirectory(new JPath("/invalid/path/dir1"));
      fail("InvalidPathException was not caught");
    } catch (InvalidPathException e) {
      return;
    }

  }

  /*
   ** Try adding a directory with a invalid name to the file system
   */
  @Test
  public void testInvalidNameAddDirectory() throws InvalidPathException, AlreadyExistsException {

    try {
      fs.addDirectory(new JPath("/dir11*&#"));
      fail("InvalidNameException was not caught");
    } catch (InvalidNameException e) {
      return;
    }

  }

  /*
   ** Try adding a directory that already exists to the file system
   */
  @Test
  public void testAlreadyExistsAddDirectory() throws InvalidPathException, InvalidNameException {

    try {
      fs.addFile(new JPath("/dir1"));
      fs.addFile(new JPath("/dir1"));
      fail("AlreadyExistsException was not caught");
    } catch (AlreadyExistsException e) {
      return;
    }

  }

  /*
   ** Try finding a file in the root directory
   */
  @Test
  public void testInRootFindFile()
      throws InvalidNameException, InvalidPathException, AlreadyExistsException {
    Path p = new JPath("file1");
    fs.addFile(p);
    File f = fs.findFile(p);

    String loc = f.getLocation().toString();
    loc = loc.substring(0, loc.length());

    assertEquals("/", loc);
  }

  /*
   ** Try finding a file not in the root directory
   */
  @Test
  public void testNotInRootFindFile()
      throws InvalidNameException, InvalidPathException, AlreadyExistsException {
    Path p = new JPath("/dir1/file1");
    fs.addDirectory(new JPath("dir1"));
    fs.addFile(p);
    File f = fs.findFile(p);

    String loc = f.getLocation().toString();

    assertEquals("/dir1/", loc);
  }

  /*
   ** Try finding a file in the parent directory
   */
  @Test
  public void testInParentFindFile()
      throws InvalidNameException, InvalidPathException, AlreadyExistsException {
    Path p = new JPath("/dir1/../file1");
    fs.addDirectory(new JPath("/dir1"));
    fs.addFile(p);
    File f = fs.findFile(p);

    String loc = f.getLocation().toString();

    assertEquals("/", loc);
  }

  /*
   ** Try finding a file with an invalid path
   */
  @Test
  public void testInvalidPathFindFile() {
    Path p = new JPath("/invalid/path/file1");
    try {
      File f = fs.findFile(p);
      fail("InvalidPathException was not caught");
    } catch (InvalidPathException e) {
      return;
    }

  }

  /*
   ** Try finding a directory in the root directory
   */
  @Test
  public void testInRootFindDirectory()
      throws InvalidNameException, InvalidPathException, AlreadyExistsException {
    Path p = new JPath("dir1");
    fs.addDirectory(p);
    Directory d = fs.findDirectory(p);

    String loc = d.getPath().toString();

    assertEquals("/dir1/", loc);
  }

  /*
   ** Try finding a directory not in the root directory
   */
  @Test
  public void testNotInRootFindDirectory()
      throws InvalidNameException, InvalidPathException, AlreadyExistsException {
    Path p = new JPath("/dir1/dir1");
    fs.addDirectory(new JPath("dir1"));
    fs.addDirectory(p);
    Directory d = fs.findDirectory(p);

    String loc = d.getPath().toString();

    assertEquals("/dir1/dir1/", loc);
  }

  /*
   ** Try finding a directory in the parent directory
   */
  @Test
  public void testInParentFindDirectory()
      throws InvalidNameException, InvalidPathException, AlreadyExistsException {
    Path p = new JPath("/dir1/dir2/../dir3");
    fs.addDirectory(new JPath("dir1"));
    fs.addDirectory(new JPath("/dir1/dir2"));
    fs.addDirectory(new JPath("/dir1/dir3"));
    Directory d = fs.findDirectory(p);

    String loc = d.getPath().toString();

    assertEquals("/dir1/dir3/", loc);
  }

  /*
   ** Try finding a directory with an invalid path
   */
  @Test
  public void testInvalidPathFindDirectory() {
    Path p = new JPath("/invalid/path/dir1");
    try {
      Directory d = fs.findDirectory(p);
      fail("InvalidPathException was not caught");
    } catch (InvalidPathException e) {
      return;
    }

  }

  /*
   ** Try changing the directory with a valid path
   */
  @Test
  public void testValidPathChangeDirectory()
      throws InvalidNameException, InvalidPathException, AlreadyExistsException {
    Path path = new JPath("somevalidpath");

    fs.addDirectory(path);
    fs.changeDirectory(path);
    assertEquals("/somevalidpath/", fs.getDirectory().getPath());
  }

  /*
   ** Try changing the directory with a invalid path
   */
  @Test
  public void testInvalidPathChangeDirectory() {
    Path path = new JPath("someinvalidpath");

    try {
      fs.changeDirectory(path);
      fail("InvalidPathException was not caught");
    } catch (InvalidPathException e) {
      return;
    }

  }

  /*
   ** Try popping from the directory stack, if its empty
   */
  @Test
  public void testEmptyStackPop() {
    Directory empty = fs.stackPop();

    assertEquals(null, empty);
  }

  /*
   ** Try redirecting to a file with an invalid symbol
   */
  @Test
  public void testInvalidSymbolRedirect()
      throws InvalidNameException, InvalidPathException, AlreadyExistsException {
    try {
      fs.redirect("invalidsymbol", "sometext", "samplefile");
      fail("Didn't catch the invalid symbol");
    } catch (IllegalArgumentException e) {
      return;
    }
  }

  /*
   ** Try redirecting to a file with an invalid name
   */
  @Test
  public void testInvalidFileNameRedirect()
      throws InvalidPathException, AlreadyExistsException, IllegalArgumentException {
    try {
      fs.redirect(">", "sometext", "invalid**file");
      fail("Didn't catch the invalid file name");
    } catch (InvalidNameException e) {
      return;
    }
  }

  /*
   ** Try redirecting to a file with an invalid path
   */
  @Test
  public void testInvalidPathRedirect()
      throws InvalidNameException, AlreadyExistsException, IllegalArgumentException {
    try {
      fs.redirect(">", "sometext", "/invalid/path");
      fail("Didn't catch the invalid path");
    } catch (InvalidPathException e) {
      return;
    }
  }

  /*
   ** Try to overwrite to a file that does not exist
   */
  @Test
  public void testOverwriteNotExistRedirect() throws InvalidNameException, AlreadyExistsException,
      IllegalArgumentException, InvalidPathException {

    fs.redirect(">", "sample text", "file1");
    File f = fs.findFile(new JPath("file1"));

    assertEquals("sample text", f.getText());


  }

  /*
   ** Try to overwrite to a file that already exists
   */
  @Test
  public void testOverwriteAlreadyExistsRedirect() throws InvalidNameException,
      AlreadyExistsException, IllegalArgumentException, InvalidPathException {
    Path p = new JPath("file1");
    fs.addFile(p);
    File f = fs.findFile(p);
    f.setText("some text to be overwritten");

    fs.redirect(">", "sample text", "file1");

    assertEquals("sample text", f.getText());


  }

  /*
   ** Try to append to a file that does not exist
   */
  @Test
  public void testAppendNotExistRedirect() throws InvalidNameException, AlreadyExistsException,
      IllegalArgumentException, InvalidPathException {

    fs.redirect(">>", "sample text", "file1");
    File f = fs.findFile(new JPath("file1"));

    assertEquals("\nsample text", f.getText());


  }

  /*
   ** Try to append to a file that already exists
   */
  @Test
  public void testAppendAlreadyExistsRedirect() throws InvalidNameException, AlreadyExistsException,
      IllegalArgumentException, InvalidPathException {
    Path p = new JPath("file1");
    fs.addFile(p);
    File f = fs.findFile(p);
    f.setText("some text to be appended");

    fs.redirect(">>", "sample text", "file1");

    assertEquals("some text to be appended\nsample text", f.getText());


  }



}
