package com.rongyu.crm.settings.web.controller;

import com.rongyu.crm.commons.contants.Constant;
import com.rongyu.crm.commons.domain.ReturnObj;
import com.rongyu.crm.commons.utils.DateUtil;
import com.rongyu.crm.settings.domain.User;
import com.rongyu.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin(){

        return "settings/qx/user/login";
    }
    @ResponseBody
    @RequestMapping("/settings/qx/user/login.do")
    public Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request, HttpSession session, HttpServletResponse response){
        //将账号密码封装到map集合
        Map<String,Object> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        //new出json对象
        ReturnObj returnObj = new ReturnObj();
        //查询得到用户
        User user = userService.queryUserByLoginActAndLoginPwd(map);
        //验证用户
        if (user==null){
            //登陆失败
            returnObj.setCode(Constant.CODE_FAILURE);
            returnObj.setMessage("账号或密码错误！");
        }else {
            //判断用户是否过期
            String expireTime = user.getExpireTime();
            if (DateUtil.formatDateStr(new Date()).compareTo(expireTime) > 0){
                //登陆失败，账号过期
                returnObj.setCode(Constant.CODE_FAILURE);
                returnObj.setMessage("账号已过期！");
            }else if("0".equals(user.getLockState())){
                //登陆失败，用户被锁定
                returnObj.setCode(Constant.CODE_FAILURE);
                returnObj.setMessage("账号被锁定！！");
            }else if(!user.getAllowIps().contains(request.getRemoteAddr())){
                //登录失败，ip受限
                returnObj.setCode(Constant.CODE_FAILURE);
                returnObj.setMessage("非法ip！");
            }else {
                //登陆成功
                returnObj.setCode(Constant.CODE_SUCCESS);
                //将登陆成功的用户信息保存到session作用域中
                session.setAttribute(Constant.SESSION_USER,user);
                //往前端写cookie，有效期为十天
                if ("true".equals(isRemPwd)){
                    Cookie actCookie = new Cookie("loginAct",user.getLoginAct());
                    actCookie.setMaxAge(10*24*60*60);
                    response.addCookie(actCookie);
                    Cookie pwdCookie = new Cookie("loginPwd",user.getLoginPwd());
                    pwdCookie.setMaxAge(10*24*60*60);
                    response.addCookie(pwdCookie);
                }else {
                    //当用户取消勾选记住密码，清除cookie
                    Cookie actCookie = new Cookie("loginAct","1");
                    actCookie.setMaxAge(0);
                    response.addCookie(actCookie);
                    Cookie pwdCookie = new Cookie("loginPwd","1");
                    pwdCookie.setMaxAge(0);
                    response.addCookie(pwdCookie);
                }
            }
        }

        return returnObj;

    }
    @RequestMapping("settings/qx/user/logout.do")
    public String logout(HttpServletResponse response,HttpSession session){
        //清除cookie
        Cookie actCookie = new Cookie("loginAct","1");
        actCookie.setMaxAge(0);
        response.addCookie(actCookie);
        Cookie pwdCookie = new Cookie("loginPwd","1");
        pwdCookie.setMaxAge(0);
        response.addCookie(pwdCookie);
        //销毁session
        session.invalidate();
        return "redirect:/";
    }
}
