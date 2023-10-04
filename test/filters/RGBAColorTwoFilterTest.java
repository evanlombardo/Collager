package filters;

import java.util.Arrays;

import model.Image;
import model.Pixel;
import model.RGBALayerImage;
import model.filters.TwoFilter;

/**
 * Defines behavior tests for {@code TwoFilter}s.
 */
public abstract class RGBAColorTwoFilterTest extends RGBAColorFilterTest {

  protected boolean invalidApply(TwoFilter filter) {
    boolean singleApply = super.invalidApply(filter);

    try {
      filter.apply(null, new RGBALayerImage(3, 3));
      return false;
    } catch (IllegalArgumentException expected) {
      // Do nothing, test continues
    }

    try {
      filter.apply(new RGBALayerImage(3, 3), null);
      return false;
    } catch (IllegalArgumentException expected) {
      return singleApply;
    }
  }

  protected boolean validApply(Image img1, Image img2, TwoFilter filter, Pixel[][] expectedPixels) {
    Image result = filter.apply(img1, img2);
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
