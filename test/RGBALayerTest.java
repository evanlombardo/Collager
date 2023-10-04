import org.junit.Before;
import org.junit.Test;

import model.Image;
import model.Layer;
import model.Pixel;
import model.RGBALayer;
import model.RGBALayerImage;
import model.RGBAPixel;

import static model.filters.FilterName.BLUE_FILTER;
import static model.filters.FilterName.DARKEN_LUMA;
import static model.filters.FilterName.DIFFERENCE;
import static model.filters.FilterName.GREEN_FILTER;
import static model.filters.FilterName.NORMAL;
import static model.filters.FilterName.RED_FILTER;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Defines tests for the behavior of a {@code RGBALayer}.
 */
public class RGBALayerTest {

  Image image1;
  Pixel[][] pixels1;
  Image image2;
  Pixel[][] pixels2;
  Image image3;
  Layer layer1;
  Layer layer2;
  Layer layer3;
  Layer layer4;
  Layer layer5;
  Layer layer6;
  Layer layer7;


  @Before
  public void init() {

    image1 = new RGBALayerImage(2, 2);
    pixels1 = new RGBAPixel[][]{{new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(134, 122, 3, 5, 255)}, {new RGBAPixel(0, 0, 0, 0, 255),
            new RGBAPixel(10, 220, 40, 65, 255)}};
    image2 = new RGBALayerImage(pixels1);
    pixels2 = new RGBAPixel[][]{{new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(38, 122, 22, 25, 255)}, {new RGBAPixel(5, 72, 30, 120, 255),
            new RGBAPixel(100, 60, 57, 72, 255)}};
    image3 = new RGBALayerImage(pixels2);

    layer1 = new RGBALayer("name1", NORMAL, 2, 2);
    layer2 = new RGBALayer("name2", NORMAL, 2, 2);
    layer3 = new RGBALayer("name3", BLUE_FILTER, 2, 2).combine(image1, 0, 0);
    layer4 = new RGBALayer("name4", NORMAL, 2, 2).combine(image2, 0, 0);
    layer5 = new RGBALayer("name5", NORMAL, 2, 2).combine(image3, 0, 0);
    layer6 = new RGBALayer("name6", DARKEN_LUMA, 2, 2).combine(image3, 0, 0);
    layer7 = new RGBALayer("name7", NORMAL, 2, 2).combine(image3, 0, 0);

  }

  @Test
  public void createValidRGBALayerWithoutImageTest() {

    Pixel[][] actualPixels1 = layer1.getImage().getPixels();

    for (int i = 0; i < actualPixels1.length; i++) {

      for (int j = 0; j < actualPixels1.length; j++) {

        assertArrayEquals(new int[]{255, 255, 255, 0}, actualPixels1[i][j].asRGBA());
      }
    }

    Pixel[][] actualPixels2 = layer2.getImage().getPixels();

    for (int i = 0; i < actualPixels2.length; i++) {

      for (int j = 0; j < actualPixels2.length; j++) {

        assertArrayEquals(new int[]{255, 255, 255, 0}, actualPixels2[i][j].asRGBA());
      }
    }

    Pixel[][] actualPixels3 = layer3.getImage().getPixels();

    for (int i = 0; i < actualPixels3.length; i++) {

      for (int j = 0; j < actualPixels3.length; j++) {

        assertArrayEquals(new int[]{0, 0, 0, 0}, actualPixels3[i][j].asRGBA());
      }
    }


  }

  @Test
  public void createInvalidRGBALayerWithoutImageTest() {

    try {
      new RGBALayer(null, NORMAL, 5, 5);
      fail("Should not combine with invalid image/start coordinates");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBALayer("name", null, 5, 5);
      fail("Should not combine with invalid image/start coordinates");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBALayer("name", NORMAL, -5, 5);
      fail("Should not combine with invalid image/start coordinates");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBALayer("name", NORMAL, -5, -5);
      fail("Should not combine with invalid image/start coordinates");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBALayer("name", NORMAL, 5, -5);
      fail("Should not combine with invalid image/start coordinates");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBALayer(null, null, 5, -5);
      fail("Should not combine with invalid image/start coordinates");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }
  }

  @Test
  public void createValidRGBALayerWithImageTest() {

    Pixel[][] actualPixels1 = layer4.getImage().getPixels();
    Pixel[][] expectedPixels1 = new RGBAPixel[][]{{new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(134, 122, 3, 5, 255),
            new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255)}, {
            new RGBAPixel(0, 0, 0, 0, 255),
            new RGBAPixel(10, 220, 40, 65, 255),
            new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255)},
        {new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255)},
        {new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255)},
        {new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255)},
        {new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255)},
        {new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255)},
        {new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255)},
        {new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255)},
        {new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255)}};

    for (int i = 0; i < actualPixels1.length; i++) {

      for (int j = 0; j < actualPixels1[0].length; j++) {

        assertArrayEquals(expectedPixels1[i][j].asRGBA(), actualPixels1[i][j].asRGBA());
      }
    }

    Pixel[][] actualPixels2 = layer5.getImage().getPixels();

    for (int i = 0; i < actualPixels2.length; i++) {

      for (int j = 0; j < actualPixels2[0].length; j++) {

        assertArrayEquals(pixels2[i][j].asRGBA(), actualPixels2[i][j].asRGBA());
      }
    }
  }

  @Test
  public void createInvalidRGBALayerWithImageTest() {

    try {
      new RGBALayer(null, NORMAL, 5, 5).combine(new RGBALayerImage(4, 4), 0, 0);
      fail("Should not combine with invalid image/start coordinates");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBALayer("name", null, 5, 5).combine(new RGBALayerImage(4, 4), 0, 0);
      fail("Should not combine with invalid image/start coordinates");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBALayer("name", NORMAL, -5, 5).combine(new RGBALayerImage(4, 4), 0, 0);
      fail("Should not combine with invalid image/start coordinates");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBALayer("name", NORMAL, 5, -5).combine(new RGBALayerImage(pixels1), 0, 0);
      fail("Should not combine with invalid image/start coordinates");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBALayer(null, null, 5, -5).combine(new RGBALayerImage(pixels1), 0, 0);
      fail("Should not combine with invalid image/start coordinates");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBALayer("name", NORMAL, 5, -5).combine((Image) null, 0, 0);
      fail("Should not combine with invalid image/start coordinates");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBALayer(null, null, 5, -5).combine((Image) null, 0, 0);
      fail("Should not combine with invalid image/start coordinates");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

  }

  @Test
  public void validCombineTest() {


    Pixel[][] actualPixels1 = layer3.combine(layer4, 0, 0).getImage().getPixels();
    Pixel[][] expectedPixels1 = {{new RGBAPixel(255, 255, 255, 255, 255),
            new RGBAPixel(134, 122, 3, 5, 255)}, {new RGBAPixel(0, 0, 0, 0, 255),
            new RGBAPixel(10, 220, 40, 65, 255)}};

    for (int i = 0; i < actualPixels1.length; i++) {

      for (int j = 0; j < actualPixels1.length; j++) {

        assertArrayEquals(expectedPixels1[i][j].asRGBA(), actualPixels1[i][j].asRGBA());
      }
    }

    assertEquals(expectedPixels1.length, actualPixels1.length);
    assertEquals(expectedPixels1[0].length, actualPixels1[0].length);

    Pixel[][] actualPixels2 = layer4.combine(layer5, 0, 0).getImage().getPixels();
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

    Pixel[][] actualPixels3 = layer3.combine(image2, 0, 0).getImage().getPixels();
    Pixel[][] expectedPixels3 = {{new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255)}, {new RGBAPixel(255, 255, 255, 255, 255),
            new RGBAPixel(134, 122, 3, 5, 255)}};

    for (int i = 0; i < actualPixels3.length; i++) {

      for (int j = 0; j < actualPixels3.length; j++) {

        assertArrayEquals(expectedPixels1[i][j].asRGBA(), actualPixels1[i][j].asRGBA());
      }
    }


  }

  @Test
  public void invalidCombineTest() {

    try {
      layer3.combine((Image) null, 0, 0);
      fail("Should not combine with invalid image/start coordinates");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      layer3.combine((Image) null, -1, 1);
      fail("Should not combine with invalid image/start coordinates");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      layer3.combine((Image) null, 1, -1);
      fail("Should not combine with invalid image/start coordinates");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      layer3.combine(new RGBALayerImage(pixels1), 1, -1);
      fail("Should not combine with invalid image/start coordinates");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }
  }

  @Test
  public void invalidUpdateFilterTest() {

    try {
      layer1.updateFilter(null);
      fail("Should not combine with invalid image/start coordinates");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      layer2.updateFilter(null);
      fail("Should not combine with invalid image/start coordinates");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

  }

  @Test
  public void validApplyFilterTest() {

    layer1.updateFilter(RED_FILTER.getName());

    Pixel[][] actualPixels1 = layer1.getImage().getPixels();

    for (int i = 0; i < actualPixels1.length; i++) {

      for (int j = 0; j < actualPixels1.length; j++) {

        assertArrayEquals(new int[]{255, 255, 255, 0}, actualPixels1[i][j].asRGBA());
      }
    }

    Layer red = layer1.applyFilter();

    Pixel[][] actualPixels2 = red.getImage().getPixels();

    for (int i = 0; i < actualPixels2.length; i++) {

      for (int j = 0; j < actualPixels2.length; j++) {

        assertArrayEquals(new int[]{255, 0, 0, 0}, actualPixels2[i][j].asRGBA());
      }
    }

    layer1.updateFilter(GREEN_FILTER.getName());
    Layer green = layer1.applyFilter();

    Pixel[][] actualPixels3 = green.getImage().getPixels();

    for (int i = 0; i < actualPixels3.length; i++) {

      for (int j = 0; j < actualPixels3.length; j++) {

        assertArrayEquals(new int[]{0, 255, 0, 0}, actualPixels3[i][j].asRGBA());
      }
    }

    layer1.updateFilter(NORMAL.getName());
    Layer normal = layer1.applyFilter();

    Pixel[][] actualPixels4 = normal.getImage().getPixels();

    for (int i = 0; i < actualPixels4.length; i++) {

      for (int j = 0; j < actualPixels4.length; j++) {

        assertArrayEquals(new int[]{255, 255, 255, 0}, actualPixels4[i][j].asRGBA());
      }
    }

    layer4.updateFilter(RED_FILTER.getName());
    Layer red1 = layer4.applyFilter();

    Pixel[][] actualPixels5 = red1.getImage().getPixels();
    Pixel[][] expectedPixels1 = new RGBAPixel[][]{{new RGBAPixel(255, 0, 0, 255),
            new RGBAPixel(134, 0, 0, 5, 255)}, {new RGBAPixel(0, 0, 0, 0, 255),
            new RGBAPixel(10, 0, 0, 65, 255)}};

    for (int i = 0; i < actualPixels5.length; i++) {

      for (int j = 0; j < actualPixels5.length; j++) {

        assertArrayEquals(expectedPixels1[i][j].asRGBA(), actualPixels5[i][j].asRGBA());
      }
    }

  }

  @Test
  public void validGetLayerNameTest() {

    assertEquals("name1", layer1.getLayerName());
    assertEquals("name3", layer3.getLayerName());
    assertEquals("name5", layer5.getLayerName());

  }

  @Test
  public void validGetFilterNameTest() {

    assertEquals("normal", layer1.getFilterName());
    assertEquals("blue_filter", layer3.getFilterName());
    assertEquals("darken_luma", layer6.getFilterName());

  }

  @Test
  public void validGetImageTest() {

    Pixel[][] actualPixels1 = layer1.getImage().getPixels();

    for (int i = 0; i < actualPixels1.length; i++) {

      for (int j = 0; j < actualPixels1.length; j++) {

        assertArrayEquals(new int[]{255, 255, 255, 0}, actualPixels1[i][j].asRGBA());
      }
    }

    Pixel[][] actualPixels2 = layer2.getImage().getPixels();

    for (int i = 0; i < actualPixels2.length; i++) {

      for (int j = 0; j < actualPixels2.length; j++) {

        assertArrayEquals(new int[]{255, 255, 255, 0}, actualPixels2[i][j].asRGBA());
      }
    }

  }

  @Test
  public void testInvalidApplyTwoFilter() {

    try {
      layer1.applyTwoFilter(image3);
      fail("Filter should not apply with invalid inputs");
    } catch (IllegalStateException expected) {
      // Do nothing, test passes
    }

    try {
      new RGBALayer("name", DIFFERENCE, 2, 2).applyTwoFilter((Image) null);
      fail("Filter should not apply with invalid inputs");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passes
    }

    try {
      new RGBALayer("name", DIFFERENCE, 2, 2).applyTwoFilter((Layer) null);
      fail("Filter should not apply with invalid inputs");
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

    Layer result = new RGBALayer("name", DIFFERENCE, 2, 2)
            .combine(new RGBALayerImage(pixels), 0, 0).applyTwoFilter(image3);

    RGBAPixel[][] expectedPixels = new RGBAPixel[][]{{new RGBAPixel(0, 0, 0, 255),
            new RGBAPixel(217, 133, 233, 25, 255)},
        {new RGBAPixel(250, 183, 225, 120, 255),
            new RGBAPixel(155, 195, 198, 72, 255)}};

    for (int i = 0; i < result.getImage().getHeight(); i++) {

      for (int j = 0; j < result.getImage().getWidth(); j++) {

        assertArrayEquals(expectedPixels[i][j].asRGBA(),
                result.getImage().getPixels()[i][j].asRGBA());
      }
    }

    result = new RGBALayer("name", DIFFERENCE, 2, 2)
            .combine(new RGBALayerImage(pixels), 0, 0).applyTwoFilter(layer5);

    for (int i = 0; i < result.getImage().getHeight(); i++) {

      for (int j = 0; j < result.getImage().getWidth(); j++) {

        assertArrayEquals(expectedPixels[i][j].asRGBA(),
                result.getImage().getPixels()[i][j].asRGBA());
      }
    }
  }

}