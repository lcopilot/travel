package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteDaoImpl implements RouteDao {

	private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

	@Override
	public int findTotalCount(int cid, String rname) {
		//String sql = "select count(*) from tab_route where cid = ?";
		//1.定义sql模板
		String sql = "select count(*) from tab_route where 1=1 ";
		StringBuilder sb = new StringBuilder(sql);

		List params = new ArrayList();//条件们
		//2.判断参数是否有值
		if (cid != 0) {
			sb.append(" and cid = ? ");

			params.add(cid);//添加？对应的值
		}

		if (rname != null && rname.length() > 0) {
			sb.append(" and rname like ? ");

			params.add("%" + rname + "%");
		}

		sql = sb.toString();

		return template.queryForObject(sql, Integer.class, params.toArray());
	}

	@Override
	public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
		//String sql = "select * from tab_route where cid = ? and rname like ?  limit ? , ?";
		String sql = " select * from tab_route where 1 = 1 ";
		//1.定义sql模板
		StringBuilder sb = new StringBuilder(sql);

		List params = new ArrayList();//条件们
		//2.判断参数是否有值
		if (cid != 0) {
			sb.append(" and cid = ? ");

			params.add(cid);//添加？对应的值
		}
		if (rname != null && rname.length() > 0) {
			sb.append(" and rname like ? ");
			params.add("%" + rname + "%");
		}
		sb.append(" limit ? , ? ");//分页条件

		sql = sb.toString();

		params.add(start);
		params.add(pageSize);

		return template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
	}

	@Override
	public Route findOne(int rid) {
		String sql = "SELECT*FROM tab_route WHERE rid=?";
		return template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
	}

	@Override
	public int findMyFavoriteTotalCount(int uid) {
		String sql="select count(*) from tab_favorite where uid=?";
		return template.queryForObject(sql, Integer.class,uid);
	}

	@Override
	public List<Route> findMyFavoriteByPage(int uid, int start, int pageSize) {
		//定义sql
		String sql = " select rid from tab_favorite where uid=? limit ? , ?";
		String sql1 = "select * from tab_route where rid=? ";
		List<Map<String, Object>> list= template.queryForList(sql, 1,start,pageSize);
		List<Route> query=new ArrayList<Route>();
		for (Map<String, Object> stringObjectMap : list) {
			for (Object value : stringObjectMap.values()) {
				query.add(template.queryForObject(sql1, new BeanPropertyRowMapper<Route>(Route.class),value));
			}
		}

		return query;

	}

}