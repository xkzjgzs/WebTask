package org.creation.webtask.common;

/**
 * ���һЩ������������Ϣ���������UA�����糬ʱʱ�䡢��������Դ�����
 *
 */
public class CommonData {
	
	//������һЩ������Ϣ��һ�������޸�
	final public static String UA_CHROME = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.152 Safari/535.19 CoolNovo/2.0.3.55";
	final public static String UA_IE8 = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0)";
	final public static String UA_BAIDU_PC = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; baidubrowser 1.x)";
	final public static String UA_BAIDU_ANDROID = "Mozilla/5.0 (Linux; U; Android 2.3.5; zh-cn; MI-ONE Plus Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) FlyFlow/2.4 Version/4.0 Mobile Safari/533.1 baidubrowser/042_1.8.4.2_diordna_458_084/imoaiX_01_5.3.2_sulP-ENO-IM/100028m";
	final public static String UA_ANDROID = "Mozilla/5.0 (Linux; U; Android 2.3.5; zh-cn; MI-ONE Plus Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
	final public static String UA_IPHONE = "Mozilla/5.0 (iPhone; CPU iPhone OS 5_0_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko)";
	final public static int TIME_OUT = 30000;//��ʱʱ��Ϊ30��
	final public static int RETRY_TIMES = 3;//���Դ���
	
	
	
	/**�˳�������������ֵ�������޸�*/
	public static final String SRV_ACTION = "me.shumei.oks.signsrvaction";
	/**�˳�������������ֵ�������޸�*/
	public static final int CMD_THREAD_SHOW_TOAST = 9999;
	
	/**
	 * ����һ��ǩ�����������͹㲥��������ǩ����������Toast��ʾ��Ϣ
	 * @param context Context
	 * @param msg Ҫ��������Ϣ
	 * @param timetype true��ʾ��ʱ���Toast��false��ʾ��ʱ���Toast
	 */
	public static void sendShowToastBC(String msg)
	{
		System.out.println(msg);
	}
	
	public CommonData() {
	}

}
