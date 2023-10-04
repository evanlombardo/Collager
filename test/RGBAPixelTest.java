import org.junit.Before;
import org.junit.Test;

import model.Pixel;
import model.RGBAPixel;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Defines the tests for the behavior of a {@code RGBAPixel}.
 */
public class RGBAPixelTest {

  private Pixel p1;
  private Pixel p2;
  private Pixel p3;
  private Pixel p4;
  private Pixel p5;
  private Pixel p6;

  @Before
  public void init() {

    p1 = new RGBAPixel(0, 0, 0, 0, 1);
    p2 = new RGBAPixel(0, 0, 0, 255);
    p3 = new RGBAPixel(255, 255, 255, 255);
    p4 = new RGBAPixel(199, 37, 99, 376, 399);
    p5 = new RGBAPixel(39, 19, 9, 31, 39);
    p6 = new RGBAPixel(1, 0, 1, 0, 1);

  }

  @Test
  public void testInvalidCreatePixel() {

    // Tests for when values are less than min

    try {
      new RGBAPixel(-1, 2, 4, 5, 255);
      fail("Pixel should not make when the values are invalid");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBAPixel(1, -2, 4, 5, 255);
      fail("Pixel should not make when the values are invalid");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBAPixel(1, 2, -4, 5, 255);
      fail("Pixel should not make when the values are invalid");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBAPixel(1, 2, 4, -5, 255);
      fail("Pixel should not make when the values are invalid");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBAPixel(1, 2, 4, 5, -1);
      fail("Pixel should not make when the values are invalid");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    // Tests with no alpha in construction

    try {
      new RGBAPixel(-1, 2, 4, 255);
      fail("Pixel should not make when the values are invalid");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBAPixel(1, -2, 4, 255);
      fail("Pixel should not make when the values are invalid");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBAPixel(1, 2, -4, 255);
      fail("Pixel should not make when the values are invalid");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBAPixel(1, 2, 4, -1);
      fail("Pixel should not make when the values are invalid");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    // Tests for values larger than max

    try {
      new RGBAPixel(1000, 2, 4, 5, 255);
      fail("Pixel should not make when the values are invalid");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBAPixel(1, 2000, 4, 5, 255);
      fail("Pixel should not make when the values are invalid");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBAPixel(1, 2, 400, 5, 255);
      fail("Pixel should not make when the values are invalid");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBAPixel(1, 2, 4, 5000, 255);
      fail("Pixel should not make when the values are invalid");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBAPixel(501, 2, 4, 5, 500);
      fail("Pixel should not make when the values are invalid");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBAPixel(1, 602, 4, 5, 600);
      fail("Pixel should not make when the values are invalid");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBAPixel(1, 2, 400, 5, 115);
      fail("Pixel should not make when the values are invalid");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBAPixel(1, 2, 4, 5000, 4999);
      fail("Pixel should not make when the values are invalid");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    // Tests with no alpha in construction

    try {
      new RGBAPixel(1000, 2, 4, 255);
      fail("Pixel should not make when the values are invalid");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBAPixel(1, 2000, 4, 255);
      fail("Pixel should not make when the values are invalid");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBAPixel(1, 2, 400, 255);
      fail("Pixel should not make when the values are invalid");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBAPixel(500, 2, 4, 400);
      fail("Pixel should not make when the values are invalid");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBAPixel(1, 2000, 4, 300);
      fail("Pixel should not make when the values are invalid");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBAPixel(1, 2, 400, 305);
      fail("Pixel should not make when the values are invalid");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    // testing when the maximum is 0

    try {
      new RGBAPixel(1, 2, 200, 0);
      fail("Pixel should not make when the values are invalid");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }

    try {
      new RGBAPixel(0, 0, 0, 0, 0);
      fail("Pixel should not make when the values are invalid");
    } catch (IllegalArgumentException expected) {
      // Do nothing, test passed
    }
  }

  @Test
  public void testValidCreatePixel() {
    assertArrayEquals(new int[]{0, 0, 0, 0}, p1.asRGBA());
    assertEquals(255, p1.getMax());
    assertArrayEquals(new int[]{0, 0, 0, 255}, p2.asRGBA());
    assertEquals(255, p2.getMax());
    assertArrayEquals(new int[]{255, 255, 255, 255}, p3.asRGBA());
    assertEquals(255, p3.getMax());
    assertArrayEquals(new int[]{127, 23, 63, 240}, p4.asRGBA());
    assertEquals(255, p4.getMax());
    assertArrayEquals(new int[]{255, 127, 63, 204}, p5.asRGBA());
    assertEquals(255, p5.getMax());
    assertArrayEquals(new int[]{255, 0, 255, 0}, p6.asRGBA());
    assertEquals(255, p6.getMax());
  }

  @Test
  public void testAsRGBA() {
    assertArrayEquals(new int[]{0, 0, 0, 0}, p1.asRGBA());
    assertArrayEquals(new int[]{0, 0, 0, 255}, p2.asRGBA());
    assertArrayEquals(new int[]{255, 255, 255, 255}, p3.asRGBA());
    assertArrayEquals(new int[]{127, 23, 63, 240}, p4.asRGBA());
    assertArrayEquals(new int[]{255, 127, 63, 204}, p5.asRGBA());
    assertArrayEquals(new int[]{255, 0, 255, 0}, p6.asRGBA());
  }

  @Test
  public void testAsRGB() {
    assertArrayEquals(new int[]{0, 0, 0}, p1.asRGB());
    assertArrayEquals(new int[]{0, 0, 0}, p2.asRGB());
    assertArrayEquals(new int[]{255, 255, 255}, p3.asRGB());
    assertArrayEquals(new int[]{120, 22, 59}, p4.asRGB());
    assertArrayEquals(new int[]{204, 102, 50}, p5.asRGB());
    assertArrayEquals(new int[]{0, 0, 0}, p6.asRGB());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullCombine() {
    p3.combine(null);
  }

  @Test
  public void testValidCombine() {
    Pixel c1 = p4.combine(p6);
    Pixel c2 = p1.combine(p2);
    Pixel c3 = p4.combine(p5);
    Pixel c4 = p5.combine(p4);

    assertArrayEquals(new int[]{127, 23, 63, 240}, c1.asRGBA());
    assertArrayEquals(new int[]{0, 0, 0, 255}, c2.asRGBA());
    assertArrayEquals(new int[]{231, 107, 63, 252}, c3.asRGBA());
    assertArrayEquals(new int[]{133, 28, 63, 252}, c4.asRGBA());
  }

  @Test
  public void testGetMax() {
    assertEquals(255, p3.getMax());
    assertEquals(255, p4.getMax());
    assertEquals(255, p5.getMax());
  }
}
