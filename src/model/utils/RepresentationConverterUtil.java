package model.utils;

/**
 * This class contains utility methods to convert an RGB representation to HSL and back.
 */
public class RepresentationConverterUtil {

  /**
   * Converts an RGB representation in the range 0-255 into an HSL
   * representation where
   * <ul>
   * <li> 0 &lt;= H &lt; 360</li>
   * <li> 0 &lt;= S &lt;= 1</li>
   * <li> 0 &lt;= L &lt;= 1</li>
   * </ul>.
   *
   * @param red   red value of the RGB between 0 and 255.
   * @param green green value of the RGB between 0 and 255.
   * @param blue  blue value of the RGB between 0 and 255.
   * @return the hue, saturation, and lightness as an array.
   */
  public static double[] convertRGBtoHSL(double red, double green, double blue) {
    double r = red / 255;
    double g = green / 255;
    double b = blue / 255;

    if (r < 0 || r > 1) {
      throw new IllegalArgumentException("Red value must be between 0 and 1");
    }

    if (g < 0 || g > 1) {
      throw new IllegalArgumentException("Green value must be between 0 and 1");
    }

    if (b < 0 || b > 1) {
      throw new IllegalArgumentException("Blue value must be between 0 and 1");
    }

    double componentMax = Math.max(r, Math.max(g, b));
    double componentMin = Math.min(r, Math.min(g, b));
    double delta = componentMax - componentMin;

    double lightness = (componentMax + componentMin) / 2;
    double hue;
    double saturation;
    if (delta == 0) {
      hue = 0;
      saturation = 0;
    } else {
      saturation = delta / (1 - Math.abs(2 * lightness - 1));
      hue = 0;
      if (componentMax == r) {
        hue = (g - b) / delta;
        while (hue < 0) {
          hue += 6; //hue must be positive to find the appropriate modulus
        }
        hue = hue % 6;
      } else if (componentMax == g) {
        hue = (b - r) / delta;
        hue += 2;
      } else if (componentMax == b) {
        hue = (r - g) / delta;
        hue += 4;
      }

      hue = hue * 60;
    }

    return new double[]{hue, saturation, lightness};
  }


  /**
   * Converts an HSL representation where
   * <ul>
   * <li> 0 &lt;= H &lt; 360</li>
   * <li> 0 &lt;= S &lt;= 1</li>
   * <li> 0 &lt;= L &lt;= 1</li>
   * </ul>
   * into an RGB representation where each component is in the range 0-255.
   *
   * @param hue        hue of the HSL representation.
   * @param saturation saturation of the HSL representation.
   * @param lightness  lightness of the HSL representation.
   */
  public static int[] convertHSLtoRGB(double hue, double saturation, double lightness) {
    double r = convertFn(hue, saturation, lightness, 0) * 255;
    double g = convertFn(hue, saturation, lightness, 8) * 255;
    double b = convertFn(hue, saturation, lightness, 4) * 255;

    return new int[]{(int) Math.round(r), (int) Math.round(g), (int) Math.round(b)};
  }

  /*
   * Helper method that performs the translation from the HSL polygonal
   * model to the more familiar RGB model
   */
  private static double convertFn(double hue, double saturation, double lightness, int n) {
    double k = (n + (hue / 30)) % 12;
    double a = saturation * Math.min(lightness, 1 - lightness);

    return lightness - a * Math.max(-1, Math.min(k - 3, Math.min(9 - k, 1)));
  }

}
