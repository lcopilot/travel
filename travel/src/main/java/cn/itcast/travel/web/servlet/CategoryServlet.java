package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: travel
 * @auther: MuGe
 * @date: 2019/9/2
 * @time: 10:31
 * @description:
 */
@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {

	private CategoryService service = new CategoryServiceImpl();

	public void findAll(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		//1.调用service查询所有
		List<Category> cs = service.findAll();
		//2.序列化json返回
		/*ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json;charset=utf-8");
		mapper.writeValue(response.getOutputStream(), cs);*/
		writeValue(cs, response);

	}


}
