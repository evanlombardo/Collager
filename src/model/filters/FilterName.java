package model.filters;

import static model.filters.FilterType.ONE;
import static model.filters.FilterType.TWO;

/**
 * This class holds all the different {@code Filter}s with their name and a corresponding
 * {@code Filter} object, as well as the type of filter that it is.
 */

public enum FilterName {
  NORMAL("normal", new NormalFilter(), ONE),
  RED_FILTER("red_filter", new RGBARedFilter(), ONE),
  BLUE_FILTER("blue_filter", new RGBABlueFilter(), ONE),
  GREEN_FILTER("green_filter", new RGBAGreenFilter(), ONE),
  BRIGHTEN_INTENSITY("brighten_intensity", new RGBABrightenIntensity(), ONE),
  DARKEN_INTENSITY("darken_intensity", new RGBADarkenIntensity(), ONE),
  BRIGHTEN_LUMA("brighten_luma", new RGBABrightenLuma(), ONE),
  DARKEN_LUMA("darken_luma", new RGBADarkenLuma(), ONE),
  BRIGHTEN_MAX("brighten_max", new RGBABrightenMax(), ONE),
  DARKEN_MAX("darken_max", new RGBADarkenMax(), ONE),
  MULTIPLY("darken_multiply", new RGBAMultiply(), TWO),
  SCREEN("brighten_screen", new RGBAScreen(), TWO),
  DIFFERENCE("inversion_difference", new RGBADifference(), TWO);


  private final String name;
  private final Filter filter;
  private final FilterType filterType;

  private FilterName(String name, Filter filter, FilterType filterType) {
    this.name = name;
    this.filter = filter;
    this.filterType = filterType;
  }

  public String getName() {
    return this.name;
  }

  public Filter getFilter() {
    return this.filter;
  }

  /**
   * If the filter is a {@code TwoFilter}, then return it.
   *
   * @return the filter as a {@code TwoFilter}.
   * @throws IllegalStateException if the filter is not a {@code TwoFilter}.
   */
  public TwoFilter getTwoFilter() throws IllegalStateException {
    if (this.filterType != TWO) {
      throw new IllegalStateException("Specified filter must be a TwoFilter");
    }

    return (TwoFilter) this.filter;
  }
}
