package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.util.JedisPoolUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import redis.clients.jedis.Jedis;

/**
 * @program: travel
 * @auther: MuGe
 * @date: 2019/9/1
 * @time: 8:57
 * @description:
 */

/**
 * 注册短信验证码验证
 */
@WebServlet("/smsCheckServlet")
public class SmsCheckServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取手机和输入的验证码
		String telephone = request.getParameter("telephone");
		String smsCode = request.getParameter("smsCode");
		//2.从redis取出对应的验证码
		//2.1.获取链接
		Jedis jedis = JedisPoolUtils.getJedis();
		//2.2.取出验证码
		String redis_smsCode = jedis.get("regist_" + telephone);
		//3.判断验证码
		ResultInfo info = new ResultInfo();
		if (smsCode.equals(redis_smsCode)){
			//验证码正确
			info.setFlag(true);
			info.setErrorMsg("验证码正确");
		}else {
			//验证码错误
			info.setFlag(false);
			info.setErrorMsg("验证码错误");
		}
		//4.将info对象序列化为json
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(info);
		//5.将json数据写回客户端
		//设置content-type
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(json);
		//6.关闭 归还连接
		jedis.close();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
}
