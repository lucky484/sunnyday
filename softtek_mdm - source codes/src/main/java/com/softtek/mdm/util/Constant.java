package com.softtek.mdm.util;

/**
 * 常量
 * 
 * @author jane.hui
 *
 */
public interface Constant {

	// 当前页
	public static final String SECHO = "sEcho";

	// 总页数
	public static final String ITOTALRECORDS = "iTotalRecords";

	// 显示总页数
	public static final String ITOTALDISPLAYRECORDS = "iTotalDisplayRecords";

	// 返回分页list数据
	public static final String AADATA = "aaData";

	// 空字符串
	public static final String EMPTY_STR = "";

	// 是
	public static final String YES = "1";

	// 否
	public static final String NO = "0";

	// 推送设备策略默认策略(88)
	public static final String DEFAULT_VALUE = "88";

    /**
     * 分隔符
     */
    public static final String  SEPARATOR = "#";

	
	/**
	 * 空数组
	 */
	public static final String EMPTY_ARRAY = "[]";

	/**
	 * 应用发布文件上传保存路径
	 */
	public static final String FILE_PATH = "filePath";

	/**
	 * 默认策略id:0
	 */
	public static final String DEFAULT_POLICY_ID = "0";

	/**
	 * 扩展名
	 */
	public static final String EXT_NAME = ".png";

	/**
	 * 后缀.mobileconfig
	 */
	public static final String EXT_MOBILECONFIG = ".mobileconfig";

	/**
	 * 分页相关常量
	 */
	public interface PageRelatedConstant {
		/**
		 * 查询起始页
		 */
		public static final String START_PAGE = "start";

		/**
		 * 每页查询条数
		 */
		public static final String PAGE_OFFSET = "offset";
	}

	// 设备安装配置描述文件的时候认证信息
	// Topic
	public static String TOPIC = "Topic";
	// UDID
	public static String UDID = "UDID";

	/**
	 * 表示应用的发布类型
	 */
	public interface ReleaseType {

		/**
		 * ios应用
		 */
		public static final String IOS = "1";

		/**
		 * android应用
		 */
		public static final String ANDROID = "0";

	}
	
	/**
	 * 应用管理->黑白名单-应用类型(0.ios类型,1.android类型)
	 */
	public interface NameAppType {

		/**
		 * ios应用
		 */
		public static final String IOS = "0";

		/**
		 * android应用
		 */
		public static final String ANDROID = "1";

	}
	
	/**
	 * 应用管理->黑白名单-应用类型(ios,android)
	 */
	public interface NameAppTypeDesc {

		/**
		 * ios应用
		 */
		public static final String IOS = "ios";

		/**
		 * android应用
		 */
		public static final String ANDROID = "android";

	}
	
	/**
	 * 消息类型
	 */
	public interface MobileConfig {

		public static final String MESSAGE_TYPE = "MessageType";

		// 监听的主题
		public static final String TOPIC = "Topic";

		// 设备UDID
		public static final String UDID = "UDID";

		// 设备接收推送消息的唯一令牌
		public static final String TOKEN = "Token";

		// 于清除设备密码的一个令牌数据
		public static final String UNLOCK_TOKEN = "UnlockToken";

		// MDM Server向设备推送请求时的唯一令牌
		public static final String PUSH_MAGIC = "PushMagic";

		public static final String AWAITING_CONFIGURATION = "AwaitingConfiguration";
		// 状态
		public static final String STATUS = "Status";
		// 命令uuid
		public static final String COMMAND_UUID = "CommandUUID";
		// 拒绝安装原因(当iphone、ipad设备安装应用时候发生的错误)
		public static final String REJECTION_REASON = "RejectionReason";
		
	}

	/**
	 * 获取最多可允许的失败次数值
	 * @author jane.hui
	 *
	 */
	public interface getFailureTimes{
		
		public static final String ZERO = "0";
		
		public static final String ONE = "1";
		
		public static final String TWO = "2";
		
		public static final String THREE = "3";
		
		public static final String FOUR = "4";
		
		public static final String FIVE = "5";
		
		public static final String SIX = "6";
		
	}
	
	/**
	 * 获取最多可允许的失败次数描述
	 * @author jane.hui
	 *
	 */
	public interface getFailureTimesDesc{
		
		public static final String FOUR = "4";
		
		public static final String FIVE = "5";
		
		public static final String SIX = "6";
		
		public static final String SEVEN = "7";
		
		public static final String EIGHT = "8";
		
		public static final String NINE = "9";
		
		public static final String TEN = "10";
		
	}
	
	/**
	 * 返回某个功能的模块
	 */
	public interface getModule {

		/**
		 * 应用管理
		 */
		public static final String APPLICATION = "application";

		/**
		 * 设备策略(Ios策略webclip模块)
		 */
		public static final String DEVICE_POLICY = "devicePolicy";
	}

	/**
	 * 返回服务状态
	 */
	public interface getStatus {

		/**
		 * 服务处理空闲状态
		 */
		public static final String IDLE = "Idle";

		/**
		 * 服务指令完成
		 */
		public static final String ACKNOWLEDGED = "Acknowledged";
		
		/**
		 * 服务指令already send but not response
		 */
		public static final String PENDING = "Pengding";
		
		public static final String ERROR = "Error";
		
		/**
		 * 设备以及收到指令，但是现在不能执行，将在以后执行
		 */
		public static final String NOTNOW = "NotNow";
	}

	/**
	 * 消息内容
	 * 
	 * @author jane.hui
	 *
	 */
	public interface MESSAGE_TYPE {

		/**
		 * 授权认证
		 */
		public static final String AUTHENTICATE = "Authenticate";

		public static final String TokenUpdate = "TokenUpdate";
		
		/**
		 * 卸载描述文件
		 */
		public static final String CheckOut = "CheckOut";

	}

	/**
	 * 
	 */
	public interface Control {

		public static final String ONE = "1";

		public static final String TWO = "2";

	}

	/**
	 * 上网行为常量
	 */
	public interface NetBehaviorConstant {
		/**
		 * 黑白名单类型对应key字符串
		 */
		public static final String BLACK_WHITE_LIST_TYPE = "blackWhiteListType";

		/**
		 * 黑白名单名称
		 */
		public static final String BLACK_WHITE_LIST_NAME = "blackWhiteListName";

		/**
		 * 黑白名单备注
		 */
		public static final String BLACK_WHITE_LIST_REMARK = "blackWhiteRemark";

		/**
		 * 黑白名单ID
		 */
		public static final String BLACK_WHITE_LIST_ID = "blackWhiteListId";

		/**
		 * 黑名单类型
		 */
		public static final int BLACK_LIST_TYPE = 0;

		/**
		 * 白名单类型
		 */
		public static final int WHITE_LIST_TYPE = 1;

	}

	public interface OrganizationConstant {
		public static final String enabled = "1";
		public static final String disabled = "0";
		/*
		 * public static final String exportName = "机构列表"; public static final
		 * String userStatistics = "用户统计列表"; public static final String
		 * fluxStatistics = "流量统计列表"; public static final String
		 * fluxTotalStatistics = "流量总数列表"; public static final String
		 * userTotalStatistics = "用户总数列表"; public static final String
		 * userActiveStatistics = "激活用户数列表"; public static final String
		 * userInActiveStatistics = "未激活用户数列表"; public static final String
		 * userDeleteStatistics = "删除用户数列表";
		 */
	}

	/**
	 * 全局使用常量
	 */
	public interface FullRegionUseConstant {
		/**
		 * 组织ID对应key
		 */
		public static final String ORGANIZATION_ID = "organizationId";

		/**
		 * 用户名key
		 */
		public static final String USER_NAME = "userName";
	}

	/**
	 * 分隔符常量
	 */
	public interface SplitSymbol {
		/**
		 * split symbol #
		 */
		public static final String HASHES_SYMBOL = "#";

		/**
		 * split symbol ,(EN)
		 */
		public static final String COMMA_SYMBOL = ",";

		/**
		 * 斜杠
		 */
		public static final String SLASH = "/";
	}

	/**
	 * 操作命令
	 */
	public interface Command {

		public static final String LOCK = "DeviceLock";
	}

	/**
	 * 操作指令结果
	 * 
	 * @author jane.hui
	 *
	 */
	public interface Result {
		/**
		 * 推送指令前插入Command命令时默认操作状态 0(未做任何处理)
		 */
		public static final String NONE = "0";
		/**
		 * 设备处于空闲状态
		 */
		public static final String IDLE = "1";
		/**
		 * 设备执行命令完成
		 */
		public static final String ACKNOWLEDGED = "2";
		/**
		 * 命令悬挂，表示发送了命令，但是尚未返回
		 */
		public static final String PENDING="3";
		/**
		 * 命令处理失败
		 */
		public static final String ERROR="4";
		
		/**
		 * 命令处理失败
		 */
		public static final String COMMAND_FORMAT_ERROR = "6";
		
		/**
		 * 命令已经发送成功，但是设备暂未处理
		 */
		public static final String NOTNOW="5";
	}
	
	/**
	 * 安全类型
	 * @author jane.hui
	 */
	public interface IEncryptionResult{
		
		/**
		 * WEB
		 */
		public static final String WEP = "1";
		
		/**
		 * WPA
		 */
		public static final String WPA = "2";
		
	}
	
	/**
	 * 标识plist文件元素的类型是
	 * @author jane.hui
	 *
	 */
	public interface ElementType {
		// 元素key
		public static final String KEY = "key";
		// 元素string
		public static final String STRING = "string";
		// 元素integer
		public static final String INTEGER = "integer";
		// 元素data
		public static final String DATA = "data";
		// 元素array
		public static final String ARRAY = "array";
		// 元素dict
		public static final String DICT = "dict";
		// 元素节点true
		public static final String TRUE = "true";
		// 元素节点false
		public static final String FALSE = "false";
		// version
		public static final String VERSION = "version";
		// 1.0
		public static final String ONEPOINTTWO = "1.0";
		// 元素plist
		public static final String PLIST = "plist";
		// utf-8
		public static final String UTF8 = "utf-8";
		// 头文件
		public static final String APPLE_DTD = "-//Apple//DTD PLIST 1.0//EN";
		// 头文件
		public static final String PROPERTY_LIST = "http://www.apple.com/DTDs/PropertyList-1.0.dtd";
		// 命令
		public static final String COMMAND = "Command";
		//元素number
		public static final String NUMBER = "number";
		
	}

	/**
	 * iOS的元数据配置
	 * .mobileconfig描述文件
	 * 
	 * @author color.wu
	 *
	 */
	public interface IIosMetadata {
		/**
		 * 配置通用信息模块
		 * 位于根节点plist下dict节点中
		 * 字段格式为：_标签名称或缩写_标签内容名称（upper(匈牙利命名值)）_标签值类型
		 * @author color.wu
		 *
		 */
		public interface IGeneral{
			
			/**
			 * 配置该模块的唯一ID
			 * 例如：aXN0RGV0ZWN0aW9uPC9rZXk+CgkJCTxmYWxzZS8+CgkJCTxrZXk+cmF0aW5nQXBwczwva2V5PgoJ......
			 */
			public static final String _KEY_PAYLOAD_STR="Payload";
			/**
			 * 配置设备所有模块配置的内容
			 * 处理通用模块之外，其他模块将位于当前节点的array节点中，一个dict为一个模块
			 */
			public static final String _KEY_PAYLOADCONTENT_ARRAY="PayloadContent";
			/**
			 * 配置通用模块的描述
			 * 例如：描述文件描述。
			 */
			public static final String _KEY_PAYLOADDESCRIPTION_STR="PayloadDescription";
			/**
			 * 配置通用模块的名称
			 * 例如：common
			 */
			public static final String _KEY_PAYLOADDISPLAYNAME_STR="PayloadDisplayName";
			/**
			 * 配置通用模块的唯一标识，相同的标识，则会进行覆盖安装
			 * 例如：mdm.softtek.com
			 */
			public static final String _KEY_PAYLOADIDENTIFIER_STR="PayloadIdentifier";
			/**
			 * 配置通用模块的组织名称
			 * 例如：softtek
			 */
			public static final String _KEY_PAYLOADORGANIZATION_STR="PayloadOrganization";
			/**
			 * 配置是否允许移除描述文件
			 * 例如：<false/> 或者 <true/>
			 */
			public static final String _KEY_PAYLOADREMOVALDISALLOWED_BOOL="PayloadRemovalDisallowed";
			/**
			 * 配置该文件的类型
			 * 例如：Configuration
			 */
			public static final String _KEY_PAYLOADTYPE_STR="PayloadType";
			/**
			 * 配置该模块的唯一ID
			 * 例如：0B99B37F-5C83-440B-904B-A0D853827A5A
			 */
			public static final String _KEY_PAYLOADUUID_STR="PayloadUUID";
			/**
			 * 配置该模块版本
			 * 例如：一般为1
			 */
			public static final String _KEY_PAYLOADVERSION_INT="PayloadVersion";
			
			/**
			 * 配置设备限制模块 字段格式为：_标签名称或缩写_标签内容名称（upper(匈牙利命名值)）_标签值类型 
			 * e.g.
			 * _KEY_PAYLOADDESCRIPTION_STR
			 * 表示是一个key标签，标签中内容是PayloadDescription,对于于key的值应该是string类型，所以结果是如下所示
			 * <key>PayloadDescription</key>
			 * <string>配置设备限制。</string>
			 * @author color.wu
			 *
			 */
			public interface IRestrict {
				/**
				 * 配置设备限制描述
				 * 例如：配置设备限制。
				 */
				public static final String _KEY_INSTALLPROFILE_STR = "InstallProfile";
				/**
				 * 配置设备限制描述
				 * 例如：配置设备限制。
				 */
				public static final String _KEY_PAYLOADDESCRIPTION_STR = "PayloadDescription";
				/**
				 * 配置设备限制名称，例如限制，
				 */
				public static final String _KEY_PAYLOADDISPLAYNAME_STR="PayloadDisplayName";
				/**
				 * 配置设备唯一标识
				 * 例如：com.apple.applicationaccess.A7380CBE-E641-4D8E-BC3A-388037A0BE7F
				 */
				public static final String _KEY_PAYLOADIDENTIFIER_STR="PayloadIdentifier";
				/**
				 * 配置设备所属组织机构，应该和通用配置的PayloadOrganization一样
				 * 例如：softtek
				 */
				public static final String _KEY_PAYLOADORGANIZATION_STR="PayloadOrganization";
				/**
				 * 配置设备配置类型
				 * 例如：com.apple.applicationaccess
				 */
				public static final String _KEY_PAYLOADTYPE_STR="PayloadType";
				/**
				 * 配置设备配置唯一标识
				 * 例如：A7380CBE-E641-4D8E-BC3A-388037A0BE7C
				 */
				public static final String _KEY_PAYLOADUUID_STR="PayloadUUID";
				/**
				 * 配置该模块版本
				 * 例如：一般为1
				 */
				public static final String _KEY_PAYLOADVERSION_INT="PayloadVersion";
				/**
				 * 配置设备是否允许添加游戏好友
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_ALLOWADDINGGAMECENTERFRIENDS_BOOL="allowAddingGameCenterFriends";
				/**
				 * 配置设备是否允许安装应用（也就是是否使用app store）
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_ALLOWAPPINSTALLATION_BOOL="allowAppInstallation";
				/**
				 * 配置设备是否允许使用助理
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_ALLOWASSISTANT_BOOL="allowAssistant";
				/**
				 * 配置设备是否允许锁屏的时候使用助理
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_ALLOWASSISTANTWHILELOCKED_BOOL="allowAssistantWhileLocked";
				/**
				 * 配置设备是否允许使用相机
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_ALLOWCAMERA_BOOL="allowCamera";
				/**
				 * 配置设备是否允许icloud云备份
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_ALLOWCLOUDBACKUP_BOOL="allowCloudBackup";
				/**
				 * 配置设备是否允许同步icloud云文档
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_ALLOWCLOUDDOCUMENTSYNC_BOOL="allowCloudDocumentSync";
				/**
				 * 配置设备是否允许向Apple发送诊断数据
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_ALLOWDIAGNOSTICSUBMISSION_BOOL="allowDiagnosticSubmission";
				/**
				 * 配置设备是否允许
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_ALLOWEXPLICITCONTENT_BOOL="allowExplicitContent";
				/**
				 * 配置设备是否允许在漫游时，自动同步
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_ALLOWGLOBALBACKGROUNDFETCHWHENROAMING_BOOL="allowGlobalBackgroundFetchWhenRoaming";
				/**
				 * 配置设备是否允许应用程序内购买
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_ALLOWINAPPPURCHASES_BOOL="allowInAppPurchases";
				/**
				 * 配置设备是否允许多人游戏
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_ALLOWMULTIPLAYERGAMING_BOOL="allowMultiplayerGaming";
				/**
				 * 配置设备是否允许icloud照片流
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_ALLOWPHOTOSTREAM_BOOL="allowPhotoStream";
				/**
				 * 配置设备是否允许使用safari
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_ALLOWSAFARI_BOOL="allowSafari";
				/**
				 * 配置设备是否允许捕捉屏幕
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_ALLOWSCREENSHOT_BOOL="allowScreenShot";
				/**
				 * 配置设备是否允许用户接受不被信任的TLS证书
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_ALLOWUNTRUSTEDTLSPROMPT_BOOL="allowUntrustedTLSPrompt";
				/**
				 * 配置设备是否允许用户视频会议
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_ALLOWVIDEOCONFERENCING_BOOL="allowVideoConferencing";
				/**
				 * 配置设备是否允许用户语音拨号
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_ALLOWVOICEDIALING_BOOL="allowVoiceDialing";
				/**
				 * 配置设备是否允许用户使用YouTube
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_ALLOWYOUTUBE_BOOL="allowYouTube";
				/**
				 * 配置设备是否允许用户使用iTunes
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_ALLOWITUNES_BOOL="allowiTunes";
				/**
				 * 配置设备是否强制对备份进行加密
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_FORCEENCRYPTEDBACKUP_BOOL="forceEncryptedBackup";
				/**
				 * 配置设备是否要用户在购买itunes商店商品时需要输入密码
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_FORCEITUNESSTOREPASSWORDENTRY_BOOL="forceITunesStorePasswordEntry";
				/**
				 * 配置设备APP评级
				 * 例如：1000
				 */
				public static final String _KEY_RATINGAPPS_INT="ratingApps";
				/**
				 * 配置设备电影评级
				 * 例如：1000
				 */
				public static final String _KEY_RATINGMOVIES_INT="ratingMovies";
				/**
				 * 配置设备评级地区
				 * 例如：us
				 */
				public static final String _KEY_RATINGREGION_STR="ratingRegion";
				/**
				 * 配置设备电视评级
				 * 例如：1000
				 */
				public static final String _KEY_RATINGTVSHOWS_INT="ratingTVShows";
				/**
				 * 配置设备safari允许cookies
				 * 例如：2
				 */
				public static final String _KEY_SAFARIACCEPTCOOKIES_INT="safariAcceptCookies";
				/**
				 * 配置设备safari允许自动填充
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_SAFARIALLOWAUTOFILL_BOOl="safariAllowAutoFill";
				/**
				 * 配置设备safari允许JS
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_SAFARIALLOWJAVASCRIPT_BOOl="safariAllowJavaScript";
				/**
				 * 配置设备safari是否阻止弹出是窗口
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_SAFARIALLOWPOPUPS_BOOl="safariAllowPopups";
				/**
				 * 配置设备safari是否强制发出欺诈警告
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_SAFARIFORCEFRAUDWARNING_BOOl="safariForceFraudWarning";
			}
			
			/**
			 * 配置移除描述文件模块 字段格式为：_标签名称或缩写_标签内容名称（upper(匈牙利命名值)）_标签值类型 
			 * e.g.
			 * _KEY_PAYLOADDESCRIPTION_STR
			 * 表示是一个key标签，标签中内容是PayloadDescription,对于于key的值应该是string类型，所以结果是如下所示
			 * <key>PayloadDescription</key>
			 * <string>配置用于移除描述文件的密码</string>
			 * @author color.wu
			 *
			 */
			public interface IRemoveMobileConfig {
				 /* 配置移除描述文件描述
				 * 例如：配置用于移除描述文件的密码。
				 */
				public static final String _KEY_PAYLOADDESCRIPTION_STR = "PayloadDescription";
				/**
				 * 配置移除描述文件名称，例如移除描述文件，
				 */
				public static final String _KEY_PAYLOADDISPLAYNAME_STR="PayloadDisplayName";
				/**
				 * 配置移除描述文件唯一标识
				 * 例如：com.apple.profileRemovalPassword.6A862BB1-9CF7-45C4-A0CB-ED30E3CAAD3F
				 */
				public static final String _KEY_PAYLOADIDENTIFIER_STR="PayloadIdentifier";
				/**
				 * 配置移除描述文件类型
				 * 例如：com.apple.profileRemovalPassword
				 */
				public static final String _KEY_PAYLOADTYPE_STR="PayloadType";
				/**
				 * 配置移除描述文件唯一标识
				 * 例如：6A862BB1-9CF7-45C4-A0CB-ED30E3CAAD3F
				 */
				public static final String _KEY_PAYLOADUUID_STR="PayloadUUID";
				/**
				 * 配置移除描述文件模块版本，注意类型是<real>1</real>
				 * 例如：一般为1
				 */
				public static final String _KEY_PAYLOADVERSION_REAL="PayloadVersion";
				/**
				 * 配置移除描述文件模块密码
				 * 例如：123456
				 */
				public static final String _KEY_REMOVALPASSWORD_INT="RemovalPassword";
			}
			
			/**
			 * 配置设备证书模块用于消息推送认证 字段格式为：_标签名称或缩写_标签内容名称（upper(匈牙利命名值)）_标签值类型 
			 * e.g.
			 * _KEY_PAYLOADDESCRIPTION_STR
			 * 表示是一个key标签，标签中内容是PayloadDescription,对于于key的值应该是string类型，所以结果是如下所示
			 * <key>PayloadDescription</key>
			 * <string>提供设备鉴定（证书或身份）。</string>
			 * @author color.wu
			 *
			 */
			public interface ICertificatePush{
				 /* 配置证书模块描述
				 * 例如：提供设备鉴定（证书或身份）
				 */
				public static final String _KEY_PAYLOADDESCRIPTION_STR = "PayloadDescription";
				/**
				 * 配置证书模块名称，例如customerAK.p12
				 */
				public static final String _KEY_PAYLOADDISPLAYNAME_STR="PayloadDisplayName";
				/**
				 * 配置证书模块唯一标识
				 * 例如：com.apple.security.pkcs12.F6BF0C7F-3E2C-420D-BD3C-650BE79FD923
				 */
				public static final String _KEY_PAYLOADIDENTIFIER_STR="PayloadIdentifier";
				/**
				 * 配置证书模块所属组织机构，应该和通用配置的PayloadOrganization一样
				 * 例如：softtek
				 */
				public static final String _KEY_PAYLOADORGANIZATION_STR="PayloadOrganization";
				/**
				 * 配置证书模块文件类型
				 * 例如：com.apple.security.pkcs12
				 */
				public static final String _KEY_PAYLOADTYPE_STR="PayloadType";
				/**
				 * 配置证书模块唯一标识
				 * 例如：F6BF0C7F-3E2C-420D-BD3C-650BE79FD923
				 */
				public static final String _KEY_PAYLOADUUID_STR="PayloadUUID";
				/**
				 * 配置证书模块版本
				 * 例如：一般为1
				 */
				public static final String _KEY_PAYLOADVERSION_INT="PayloadVersion";
				/**
				 * 配置证书模块证书密码
				 */
				public static final String _KEY_PASSWORD_STR="Password";
				/**
				 * 配置证书模块证书文件名称
				 */
				public static final String _KEY_PAYLOADCERTIFICATEFILENAME_STR="PayloadCertificateFileName";
				/**
				 * 配置证书模块证书内容，为加密内容
				 */
				public static final String _KEY_PAYLOADCONTENT_DATA="PayloadContent";
			}
			
			/**
			 * 配置设备证书模块用于ssl通信认证  字段格式为：_标签名称或缩写_标签内容名称（upper(匈牙利命名值)）_标签值类型 
			 * e.g.
			 * _KEY_PAYLOADDESCRIPTION_STR
			 * 表示是一个key标签，标签中内容是PayloadDescription,对于于key的值应该是string类型，所以结果是如下所示
			 * <key>PayloadDescription</key>
			 * <string>提供设备鉴定（证书或身份）。</string>
			 * @author color.wu
			 *
			 */
			public interface ICertificateSSL{
				 /* 配置证书模块描述
				 * 例如：提供设备鉴定（证书或身份）
				 */
				public static final String _KEY_PAYLOADDESCRIPTION_STR = "PayloadDescription";
				/**
				 * 配置证书模块名称，例如Cert.p12
				 */
				public static final String _KEY_PAYLOADDISPLAYNAME_STR="PayloadDisplayName";
				/**
				 * 配置证书模块唯一标识
				 * 例如：com.apple.security.pkcs12.C32B4377-5D33-443D-8391-50F12D8FC8C7
				 */
				public static final String _KEY_PAYLOADIDENTIFIER_STR="PayloadIdentifier";
				/**
				 * 配置证书模块所属组织机构，应该和通用配置的PayloadOrganization一样
				 * 例如：softtek
				 */
				public static final String _KEY_PAYLOADORGANIZATION_STR="PayloadOrganization";
				/**
				 * 配置证书模块文件类型
				 * 例如：com.apple.security.pkcs12
				 */
				public static final String _KEY_PAYLOADTYPE_STR="PayloadType";
				/**
				 * 配置证书模块唯一标识
				 * 例如：C32B4377-5D33-443D-8391-50F12D8FC8C7
				 */
				public static final String _KEY_PAYLOADUUID_STR="PayloadUUID";
				/**
				 * 配置证书模块版本
				 * 例如：一般为1
				 */
				public static final String _KEY_PAYLOADVERSION_INT="PayloadVersion";
				/**
				 * 配置证书模块证书密码
				 */
				public static final String _KEY_PASSWORD_STR="Password";
				/**
				 * 配置证书模块证书文件名称
				 */
				public static final String _KEY_PAYLOADCERTIFICATEFILENAME_STR="PayloadCertificateFileName";
				/**
				 * 配置证书模块证书内容，为加密内容
				 */
				public static final String _KEY_PAYLOADCONTENT_DATA="PayloadContent";
			}
			
			/**
			 * 配置设备wifi模块  字段格式为：_标签名称或缩写_标签内容名称（upper(匈牙利命名值)）_标签值类型 
			 * e.g.
			 * _KEY_PAYLOADDESCRIPTION_STR
			 * 表示是一个key标签，标签中内容是PayloadDescription,对于于key的值应该是string类型，所以结果是如下所示
			 * <key>PayloadDescription</key>
			 * <string>配置无线连接设置</string>
			 * @author color.wu
			 *
			 */
			public interface IWifi{
				 /* 配置设备wifi模块描述
				 * 例如：配置无线连接设置。
				 */
				public static final String _KEY_PAYLOADDESCRIPTION_STR = "PayloadDescription";
				/**
				 * 配置设备wifi模块名称，例如Wi-Fi (MDM)
				 */
				public static final String _KEY_PAYLOADDISPLAYNAME_STR="PayloadDisplayName";
				/**
				 * 配置设备wifi模块唯一标识
				 * 例如：com.apple.wifi.managed.3422D3C7-D67E-466E-B26D-7E586BB14818
				 */
				public static final String _KEY_PAYLOADIDENTIFIER_STR="PayloadIdentifier";
				/**
				 * 配置设备wifi模块所属组织机构，应该和通用配置的PayloadOrganization一样
				 * 例如：softtek
				 */
				public static final String _KEY_PAYLOADORGANIZATION_STR="PayloadOrganization";
				/**
				 * 配置设备wifi模块文件类型
				 * 例如：com.apple.wifi.managed
				 */
				public static final String _KEY_PAYLOADTYPE_STR="PayloadType";
				/**
				 * 配置设备wifi模块唯一标识
				 * 例如：51F24B23-E392-4335-ADE9-23995E9324DC
				 */
				public static final String _KEY_PAYLOADUUID_STR="PayloadUUID";
				/**
				 * 配置设备wifi模块版本
				 * 例如：一般为1
				 */
				public static final String _KEY_PAYLOADVERSION_INT="PayloadVersion";
				/**
				 * 配置设备wifi模块是否自动加入
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_AUTOJOIN_BOOL="AutoJoin";

				/**
				 * 配置设备wifi模块加密方式
				 * 例如：Any
				 */
				public static final String _KEY_ENCRYPTIONTYPE_STR="EncryptionType";
				/**
				 * 配置设备wifi模块是否隐藏wifi
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_HIDDEN_NETWORK_BOOl="HIDDEN_NETWORK";
				/**
				 * 配置设备wifi模块是否是热点
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_ISHOTSPOT_BOOl="IsHotspot";
				/**
				 * 配置设备wifi模块wifi密码
				 * 例如：123456
				 */
				public static final String _KEY_PASSWORD_STR="Password";
				/**
				 * 配置设备wifi模块wifi代理模式
				 * 例如：None
				 */
				public static final String _KEY_PROXYTYPE_STR="ProxyType";
				/**
				 * 配置设备wifi模块wifi名称
				 * 例如：MDM
				 */
				public static final String _KEY_SSID_STR_STR="SSID_STR";
				
				/**
				 * 配置设备wifi模块安全类型
				 * 例如：0.无    1.WEP  2.WPA 
				 */
				public interface IEncryptionType {
					
					/**
					 * 配置设备wifi模块安全类型WEP
					 */
					public static final String _KEY_WEP_STR = "WEP";
					
					/**
					 * 配置设备wifi模块安全类型WPA
					 */
					public static final String _KEY_WPA_STR = "WPA";
				}
			}
			
			/**
			 * 配置WIFI值内容模块  字段格式为：_标签名称或缩写_标签内容（upper(匈牙利命名值)）_标签值类型 
			 * e.g.
			 * _KEY_PAYLOADDESCRIPTION_STR
			 * 表示是一个key标签，标签中内容是PayloadDescription,对于于key的值应该是string类型，所以结果是如下所示
			 * <key>PayloadDescription</key>
			 * <string>Configures wireless connectivity settings.</string>
			 * @author jane.hui
			 *
			 */
			public interface IWifiContent{
	
				/**
				 * 配置设备wifi模块描述
				 */
				public static final String _KEY_PAYLOADDESCRIPTIONCONTENT_STR = "Configures wireless connectivity settings.";
				/**
				 * 配置设备密码模块名称，例如密码
				 */
				public static final String _KEY_PAYLOADDISPLAYNAMECONTENT_STR="Wi-Fi (MDM)";
				
				/**
				 * 配置设备wifi模块唯一标识
				 */
				public static final String _KEY_PAYLOADIDENTIFIERCONTENT_STR="mdm.softtek.com";
				
				/**
				 * 配置设备wifi模块唯一标识
				 */
				public static final String _KEY_PAYLOADTYPE_STR="com.apple.wifi.managed";
				
				/**
				 * 配置设备wifi模块所属组织机构，应该和通用配置的PayloadOrganization一样
				 */
				public static final String _KEY_PAYLOADORGANIZATIONCONTENT_STR="softtek";
				
				/**
				 * 配置设备wifi模块唯一标识
				 */
				public static final String _KEY_PAYLOADUUIDCONTENT_STR="PayloadUUID";
				
				/**
				 * 配置设备wifi模块版本
				 */
				public static final String _KEY_PAYLOADVERSIONCONTENT_INT="1";
				
				/**
				 * 配置设备wifi模块wifi代理模式
				 */
				public static final String _KEY_PROXYTYPECONTENT_STR="None";
				
			}
			
			/**
			 * 配置设备密码模块  字段格式为：_标签名称或缩写_标签内容名称（upper(匈牙利命名值)）_标签值类型 
			 * e.g.
			 * _KEY_PAYLOADDESCRIPTION_STR
			 * 表示是一个key标签，标签中内容是PayloadDescription,对于于key的值应该是string类型，所以结果是如下所示
			 * <key>PayloadDescription</key>
			 * <string>配置与安全相关的项目</string>
			 * @author color.wu
			 *
			 */
			public interface IPassCode{
				 /* 配置设备密码模块描述
				 * 例如：配置与安全相关的项目
				 */
				public static final String _KEY_PAYLOADDESCRIPTION_STR = "PayloadDescription";
				/**
				 * 配置设备密码模块名称，例如密码
				 */
				public static final String _KEY_PAYLOADDISPLAYNAME_STR="PayloadDisplayName";
				/**
				 * 配置设备密码模块唯一标识
				 * 例如：com.apple.mobiledevice.passwordpolicy.6CAC7878-24A2-483D-9FED-3AE297A3EB3C
				 */
				public static final String _KEY_PAYLOADIDENTIFIER_STR="PayloadIdentifier";
				/**
				 * 配置设备密码模块所属组织机构，应该和通用配置的PayloadOrganization一样
				 * 例如：softtek
				 */
				public static final String _KEY_PAYLOADORGANIZATION_STR="PayloadOrganization";
				/**
				 * 配置设备密码模块文件类型
				 * 例如：com.apple.mobiledevice.passwordpolicy
				 */
				public static final String _KEY_PAYLOADTYPE_STR="PayloadType";
				/**
				 * 配置设备密码模块唯一标识
				 * 例如：6CAC7878-24A2-483D-9FED-3AE297A3EB3C
				 */
				public static final String _KEY_PAYLOADUUID_STR="PayloadUUID";
				/**
				 * 配置设备密码模块版本
				 * 例如：一般为1
				 */
				public static final String _KEY_PAYLOADVERSION_INT="PayloadVersion";
				/**
				 * 配置设备密码模块是否允许简单密码
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_ALLOWSIMPLE_BOOL="allowSimple";
				/**
				 * 配置设备密码是否要求包含字母和数字
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_FORCEPIN_BOOL="forcePIN";
				/**
				 * 配置设备密码模块最大失败尝试次数
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_MAXFAILEDATTEMPTS_INT="maxFailedAttempts";
				/**
				 * 配置设备密码模块设备锁定宽限时间（分钟）
				 * 例如：15
				 */
				public static final String _KEY_MAXGRACEPERIOD_INT="maxGracePeriod";
				/**
				 * 配置设备密码模块自动锁定时间（分钟）
				 * 例如：3
				 */
				public static final String _KEY_MAXINACTIVITY_INT="maxInactivity";
				/**
				 * 配置设备密码模块最长密码有效时间（天）
				 * 例如：2
				 */
				public static final String _KEY_MAXPINAGEINDAYS_INT="maxPINAgeInDays";
				/**
				 * 配置设备密码模块必须包含复杂字符最短数目
				 * 例如：2
				 */
				public static final String _KEY_MINCOMPLEXCHARS_INT="minComplexChars";
				/**
				 * 配置设备密码模块最短长度
				 * 例如：2
				 */
				public static final String _KEY_MINLENGTH_INT="minLength";
				/**
				 * 配置设备密码模块历史记录
				 * 例如：46
				 */
				public static final String _KEY_PINHISTORY_INT="pinHistory";
				/**
				 * 配置设备密码模块必须包含字符
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_PINHISTORY_BOOL="requireAlphanumeric";
			}
			
			/**
			 * 配置移动设备管理模块  字段格式为：_标签名称或缩写_标签内容名称（upper(匈牙利命名值)）_标签值类型 
			 * e.g.
			 * _KEY_PAYLOADDESCRIPTION_STR
			 * 表示是一个key标签，标签中内容是PayloadDescription,对于于key的值应该是string类型，所以结果是如下所示
			 * <key>PayloadDescription</key>
			 * <string>配置“移动设备管理”</string>
			 * @author color.wu
			 *
			 */
			public interface IDeviceManage{
				 /* 配置移动设备管理模块描述
				 * 例如：配置与安全相关的项目
				 */
				public static final String _KEY_PAYLOADDESCRIPTION_STR = "PayloadDescription";
				/**
				 * 配置移动设备管理模块名称，例如移动设备管理
				 */
				public static final String _KEY_PAYLOADDISPLAYNAME_STR="PayloadDisplayName";
				/**
				 * 配置移动设备管理模块唯一标识
				 * 例如：mdm.softtek.com.mdm
				 */
				public static final String _KEY_PAYLOADIDENTIFIER_STR="PayloadIdentifier";
				/**
				 * 配置移动设备管理模块所属组织机构，应该和通用配置的PayloadOrganization一样
				 * 例如：softtek
				 */
				public static final String _KEY_PAYLOADORGANIZATION_STR="PayloadOrganization";
				/**
				 * 配置移动设备管理模块文件类型
				 * 例如：com.apple.mdm
				 */
				public static final String _KEY_PAYLOADTYPE_STR="PayloadType";
				/**
				 * 配置移动设备管理模块唯一标识
				 * 例如：F4280005-D760-4A47-A4F1-E1F5CE71A414
				 */
				public static final String _KEY_PAYLOADUUID_STR="PayloadUUID";
				/**
				 * 配置移动设备管理模块版本
				 * 例如：一般为1
				 */
				public static final String _KEY_PAYLOADVERSION_INT="PayloadVersion";
				/**
				 * 配置移动设备管理模块是否允许访问入口
				 * 例如：8191
				 */
				public static final String _KEY_ACCESSRIGHTS_INT="AccessRights";
				/**
				 * 配置移动设备管理的CHECKINURL
				 * 例如：https://example.checkinurl
				 */
				public static final String _KEY_CHECKINURL_STR="CheckInURL";
				/**
				 * 配置移动设备管理模块是否移除时check
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_CHECKOUTWHENREMOVED_BOOL="CheckOutWhenRemoved";
				/**
				 * 配置移动设备管理模块ServerURL
				 * 例如：https://example.serverurl
				 */
				public static final String _KEY_MAXGRACEPERIOD_STR="ServerURL";
				/**
				 * 配置移动设备管理模块是否签名
				 * 例如：<true/> or <false/>
				 */
				public static final String _KEY_MAXINACTIVITY_BOOL="SignMessage";
				/**
				 * 配置移动设备管理模块用于管理信息的推送通知主题
				 * 例如：com.apple.mgmt.External.53850d79-edc8-4d79-9487-7a4ad0bec94a
				 */
				public static final String _KEY_MAXPINAGEINDAYS_STR="Topic";
			}
			
			/**
			 * 配置推送命令模块  字段格式为：_标签名称或缩写_标签内容名称（upper(匈牙利命名值)）_标签值类型 
			 * e.g.
			 * _KEY_PAYLOADDESCRIPTION_STR
			 * 表示是一个key标签，标签中内容是RequestType,对于于key的值应该是string类型，所以结果是如下所示
			 * <key>PayloadDescription</key>
			 * <string>DeviceLock</string>
			 * @author jane.hui
			 *
			 */
			public interface ICommand{

				/**
				 * 配置推送命令管理模块RequestType
				 */
				public static final String _KEY_REQUESTTYPE_STR = "RequestType";
				
				/**
				 * 配置推送命令管理模块Command
				 */
				public static final String _KEY_COMMAND_STR = "Command";
				
				/**
				 * 配置推送命令管理模块commandUUID
				 */
				public static final String _KEY_COMMANDUUID_STR = "CommandUUID";
				
				/**
				 * 清楚锁频密码
				 */
				public static final String ClearPasscode = "ClearPasscode";
				
				public static final String UnlockToken = "UnlockToken";
				
				public static final String InstalledApplicationList = "InstalledApplicationList";
			}
		}
	}

	/**
	 * iOS回调ServerUrl返回的状态
	 * @author color.wu
	 *
	 */
	public interface IIosInvokState{
		/**
		 * 一切ok
		 */
		public static final String ACKNOWLEDGED = "Acknowledged";
		/**
		 * 发送错误
		 */
		public static final String Error = "Error";
		/**
		 * 命令格式错误
		 */
		public static final String COMMANDFORMATERROR = "CommandFormatError";
		/**
		 * 设备空闲
		 */
		public static final String IDLE = "Idle";
		/**
		 * 设备以及收到指令，但是现在不能执行，将在以后执行
		 */
		public static final String NOTNOW = "NotNow";
		
		/**
		 * 应用已安装
		 */
		public static final String AppAlreadyInstalled = "AppAlreadyInstalled";

 		/**
 		 * 应用已加入队列
 		 */
		public static final String AppAlreadyQueued = "AppAlreadyQueued";
		
		/**
		 * 不支持安装应用
		 */
		public static final String NotSupported = "NotSupported";
		
		/**
		 * 不能核验应用id
		 */
		public static final String CouldNotVerifyAppID = "CouldNotVerifyAppID";
	
		/**
		 * Appstore被禁用
		 */
		public static final String AppStoreDisabled = "AppStoreDisabled";
		
		/**
		 * 不是一个应用
		 */
		public static final String NotAnApp = "NotAnApp";
		
		/**
		 * 购买方式不支持
		 */
		public static final String PurchaseMethodNotSupported = "";
	}
	
	/**
	 * 命令实现类
	 * @author jane.hui
	 */
	public interface IIosClassName {
		
		/**
		 * 设备策略推送指令实现类
		 */
		public static final String IosDeviceServiceImpl = "IosDeviceServiceImpl";
		
		/**
		 * 设备策略推送指令实现类
		 */
		public static final String IosDeviceInfoPlistServiceImpl = "IosDeviceInfoPlistServiceImpl";
		
		/**
		 * 设备管理推送实现类
		 */
		public static final String IosDeviceSettingServiceImpl = "IosDeviceSettingServiceImpl";
		
		public static final String IosDeviceAppListSaveServiceImpl = "IosDeviceAppListSaveServiceImpl";
		/**
		 * 
		 * 设备规则推送指令的服务类，位于service.ios包下
		 */
		public static final String IosDeviceRuleService="IosDeviceRuleService";
		
		/**
		 * 应用管理推送实现类(安装应用)
		 */
		public static final String IosApplicationServiceImpl = "IosApplicationServiceImpl";
		
		/**
		 * 应用管理推送实现类(移除应用)
		 */
		public static final String IosApplicationRemoveServiceImpl = "IosApplicationRemoveServiceImpl";
		
		/**
		 * 更新app
		 */
		public static final String IosPackageAppUpdateService="iosPackageAppUpdateService";
	}
	
	/**
	 * 修改元素节点位置
	 * @author jane.hui
	 */
	public interface IElementPosition{
		
		/**
		 * 允许安装元素位置
		 */
		public static final String AllowAppInstallation = "/plist/dict/array/dict/allowAppInstallation";
		
		/**
		 * 允许安装相机
		 */
		public static final String AllowCamera = "/plist/dict/array/dict/allowCamera";
		
		/**
		 * 是否允许屏幕截屏
		 */
		public static final String AllowScreenShot = "/plist/dict/array/dict/allowScreenShot";
		
		/**
		 * 是否允许使用YouTube
		 */
		public static final String AllowYouTube = "/plist/dict/array/dict/allowYouTube";
		
		/**
		 * 是否允许使用allowiTunes
		 */
		public static final String AllowiTunes = "/plist/dict/array/dict/allowiTunes";
		
		/**
		 * 是否允许使用Safari
		 */
		public static final String AllowSafari = "/plist/dict/array/dict/allowSafari";
		
		/**
		 * 是否允许备份
		 */
		public static final String AllowCloudBackup = "/plist/dict/array/dict/allowCloudBackup";
		
		/**
		 * 是否允许文档同步
		 */
		public static final String AllowCloudDocumentSync = "/plist/dict/array/dict/allowCloudDocumentSync";
		
		/**
		 * 是否允许照片流
		 */
		public static final String AllowPhotoStream = "/plist/dict/array/dict/allowPhotoStream";
		
		/**
		 * 允许受管控的应用打开不受管控的应用的文档
		 */
		public static final String AllowOpenFromManagedToUnmanaged = "/plist/dict/array/dict/allowOpenFromManagedToUnmanaged";
		
		/**
		 * 允许不受管控的应用打开受管控的应用的文档
		 */
		public static final String AllowOpenFromUnmanagedToManaged = "/plist/dict/array/dict/allowOpenFromUnmanagedToManaged";
		
		/**
		 * 是否允许添加游戏好友
		 */
		public static final String AllowAddingGameCenterFriends = "/plist/dict/array/dict/allowAddingGameCenterFriends";
		
		/**
		 * 允许iCloud照片图库
		 */
		public static final String AllowCloudPhotoLibrary = "/plist/dict/array/dict/allowCloudPhotoLibrary";
		
		/**
		 * 允许iCloud照片共享
		 */
		public static final String AllowSharedStream = "/plist/dict/array/dict/allowSharedStream";
		
		/**
		 * 允许siri
		 */
		public static final String AllowAssistant = "/plist/dict/array/dict/allowAssistant";
		
		/**
		 * 允许指纹解锁
		 */
		public static final String AllowFingerprintForUnlock = "/plist/dict/array/dict/allowFingerprintForUnlock";
		
		/**
		 * 允许使用handoff
		 */
		public static final String AllowActivityContinuation = "/plist/dict/array/dict/allowActivityContinuation";
		
		/**
		 * 允许锁屏时显示通知消息
		 */
		public static final String AllowLockScreenNotificationsView = "/plist/dict/array/dict/allowLockScreenNotificationsView";
		
		/**
		 * 允许锁屏时显示控制中心的消息
		 */
		public static final String AllowLockScreenControlCenter = "/plist/dict/array/dict/allowLockScreenControlCenter";
		
		/**
		 * 限制广告追踪
		 */
		public static final String SafariForceFraudWarning = "/plist/dict/array/dict/safariForceFraudWarning";
		
		/**
		 * 设备锁定时允许使用Siri
		 */
		public static final String AllowAssistantWhileLocked = "/plist/dict/array/dict/allowAssistantWhileLocked";
		
		/**
		 * 允许锁屏时显示TodayView的消息
		 */
		public static final String AllowLockScreenTodayView = "/plist/dict/array/dict/allowLockScreenTodayView";
		
		/**
		 * icloud同步钥匙串
		 */
		public static final String AllowCloudKeychainSync = "/plist/dict/array/dict/allowCloudKeychainSync";
		
		/**
		 * 允许在漫游时自动同步
		 */
		public static final String AllowGlobalBackgroundFetchWhenRoaming = "/plist/dict/array/dict/allowGlobalBackgroundFetchWhenRoaming";
		
		/**
		 * 允许多人游戏
		 */
		public static final String AllowMultiplayerGaming = "/plist/dict/array/dict/allowMultiplayerGaming";
		
		/**
		 * 允许语音拨号
		 */
		public static final String AllowVoiceDialing = "/plist/dict/array/dict/allowVoiceDialing";
		
		/**
		 * 用户在购买itunes商店商品时需要输入密码
		 */
		public static final String ForceITunesStorePasswordEntry = "/plist/dict/array/dict/forceITunesStorePasswordEntry";
		
		/**
		 *  允许被管理的应用将数据存储到iCloud
		 */
		public static final String AllowManagedAppsCloudSync = "/plist/dict/array/dict/allowManagedAppsCloudSync";
		
		/**
		 *  允许facetime
		 */
		public static final String AllowVideoConferencing = "/plist/dict/array/dict/allowVideoConferencing";
		
		/**
		 * 允许简单和数字
		 */
		public static final String allowSimple = "/plist/dict/array/dict/allowSimple";
		
		/**
		 * 要求字母和数字值
		 */
		public static final String ForcePIN = "/plist/dict/array/dict/forcePIN";
		
		/**
		 * 最小密码长度
		 */
		public static final String MinLength = "/plist/dict/array/dict/minLength";
		
		/**
		 * 配置设备密码模块最大失败尝试次数
		 */
		public static final String MaxFailedAttempts = "/plist/dict/array/dict/maxFailedAttempts";
		
		/**
		 * 配置设备密码模块最大失败尝试次数
		 */
		public static final String PinHistory = "/plist/dict/array/dict/pinHistory";
	}
	
	/**
	 * 命令类别
	 * @author jane.hui
	 *
	 */
	public interface ICommandType {
		
		/**
		 * 设备策略类别
		 */
		public static final String DevicePolicy = "devicePolicy";
		
		/**
		 * 设备管理
		 */
		public static final String DeviceManage = "deviceManage";
		/**
		 * 设备规则
		 */
		public static final String DeviceRule="deviceRule";
		
		/**
		 * 应用管理
		 */
		public static final String Application = "application";
		
		/**
		 * 应用管理(移除应用)
		 */
		public static final String RemoveApplicaiton = "removeApplicaiton";
		
		/**
		 * 获取应用列表
		 */
		public static final String AppList = "appList";
		/**
		 * app更新
		 */
		public static final String AppUpdate = "appUpdate";
	}
	
	/**
	 * 管理已安装应用类别
	 * @author jane.hui
	 *
	 */
	public interface IChangeManagementStateType {
		
		/**
		 * 应用受管理
		 */
		public static final String Managed = "Managed";
		
	}
	
	public interface IDeviceInfo{
		
		public static final String Queries = "Queries";
		
		public static final String DeviceInformation = "DeviceInformation";
		
		public static final String DeviceName = "DeviceName";
		
		public static final String SerialNumber = "SerialNumber";
		
		public static final String IMEI = "IMEI";
		
		public static final String OSVersion = "OSVersion";
		
		public static final String PhoneNumber = "PhoneNumber";
		 
		public static final String BatteryLevel = "BatteryLevel";
		
		public static final String DeviceCapacity = "DeviceCapacity";
		
		public static final String AvailableDeviceCapacity = "AvailableDeviceCapacity";
		
		public static final String ModelName = "ModelName";
		
		public static final String SIMCarrierNetwork = "SIMCarrierNetwork";
		
		public static final String CurrentCarrierNetwork = "CurrentCarrierNetwork";
		
		public static final String ICCID = "ICCID";
		
		public static final String WiFiMAC = "WiFiMAC";
		
		public static final String BluetoothMAC = "BluetoothMAC";
		
		public static final String VoiceRoamingEnabled = "VoiceRoamingEnabled";
		
		public static final String PersonalHotspotEnabled = "PersonalHotspotEnabled";
		
		public static final String DataRoamingEnabled = "DataRoamingEnabled";
		
		public static final String SubscriberCarrierNetwork = "SubscriberCarrierNetwork";
	}
	
	/**
	 * 指定RequestType
	 * @author color.wu
	 *
	 */
	public interface RequestType{
		/**
		 * ProfileList Commands Return a List of Installed Profiles
		 */
		public static final String PROFILELIST="ProfileList";
		/**
		 * InstallProfile Commands Install a Configuration Profile
		 */
		public static final String INSTALLPROFILE="InstallProfile";
		/**
		 * RemoveProfile Commands Remove a Profile From the Device
		 */
		public static final String REMOVEPROFILE="RemoveProfile";
		/**
		 * RemoveProfile Commands Remove a Profile From the Device
		 */
		public static final String IDENTIFIER="Identifier";
		/**
		 * CertificateList Commands Get a List of Installed Certificates
		 */
		public static final String CERTIFICATELIST="CertificateList";
		/**
		 * InstalledApplicationList Commands Get a List of Third-Party Applications
		 */
		public static final String INSTALLEDAPPLICATIONLIST="InstalledApplicationList";
		/**
		 * DeviceInformation Commands Get Information About the Device
		 */
		public static final String DEVICEINFORMATION="DeviceInformation";
		/**
		 * SecurityInfo Commands Request Security-Related Information
		 */
		public static final String SECURITYINFO="SecurityInfo";
		/**
		 * DeviceLock Command Locks the Device Immediately
		 */
		public static final String DEVICELOCK="DeviceLock";
		/**
		 * ClearPasscode Commands Clear the Passcode for a Device
		 */
		public static final String CLEARPASSCODE="ClearPasscode";
		/**
		 * EraseDevice Commands Remotely Erase a Device Upon receiving
		 */
		public static final String ERASEDEVICE="EraseDevice";
		/**
		 * Restrictions Commands Get a List of Installed Restrictions
		 */
		public static final String RESTRICTIONS="Restrictions";
		/**
		 * Clear Restrictions Password
		 */
		public static final String CLEARRETRICTIONSPASSWORD="ClearRestrictionsPassword";
		/**
		 * InstallApplication Commands Install a Third-Party Application
		 */
		public static final String INSTALLAPPLICATION="InstallApplication";
		/**
		 * RemoveApplication Commands Remove Installed Managed Applications The RemoveApplication
		 */
		public static final String REMOVEAPPLICATION="RemoveApplication";
		/**
		 * Managed Settings
		 */
		public static final String SETTINGS="Settings";
		/**
		 * VoiceRoaming Modifies the Voice Roaming Setting
		 */
		public static final String SETTINGS_VOICEROAMING="VoiceRoaming";
		/**
		 * PersonalHotspot Modifies the Personal Hotspot Setting
		 */
		public static final String SETTINGS_PERSONALHOTSPOT="PersonalHotspot";
		/**
		 * DataRoaming Modifies the Data Roaming Setting
		 */
		public static final String SETTINGS_DATAROAMING="DataRoaming";
	}
}