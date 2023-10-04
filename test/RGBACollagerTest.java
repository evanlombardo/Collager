import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import controller.utils.ImageUtil;
import model.Collager;
import model.Image;
import model.Layer;
import model.Pixel;
import model.RGBACollager;
import model.RGBALayerImage;
import model.RGBAPixel;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Defines tests for the behavior of a {@code RGBACollager}.
 */
public class RGBACollagerTest {

  Collager collage1;

  @Before
  public void init() {

    collage1 = new RGBACollager();

  }

  @Test
  public void createInvalidProject() {

    try {
      collage1.createProject(-1, 3);
      fail("Should not create image with invalid inputs");
    } catch (IllegalArgumentException expected) {
      // Do nothing, tests passed
    }

    try {
      collage1.createProject(1, -3);
      fail("Should not create image with invalid inputs");
    } catch (IllegalArgumentException expected) {
      // Do nothing, tests passed
    }

    try {
      collage1.createProject(-1, -3);
      fail("Should not create image with invalid inputs");
    } catch (IllegalArgumentException expected) {
      // Do nothing, tests passed
    }

  }

  @Test
  public void createValidProject() {
    collage1.createProject(4, 5);
    collage1.addLayer("test");
    ImageUtil.saveImage("test.ppm", collage1.getFinalImage());

    try {
      String fileContents = Files.readString(Path.of("test.ppm"));
      assertEquals("P3\n5 4\n255\n0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n0 0 0 0 0 0 0 0 0 0" +
                      " 0 0 0 0 0\n0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n",
              fileContents);
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }

    collage1.createProject(2, 3);
    collage1.addLayer("test");
    ImageUtil.saveImage("test.ppm", collage1.getFinalImage());

    try {
      String fileContents = Files.readString(Path.of("test.ppm"));
      assertEquals("P3\n3 2\n255\n0 0 0 0 0 0 0 0 0\n0 0 0 0 0 0 0 0 0\n", fileContents);
      Files.delete(Path.of("test.ppm"));
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
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
  public void testInvalidSetFilter() {

    try {
      collage1.setFilter("test", "normal");
      fail("Method should not run before project exists");
    } catch (IllegalStateException expected) {
      // Do nothing, test passed
    }

    ImageUtil.readCollage("./res/testLoadSource.collage", collage1);

    try {
      collage1.setFilter("test", "g");
      fail("Filter should not set with invalid name");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      collage1.setFilter("g", "normal");
      fail("Filter should not set with invalid name");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      collage1.setFilter(null, "normal");
      fail("Filter should not set with invalid name");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      collage1.setFilter("test", null);
      fail("Filter should not set with invalid name");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }
  }

  @Test
  public void testInvalidAddImage() {

    Pixel[][] pixel = new Pixel[1][2];
    pixel[0][0] = new RGBAPixel(4, 4, 4, 255, 255);
    pixel[0][1] = new RGBAPixel(4, 4, 4, 255, 255);
    Image image = new RGBALayerImage(pixel);

    try {
      collage1.addImage("test", image, 0, 0);
      fail("Image should not add before project exists");
    } catch (IllegalStateException expected) {
      // Do nothing, test passed
    }

    try {
      collage1.addImage("test", ImageUtil.readPPM("./res/testLoadSource.ppm"), 0, 0);
      fail("Image should not add before project exists");
    } catch (IllegalStateException expected) {
      // Do nothing, test passed
    }

    try {
      collage1.createProject(4, 5);
      collage1.addImage("test", ImageUtil.readPPM("./res/testLoadSource.ppm"), 0, 0);
      fail("Image should not add if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      collage1.createProject(4, 5);
      collage1.addLayer("test");
      collage1.addImage("test", ImageUtil.readPPM("hsgvligeroasgr.ppm"), 0, 0);
      fail("Image should not add if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      collage1.createProject(4, 5);
      collage1.addLayer("test");
      collage1.addImage("tests", ImageUtil.readPPM("./res/testLoadSource.ppm"), 0, 0);
      fail("Image should not add if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      collage1.createProject(2, 3);
      collage1.addLayer("test");
      collage1.addImage("test", "./res/testLoadSource.ppm", 0, 0);
      fail("Image should not add if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      collage1.createProject(4, 5);
      collage1.addLayer("test");
      collage1.addImage("test", ImageUtil.readPPM("./res/testLoadSource.ppm"), 1, 0);
      fail("Image should not add if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      collage1.createProject(4, 5);
      collage1.addLayer("test");
      collage1.addImage("test", ImageUtil.readPPM("./res/testLoadSource.ppm"), 0, 1);
      fail("Image should not add if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      collage1.createProject(4, 5);
      collage1.addLayer("test");
      collage1.addImage("test", ImageUtil.readPPM("./res/testLoadSource.collage"), 0, 0);
      fail("Image should not add if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      collage1.createProject(4, 5);
      collage1.addLayer("test");
      collage1.addImage(null, ImageUtil.readPPM("./res/testLoadSource.ppm"), 0, 0);
      fail("Image should not add if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      collage1.createProject(4, 5);
      collage1.addLayer("test");
      collage1.addImage("test", (String) null, 0, 0);
      fail("Image should not add if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      collage1.createProject(4, 5);
      collage1.addImage("test", image, 0, 0);
      fail("Image should not add if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      collage1.createProject(4, 5);
      collage1.addLayer("test");
      collage1.addImage("tests", image, 0, 0);
      fail("Image should not add if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      collage1.createProject(2, 1);
      collage1.addLayer("test");
      collage1.addImage("test", image, 0, 0);
      fail("Image should not add if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      collage1.createProject(1, 2);
      collage1.addLayer("test");
      collage1.addImage("test", image, 1, 0);
      fail("Image should not add if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      collage1.createProject(1, 2);
      collage1.addLayer("test");
      collage1.addImage("test", image, 0, 1);
      fail("Image should not add if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      collage1.createProject(4, 5);
      collage1.addLayer("test");
      collage1.addImage(null, image, 0, 0);
      fail("Image should not add if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      collage1.createProject(4, 5);
      collage1.addLayer("test");
      collage1.addImage("test", (Image) null, 0, 0);
      fail("Image should not add if invalid parameters");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }
  }

  @Test
  public void testValidAddImage() {

    collage1.createProject(4, 5);
    collage1.addLayer("test");
    collage1.addImage("test", ImageUtil.readPPM("./res/testLoadSource.ppm"), 0, 0);
    ImageUtil.saveImage("testLoadResult.ppm", collage1.getFinalImage());

    try {
      String fileContents = Files.readString(Path.of("testLoadResult.ppm"));
      assertEquals("P3\n5 4\n255\n2 2 2 5 3 1 0 0 0 0 0 0 0 0 0\n2 5 21 57 3 18 0 0 0 0" +
                      " 0 0 0 0 0\n0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n",
              fileContents);
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }

    Pixel[][] pixel = new Pixel[1][1];
    pixel[0][0] = new RGBAPixel(4, 4, 4, 255, 255);
    collage1.addImage("test", new RGBALayerImage(pixel), 0, 0);
    ImageUtil.saveImage("testLoadResult.ppm", collage1.getFinalImage());

    try {
      String fileContents = Files.readString(Path.of("testLoadResult.ppm"));
      assertEquals("P3\n5 4\n255\n4 4 4 5 3 1 0 0 0 0 0 0 0 0 0\n2 5 21 57 3 18 0 0 0 0" +
                      " 0 0 0 0 0\n0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n",
              fileContents);
      Files.delete(Path.of("testLoadResult.ppm"));
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }

    collage1.createProject(4, 5);
    collage1.addLayer("test");
    collage1.addImage("test", ImageUtil.readPPM("./res/testLoadSource.ppm"), 0, 0);
    ImageUtil.saveImage("testLoadResult.ppm", collage1.getFinalImage());

    try {
      String fileContents = Files.readString(Path.of("testLoadResult.ppm"));
      assertEquals("P3\n5 4\n255\n2 2 2 5 3 1 0 0 0 0 0 0 0 0 0\n2 5 21 57 3 18 0 0 0 0" +
                      " 0 0 0 0 0\n0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n",
              fileContents);
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }

    pixel = new Pixel[1][1];
    pixel[0][0] = new RGBAPixel(4, 4, 4, 255, 255);
    collage1.addImage("test", collage1.createJavaImage(new RGBALayerImage(pixel),
            BufferedImage.TYPE_INT_ARGB), 0, 0);
    ImageUtil.saveImage("testLoadResult.ppm", collage1.getFinalImage());

    try {
      String fileContents = Files.readString(Path.of("testLoadResult.ppm"));
      assertEquals("P3\n5 4\n255\n4 4 4 5 3 1 0 0 0 0 0 0 0 0 0\n2 5 21 57 3 18 0 0 0 0" +
                      " 0 0 0 0 0\n0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n",
              fileContents);
      Files.delete(Path.of("testLoadResult.ppm"));
    } catch (IOException e) {
      fail("Unknown IOException: " + e.getMessage());
    }
  }

  @Test
  public void testValidAddLayer() {

    ImageUtil.readCollage("./res/testLoadSource.collage", collage1);
    ImageUtil.saveCollage("testLoadResult.collage", collage1.getHeight(),
            collage1.getWidth(), collage1.getLayers());

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

    collage1.addLayer("test2");
    ImageUtil.saveCollage("testLoadResult.collage", collage1.getHeight(),
            collage1.getWidth(), collage1.getLayers());

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
  public void testInvalidAddLayer() {

    try {
      collage1.addLayer("test");
      fail("Method should not run before project exists");
    } catch (IllegalStateException expected) {
      // Do nothing, test passed
    }

    collage1.createProject(3, 4);

    try {
      collage1.addLayer(null);
      fail("Should not create layer when name is null");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    boolean firstPassed = false;

    try {
      collage1.addLayer("test");
      firstPassed = true;
      collage1.addLayer("test");
      fail("Should not create layer when name is null");
    } catch (IllegalArgumentException expected) {
      assertTrue(firstPassed);
    }
  }

  @Test
  public void testInvalidGetImageAtLayer() {

    try {
      collage1.getImageAtLayer("");
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
  public void testValidGetImageAtLayer() {
    collage1.createProject(2, 2);
    collage1.addLayer("bottom");
    collage1.addLayer("middle");
    collage1.addLayer("top");
    collage1.addImage("bottom",
            new RGBALayerImage(new Pixel[][]{{new RGBAPixel(255, 0, 0, 255)}}),
            0, 0);
    collage1.addImage("middle",
            new RGBALayerImage(new Pixel[][]{{new RGBAPixel(0, 255, 0, 255)}}),
            1, 0);
    collage1.addImage("top",
            new RGBALayerImage(new Pixel[][]{{new RGBAPixel(0, 0, 255, 255)}}),
            0, 1);

    Image result = collage1.getImageAtLayer("middle");

    Pixel[][] expectedPixels = {{new RGBAPixel(255, 0, 0, 255),
            new RGBAPixel(0, 0, 0, 0, 255)},
        {new RGBAPixel(0, 255, 0, 255),
            new RGBAPixel(0, 0, 0, 0, 255)}};

    for (int i = 0; i < result.getHeight(); i++) {

      for (int j = 0; j < result.getWidth(); j++) {

        assertArrayEquals(expectedPixels[i][j].asRGBA(), result.getPixels()[i][j].asRGBA());
      }
    }
  }

  @Test
  public void testInvalidGetFinalImage() {

    try {
      collage1.getFinalImage();
      fail("Should not get image at layer during invalid state");
    } catch (IllegalStateException expected) {
      // Do nothing, test passes
    }

    try {
      Collager collager = new RGBACollager();
      collager.createProject(3, 3);
      collage1.getFinalImage();
      fail("Should not get image at layer when invalid arguments");
    } catch (IllegalStateException expected) {
      // Do nothing, test passes
    }
  }

  @Test
  public void testValidGetFinalImage() {
    collage1.createProject(2, 2);
    collage1.addLayer("bottom");
    collage1.addLayer("middle");
    collage1.addLayer("top");
    collage1.addImage("bottom",
            new RGBALayerImage(new Pixel[][]{{new RGBAPixel(255, 0, 0, 255)}}),
            0, 0);
    collage1.addImage("middle",
            new RGBALayerImage(new Pixel[][]{{new RGBAPixel(0, 255, 0, 255)}}),
            1, 0);
    collage1.addImage("top",
            new RGBALayerImage(new Pixel[][]{{new RGBAPixel(0, 0, 255, 255)}}),
            0, 1);

    Image result = collage1.getFinalImage();

    Pixel[][] expectedPixels = {{new RGBAPixel(255, 0, 0, 255),
            new RGBAPixel(0, 0, 255, 255)},
        {new RGBAPixel(0, 255, 0, 255),
            new RGBAPixel(0, 0, 0, 0, 255)}};

    for (int i = 0; i < result.getHeight(); i++) {

      for (int j = 0; j < result.getWidth(); j++) {

        assertArrayEquals(expectedPixels[i][j].asRGBA(), result.getPixels()[i][j].asRGBA());
      }
    }
  }

  @Test
  public void testGetWidth() {
    collage1.createProject(3, 7);

    assertEquals(7, collage1.getWidth());

    collage1.createProject(1, 2);

    assertEquals(2, collage1.getWidth());
  }

  @Test
  public void testGetHeight() {
    collage1.createProject(3, 7);

    assertEquals(3, collage1.getHeight());

    collage1.createProject(1, 2);

    assertEquals(1, collage1.getHeight());
  }

  @Test
  public void testGetLayers() {
    collage1.createProject(2, 2);
    collage1.addLayer("layer1");
    collage1.addLayer("layer2");
    collage1.addLayer("layer3");

    String[] expectedNames = {"layer1", "layer2", "layer3"};
    List<Layer> result = collage1.getLayers();

    for (int i = 0; i < expectedNames.length; i++) {
      assertEquals(expectedNames[i], result.get(i).getLayerName());
    }

    collage1.addLayer("layer4");

    expectedNames = new String[]{"layer1", "layer2", "layer3", "layer4"};
    result = collage1.getLayers();

    for (int i = 0; i < expectedNames.length; i++) {
      assertEquals(expectedNames[i], result.get(i).getLayerName());
    }
  }

  @Test
  public void testGetInvalidLayers() {
    try {
      collage1.getLayers();
      fail("Should not work when project does not exist");
    } catch (IllegalStateException expected) {
      // Do nothing, test passed
    }

  }

  @Test
  public void testGetLayerNames() {
    collage1.createProject(2, 2);
    collage1.addLayer("layer1");
    collage1.addLayer("layer2");
    collage1.addLayer("layer3");

    String[] expectedNames = {"layer1", "layer2", "layer3"};

    assertArrayEquals(expectedNames, collage1.getLayerNames().toArray(new String[0]));

    collage1.addLayer("layer4");

    expectedNames = new String[]{"layer1", "layer2", "layer3", "layer4"};

    assertArrayEquals(expectedNames, collage1.getLayerNames().toArray(new String[0]));
  }

  @Test
  public void testGetInvalidLayerNames() {
    try {
      collage1.getLayerNames();
      fail("Should not work when project does not exist");
    } catch (IllegalStateException expected) {
      // Do nothing, test passed
    }

    collage1.createProject(2, 2);

    try {
      collage1.getLayerNames();
      fail("Should not work when no layers exist");
    } catch (IllegalStateException expected) {
      // Do nothing, test passed
    }
  }

  @Test
  public void testCreateJavaImage() {

    BufferedImage expected = new BufferedImage(2, 1, BufferedImage.TYPE_INT_ARGB);

    expected.setRGB(0,0, ((255 << 16) | (255 << 8)) | 255);
    expected.setRGB(1,0, ((255 << 16) | (255 << 8)) | 255);

    BufferedImage actual = collage1.createJavaImage(new RGBALayerImage(1, 2),
            BufferedImage.TYPE_INT_ARGB);

    for (int i = 0; i < actual.getWidth(); i++) {

      for (int j = 0; j < actual.getHeight(); j++) {

        assertEquals(expected.getRGB(i, j), actual.getRGB(i, j));
      }
    }

    expected = new BufferedImage(2, 1, BufferedImage.TYPE_INT_ARGB);

    expected.setRGB(0,0, 255 << 24);
    expected.setRGB(1,0, 255 << 24);

    actual = collage1.createJavaImage(new RGBALayerImage(1, 2), BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < actual.getWidth(); i++) {

      for (int j = 0; j < actual.getHeight(); j++) {

        assertEquals(expected.getRGB(i, j), actual.getRGB(i, j));
      }
    }
  }

  @Test
  public void testInvalidCreateJavaImage() {

    try {
      collage1.createJavaImage(null, BufferedImage.TYPE_INT_ARGB);
      fail("Should not create image with invalid inputs");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      collage1.createJavaImage(new RGBALayerImage(2, 2), 0);
      fail("Should not create image with invalid inputs");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }
  }

  @Test
  public void testGetFilterNames() {
    assertArrayEquals(new String[]{"normal", "red_filter", "blue_filter", "green_filter",
        "brighten_intensity", "darken_intensity", "brighten_luma", "darken_luma",
        "brighten_max", "darken_max", "darken_multiply", "brighten_screen",
        "inversion_difference"}, collage1.getFilterNames());
  }

}