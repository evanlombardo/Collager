package model.filters;

/**
 * Defines a filter that uses {@code RGBAPixel}s to brighten an image by
 * increasing the RGB values by its weighted luma value.
 */
public class RGBABrightenLuma extends RGBABrightenDarken {

  @Override
  protected int createDifference(int red, int green, int blue) {
    return (int) Math.round(red * .2126 + green * .7152 + blue * .0722);
  }
}
