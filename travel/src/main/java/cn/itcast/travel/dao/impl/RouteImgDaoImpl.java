package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @program: travel
 * @auther: MuGe
 * @date: 2019/9/4
 * @time: 13:24
 * @description:
 */
public class RouteImgDaoImpl implements RouteImgDao {
	private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
	@Override
	public List<RouteImg> findBtRid(int rid) {
		String sql="SELECT*FROM tab_route_img WHERE rid=?";
		return  template.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class),rid);
	}

}
