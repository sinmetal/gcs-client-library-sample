package org.sinmetal.gcsexample.controller;

import java.io.IOException;
import java.util.List;

import org.sinmetal.gcsexample.model.BlobContent;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * BlobContentController
 * @author sinmetal
 */
public class BlobContentController extends Controller {

	@Override
	protected Navigation run() throws Exception {
		final List<BlobContent> contents = Datastore.query(BlobContent.class).asList();
		try {
			new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS).writeValue(
					response.getWriter(), contents);
		} catch (IOException e) {
			throw new IllegalStateException();
		}
		return null;
	}

}
