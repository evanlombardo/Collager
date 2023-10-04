package filters;

import org.junit.Before;
import org.junit.Test;

import model.Pixel;
import model.RGBALayerImage;
import model.RGBAPixel;
import model.filters.RGBAScreen;
import model.filters.TwoFilter;

import static org.junit.Assert.assertTrue;


/**
 * This class tests the RGBAScreen class and all of its methods.
 */
public class RGBAScreenTest extends RGBAColorTwoFilterTest {

  private TwoFilter filter;

  @Before
  public void init() {
    filter = new RGBAScreen();
  }

  @Override
  @Test
  public void testInvalidApply() {
    assertTrue(super.invalidApply(filter));
  }

  @Override
  @Test
  public void testValidApply() {

    Pixel[][] pixels = {{new RGBAPixel(255, 255, 255, 255, 255),
            new RGBAPixel(12, 40, 48, 255, 255)},
        {new RGBAPixel(33, 2, 3, 255, 255), new RGBAPixel(0, 0, 0, 255, 255)}};
    Pixel[][] pixels2 = {{new RGBAPixel(255, 255, 255, 255, 255),
            new RGBAPixel(47, 75, 83, 255, 255)},
        {new RGBAPixel(42, 11, 12, 255, 255), new RGBAPixel(0, 0, 0, 255)}};
    Pixel[][] filteredPixels1 = {{new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(255, 255, 255, 0, 255)},
        {new RGBAPixel(255, 255, 255, 0, 255), new RGBAPixel(255, 255, 255, 0, 255)}};
    Pixel[][] filteredPixel2 = {{new RGBAPixel(255, 255, 255, 255, 255),
            new RGBAPixel(63, 101, 112, 255, 255)},
        {new RGBAPixel(67, 18, 19, 255, 255), new RGBAPixel(0, 0, 0, 255, 255)}};

    assertTrue(super.validApply(new RGBALayerImage(pixels),
        new RGBAScreen(), filteredPixels1));

    assertTrue(super.validApply(new RGBALayerImage(pixels), new RGBALayerImage(pixels2),
        new RGBAScreen(), filteredPixel2));


  }
}
