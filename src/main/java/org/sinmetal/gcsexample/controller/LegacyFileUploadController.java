package org.sinmetal.gcsexample.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.sinmetal.gcsexample.meta.BlobContentMeta;
import org.sinmetal.gcsexample.model.BlobContent;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.UploadOptions;
import com.google.appengine.api.datastore.Key;

public class LegacyFileUploadController extends Controller {

	@Override
	protected Navigation run() throws Exception {
		switch (request.getMethod()) {
			case "GET":
				doGet();
				break;
			case "POST":
				doPost();
				break;
			case "DELETE":
				doDelete();
				break;
			default:
				throw new UnsupportedOperationException();
		}
		return null;
	}

	private void doGet() throws IOException {
		final BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

		final String bucketName = "example-test";
		UploadOptions options = UploadOptions.Builder.withGoogleStorageBucketName(bucketName);
		response.getWriter()
			.write(blobstoreService.createUploadUrl("/legacyBlobRedirect", options));
	}

	private void doPost() throws IOException {

	}

	private void doDelete() {
		final BlobContentMeta bc = BlobContentMeta.get();
		final BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

		List<Key> blobContentKeys = Datastore.query(BlobContentMeta.get()).asKeyList();
		List<BlobContent> blobContents = Datastore.get(bc, blobContentKeys);
		List<BlobKey> deleteBlobKeys = new ArrayList<>();
		for (BlobContent blobContent : blobContents) {
			final List<BlobKey> blobKeys = blobContent.getBlobKeys();
			for (BlobKey blobKey : blobKeys) {
				deleteBlobKeys.add(blobKey);
			}
		}

		blobstoreService.delete(deleteBlobKeys.toArray(new BlobKey[0]));
		Datastore.delete(blobContentKeys);
	}
}
