package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

/**
 * @program: travel
 * @auther: MuGe
 * @date: 2019/9/6
 * @time: 8:50
 * @description:
 */
public class FavoriteServiceImpl implements FavoriteService {

	private FavoriteDao favoriteDao = new FavoriteDaoImpl();

	@Override
	public boolean isFavorite(String rid, int uid) {
		Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);
		return favorite != null; //如果对象有值,则为true,反之则为false
	}

	@Override
	public void addAndCancel(String rid, int uid) {
		favoriteDao.addAndCancel(Integer.parseInt(rid), uid);
	}
}
