package filters;

import org.junit.Before;
import org.junit.Test;

import model.Image;
import model.Pixel;
import model.RGBALayerImage;
import model.RGBAPixel;
import model.filters.RGBADifference;
import model.filters.TwoFilter;

import static org.junit.Assert.assertTrue;

/**
 * Defines tests for the behavior of the inversion difference filter.
 */
public class RGBADifferenceTest extends RGBAColorTwoFilterTest {

  private TwoFilter filter;

  @Before
  public void init() {
    filter = new RGBADifference();
  }

  @Override
  @Test
  public void testInvalidApply() {
    assertTrue(super.invalidApply(filter));
  }

  @Override
  @Test
  public void testValidApply() {
    Pixel[][] pixels = {{new RGBAPixel(13, 16, 79, 255, 255),
            new RGBAPixel(58, 37, 56, 69, 255)},
        {new RGBAPixel(39, 69, 35, 255),
            new RGBAPixel(58, 58, 67, 255)}};
    Image img1 = new RGBALayerImage(pixels);

    Pixel[][] pixels2 = {{new RGBAPixel(83, 5, 3, 99, 255),
            new RGBAPixel(58, 58, 58, 255, 255)},
        {new RGBAPixel(94, 6, 5, 43, 255),
            new RGBAPixel(255, 255, 255, 255, 255)}};
    Image img2 = new RGBALayerImage(pixels2);

    Pixel[][] expectedPixels1 = {{new RGBAPixel(242, 239, 176, 0, 255),
            new RGBAPixel(239, 245, 240, 0, 255)},
        {new RGBAPixel(216, 186, 220, 0, 255),
            new RGBAPixel(197, 197, 188, 0, 255)}};

    Pixel[][] expectedPixels2 = {{new RGBAPixel(70, 11, 76, 99, 255),
            new RGBAPixel(42, 48, 43, 255, 255)},
        {new RGBAPixel(55, 63, 30, 43, 255),
            new RGBAPixel(197, 197, 188, 255, 255)}};

    assertTrue(super.validApply(img1, filter, expectedPixels1));
    assertTrue(super.validApply(img1, img2, filter, expectedPixels2));
  }

}
