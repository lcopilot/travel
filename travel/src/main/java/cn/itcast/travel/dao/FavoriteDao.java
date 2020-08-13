package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

/**
 * @program: travel
 * @auther: MuGe
 * @date: 2019/9/6
 * @time: 8:52
 * @description:
 */
public interface FavoriteDao {

	/**
	 * 根据rid和uid查询收藏信息
	 * @param rid
	 * @param uid
	 * @return
	 */
	public Favorite findByRidAndUid(int rid,int uid);

	/**
	 * 根据rid查询收藏次数
	 * @param rid
	 * @return
	 */
	int findCountByRid(int rid);

	/**
	 * 添加取消收藏
	 * @param rid
	 * @param uid
	 */
	void addAndCancel(int rid, int uid);
}
