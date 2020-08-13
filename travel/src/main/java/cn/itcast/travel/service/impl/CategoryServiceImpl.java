package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisPoolUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

/**
 * @program: travel
 * @auther: MuGe
 * @date: 2019/9/2
 * @time: 10:44
 * @description:
 */
public class CategoryServiceImpl implements CategoryService {

	private CategoryDao categoryDao = new CategoryDaoImpl();

	@Override
	public List<Category> findAll() {
		//1.从redis中查询
		//1.1获取jedis客户端
		Jedis jedis = JedisPoolUtils.getJedis();
		//1.2使用sortedset排序查询
		//Set<String> categorys = jedis.zrange("category", 0, -1);
		//1.3查询sortedset中的分数(cid)和值(cname)
		Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);

		List<Category> cs = null;
		//2.判断查询的集合是否为空
		if (categorys == null || categorys.size() == 0) {
			//3.如果为空,需要从数据库查询,再将数据存入redis
			//3.1从数据库查询
			cs = categoryDao.findAll();
			//3.2将集合数据存储到redis
			for (int i = 0; i < cs.size(); i++) {
				jedis.zadd("category", cs.get(i).getCid(), cs.get(i).getCname());
			}
		}else {
			//4.如果不为空,将set的数据存入list 转换
			cs=new ArrayList<Category>();
			for (Tuple tuple : categorys) {
				Category category = new Category();
				category.setCname(tuple.getElement());
				category.setCid((int) tuple.getScore());
				cs.add(category);
			}
		}

		return cs;
	}
}
