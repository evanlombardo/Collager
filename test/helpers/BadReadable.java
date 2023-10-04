package helpers;

import java.io.IOException;
import java.nio.CharBuffer;

/**
 * This class represents an invalid {@code Readable} that always throws an IOException.
 */

public class BadReadable implements Readable {

  private String str;

  /**
   * Constructor which instantiates a readable string to whatever parameter is passed in.
   *
   * @param str the string that is passed in for the field.
   */
  public BadReadable(String str) {
    this.str = str;
  }

  /**
   * Constructor which instantiates a readable string to an empty string.
   */
  public BadReadable() {
    this.str = "";
  }

  @Override
  public int read(CharBuffer cb) throws IOException {
    throw new IOException();
  }


}
