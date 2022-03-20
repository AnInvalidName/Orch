package com.orch.core.service.thread;

public class ExecutionException extends RuntimeException{
    public ExecutionException(){
        super();
    }

    public ExecutionException(String msg){
        super(msg);
    }
}
