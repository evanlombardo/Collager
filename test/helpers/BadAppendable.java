package helpers;

import java.io.IOException;

/**
 * This class represents an appendable where an IOException is thrown in every method for testing
 * purposes.
 */
public class BadAppendable implements Appendable {

  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException();
  }

  @Override
  public Appendable append(CharSequence csq, int integer, int integer2) throws IOException {
    throw new IOException();
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException();
  }
}
