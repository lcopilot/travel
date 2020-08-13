package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @program: travel
 * @auther: MuGe
 * @date: 2019/9/2
 * @time: 10:39
 * @description:
 */
public class CategoryDaoImpl implements CategoryDao {

	private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

	@Override
	public List<Category> findAll() {
		String sql = "SELECT*FROM tab_category";
		return template.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
	}
}
