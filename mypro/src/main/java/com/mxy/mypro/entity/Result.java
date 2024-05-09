package com.mxy.mypro.entity;


import com.mxy.mypro.enums.ResponseEnum;

import java.util.HashMap;

import java.util.Map;


public class Result {

    private Integer code; // 返回状态码

    private String message;// 返回信息

    private Map<String, Object> data = new HashMap(); // 返回数据

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    /**

     * 构造器私有

     */

    private Result(){}

    /**

     * 返回成功

     */

    public static Result ok(String message){

        Result r = new Result();

        r.setCode(ResponseEnum.SUCCESS.getCode());

        r.setMessage(message);

        return r;

    }

    /**

     * 返回失败

     */

    public static Result error(String message){

        Result r = new Result();

        r.setCode(ResponseEnum.ERROR.getCode());

        r.setMessage(message);

        return r;

    }

    /**

     * 通过枚举类设置特定结果

     */

    public static Result setResult(ResponseEnum responseEnum){

        Result r = new Result();

        r.setCode(responseEnum.getCode());

        r.setMessage(responseEnum.getMessage());

        return r;

    }

    public Result message(String message){

        this.setMessage(message);

        return this;

    }

    public Result code(Integer code){

        this.setCode(code);

        return this;

    }

    public Result data(String key, Object value){

        this.data.put(key, value);

        return this;

    }

    public Result data(Map<String, Object> map){

        this.setData(map);

        return this;

    }

}