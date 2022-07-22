package com.rongyu.crm.commons.domain;

public class ReturnObj {
    private String code;//传递成功信息
    private String message;//传递失败信息
    private Object retData;//其他数据

    public ReturnObj() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRetData() {
        return retData;
    }

    public void setRetData(Object retData) {
        this.retData = retData;
    }
}
