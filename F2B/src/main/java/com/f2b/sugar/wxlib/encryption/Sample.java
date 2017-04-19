package com.f2b.sugar.wxlib.encryption;

//
//import java.io.StringReader;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.NodeList;
//import org.xml.sax.InputSource;

public class Sample {

	public static void main(String[] args) throws Exception {
		String sToken = "ILvQNEun9rjWw";
		String sCorpID = "wxac9f1336104f190a";
		String sEncodingAESKey = "nKl27SJOgJhG9gpISTqEdSKIh4XopOE6KvCXS8xMgnP";

		WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey, sCorpID);
		/*
		 * 假定公众平台上开发者设置的Token 1. 验证回调URL 点击验证时，企业收到类似请求： GET /cgi-bin/wxpush?msg_signature =5c45ff5e21c57e6ad56bac8758b79b1d9ac89fd3 &timestamp=1409659589&nonce=263014780
		 * &echostr=P9nAzCzyDtyTWESHep1vC5X9xho %2FqYX3Zpb4yKa9SKld1DsH3Iyt3tP3zNdtp %2B4RPcs8TgAE7OaBO%2BFZXvnaqQ%3D%3D HTTP/1.1 Host: qy.weixin.qq.com 接收到该请求时，企业应1.先验证签名的正确性 2. 解密出echostr原文。
		 * 以上两步用VerifyURL完成
		 */
		// 解析出url上的参数值如下：
		String sVerifyMsgSig = "246c1a700887d9b1b8a737d6f71e82d55ff06c69";
		String sVerifyTimeStamp = "1414966261";
		String sVerifyNonce = "1819635893";
		String sVerifyEchoStr = "ZfG9HwZpQd2MnoqXFIFagK50tswC2HDb08rSK6NxbObh/R3IKzmefFr9QBlhkHQuMT3cximUEA62J2XEUPeLDA==";
		String sEchoStr = wxcpt.VerifyURL(sVerifyMsgSig, sVerifyTimeStamp, sVerifyNonce, sVerifyEchoStr);
		System.out.println("verifyurl echostr: " + sEchoStr);

		/*
		 * 2. 对用户回复的数据进行解密。 用户回复消息或者点击事件响应时，企业会收到回调消息，假设企业收到的推送消息： POST /cgi-bin/wxpush? msg_signature=477715d11cdb4164915debcba66cb864d751f3e6 &timestamp=1409659813&nonce=1372623149 HTTP/1.1 Host:
		 * qy.weixin.qq.com Content-Length: 613
		 * 
		 * <xml> <ToUserName><![CDATA[wx5823bf96d3bd56c7]]></ToUserName> <Encrypt><![CDATA[RypEvHKD8QQKFhvQ6QleEB4J58tiPdvo+rtK1I9qca6aM/ wvqnLSV5zEPeusUiX5L5X/0lWfrf0QADHHhGd3QczcdCUpj911L3vg3W/
		 * sYYvuJTs3TUUkSUXxaccAS0qhxchrRYt66wiSpGLYL42aM6A8dTT +6k4aSknmPj48kzJs8qLjvd4Xgpue06DOdnLxAUHzM6 +kDZ+HMZfJYuR+LtwGc2hgf5gsijff0ekUNXZiqATP7PF5mZxZ3Izoun1s4zG4LUMnvw2r +KqCKIw+3IQH03v+
		 * BCA9nMELNqbSf6tiWSrXJB3LAVGUcallcrw8V2t9EL4EhzJWrQUax5wLVMNS0 +rUPA3k22Ncx4XXZS9o0MBH27Bo6BpNelZpS +/uh9KsNlY6bHCmJU9p8g7m3fVKn28H3KDYA5Pl
		 * /T8Z1ptDAVe0lXdQ2YoyyH2uyPIGHBZZIs2pDBS8R07+qN+E7Q==]]></Encrypt> <AgentID><![CDATA[218]]></AgentID> </xml>
		 */
		String sReqMsgSig = "7a94e6b04dca8984ce07c5e357c0820ead6501a4";
		String sReqTimeStamp = "1415468479";
		String sReqNonce = "847980115";
		String sReqData = "<xml>" +
				"<ToUserName><![CDATA[wxac9f1336104f190a]]></ToUserName>" +
				"<Encrypt><![CDATA[+VC2GMuAmnI3yD8OP4ZgcVstvblCPv5AdwXWxtCkHsFSu0gdzsFUPzP6j3sSDbcB+zOxeAHm2Xl/J2BHOHbeeUpXJQYKnsv316UHF+4oPCPhC4oqR39KRh0CgaH6beBvv5qNVXgBlc/d6JwKRNVHF0F43HP4w3vAnBbRg0XAIbNmhpRvBG0KmzSpwUcOI66TcjYoyH5P2Nh2zzv7oA7fgcty4GzkGBR1d9+X3H2jE4r4qC41Ki3kOoaScGDcS4Xp9ip0UGJ42lirF4/4zB3QjOmzcb8dpgu4WO3soMsl1dv84iVHPxAxy8tyuQAzhXGiKkDmDEUtqW4BZT5n1Jm6KOrZCjKihGn1pYlQBp+/jB0lcGMLInxtG6xj3WvLZfcbJRUVJ1ATHiXcfXb6C2XmIOoDbtA5g9PgcXyo1YIAM5Y=]]></Encrypt>" +
				"<AgentID><![CDATA[1]]></AgentID>" +
				"</xml>";

		String sMsg = wxcpt.DecryptMsg(sReqMsgSig, sReqTimeStamp, sReqNonce, sReqData);
		System.out.println("after decrypt msg: " + sMsg);

		/*
		 * 3. 企业回复用户消息也需要加密和拼接xml字符串。 假设企业需要回复用户的消息为： <xml> <ToUserName><![CDATA[mycreate]]></ToUserName> <FromUserName><![CDATA[wx5823bf96d3bd56c7]]></FromUserName>
		 * <CreateTime>1348831860</CreateTime> <MsgType><![CDATA[text]]></MsgType> <Content><![CDATA[this is a test]]></Content> <MsgId>1234567890123456</MsgId> <AgentID>128</AgentID> </xml>
		 * 生成xml格式的加密消息过程为：
		 */
		String sRespData = "<xml><ToUserName><![CDATA[mycreate]]></ToUserName><FromUserName><![CDATA[wx5823bf96d3bd56c7]]></FromUserName><CreateTime>1348831860</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[this is a test]]></Content><MsgId>1234567890123456</MsgId><AgentID>128</AgentID></xml>";

		String sEncryptMsg = wxcpt.EncryptMsg(sRespData, sReqTimeStamp, sReqNonce);
		System.out.println("after encrypt sEncrytMsg: " + sEncryptMsg);

	}
}
