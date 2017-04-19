package com.softtek.mdm.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Ios设备策略
 * @author jane.hui
 *
 */
public class IosDevicePolicy implements Serializable {
    /**
     * 主键id
     * @mbggenerated
     */
    private Integer id;

    /**
     * 策略名称
     * @mbggenerated
     */
    private String name;

    /**
     * 策略描述
     * @mbggenerated
     */
    private String description;

    /**
     * 是否启用(1.启用 0.禁用)
     * @mbggenerated
     */
    private String isEnable;

    /**
     * 对应机构
     * @mbggenerated
     */
    private Integer orgId;

    /**
     * 是否启用密码(1.启用 0.不启用)
     */
    private String isEnablePassword;
    
    /**
     * 允许简单值
     * @mbggenerated
     */
    private String allowSimpleValue;

    /**
     * 要求字母和数字值
     * @mbggenerated
     */
    private String letterDigitValue;

    /**
     * 最小密码长度
     * @mbggenerated
     */
    private String passwordLength;

    /**
     * 复杂字符的最小数量
     * @mbggenerated
     */
    private String passwordComplexity;

    /**
     * 自动锁定前最长时间
     * @mbggenerated
     */
    private String lockLongestTime;

    /**
     * 设备锁定前的最长宽限时间
     * @mbggenerated
     */
    private String graceTime;

    /**
     * 最多可允许的失败次数 
     * @mbggenerated
     */
    private String failureTimes;

    /**
     * 密码的最长有效期（天） 
     * @mbggenerated
     */
    private Integer validityPeriod;

    /**
     * 密码历史记录
     * @mbggenerated
     */
    private Integer passwordHistory;

    /**
     * 允许安装应用程序
     * @mbggenerated
     */
    private String allowInstallApp;

    /**
     * 允许移除应用程序
     * @mbggenerated
     */
    private String allowRemoveApp;

    /**
     * 允许使用相机
     * @mbggenerated
     */
    private String allowUseCamera;

    /**
     * 允许FaceTime
     * @mbggenerated
     */
    private String allowFaceTime;

    /**
     * 允许屏幕捕捉
     * @mbggenerated
     */
    private String allowScreenCatch;

    /**
     * 允许Siri
     * @mbggenerated
     */
    private String allowSiri;

    /**
     * 允许语音拨号
     * @mbggenerated
     */
    private String allowVoiceDialing;

    /**
     * 允许应用内购买
     * @mbggenerated
     */
    private String allowAppPurchase;

    /**
     * 强制用户为所有购买项目输入iTunes Store密码
     * @mbggenerated
     */
    private String forceItunesStore;

    /**
     * 限制广告追踪
     * @mbggenerated
     */
    private String limitAdvertTracking;

    /**
     * 允许修改账户设置
     * @mbggenerated
     */
    private String allowModifyUser;

    /**
     * 允许修改“查找我的朋友”设置
     * @mbggenerated
     */
    private String allowFindFriends;

    /**
     * 允许与未安装Configurator的主机配对
     * @mbggenerated
     */
    private String allowHostPair;

    /**
     * 允许锁屏时显示控制中心的消息
     * @mbggenerated
     */
    private String allowDisplayNews;

    /**
     * 允许指纹解锁
     * @mbggenerated
     */
    private String allowFingerprintUnlock;

    /**
     * 允许锁屏时显示通知消息
     * @mbggenerated
     */
    private String allowNoticeNews;

    /**
     * 允许锁屏时显示Passbook消息
     * @mbggenerated
     */
    private String allowDisplayPassbook;

    /**
     * 允许使用YouTube
     * @mbggenerated
     */
    private String allowUseYoutube;

    /**
     * 允许使用iTunes Store
     * @mbggenerated
     */
    private String allowUseItunes;

    /**
     * 允许AirDrop
     * @mbggenerated
     */
    private String allowAirdrop;

    /**
     * 允许iMessage
     * @mbggenerated
     */
    private String allowImessage;

    /**
     * 允许使用iBooks Store
     * @mbggenerated
     */
    private String allowIbookStore;

    /**
     * 允许使用Game Center
     * @mbggenerated
     */
    private String allowGameCenter;

    /**
     * 允许使用Safari
     * @mbggeneratedf
     */
    private String allowUseSafari;

    /**
     * 允许备份
     * @mbggenerated
     */
    private String allowBackup;

    /**
     * 允许文档同步
     * @mbggenerated
     */
    private String allowDocumentSynchronization;

    /**
     * 允许照片流（不允许会导致数据丢失）
     * @mbggenerated
     */
    private String allowPhotoStream;

    /**
     * 安全类型
     * @mbggenerated
     */
    private String safeType;

    /**
     * 创建时间
     * @mbggenerated
     */
    private Date createDate;

    /**
     * 创建者
     * @mbggenerated
     */
    private Integer createUser;

    /**
     * 更新时间
     * @mbggenerated
     */
    private Date updateDate;

    /**
     * 更新者
     * @mbggenerated
     */
    private Integer updateUser;

    /**
     * 删除时间
     * @mbggenerated
     */
    private Date deleteTime;
    
    /**
     * 版本号
     */
    private String version;

    /**
     * 允许添加 Game Center好友
     */
    private String allowAddingGameCenterFriends;
    
    /**
     * 授权部门list
     */
    private List<IospolicyDepartment> iosDepartmentList;
    
    /**
     * 授权虚拟组list
     */
    private List<IosPolicyVirtual> iosVirtualList;
    
    /**
     * 授权用户list
     */
    private List<IosPolicyUser> iosUserList;
    
    /**
     * 授权wifi list
     */
    private List<IosWifiConfigure> iosWifiList;
    
    /**
     * 授权用户id list
     */
    private List<Integer> userIdList;
    
    /**
     * 黑名单List
     */
    private List<BlackWhiteListUrl> urlList;
    
    /**
     * 允许受管控的应用打开不受管控的应用的文档
     */
    private String allowOpenFromManagedToUnmanaged;
    
    /**
     * 允许不受管控的应用打开受管控的应用的文档
     */
    private String allowOpenFromUnmanagedToManaged;
    
    /**
     * 允许在漫游时自动同步
     */
    private String allowGlobalBackgroundFetchWhenRoaming;
    
    /**
     * 设备锁定时允许使用Siri
     */
    private String allowAssistantWhileLocked;
    
    /**
     * 允许锁屏时显示TodayView的消息
     */
    private String allowLockScreenTodayView;
    
    /**
     * 允许系统密钥管理在线更新
     */
    private String allowCloudKeychainSync;
    
    /**
     * 允许锁屏时显示控制中心的消息
     */
    private String allowLockScreenControlCenter;
    
    /**
     * 允许指纹解锁
     */
    private String allowFingerprintForUnlock;
    
    /**
     * 允许锁屏时显示通知消息
     */
    private String allowLockScreenNotificationsView;
    
    /**
     * 允许被管理的应用将数据存储到iCloud
     */
    private String allowManagedAppsCloudSync;
    
    /**
     * 允许iCloud照片图库
     */
    private String allowCloudPhotoLibrary;
    
    /**
     * 允许iCloud照片共享
     */
    private String allowSharedStream;
    
    /**
     * 允许handoff
     */
    private String allowActivityContinuation;
    
    /**
     * 允许多人游戏
     */
    private String allowMultiplayerGaming;
    
    /**
     * 是否限制上网时间段(1.限制上网时间段  0.不限制上网时间段)
     */
    private String isNetLimit;
    
    /**
     * 上网时间段字符串
     */
    private String visitTimeStr;
    
    /**
     * 是否启用网页黑名单(0.启用白名单 1.启用黑名单)
     */
    private String enableBlacklist;
    
    /**
     * 是否启用应用黑名单(0.启用白名单  1.启用黑名单)
     */
    private String enableAppNameList;
    
    /**
     * ios设备策略网页id列表
     */
    private String ids;
    
    /**
     * ios设备策略网页名单列表
     */
    private String names;
    
    /**
     * 设备token列表
     */
    private List<String> deviceTokenList;
    
    public String getAllowAddingGameCenterFriends() {
		return allowAddingGameCenterFriends;
	}

	public void setAllowAddingGameCenterFriends(String allowAddingGameCenterFriends) {
		this.allowAddingGameCenterFriends = allowAddingGameCenterFriends;
	}

	/**
     * 序列号
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable == null ? null : isEnable.trim();
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }
    
    public String getIsEnablePassword() {
		return isEnablePassword;
	}

	public void setIsEnablePassword(String isEnablePassword) {
		this.isEnablePassword = isEnablePassword;
	}

	public String getAllowSimpleValue() {
        return allowSimpleValue;
    }

    public void setAllowSimpleValue(String allowSimpleValue) {
        this.allowSimpleValue = allowSimpleValue == null ? null : allowSimpleValue.trim();
    }
    
    public String getLetterDigitValue() {
        return letterDigitValue;
    }

    public void setLetterDigitValue(String letterDigitValue) {
        this.letterDigitValue = letterDigitValue == null ? null : letterDigitValue.trim();
    }

    public String getPasswordLength() {
        return passwordLength;
    }

    public void setPasswordLength(String passwordLength) {
        this.passwordLength = passwordLength == null ? null : passwordLength.trim();
    }
    
    public String getPasswordComplexity() {
        return passwordComplexity;
    }

    public void setPasswordComplexity(String passwordComplexity) {
        this.passwordComplexity = passwordComplexity == null ? null : passwordComplexity.trim();
    }

    public String getLockLongestTime() {
        return lockLongestTime;
    }

    public void setLockLongestTime(String lockLongestTime) {
        this.lockLongestTime = lockLongestTime == null ? null : lockLongestTime.trim();
    }

    public String getGraceTime() {
        return graceTime;
    }

    public void setGraceTime(String graceTime) {
        this.graceTime = graceTime == null ? null : graceTime.trim();
    }

    public String getFailureTimes() {
        return failureTimes;
    }

    public void setFailureTimes(String failureTimes) {
        this.failureTimes = failureTimes == null ? null : failureTimes.trim();
    }

    public Integer getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(Integer validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public Integer getPasswordHistory() {
        return passwordHistory;
    }

    public void setPasswordHistory(Integer passwordHistory) {
        this.passwordHistory = passwordHistory;
    }

    public String getAllowInstallApp() {
        return allowInstallApp;
    }

    public void setAllowInstallApp(String allowInstallApp) {
        this.allowInstallApp = allowInstallApp == null ? null : allowInstallApp.trim();
    }

    public String getAllowRemoveApp() {
        return allowRemoveApp;
    }

    public void setAllowRemoveApp(String allowRemoveApp) {
        this.allowRemoveApp = allowRemoveApp == null ? null : allowRemoveApp.trim();
    }

    public String getAllowUseCamera() {
        return allowUseCamera;
    }
    
    public void setAllowUseCamera(String allowUseCamera) {
        this.allowUseCamera = allowUseCamera == null ? null : allowUseCamera.trim();
    }

    public String getAllowFaceTime() {
        return allowFaceTime;
    }

    public void setAllowFaceTime(String allowFaceTime) {
        this.allowFaceTime = allowFaceTime == null ? null : allowFaceTime.trim();
    }
    
    public String getAllowScreenCatch() {
        return allowScreenCatch;
    }
    
    public void setAllowScreenCatch(String allowScreenCatch) {
        this.allowScreenCatch = allowScreenCatch == null ? null : allowScreenCatch.trim();
    }
    
    public String getAllowSiri() {
        return allowSiri;
    }

    public void setAllowSiri(String allowSiri) {
        this.allowSiri = allowSiri == null ? null : allowSiri.trim();
    }

    public String getAllowVoiceDialing() {
        return allowVoiceDialing;
    }
    
    public void setAllowVoiceDialing(String allowVoiceDialing) {
        this.allowVoiceDialing = allowVoiceDialing == null ? null : allowVoiceDialing.trim();
    }

    public String getAllowAppPurchase() {
        return allowAppPurchase;
    }

    public void setAllowAppPurchase(String allowAppPurchase) {
        this.allowAppPurchase = allowAppPurchase == null ? null : allowAppPurchase.trim();
    }
    
    public String getForceItunesStore() {
        return forceItunesStore;
    }

    public void setForceItunesStore(String forceItunesStore) {
        this.forceItunesStore = forceItunesStore == null ? null : forceItunesStore.trim();
    }
    
    public String getLimitAdvertTracking() {
        return limitAdvertTracking;
    }

    public void setLimitAdvertTracking(String limitAdvertTracking) {
        this.limitAdvertTracking = limitAdvertTracking == null ? null : limitAdvertTracking.trim();
    }

    public String getAllowModifyUser() {
        return allowModifyUser;
    }

    public void setAllowModifyUser(String allowModifyUser) {
        this.allowModifyUser = allowModifyUser == null ? null : allowModifyUser.trim();
    }
    
    public String getAllowFindFriends() {
        return allowFindFriends;
    }

    public void setAllowFindFriends(String allowFindFriends) {
        this.allowFindFriends = allowFindFriends == null ? null : allowFindFriends.trim();
    }

    public String getAllowHostPair() {
        return allowHostPair;
    }

    public void setAllowHostPair(String allowHostPair) {
        this.allowHostPair = allowHostPair == null ? null : allowHostPair.trim();
    }
    
    public String getAllowDisplayNews() {
        return allowDisplayNews;
    }

    public void setAllowDisplayNews(String allowDisplayNews) {
        this.allowDisplayNews = allowDisplayNews == null ? null : allowDisplayNews.trim();
    }

    public String getAllowFingerprintUnlock() {
        return allowFingerprintUnlock;
    }
    
    public void setAllowFingerprintUnlock(String allowFingerprintUnlock) {
        this.allowFingerprintUnlock = allowFingerprintUnlock == null ? null : allowFingerprintUnlock.trim();
    }

    public String getAllowNoticeNews() {
        return allowNoticeNews;
    }

    public void setAllowNoticeNews(String allowNoticeNews) {
        this.allowNoticeNews = allowNoticeNews == null ? null : allowNoticeNews.trim();
    }
    
    public String getAllowDisplayPassbook() {
        return allowDisplayPassbook;
    }

    public void setAllowDisplayPassbook(String allowDisplayPassbook) {
        this.allowDisplayPassbook = allowDisplayPassbook == null ? null : allowDisplayPassbook.trim();
    }

    public String getAllowUseYoutube() {
        return allowUseYoutube;
    }

    public void setAllowUseYoutube(String allowUseYoutube) {
        this.allowUseYoutube = allowUseYoutube == null ? null : allowUseYoutube.trim();
    }

    public String getAllowUseItunes() {
        return allowUseItunes;
    }

    public void setAllowUseItunes(String allowUseItunes) {
        this.allowUseItunes = allowUseItunes == null ? null : allowUseItunes.trim();
    }

    public String getAllowAirdrop() {
        return allowAirdrop;
    }

    public void setAllowAirdrop(String allowAirdrop) {
        this.allowAirdrop = allowAirdrop == null ? null : allowAirdrop.trim();
    }

    public String getAllowImessage() {
        return allowImessage;
    }

    public void setAllowImessage(String allowImessage) {
        this.allowImessage = allowImessage == null ? null : allowImessage.trim();
    }

    public String getAllowIbookStore() {
        return allowIbookStore;
    }

    public void setAllowIbookStore(String allowIbookStore) {
        this.allowIbookStore = allowIbookStore == null ? null : allowIbookStore.trim();
    }

    public String getAllowGameCenter() {
        return allowGameCenter;
    }

    public void setAllowGameCenter(String allowGameCenter) {
        this.allowGameCenter = allowGameCenter == null ? null : allowGameCenter.trim();
    }

    public String getAllowUseSafari() {
        return allowUseSafari;
    }

    public void setAllowUseSafari(String allowUseSafari) {
        this.allowUseSafari = allowUseSafari == null ? null : allowUseSafari.trim();
    }

    public String getAllowBackup() {
        return allowBackup;
    }

    public void setAllowBackup(String allowBackup) {
        this.allowBackup = allowBackup == null ? null : allowBackup.trim();
    }

    public String getAllowDocumentSynchronization() {
        return allowDocumentSynchronization;
    }

    public void setAllowDocumentSynchronization(String allowDocumentSynchronization) {
        this.allowDocumentSynchronization = allowDocumentSynchronization == null ? null : allowDocumentSynchronization.trim();
    }

    public String getAllowPhotoStream() {
        return allowPhotoStream;
    }

 
    public void setAllowPhotoStream(String allowPhotoStream) {
        this.allowPhotoStream = allowPhotoStream == null ? null : allowPhotoStream.trim();
    }

    public String getSafeType() {
        return safeType;
    }

    public void setSafeType(String safeType) {
        this.safeType = safeType == null ? null : safeType.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    
    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }
 
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public List<IospolicyDepartment> getIosDepartmentList() {
		return iosDepartmentList;
	}

	public void setIosDepartmentList(List<IospolicyDepartment> iosDepartmentList) {
		this.iosDepartmentList = iosDepartmentList;
	}

	public List<IosPolicyVirtual> getIosVirtualList() {
		return iosVirtualList;
	}

	public void setIosVirtualList(List<IosPolicyVirtual> iosVirtualList) {
		this.iosVirtualList = iosVirtualList;
	}

	public List<IosPolicyUser> getIosUserList() {
		return iosUserList;
	}

	public void setIosUserList(List<IosPolicyUser> iosUserList) {
		this.iosUserList = iosUserList;
	}

	public List<IosWifiConfigure> getIosWifiList() {
		return iosWifiList;
	}

	public void setIosWifiList(List<IosWifiConfigure> iosWifiList) {
		this.iosWifiList = iosWifiList;
	}
	
	public List<Integer> getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(List<Integer> userIdList) {
		this.userIdList = userIdList;
	}
	
	public String getAllowOpenFromManagedToUnmanaged() {
		return allowOpenFromManagedToUnmanaged;
	}

	public void setAllowOpenFromManagedToUnmanaged(String allowOpenFromManagedToUnmanaged) {
		this.allowOpenFromManagedToUnmanaged = allowOpenFromManagedToUnmanaged;
	}

	public String getAllowOpenFromUnmanagedToManaged() {
		return allowOpenFromUnmanagedToManaged;
	}

	public void setAllowOpenFromUnmanagedToManaged(String allowOpenFromUnmanagedToManaged) {
		this.allowOpenFromUnmanagedToManaged = allowOpenFromUnmanagedToManaged;
	}

	public String getAllowGlobalBackgroundFetchWhenRoaming() {
		return allowGlobalBackgroundFetchWhenRoaming;
	}

	public void setAllowGlobalBackgroundFetchWhenRoaming(String allowGlobalBackgroundFetchWhenRoaming) {
		this.allowGlobalBackgroundFetchWhenRoaming = allowGlobalBackgroundFetchWhenRoaming;
	}

	public String getAllowAssistantWhileLocked() {
		return allowAssistantWhileLocked;
	}

	public void setAllowAssistantWhileLocked(String allowAssistantWhileLocked) {
		this.allowAssistantWhileLocked = allowAssistantWhileLocked;
	}

	public String getAllowLockScreenTodayView() {
		return allowLockScreenTodayView;
	}

	public void setAllowLockScreenTodayView(String allowLockScreenTodayView) {
		this.allowLockScreenTodayView = allowLockScreenTodayView;
	}

	public String getAllowCloudKeychainSync() {
		return allowCloudKeychainSync;
	}

	public void setAllowCloudKeychainSync(String allowCloudKeychainSync) {
		this.allowCloudKeychainSync = allowCloudKeychainSync;
	}

	public String getAllowLockScreenControlCenter() {
		return allowLockScreenControlCenter;
	}

	public void setAllowLockScreenControlCenter(String allowLockScreenControlCenter) {
		this.allowLockScreenControlCenter = allowLockScreenControlCenter;
	}

	public String getAllowFingerprintForUnlock() {
		return allowFingerprintForUnlock;
	}

	public void setAllowFingerprintForUnlock(String allowFingerprintForUnlock) {
		this.allowFingerprintForUnlock = allowFingerprintForUnlock;
	}

	public String getAllowLockScreenNotificationsView() {
		return allowLockScreenNotificationsView;
	}

	public void setAllowLockScreenNotificationsView(String allowLockScreenNotificationsView) {
		this.allowLockScreenNotificationsView = allowLockScreenNotificationsView;
	}

	public String getAllowManagedAppsCloudSync() {
		return allowManagedAppsCloudSync;
	}

	public void setAllowManagedAppsCloudSync(String allowManagedAppsCloudSync) {
		this.allowManagedAppsCloudSync = allowManagedAppsCloudSync;
	}

	public String getAllowCloudPhotoLibrary() {
		return allowCloudPhotoLibrary;
	}

	public void setAllowCloudPhotoLibrary(String allowCloudPhotoLibrary) {
		this.allowCloudPhotoLibrary = allowCloudPhotoLibrary;
	}

	public String getAllowSharedStream() {
		return allowSharedStream;
	}

	public void setAllowSharedStream(String allowSharedStream) {
		this.allowSharedStream = allowSharedStream;
	}
	
	public String getAllowActivityContinuation() {
		return allowActivityContinuation;
	}

	public void setAllowActivityContinuation(String allowActivityContinuation) {
		this.allowActivityContinuation = allowActivityContinuation;
	}

	public String getAllowMultiplayerGaming() {
		return allowMultiplayerGaming;
	}

	public void setAllowMultiplayerGaming(String allowMultiplayerGaming) {
		this.allowMultiplayerGaming = allowMultiplayerGaming;
	}
	
	public List<BlackWhiteListUrl> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<BlackWhiteListUrl> urlList) {
		this.urlList = urlList;
	}

	public String getIsNetLimit() {
		return isNetLimit;
	}

	public void setIsNetLimit(String isNetLimit) {
		this.isNetLimit = isNetLimit;
	}
	
	public String getVisitTimeStr() {
		return visitTimeStr;
	}

	public void setVisitTimeStr(String visitTimeStr) {
		this.visitTimeStr = visitTimeStr;
	}
	
	public String getEnableBlacklist() {
		return enableBlacklist;
	}

	public void setEnableBlacklist(String enableBlacklist) {
		this.enableBlacklist = enableBlacklist;
	}

	public String getEnableAppNameList() {
		return enableAppNameList;
	}

	public void setEnableAppNameList(String enableAppNameList) {
		this.enableAppNameList = enableAppNameList;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public List<String> getDeviceTokenList() {
		return deviceTokenList;
	}

	public void setDeviceTokenList(List<String> deviceTokenList) {
		this.deviceTokenList = deviceTokenList;
	}

	@Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        IosDevicePolicy other = (IosDevicePolicy) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getIsEnable() == null ? other.getIsEnable() == null : this.getIsEnable().equals(other.getIsEnable()))
            && (this.getOrgId() == null ? other.getOrgId() == null : this.getOrgId().equals(other.getOrgId()))
            && (this.getAllowSimpleValue() == null ? other.getAllowSimpleValue() == null : this.getAllowSimpleValue().equals(other.getAllowSimpleValue()))
            && (this.getLetterDigitValue() == null ? other.getLetterDigitValue() == null : this.getLetterDigitValue().equals(other.getLetterDigitValue()))
            && (this.getPasswordLength() == null ? other.getPasswordLength() == null : this.getPasswordLength().equals(other.getPasswordLength()))
            && (this.getPasswordComplexity() == null ? other.getPasswordComplexity() == null : this.getPasswordComplexity().equals(other.getPasswordComplexity()))
            && (this.getLockLongestTime() == null ? other.getLockLongestTime() == null : this.getLockLongestTime().equals(other.getLockLongestTime()))
            && (this.getGraceTime() == null ? other.getGraceTime() == null : this.getGraceTime().equals(other.getGraceTime()))
            && (this.getFailureTimes() == null ? other.getFailureTimes() == null : this.getFailureTimes().equals(other.getFailureTimes()))
            && (this.getValidityPeriod() == null ? other.getValidityPeriod() == null : this.getValidityPeriod().equals(other.getValidityPeriod()))
            && (this.getPasswordHistory() == null ? other.getPasswordHistory() == null : this.getPasswordHistory().equals(other.getPasswordHistory()))
            && (this.getAllowInstallApp() == null ? other.getAllowInstallApp() == null : this.getAllowInstallApp().equals(other.getAllowInstallApp()))
            && (this.getAllowRemoveApp() == null ? other.getAllowRemoveApp() == null : this.getAllowRemoveApp().equals(other.getAllowRemoveApp()))
            && (this.getAllowUseCamera() == null ? other.getAllowUseCamera() == null : this.getAllowUseCamera().equals(other.getAllowUseCamera()))
            && (this.getAllowFaceTime() == null ? other.getAllowFaceTime() == null : this.getAllowFaceTime().equals(other.getAllowFaceTime()))
            && (this.getAllowScreenCatch() == null ? other.getAllowScreenCatch() == null : this.getAllowScreenCatch().equals(other.getAllowScreenCatch()))
            && (this.getAllowSiri() == null ? other.getAllowSiri() == null : this.getAllowSiri().equals(other.getAllowSiri()))
            && (this.getAllowVoiceDialing() == null ? other.getAllowVoiceDialing() == null : this.getAllowVoiceDialing().equals(other.getAllowVoiceDialing()))
            && (this.getAllowAppPurchase() == null ? other.getAllowAppPurchase() == null : this.getAllowAppPurchase().equals(other.getAllowAppPurchase()))
            && (this.getForceItunesStore() == null ? other.getForceItunesStore() == null : this.getForceItunesStore().equals(other.getForceItunesStore()))
            && (this.getLimitAdvertTracking() == null ? other.getLimitAdvertTracking() == null : this.getLimitAdvertTracking().equals(other.getLimitAdvertTracking()))
            && (this.getAllowModifyUser() == null ? other.getAllowModifyUser() == null : this.getAllowModifyUser().equals(other.getAllowModifyUser()))
            && (this.getAllowFindFriends() == null ? other.getAllowFindFriends() == null : this.getAllowFindFriends().equals(other.getAllowFindFriends()))
            && (this.getAllowHostPair() == null ? other.getAllowHostPair() == null : this.getAllowHostPair().equals(other.getAllowHostPair()))
            && (this.getAllowDisplayNews() == null ? other.getAllowDisplayNews() == null : this.getAllowDisplayNews().equals(other.getAllowDisplayNews()))
            && (this.getAllowFingerprintUnlock() == null ? other.getAllowFingerprintUnlock() == null : this.getAllowFingerprintUnlock().equals(other.getAllowFingerprintUnlock()))
            && (this.getAllowNoticeNews() == null ? other.getAllowNoticeNews() == null : this.getAllowNoticeNews().equals(other.getAllowNoticeNews()))
            && (this.getAllowDisplayPassbook() == null ? other.getAllowDisplayPassbook() == null : this.getAllowDisplayPassbook().equals(other.getAllowDisplayPassbook()))
            && (this.getAllowUseYoutube() == null ? other.getAllowUseYoutube() == null : this.getAllowUseYoutube().equals(other.getAllowUseYoutube()))
            && (this.getAllowUseItunes() == null ? other.getAllowUseItunes() == null : this.getAllowUseItunes().equals(other.getAllowUseItunes()))
            && (this.getAllowAirdrop() == null ? other.getAllowAirdrop() == null : this.getAllowAirdrop().equals(other.getAllowAirdrop()))
            && (this.getAllowImessage() == null ? other.getAllowImessage() == null : this.getAllowImessage().equals(other.getAllowImessage()))
            && (this.getAllowIbookStore() == null ? other.getAllowIbookStore() == null : this.getAllowIbookStore().equals(other.getAllowIbookStore()))
            && (this.getAllowGameCenter() == null ? other.getAllowGameCenter() == null : this.getAllowGameCenter().equals(other.getAllowGameCenter()))
            && (this.getAllowUseSafari() == null ? other.getAllowUseSafari() == null : this.getAllowUseSafari().equals(other.getAllowUseSafari()))
            && (this.getAllowBackup() == null ? other.getAllowBackup() == null : this.getAllowBackup().equals(other.getAllowBackup()))
            && (this.getAllowDocumentSynchronization() == null ? other.getAllowDocumentSynchronization() == null : this.getAllowDocumentSynchronization().equals(other.getAllowDocumentSynchronization()))
            && (this.getAllowPhotoStream() == null ? other.getAllowPhotoStream() == null : this.getAllowPhotoStream().equals(other.getAllowPhotoStream()))
            && (this.getSafeType() == null ? other.getSafeType() == null : this.getSafeType().equals(other.getSafeType()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateDate() == null ? other.getUpdateDate() == null : this.getUpdateDate().equals(other.getUpdateDate()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getDeleteTime() == null ? other.getDeleteTime() == null : this.getDeleteTime().equals(other.getDeleteTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getIsEnable() == null) ? 0 : getIsEnable().hashCode());
        result = prime * result + ((getOrgId() == null) ? 0 : getOrgId().hashCode());
        result = prime * result + ((getAllowSimpleValue() == null) ? 0 : getAllowSimpleValue().hashCode());
        result = prime * result + ((getLetterDigitValue() == null) ? 0 : getLetterDigitValue().hashCode());
        result = prime * result + ((getPasswordLength() == null) ? 0 : getPasswordLength().hashCode());
        result = prime * result + ((getPasswordComplexity() == null) ? 0 : getPasswordComplexity().hashCode());
        result = prime * result + ((getLockLongestTime() == null) ? 0 : getLockLongestTime().hashCode());
        result = prime * result + ((getGraceTime() == null) ? 0 : getGraceTime().hashCode());
        result = prime * result + ((getFailureTimes() == null) ? 0 : getFailureTimes().hashCode());
        result = prime * result + ((getValidityPeriod() == null) ? 0 : getValidityPeriod().hashCode());
        result = prime * result + ((getPasswordHistory() == null) ? 0 : getPasswordHistory().hashCode());
        result = prime * result + ((getAllowInstallApp() == null) ? 0 : getAllowInstallApp().hashCode());
        result = prime * result + ((getAllowRemoveApp() == null) ? 0 : getAllowRemoveApp().hashCode());
        result = prime * result + ((getAllowUseCamera() == null) ? 0 : getAllowUseCamera().hashCode());
        result = prime * result + ((getAllowFaceTime() == null) ? 0 : getAllowFaceTime().hashCode());
        result = prime * result + ((getAllowScreenCatch() == null) ? 0 : getAllowScreenCatch().hashCode());
        result = prime * result + ((getAllowSiri() == null) ? 0 : getAllowSiri().hashCode());
        result = prime * result + ((getAllowVoiceDialing() == null) ? 0 : getAllowVoiceDialing().hashCode());
        result = prime * result + ((getAllowAppPurchase() == null) ? 0 : getAllowAppPurchase().hashCode());
        result = prime * result + ((getForceItunesStore() == null) ? 0 : getForceItunesStore().hashCode());
        result = prime * result + ((getLimitAdvertTracking() == null) ? 0 : getLimitAdvertTracking().hashCode());
        result = prime * result + ((getAllowModifyUser() == null) ? 0 : getAllowModifyUser().hashCode());
        result = prime * result + ((getAllowFindFriends() == null) ? 0 : getAllowFindFriends().hashCode());
        result = prime * result + ((getAllowHostPair() == null) ? 0 : getAllowHostPair().hashCode());
        result = prime * result + ((getAllowDisplayNews() == null) ? 0 : getAllowDisplayNews().hashCode());
        result = prime * result + ((getAllowFingerprintUnlock() == null) ? 0 : getAllowFingerprintUnlock().hashCode());
        result = prime * result + ((getAllowNoticeNews() == null) ? 0 : getAllowNoticeNews().hashCode());
        result = prime * result + ((getAllowDisplayPassbook() == null) ? 0 : getAllowDisplayPassbook().hashCode());
        result = prime * result + ((getAllowUseYoutube() == null) ? 0 : getAllowUseYoutube().hashCode());
        result = prime * result + ((getAllowUseItunes() == null) ? 0 : getAllowUseItunes().hashCode());
        result = prime * result + ((getAllowAirdrop() == null) ? 0 : getAllowAirdrop().hashCode());
        result = prime * result + ((getAllowImessage() == null) ? 0 : getAllowImessage().hashCode());
        result = prime * result + ((getAllowIbookStore() == null) ? 0 : getAllowIbookStore().hashCode());
        result = prime * result + ((getAllowGameCenter() == null) ? 0 : getAllowGameCenter().hashCode());
        result = prime * result + ((getAllowUseSafari() == null) ? 0 : getAllowUseSafari().hashCode());
        result = prime * result + ((getAllowBackup() == null) ? 0 : getAllowBackup().hashCode());
        result = prime * result + ((getAllowDocumentSynchronization() == null) ? 0 : getAllowDocumentSynchronization().hashCode());
        result = prime * result + ((getAllowPhotoStream() == null) ? 0 : getAllowPhotoStream().hashCode());
        result = prime * result + ((getSafeType() == null) ? 0 : getSafeType().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateDate() == null) ? 0 : getUpdateDate().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getDeleteTime() == null) ? 0 : getDeleteTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", isEnable=").append(isEnable);
        sb.append(", orgId=").append(orgId);
        sb.append(", allowSimpleValue=").append(allowSimpleValue);
        sb.append(", letterDigitValue=").append(letterDigitValue);
        sb.append(", passwordLength=").append(passwordLength);
        sb.append(", passwordComplexity=").append(passwordComplexity);
        sb.append(", lockLongestTime=").append(lockLongestTime);
        sb.append(", graceTime=").append(graceTime);
        sb.append(", failureTimes=").append(failureTimes);
        sb.append(", validityPeriod=").append(validityPeriod);
        sb.append(", passwordHistory=").append(passwordHistory);
        sb.append(", allowInstallApp=").append(allowInstallApp);
        sb.append(", allowRemoveApp=").append(allowRemoveApp);
        sb.append(", allowUseCamera=").append(allowUseCamera);
        sb.append(", allowFaceTime=").append(allowFaceTime);
        sb.append(", allowScreenCatch=").append(allowScreenCatch);
        sb.append(", allowSiri=").append(allowSiri);
        sb.append(", allowVoiceDialing=").append(allowVoiceDialing);
        sb.append(", allowAppPurchase=").append(allowAppPurchase);
        sb.append(", forceItunesStore=").append(forceItunesStore);
        sb.append(", limitAdvertTracking=").append(limitAdvertTracking);
        sb.append(", allowModifyUser=").append(allowModifyUser);
        sb.append(", allowFindFriends=").append(allowFindFriends);
        sb.append(", allowHostPair=").append(allowHostPair);
        sb.append(", allowDisplayNews=").append(allowDisplayNews);
        sb.append(", allowFingerprintUnlock=").append(allowFingerprintUnlock);
        sb.append(", allowNoticeNews=").append(allowNoticeNews);
        sb.append(", allowDisplayPassbook=").append(allowDisplayPassbook);
        sb.append(", allowUseYoutube=").append(allowUseYoutube);
        sb.append(", allowUseItunes=").append(allowUseItunes);
        sb.append(", allowAirdrop=").append(allowAirdrop);
        sb.append(", allowImessage=").append(allowImessage);
        sb.append(", allowIbookStore=").append(allowIbookStore);
        sb.append(", allowGameCenter=").append(allowGameCenter);
        sb.append(", allowUseSafari=").append(allowUseSafari);
        sb.append(", allowBackup=").append(allowBackup);
        sb.append(", allowDocumentSynchronization=").append(allowDocumentSynchronization);
        sb.append(", allowPhotoStream=").append(allowPhotoStream);
        sb.append(", safeType=").append(safeType);
        sb.append(", createDate=").append(createDate);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", deleteTime=").append(deleteTime);
        sb.append("]");
        return sb.toString();
    }
}