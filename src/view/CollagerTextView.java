package view;

import java.io.IOException;

/**
 * This defines the implementation for a view for a text-based collager program. The view writes
 * the given messages to the {@code Appendable} given to it.
 */
public class CollagerTextView implements CollagerView {

  private Appendable out;

  /**
   * Creates the view without an {@code Appendable}. The output {@code Appendable} is then assumed
   * to be System.out (the console).
   */
  public CollagerTextView() throws IllegalArgumentException {

    this.out = System.out;
  }

  /**
   * Creates the view with an {@code Appendable} that will be written to when the renderMessage
   * method is called.
   *
   * @param out the {@code Appendable} object for this view.
   * @throws IllegalArgumentException if the {@code Appendable} is null.
   */
  public CollagerTextView(Appendable out)
          throws IllegalArgumentException {

    if (out == null) {
      throw new IllegalArgumentException("Appendable object is null");
    }

    this.out = out;
  }

  @Override
  public void renderMessage(String message) throws IllegalStateException, IllegalArgumentException {

    if (message == null) {
      throw new IllegalArgumentException("Message is null");
    }

    try {
      this.out.append(message);
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }

  }

}
