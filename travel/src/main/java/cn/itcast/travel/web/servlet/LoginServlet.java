package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;

/**
 * @program: travel
 * @auther: MuGe
 * @date: 2019/9/1
 * @time: 10:35
 * @description:
 */

/**
 * 用户登录
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
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
		UserService service = new UserServiceImpl();
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

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
}
