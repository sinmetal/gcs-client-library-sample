package org.sinmetal.gcsexample.controller;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.sinmetal.gcsexample.model.BlobContent;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;
import org.slim3.util.StringUtil;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.UploadOptions;

/**
 * GCSにFileをUploadするController
 * @author sinmetal
 */
public class UploadFileController extends Controller {

	private static final String BUCKET = "sinpkmnms-pro";

	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();


	@Override
	protected Navigation run() throws Exception {
		switch (request.getMethod()) {
			case "GET":
				String key = request.getParameter("key");
				if (StringUtil.isEmpty(key)) {
					respondUploadUrl();
				} else {
					downloadGcsFile(new BlobKey(key));
				}
				break;
			case "POST":
				receiveBlobServiceRedirect();
				break;
			default:
				throw new UnsupportedOperationException();
		}
		return null;
	}

	private void respondUploadUrl() throws Exception {
		System.out.println("responseUploadUrl");

		final long MEGA_BYTE = 1024 * 1024 * 1024;

		final UploadOptions options =
				UploadOptions.Builder.withGoogleStorageBucketName(BUCKET).maxUploadSizeBytes(
						100 * MEGA_BYTE);
		final String url =
				BlobstoreServiceFactory.getBlobstoreService().createUploadUrl("/uploadFile",
						options);
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		response.getWriter().println("{\"url\":\"" + new URL(url).getPath() + "\"}");
		response.flushBuffer();
	}

	private void downloadGcsFile(BlobKey blobKey) throws Exception {
		System.out.println("downloadGcsFile");

		blobstoreService.serve(blobKey, response);
	}

	private void receiveBlobServiceRedirect() throws Exception {
		System.out.println("receiveBlobServiceRedirect");

		final Map<String, List<BlobInfo>> infoListMap = blobstoreService.getBlobInfos(request);

		for (Entry<String, List<BlobInfo>> entry : infoListMap.entrySet()) {
			System.out.println(entry.getKey());

			for (BlobInfo info : entry.getValue()) {
				BlobContent content = new BlobContent(info);
				Datastore.put(content);
			}
		}
	}
}
