-- 2016-10-31
-- add c_user_balance table(C端用户账户余额表)
DROP TABLE IF EXISTS `c_user_balance`;
CREATE TABLE `c_user_balance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `c_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户外键',
  `account_balance` int(11) NOT NULL DEFAULT '0' COMMENT '账户余额',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- add b_user_balance table(B端用户帐户余额表)
DROP TABLE IF EXISTS `b_user_balance`;
CREATE TABLE `b_user_balance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `b_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户外键',
  `account_balance` int(11) NOT NULL DEFAULT '0' COMMENT '账户余额',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- add b_user_balance_record table(B端用户帐号余额操作记录表)
DROP TABLE IF EXISTS `b_user_balance_record`;
CREATE TABLE `b_user_balance_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `operate_type` char(1) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '操作类型(1.充值 2.支付购买 3.退款 4.提现 5.分佣)',
  `operate_content` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '操作内容',
  `b_user_balance_id` int(11) NOT NULL DEFAULT '0' COMMENT 'B端用户帐户余额外键',
  `tag` char(1) COLLATE utf8_unicode_ci NOT NULL COMMENT '标识[1.增加(+)   2.减去(-)]',
  `money` int(11) NOT NULL DEFAULT '0' COMMENT '金额',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- add b_user_balance_record table(C端用户帐号余额操作记录表)
DROP TABLE IF EXISTS `c_user_balance_record`;
CREATE TABLE `c_user_balance_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `operate_type` char(1) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '操作类型(1.充值 2.支付购买 3.退款 4.提现 5.分佣)',
  `operate_content` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '操作内容',
  `c_user_balance_id` int(11) NOT NULL DEFAULT '0' COMMENT 'C端用户帐户余额外键',
  `tag` char(1) COLLATE utf8_unicode_ci NOT NULL COMMENT '标识[1.增加(+)   2.减去(-)]',
  `money` int(11) NOT NULL DEFAULT '0' COMMENT '金额',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- add wallet_payment_record(钱包支付记录表)
DROP TABLE IF EXISTS `c_wallet_payment_record`;
CREATE TABLE `c_wallet_payment_record` (
  `id` int(11) NOT NULL,
  `out_trade_no` varchar(200) COLLATE utf8_unicode_ci NOT NULL COMMENT '商户订单号',
  `total_fee` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支付金额',
  `result` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '钱包支付结果(success:支付成功  failure:支付失败)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- add c_rechard_order(C端充值订单表)
DROP TABLE IF EXISTS `c_rechard_order`;
CREATE TABLE `c_rechard_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '充值订单号',
  `pay_type` varchar(2) COLLATE utf8_unicode_ci DEFAULT '0' COMMENT '支付方式(0.支付宝支付  1.微信支付)',
  `total_fee` int(11) DEFAULT '0' COMMENT '充值金额',
  `status` char(1) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '0.待付款 1.已付款 ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- add c_rechard_pay_record(C端充值支付宝回调记录表)
DROP TABLE IF EXISTS `c_rechard_pay_record`;
CREATE TABLE `c_rechard_pay_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `out_trade_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `body` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `buyer_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `seller_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `subject` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `total_amount` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `trade_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `trade_content` text COLLATE utf8_unicode_ci,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- add c_wx_rechard_pay_record(C端充值微信回调记录表)
DROP TABLE IF EXISTS `c_wx_rechard_pay_record`;
CREATE TABLE `c_wx_rechard_pay_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `mch_id` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `device_info` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `nonce_str` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sign` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `result_code` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `openid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `trade_type` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `total_fee` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `transaction_id` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `out_trade_no` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attach` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- modify f_sales_order_details (订单详情字段类型长度过短修改)

alter table f_sales_order_details MODIFY goods_name VARCHAR(250); 　　
alter table f_sales_order_details_bak_one MODIFY goods_name VARCHAR(250); 　　　

-- add b_notice(C端公告表)
DROP TABLE IF EXISTS `b_notice`;
CREATE TABLE `b_notice` (
  `id` int(11) NOT NULL,
  `notice_title` varchar(250) CHARACTER SET utf8 DEFAULT NULL COMMENT '公告',
  `detail` text COLLATE utf8_unicode_ci COMMENT '详情',
  `create_user` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` varchar(3) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '状态(0:失效的， 1:有效的)',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- b_sales_order table add catalog
alter table b_sales_order add catalog char(1) comment '0.水果外卖 1.传统流程';
alter table b_sales_order add freight int(11) comment '运费';
alter table b_sales_order add pay_time datetime comment '付款时间';
alter table b_sales_order_details add created_time datetime comment '创建时间';

-- add b_notice(B端wap图表)
DROP TABLE IF EXISTS `b_slider_waps`;
CREATE TABLE `b_slider_waps` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `wap_img_url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '图片地址',
  `create_user` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` varchar(3) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '状态(0:失效的， 1:有效的)',
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- add b_shop_freight(B端省运费表)
DROP TABLE IF EXISTS `b_shop_freight`;
CREATE TABLE `b_shop_freight` (
  ` id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `province_id` int(11) NOT NULL COMMENT '省外键',
  `shop_id` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '店铺外键',
  `freight` int(11) DEFAULT NULL COMMENT '运费',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (` id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
-- 添加 wap图 详情类型字段
alter table b_slider_waps add type char(2) comment '类型：0-商品详情；1-自定义信息；2-链接';
alter table b_slider_waps add content varchar(255) comment '公告内容';

-- 添加 类型表 广告图字段
alter table b_kind add pic_url varchar(255) comment '广告图';


-- modify 佣金比例修改为字符串
alter table b_goods MODIFY share_percent varchar(50) comment '佣金百分比';


-- 添加商品参数
alter table b_goods add parameter text comment '商品参数';

-- 添加物流公司代码和运单号
alter table b_sales_order add logistics_code varchar(255) comment '物流公司代码';
alter table b_sales_order add waybill_number varchar(100) comment '运单号';

-- 新增客户评价表
DROP TABLE IF EXISTS `c_comment`;
CREATE TABLE `c_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '评价人',
  `content` text COLLATE utf8_unicode_ci COMMENT '评价内容',
  `score` int(1) DEFAULT NULL COMMENT '评价分数',
  `img_url` text COLLATE utf8_unicode_ci COMMENT '图片路径',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `goods_no` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- 新增收藏商品表
DROP TABLE IF EXISTS `c_favorite_goods`;
CREATE TABLE `c_favorite_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(11) DEFAULT NULL,
  `goods_no` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品编号',
  `create_time` datetime DEFAULT NULL COMMENT '收藏商品时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- 新增收藏商店表
DROP TABLE IF EXISTS `c_favorite_shop`;
CREATE TABLE `c_favorite_shop` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(11) DEFAULT NULL,
  `shop_id` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商店ID',
  `create_time` datetime DEFAULT NULL COMMENT '收藏商品时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 2016/11/14 add by jane.hui
DROP TABLE IF EXISTS `c_logistics_code`;
CREATE TABLE `c_logistics_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '物流公司代码',
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '物流公司名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=274 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `c_logistics_code` VALUES ('1', 'AJ', '安捷快递');
INSERT INTO `c_logistics_code` VALUES ('2', 'ANE', '安能物流');
INSERT INTO `c_logistics_code` VALUES ('3', 'AXD', '安信达快递');
INSERT INTO `c_logistics_code` VALUES ('4', 'BQXHM', '北青小红帽');
INSERT INTO `c_logistics_code` VALUES ('5', 'BFDF', '百福东方');
INSERT INTO `c_logistics_code` VALUES ('6', 'BTWL', '百世快运');
INSERT INTO `c_logistics_code` VALUES ('7', 'CCES', 'CCES快递');
INSERT INTO `c_logistics_code` VALUES ('8', 'CITY100', '城市100');
INSERT INTO `c_logistics_code` VALUES ('9', 'COE', 'COE东方快递');
INSERT INTO `c_logistics_code` VALUES ('10', 'CSCY', '长沙创一');
INSERT INTO `c_logistics_code` VALUES ('11', 'CDSTKY', '成都善途速运');
INSERT INTO `c_logistics_code` VALUES ('12', 'DBL', '德邦');
INSERT INTO `c_logistics_code` VALUES ('13', 'DSWL', 'D速物流');
INSERT INTO `c_logistics_code` VALUES ('14', 'DTWL', '大田物流');
INSERT INTO `c_logistics_code` VALUES ('15', 'EMS', 'EMS');
INSERT INTO `c_logistics_code` VALUES ('16', 'FAST', '快捷速递');
INSERT INTO `c_logistics_code` VALUES ('17', 'FEDEX', 'FEDEX联邦(国内件）');
INSERT INTO `c_logistics_code` VALUES ('18', 'FEDEX_GJ', 'FEDEX联邦(国际件）');
INSERT INTO `c_logistics_code` VALUES ('19', 'FKD', '飞康达');
INSERT INTO `c_logistics_code` VALUES ('20', 'GDEMS', '广东邮政');
INSERT INTO `c_logistics_code` VALUES ('21', 'GSD', '共速达');
INSERT INTO `c_logistics_code` VALUES ('22', 'GTO', '国通快递');
INSERT INTO `c_logistics_code` VALUES ('23', 'GTSD', '高铁速递');
INSERT INTO `c_logistics_code` VALUES ('24', 'HFWL', '汇丰物流');
INSERT INTO `c_logistics_code` VALUES ('25', 'HHTT', '天天快递');
INSERT INTO `c_logistics_code` VALUES ('26', 'HLWL', '恒路物流');
INSERT INTO `c_logistics_code` VALUES ('27', 'HOAU', '天地华宇');
INSERT INTO `c_logistics_code` VALUES ('28', 'hq568', '华强物流');
INSERT INTO `c_logistics_code` VALUES ('29', 'HTKY', '百世快递');
INSERT INTO `c_logistics_code` VALUES ('30', 'HXLWL', '华夏龙物流');
INSERT INTO `c_logistics_code` VALUES ('31', 'HYLSD', '好来运快递');
INSERT INTO `c_logistics_code` VALUES ('32', 'JGSD', '京广速递');
INSERT INTO `c_logistics_code` VALUES ('33', 'JIUYE', '九曳供应链');
INSERT INTO `c_logistics_code` VALUES ('34', 'JJKY', '佳吉快运');
INSERT INTO `c_logistics_code` VALUES ('35', 'JLDT', '嘉里物流');
INSERT INTO `c_logistics_code` VALUES ('36', 'JTKD', '捷特快递');
INSERT INTO `c_logistics_code` VALUES ('37', 'JXD', '急先达');
INSERT INTO `c_logistics_code` VALUES ('38', 'JYKD', '晋越快递');
INSERT INTO `c_logistics_code` VALUES ('39', 'JYM', '加运美');
INSERT INTO `c_logistics_code` VALUES ('40', 'JYWL', '佳怡物流');
INSERT INTO `c_logistics_code` VALUES ('41', 'KYWL', '跨越物流');
INSERT INTO `c_logistics_code` VALUES ('42', 'LB', '龙邦快递');
INSERT INTO `c_logistics_code` VALUES ('43', 'LHT', '联昊通速递');
INSERT INTO `c_logistics_code` VALUES ('44', 'MHKD', '民航快递');
INSERT INTO `c_logistics_code` VALUES ('45', 'MLWL', '明亮物流');
INSERT INTO `c_logistics_code` VALUES ('46', 'NEDA', '能达速递');
INSERT INTO `c_logistics_code` VALUES ('47', 'PADTF', '平安达腾飞快递');
INSERT INTO `c_logistics_code` VALUES ('48', 'QCKD', '全晨快递');
INSERT INTO `c_logistics_code` VALUES ('49', 'QFKD', '全峰快递');
INSERT INTO `c_logistics_code` VALUES ('50', 'QRT', '全日通快递');
INSERT INTO `c_logistics_code` VALUES ('51', 'RFD', '如风达');
INSERT INTO `c_logistics_code` VALUES ('52', 'SAD', '赛澳递');
INSERT INTO `c_logistics_code` VALUES ('53', 'SAWL', '圣安物流');
INSERT INTO `c_logistics_code` VALUES ('54', 'SBWL', '盛邦物流');
INSERT INTO `c_logistics_code` VALUES ('55', 'SDWL', '上大物流');
INSERT INTO `c_logistics_code` VALUES ('56', 'SF', '顺丰快递');
INSERT INTO `c_logistics_code` VALUES ('57', 'SFWL', '盛丰物流');
INSERT INTO `c_logistics_code` VALUES ('58', 'SHWL', '盛辉物流');
INSERT INTO `c_logistics_code` VALUES ('59', 'ST', '速通物流');
INSERT INTO `c_logistics_code` VALUES ('60', 'STO', '申通快递');
INSERT INTO `c_logistics_code` VALUES ('61', 'STWL', '速腾快递');
INSERT INTO `c_logistics_code` VALUES ('62', 'SURE', '速尔快递');
INSERT INTO `c_logistics_code` VALUES ('63', 'TSSTO', '唐山申通');
INSERT INTO `c_logistics_code` VALUES ('64', 'UAPEX', '全一快递');
INSERT INTO `c_logistics_code` VALUES ('65', 'UC', '优速快递');
INSERT INTO `c_logistics_code` VALUES ('66', 'WJWL', '万家物流');
INSERT INTO `c_logistics_code` VALUES ('67', 'WXWL', '万象物流');
INSERT INTO `c_logistics_code` VALUES ('68', 'XBWL', '新邦物流');
INSERT INTO `c_logistics_code` VALUES ('69', 'XFEX', '信丰快递');
INSERT INTO `c_logistics_code` VALUES ('70', 'XYT', '希优特');
INSERT INTO `c_logistics_code` VALUES ('71', 'XJ', '新杰物流');
INSERT INTO `c_logistics_code` VALUES ('72', 'YADEX', '源安达快递');
INSERT INTO `c_logistics_code` VALUES ('73', 'YCWL', '远成物流');
INSERT INTO `c_logistics_code` VALUES ('74', 'YD', '韵达快递');
INSERT INTO `c_logistics_code` VALUES ('75', 'YDH', '义达国际物流');
INSERT INTO `c_logistics_code` VALUES ('76', 'YFEX', '越丰物流');
INSERT INTO `c_logistics_code` VALUES ('77', 'YFHEX', '原飞航物流');
INSERT INTO `c_logistics_code` VALUES ('78', 'YFSD', '亚风快递');
INSERT INTO `c_logistics_code` VALUES ('79', 'YTKD', '运通快递');
INSERT INTO `c_logistics_code` VALUES ('80', 'YTO', '圆通速递');
INSERT INTO `c_logistics_code` VALUES ('81', 'YXKD', '亿翔快递');
INSERT INTO `c_logistics_code` VALUES ('82', 'YZPY', '邮政平邮/小包');
INSERT INTO `c_logistics_code` VALUES ('83', 'ZENY', '增益快递');
INSERT INTO `c_logistics_code` VALUES ('84', 'ZHQKD', '汇强快递');
INSERT INTO `c_logistics_code` VALUES ('85', 'ZJS', '宅急送');
INSERT INTO `c_logistics_code` VALUES ('86', 'ZTE', '众通快递');
INSERT INTO `c_logistics_code` VALUES ('87', 'ZTKY', '中铁快运');
INSERT INTO `c_logistics_code` VALUES ('88', 'ZTO', '中通速递');
INSERT INTO `c_logistics_code` VALUES ('89', 'ZTWL', '中铁物流');
INSERT INTO `c_logistics_code` VALUES ('90', 'ZYWL', '中邮物流');
INSERT INTO `c_logistics_code` VALUES ('91', 'AMAZON', '亚马逊物流');
INSERT INTO `c_logistics_code` VALUES ('92', 'SUBIDA', '速必达物流');
INSERT INTO `c_logistics_code` VALUES ('93', 'RFEX', '瑞丰速递');
INSERT INTO `c_logistics_code` VALUES ('94', 'QUICK', '快客快递');
INSERT INTO `c_logistics_code` VALUES ('95', 'CJKD', '城际快递');
INSERT INTO `c_logistics_code` VALUES ('96', 'CNPEX', 'CNPEX中邮快递');
INSERT INTO `c_logistics_code` VALUES ('97', 'HOTSCM', '鸿桥供应链');
INSERT INTO `c_logistics_code` VALUES ('98', 'HPTEX', '海派通物流公司');
INSERT INTO `c_logistics_code` VALUES ('99', 'AYCA', '澳邮专线');
INSERT INTO `c_logistics_code` VALUES ('100', 'PANEX', '泛捷快递');
INSERT INTO `c_logistics_code` VALUES ('101', 'PCA', 'PCA Express');
INSERT INTO `c_logistics_code` VALUES ('102', 'UEQ', 'UEQ Express');
INSERT INTO `c_logistics_code` VALUES ('202', 'IMLGYZ', '摩洛哥邮政');
INSERT INTO `c_logistics_code` VALUES ('203', 'IMLQSYZ', '毛里求斯邮政');
INSERT INTO `c_logistics_code` VALUES ('204', 'IMLXYEMS', '马来西亚EMS');
INSERT INTO `c_logistics_code` VALUES ('205', 'IMLXYYZ', '马来西亚邮政');
INSERT INTO `c_logistics_code` VALUES ('206', 'IMQDYZ', '马其顿邮政');
INSERT INTO `c_logistics_code` VALUES ('207', 'IMTNKEMS', '马提尼克EMS');
INSERT INTO `c_logistics_code` VALUES ('208', 'IMTNKYZ', '马提尼克邮政');
INSERT INTO `c_logistics_code` VALUES ('209', 'IMXGYZ', '墨西哥邮政');
INSERT INTO `c_logistics_code` VALUES ('210', 'INFYZ', '南非邮政');
INSERT INTO `c_logistics_code` VALUES ('211', 'INRLYYZ', '尼日利亚邮政');
INSERT INTO `c_logistics_code` VALUES ('212', 'INWYZ', '挪威邮政');
INSERT INTO `c_logistics_code` VALUES ('213', 'IPTYYZ', '葡萄牙邮政');
INSERT INTO `c_logistics_code` VALUES ('214', 'IQQKD', '全球快递');
INSERT INTO `c_logistics_code` VALUES ('215', 'IQTWL', '全通物流');
INSERT INTO `c_logistics_code` VALUES ('216', 'ISDYZ', '苏丹邮政');
INSERT INTO `c_logistics_code` VALUES ('217', 'ISEWDYZ', '萨尔瓦多邮政');
INSERT INTO `c_logistics_code` VALUES ('218', 'ISEWYYZ', '塞尔维亚邮政');
INSERT INTO `c_logistics_code` VALUES ('219', 'ISLFKYZ', '斯洛伐克邮政');
INSERT INTO `c_logistics_code` VALUES ('220', 'ISLWNYYZ', '斯洛文尼亚邮政');
INSERT INTO `c_logistics_code` VALUES ('221', 'ISNJEYZ', '塞内加尔邮政');
INSERT INTO `c_logistics_code` VALUES ('222', 'ISPLSYZ', '塞浦路斯邮政');
INSERT INTO `c_logistics_code` VALUES ('223', 'ISTALBYZ', '沙特阿拉伯邮政');
INSERT INTO `c_logistics_code` VALUES ('224', 'ITEQYZ', '土耳其邮政');
INSERT INTO `c_logistics_code` VALUES ('225', 'ITGYZ', '泰国邮政');
INSERT INTO `c_logistics_code` VALUES ('226', 'ITLNDHDBGE', '特立尼达和多巴哥EMS');
INSERT INTO `c_logistics_code` VALUES ('227', 'ITNSYZ', '突尼斯邮政');
INSERT INTO `c_logistics_code` VALUES ('228', 'ITSNYYZ', '坦桑尼亚邮政');
INSERT INTO `c_logistics_code` VALUES ('229', 'IWDMLYZ', '危地马拉邮政');
INSERT INTO `c_logistics_code` VALUES ('230', 'IWGDYZ', '乌干达邮政');
INSERT INTO `c_logistics_code` VALUES ('231', 'IWKLEMS', '乌克兰EMS');
INSERT INTO `c_logistics_code` VALUES ('232', 'IWKLYZ', '乌克兰邮政');
INSERT INTO `c_logistics_code` VALUES ('233', 'IWLGYZ', '乌拉圭邮政');
INSERT INTO `c_logistics_code` VALUES ('234', 'IWLYZ', '文莱邮政');
INSERT INTO `c_logistics_code` VALUES ('235', 'IWZBKSTEMS', '乌兹别克斯坦EMS');
INSERT INTO `c_logistics_code` VALUES ('236', 'IWZBKSTYZ', '乌兹别克斯坦邮政');
INSERT INTO `c_logistics_code` VALUES ('237', 'IXBYYZ', '西班牙邮政');
INSERT INTO `c_logistics_code` VALUES ('238', 'IXFLWL', '小飞龙物流');
INSERT INTO `c_logistics_code` VALUES ('239', 'IXGLDNYYZ', '新喀里多尼亚邮政');
INSERT INTO `c_logistics_code` VALUES ('240', 'IXJPEMS', '新加坡EMS');
INSERT INTO `c_logistics_code` VALUES ('241', 'IXJPYZ', '新加坡邮政');
INSERT INTO `c_logistics_code` VALUES ('242', 'IXLYYZ', '叙利亚邮政');
INSERT INTO `c_logistics_code` VALUES ('243', 'IXLYZ', '希腊邮政');
INSERT INTO `c_logistics_code` VALUES ('244', 'IXPSJ', '夏浦世纪');
INSERT INTO `c_logistics_code` VALUES ('245', 'IXPWL', '夏浦物流');
INSERT INTO `c_logistics_code` VALUES ('246', 'IXXLYZ', '新西兰邮政');
INSERT INTO `c_logistics_code` VALUES ('247', 'IXYLYZ', '匈牙利邮政');
INSERT INTO `c_logistics_code` VALUES ('248', 'IYDLYZ', '意大利邮政');
INSERT INTO `c_logistics_code` VALUES ('249', 'IYDNXYYZ', '印度尼西亚邮政');
INSERT INTO `c_logistics_code` VALUES ('250', 'IYDYZ', '印度邮政');
INSERT INTO `c_logistics_code` VALUES ('251', 'IYGYZ', '英国邮政');
INSERT INTO `c_logistics_code` VALUES ('252', 'IYLYZ', '伊朗邮政');
INSERT INTO `c_logistics_code` VALUES ('253', 'IYMNYYZ', '亚美尼亚邮政');
INSERT INTO `c_logistics_code` VALUES ('254', 'IYMYZ', '也门邮政');
INSERT INTO `c_logistics_code` VALUES ('255', 'IYNYZ', '越南邮政');
INSERT INTO `c_logistics_code` VALUES ('256', 'IYSLYZ', '以色列邮政');
INSERT INTO `c_logistics_code` VALUES ('257', 'IYTG', '易通关');
INSERT INTO `c_logistics_code` VALUES ('258', 'IYWWL', '燕文物流');
INSERT INTO `c_logistics_code` VALUES ('259', 'IZBLTYZ', '直布罗陀邮政');
INSERT INTO `c_logistics_code` VALUES ('260', 'IZLYZ', '智利邮政');
INSERT INTO `c_logistics_code` VALUES ('261', 'JP', '日本邮政');
INSERT INTO `c_logistics_code` VALUES ('262', 'NL', '荷兰邮政');
INSERT INTO `c_logistics_code` VALUES ('263', 'ONTRAC', 'ONTRAC');
INSERT INTO `c_logistics_code` VALUES ('264', 'QQYZ', '全球邮政');
INSERT INTO `c_logistics_code` VALUES ('265', 'RDSE', '瑞典邮政');
INSERT INTO `c_logistics_code` VALUES ('266', 'SWCH', '瑞士邮政');
INSERT INTO `c_logistics_code` VALUES ('267', 'TAIWANYZ', '台湾邮政');
INSERT INTO `c_logistics_code` VALUES ('268', 'TNT', 'TNT快递');
INSERT INTO `c_logistics_code` VALUES ('269', 'UPS', 'UPS');
INSERT INTO `c_logistics_code` VALUES ('270', 'USPS', 'USPS美国邮政');
INSERT INTO `c_logistics_code` VALUES ('271', 'YAMA', '日本大和运输(Yamato)');
INSERT INTO `c_logistics_code` VALUES ('272', 'YODEL', 'YODEL');
INSERT INTO `c_logistics_code` VALUES ('273', 'YUEDANYOUZ', '约旦邮政');

-- 2016/11/16 add by jane.hui 
-- 用户积分表
DROP TABLE IF EXISTS `c_user_score`;
CREATE TABLE `c_user_score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `c_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户外键',
  `score` int(11) NOT NULL DEFAULT '0' COMMENT '积分',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 用户积分操作记录表
CREATE TABLE `c_user_score_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `operate_type` char(1) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '操作类型(1.充值 2.消费 3.取得积分)',
  `operate_content` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '操作内容',
  `c_user_score_id` int(11) NOT NULL DEFAULT '0' COMMENT 'C端用户帐户积分外键',
  `tag` char(1) COLLATE utf8_unicode_ci NOT NULL COMMENT '标识[1.增加(+)   2.减去(-)]',
  `score` int(11) NOT NULL DEFAULT '0' COMMENT '积分',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- add by jing.liu  add date 2016/11/17
 alter table b_sales_order add receive_order int(2); 
 
 alter table b_sales_order_details add goods_status int(2); 
 
 alter table b_sales_order add deliver_time datetime; 

 CREATE TABLE `return_pay` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_detail_id` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `receive_type` int(2) DEFAULT NULL COMMENT '收货状态  0- 退款  1-未收到货   2-已收到货',
  `return_type` int(2) DEFAULT NULL COMMENT '退款类型   1-退款  2-退货',
  `return_reason` text COLLATE utf8_unicode_ci COMMENT '退款原因',
  `return_amount` int(11) DEFAULT NULL COMMENT '退款金额',
  `return_remark` text COLLATE utf8_unicode_ci COMMENT '退款说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `return_pay_img` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `return_pay_id` int(11) DEFAULT NULL,
  `img_url` varchar(2000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '图片地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- B2C端订单详情表增加小计和佣金百分比字段
alter table b_sales_order_details add total_price int(11) comment '小计';
alter table b_sales_order_details add share_percent varchar(255) comment '佣金百分比';



-- ===================================================================================2016/11/25=====================================================================================================================
-- add by jing.liu add date 2016/11/21
alter table b_sales_order add receive_time datetime COMMENT '收货时间';

alter table b_sales_order add cancel_time datetime COMMENT '取消订单时间';

alter table b_sales_order add return_status datetime COMMENT '退款状态';

alter table b_sales_order add return_time datetime COMMENT '退款时间';

alter table b_sales_order add real_pay Integer COMMENT '真是金额';

alter table return_pay add check_time datetime COMMENT '审核时间';

alter table return_pay add created_time datetime COMMENT '创建时间';

alter table return_pay add updated_time datetime COMMENT '修改时间';

alter table f_sales_order add pay_time datetime COMMENT '支付时间';

alter table f_sales_order add deliver_time datetime COMMENT '发货时间';

alter table f_sales_order add receiver_time datetime COMMENT '收货时间';

alter table f_order_diffence add created_time datetime COMMENT '创建时间';

alter table f_order_diffence add updated_time datetime COMMENT '修改时间';

-- add by mozzie.chu 2016.11.23 >>>>>>>>>>>>f_help
CREATE  TABLE `f_help` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `question` VARCHAR(200) NOT NULL COMMENT '问题' ,
  `answer` TEXT NOT NULL COMMENT '回答' ,
  `type` VARCHAR(45) NULL COMMENT 'type(一级内容)\n 0:购买咨询 | 1:支付问题 | 2:物流与售后 | 3:其他问题 | 4:问答详情' ,
  `status` VARCHAR(45) NULL COMMENT 'status(二级内容)\n0-0:购买咨询，0-1:购买相关问题 |\n1-0:支付问题，1-1:支付相关，1-2:积分卡券使用，1-3:支付异常 |\n2-0:物流与售后，2-1:物流配送，2-2:售后咨询 |\n 3-0:其它问题，3-1:帐号与密码 |\n 4-0:问答详情 |' ,
  `create_time` DATETIME NULL COMMENT '创建时间' ,
  `update_time` DATETIME NULL COMMENT '修改时间' ,
  `pic` VARCHAR(200) NULL COMMENT '图片路径' ,
  `style` VARCHAR(45) NULL COMMENT '是否热点问题\n 0:否，1:是' ,
  PRIMARY KEY (`id`) )
DEFAULT CHARACTER SET = hp8
COMMENT = '帮助中心';

-- add by mozzie.chu 2016.11.23 >>>>>>>>>>>>f_menu
INSERT INTO `f_menu` (`id`, `menu_name`, `remark`, `icon`, `weight`, `type`) VALUES ('1000000198', '帮助管理', '帮助管理', '/resources/platform/img/icon/auth_code_manage.png', '116', '1');
INSERT INTO `f_menu` (`id`, `menu_name`, `remark`, `parent_id`, `href`, `weight`, `type`) VALUES ('1000000199', '帮助中心', '帮助中心', '1000000198', '/farm/bhelp/helpcenter', '117', '1');
INSERT INTO `f_menu` (`id`, `menu_name`, `remark`, `parent_id`, `href`, `weight`, `type`) VALUES ('1000000200', '购物咨询', '购物咨询', '1000000198', '/farm/bhelp/shoppingadvice', '118', '1');
INSERT INTO `f_menu` (`id`, `menu_name`, `remark`, `parent_id`, `href`, `weight`, `type`) VALUES ('1000000201', '支付问题', '支付问题', '1000000198', '/farm/bhelp/paymentproblem', '119', '1');
INSERT INTO `f_menu` (`id`, `menu_name`, `remark`, `parent_id`, `href`, `weight`, `type`) VALUES ('1000000202', '物流与售后', '物流与售后', '1000000198', '/farm/bhelp/logisticsandcustomer', '120', '1');
INSERT INTO `f_menu` (`id`, `menu_name`, `remark`, `parent_id`, `href`, `weight`, `type`) VALUES ('1000000203', '其他问题', '其他问题', '1000000198', '/farm/bhelp/otherproblem', '121', '1');
INSERT INTO `f_menu` (`id`, `menu_name`, `remark`, `parent_id`, `href`, `weight`, `type`) VALUES ('1000000204', '问答详情', '问答详情', '1000000198', '/farm/bhelp/qanda', '122', '1');
INSERT INTO `f_menu` (`id`, `remark`, `parent_id`, `href`, `weight`, `type`) VALUES ('1000000207', '帮助中心', '1000000199', '/farm/bhelp/querypage', '124', '2');
INSERT INTO `f_menu` (`id`, `remark`, `parent_id`, `href`, `weight`, `type`) VALUES ('1000000210', '购物咨询', '1000000200', '/farm/bhelp/insert', '128', '2');


-- add by mozzie.chu 2016.11.23 >>>>>>>>>>>>f_role_to_menu
INSERT INTO `f_role_to_menu` (`id`, `role_id`, `menu_id`) VALUES ('346', '1', '1000000198');
INSERT INTO `f_role_to_menu` (`id`, `role_id`, `menu_id`) VALUES ('347', '1', '1000000199');
INSERT INTO `f_role_to_menu` (`id`, `role_id`, `menu_id`) VALUES ('348', '1', '1000000200');
INSERT INTO `f_role_to_menu` (`id`, `role_id`, `menu_id`) VALUES ('349', '1', '1000000201');
INSERT INTO `f_role_to_menu` (`id`, `role_id`, `menu_id`) VALUES ('350', '1', '1000000202');
INSERT INTO `f_role_to_menu` (`id`, `role_id`, `menu_id`) VALUES ('351', '1', '1000000203');
INSERT INTO `f_role_to_menu` (`id`, `role_id`, `menu_id`) VALUES ('352', '1', '1000000204');
INSERT INTO `f_role_to_menu` (`id`, `role_id`, `menu_id`) VALUES ('354', '1', '1000000207');
INSERT INTO `f_role_to_menu` (`id`, `role_id`, `menu_id`) VALUES ('358', '1', '1000000210');

-- update by mozzie.chu 2016.11.24 >>>>>>>>>>>>f_help
ALTER TABLE `f_help` CHARACTER SET = utf8 ;
ALTER TABLE `f_help` CHANGE COLUMN `question` `question` VARCHAR(200) CHARACTER SET 'utf8' NOT NULL COMMENweightT '问题'  , CHANGE COLUMN `answer` `answer` TEXT CHARACTER SET 'utf8' NOT NULL COMMENT '回答'  , CHANGE COLUMN `type` `type` VARCHAR(45) CHARACTER SET 'utf8' NULL COMMENT 'type(一级内容)\n 0:购买咨询 | 1:支付问题 | 2:物流与售后 | 3:其他问题 | 4:问答详情'  , CHANGE COLUMN `status` `status` VARCHAR(45) CHARACTER SET 'utf8' NULL DEFAULT NULL COMMENT 'status(二级内容)\n0-0:购买咨询，0-1:购买相关问题 |\n1-0:支付问题，1-1:支付相关，1-2:积分卡券使用，1-3:支付异常 |\n2-0:物流与售后，2-1:物流配送，2-2:售后咨询 |\n 3-0:其它问题，3-1:帐号与密码 |\n 4-0:问答详情 |'  , CHANGE COLUMN `pic` `pic` VARCHAR(200) CHARACTER SET 'utf8' NULL DEFAULT NULL COMMENT '图片路径'  , CHANGE COLUMN `style` `style` VARCHAR(45) CHARACTER SET 'utf8' NULL DEFAULT NULL COMMENT '是否热点问题\n 0:否，1:是'  ;

-- 充值订单表增加create_time和user_id by jane.hui  2016/11/25
alter table c_rechard_order add create_time datetime comment '创建时间';
alter table c_rechard_order add user_id int(11) comment '用户外键';

-- =================================================================================================== 2016/12/03 version new ==================================================================================================================================================================================================================================================================================================================================================================
-- add by jing.liu //   add date 2016/11/28
INSERT INTO f_menu (id, remark, parent_id, href, weight, type) VALUES ('1000000211','删除订单',1000000071,'/farm/order/deleteOrder', '129', '2');
INSERT INTO f_role_to_menu (role_id, menu_id) VALUES ('1', '1000000211');

-- F端商品表增加权重标识(weight) by jane.hui 2016/11/28
alter table f_goods add weight int(11) comment '权重';


-- add by mozzie.chu 2016.11.29 >>>>>>>>>>>>f_menu
INSERT INTO `f_menu` (`id`, `remark`, `parent_id`, `href`, `weight`, `type`) VALUES ('1000000213', '帮助删除', '1000000199', '/farm/bhelp/delete', '131', '2');
INSERT INTO `f_menu` (`id`, `remark`, `parent_id`, `href`, `weight`, `type`) VALUES ('1000000214', '帮助修改', '1000000199', '/farm/bhelp/update', '132', '2');
INSERT INTO `f_menu` (`id`, `remark`, `parent_id`, `href`, `weight`, `type`) VALUES ('1000000216', '帮助查看', '1000000199', '/farm/bhelp/show', '134', '2');
-- add by mozzie.chu 2016.11.29 >>>>>>>>>>>>f_role_to_menu
INSERT INTO `f_role_to_menu` (`id`, `role_id`, `menu_id`) VALUES ('360', '1', '1000000213');
INSERT INTO `f_role_to_menu` (`id`, `role_id`, `menu_id`) VALUES ('361', '1', '1000000214');
INSERT INTO `f_role_to_menu` (`id`, `role_id`, `menu_id`) VALUES ('364', '1', '1000000216');
-- update by mozzie.chu 2016.11.29 >>>>>>>>>>>>f_help
DELETE FROM `f_menu` WHERE `id`='1000000204';


-- 新增商品评价(更改大量字段所以重新刷新脚本) by josen.yang 2016/11/29
DROP TABLE IF EXISTS `c_comment`;
CREATE TABLE `c_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '评价人',
  `content` text COLLATE utf8_unicode_ci COMMENT '评价内容',
  `oder_details_id` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '订单详情Id',
  `goods_no` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品编号',
  `service_score` int(1) DEFAULT NULL COMMENT '服务分数',
  `speed_score` int(1) DEFAULT NULL COMMENT '速度分数',
  `quality_score` int(1) DEFAULT NULL COMMENT '质量分数',
  `synthetical_score` int(1) DEFAULT NULL COMMENT '综合分数',
  `img_url` text COLLATE utf8_unicode_ci COMMENT '图片路径',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 评价标识 by josen.yang 2016/11/28
alter table b_sales_order_details add is_comment int(1) comment '是否评价';
-- add by jing.liu  add date 2016/11/29
alter table b_sales_order add receive_order_time datetime comment '接单时间';

-- add f端wap图字段增加  by josen.yang 2016/11/29
alter table f_slider_waps add type char comment '类型：0-商品详情；1-自定义信息；2-链接';
alter table f_slider_waps add content text comment '内容';



-- add by josen.yang 2016.11.29 >>>>>>>>>>>>f_menu
INSERT INTO `f_menu` (`id`, `remark`, `parent_id`, `href`, `weight`, `type`) VALUES ('1000000218', 'F端wap图新增修改', '1000000082', '/farm/home/wap/addpage', '136', '2');
INSERT INTO `f_menu` (`id`, `remark`, `parent_id`, `href`, `weight`, `type`) VALUES ('1000000219', '发布F端轮播图校验商品编号', '1000000082', '/farm/home/wap/bgoods-no-istrue', '137', '2');
-- add by josen.yang 2016.11.29 >>>>>>>>>>>>f_role_to_menu
INSERT INTO `f_role_to_menu` (`id`, `role_id`, `menu_id`) VALUES ('366', '1', '1000000218');
INSERT INTO `f_role_to_menu` (`id`, `role_id`, `menu_id`) VALUES ('367', '1', '1000000219');

-- add by mozzie.chu 2016.11.30 >>>>>>>>>>>>f_menu
INSERT INTO `f_menu` (`id`, `remark`, `parent_id`, `href`, `weight`, `type`) VALUES ('1000000217', '帮助修改页', '1000000199', '/farm/bhelp/show-update', '135', '2');
-- add by mozzie.chu 2016.11.30 >>>>>>>>>>>>f_role_to_menu
INSERT INTO `f_role_to_menu` (`id`, `role_id`, `menu_id`) VALUES ('365', '1', '1000000217');
-- update by mozzie.chu 2016.11.30 >>>>>>>>>>>>f_menu
UPDATE `f_menu` SET `parent_id`='1000000217' WHERE `id`='1000000214';

-- add by jackson.zhu 2016.12.01>>>>>>>>>>>>>f_menu
INSERT INTO `f_menu` VALUES ('1000000205', '通知设置', '通知设置', null, null, '/resources/platform/img/icon/system_manage.png', '123', '1');
INSERT INTO `f_menu` VALUES ('1000000206', '常见问题列表', '常见问题列表', '1000000205', '/farm/problem/index', null, '124', '1');

INSERT INTO `f_menu` VALUES ('1000000208', null, '常见问题列表', '1000000206', '/farm/problem/page', null, '126', '2');
INSERT INTO `f_menu` VALUES ('1000000209', null, '常见问题删除', '1000000206', '/farm/problem/delete', null, '127', '2');

INSERT INTO `f_menu` VALUES ('1000000212', null, '常见问题查看', '1000000206', '/farm/problem/show', null, '130', '2');

INSERT INTO `f_menu` VALUES ('1000000215', null, '常见问题修改', '1000000206', '/farm/problem/update', null, '133', '2');

INSERT INTO `f_menu` VALUES ('1000000220', null, '常见问题添加', '1000000206', '/farm/problem/add', null, '129', '2');

-- add by jackson.zhu 2016.12.01>>>>>>>>>>>>>f_role_to_menu
INSERT INTO `f_role_to_menu` VALUES ('353', '1', '1000000206');

INSERT INTO `f_role_to_menu` VALUES ('355', '1', '1000000205');

INSERT INTO `f_role_to_menu` VALUES ('356', '1', '1000000208');
INSERT INTO `f_role_to_menu` VALUES ('357', '1', '1000000209');

INSERT INTO `f_role_to_menu` VALUES ('369', '1', '1000000215');
INSERT INTO `f_role_to_menu` VALUES ('363', '1', '1000000212');

INSERT INTO `f_role_to_menu` VALUES ('368', '1', '1000000220');

-- b_sales_order_details表增加字段is_commission，标识该笔订单是否参与分佣  by jane.hui 2016/12/06 start 
alter table b_sales_order_details add is_commission char(1) comment '是否参与佣金 0:不参与 1：参与';
-- b_goods表增加字段is_commission，标识该笔订单是否参与分佣  by jane.hui 2016/12/06 
alter table b_goods add is_commission char(1) comment '是否参与佣金 0:不参与 1：参与';
-- 将所有水果分类的商品设置为不参与佣金计算
update b_goods T1 set T1.is_commission = 0 where T1.kind_id IN (select T2.id from b_kind T2 where T2.catalog = 0);
-- 将所有不是水果分类的商品设置为参与佣金计算
update b_goods T1 set T1.is_commission = 1 where T1.kind_id IN (select T2.id from b_kind T2 where T2.catalog = 1);
-- 查询设置水果数据是否正确
SELECT T1.id,T1.is_commission,T2.catalog FROM b_goods T1 LEFT JOIN b_kind T2 ON T1.kind_id = T2.id WHERE T2.catalog = 0;
SELECT T1.id,T1.is_commission,T2.catalog FROM b_goods T1 LEFT JOIN b_kind T2 ON T1.kind_id = T2.id WHERE T2.catalog = 1;
-- b_sales_order_details表的is_commission增加数据
update b_sales_order_details T1 set T1.is_commission = 1 where T1.goods_id in (SELECT T2.id FROM b_goods T2 where T2.is_commission = 1);
update b_sales_order_details T1 set T1.is_commission = 0 where T1.goods_id in (SELECT T2.id FROM b_goods T2 where T2.is_commission = 0);
SELECT T1.id,T1.is_commission,T2.is_commission FROM b_sales_order_details T1 LEFT JOIN b_goods T2 ON T1.goods_id = T2.id WHERE T2.is_commission = 1;
SELECT T1.id,T1.is_commission,T2.is_commission FROM b_sales_order_details T1 LEFT JOIN b_goods T2 ON T1.goods_id = T2.id WHERE T2.is_commission = 0;
-- b_sales_order_details表增加字段is_commission，标识该笔订单是否参与分佣  by jane.hui 2016/12/06 end 

-- 增加F2B端充值订单表 by jane.hui 2016/11/16  
CREATE TABLE `b_rechard_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '充值订单号',
  `pay_type` varchar(2) COLLATE utf8_unicode_ci DEFAULT '0' COMMENT '支付方式(0.支付宝支付  1.微信支付)',
  `total_fee` int(11) DEFAULT '0' COMMENT '充值金额',
  `status` char(1) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '0.待付款 1.已付款 ',
  `create_time` datetime(1) DEFAULT NULL COMMENT '创建时间',
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=291 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 增加F2B端充值支付宝支付记录表  by jane.hui 2016/11/16
CREATE TABLE `b_rechard_pay_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `out_trade_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `body` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `buyer_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `seller_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `subject` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `total_amount` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `trade_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `trade_content` text COLLATE utf8_unicode_ci,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci; 

-- 增加F2B端微信充值支付记录表 by jane.hui 2016/11/16
CREATE TABLE `b_wx_rechard_pay_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `mch_id` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `device_info` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `nonce_str` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sign` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `result_code` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `openid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `trade_type` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `total_fee` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `transaction_id` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `out_trade_no` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attach` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- cuser表增加pay_password字段 by jane.hui 2016/12/15
alter table c_user add pay_password varchar(255) comment '支付密码';

-- c_user_balance 表增加commission字段以及freeze_commission by jane.hui 2016/12/15
alter table c_user_balance add commission int(11) comment '可用佣金' default 0;
alter table c_user_balance add total_commission int(11) comment '全部佣金(未计算税)' default 0;

-- b_user_balance 表增加commission字段以及freeze_commission by jane.hui 2016/12/15
alter table b_user_balance add commission int(11) comment '可用佣金' default 0;
alter table b_user_balance add total_commission int(11) comment '全部佣金(未计算税)' default 0;

-- c_user_balance_record表添加is_calculated字段 by jane.hui 2016/12/22 
alter table c_user_balance_record add is_calculated char(1) comment '是否被计算佣金过(1.已计算过 0.未计算过)' default 0;

-- b_user_balance_record表添加is_calculated字段 by jane.hui 2016/12/22
alter table b_user_balance_record add is_calculated char(1) comment '是否被计算佣金过(1.已计算过 0.未计算过)' default 0;

-- f_goods 表增加weight字段以及set_weight_time by josen.yang 2016/12/28
alter table f_goods add set_weight_time datetime comment '设置权重时间' default NULL;
alter table b_user_balance add weight int(11) comment '权重' default 0;

-- f_kind 表增加set_weight_time by josen.yang 2016/12/28
alter table f_kind add set_weight_time datetime comment '设置权重时间' default NULL;

-- add by josen.yang 2016.12.28>>>>>>>>>>>>>f_role_to_menu
INSERT INTO `f_menu` VALUES ('1000000222', null, '修改F端分类权值', '1000000011', '/farm/admin/kind/update-fkind-weight', null, '139', '2');
INSERT INTO `f_role_to_menu` VALUES ('371', '1', '1000000222');
insert into f_role_to_menu(role_id,menu_id)values(6,1000000221);
insert into f_role_to_menu(role_id,menu_id)values(6,1000000222);