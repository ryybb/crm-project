package com.rongyu.crm.workbench.mapper;

import com.rongyu.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Tue Jul 12 21:30:26 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Tue Jul 12 21:30:26 CST 2022
     */
    int insertSelective(Activity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Tue Jul 12 21:30:26 CST 2022
     */
    Activity selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Tue Jul 12 21:30:26 CST 2022
     */
    int updateByPrimaryKeySelective(Activity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity
     *
     * @mbggenerated Tue Jul 12 21:30:26 CST 2022
     */
    int updateByPrimaryKey(Activity record);

    /**
     * 新增市场活动
     * @mbggenerated Tue Jul 12 21:30:26 CST 2022
     */
    int insertActivity(Activity activity);

    /**
     * 查询市场活动列表
     * @param map
     * @return
     */
    List<Activity> selectActivityByConditionForPage(Map<String,Object> map);

    /**
     * 查询市场活动记录条数
     * @param map
     * @return
     */
    int selectCountOfActivityByCondition(Map<String,Object> map);

    /**
     * 删除市场活动
     * @param ids
     * @return
     */
    int deleteActivityByIds(String[] ids);

    /**
     * 根据id查询市场活动
     * @param id
     * @return
     */
    Activity selectActivityById(String id);

    /**
     * 修改市场活动
     * @param activity
     * @return
     */
    int updateActivity(Activity activity);

    /**
     * 查询所有市场活动
     * @return
     */
    List<Activity> selectAllActivities();

    /**
     * 有选择的查询市场活动
     * @param ids
     * @return
     */
    List<Activity> selectChooseActivityByIds(String[] ids);

    /**
     * 新增多条市场活动
     * @param activityList
     * @return
     */
    int insertActivityByList(List<Activity> activityList);

    /**
     * 根据id查询市场活动的详细内容
     * @param id
     * @return
     */
    Activity selectActivityForDetailById(String id);
}