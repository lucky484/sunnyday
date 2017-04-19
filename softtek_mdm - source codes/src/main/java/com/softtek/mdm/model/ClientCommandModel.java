package com.softtek.mdm.model;

public class ClientCommandModel extends BaseEntity{
     
	     private Integer id;
	     
	     private String sn;
	     
	     private Integer removeDevice;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getSn() {
			return sn;
		}

		public void setSn(String sn) {
			this.sn = sn;
		}

		public Integer getRemoveDevice() {
			return removeDevice;
		}

		public void setRemoveDevice(Integer removeDevice) {
			this.removeDevice = removeDevice;
		}
	     
	    
}
