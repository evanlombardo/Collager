package filters;

import org.junit.Test;

import model.Pixel;
import model.RGBALayerImage;
import model.RGBAPixel;
import model.filters.RGBADarkenIntensity;

import static org.junit.Assert.assertTrue;

/**
 * This class tests the RGBADarkenIntensity class and all of its methods.
 */
public class RGBADarkenIntensityTest extends RGBAColorFilterTest {


  @Test
  @Override
  public void testInvalidApply() {
    assertTrue(super.invalidApply(new RGBADarkenIntensity()));
  }

  @Test
  @Override
  public void testValidApply() {
    Pixel[][] pixels = {{new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(12, 40, 48, 255)},
        {new RGBAPixel(33, 2, 3, 255),
            new RGBAPixel(0, 0, 0, 255)}};
    Pixel[][] filteredPixels = {{new RGBAPixel(0, 0, 0, 255),
            new RGBAPixel(0, 7, 15, 255)},
        {new RGBAPixel(20, 0, 0, 255),
            new RGBAPixel(0, 0, 0, 255)}};

    assertTrue(super.validApply(new RGBALayerImage(pixels),
            new RGBADarkenIntensity(), filteredPixels));
  }
}
