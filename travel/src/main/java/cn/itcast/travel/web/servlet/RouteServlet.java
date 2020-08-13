package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

	private RouteService routeService = new RouteServiceImpl();
	private FavoriteService favoriteService = new FavoriteServiceImpl();

	/**
	 * 分页查询
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void pageQuery(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.接受参数
		String currentPageStr = request.getParameter("currentPage");
		String pageSizeStr = request.getParameter("pageSize");    //每页显示条数
		String cidStr = request.getParameter("cid");

		//接受rname线路名称
		String ranme = request.getParameter("rname");
		//重新编码
		if (ranme.equals("null")) {
			ranme = null;
		} else {
			ranme = new String(ranme.getBytes("iso-8859-1"), "utf-8");
		}
		int cid = 0; //类别id
		//2.处理参数
		if (cidStr != null && cidStr.length() > 0 && !cidStr.equals("null")) {
			cid = Integer.parseInt(cidStr);
		}
		int currentPage = 0;//当前页码，如果不传递，则默认为第一页
		if (currentPageStr != null && currentPageStr.length() > 0) {
			currentPage = Integer.parseInt(currentPageStr);
		} else {
			currentPage = 1;
		}

		int pageSize = 0;//每页显示条数，如果不传递，默认每页显示5条记录
		if (pageSizeStr != null && pageSizeStr.length() > 0) {
			pageSize = Integer.parseInt(pageSizeStr);
		} else {
			pageSize = 5;
		}

		//3. 调用service查询PageBean对象
		PageBean<Route> pb = routeService.pageQuery(cid, currentPage, pageSize, ranme);

		//4. 将pageBean对象序列化为json，返回
		writeValue(pb, response);

	}

	/**
	 * 根据id查询一个旅游线路的详细信息
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void findOne(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.接收参数id
		String rid = request.getParameter("rid");
		//2.调用service查询route对象
		Route route = routeService.findOne(rid);
		//3.转为json写回客户端
		writeValue(route, response);
	}

	/**
	 * 判断用户是否收藏过该线路
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void isFavorite(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取线路id
		String rid = request.getParameter("rid");

		//2.获取当前登录的user
		User user = (User) request.getSession().getAttribute("user");
		int uid;
		if (user == null) {
			//用户尚未登录
			uid = 0;
		} else {
			//用户已经登录
			uid = user.getUid();
		}

		//3.调用service
		boolean flag = favoriteService.isFavorite(rid, uid);

		//4.写回客户端
		writeValue(flag, response);
	}

	/**
	 * 添加收藏
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void addAndCancelFavorite(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取rid
		String rid = request.getParameter("rid");

		//2.获取当前登录的user
		User user = (User) request.getSession().getAttribute("user");
		int uid;    //用户id
		if (user == null) {
			//用户尚未登录
			return;
		} else {
			//用户已经登录
			uid = user.getUid();
		}

		//3.调用service添加
		favoriteService.addAndCancel(rid, uid);
	}

	public void myFavorite(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取参数
		//1.1获取uid
		User user = (User) request.getSession().getAttribute("user");
		//1.2获取页码
		String currentPageStr = request.getParameter("currentPage");
		String pageSizeStr = request.getParameter("pageSize");    //每页显示条数
		//处理参数
		//当前页码，如果不传递，则默认为第一页
		int currentPage =0;
		if (currentPageStr != null && currentPageStr.length() > 0) {
			currentPage = Integer.parseInt(currentPageStr);
		} else {
			currentPage = 1;
		}

		int pageSize = 0;//每页显示条数，如果不传递，默认每页显示12条记录
		if (pageSizeStr != null && pageSizeStr.length() > 0) {
			pageSize = Integer.parseInt(pageSizeStr);
		} else {
			pageSize = 12;
		}

		//2.调用service查询
		PageBean<Route> pb=routeService.myFavorite(user.getUid(),currentPage,pageSize);
		//4. 将pageBean对象序列化为json，返回
		writeValue(pb, response);
	}
}
