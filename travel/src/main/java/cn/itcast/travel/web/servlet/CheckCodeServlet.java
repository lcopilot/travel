package cn.itcast.travel.web.servlet;


import cn.itcast.travel.util.VerifyCode;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @program: Itcast
 * @auther: MuGe
 * @date: 2019/8/11
 * @time: 21:07
 * @description:
 */

/**
 * 验证码
 */
@WebServlet("/checkCode")
public class CheckCodeServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//服务器通知浏览器不要缓存
		response.setHeader("pragma","no-cache");
		response.setHeader("cache-control","no-cache");
		response.setHeader("expires","0");

		int width=100;
		int height=38;

		BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
		String randomText = VerifyCode.drawRandomText(width, height, image);
		//将验证码存入Session中
		//ImageIO.write(image,"jpg" ,response.getOutputStream() );
		request.getSession().setAttribute("CHECKCODE_SERVLET", randomText);
		response.setContentType("image/png");//必须设置响应内容类型为图片，否则前台不识别
		OutputStream os = response.getOutputStream(); //获取文件输出流
		ImageIO.write(image,"PNG",os);


	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request,response );
	}

}



