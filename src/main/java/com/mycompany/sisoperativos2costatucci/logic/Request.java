/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sisoperativos2costatucci.logic;

/**
 *
 * @author astv06
 */
public class Request {
    private int pos;
    private String op;
    private Request nextRequest;

    public Request(int pos, String op) {
        this.pos = pos;
        this.op = op;
    }

    public int getPos() {
        return pos;
    }

    public String getOp() {
        return op;
    }

    public Request getNextRequest() {
        return nextRequest;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public void setNextRequest(Request nextRequest) {
        this.nextRequest = nextRequest;
    }
    
    
}
