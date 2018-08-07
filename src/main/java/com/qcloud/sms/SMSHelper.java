package com.qcloud.sms;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class SMSHelper {
	
	private static final String SMS_NATION_CODE_CN="86";//国家码：中国
	private static int SMS_APP_ID=0;//腾讯云短信应用id
	private static String SMS_APP_KEY=null;//腾讯云短信应用key
	private static SmsSingleSender smsSingleSender = null;//单发短信
	private static SmsMultiSender smsMultiSender = null;//多发短信
	
	public SMSHelper(String configFileName){
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFileName);
		Properties properties = new Properties();
		try{
			properties.load(in);
			SMS_APP_ID = Integer.valueOf(properties.getProperty("sms_app_id"));
			SMS_APP_KEY = properties.getProperty("sms_app_key");
		}catch(Exception e){
			
		}
	}
	
	/**
	 * 单发短信
	 * @param type 短信类型  0:普通;1营销:
	 * @param nationCode 国家码
	 * @param phoneNumber 电话号码
	 * @param msg 短信内容
	 * @return
	 */
	public SmsSingleSenderResult sms4Single(int type,String nationCode,String phoneNumber,String msg) throws Exception{
		if(null==smsSingleSender){
			smsSingleSender = new SmsSingleSender(SMS_APP_ID, SMS_APP_KEY);
		}
		return smsSingleSender.send(type, nationCode, phoneNumber, msg, "", "");
	}
	
	/**
	 * 单发短信，中国境内号码
	 * @param type
	 * @param nationCode
	 * @param phoneNumber
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	public SmsSingleSenderResult sms4SingleInCN(int type,String phoneNumber,String msg) throws Exception{
		return sms4Single(type,SMS_NATION_CODE_CN,phoneNumber,msg);
	}
	/**
	 * 单发普通短信
	 * @param nationCode 国家码
	 * @param phoneNumber 手机号
	 * @param msg 短信内容
	 * @return
	 * @throws Exception
	 */
	public SmsSingleSenderResult sms4SingleNormal(String nationCode,String phoneNumber,String msg) throws Exception{
		return sms4Single(0, nationCode, phoneNumber, msg);
	}
	
	/**
	 * 单发普通短信，中国境内号码
	 * @param nationCode 国家码
	 * @param phoneNumber 手机号
	 * @param msg 短信内容
	 * @return
	 * @throws Exception
	 */
	public SmsSingleSenderResult sms4SingleNormalInCN(String phoneNumber,String msg) throws Exception{
		return sms4Single(0, SMS_NATION_CODE_CN, phoneNumber, msg);
	}
	
	/**
	 * 单发营销短信
	 * @param nationCode 国家码
	 * @param phoneNumber 手机号
	 * @param msg 短信内容
	 * @return
	 * @throws Exception
	 */
	public SmsSingleSenderResult sms4SingleMarket(String nationCode,String phoneNumber,String msg) throws Exception{
		return sms4Single(1, nationCode, phoneNumber, msg);
	}
	
	/**
	 * 单发营销短信，中国境内号码
	 * @param nationCode 国家码
	 * @param phoneNumber 手机号
	 * @param msg 短信内容
	 * @return
	 * @throws Exception
	 */
	public SmsSingleSenderResult sms4SingleMarketInCN(String phoneNumber,String msg) throws Exception{
		return sms4Single(1, SMS_NATION_CODE_CN, phoneNumber, msg);
	}
	
	/**
	 * 单发模板短信
	 * @param nationCode 国际码
	 * @param phoneNumber 手机号
	 * @param templId 模板id
	 * @param params 模板中包含的参数，一定要注意顺序
	 * @return
	 * @throws Exception
	 */
	public SmsSingleSenderResult sms4SingleTp(String nationCode,
			String phoneNumber,int templId,ArrayList<String> params) throws Exception{
		if(null==smsSingleSender){
			smsSingleSender = new SmsSingleSender(SMS_APP_ID, SMS_APP_KEY);
		}
		return smsSingleSender.sendWithParam(nationCode, phoneNumber, templId, params, "", "", "");
	}
	
	/**
	 * 单发模板短信，中国境内号码
	 * @param phoneNumber 手机号
	 * @param templId 模板id
	 * @param params 模板中包含的参数，一定要注意顺序
	 * @return
	 * @throws Exception
	 */
	public SmsSingleSenderResult sms4SingleTpInCN(String phoneNumber,int templId,ArrayList<String> params) throws Exception{
		return sms4SingleTp(SMS_NATION_CODE_CN,phoneNumber,templId,params);
	}
	
	/**
	 * 单发模板短信
	 * @param nationCode 国际码
	 * @param phoneNumber 手机号
	 * @param templId 模板id
	 * @param params 模板中包含的参数，字符串类型，多个参数之间用逗号（","）分隔，一定要注意顺序
	 * @return
	 * @throws Exception
	 */
	public SmsSingleSenderResult sms4SingleTp(String nationCode,
			String phoneNumber,int templId,String params) throws Exception{
		if(null==params||"".equals(params)||"null".equals(params)){
			throw new Exception("缺少模板{}中的参数信息！");
		}
		List<String> list = Arrays.asList(params.split(","));
		return sms4SingleTp(nationCode, phoneNumber, templId, new ArrayList<String>(list));
	}
	
	/**
	 * 单发模板短信，中国境内手机号
	 * @param phoneNumber 手机号
	 * @param templId 模板id
	 * @param params 模板中包含的参数，字符串类型，多个参数之间用逗号（","）分隔，一定要注意顺序
	 * @return
	 * @throws Exception
	 */
	public SmsSingleSenderResult sms4SingleTpInCN(String phoneNumber,int templId,String params) throws Exception{
		return sms4SingleTp(SMS_NATION_CODE_CN,phoneNumber,templId,params);
	}
	
	/**
	 * 群发消息
	 * @param type 消息类型 0:普通消息 1:营销消息
	 * @param nationCode 国家码
	 * @param phoneNumbers 手机号list，多个手机号
	 * @param msg 短信内容
	 * @return
	 * @throws Exception
	 */
	public SmsMultiSenderResult sms4Multi(int type,String nationCode,ArrayList<String> phoneNumbers,String msg) throws Exception {
		if(null==smsMultiSender){
			smsMultiSender = new SmsMultiSender(SMS_APP_ID, SMS_APP_KEY);
		}
		return smsMultiSender.send(type, nationCode, phoneNumbers, msg, "", "");
	}
	
	/**
	 * 群发消息，中国境内号码
	 * @param type 消息类型 0:普通消息 1:营销消息
	 * @param phoneNumbers 手机号list，多个手机号
	 * @param msg 短信内容
	 * @return
	 * @throws Exception
	 */
	public SmsMultiSenderResult sms4MultiInCN(int type,ArrayList<String> phoneNumbers,String msg) throws Exception {
		return sms4Multi(type,SMS_NATION_CODE_CN,phoneNumbers,msg);
	}
	
	/**
	 * 群发消息
	 * @param type 消息类型 0:普通消息 1:营销消息
	 * @param nationCode 国家码
	 * @param phoneNumbers 手机号字符串，多个手机号之间以逗号（","）分隔
	 * @param msg 短信内容
	 * @return
	 * @throws Exception
	 */
	public SmsMultiSenderResult sms4Multi(int type,String nationCode,String phoneNumbers,String msg) throws Exception {
		if(null==phoneNumbers||"".equals(phoneNumbers)||"null".equals(phoneNumbers)){
			throw new Exception("请填写至少一个手机号！");
		}
		List<String> list = Arrays.asList(phoneNumbers.split(","));
		return sms4Multi(type, nationCode, new ArrayList<String>(list), msg);
	}
	
	/**
	 * 群发消息，中国境内号码
	 * @param type 消息类型 0:普通消息 1:营销消息
	 * @param phoneNumbers 手机号字符串，多个手机号之间以逗号（","）分隔
	 * @param msg 短信内容
	 * @return
	 * @throws Exception
	 */
	public SmsMultiSenderResult sms4MultiInCN(int type,String phoneNumbers,String msg) throws Exception {
		return sms4Multi(type,SMS_NATION_CODE_CN,phoneNumbers,msg);
	}
	
	/**
	 * 群发普通短信
	 * @param nationCode 国家码
	 * @param msg 短信内容
	 * @param phoneNumbers 手机号list，多个手机号
	 * @return
	 * @throws Exception
	 */
	public SmsMultiSenderResult sms4MultiNormal(String nationCode,ArrayList<String> phoneNumbers,String msg)throws Exception{
		return sms4Multi(0, nationCode, phoneNumbers, msg);
	}
	
	/**
	 * 群发普通短信，中国境内号码
	 * @param nationCode 国家码
	 * @param msg 短信内容
	 * @param phoneNumbers 手机号list，多个手机号
	 * @return
	 * @throws Exception
	 */
	public SmsMultiSenderResult sms4MultiNormalInCN(ArrayList<String> phoneNumbers,String msg)throws Exception{
		return sms4Multi(0, SMS_NATION_CODE_CN, phoneNumbers, msg);
	}
	
	/**
	 * 群发普通短信
	 * @param nationCode 国家码
	 * @param msg 短信内容
	 * @param phoneNumbers 手机号字符串，多个手机号用逗号","分隔
	 * @return
	 * @throws Exception
	 */
	public SmsMultiSenderResult sms4MultiNormal(String nationCode,String phoneNumbers,String msg)throws Exception{
		return sms4Multi(0, nationCode, phoneNumbers, msg);
	}
	
	/**
	 * 群发普通短信，中国境内号码
	 * @param msg 短信内容
	 * @param phoneNumbers 手机号字符串，多个手机号用逗号","分隔
	 * @return
	 * @throws Exception
	 */
	public SmsMultiSenderResult sms4MultiNormalInCN(String phoneNumbers,String msg)throws Exception{
		return sms4Multi(0, SMS_NATION_CODE_CN, phoneNumbers, msg);
	}
	
	/**
	 * 群发营销短信
	 * @param nationCode 国家码
	 * @param msg 短信内容
	 * @param phoneNumbers 手机号list，多个手机号
	 * @return
	 * @throws Exception
	 */
	public SmsMultiSenderResult sms4MultiMarket(String nationCode,ArrayList<String> phoneNumbers,String msg)throws Exception{
		return sms4Multi(1, nationCode, phoneNumbers, msg);
	}
	
	/**
	 * 群发营销短信，中国境内号码
	 * @param msg 短信内容
	 * @param phoneNumbers 手机号list，多个手机号
	 * @return
	 * @throws Exception
	 */
	public SmsMultiSenderResult sms4MultiMarketInCN(ArrayList<String> phoneNumbers,String msg)throws Exception{
		return sms4Multi(1, SMS_NATION_CODE_CN, phoneNumbers, msg);
	}
	
	/**
	 * 群发营销短信
	 * @param nationCode 国家码
	 * @param msg 短信内容
	 * @param phoneNumbers 手机号字符串，多个手机号用逗号","分隔
	 * @return
	 * @throws Exception
	 */
	public SmsMultiSenderResult sms4MultiMarket(String nationCode,String phoneNumbers,String msg)throws Exception{
		return sms4Multi(1, nationCode, phoneNumbers, msg);
	}
	
	/**
	 * 群发营销短信，中国境内号码
	 * @param msg 短信内容
	 * @param phoneNumbers 手机号list，多个手机号
	 * @return
	 * @throws Exception
	 */
	public SmsMultiSenderResult sms4MultiMarketInCN(String phoneNumbers,String msg)throws Exception{
		return sms4Multi(1, SMS_NATION_CODE_CN, phoneNumbers, msg);
	}
	
	/**
	 * 群发模板短信
	 * @param nationCode 国家码
	 * @param phoneNumbers 手机号码
	 * @param templId 模板id
	 * @param params 模板参数，多个{} 中的内容
	 * @return
	 * @throws Exception
	 */
	public SmsMultiSenderResult sms4MultiTp(String nationCode,
			ArrayList<String> phoneNumbers,int templId,ArrayList<String> params) throws Exception{
		if(null==smsMultiSender){
			smsMultiSender = new SmsMultiSender(SMS_APP_ID, SMS_APP_KEY);
		}
		return smsMultiSender.sendWithParam(nationCode, phoneNumbers, templId, params, "", "","");
	}
	
	/**
	 * 群发模板短信，中国境内号码
	 * @param phoneNumbers 手机号码
	 * @param templId 模板id
	 * @param params 模板参数，多个{} 中的内容
	 * @return
	 * @throws Exception
	 */
	public SmsMultiSenderResult sms4MultiTpInCN(ArrayList<String> phoneNumbers,int templId,
			ArrayList<String> params) throws Exception{
		return sms4MultiTp(SMS_NATION_CODE_CN,phoneNumbers,templId,params);
	}
	
	/**
	 * 群发模板短信
	 * @param nationCode 国家码
	 * @param phoneNumbers 手机号码字符串，中间以逗号","分隔
	 * @param templId 模板id
	 * @param params 模板参数 多个{} 中的内容。字符串，中间以逗号","分隔
	 * @return
	 * @throws Exception
	 */
	public SmsMultiSenderResult sms4MultiTp(String nationCode,
			String phoneNumbers,int templId,String params) throws Exception{
		if(null==phoneNumbers||"".equals(phoneNumbers)||"null".equals(phoneNumbers)){
			throw new Exception("请填写至少一个手机号！");
		}
		if(null==params||"".equals(params)||"null".equals(params)){
			throw new Exception("缺少模板{}中的参数信息！");
		}
		List<String> phones = Arrays.asList(phoneNumbers.split(","));
		List<String> paramsList = Arrays.asList(params.split(","));
		return sms4MultiTp(nationCode, new ArrayList<String>(phones), templId, new ArrayList<String>(paramsList));
	}
	
	/**
	 * 群发模板短信，中国境内号码
	 * @param phoneNumbers 手机号码字符串，中间以逗号","分隔
	 * @param templId 模板id
	 * @param params 模板参数 多个{} 中的内容。字符串，中间以逗号","分隔
	 * @return
	 * @throws Exception
	 */
	public SmsMultiSenderResult sms4MultiTpInCN(String phoneNumbers,int templId,String params) throws Exception{
		return sms4MultiTp(SMS_NATION_CODE_CN,phoneNumbers,templId,params);
	}
	
	public static void main(String[] args) throws Exception {
		SMSHelper sh = new SMSHelper("smsconfig.properties");
		System.out.println(sh.sms4SingleTpInCN("15313685599", 82386, new ArrayList<String>()));
	}
}
