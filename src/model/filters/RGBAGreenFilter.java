package model.filters;

import model.Pixel;
import model.RGBAPixel;

/**
 * Defines a filter that only displays the green hue of the original image.
 */
public class RGBAGreenFilter extends RGBAColorFilter {

  @Override
  protected Pixel createResultPixel(Pixel pixel) {
    int[] pixelData = pixel.asRGBA();

    return new RGBAPixel(0, pixelData[1], 0, pixelData[3], pixel.getMax());
  }
}
