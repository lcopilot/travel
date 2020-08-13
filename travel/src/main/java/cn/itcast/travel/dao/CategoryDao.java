package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;
import java.util.List;

/**
 * @program: travel
 * @auther: MuGe
 * @date: 2019/9/2
 * @time: 10:37
 * @description:
 */
public interface CategoryDao {

	/**
	 * 查询所有
	 * @return
	 */
	public List<Category> findAll();
}
