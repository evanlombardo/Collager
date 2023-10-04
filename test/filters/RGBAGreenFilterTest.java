package filters;

import org.junit.Test;

import model.Pixel;
import model.RGBALayerImage;
import model.RGBAPixel;
import model.filters.RGBAGreenFilter;

import static org.junit.Assert.assertTrue;

/**
 * This class tests the RGBAGreenFilter class and all of its methods.
 */
public class RGBAGreenFilterTest extends RGBAColorFilterTest {


  @Test
  @Override
  public void testInvalidApply() {
    assertTrue(super.invalidApply(new RGBAGreenFilter()));
  }

  @Test
  @Override
  public void testValidApply() {
    Pixel[][] pixels = {{new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(12, 40, 48, 255)},
        {new RGBAPixel(33, 2, 3, 255),
            new RGBAPixel(0, 0, 0, 255)}};
    Pixel[][] filteredPixels = {{new RGBAPixel(0, 255, 0, 255),
            new RGBAPixel(0, 40, 0, 255)},
        {new RGBAPixel(0, 2, 0, 255),
            new RGBAPixel(0, 0, 0, 255)}};

    assertTrue(super.validApply(new RGBALayerImage(pixels),
            new RGBAGreenFilter(), filteredPixels));
  }
}
