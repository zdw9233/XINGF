package com.uyi.app;

public  abstract class Constens {
	public static int START_ACTIVITY_FOR_RESULT  = 10000;//跳转界面1
	public static int START_ACTIVITY_FOR_RESULT_TWO  = 10005;//跳转界面2
	public static int START_ACTIVITY_FOR_RESULT_THREE  = 10006;//跳转界面3
	public static int BACK_LOGIN  = 10001;//登陆
	public static int PHOTO_REQUEST_GALLERY = 10020;//相册
	public static int PHOTO_REQUEST_TAKEPHOTO = 10021;//相机
	public static int PHOTO_REQUEST_CUT = 10022;//裁剪
	public static int START_ALIPAY_FOR_RESULT  = 10003;//阿里支付跳转界面

	 
	public static final String CHAR_ENCODING = "UTF-8";
	public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String DATE_FORMAT_HH_MM_SS = "HH:mm:ss";
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String USERNAME_REGEX = "[a-zA-Z0-9_-]+";
	public static final String USERNAME_REGEX_AND_LEN = "[a-zA-Z0-9_-]{4,32}";
	public static final int USERNAME_MIN_LEN = 6;
	public static final int USERNAME_MAX_LEN = 32;
	public static final int REAL_NAME_LEN = 20;

	public static final String PASSWORD_REGEX = "(?=.*?[A-Z]+.*?)(?=.*?[a-z]+.*?)(?=.*?[0-9]+.*?)(?=.*?[\\p{Punct}]+.*?).*";
	public static final String PASSWORD_NEW_REGEX = "(?=.*?[a-zA-Z]+.*?)(?=.*?[0-9]+.*?).*";
	public static final int PASSWORD_MIN_LEN = 6;
	public static final int PASSWORD_MAX_LEN = 32;

	public static final String EMAIL_REGEX = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
	public static final String ID_CARD_REGEX = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";

	public static final String PHONE_REGEX = "(^(\\d{3,4}[-]?)?\\d{7,8})$|^((1[0-9][0-9]\\d{8}$))";
	
	
	
	/**
	 * 测试服地址
	 */
	public final static String SERVER_URL = "http://121.42.142.228:8080";
	
	/**
	 * 生产服地址
	 */
//	public final static String SERVER_URL = "http://www.uyidoctor.com";
	//登陆
	public final static String LOGIN_URL = SERVER_URL+"/app/api/account/login";
	//获取问题答案列表
	public final static String SAFE_QUESTIONS = SERVER_URL+"/app/api/common/safe/questions";
	
	/**
	 * 客户充值健康豆 ${money} POST
	 */
	public final static String CUSTOMER_BUY_MOBILE_COIN = SERVER_URL+"/app/api/customer/buy/mobile/coin/%s";
	
	/**
	 * 客户充值成功后查询健康豆 ${orderNo} GET
	 */
	public final static String CUSTOMER_BUY_COIN_RESULT = SERVER_URL+"/app/api/customer/buy/coin/result/%s";
	
	/**
	 * 检查问题
	 */
	public final static String SAFE_CHECK_ANSWER = SERVER_URL+"/app/api/common/safe/check/answer";
	
	/**
	 * 客户重置密码
	 */
	public final static String FORGOT_PASSWORD = SERVER_URL+"/app/api/account/forgot/password";
	/**
	 * 医生修改密码
	 */
	public final static String SETTING_PASSWORD = SERVER_URL+"/app/api/doctor/setting/password";
	/**
	 * 用户安全问题获取
	 */
	public final static String SAFE_QUESTION = SERVER_URL+"/app/api/common/account/safe/question?account=%s&phoneNumber=%s";
	
	/**
	 * type 1: 检查用户/医生账号名
	 * type	2: 检查健康团队名字
	 * 
	 */
	public final static String CHECK_NAME_USED = SERVER_URL+"/app/api/common/check/name/used?type=%s&name=%s";
	/**
	 * 省查询
	 */
	public final static String PROVINCDS = SERVER_URL+"/app/api/common/provinces";
	/**
	 * 市查询
	 */
	public final static String PROVINCD = SERVER_URL+"/app/api/common/cities/province/%s";
	
	/**
	 * 患者注册
	 */
	public final static String ACCOUNT_REGISTER = SERVER_URL+"/app/api/account/register";
	
	/**
	 * 获取患者基本信息
	 */
	public final static String ACCOUNT_DETAIL = SERVER_URL+"/app/api/account/detail";
	
	/**
	 * 医生更新基本信息
	 */
	public final static String DOCTOR_UPDATE = SERVER_URL+"/app/api/doctor/update";
	
	
	/**
	 * 创建咨询
	 * consultId  null咨询    not null 就是随访
	 */
	public final static String ACCOUNT_HEALTH_CONSULT = SERVER_URL+"/app/api/account/health/consult";
	
	/**
	 * 补充咨询资料
	 */
	public final static String ACCOUNT_HEALTH_CONSULT_ = SERVER_URL+"/app/api/account/health/consult/%s";
	
	/**
	 * 查询健康咨询/随访(处理结果)/线下检查(处理结果) 列表
	 * type:1: All
			2: 线下检查
			3: 随访
			4: 直接处理
			5：讨论组
	 * consultType:1: All
					2: 普通咨询
					3: 随访
	 * page
	 * pageSize
	 */
	public final static String HEALTH_CONSULTS = SERVER_URL+"/app/api/doctor/health/consults?type=%s&consultType=%s&page=%s&pageSize=%s";
	
	/**
	 * 专家/资深 处理健康咨询
	 * ${type}
	 * 	1: 返回客户补充资料(专家, 助理)
		2: 提交医生处理(助理))
		3: 直接处理(专家)
		4: 线下检查(专家)
		5: 随访(专家)
		6: 提交讨论组 (专家)
		7: 首席添加意见(首席)
		8: 助理添加线下检查报告
	 * ${advice} 处理意见
	 * ${checkDate}   type 4 5 要传此字段
	 */
	public final static String  DOCTOR_PROCESS_HEALTH_CONSULT_  = SERVER_URL + "/app/api/doctor/process/health/consult/%s";
	
	/**
	 * 查询健康咨询
	 */ 
	public final static String HEALTH_CONSULT = SERVER_URL+"/app/api/doctor/health/consult/%s";
	
	/**
	 * 查询加入的健康团队
	 */
	public final static String HEALTH_GROUPS = SERVER_URL+"/app/api/account/health/groups";
	
	/**
	 * 评价本次咨询  %s   consultId
	 */
	public final static String HEALTH_CONSULT_CONSULT = SERVER_URL+"/app/api/account/health/consult/%s/comment";
	
	
	/**
	 * 查询消息数量
	 *  1: 今日日程数
		2:未读消息+公告数
		3:咨询数
		4: 随访数
		5: 线下检查数
		6: 健康管理新报警数
		8: 健康问答数
	 *  
	 */
	public final static String ACCOUNT_STATISTICS = SERVER_URL+"/app/api/doctor/statistics/%s";
	
	/**
	 * 健康报告图表
	 * ${type}
	 * ${startDate}
	 * ${endDate}
	 */
	public final static String HEALTH_REPORT = SERVER_URL+"/app/api/doctor/customer/%s/health/report?type=%s&startDate=%s&endDate=%s";



	/**
	 * 客户上传日常健康检查资料
	 * POST
	 */
	public final static String HEALTH_CHECK_INFOS_SAVE = SERVER_URL+"/app/api/account/health/check/infos";
	
	
	/**
	 * 医生查询客户日常健康检查资料查询列表
	 * GET
	 * ${startDate}
	 * ${endData}
	 * ${pageIndex}
	 * ${pageSize}
	 */
	public final static String HEALTH_CHECK_INFOS = SERVER_URL+"/app/api/doctor/query/%s/health/check/infos?startDate=%s&endDate=%s&page=%s&pageSize=%s";
	
	
	/**
	 * 客户日常健康检查资料查询详细
	 * GET
	 * ${id}
	 * 
	 */
	public final static String HEALTH_CHECK_INFO = SERVER_URL+"/app/api/doctor/query/health/check/info/%s";
	
	
	
	
	/**
	 * 查询健康问答
	 * /app/api/account/health/advices?startTime={startTime}&endTime={endTime}&page={page}&pageSize={pageSize}&sort={sort}
	 */
	public final static String HEALTH_ADVICES = SERVER_URL+"/app/api/doctor/health/advices?page=%s&pageSize=%s";
	
	
	
	/**
	 * 创建健康问答
	 */
	public final static String CONSULT_ADVICD = SERVER_URL+"/app/api/consult/advice";
	
	
	/**
	 * 健康问答回复查询   
	 * ${id}  ${page}  ${pageSize}
	 * %s
	 */
	public final static String CONSULT_HEALTH_ADVICD = SERVER_URL+"/app/api/consult/health/advice/%s/replies?page=%s&pageSize=%s&sort=1";
	
	/**
	 * 回复健康问答
	 */
	public final static String CONSULT_ADVICD_REPLY = SERVER_URL+"/app/api/consult/advice/reply";
	
	
	/**
	 * 日程查询GET   
	 * ${today}
	 * ${startDate}
	 * ${endDate}
	 * ${page}
	 * ${pageSize}
	 */
	public final static String ACCOUNT_SCHEDULES = SERVER_URL+ "/app/api/doctor/schedules?startDate=%s&endDate=%s&page=%s&pageSize=%s";
	
	/**
	 * /日程创建 POST
	 */
	public final static String ACCOUNT_SCHEDULE = SERVER_URL+ "/app/api/doctor/schedule";
	
	
	
	
	/**
	 * 医生查询消息/公告列表 ${type} ${isRead} ${page}${pageSize} GET
	 * 
	 */
	public final static String ACCOUNT_MESSAGES = SERVER_URL+ "/app/api/doctor/messages?type=%s&isRead=%s&page=%s&pageSize=%s";
	
	
	/**
	 * 医生查询消息/通知详情   ${id} ${type} GET
	 */
	public final static String ACCOUNT_MESSAGE = SERVER_URL+ "/app/api/doctor/message/%s/type/%s";
	
	/**
	 * 医生消息/公告删除/标记已读 POST
	 * 
	 * ${operate} 	1: 标记已读   	 2: 删除
	 * ${type}		1: 消息		 2: 公告
	 * ${ids}
	 */
	public final static String ACCOUNT_MESSAGE_OPERATE = SERVER_URL+ "/app/api/doctor/message/operate";
	
	
	
	/**
	 * 医生查询健康团队/专属咨询列表 GET
	 * ${type}1: 我的预约  2: 所有预约(所有可以预约的专属咨询)
	 * ${page}
	 * ${pageSize}
	 */
	public final static String ACCOUNT_QUERY_EXCLUSIVE_CONSULTS = SERVER_URL+ "/app/api/doctor/query/exclusive/consults?type=%s&page=%s&pageSize=%s";
	
	
	
	
	/**
	 * 客户查询专属咨询详细
	 * ${id}
	 */
	public final static String ACCOUNT_EXCLUSIVE_CONSULT = SERVER_URL+ "/app/api/doctor/exclusive/consult/%s";
	/**
	 * 添加专属咨询
	 */
	public final static String ACCOUNT_EXCLUSIVE_CONSULT_ADD = SERVER_URL+ "/app/api/doctor/exclusive/consult";
	
	/**
	 * 客户预约专属预约
	 */
	public final static String ACCOUNT_RESERVE_EXCLUSIVE_CONSULT = SERVER_URL+ "/app/api/doctor/reserve/exclusive/consult/%s";
	
	
	
	
	/**
	 * 医生查询自己发布的消息/公告列表    GET
	 * ${type}  1: 消息 2: 公告
	 * ${page}
	 * ${pageSize}
	 */
	public final static String DOCTOR_MY_MESSAGES = SERVER_URL+ "/app/api/doctor/my/messages?type=%s&page=%s&pageSize=%s";
	
	/**
	 * 医生查询自己发布的消息/公告详情    GET
	 * ${msgId}  
	 * ${type}  1: 消息 2: 公告
	 */
	public final static String DOCTOR_MY_MESSAGE = SERVER_URL+ "/app/api/doctor/my/message/%s/type/%s";
	
	/**
	 * 医生发布公告 POST
	 */
	public final static String DOCTOR_MY_PUBLISH = SERVER_URL+ "/app/api/doctor/message/publish";
	
	
	/**
	 * 医生查询用户 根据名称  
	 * ${name}${page}${pageSize}
	 * 
	 */
	public final static String DOCTOR_QUERY_CUSTOMERS = SERVER_URL+ "/app/api/doctor/query/customers?name=%s&page=%s&pageSize=%s";
	
	
	/**
	 * 医生查询客户健康咨询详情  
	 */
	public final static String HEALTH_CONSULT_DISCUSSES = SERVER_URL+ "/app/api/doctor/health/consult/%s/discusses?page=%s&pageSize=%s";
	
	/**
	 * 医生采用客户健康咨询讨论意见 POST
	 * 
	 */
	public final static String HEALTH_CONSULT_USE_DISCUSS = SERVER_URL+ "/app/api/doctor/health/consult/%s/use/discuss";
	
	/**
	 * 医生发表意见 POST ${coonsultId}
	 * 
	 * 
	 */
	public final static String HEALTH_CONSULT_DISCUSS = SERVER_URL+ "/app/api/doctor/health/consult/%s/discuss";
	
	
	/**
	 * 医生查询用户个人资料
	 */
	public final static String DOCTOR_QUERY_CUSTOMER_INFO = SERVER_URL+ "/app/api/doctor/customer/%s/info";
	
	/**
	 * 医生查询用户健康资料
	 */
	public final static String DOCTOR_QUERY_CUSTOMER_HEALTH_INFO = SERVER_URL+ "/app/api/doctor/customer/%s/health/info";
	
	/**
	 * 医生查看专属咨询列表
	 */
	public final static String DOCTOR_EXCLUSIVE_CONSULTS = SERVER_URL + "/app/api/doctor/exclusive/consults";
	/**
	 * 医生查看健康管理列表
	 */
//	public final static String DOCTOR_HEALTH_MANAGER = SERVER_URL + "/app/api/doctor/group/customer/check/infos?name=%s&page=%s&pageSize=%s";
	public final static String DOCTOR_HEALTH_MANAGER = SERVER_URL + "/app/api/doctor/group/customer/check/infos?name=%s&page=%s&pageSize=%s";
	/**
	 * 查询所有健康团队
	 */
	public final static String HEALTH_GROUPS_ALL = SERVER_URL+"/app/api/account/query/health/groups?name=%s&cityId=%s&page=%s&pageSize=%s";
	/**
	 * 查询所有城市
	 */
	public final static String HEALTH_CITYs = SERVER_URL+"/app/api/common/cities";
	/**
	 * 查询健康团队详情
	 */
	public final static String HEALTH_GROUPS_DETAILS = SERVER_URL+"/app/api/common/health/group/%s";
	/**
	 * 查询健康团队医生
	 */
	public final static String HEALTH_GROUPS_DOCTER = SERVER_URL+"/app/api/common/health/group/%s/doctors";
	/**
	 * 查询健康团队医生详情
	 */
	public final static String HEALTH_GROUPS_DOCTER_DETAILS = SERVER_URL+"/app/api/common/doctor/%s";
	/**
	 * 查询健康团队医生评价
	 */
	public final static String HEALTH_GROUPS_DOCTER_DETAILS_PINJIA = SERVER_URL+"/app/api/common/doctor/%s/comments?page=%s&pageSize=%s";
	/**
	 * 客户加入健康团队
	 */
	public final static String HEALTH_GROUPS_JIARU = SERVER_URL+"/app/api/account/join/health/group/%s";
	
	/**
	 * 添加收费标准
	 */
	public final static String DOCTOR_HEALTH_FEE = SERVER_URL+"/app/api/doctor/health/group/fee?beans=";
	
	/**
	 * 报警标准
	 */
	public final static String DOCTOR_HEALTH_WARNING_DATA = SERVER_URL+"/app/api/doctor/health/info/warning/data";

	/**
	 * 医生解除报警
	 */
	public final static String DOCTOR_CLEAR_HEALTH_WARNING_DATA = SERVER_URL+"/app/api/doctor/clear/health/check/info/warn/%s";
	
	/**
	 * 系统公告列表
	 */
	public final static String COMMON_BULLETINS = SERVER_URL+ "/app/api/common/bulletins?&page=%s&pageSize=%s";
	
	/**
	 * 系统公告详情
	 */
	public final static String COMMON_BULLETINS_DETAILS = SERVER_URL+ "/app/api/common/bulletin/%s";
	
}
