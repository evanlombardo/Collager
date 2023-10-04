import org.junit.Test;

import java.io.StringReader;

import controller.CollageController;
import controller.RGBACollageController;
import helpers.ConfirmInputs;
import view.CollagerTextView;
import view.CollagerView;

import static org.junit.Assert.assertEquals;

/**
 * This class represents tests that ensure the correct inputs are passed to the methods.
 */
public class ConfirmInputsTest {

  @Test
  public void testInputsOfCreateProject() {
    Readable in = new StringReader("new-project 100 100");
    StringBuilder log = new StringBuilder();
    ConfirmInputs mock = new ConfirmInputs(log);
    CollagerView view = new CollagerTextView(log);
    CollageController c = new RGBACollageController(in, mock, view);

    c.runCollage();
    assertEquals("A new project creation was attempted with , height: 100, and width:" +
                    " 100.\n",
            log.toString());
  }

  @Test
  public void testInputsOfAddLayer() {
    Readable in = new StringReader("add-layer layer1");
    StringBuilder log = new StringBuilder();
    CollagerView view = new CollagerTextView(log);
    ConfirmInputs mock = new ConfirmInputs(log);
    CollageController c = new RGBACollageController(in, mock, view);

    c.runCollage();
    assertEquals("A new layer addition was attempted. The layer's name was layer1\n",
            log.toString());
  }

  @Test
  public void testInputsOfSetFilter() {
    Readable in = new StringReader("set-filter layer1 green");
    StringBuilder log = new StringBuilder();
    ConfirmInputs mock = new ConfirmInputs(log);
    CollagerView view = new CollagerTextView(log);
    CollageController c = new RGBACollageController(in, mock, view);

    c.runCollage();
    assertEquals("A new filter setting was attempted. The layer's name was layer1 " +
                    "and the filter name was: green\n",
            log.toString());
  }
}
