package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;
import java.util.List;

/**
 * @program: travel
 * @auther: MuGe
 * @date: 2019/9/2
 * @time: 10:44
 * @description:
 */
public interface CategoryService {
	public List<Category> findAll();
}
