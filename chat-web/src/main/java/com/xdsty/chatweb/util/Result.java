package com.xdsty.chatweb.util;

/**
 * description
 *
 * @author 张富华 (fuhua.zhang@ucarinc.com)
 * @version 1.0
 * @date 2019/11/5 11:09
 */
public class Result<T> {

    public static final Integer OK = 0;

    public static final Integer FAILURE = -100;

    public static final Integer NO_AUTHORITY = -200;

    private int code;

    private String msg;

    private int count;

    private T data;

    private Result(int code, String message, int count, T data){
        this.count = count;
        this.data = data;
        this.code = code;
        this.msg = message;
    }

    private Result(int code, String message, T data){
        this.data = data;
        this.code = code;
        this.msg = message;
    }

    public boolean isSuccess(){
        return this.code == OK;
    }


    /**
     * 返回list数据
     * @param total 总数
     * @param data 数据
     * @param <T>
     * @return
     */
    public static <T> Result<T> createSuccessResult(int total, T data){
        return new Result<T>(OK, "", total, data);
    }

    public static <T> Result<T> createSuccessResult(){
        return new Result<T>(OK, "", null);
    }

    public static <T> Result<T> createByFailure(String msg){
        return new Result<T>(FAILURE, msg, null);
    }

    public static <T> Result<T> createByFailure(T data){
        return new Result<T>(OK, "", data);
    }

    public static <T> Result<T> createByFailure(){
        return new Result<T>(FAILURE, "操作失败，请重试", null);
    }

    public static <T> Result<T> createSuccessResult(T data){
        return new Result<>(OK, "", data);
    }


    public static <T> Result<T> createNoAuthorityResult(){
        return new Result<>(NO_AUTHORITY, "没有权限", null);
    }


    public static Integer getOK() {
        return OK;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
