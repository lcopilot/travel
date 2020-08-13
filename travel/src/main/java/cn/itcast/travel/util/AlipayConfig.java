package cn.itcast.travel.util;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016100100643400";

	// 商户私钥，您的PKCS8格式RSA2私钥
	public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCA/N5Qi5HIQg444iTtUZLODs2hJhZ7sE2Dihccrw7YXMjWMs0buIpPS8v9Ws0T5Sshu8dh/oBsBwT6Dlw8HTuWlRC+3C2h+lAwVUd5F6ShmdewNFDQDOVMlJ8tbNxMPqrs9lFRpOWVjQDyWYLS0Rqgkl7zTm+M0jY4v72N4t5F0/IGRBL3x4xT+AxldAnZhGAsUk9qc/jjXThL26QRYwemAL5YiugzaJHZTje3a+tTNDOPGAwqulCDyBDuIDHbFo6s28rpt+6sMHU/BoEehwCFlKgtoLYjkXDYo4GINu5yWYOJ/mZ/zxSfZVKHrDq48L7JkNO+DuDpHQaeZZekijApAgMBAAECggEAXwbsWfaZDinsBGhGwDfqqbgqMeC3i6RVVuUzJcSqcSfaJkQC67tG2iiDPkHY+CFLZ2zJ9R3kSrHZLsfJl9gJgGSNk67uR3j7/r+1Cjcq6ItIZd4E82GsxIBxzKt4mQtKLY1CV53eLhFVczYStXGr5kQvg5xzfhGYNLK92XwcOOBhyaPKOxysei1iCU8YZ+2H6l4fMk8BwXVSDCPPc0H8Ev/+aWGgVLED8kayyslAXSJl1vjEBazFpNreC4z4oWnARDV72BHxicHFWgT9bOREBHr3+kD/cQLiKvam3yZHvX8OEjYLZilJCH28XSqHsWqeGdGSgtxtW1oif9CfsqnioQKBgQDEg6ofQkuKDI0cNcJx+bZfqRjMEogwR2Cl320MYoHEt84Yzmdrb6tXSz9WHg2IQKmgI+eSn3waSla7oFxqpFyg0q80pXXpGcJWGlPDwa+kpZgoZOgx/D6RXhC3wNUZc0xdrX4ru34FwMBkM7RDN15Ow6ppmm1NmlM69kTJkIzaLQKBgQCoCGuMR55OO8kVKPmkDaBiAnoTIuiiQDior1tedNA8ACaSRlg+4DciFXOFiOOoFWaEDmtNctlId0Nxf6G3S2nTT0pEsIpK56k+54/tsq9rA5cuv+dDCvHY7RXpfKheFzmf+Xiuar+fnCEtg79FdYjPtuukDB3FySTl/44gw5xXbQKBgAquRU7RoZ1tNTY1Tc44DkuAu1JMJ47IMl6RmDG2IOt6Of9rKH/UX/Gsq1KY649eRFnp2apEdNBQ/gnQm3VIXah4CwHHhHMY7VEUuTzIiC1gurZnVZ9x4EyyWiJCntJ1S89DoLnrxNu3MD3B+ag1mDCbveVjeVFgc+mefPqfmqg5AoGBAI93wPvhGlUJzsJ2Xc+oosuL+dbk82O1GatNLeIUMsdICabS0P2+StSbJCtbmaUlwIX4Gb3i1Yp96eO6acUqaoy1ImBLq4gRC/xdkhINgqEIS8jqqPYhHiVtKExW1xqfEeB5DIU/N6V7lQhbeLBBjjJrp/5FKhZ5izulWyC2Y4JxAoGAcWe3K4pb9wPX/3BH4T8Fl0U/7PCDtSwyzZQuITOdNRGmEDW88ZzZX94DxJWMFkt7vbx1ulJqd/n0gxNldQbce3JdS7IPAil/gB8VGXL8+fqg5lXxoqL5Rk2xmEkjyzM5T0pykIFNK7fFwseFUKAtuBSXo7TY3rrECX/z+DhQZlU=";

	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
	public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyBx2NK6EasxVtZIGJSnRX/b/Yip0HXob7dEKvKCOZdUDIx1kiESSnbU2z6OStkQ1yvLK4k+qu8uyXkTv5a+CE2LSW8AC1+3BSWattC/PUMxUzM7JOSQFcRw7IrVuAEjanwcawBMFXe08ke8SsyHB8O98GvLK9j4Wh+sDRkCgb9XS8pfkCaswESm16e0pVUfOCOYxlrHBLpbCbuARDcmu66KFlQye01ISitgUIhEflxksdnSsr5KBe03ljyDVdJ9hfxrDYFLlIXGgixlNomJ0egnO5IGXDuaxg8rsgvpjpLf2n/np9khct6OFKykGtxRkNxnffC+uwIPHTr/b14JXxQIDAQAB";

	// 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

	// 签名方式
	public static String sign_type = "RSA2";

	// 字符编码格式
	public static String charset = "utf-8";

	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

	// 支付宝网关
	public static String log_path = "C:\\";

//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	/**
	 * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
	 * 
	 * @param sWord 要写入日志里的文本内容
	 */
	public static void logResult(String sWord) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
			writer.write(sWord);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
