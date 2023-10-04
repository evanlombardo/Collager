package model.filters;

/**
 * Defines a filter that uses {@code RGBAPixel}s to darken an image by
 * decreasing the RGB values by its weighted luma value.
 */
public class RGBADarkenLuma extends RGBABrightenDarken {

  @Override
  protected int createDifference(int red, int green, int blue) {
    return -1 * ((int) Math.round(red * .2126 + green * .7152 + blue * .0722));
  }
}
