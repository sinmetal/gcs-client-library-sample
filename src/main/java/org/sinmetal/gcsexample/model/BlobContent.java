package org.sinmetal.gcsexample.model;

import java.util.List;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Key;

/**
 * @author sinmetal
 */
@Model
public class BlobContent {

	@Attribute(primaryKey = true)
	Key key;

	List<BlobKey> blobKeys;


	/**
	 * @return the key
	 * @category accessor
	 */
	public Key getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 * @category accessor
	 */
	public void setKey(Key key) {
		this.key = key;
	}

	/**
	 * @return the blobKeys
	 * @category accessor
	 */
	public List<BlobKey> getBlobKeys() {
		return blobKeys;
	}

	/**
	 * @param blobKeys the blobKeys to set
	 * @category accessor
	 */
	public void setBlobKeys(List<BlobKey> blobKeys) {
		this.blobKeys = blobKeys;
	}

}
