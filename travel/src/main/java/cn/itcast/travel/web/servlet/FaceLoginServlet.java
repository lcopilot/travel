package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import cn.itcast.travel.util.BaiduAiUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

/**
 * @program: Itcast
 * @auther: MuGe
 * @date: 2019/8/17
 * @time: 13:14
 * @description:
 */
@WebServlet("/FaceLoginServlet")
public class FaceLoginServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
				UserService service = new UserServiceImpl();
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}


}
