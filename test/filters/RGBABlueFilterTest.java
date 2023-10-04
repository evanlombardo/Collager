package filters;

import org.junit.Test;

import model.Pixel;
import model.RGBALayerImage;
import model.RGBAPixel;
import model.filters.RGBABlueFilter;

import static org.junit.Assert.assertTrue;

/**
 * Defines tests to define the behavior of {@code RGBABlueFilter}.
 */
public class RGBABlueFilterTest extends RGBAColorFilterTest {

  @Test
  @Override
  public void testInvalidApply() {
    assertTrue(super.invalidApply(new RGBABlueFilter()));
  }

  @Test
  @Override
  public void testValidApply() {
    Pixel[][] pixels = {{new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(12, 40, 48, 255)},
        {new RGBAPixel(33, 2, 3, 255),
            new RGBAPixel(0, 0, 0, 255)}};
    Pixel[][] filteredPixels = {{new RGBAPixel(0, 0, 255, 255),
            new RGBAPixel(0, 0, 48, 255)},
        {new RGBAPixel(0, 0, 3, 255),
            new RGBAPixel(0, 0, 0, 255)}};

    assertTrue(super.validApply(new RGBALayerImage(pixels), new RGBABlueFilter(), filteredPixels));
  }
}
