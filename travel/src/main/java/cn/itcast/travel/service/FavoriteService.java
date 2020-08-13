package cn.itcast.travel.service;

/**
 * @program: travel
 * @auther: MuGe
 * @date: 2019/9/6
 * @time: 8:49
 * @description:
 */
public interface FavoriteService {

	/**
	 * 判断是否收藏
	 * @param rid
	 * @param uid
	 * @return
	 */
	public boolean isFavorite(String rid,int uid);

	/**
	 * 添加取消收藏
	 * @param rid
	 * @param uid
	 */
	void addAndCancel(String rid, int uid);
}
