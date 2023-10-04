package model.filters;

import model.Pixel;
import model.RGBAPixel;
import model.utils.RepresentationConverterUtil;

/**
 * Defines how the screen filter lightens an image. The image is lightened by multiplying the
 * complements of the lightness values of the two images, and takes the complement of the product.
 */
public class RGBAScreen extends RGBAColorTwoFilter {

  @Override
  protected Pixel createResultPixel(Pixel pixel1, Pixel pixel2) {

    int[] rgba1 = pixel1.asRGB();
    int[] rgba2 = pixel2.asRGBA();

    double[] hsl1 = RepresentationConverterUtil.convertRGBtoHSL(rgba1[0], rgba1[1], rgba1[2]);
    double[] hsl2 = RepresentationConverterUtil.convertRGBtoHSL(rgba2[0], rgba2[1], rgba2[2]);

    int[] rgbResult = RepresentationConverterUtil.convertHSLtoRGB(hsl2[0], hsl2[1],
            (1 - ((1 - hsl1[2]) * (1 - hsl2[2]))));

    return new RGBAPixel(rgbResult[0], rgbResult[1], rgbResult[2], rgba2[3], 255);

  }
}
