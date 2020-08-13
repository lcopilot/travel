package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.util.JedisPoolUtils;
import cn.itcast.travel.util.SmsUtils;
import com.aliyuncs.exceptions.ClientException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import redis.clients.jedis.Jedis;

/**
 * @program: travel
 * @auther: MuGe
 * @date: 2019/8/31
 * @time: 20:41
 * @description:
 */

/**
 * 发送注册短信验证码
 */
@WebServlet("/sendSmsServlet")
public class SendSmsServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取手机号
		String telephone = request.getParameter("telephone");
		//2.生成6位数的随机验证码
		Random ra = new Random();
		String code = Integer.toString(ra.nextInt(899999) + 100000);
		try {
			//3.发送短信
			boolean flag = SmsUtils.sendRegistSms(telephone, code);
			ResultInfo info = new ResultInfo();
			if (flag) {
				//发送成功
				//4.将验证码存入redis 并设置5分钟后不可用
				//4.1.获取链接
				Jedis jedis = JedisPoolUtils.getJedis();
				//4.2.存入
				jedis.setex("regist_"+telephone,300, code);
				//4.3.关闭 归还连接
				jedis.close();

				info.setFlag(flag);
				info.setErrorMsg("验证码已发送");
			}else {
				//发送失败
				info.setFlag(flag);
				info.setErrorMsg("验证码发送失败,请稍后重试");
			}
			//将info对象序列化为json
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(info);
			//将json数据写回客户端
			//设置content-type
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(json);

		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
}
