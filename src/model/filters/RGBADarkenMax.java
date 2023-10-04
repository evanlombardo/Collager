package model.filters;

/**
 * Defines a filter that uses {@code RGBAPixel}s to darken an image by
 * decreasing the RGB values by its max value.
 */
public class RGBADarkenMax extends RGBABrightenDarken {

  @Override
  protected int createDifference(int red, int green, int blue) {
    return -1 * Math.max(Math.max(red, green), blue);
  }
}
