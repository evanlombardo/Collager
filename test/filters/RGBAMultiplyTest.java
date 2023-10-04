package filters;

import org.junit.Test;

import model.Pixel;
import model.RGBALayerImage;
import model.RGBAPixel;
import model.filters.RGBAMultiply;

import static org.junit.Assert.assertTrue;


/**
 * This class tests the RGBAMultiply class and all of its methods.
 */
public class RGBAMultiplyTest extends RGBAColorTwoFilterTest {


  @Override
  @Test
  public void testInvalidApply() {

    assertTrue(super.invalidApply(new RGBAMultiply()));

  }

  @Override
  @Test
  public void testValidApply() {


    Pixel[][] pixels = {{new RGBAPixel(255, 255, 255, 255, 255),
        new RGBAPixel(12, 40, 48, 255, 255)},
        {new RGBAPixel(33, 2, 3, 255, 255),
            new RGBAPixel(0, 0, 0, 255, 255)}};
    Pixel[][] pixels2 = {{new RGBAPixel(255, 255, 255, 255, 255),
            new RGBAPixel(47, 75, 83, 255, 255)},
        {new RGBAPixel(42, 11, 12, 255, 255),
            new RGBAPixel(0, 0, 0, 255)}};
    Pixel[][] filteredPixels1 = {{new RGBAPixel(255, 255, 255, 0, 255),
            new RGBAPixel(30, 30, 30, 0, 255)},
        {new RGBAPixel(18, 18, 18, 0, 255),
            new RGBAPixel(0, 0, 0, 0, 255)}};
    Pixel[][] filteredPixel2 = {{new RGBAPixel(255, 255, 255, 255, 255),
            new RGBAPixel(6, 9, 10, 255, 255)},
        {new RGBAPixel(3, 1, 1, 255, 255),
            new RGBAPixel(0, 0, 0, 255, 255)}};

    assertTrue(super.validApply(new RGBALayerImage(pixels),
            new RGBAMultiply(), filteredPixels1));

    assertTrue(super.validApply(new RGBALayerImage(pixels), new RGBALayerImage(pixels2),
            new RGBAMultiply(), filteredPixel2));

  }

}
