package com.test.testboot.common;

import io.swagger.v3.oas.annotations.media.Schema;

//统一设置包装类，返回数据的类型
@Schema(description = "响应返回数据对象")
public class Result {
    //状态码
    @Schema(
            title = "code",
            description = "响应码",
            format = "int32",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer code;
    //消息
    @Schema(
            title = "msg",
            description = "响应信息",
            accessMode = Schema.AccessMode.READ_ONLY,
            example = "成功或失败",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String message;
    //数据
    @Schema(title = "data", description = "响应数据", accessMode = Schema.AccessMode.READ_ONLY)
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
