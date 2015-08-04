package org.sinmetal.gcsexample.controller;

import org.sinmetal.gcsexample.util.SignedUrlUtil;
import org.slim3.controller.Navigation;
import org.slim3.controller.SimpleController;

/**
 * Signed Urlを生成するAPI
 * @author sinmetal
 */
public class SignedUrlController extends SimpleController {

	@Override
	protected Navigation run() throws Exception {
		String verb = request.getParameter("verb");
		String path = request.getParameter("path");

		String signedUrl = SignedUrlUtil.getSignedUrl(verb, path);

		response.setStatus(200);
		response.setContentType("text/plain");
		response.getWriter().append(signedUrl);
		response.getWriter().flush();
		return null;
	}
}
