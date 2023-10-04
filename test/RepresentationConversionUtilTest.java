import org.junit.Test;

import model.utils.RepresentationConverterUtil;

import static org.junit.Assert.assertArrayEquals;

/**
 * Defines tests for the behavior of conversion of HSL/RGB.
 */
public class RepresentationConversionUtilTest {

  @Test
  public void testConvertHSLtoRGB() {
    int[] result = RepresentationConverterUtil.convertHSLtoRGB(146.25, .79279, .43529);

    assertArrayEquals(new int[]{23, 199, 100}, result);
  }

  @Test
  public void testConvertRGBtoHSL() {
    double[] result = RepresentationConverterUtil.convertRGBtoHSL(23, 199, 100);

    assertArrayEquals(new double[]{146.25, .79279, .43529}, result, .001);
  }
}
