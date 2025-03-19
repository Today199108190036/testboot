package com.test.testboot.common;

//统一设置包装类，返回数据的类型
public class Result {
    //状态码
    private Integer code;
    //消息
    private String message;
    //数据
    private Object data;

    public static Result success(){
        Result result = new Result();
        result.setCode(200);
        result.setMessage("请求成功");
        return result;
    }

    public static Result success(Object data){
        Result result = success();
        result.setData(data);
        return result;
    }

    public static Result error(){
        Result result = new Result();
        result.setCode(500);
        result.setMessage("请求失败");
        return result;
    }

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + (data != null ? data.toString() : "null") +
                '}';
    }
}
