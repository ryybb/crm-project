package com.rongyu.crm.workbench.service;

import com.rongyu.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkService {

    List<ActivityRemark> queryActivityRemarkForDetailByActivityId(String activityId);

    int saveCreateActivityRemark(ActivityRemark activityRemark);

    int saveEditActivityRemark(ActivityRemark activityRemark);

    int deleteActivityRemarkById(String id);
}
