package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

/**
 * @program: travel
 * @auther: MuGe
 * @date: 2019/8/31
 * @time: 10:36
 * @description:
 */
public interface UserService {
	/**
	 * 注册用户
	 * @param user
	 * @return
	 */
	boolean regist(User user);

	boolean active(String code);

	User login(User user);

	User faceLogin(User user);

	User telephone(String telephone);
}
