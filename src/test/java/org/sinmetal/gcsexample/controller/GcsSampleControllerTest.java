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
		tester.request.setMethod("GET");
		tester.param("bucketName", "testbucket");
		tester.param("filePath", "hogefile");
		tester.start("/gcsSample");

		Controller controller = tester.getController();
		assertThat(controller, instanceOf(GcsSampleControllerTest.class));
		assertThat(controller, is(notNullValue()));
		assertThat(tester.isRedirect(), is(false));
		assertThat(tester.getDestinationPath(), is(nullValue()));
	}

	@Test
	public void post() throws Exception {
		tester.request.setMethod("POST");
		tester.param("bucketName", "testbucket");
		tester.param("filepath", "hogefile");
		tester.addBlobKey("", "");
		//InputStream inputStream = "";
		//tester.request.set
		tester.start("/gcsSample");

		Controller controller = tester.getController();
		assertThat(controller, instanceOf(GcsSampleControllerTest.class));
		assertThat(controller, is(notNullValue()));
		assertThat(tester.isRedirect(), is(false));
		assertThat(tester.getDestinationPath(), is(nullValue()));
	}
}
