package filters;

import org.junit.Test;

import model.Pixel;
import model.RGBALayerImage;
import model.RGBAPixel;
import model.filters.RGBADarkenLuma;

import static org.junit.Assert.assertTrue;

/**
 * This class tests the RGBADarkenLuma class and all of its methods.
 */
public class RGBADarkenLumaTest extends RGBAColorFilterTest {


  @Test
  @Override
  public void testInvalidApply() {
    assertTrue(super.invalidApply(new RGBADarkenLuma()));
  }

  @Test
  @Override
  public void testValidApply() {
    Pixel[][] pixels = {{new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(12, 40, 48, 255)},
        {new RGBAPixel(33, 2, 3, 255),
            new RGBAPixel(0, 0, 0, 255)}};
    Pixel[][] filteredPixels = {{new RGBAPixel(0, 0, 0, 255),
            new RGBAPixel(0, 5, 13, 255)},
        {new RGBAPixel(24, 0, 0, 255),
            new RGBAPixel(0, 0, 0, 255)}};

    assertTrue(super.validApply(new RGBALayerImage(pixels), new RGBADarkenLuma(), filteredPixels));
  }
}
