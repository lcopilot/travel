package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.OrderFormDao;
import cn.itcast.travel.dao.impl.OrderFormDaoImpl;
import cn.itcast.travel.service.OrderFormService;

/**
 * @program: travel
 * @auther: MuGe
 * @date: 2019/9/7
 * @time: 18:03
 * @description:
 */
public class OrderFormServiceImpl implements OrderFormService {
	OrderFormDao orderFormDao=new OrderFormDaoImpl();
	@Override
	public void createOrder(String sNow, int uid, int rid) {
		orderFormDao.createOrder(sNow, uid, rid);
	}
}
