package org.sinmetal.gcsexample.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.sinmetal.gcsexample.model.BlobContent;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class LegacyBlobRedirectController extends Controller {

	@Override
	protected Navigation run() throws Exception {
		final BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		Map<String, List<BlobKey>> uploads = blobstoreService.getUploads(request);

		final List<BlobContent> blobContents = new ArrayList<>();
		for (String name : uploads.keySet()) {
			List<BlobKey> list = uploads.get(name);
			final BlobContent blobContent = new BlobContent();
			blobContent.setBlobKeys(list);
			blobContents.add(blobContent);
		}
		Datastore.put(blobContents);

		return null;

	}

}
