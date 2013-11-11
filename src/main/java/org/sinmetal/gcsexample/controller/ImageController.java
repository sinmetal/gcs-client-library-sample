package org.sinmetal.gcsexample.controller;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

/**
 * {@link ImagesService} でgcsのfileを扱うサンプル
 * @author sinmetal
 */
public class ImageController extends Controller {

	@Override
	protected Navigation run() throws Exception {
		// "/gs/{{your gcs bucket name}}/{{file name}}"
		final String fileName = asString("fileName");

		ImagesService imagesService = ImagesServiceFactory.getImagesService();
		ServingUrlOptions options = ServingUrlOptions.Builder.withGoogleStorageFileName(fileName);
		String imageUrl = imagesService.getServingUrl(options);
		response.getWriter().write(imageUrl);

		return null;
	}
}
