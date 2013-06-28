package org.sinmetal.gcsexample.controller;

import org.slim3.controller.router.RouterImpl;

/**
 * @author sinmetal
 */
public class AppRouter extends RouterImpl {

	/**
	 * the constructor.
	 * @category constructor
	 */
	public AppRouter() {
		addRouting("/gcs/{bucketName}/{filePath}",
				"/gcs?bucketName={bucketName}&filePath={filePath}");
	}
}
