package com.rongyu.crm.workbench.web.controller;


import com.rongyu.crm.commons.contants.Constant;
import com.rongyu.crm.commons.utils.exportUtil;
import com.rongyu.crm.commons.domain.ReturnObj;
import com.rongyu.crm.commons.utils.HSSFUtil;
import com.rongyu.crm.commons.utils.DateUtil;
import com.rongyu.crm.commons.utils.UUIDUtil;
import com.rongyu.crm.settings.domain.User;
import com.rongyu.crm.settings.service.UserService;
import com.rongyu.crm.workbench.domain.Activity;
import com.rongyu.crm.workbench.domain.ActivityRemark;
import com.rongyu.crm.workbench.service.ActivityRemarkService;
import com.rongyu.crm.workbench.service.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

@Controller
public class ActivityController {
    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ActivityRemarkService activityRemarkService;

    @RequestMapping("/workbench/activity/index.do")
    public String index(HttpServletRequest request){
        List<User> userList = userService.queryAllUsers();
        request.setAttribute("userList",userList);
        return "workbench/activity/index";
    }

    @ResponseBody
    @RequestMapping("/workbench/activity/saveCreateActivity.do")
    public Object saveCreateActivity(Activity activity, HttpSession session){
        //添加活动的id，创建时间和创建者
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        activity.setId(UUIDUtil.getUUID());
        activity.setCreateTime(DateUtil.formatDateStr(new Date()));
        activity.setCreateBy(user.getId());
        ReturnObj returnObj = new ReturnObj();
        try {
            int ret = activityService.saveCreateActivity(activity);
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

    @ResponseBody
    @RequestMapping("/workbench/activity/queryActivityByConditionForPage.do")
    public Object queryActivityByConditionForPage(String name,String owner,String startDate,String endDate,int pageNo,
                                                  int pageSize){
        //参数封装
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("beginNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        //返回查询市场活动集合和市场活动总记录条数
        List<Activity> activityList = activityService.queryActivityByConditionForPage(map);
        int totalRows = activityService.queryCountOfActivityByCondition(map);
        //封装json对象
        Map<String,Object> retMap = new HashMap<>();
        retMap.put("activityList",activityList);
        retMap.put("totalRows",totalRows);
        return retMap;
    }

    @ResponseBody
    @RequestMapping("/workbench/activity/deleteActivityIds.do")
    public Object deleteActivityIds(String[] id){
        ReturnObj returnObj = new ReturnObj();
        try{
            int ret = activityService.deleteActivityByIds(id);
            if (ret > 0){
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

    @ResponseBody
    @RequestMapping("/workbench/activity/queryActivityById.do")
    public Object queryActivityById(String id){
        Activity activity = activityService.queryActivityById(id);
        return activity;
    }

    @ResponseBody
    @RequestMapping("/workbench/activity/saveEditActivity.do")
    public Object saveEditActivity(Activity activity,HttpSession session){
        //封装参数
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        activity.setEditBy(user.getId());
        activity.setEditTime(DateUtil.formatDateStr(new Date()));
        ReturnObj returnObj = new ReturnObj();
        try{
            int ret = activityService.saveEditActivity(activity);
            if (ret > 0){
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

    @RequestMapping("/workbench/activity/exportAllActivities.do")
    public void exportAllActivities(HttpServletResponse response) throws IOException {
        List<Activity> activityList = activityService.queryAllActivities();
        exportUtil.exportActivity(activityList,response);
    }

    @RequestMapping("/workbench/activity/exportChooseActivities.do")
    public void exportChooseActivities(String[] id,HttpServletResponse response) throws IOException {
        List<Activity> activityList = activityService.queryChooseActivityByIds(id);
        exportUtil.exportActivity(activityList,response);
    }

    @ResponseBody
    @RequestMapping("/workbench/activity/importActivity.do")
    public Object importActivity(MultipartFile activityFile,HttpSession session){
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        ReturnObj returnObj = new ReturnObj();
        try {
            //解析excel文件
            InputStream is = activityFile.getInputStream();
            HSSFWorkbook wb = new HSSFWorkbook(is);
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;
            Activity activity = null;
            List<Activity> activityList = new ArrayList<>();
            for(int i = 1;i<=sheet.getLastRowNum();i++){    //获取当前表的最后一行的下标
                row = sheet.getRow(i);
                activity = new Activity();
                activity.setId(UUIDUtil.getUUID());
                activity.setOwner(user.getId());
                activity.setCreateTime(DateUtil.formatDateStr(new Date()));
                activity.setCreateBy(user.getId());
                for (int j = 0;j<row.getLastCellNum();j++){ //获取当前行的最后一列的下标+1
                    cell = row.getCell(j);
                    //获取指定单元格内容并将其转为字符串类型
                    String cellValue = HSSFUtil.getCellValueForStr(cell);
                    if (j==0){
                        activity.setName(cellValue);
                    }else if (j==1){
                        activity.setStartDate(cellValue);
                    }else if (j==2){
                        activity.setEndDate(cellValue);
                    }else if (j==3){
                        activity.setCost(cellValue);
                    }else if (j==4){
                        activity.setDescription(cellValue);
                    }
                }
                //将每个市场活动保存都到List集合中
                activityList.add(activity);
            }
            //将activityList保存到数据库中
            int ret = activityService.saveImportActivityByList(activityList);
            returnObj.setCode(Constant.CODE_SUCCESS);
            returnObj.setRetData(ret);
        } catch (Exception e) {
            e.printStackTrace();
            returnObj.setCode(Constant.CODE_FAILURE);
            returnObj.setMessage("系统繁忙，请稍后再试！");
        }
        return returnObj;
    }

    @RequestMapping("/workbench/activity/detailActivity.do")
    public String detailActivity(String id,HttpServletRequest request){

        Activity activity = activityService.queryActivityForDetailById(id);
        List<ActivityRemark> activityRemarkList = activityRemarkService.queryActivityRemarkForDetailByActivityId(id);
        //将查询的市场活动和备注内容保存到request作用域中
        request.setAttribute("activity",activity);
        request.setAttribute("remarkList",activityRemarkList);
        return "workbench/activity/detail";

    }
}
