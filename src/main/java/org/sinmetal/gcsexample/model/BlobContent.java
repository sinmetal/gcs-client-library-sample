package org.sinmetal.gcsexample.model;

import java.util.Date;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.Model;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Key;

/**
 * BlobContent
 * @author sinmetal
 */
@Model
public class BlobContent {

	/** BlobKey */
	@Attribute(primaryKey = true)
	Key key;

	String filename;

	String contentType;

	Long size;

	Date creation;

	String md5Hash;


	/**
	 * {@link Key} 生成
	 * @param blobKey 
	 * @return {@link Key}
	 * @author sinmetal
	 */
	public static Key createKey(BlobKey blobKey) {
		return Datastore.createKey(BlobContent.class, blobKey.getKeyString());
	}

	/**
	 * the constructor.
	 * @category constructor
	 */
	public BlobContent() {
	}

	/**
	 * the constructor.
	 * @param info
	 * @category constructor
	 */
	public BlobContent(BlobInfo info) {
		this.key = createKey(info.getBlobKey());
		this.filename = info.getFilename();
		this.contentType = info.getContentType();
		this.md5Hash = info.getMd5Hash();
		this.creation = info.getCreation();
		this.size = info.getSize();
	}

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
	 * @return the filename
	 * @category accessor
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 * @category accessor
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the contentType
	 * @category accessor
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 * @category accessor
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the size
	 * @category accessor
	 */
	public Long getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 * @category accessor
	 */
	public void setSize(Long size) {
		this.size = size;
	}

	/**
	 * @return the creation
	 * @category accessor
	 */
	public Date getCreation() {
		return creation;
	}

	/**
	 * @param creation the creation to set
	 * @category accessor
	 */
	public void setCreation(Date creation) {
		this.creation = creation;
	}

	/**
	 * @return the md5Hash
	 * @category accessor
	 */
	public String getMd5Hash() {
		return md5Hash;
	}

	/**
	 * @param md5Hash the md5Hash to set
	 * @category accessor
	 */
	public void setMd5Hash(String md5Hash) {
		this.md5Hash = md5Hash;
	}

}
