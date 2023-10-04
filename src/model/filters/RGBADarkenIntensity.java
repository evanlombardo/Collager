package model.filters;

/**
 * Defines a filter that uses {@code RGBAPixel}s to darken an image by
 * decreasing the RGB values by its average.
 */
public class RGBADarkenIntensity extends RGBABrightenDarken {

  @Override
  protected int createDifference(int red, int green, int blue) {
    return -1 * ((int) Math.round((red + green + blue) / 3.0));
  }
}
