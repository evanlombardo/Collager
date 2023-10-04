package filters;

import org.junit.Test;

import model.Pixel;
import model.RGBALayerImage;
import model.RGBAPixel;
import model.filters.RGBABrightenMax;

import static org.junit.Assert.assertTrue;

/**
 * This class tests the RGBABrightenMax class and all of its methods.
 */
public class RGBABrightenMaxTest extends RGBAColorFilterTest {


  @Test
  @Override
  public void testInvalidApply() {
    assertTrue(super.invalidApply(new RGBABrightenMax()));
  }

  @Test
  @Override
  public void testValidApply() {
    Pixel[][] pixels = {{new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(12, 40, 48, 255)},
        {new RGBAPixel(33, 2, 3, 255),
            new RGBAPixel(0, 0, 0, 255)}};
    Pixel[][] filteredPixels = {{new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(60, 88, 96, 255)},
        {new RGBAPixel(66, 35, 36, 255),
            new RGBAPixel(0, 0, 0, 255)}};

    assertTrue(super.validApply(new RGBALayerImage(pixels), new RGBABrightenMax(), filteredPixels));
  }
}
