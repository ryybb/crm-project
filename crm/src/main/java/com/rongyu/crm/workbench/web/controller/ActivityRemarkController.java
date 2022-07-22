package com.rongyu.crm.workbench.web.controller;

import com.rongyu.crm.commons.contants.Constant;
import com.rongyu.crm.commons.domain.ReturnObj;
import com.rongyu.crm.commons.utils.DateUtil;
import com.rongyu.crm.commons.utils.UUIDUtil;
import com.rongyu.crm.settings.domain.User;
import com.rongyu.crm.workbench.domain.ActivityRemark;
import com.rongyu.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class ActivityRemarkController {

    @Autowired
    private ActivityRemarkService activityRemarkService;

    @ResponseBody
    @RequestMapping("/workbench/activity/saveCreateActivityRemark.do")
    public Object saveCreateActivityRemark(ActivityRemark activityRemark, HttpSession session){
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        ReturnObj returnObj = new ReturnObj();
        //封装参数
        activityRemark.setId(UUIDUtil.getUUID());
        activityRemark.setCreateTime(DateUtil.formatDateStr(new Date()));
        activityRemark.setCreateBy(user.getId());
        activityRemark.setEditFlag(Constant.EDIT_FLAG_NO_EDIT);
        //保存备注
        try{
            int ret = activityRemarkService.saveCreateActivityRemark(activityRemark);
            if (ret>0){
                returnObj.setCode(Constant.CODE_SUCCESS);
                returnObj.setRetData(activityRemark);
            }else {
                returnObj.setCode(Constant.CODE_FAILURE);
                returnObj.setMessage("系统繁忙，请稍后再试！");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObj.setCode(Constant.CODE_FAILURE);
            returnObj.setMessage("系统繁忙，请稍后再试！");
        }
        return returnObj;
    }

    @ResponseBody
    @RequestMapping("/workbench/activity/saveEditActivityRemark.do")
    public Object saveEditActivityRemark(ActivityRemark activityRemark,HttpSession session){
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        ReturnObj returnObj = new ReturnObj();
        //封装参数
        activityRemark.setEditTime(DateUtil.formatDateStr(new Date()));
        activityRemark.setEditBy(user.getId());
        activityRemark.setEditFlag(Constant.EDIT_FLAG_YES_EDIT);
        //保存修改的备注
        try {
            int ret = activityRemarkService.saveEditActivityRemark(activityRemark);
            if (ret>0){
                returnObj.setCode(Constant.CODE_SUCCESS);
                returnObj.setRetData(activityRemark);
            }else {
                returnObj.setCode(Constant.CODE_FAILURE);
                returnObj.setMessage("系统繁忙，请稍后再试！");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObj.setCode(Constant.CODE_FAILURE);
            returnObj.setMessage("系统繁忙，请稍后再试！");
        }
        return returnObj;
    }

    @ResponseBody
    @RequestMapping("/workbench/activity/deleteActivityRemarkById.do")
    public Object deleteActivityRemarkById(String id){
        ReturnObj returnObj = new ReturnObj();
        try {
            int ret = activityRemarkService.deleteActivityRemarkById(id);
            if (ret>0){
                returnObj.setCode(Constant.CODE_SUCCESS);
            }else {
                returnObj.setCode(Constant.CODE_FAILURE);
                returnObj.setMessage("系统繁忙，请稍后再试！");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObj.setCode(Constant.CODE_FAILURE);
            returnObj.setMessage("系统繁忙，请稍后再试！");
        }
        return returnObj;
    }
}
