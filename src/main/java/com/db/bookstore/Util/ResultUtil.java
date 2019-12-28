package com.db.bookstore.Util;

public class ResultUtil {



    public static Result success(Object obj,long cost){

        Result result=new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setCost(cost);
        result.setData(obj);

        return result;
    }

    public static Result success(Object obj){
        return ResultUtil.success(obj,-1);
    }

    public static Result success(long cost){
        return ResultUtil.success(null,cost);
    }

    public static Result success(){

        return ResultUtil.success(null);
    }



    public static Result error(ResultEnum e,long cost){

        Result result=new Result();
        result.setCode(e.getCode());
        result.setCost(cost);
        return result;

    }

    public static Result error(ResultEnum e){
        return ResultUtil.error(e,-1);
    }

}
