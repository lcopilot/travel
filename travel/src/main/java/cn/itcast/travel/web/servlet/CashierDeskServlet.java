package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.OrderFormService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.OrderFormServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: travel
 * @auther: MuGe
 * @date: 2019/9/7
 * @time: 9:20
 * @description:
 */
@WebServlet("/cashierDesk/*")
public class CashierDeskServlet extends BaseServlet {
	private OrderFormService orderFormService=new OrderFormServiceImpl();
	private RouteService routeService = new RouteServiceImpl();
	public void payment(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取rid
		Object user = request.getSession().getAttribute("user");
		String rid = request.getParameter("rid");
		//2.调用service查询route对象
		Route route = routeService.findOne(rid);
		request.getSession().setAttribute("route", route);
		boolean flag=false;
		if (user!=null && rid!=null){
			//用户已登录
			flag=true;
		}else {
			//用户没有登录

		}
		writeValue(flag, response);
	}

	public void createOrder(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取订单号
		String sNow = request.getParameter("sNow");
		User user = (User)request.getSession().getAttribute("user");
		Route route = (Route)request.getSession().getAttribute("route");
		//生成订单
		orderFormService.createOrder(sNow,user.getUid(),route.getRid() );
	}

}
