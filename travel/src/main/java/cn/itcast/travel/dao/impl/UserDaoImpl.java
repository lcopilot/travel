package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import com.alibaba.druid.util.JdbcUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @program: travel
 * @auther: MuGe
 * @date: 2019/8/31
 * @time: 10:38
 * @description:
 */
public class UserDaoImpl implements UserDao {

	private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

	/**
	 * 根据用户名查询user
	 *
	 * @param username
	 * @return
	 */
	@Override
	public User findByUsername(String username) {
		User user = null;
		try {
			//1.定义sql
			String sql = "SELECT*FROM tab_user WHERE username=?";
			//2.执行
			user = template
					.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
		} catch (DataAccessException e) {

		}

		return user;
	}

	/**
	 * 保存用户信息  注册
	 *
	 * @param user
	 */
	@Override
	public void save(User user) {
		//1.定义sql
		String sql = "INSERT INTO tab_user(username,password,name,birthday,sex,telephone,email,status,code) VALUES(?,?,?,?,?,?,?,?,?)";
		//执行
		template.update(sql, user.getUsername(), user.getPassword(), user.getName(),
				user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(),
				user.getStatus(), user.getCode());

	}

	/**
	 * 根据激活码查询用户对象
	 *
	 * @param code
	 * @return
	 */
	@Override
	public User findByCode(String code) {
		User user = null;
		try {
			String sql = "SELECT*FROM tab_user WHERE code=?";
			user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return user;
	}

	/**
	 * 修改指定用户激活状态
	 *
	 * @param user
	 */
	@Override
	public void updateStatus(User user) {
		String sql = "UPDATE tab_user SET status=? WHERE uid=?";
		template.update(sql, "Y", user.getUid());
	}

	/**
	 * 根据用户名和密码查询
	 *
	 * @param username
	 * @param password
	 * @return
	 */
	@Override
	public User findByUsernameAndPassword(String username, String password) {
		User user = null;
		try {
			//1.定义sql
			String sql = "SELECT*FROM tab_user WHERE username=? AND password=?";
			//2.执行
			user = template
					.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username,
							password);
		} catch (DataAccessException e) {

		}

		return user;
	}

	/**
	 * 根据id查询
	 * @param uid
	 * @return
	 */
	@Override
	public User findUserById(int uid) {
		User user = null;
		try {
			String sql = "SELECT * FROM tab_user WHERE uid = ?";
			user = template
					.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), uid);
		} catch (Exception e) {

		}
		return user;
	}

	/**
	 * 根据手机号查询
	 * @param telephone
	 * @return
	 */
	@Override
	public User findByTelephone(String telephone) {
		User user = null;
		try {
			String sql = "SELECT * FROM tab_user WHERE telephone = ?";
			user = template
					.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), telephone);
		} catch (Exception e) {

		}
		return user;
	}
}
