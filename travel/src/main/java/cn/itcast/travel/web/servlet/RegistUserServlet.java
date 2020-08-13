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
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;

/**
 * @program: travel
 * @auther: MuGe
 * @date: 2019/8/31
 * @time: 10:34
 * @description:
 */

/**
 * 用户注册
 */
@WebServlet("/registUserServlet")
public class RegistUserServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//验证码校验
		//获取
		String check = request.getParameter("check");
		HttpSession session = request.getSession();
		String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVLET");
		//防止验证码复用
		session.removeAttribute("CHECKCODE_SERVLET");
		//校验
		if (checkcode_server==null || !checkcode_server.equalsIgnoreCase(check)){
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
		UserService service = new UserServiceImpl();
		boolean flag=service.regist(user);
		ResultInfo info = new ResultInfo();
		//4.响应结果
		if (flag){
			//注册成功
			info.setFlag(flag);
		}else {
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
}
