import org.junit.Before;
import org.junit.Test;

import model.Image;
import model.Pixel;
import model.RGBALayerImage;
import model.RGBAPixel;
import model.filters.Filter;
import model.filters.RGBABlueFilter;
import model.filters.RGBADifference;
import model.filters.RGBAGreenFilter;
import model.filters.RGBARedFilter;
import model.filters.RGBAScreen;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Defines tests for the behavior of a {@code RGBALayerImage}.
 */
public class RGBALayerImageTest {

  /*
   *
   * height less than 1
   * width less than 1
   * pixel list is null
   * a pixel in list is null
   * if image that is null
   * if startY and startX are out of bounds
   * if filter is null
   *
   */

  private Image image1;
  private Image image2;
  private Image image3;
  private RGBAPixel[][] pixels;

  @Before
  public void init() {

    image1 = new RGBALayerImage(3, 3);
    pixels = new RGBAPixel[][]{{new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(134, 122, 3, 5, 255)}, {new RGBAPixel(0, 0, 0, 0, 255),
            new RGBAPixel(10, 220, 40, 65, 255)}};
    image2 = new RGBALayerImage(pixels);

    RGBAPixel[][] pixels2 = new RGBAPixel[][]{{new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(38, 122, 22, 25, 255)},
        {new RGBAPixel(5, 72, 30, 120, 255),
            new RGBAPixel(100, 60, 57, 72, 255)}};
    image3 = new RGBALayerImage(pixels2);
  }

  @Test
  public void createInvalidRGBALayerImage() {

    RGBAPixel[][] badPixels = {{new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(134, 122, 3, 5, 255)},
        {new RGBAPixel(0, 0, 0, 0, 255), null}};

    try {
      new RGBALayerImage(-1, 1);
      fail("Should not create image with invalid inputs");
    } catch (IllegalArgumentException expected) {
      // Do nothing, tests passed
    }

    try {
      new RGBALayerImage(1, -1);
      fail("Should not create image with invalid inputs");
    } catch (IllegalArgumentException expected) {
      // Do nothing, tests passed
    }

    try {
      new RGBALayerImage(-1, -1);
      fail("Should not create image with invalid inputs");
    } catch (IllegalArgumentException expected) {
      // Do nothing, tests passed
    }

    try {
      new RGBALayerImage(null);
      fail("Should not create image with invalid inputs");
    } catch (IllegalArgumentException expected) {
      // Do nothing, tests passed
    }

    try {
      new RGBALayerImage(badPixels);
      fail("Should not create image with invalid inputs");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }
  }

  @Test
  public void testCreateValidImage() {
    Pixel[][] actualPixels = image2.getPixels();

    for (int i = 0; i < actualPixels.length; i++) {

      for (int j = 0; j < actualPixels.length; j++) {

        assertArrayEquals(this.pixels[i][j].asRGBA(), actualPixels[i][j].asRGBA());
      }
    }
  }

  @Test
  public void testGetPixels() {
    RGBAPixel[][] newPixels = new RGBAPixel[3][3];

    for (int i = 0; i < 3; i++) {

      for (int j = 0; j < 3; j++) {

        newPixels[i][j] = new RGBAPixel(255, 255, 255, 0, 255);
      }
    }

    Pixel[][] actualPixels1 = image1.getPixels();

    for (int i = 0; i < actualPixels1.length; i++) {

      for (int j = 0; j < actualPixels1.length; j++) {

        assertArrayEquals(newPixels[i][j].asRGBA(), actualPixels1[i][j].asRGBA());
      }
    }

    assertEquals(newPixels.length, actualPixels1.length);
    assertEquals(newPixels[0].length, actualPixels1[0].length);

    Pixel[][] actualPixels2 = image2.getPixels();

    for (int i = 0; i < actualPixels2.length; i++) {

      for (int j = 0; j < actualPixels2.length; j++) {

        assertArrayEquals(this.pixels[i][j].asRGBA(), actualPixels2[i][j].asRGBA());
      }
    }

    assertEquals(this.pixels.length, actualPixels2.length);
    assertEquals(this.pixels[0].length, actualPixels2[0].length);
  }

  @Test
  public void testInvalidCombine() {

    try {
      image1.combine(null, 1, 1);
      fail("Should not combine with invalid image/start coordinates");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      image1.combine(image2, -1, 0);
      fail("Should not combine with invalid image/start coordinates");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      image1.combine(image2, 0, -1);
      fail("Should not combine with invalid image/start coordinates");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      image2.combine(image1, 0, 0);
      fail("Should not combine with invalid image/start coordinates");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      image1.combine(image2, 0, 2);
      fail("Should not combine with invalid image/start coordinates");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      image1.combine(image2, 2, 0);
      fail("Should not combine with invalid image/start coordinates");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      image1.combine(image2, 2, 2);
      fail("Should not combine with invalid image/start coordinates");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }
  }

  @Test
  public void testValidCombine() {

    Pixel[][] actualPixels1 = image1.combine(image2, 1, 1).getPixels();
    Pixel[][] expectedPixels1 = {{new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255)},
        {new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 255, 255),
            new RGBAPixel(134, 122, 3, 5, 255)}, {new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(0, 0, 0, 0, 255), new RGBAPixel(10, 220, 40, 65, 255)}};

    for (int i = 0; i < actualPixels1.length; i++) {

      for (int j = 0; j < actualPixels1.length; j++) {

        assertArrayEquals(expectedPixels1[i][j].asRGBA(), actualPixels1[i][j].asRGBA());
      }
    }

    assertEquals(expectedPixels1.length, actualPixels1.length);
    assertEquals(expectedPixels1[0].length, actualPixels1[0].length);

    Pixel[][] actualPixels2 = image2.combine(image3, 0, 0).getPixels();
    Pixel[][] expectedPixels2 = {{new RGBAPixel(255, 255, 255, 255, 255),
            new RGBAPixel(53, 122, 19, 30, 255)}, {new RGBAPixel(5, 72, 30, 120, 255),
            new RGBAPixel(65, 123, 50, 119, 255)}};

    for (int i = 0; i < actualPixels2.length; i++) {

      for (int j = 0; j < actualPixels2[0].length; j++) {

        assertArrayEquals(expectedPixels2[i][j].asRGBA(), actualPixels2[i][j].asRGBA());
      }
    }

    assertEquals(expectedPixels2.length, actualPixels2.length);
    assertEquals(expectedPixels2[0].length, actualPixels2[0].length);


  }


  @Test(expected = IllegalArgumentException.class)
  public void testInvalidApplyFilter() {
    image1.applyFilter(null);
  }

  @Test
  public void testValidApplyFilter() {
    Filter redFilter = new RGBARedFilter();
    Filter greenFilter = new RGBAGreenFilter();
    Filter blueFilter = new RGBABlueFilter();

    Pixel[][] actualPixels1 = image2.applyFilter(redFilter).getPixels();
    Pixel[][] expectedPixels1 = new RGBAPixel[][]{{new RGBAPixel(255, 0, 0, 255),
            new RGBAPixel(134, 0, 0, 5, 255)}, {new RGBAPixel(0, 0, 0, 0, 255),
            new RGBAPixel(10, 0, 0, 65, 255)}};

    for (int i = 0; i < actualPixels1.length; i++) {

      for (int j = 0; j < actualPixels1.length; j++) {

        assertArrayEquals(expectedPixels1[i][j].asRGBA(), actualPixels1[i][j].asRGBA());
      }
    }

    assertEquals(expectedPixels1.length, actualPixels1.length);
    assertEquals(expectedPixels1[0].length, actualPixels1[0].length);

    Pixel[][] actualPixels2 = image2.applyFilter(greenFilter).getPixels();
    Pixel[][] expectedPixels2 = new RGBAPixel[][]{{new RGBAPixel(0, 255, 0, 255),
            new RGBAPixel(0, 122, 0, 5, 255)}, {new RGBAPixel(0, 0, 0, 0, 255),
            new RGBAPixel(0, 220, 0, 65, 255)}};

    for (int i = 0; i < actualPixels2.length; i++) {

      for (int j = 0; j < actualPixels2.length; j++) {

        assertArrayEquals(expectedPixels2[i][j].asRGBA(), actualPixels2[i][j].asRGBA());
      }
    }

    assertEquals(expectedPixels2.length, actualPixels2.length);
    assertEquals(expectedPixels2[0].length, actualPixels2[0].length);

    Pixel[][] actualPixels3 = image2.applyFilter(blueFilter).getPixels();
    Pixel[][] expectedPixels3 = pixels = new RGBAPixel[][]{{new RGBAPixel(0, 0, 255, 255),
            new RGBAPixel(0, 0, 3, 5, 255)}, {new RGBAPixel(0, 0, 0, 0, 255),
            new RGBAPixel(0, 0, 40, 65, 255)}};

    for (int i = 0; i < actualPixels3.length; i++) {

      for (int j = 0; j < actualPixels3.length; j++) {

        assertArrayEquals(expectedPixels3[i][j].asRGBA(), actualPixels3[i][j].asRGBA());
      }
    }

    assertEquals(expectedPixels3.length, actualPixels3.length);
    assertEquals(expectedPixels3[0].length, actualPixels3[0].length);
  }

  @Test
  public void testGetHeight() {

    assertEquals(3, image1.getHeight());
    assertEquals(2, image2.getHeight());
    assertEquals(2, image3.getHeight());
  }

  @Test
  public void testGetWidth() {

    assertEquals(3, image1.getWidth());
    assertEquals(2, image2.getWidth());
    assertEquals(2, image3.getWidth());
  }

  @Test
  public void testInvalidApplyTwoFilter() {

    try {
      image1.applyTwoFilter(null, image2);
      fail("Filter should not apply when values are null");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passes
    }

    try {
      image1.applyTwoFilter(new RGBAScreen(), null);
      fail("Filter should not apply when values are null");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passes
    }
  }

  @Test
  public void testValidApplyTwoFilter() {
    RGBAPixel[][] pixels = new RGBAPixel[][]{{new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)},
        {new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)}};

    Image result = new RGBALayerImage(pixels).applyTwoFilter(new RGBADifference(), image3);

    RGBAPixel[][] expectedPixels = new RGBAPixel[][]{{new RGBAPixel(0, 0, 0, 255),
            new RGBAPixel(217, 133, 233, 25, 255)},
        {new RGBAPixel(250, 183, 225, 120, 255),
            new RGBAPixel(155, 195, 198, 72, 255)}};

    for (int i = 0; i < result.getHeight(); i++) {

      for (int j = 0; j < result.getWidth(); j++) {

        assertArrayEquals(expectedPixels[i][j].asRGBA(), result.getPixels()[i][j].asRGBA());
      }
    }
  }
}
