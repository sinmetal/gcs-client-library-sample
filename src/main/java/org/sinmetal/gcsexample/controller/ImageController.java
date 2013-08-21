package org.sinmetal.gcsexample.controller;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

public class ImageController extends Controller {

	@Override
	protected Navigation run() throws Exception {
		final String fileName = asString("fileName");

		ImagesService imagesService = ImagesServiceFactory.getImagesService();
		//GcsFilename gcsFilename = new GcsFilename("example-test", "77367966.png");
		ServingUrlOptions options = ServingUrlOptions.Builder.withGoogleStorageFileName(fileName);
		String imageUrl = imagesService.getServingUrl(options);
		response.getWriter().write(imageUrl);

		return null;
	}
}
