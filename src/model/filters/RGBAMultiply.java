package model.filters;

import model.Pixel;
import model.RGBAPixel;
import model.utils.RepresentationConverterUtil;

/**
 * Defines how the multiply filter darkens an image. The image is darkened by multiplying the
 * lightness values of the two images.
 */
public class RGBAMultiply extends RGBAColorTwoFilter {

  @Override
  protected Pixel createResultPixel(Pixel pixel1, Pixel pixel2) {

    int[] rgba1 = pixel1.asRGB();
    int[] rgba2 = pixel2.asRGBA();

    double[] hsl1 = RepresentationConverterUtil.convertRGBtoHSL(rgba1[0], rgba1[1], rgba1[2]);
    double[] hsl2 = RepresentationConverterUtil.convertRGBtoHSL(rgba2[0], rgba2[1], rgba2[2]);

    int[] rgbResult = RepresentationConverterUtil.convertHSLtoRGB(hsl2[0], hsl2[1],
            hsl1[2] * hsl2[2]);

    return new RGBAPixel(rgbResult[0], rgbResult[1], rgbResult[2], rgba2[3], 255);

  }
}
