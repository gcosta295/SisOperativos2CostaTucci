/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sisoperativos2costatucci.logic;

/**
 *
 * @author astv06
 */
public class Block {
    private int id;
    private int size;
    private String info;
    private Block next;

    public void setSize(int size) {
        this.size = size;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setNext(Block next) {
        this.next = next;
    }

    public int getSize() {
        return size;
    }

    public String getInfo() {
        return info;
    }

    public Block getNext() {
        return next;
    }

    public Block(String info, int id) {
        this.size = 8;
        this.info = info;
        this.next = null;
        this.id=id;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
}
