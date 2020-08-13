package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {

    /**
     * 根据cid查询总记录数
     */
    public int findTotalCount(int cid,String rname);

    /**
     * 根据cid，start,pageSize查询当前页的数据集合
     */
    public List<Route> findByPage(int cid, int start, int pageSize,String rname);

    /**
     * 根据id查询
     * @param rid
     * @return
     */
    public Route findOne(int rid);

    /**
     * 根据uid查询
     * @param uid
     * @return
     */
    int findMyFavoriteTotalCount(int uid);

    /**
     * 根据cid，start,pageSize查询当前页的数据集合
     * @param uid
     * @param start
     * @param pageSize
     * @return
     */
    List<Route> findMyFavoriteByPage(int uid, int start, int pageSize);
}
