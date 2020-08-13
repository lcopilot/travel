package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import java.util.List;

/**
 * @program: travel
 * @auther: MuGe
 * @date: 2019/9/4
 * @time: 13:21
 * @description:
 */
public interface RouteImgDao {

	/**
	 * 根据route的id查询图片
	 * @param rid
	 * @return
	 */
	public List<RouteImg> findBtRid(int rid);

}
