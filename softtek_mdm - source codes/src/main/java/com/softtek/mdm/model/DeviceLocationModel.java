package com.softtek.mdm.model;

public class DeviceLocationModel extends BaseEntity{

	    private Integer id;
	    
	    private Integer deviceId;
	    
	    private String longitude;
	    
	    private String latitude;
	    
	    private String locDec;
	    
	    private String sn;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Integer getDeviceId() {
			return deviceId;
		}

		public void setDeviceId(Integer deviceId) {
			this.deviceId = deviceId;
		}

		public String getLongitude() {
			return longitude;
		}

		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}

		public String getLatitude() {
			return latitude;
		}

		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}

		public String getLocDec() {
			return locDec;
		}

		public void setLocDec(String locDec) {
			this.locDec = locDec;
		}

		public String getSn() {
			return sn;
		}

		public void setSn(String sn) {
			this.sn = sn;
		}

	    
}
