package cn.itcast.travel.web.servlet;

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
 * @time: 12:05
 * @description:
 */

/**
 * 用户退出
 */
@WebServlet("/exitServlet")
public class ExitServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		//1.销毁session
		request.getSession().invalidate();

		//2.跳转页面
		response.sendRedirect(request.getContextPath()+"/login.html");

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
}
