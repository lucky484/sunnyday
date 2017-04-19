package com.softtek.mdm.model;

public class SecurityEventLogModel extends BaseEntity{
    
	    
	    private Integer id;
	    
	    private String eventType;
	    
	    private String level;
	    
	    private String operateContent;
	    
	    private String eventTypeStr;
	    
	    private String levelStr;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getEventType() {
			return eventType;
		}

		public void setEventType(String eventType) {
			this.eventType = eventType;
		}

		public String getLevel() {
			return level;
		}

		public void setLevel(String level) {
			this.level = level;
		}

		public String getOperateContent() {
			return operateContent;
		}

		public void setOperateContent(String operateContent) {
			this.operateContent = operateContent;
		}

		public String getEventTypeStr() {

			return eventTypeStr;
		}

		public void setEventTypeStr(String eventTypeStr) {
			this.eventTypeStr = eventTypeStr;
		}

		public String getLevelStr() {
			return levelStr;
		}

		public void setLevelStr(String levelStr) {
			this.levelStr = levelStr;
		}
	    
	    
}
