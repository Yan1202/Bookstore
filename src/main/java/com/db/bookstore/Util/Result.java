package com.db.bookstore.Util;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {

    private int code;
    private long cost;
    private Object data;

    public Result(){
        super();
    }

    public Result(int code,long cost,Object data){
        this.code=code;
        this.cost=cost;
        this.data=data;
    }

    @Override
    public String toString(){

        return "{ \"code\": "+code+
                ", \"msg\": \""+cost+"\""+
                ", \"data\": "+data+" }";
    }



}
