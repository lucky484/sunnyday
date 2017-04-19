package com.softtek.mdm.bean;

/**
 * appstore中的应用列表bean
 * @author jane.hui
 *
 */
public class AppListBean {

	/**
	 * 包名
	 */
	private String trackName;
	
	/**
	 * 应用id
	 */
	private String bundleId;
	
	/**
	 * 价格(美元)
	 */
	private String price;
	
	/**
	 * 类别
	 */
	private String primaryGenreName;
	
	/**
	 * 图片
	 */
	private String artworkUrl100;
	
	/**
	 * 显示名称
	 */
	private String displayTrackName;
	
	/**
	 * 作者
	 */
	private String artistName;
	
	/***
	 * 文件大小
	 */
	private String fileSizeBytes;
	
	/**
	 * 版本号
	 */
	private String version;
	
	/**
	 * 描述
	 */
	private String description;
	
	/**
	 * iTunesStoreID
	 */
	private String trackId;

	/**
	 * 格式化价格
	 */
	private String formattedPrice;
	
	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public String getBundleId() {
		return bundleId;
	}

	public void setBundleId(String bundleId) {
		this.bundleId = bundleId;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPrimaryGenreName() {
		return primaryGenreName;
	}

	public void setPrimaryGenreName(String primaryGenreName) {
		this.primaryGenreName = primaryGenreName;
	}

	public String getArtworkUrl100() {
		return artworkUrl100;
	}

	public void setArtworkUrl100(String artworkUrl100) {
		this.artworkUrl100 = artworkUrl100;
	}

	public String getDisplayTrackName() {
		return displayTrackName;
	}

	public void setDisplayTrackName(String displayTrackName) {
		this.displayTrackName = displayTrackName;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public String getFileSizeBytes() {
		return fileSizeBytes;
	}

	public void setFileSizeBytes(String fileSizeBytes) {
		this.fileSizeBytes = fileSizeBytes;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getFormattedPrice() {
		return formattedPrice;
	}

	public void setFormattedPrice(String formattedPrice) {
		this.formattedPrice = formattedPrice;
	}
}
