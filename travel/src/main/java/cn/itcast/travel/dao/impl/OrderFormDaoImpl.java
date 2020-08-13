package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.OrderFormDao;
import cn.itcast.travel.util.JDBCUtils;
import java.util.Date;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @program: travel
 * @auther: MuGe
 * @date: 2019/9/7
 * @time: 18:08
 * @description:
 */
public class OrderFormDaoImpl implements OrderFormDao {
	private JdbcTemplate jdbcTempla=new JdbcTemplate(JDBCUtils.getDataSource());
	@Override
	public void createOrder(String sNow, int uid, int rid) {
		String sql="INSERT INTO tab_orderform VALUES(?,?,?,?,?)";
		jdbcTempla.update(sql, uid,rid,sNow,new Date(),"N");
	}
}
