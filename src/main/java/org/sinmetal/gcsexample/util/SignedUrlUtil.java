package org.sinmetal.gcsexample.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;

import org.apache.commons.codec.binary.Base64;

import com.google.appengine.api.appidentity.AppIdentityService;
import com.google.appengine.api.appidentity.AppIdentityService.SigningResult;
import com.google.appengine.api.appidentity.AppIdentityServiceFactory;

/**
 * Signed Url Util
 * @author sinmetal
 */
public class SignedUrlUtil {

	private static final int EXPIRATION_TIME = 5;

	private static final String BASE_URL = "https://storage.googleapis.com";

	private static final String BUCKET = "cp300demo1";

	private static final AppIdentityService identityService = AppIdentityServiceFactory
		.getAppIdentityService();


	/**
	 * Signed Url 生成
	 * @param httpVerb
	 * @param fileName
	 * @return Signed Url
	 * @throws Exception
	 * @author sinmetal
	 */
	public static String getSignedUrl(final String httpVerb, final String fileName)
			throws Exception {
		final long expiration = expiration();
		final String unsigned = stringToSign(expiration, fileName, httpVerb);
		final String signature = sign(unsigned);

		return new StringBuilder(BASE_URL).append("/").append(BUCKET).append("/").append(fileName)
			.append("?GoogleAccessId=").append(identityService.getServiceAccountName())
			.append("&Expires=").append(expiration).append("&Signature=")
			.append(URLEncoder.encode(signature, "UTF-8")).toString();
	}

	static long expiration() {
		final long unitMil = 1000l;
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, EXPIRATION_TIME);
		final long expiration = calendar.getTimeInMillis() / unitMil;
		return expiration;
	}

	static String stringToSign(final long expiration, String filename, String httpVerb) {
		final String contentType = "";
		final String contentMD5 = "";
		final String canonicalizedExtensionHeaders = "";
		final String canonicalizedResource = "/" + BUCKET + "/" + filename;
		final String stringToSign =
				httpVerb + "\n" + contentMD5 + "\n" + contentType + "\n" + expiration + "\n"
						+ canonicalizedExtensionHeaders + canonicalizedResource;
		return stringToSign;
	}

	static String sign(final String stringToSign) throws UnsupportedEncodingException {
		final SigningResult signingResult = identityService.signForApp(stringToSign.getBytes());
		final String encodedSignature =
				new String(Base64.encodeBase64(signingResult.getSignature(), false), "UTF-8");
		return encodedSignature;
	}
}
