package org.sinmetal.gcsexample.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

/**
 * GCS Client Library Sample
 * 
 * @author sinmetal
 */
public class GcsSampleController extends Controller {

	private final GcsService gcsService = GcsServiceFactory
		.createGcsService(new RetryParams.Builder().initialRetryDelayMillis(10)
			.retryMaxAttempts(10).totalRetryPeriodMillis(15000).build());

	private static final int BUFFER_SIZE = 2 * 1024 * 1024;

	/** Blobstoreを使うか */
	public static final boolean SERVE_USING_BLOBSTORE_API = false;


	@Override
	public Navigation run() throws Exception {
		switch (request.getMethod()) {
			case "GET":
				doGet();
				break;
			case "POST":
				doPost();
				break;
			default:
				throw new UnsupportedOperationException();
		}
		return null;
	}

	private void doGet() throws IOException {
		GcsFilename fileName = getFileName();
		if (SERVE_USING_BLOBSTORE_API) {
			BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
			BlobKey blobKey =
					blobstoreService.createGsBlobKey("/gs/" + fileName.getBucketName() + "/"
							+ fileName.getObjectName());
			blobstoreService.serve(blobKey, response);
		} else {
			GcsInputChannel readChannel =
					gcsService.openPrefetchingReadChannel(fileName, 0, BUFFER_SIZE);
			copy(Channels.newInputStream(readChannel), response.getOutputStream());
		}
	}

	private void doPost() throws IOException {
		GcsOutputChannel outputChannel =
				gcsService.createOrReplace(getFileName(), GcsFileOptions.getDefaultInstance());
		copy(request.getInputStream(), Channels.newOutputStream(outputChannel));
	}

	private GcsFilename getFileName() {
		final String bucketName = asString("bucketName");
		final String filePath = asString("filePath");
		return new GcsFilename(bucketName, "test/" + filePath);
	}

	private void copy(InputStream input, OutputStream output) throws IOException {
		try {
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = input.read(buffer);
			while (bytesRead != -1) {
				output.write(buffer, 0, bytesRead);
				bytesRead = input.read(buffer);
			}
		} finally {
			if (input != null) {
				input.close();
			}
			if (output != null) {
				output.close();
			}
		}
	}
}
