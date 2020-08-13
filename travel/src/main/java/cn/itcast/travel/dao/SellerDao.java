package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

/**
 * @program: travel
 * @auther: MuGe
 * @date: 2019/9/4
 * @time: 13:37
 * @description:
 */
public interface SellerDao {

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Seller findById(int id);
}
