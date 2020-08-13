package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import java.util.Date;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @program: travel
 * @auther: MuGe
 * @date: 2019/9/6
 * @time: 8:54
 * @description:
 */
public class FavoriteDaoImpl implements FavoriteDao {

	private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

	@Override
	public Favorite findByRidAndUid(int rid, int uid) {
		Favorite favorite = null;
		try {
			String sql = "SELECT*FROM tab_favorite WHERE rid=? AND uid=? ";
			favorite = template
					.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid,
							uid);
		} catch (DataAccessException e) {

		}

		return favorite;
	}

	@Override
	public int findCountByRid(int rid) {
		String sql = "SELECT COUNT(*) FROM tab_favorite WHERE rid=? ";
		return template.queryForObject(sql, Integer.class,rid);
	}

	@Override
	public void addAndCancel(int rid, int uid) {
		Favorite favorite = findByRidAndUid(rid, uid);
		if(favorite!=null){
			String sql="DELETE FROM tab_favorite WHERE rid=? AND uid=?";
			template.update(sql, rid,uid);
		}else {
			String sql="INSERT INTO tab_favorite VALUES(?,?,?)";
			template.update(sql, rid,new Date(),uid);
		}

	}
}
