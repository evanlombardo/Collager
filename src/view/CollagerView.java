package view;

import java.io.IOException;

/**
 * Defines the behavior of a view for the collager program.
 */
public interface CollagerView {

  /**
   * Renders a given message to the data output in the implementation.
   *
   * @param message the message to be outputted.
   * @throws IOException if the transmission of the message to the data output fails.
   */
  void renderMessage(String message) throws IllegalArgumentException;

}