package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: travel
 * @auther: MuGe
 * @date: 2019/9/1
 * @time: 11:47
 * @description:
 */

/**
 * 查找用户
 */
@WebServlet("/findUserServlet")
public class FindUserServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		//1.从session中获取登录用户
		Object user = request.getSession().getAttribute("user");

		//2.响应数据
		//2.1.将info对象序列化为json
		ObjectMapper mapper = new ObjectMapper();
		//2.2.将json数据写回客户端
		//设置content-type
		response.setContentType("application/json;charset=utf-8");
		mapper.writeValue(response.getOutputStream(), user);

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
}
