import org.junit.Before;
import org.junit.Test;

import helpers.BadAppendable;
import view.CollagerTextView;
import view.CollagerView;

import static org.junit.Assert.assertEquals;

/**
 * This class represents tests for {@code CollagerTextView}.
 */
public class CollagerTextViewTest {

  private Appendable goodAppendable;
  private Appendable badAppendable;


  @Before
  public void init() {

    goodAppendable = new StringBuilder();
    badAppendable = new BadAppendable();

  }


  ////////////////////////// Tests for the Constructor ////////////////////////////

  // testing properties of a view with the constructor that was constructed properly when given a


  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructor() {
    CollagerView view = new CollagerTextView(null);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidConstructor2() {
    new CollagerTextView(badAppendable).renderMessage("f");
  }


  //////////////////////////// Tests for renderMessage   ////////////////////////////

  @Test
  public void testRenderMessage() {
    Appendable ap = new StringBuilder().append("smth");
    CollagerView view = new CollagerTextView(ap);
    view.renderMessage("wefhuiwefiuh");
    assertEquals("smthwefhuiwefiuh", ap.toString());
  }

  // testing that renderMessage works with a word
  @Test
  public void testRenderMessage2() {
    CollagerView view = new CollagerTextView(goodAppendable);
    view.renderMessage("hi!");
    assertEquals("hi!", goodAppendable.toString());
  }

  // testing that renderMessage works with a full sentence
  @Test
  public void testRenderMessage3() {
    CollagerView view = new CollagerTextView(goodAppendable);
    view.renderMessage("OOD class");
    assertEquals("OOD class", goodAppendable.toString());
  }

  // testing for an exception when renderMessage has trouble transmitting an output
  @Test(expected = IllegalStateException.class)
  public void testFailedRenderMessage() {
    CollagerView view = new CollagerTextView(badAppendable);

    view.renderMessage("hi");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoodAppendableBadRenderMessage() {
    CollagerView view = new CollagerTextView(goodAppendable);

    view.renderMessage(null);

  }
}