import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;

import controller.CollageController;
import controller.RGBACollageController;
import controller.utils.ImageUtil;
import model.Collager;
import model.RGBACollager;
import view.CollagerTextView;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Defines tests for the behavior of the {@code RGBACollageController}.
 */
public class RGBACollageControllerTest {

  private CollageController empty;

  @Before
  public void init() {

    empty = new RGBACollageController(new StringReader(""), new RGBACollager(),
            new CollagerTextView());

  }

  @Test
  public void testInvalidCreateRGBACollageController() {
    try {
      new RGBACollageController(null, new RGBACollager(),
              new CollagerTextView(new StringBuilder()));
      fail("Controller should not make with invalid input");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBACollageController(new StringReader(""), null,
              new CollagerTextView(new StringBuilder()));
      fail("Controller should not make with invalid input");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBACollageController(new StringReader(""), new RGBACollager(), null);
      fail("Controller should not make with invalid input");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }
  }

  @Test
  public void testInvalidNewProject() {
    Readable in1 = new StringReader("new-project f 2 quit");
    Appendable out1 = new StringBuilder();

    new RGBACollageController(in1, new RGBACollager(), new CollagerTextView(out1)).runCollage();

    assertEquals("Invalid arguments for command \"new-project\". Type \"help\" to see " +
            "command usage.\nInvalid height or width was given\nQuit!\n", out1.toString());

    Readable in2 = new StringReader("new-project 2 f quit");
    Appendable out2 = new StringBuilder();

    new RGBACollageController(in2, new RGBACollager(), new CollagerTextView(out2)).runCollage();

    assertEquals("Invalid arguments for command \"new-project\". Type \"help\" to see " +
            "command usage.\nInvalid height or width was given\nQuit!\n", out2.toString());

    Readable in3 = new StringReader("new-project -2 2 quit");
    Appendable out3 = new StringBuilder();

    new RGBACollageController(in3, new RGBACollager(), new CollagerTextView(out3)).runCollage();

    assertEquals("Invalid height or width was given\nQuit!\n", out3.toString());

    Readable in4 = new StringReader("new-project 2 -2 quit");
    Appendable out4 = new StringBuilder();

    new RGBACollageController(in4, new RGBACollager(), new CollagerTextView(out4)).runCollage();

    assertEquals("Invalid height or width was given\nQuit!\n", out4.toString());

    try {
      Readable in5 = new StringReader("new-project");
      Appendable out5 = new StringBuilder();

      new RGBACollageController(in5, new RGBACollager(), new CollagerTextView(out5)).runCollage();
      fail("Controller should not add project if input ends preemptively");
    } catch (IllegalStateException expected) {
      // Do nothing, test passed
    }

    try {
      Readable in6 = new StringReader("new-project 5");
      Appendable out6 = new StringBuilder();

      new RGBACollageController(in6, new RGBACollager(), new CollagerTextView(out6)).runCollage();
      fail("Controller should not add project if input ends preemptively");
    } catch (IllegalStateException expected) {
      // Do nothing, test passed
    }
  }

  @Test
  public void testValidNewProject() {
    Readable in1 = new StringReader("new-project 4 4 quit");
    Appendable out1 = new StringBuilder();

    new RGBACollageController(in1, new RGBACollager(), new CollagerTextView(out1)).runCollage();

    assertEquals("Quit!\n", out1.toString());

    Readable in2 = new StringReader("new-project 4 4 new-project 6 6 quit");
    Appendable out2 = new StringBuilder();

    new RGBACollageController(in2, new RGBACollager(), new CollagerTextView(out2)).runCollage();

    assertEquals("Quit!\n", out2.toString());
  }

  @Test
  public void testInvalidLoadProject() {
    Readable in1 = new StringReader("load-project ./res/testLoadSource.ppm quit");
    Appendable out1 = new StringBuilder();

    new RGBACollageController(in1, new RGBACollager(), new CollagerTextView(out1)).runCollage();

    assertEquals("Invalid Collage file: plain RAW file should begin with C1\nQuit!\n",
            out1.toString());

    Readable in2 = new StringReader("load-project skjdfkds quit");
    Appendable out2 = new StringBuilder();

    new RGBACollageController(in2, new RGBACollager(), new CollagerTextView(out2)).runCollage();

    assertEquals("File skjdfkds not found\nQuit!\n", out2.toString());
  }

  @Test
  public void testValidLoadProject() {
    Readable in = new StringReader("load-project ./res/testLoadSource.collage quit");
    Appendable out = new StringBuilder();

    new RGBACollageController(in, new RGBACollager(), new CollagerTextView(out)).runCollage();

    assertEquals("Quit!\n", out.toString());
  }

  @Test
  public void testInvalidSaveProject() {
    Readable in = new StringReader("save-project testLoadResult.collage quit");
    Appendable out = new StringBuilder();

    new RGBACollageController(in, new RGBACollager(), new CollagerTextView(out)).runCollage();

    assertEquals("Cannot save a project that does not exist\nQuit!\n", out.toString());
  }

  @Test
  public void testValidSaveProject() {
    Readable in = new StringReader("load-project ./res/testLoadSource.collage save-project " +
            "testLoadResult.collage quit");
    Appendable out = new StringBuilder();

    new RGBACollageController(in, new RGBACollager(), new CollagerTextView(out)).runCollage();

    assertEquals("Quit!\n", out.toString());

    try {
      String fileContents = Files.readString(Path.of("testLoadResult.collage"));
      assertEquals("C1\n5 4\n255\ntest normal\n2 2 2 255\n5 3 1 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n2 5 21 255\n57 3 18 255\n0 0 0 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n",
              fileContents);
      Files.delete(Path.of("testLoadResult.collage"));
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }
  }

  @Test
  public void testInvalidAddLayer() {
    Readable in1 = new StringReader("add-layer test quit");
    Appendable out1 = new StringBuilder();

    new RGBACollageController(in1, new RGBACollager(), new CollagerTextView(out1)).runCollage();

    assertEquals("Cannot add layer to a project that does not exist\nQuit!\n",
            out1.toString());

    Readable in2 = new StringReader("new-project 4 4 add-layer test add-layer test quit");
    Appendable out2 = new StringBuilder();

    new RGBACollageController(in2, new RGBACollager(), new CollagerTextView(out2)).runCollage();

    assertEquals("Name already exists\nQuit!\n", out2.toString());
  }

  @Test
  public void testValidAddLayer() {
    Readable in = new StringReader("load-project ./res/testLoadSource.collage add-layer " +
            "test2 save-project testLoadResult.collage quit");
    Appendable out = new StringBuilder();

    new RGBACollageController(in, new RGBACollager(), new CollagerTextView(out)).runCollage();

    assertEquals("Quit!\n", out.toString());

    try {
      String fileContents = Files.readString(Path.of("./res/testLoadSource.collage"));
      assertEquals("C1\n5 4\n255\ntest normal\n2 2 2 255\n5 3 1 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n2 5 21 255\n57 3 18 255\n0 0 0 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n",
              fileContents);
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }

    try {
      String fileContents = Files.readString(Path.of("testLoadResult.collage"));
      assertEquals("C1\n5 4\n255\ntest normal\n2 2 2 255\n5 3 1 255\n" +
              "0 0 0 255\n0 0 0 255\n0 0 0 255\n2 5 21 255\n57 3 18 255\n0 0 0 255\n" +
              "0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n" +
              "0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n" +
              "test2 normal\n255 255 255 0\n255 255 255 0\n255 255 255 0\n" +
              "255 255 255 0\n255 255 255 0\n255 255 255 0\n255 255 255 0\n" +
              "255 255 255 0\n255 255 255 0\n255 255 255 0\n255 255 255 0\n" +
              "255 255 255 0\n255 255 255 0\n255 255 255 0\n255 255 255 0\n" +
              "255 255 255 0\n255 255 255 0\n255 255 255 0\n255 255 255 0\n" +
              "255 255 255 0\n", fileContents);
      Files.delete(Path.of("testLoadResult.collage"));
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }
  }

  @Test
  public void testInvalidAddImageToLayer() {
    Readable in1 = new StringReader("load-project ./res/testLoadSource.collage " +
            "add-image-to-layer test2 ./res/testLoadSource.ppm 0 0 quit");
    Appendable out1 = new StringBuilder();

    new RGBACollageController(in1, new RGBACollager(), new CollagerTextView(out1)).runCollage();

    assertEquals("No layer with this name exists\nQuit!\n", out1.toString());

    Readable in2 = new StringReader("load-project ./res/testLoadSource.collage " +
            "add-image-to-layer test ./res/testLoadSource.collage 0 0 quit");
    Appendable out2 = new StringBuilder();

    new RGBACollageController(in2, new RGBACollager(), new CollagerTextView(out2)).runCollage();

    assertEquals("Invalid PPM file: plain RAW file should begin with P3\nQuit!\n",
            out2.toString());

    Readable in3 = new StringReader("load-project ./res/testLoadSource.collage " +
            "add-image-to-layer test ./res/testLoadSource.ppm 1 0 quit");
    Appendable out3 = new StringBuilder();

    new RGBACollageController(in3, new RGBACollager(), new CollagerTextView(out3)).runCollage();

    assertEquals("Invalid starting X position or image width, image must be fully " +
            "contained\nQuit!\n", out3.toString());

    Readable in4 = new StringReader("load-project ./res/testLoadSource.collage " +
            "add-image-to-layer test ./res/testLoadSource.ppm 0 1 quit");
    Appendable out4 = new StringBuilder();

    new RGBACollageController(in4, new RGBACollager(), new CollagerTextView(out4)).runCollage();

    assertEquals("Invalid starting Y position or image height, image must be fully " +
            "contained\nQuit!\n", out4.toString());

    Readable in5 = new StringReader("load-project ./res/testLoadSource.collage " +
            "add-image-to-layer test ./res/testLoadSource.ppm -1 0 quit");
    Appendable out5 = new StringBuilder();

    new RGBACollageController(in5, new RGBACollager(), new CollagerTextView(out5)).runCollage();

    assertEquals("Invalid coordinates were given\nQuit!\n", out5.toString());

    Readable in6 = new StringReader("load-project ./res/testLoadSource.collage " +
            "add-image-to-layer test2 ./res/testLoadSource.ppm 0 -1 quit");
    Appendable out6 = new StringBuilder();

    new RGBACollageController(in6, new RGBACollager(), new CollagerTextView(out6)).runCollage();

    assertEquals("Invalid coordinates were given\nQuit!\n", out6.toString());

    Readable in7 = new StringReader("add-image-to-layer test ./res/testLoadSource.ppm 0 0 quit");
    Appendable out7 = new StringBuilder();

    new RGBACollageController(in7, new RGBACollager(), new CollagerTextView(out7)).runCollage();

    assertEquals("Cannot add an image to a project that does not exist\nQuit!\n",
            out7.toString());
  }

  @Test
  public void testValidAddImageToLayer() {
    Readable in = new StringReader("load-project ./res/testLoadSource.collage add-layer test2 " +
            "add-image-to-layer test2 ./res/testLoadSource.ppm 0 0 save-project " +
            "testLoadResult.collage quit");
    Appendable out = new StringBuilder();

    new RGBACollageController(in, new RGBACollager(), new CollagerTextView(out)).runCollage();

    assertEquals("Quit!\n", out.toString());

    try {
      String fileContents = Files.readString(Path.of("./res/testLoadSource.collage"));
      assertEquals("C1\n5 4\n255\ntest normal\n2 2 2 255\n5 3 1 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n2 5 21 255\n57 3 18 255\n0 0 0 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n",
              fileContents);
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }

    try {
      String fileContents = Files.readString(Path.of("testLoadResult.collage"));
      assertEquals("C1\n5 4\n255\ntest normal\n2 2 2 255\n5 3 1 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n2 5 21 255\n57 3 18 255\n0 0 0 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n" +
                      "test2 normal\n2 2 2 255\n5 3 1 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n" +
                      "2 5 21 255\n" +
                      "57 3 18 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n" +
                      "0 0 0 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n" +
                      "0 0 0 255\n",
              fileContents);
      Files.delete(Path.of("testLoadResult.collage"));
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }
  }

  @Test
  public void testInvalidSetFilter() {
    Readable in1 = new StringReader("set-filter test red_filter quit");
    Appendable out1 = new StringBuilder();

    new RGBACollageController(in1, new RGBACollager(), new CollagerTextView(out1)).runCollage();

    assertEquals("Cannot set a filter to a project that does not exist\nQuit!\n",
            out1.toString());

    Readable in2 = new StringReader("load-project ./res/testLoadSource.collage set-filter test2 " +
            "red_filter quit");
    Appendable out2 = new StringBuilder();

    new RGBACollageController(in2, new RGBACollager(), new CollagerTextView(out2)).runCollage();

    assertEquals("No layer with this name exists\nQuit!\n", out2.toString());

    Readable in3 = new StringReader("load-project ./res/testLoadSource.collage set-filter test " +
            "sdfsdf quit");
    Appendable out3 = new StringBuilder();

    new RGBACollageController(in3, new RGBACollager(), new CollagerTextView(out3)).runCollage();

    assertEquals("No filter with this name exists\nQuit!\n", out3.toString());
  }

  @Test
  public void testValidSetFilter() {
    Readable in1 = new StringReader("load-project ./res/testLoadSource.collage set-filter test " +
            "red_filter save-project testLoadResult.collage quit");
    Appendable out1 = new StringBuilder();

    new RGBACollageController(in1, new RGBACollager(), new CollagerTextView(out1)).runCollage();

    assertEquals("Quit!\n", out1.toString());

    try {
      String fileContents = Files.readString(Path.of("./res/testLoadSource.collage"));
      assertEquals("C1\n5 4\n255\ntest normal\n2 2 2 255\n5 3 1 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n2 5 21 255\n57 3 18 255\n0 0 0 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n",
              fileContents);
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }

    try {
      String fileContents = Files.readString(Path.of("testLoadResult.collage"));
      assertEquals("C1\n5 4\n255\ntest red_filter\n2 2 2 255\n5 3 1 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n2 5 21 255\n57 3 18 255\n0 0 0 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n",
              fileContents);
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }

    Readable in2 = new StringReader("load-project ./res/testLoadSource.collage set-filter test " +
            "normal save-project testLoadResult.collage quit");
    Appendable out2 = new StringBuilder();

    new RGBACollageController(in2, new RGBACollager(), new CollagerTextView(out2)).runCollage();

    assertEquals("Quit!\n", out2.toString());

    try {
      String fileContents = Files.readString(Path.of("./res/testLoadSource.collage"));
      assertEquals("C1\n5 4\n255\ntest normal\n2 2 2 255\n5 3 1 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n2 5 21 255\n57 3 18 255\n0 0 0 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n",
              fileContents);
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }

    try {
      String fileContents = Files.readString(Path.of("testLoadResult.collage"));
      assertEquals("C1\n5 4\n255\ntest normal\n2 2 2 255\n5 3 1 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n2 5 21 255\n57 3 18 255\n0 0 0 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n",
              fileContents);
      Files.delete(Path.of("testLoadResult.collage"));
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }
  }

  @Test
  public void testInvalidSaveImage() {
    Readable in = new StringReader("save-image testLoadResult.ppm quit");
    Appendable out = new StringBuilder();

    new RGBACollageController(in, new RGBACollager(), new CollagerTextView(out)).runCollage();

    assertEquals("Cannot save an image to a project that does not exist\nQuit!\n",
            out.toString());
  }

  @Test
  public void testValidSaveImage() {
    Readable in1 = new StringReader("load-project ./res/testLoadSource.collage save-image " +
            "testLoadResult.ppm quit");
    Appendable out1 = new StringBuilder();

    new RGBACollageController(in1, new RGBACollager(), new CollagerTextView(out1)).runCollage();

    assertEquals("Quit!\n", out1.toString());

    try {
      String fileContents = Files.readString(Path.of("testLoadResult.ppm"));
      assertEquals("P3\n5 4\n255\n2 2 2 5 3 1 0 0 0 0 0 0 0 0 0\n" +
                      "2 5 21 57 3 18 0 0 0 0 0 0 0 0 0\n0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
                      "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n",
              fileContents);
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }

    Readable in2 = new StringReader("load-project ./res/testLoadSource.collage set-filter test" +
            " red_filter save-image testLoadResult.ppm quit");
    Appendable out2 = new StringBuilder();

    new RGBACollageController(in2, new RGBACollager(), new CollagerTextView(out2)).runCollage();

    assertEquals("Quit!\n", out2.toString());

    try {
      String fileContents = Files.readString(Path.of("testLoadResult.ppm"));
      assertEquals("P3\n5 4\n255\n2 0 0 5 0 0 0 0 0 0 0 0 0 0 0\n" +
                      "2 0 0 57 0 0 0 0 0 0 0 0 0 0 0\n0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
                      "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n",
              fileContents);
      Files.delete(Path.of("testLoadResult.ppm"));
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }
  }

  @Test
  public void testInvalidQuit() {
    Readable in = new StringReader("load-project quit");
    Appendable out = new StringBuilder();

    new RGBACollageController(in, new RGBACollager(), new CollagerTextView(out)).runCollage();

    assertEquals("File quit not found\n", out.toString());
  }

  @Test
  public void testValidQuit() {
    Readable in1 = new StringReader("quit");
    Appendable out1 = new StringBuilder();

    new RGBACollageController(in1, new RGBACollager(), new CollagerTextView(out1)).runCollage();

    assertEquals("Quit!\n", out1.toString());

    Readable in2 = new StringReader("load-project ./res/testLoadSource.collage quit");
    Appendable out2 = new StringBuilder();

    new RGBACollageController(in2, new RGBACollager(), new CollagerTextView(out2)).runCollage();

    assertEquals("Quit!\n", out2.toString());
  }

  @Test
  public void testHelp() {
    Readable in = new StringReader("help");
    Appendable out = new StringBuilder();

    new RGBACollageController(in, new RGBACollager(), new CollagerTextView(out)).runCollage();

    assertEquals("new-project CANVAS-HEIGHT CANVAS-WIDTH -- creates a blank project " +
            "with " +
            "the provided dimensions\n" +
            "    CANVAS-HEIGHT and CANVAS-WIDTH must be integers\n" +
            "    there are no layers by default\n" +
            "load-project PATH-TO-PROJECT-FILE -- loads a project from a .collage formatted " +
            "file\nsave-project PATH-TO-PROJECT-FILE -- saves the project in the .collage " +
            "format to the specified file\nadd-layer LAYER-NAME -- adds a layer to the project " +
            "with the specified name\n" +
            "add-image-to-layer LAYER-NAME PATH-TO-IMAGE-FILE X-POS Y-POS -- adds a PPM " +
            "formatted " +
            "image to the specified layer at the specified coordinates\n" +
            "    X-POS and Y-POS must be integers\n" +
            "set-filter LAYER-NAME FILTER-NAME -- sets the given layer to have the specified " +
            "filter\n" +
            "    available filters are:\n" +
            "        normal -- no filter applied\n" +
            "        red_filter -- only displays the red channels of the layer\n" +
            "        green_filter -- only displays the green channels of the layer\n" +
            "        blue_filter -- only displays the blue channels of the layer\n" +
            "        brighten_intensity -- brightens the layer using the average intensity of " +
            "pixels\n" +
            "        darken_intensity -- darkens the layer using the average intensity of " +
            "pixels\n" +
            "        brighten_luma -- brightens the layer using the weighted luma value\n" +
            "        darken_luma -- darkens the layer using the weighted luma value\n" +
            "        brighten_max -- brightens the layer using the maximum intensity of the " +
            "pixels\n" +
            "        darken_max -- brightens the layer using the maximum intensity of the " +
            "pixels\n" +
            "        darken_multiply -- darkens the layer by changing its L value in its" +
            " HSL form \n" +
            "        brighten_screen -- brightens the layer by changing its L value in " +
            "its HSL form\n" +
            "        inversion_difference --inverts the colors of the image by using" +
            " the different of RGB values of the composite image and top image \n" +
            "save-image PATH-TO-IMAGE-FILE -- save the result of applying all filters on " +
            "their respective layers, and combining those layers\n" +
            "quit -- quits the program\n" +
            "help -- displays this dialog\n", out.toString());
  }

  @Test
  public void testInvalidCommand() {
    Readable in = new StringReader("ragh");
    Appendable out = new StringBuilder();

    new RGBACollageController(in, new RGBACollager(), new CollagerTextView(out)).runCollage();

    assertEquals("Invalid command, type \"help\" for help.\n", out.toString());
  }

  @Test
  public void createInvalidProject() {

    try {
      empty.createProject(-1, 3);
      fail("Should not create image with invalid inputs");
    } catch (IllegalArgumentException expected) {
      // Do nothing, tests passed
    }

    try {
      empty.createProject(1, -3);
      fail("Should not create image with invalid inputs");
    } catch (IllegalArgumentException expected) {
      // Do nothing, tests passed
    }

    try {
      empty.createProject(-1, -3);
      fail("Should not create image with invalid inputs");
    } catch (IllegalArgumentException expected) {
      // Do nothing, tests passed
    }

  }

  @Test
  public void createInvalidCollage() {

    try {
      new RGBACollager(-1, 3);
      fail("Should not create collager with invalid inputs");
    } catch (IllegalArgumentException expected) {
      // Do nothing, tests passed
    }

    try {
      new RGBACollager(1, -3);
      fail("Should not create collager with invalid inputs");
    } catch (IllegalArgumentException expected) {
      // Do nothing, tests passed
    }

    try {
      new RGBACollager(-1, -3);
      fail("Should not create collage with invalid inputs");
    } catch (IllegalArgumentException expected) {
      // Do nothing, tests passed
    }

  }

  @Test
  public void testInvalidSetFilterDirect() {

    try {
      empty.setFilter("test", "normal");
      fail("Method should not run before project exists");
    } catch (IllegalStateException expected) {
      // Do nothing, test passed
    }

    empty.loadProject("./res/testLoadSource.collage");

    try {
      empty.setFilter("test", "g");
      fail("Filter should not set with invalid name");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      empty.setFilter("g", "normal");
      fail("Filter should not set with invalid name");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      empty.setFilter(null, "normal");
      fail("Filter should not set with invalid name");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      empty.setFilter("test", null);
      fail("Filter should not set with invalid name");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }
  }

  @Test
  public void testInvalidAddImage() {

    try {
      empty.addImage("test", "./res/testLoadSource.ppm", 0, 0);
      fail("Image should not add before project exists");
    } catch (IllegalStateException expected) {
      // Do nothing, test passed
    }

    try {
      empty.createProject(4, 5);
      empty.addImage("test", ImageUtil.readPPM("./res/testLoadSource.ppm"), 0, 0);
      fail("Image should not add if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      empty.createProject(4, 5);
      empty.addLayer("test");
      empty.addImage("test", ImageUtil.readPPM("hsgvligeroasgr.ppm"), 0, 0);
      fail("Image should not add if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      empty.createProject(4, 5);
      empty.addLayer("test");
      empty.addImage("tests", ImageUtil.readPPM("./res/testLoadSource.ppm"), 0, 0);
      fail("Image should not add if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      empty.createProject(2, 3);
      empty.addLayer("test");
      empty.addImage("test", "./res/testLoadSource.ppm", 0, 0);
      fail("Image should not add if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      empty.createProject(4, 5);
      empty.addLayer("test");
      empty.addImage("test", ImageUtil.readPPM("./res/testLoadSource.ppm"), 1, 0);
      fail("Image should not add if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      empty.createProject(4, 5);
      empty.addLayer("test");
      empty.addImage("test", ImageUtil.readPPM("./res/testLoadSource.ppm"), 0, 1);
      fail("Image should not add if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      empty.createProject(4, 5);
      empty.addLayer("test");
      empty.addImage("test", ImageUtil.readPPM("./res/testLoadSource.collage"), 0, 0);
      fail("Image should not add if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      empty.createProject(4, 5);
      empty.addLayer("test");
      empty.addImage(null, ImageUtil.readPPM("./res/testLoadSource.ppm"), 0, 0);
      fail("Image should not add if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      empty.createProject(4, 5);
      empty.addLayer("test");
      empty.addImage("test", (String) null, 0, 0);
      fail("Image should not add if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }
  }

  @Test
  public void testValidAddLayerDirect() {

    empty.loadProject("./res/testLoadSource.collage");
    empty.saveProject("testLoadResult.collage");

    try {
      String fileContents = Files.readString(Path.of("testLoadResult.collage"));
      assertEquals("C1\n5 4\n255\ntest normal\n2 2 2 255\n5 3 1 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n2 5 21 255\n57 3 18 255\n0 0 0 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n",
              fileContents);
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }

    empty.addLayer("test2");
    empty.saveProject("testLoadResult.collage");

    try {
      String fileContents = Files.readString(Path.of("testLoadResult.collage"));
      assertEquals("C1\n5 4\n255\ntest normal\n2 2 2 255\n5 3 1 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n2 5 21 255\n57 3 18 255\n0 0 0 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n" +
                      "0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n0 0 0 255\n" +
                      "test2 normal\n255 255 255 0\n255 255 255 0\n" +
                      "255 255 255 0\n255 255 255 0\n255 255 255 0\n255 255 255 0\n" +
                      "255 255 255 0\n255 255 255 0\n" +
                      "255 255 255 0\n255 255 255 0\n255 255 255 0\n255 255 255 0\n" +
                      "255 255 255 0\n255 255 255 0\n" +
                      "255 255 255 0\n255 255 255 0\n255 255 255 0\n255 255 255 0\n" +
                      "255 255 255 0\n255 255 255 0\n",
              fileContents);
      Files.delete(Path.of("testLoadResult.collage"));
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }
  }

  @Test
  public void testInvalidAddLayerDirect() {

    try {
      empty.addLayer("test");
      fail("Method should not run before project exists");
    } catch (IllegalStateException expected) {
      // Do nothing, test passed
    }

    empty.createProject(3, 4);

    try {
      empty.addLayer(null);
      fail("Should not create layer when name is null");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    boolean firstPassed = false;

    try {
      empty.addLayer("test");
      firstPassed = true;
      empty.addLayer("test");
      fail("Should not create layer when name is null");
    } catch (IllegalArgumentException expected) {
      assertTrue(firstPassed);
    }
  }

  @Test
  public void testInvalidGetImageAtLayer() {

    try {
      empty.getImageAt("");
      fail("Should not get image at layer during invalid state");
    } catch (IllegalStateException expected) {
      // Do nothing, test passes
    }

    try {
      Collager collager = new RGBACollager();
      collager.createProject(3, 3);
      collager.getImageAtLayer("");
      fail("Should not get image at layer when invalid arguments");
    } catch (IllegalStateException expected) {
      // Do nothing, test passes
    }

    try {
      Collager collager = new RGBACollager();
      collager.createProject(3, 3);
      collager.addLayer("jon");
      collager.getImageAtLayer("");
      fail("Should not get image at layer when invalid arguments");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passes
    }

    try {
      Collager collager = new RGBACollager();
      collager.createProject(3, 3);
      collager.addLayer("jon");
      collager.getImageAtLayer(null);
      fail("Should not get image at layer when invalid arguments");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passes
    }
  }

  @Test
  public void testInvalidGetFinalImage() {

    try {
      empty.getFinalImage();
      fail("Should not get image at layer during invalid state");
    } catch (IllegalStateException expected) {
      // Do nothing, test passes
    }

    try {
      Collager collager = new RGBACollager();
      collager.createProject(3, 3);
      empty.getFinalImage();
      fail("Should not get image at layer when invalid arguments");
    } catch (IllegalStateException expected) {
      // Do nothing, test passes
    }
  }


  @Test
  public void testGetLayerNames() {
    empty.createProject(2, 2);
    empty.addLayer("layer1");
    empty.addLayer("layer2");
    empty.addLayer("layer3");

    String[] expectedNames = {"layer1", "layer2", "layer3"};

    assertArrayEquals(expectedNames, empty.getLayerNames().toArray(new String[0]));

    empty.addLayer("layer4");

    expectedNames = new String[]{"layer1", "layer2", "layer3", "layer4"};

    assertArrayEquals(expectedNames, empty.getLayerNames().toArray(new String[0]));
  }

  @Test
  public void testGetInvalidLayerNames() {
    try {
      empty.getLayerNames();
      fail("Should not work when project does not exist");
    } catch (IllegalStateException expected) {
      // Do nothing, test passed
    }

    empty.createProject(2, 2);

    try {
      empty.getLayerNames();
      fail("Should not work when no layers exist");
    } catch (IllegalStateException expected) {
      // Do nothing, test passed
    }
  }

  @Test
  public void testGetFilterNames() {
    assertArrayEquals(new String[]{"normal", "red_filter", "blue_filter", "green_filter",
        "brighten_intensity", "darken_intensity", "brighten_luma", "darken_luma",
        "brighten_max", "darken_max", "darken_multiply", "brighten_screen",
        "inversion_difference"}, empty.getFilterNames());
  }
}
