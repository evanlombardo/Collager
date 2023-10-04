package filters;

import java.util.Arrays;

import model.Image;
import model.Pixel;
import model.filters.Filter;

/**
 * Defines tests for behavior of {@code RGBAColorFilter}s.
 */
public abstract class RGBAColorFilterTest {

  /**
   * Tests the application of a filter where the image is null.
   */
  public abstract void testInvalidApply();

  /**
   * Defines the behavior of the test for an invalid application.
   *
   * @param filter is the filter to apply.
   * @return true if the test passes and false if the test fails.
   */
  protected boolean invalidApply(Filter filter) {
    try {
      filter.apply(null);
      return false;
    } catch (IllegalArgumentException expected) {
      return true;
    }
  }

  /**
   * Tests the application of the filter given the image to apply the filter to,
   * the filter to apply, and the expected pixel result of the filter application.
   */
  public abstract void testValidApply();

  /**
   * Defines the behavior of the test for an application of a filter given the image
   * to apply the filter to, the filter to apply, and the expected pixel result of
   * the filter application.
   *
   * @param img            is the image to apply the filter to.
   * @param filter         is the filter to apply.
   * @param expectedPixels is the expected pixel result of the filtering.
   * @return true if the test passes, false if it fails.
   */
  protected boolean validApply(Image img, Filter filter, Pixel[][] expectedPixels) {
    Image result = filter.apply(img);
    Pixel[][] resultPixels = result.getPixels();

    for (int i = 0; i < resultPixels.length; i++) {

      for (int j = 0; j < resultPixels[i].length; j++) {

        if (!Arrays.equals(resultPixels[i][j].asRGBA(), expectedPixels[i][j].asRGBA())) {
          return false;
        }
      }
    }

    return true;
  }
}
