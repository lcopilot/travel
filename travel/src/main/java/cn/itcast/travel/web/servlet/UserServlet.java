package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import cn.itcast.travel.util.BaiduAiUtil;
import cn.itcast.travel.util.JedisPoolUtils;
import cn.itcast.travel.util.SmsUtils;
import com.aliyuncs.exceptions.ClientException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.json.JSONObject;
import redis.clients.jedis.Jedis;

/**
 * @program: travel
 * @auther: MuGe
 * @date: 2019/9/1
 * @time: 17:50
 * @description:
 */
@WebServlet("/user/*")    // /user/add /user/find
public class UserServlet extends BaseServlet {

	//声明UserService业务对象
	private UserService service = new UserServiceImpl();

	/**
	 * 注册功能
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void regist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//验证码校验
		//获取
		String check = request.getParameter("check");
		HttpSession session = request.getSession();
		String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVLET");
		//防止验证码复用
		session.removeAttribute("CHECKCODE_SERVLET");
		//校验
		if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
			//验证码错误
			ResultInfo info = new ResultInfo();
			//注册失败
			info.setFlag(false);
			info.setErrorMsg("验证码错误");
			//将info对象序列化为json
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(info);

			//将json数据写回客户端
			//设置content-type
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(json);
			return;
		}

		//1.获取数据
		Map<String, String[]> map = request.getParameterMap();
		//2.封装对象
		User user = new User();
		try {
			BeanUtils.populate(user, map);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		//3.调用service完成注册
		//UserService service = new UserServiceImpl();
		boolean flag = service.regist(user);
		ResultInfo info = new ResultInfo();
		//4.响应结果
		if (flag) {
			//注册成功
			info.setFlag(flag);
		} else {
			//注册失败
			info.setFlag(flag);
			info.setErrorMsg("注册失败!");
		}

		//将info对象序列化为json
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(info);

		//将json数据写回客户端
		//设置content-type
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(json);
	}

	/**
	 * 登录功能
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取验证码
		String check = request.getParameter("check");
		String checkcode_servlet = (String) request.getSession().getAttribute("CHECKCODE_SERVLET");
		//2.获取用户名和密码
		Map<String, String[]> map = request.getParameterMap();
		//3.封装user
		User user = new User();
		try {
			BeanUtils.populate(user, map);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		//4.调用service查询
		//UserService service = new UserServiceImpl();
		User loginUser = service.login(user);

		ResultInfo info = new ResultInfo();
		//5.判断验证码
		if (checkcode_servlet.equalsIgnoreCase(check)) {
			//验证码正确
			//6.判断用户对象是否为空
			if (loginUser == null) {
				//用户名密码错误
				info.setFlag(false);
				info.setErrorMsg("用户名或密码错误");
			}
			//7.判断用户是否激活
			if (loginUser != null && "N".equals(loginUser.getStatus())) {
				//用户尚未激活
				info.setFlag(false);
				info.setErrorMsg("您尚未激活,请登录邮箱激活");
			}
			//8.判断登录成功
			if (loginUser != null && "Y".equals(loginUser.getStatus())) {
				//登录成功
				//将登录用户存入session
				request.getSession().setAttribute("user", loginUser);
				info.setFlag(true);
			}
		} else {
			//验证码错误
			info.setFlag(false);
			info.setErrorMsg("验证码错误");
		}

		//9.响应数据
		//9.1.将info对象序列化为json
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(info);
		//9.2.将json数据写回客户端
		//设置content-type
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(json);
	}

	/***
	 * 查询单个对象
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void findOne(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.从session中获取登录用户
		Object user = request.getSession().getAttribute("user");

		//2.响应数据
		//2.1.将info对象序列化为json
		/*ObjectMapper mapper = new ObjectMapper();
		//2.2.将json数据写回客户端
		//设置content-type
		response.setContentType("application/json;charset=utf-8");
		mapper.writeValue(response.getOutputStream(), user);*/
		writeValue(user, response);
	}

	/**
	 * 退出功能
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void exit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.销毁session
		request.getSession().invalidate();

		//2.跳转页面
		response.sendRedirect(request.getContextPath() + "/login.html");
	}

	/**
	 * 激活功能
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void active(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取激活码
		String code = request.getParameter("code");
		if (code != null) {
			//2.调用service完成激活
			//UserService service = new UserServiceImpl();
			boolean flag = service.active(code);
			//3.判断标记
			String msg = null;
			if (flag) {
				//激活成功
				msg = "激活成功,请<a href='http://localhost/travel/login.html'>登录</a>";
			} else {
				//激活失败
				msg = "激活失败,请联系管理员";
			}
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(msg);
		}
	}

	/**
	 * 发送注册短信验证码
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void sendRegistSms(HttpServletRequest request, HttpServletResponse response)
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
				jedis.setex("regist_" + telephone, 300, code);
				//4.3.关闭 归还连接
				jedis.close();

				info.setFlag(flag);
				info.setErrorMsg("验证码已发送");
			} else {
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

	/**
	 * 发送登录验证码
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void sendLoginSms(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取手机号
		String telephone = request.getParameter("telephone");
		//2.生成6位数的随机验证码
		Random ra = new Random();
		String code = Integer.toString(ra.nextInt(899999) + 100000);
		try {
			//3.发送短信
			boolean flag = SmsUtils.sendLoginSms(telephone, code);
			ResultInfo info = new ResultInfo();
			if (flag) {
				//发送成功
				//4.将验证码存入redis 并设置5分钟后不可用
				//4.1.获取链接
				Jedis jedis = JedisPoolUtils.getJedis();
				//4.2.存入
				jedis.setex("login_" + telephone, 300, code);
				//4.3.关闭 归还连接
				jedis.close();

				info.setFlag(flag);
				info.setErrorMsg("验证码已发送");
			} else {
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

	/**
	 * 注册短信验证码验证
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void smsRegistCheck(HttpServletRequest request, HttpServletResponse response)
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
		if (smsCode.equals(redis_smsCode)) {
			//验证码正确
			info.setFlag(true);
			info.setErrorMsg("验证码正确");
		} else {
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

	/**
	 * 登录短信验证码验证
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void smsLoginCheck(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取手机和输入的验证码
		String telephone = request.getParameter("telephone");
		String smsCode = request.getParameter("smsCode");
		//2.从redis取出对应的验证码
		//2.1.获取链接
		Jedis jedis = JedisPoolUtils.getJedis();
		//2.2.取出验证码
		String redis_smsCode = jedis.get("login_" + telephone);
		//3.判断验证码
		ResultInfo info = new ResultInfo();
		if (smsCode.equals(redis_smsCode)) {
			//验证码正确
			info.setFlag(true);
			User user = service.telephone(telephone);
			User loginUser = service.login(user);
			//7.判断用户是否激活
			if (loginUser != null && "N".equals(loginUser.getStatus())) {
				//用户尚未激活
				info.setFlag(false);
				info.setErrorMsg("您尚未激活,请登录邮箱激活");
			} else {
				request.getSession().setAttribute("user", user);
			}
		} else {
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

	/**
	 * 刷脸登录功能
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void faceLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取Session
		HttpSession session = request.getSession();
		//创建工具类
		BaiduAiUtil baiduAiUtil = new BaiduAiUtil();
		//实例化PrintWriter对象
		PrintWriter out = response.getWriter();
		//创建 JSONObject
		JSONObject object = new JSONObject();
		//获取前端传输的图片
		String img = request.getParameter("img");
		//对比人脸数据,将结果返回前端
		if (baiduAiUtil.faceCheck(img)) {   //判断是否存在人脸
			String userId = baiduAiUtil.faceSearch(img);
			if (userId != null) {
				User user = new User();
				user.setUid(Integer.parseInt(userId));
				//UserService service = new UserServiceImpl();
				User loginUser = service.faceLogin(user);
				//存储数据到Session
				session.setAttribute("user", loginUser);
				//登录成功
				object.put("FaceCheck_msg", "1");
				out.print(object.toString());
			} else {
				//登录失败
				object.put("FaceCheck_msg", "2");
				out.print(object.toString());
			}
		} else {
			object.put("FaceCheck_msg", "3");
			out.print(object.toString());
		}

	}


}
