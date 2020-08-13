package cn.itcast.travel.util;

/**
 * @program: Itcast
 * @auther: MuGe
 * @date: 2019/8/28
 * @time: 12:15
 * @description:
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * JedisPool工具类 加载配置文件,配置连接池的参数 提供获取连接的方法
 */
public class JedisPoolUtils {

	private static JedisPool jedisPool;

	static {
		//读取配置文件
		InputStream is = JedisPoolUtils.class.getClassLoader()
				.getResourceAsStream("jedis.properties");
		//创建Properties对象
		Properties pro = new Properties();
		//关联文件
		try {
			pro.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//获取数据,设置到JedisPoolConfig
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(Integer.parseInt(pro.getProperty("maxTotal")));
		config.setMaxIdle(Integer.parseInt(pro.getProperty("maxIdle")));

		//初始化jedisPool
		jedisPool=new JedisPool(config,pro.getProperty("host"),Integer.parseInt(pro.getProperty("port")));
	}

	/**
	 * 获取链接方法
	 *
	 * @return
	 */
	public static Jedis getJedis() {
		return jedisPool.getResource();
	}


}
