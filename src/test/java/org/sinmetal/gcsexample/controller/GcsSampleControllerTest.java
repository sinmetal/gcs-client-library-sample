package org.sinmetal.gcsexample.controller;

import org.junit.Test;
import org.slim3.controller.Controller;
import org.slim3.tester.ControllerTestCase;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

/**
 * @author sinmetal
 */
public class GcsSampleControllerTest extends ControllerTestCase {

	/**
	 * TODO for sinmetal
	 * @throws Exception
	 * @author sinmetal
	 */
	@Test
	public void run() throws Exception {
		tester.start("/gcs");
		Controller controller = tester.getController();
		assertThat(controller, instanceOf(GcsSampleControllerTest.class));
		assertThat(controller, is(notNullValue()));
		assertThat(tester.isRedirect(), is(false));
		assertThat(tester.getDestinationPath(), is(nullValue()));
	}
}
