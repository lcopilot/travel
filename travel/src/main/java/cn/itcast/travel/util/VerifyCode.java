package cn.itcast.travel.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @program: Itcast
 * @auther: MuGe
 * @date: 2019/8/12
 * @time: 11:26
 * @description:
 */
public class VerifyCode {
    // 随机颜色
    public static Color getRandomColor() {
        Random r = new Random();
        Color color = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
        return color;
    }

    public static  String drawRandomText(int width, int height, BufferedImage verifyImg) {

        Graphics2D graphics = (Graphics2D)verifyImg.getGraphics();

        graphics.setColor(Color.WHITE);//设置画笔颜色-验证码背景色

        graphics.fillRect(0, 0, width, height);//填充背景
        // //2.2画边框
        //graphics.setColor(Color.gray);
        //graphics.drawRect(0,0 ,width -1,height-1 );
        graphics.setFont(new Font("微软雅黑", Font.BOLD, 25));

        //数字和字母的组合

        String baseNumLetter= "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";

        StringBuffer sBuffer = new StringBuffer();

        int x = 5;  //旋转原点的 x 坐标

        String ch = "";

        Random random = new Random();

        for(int i = 0;i < 4;i++){

            graphics.setColor(getRandomColor());

            //设置字体旋转角度

            int degree = random.nextInt() % 30;  //角度小于30度

            int dot = random.nextInt(baseNumLetter.length());

            ch = baseNumLetter.charAt(dot) + "";

            sBuffer.append(ch);

            //正向旋转

            graphics.rotate(degree * Math.PI / 180, x, 22);

            graphics.drawString(ch, x, 22);

            //反向旋转

            graphics.rotate(-degree * Math.PI / 180, x, 22);

            x += 24;

        }

        //画干扰线

        for (int i = 0; i <3; i++) {

            // 设置随机颜色

            graphics.setColor(getRandomColor());

            // 随机画线

            graphics.drawLine(random.nextInt(width), random.nextInt(height),

                    random.nextInt(width), random.nextInt(height));

        }

        //添加噪点

        for(int i=0;i<15;i++){

            int x1 = random.nextInt(width);

            int y1 = random.nextInt(height);

            graphics.setColor(getRandomColor());

            graphics.fillRect(x1, y1, 2,2);

        }

        return sBuffer.toString();

    }

}
