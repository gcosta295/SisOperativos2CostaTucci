/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sisoperativos2costatucci;

/**
 *
 * @author astv06
 */
public class Queue {
    private Block firstBlock;
    private Process firstProcess;
    private String queueName;
    private int queuesize;

    public Block getFirstBlock() {
        return firstBlock;
    }

    public String getQueueName() {
        return queueName;
    }

    public int getQueuesize() {
        return queuesize;
    }

    public Process getFirstProcess() {
        return firstProcess;
    }

    public void setFirstBlock(Block firstBlock) {
        this.firstBlock = firstBlock;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public void setQueuesize(int queuesize) {
        this.queuesize = queuesize;
    }

    public void setFirstProcess(Process firstProcess) {
        this.firstProcess = firstProcess;
    }
    
    public Queue (String name){
        this.firstBlock=null;
        this.firstProcess=null;
        this.queueName = name;
        this.queuesize = 0;
    }
    
    public void addBlock (Block block){
        Block tempBlock = this.firstBlock;
        while (tempBlock.getNext()!=null){
            tempBlock = tempBlock.getNext();
        }
        tempBlock.setNext(block);
        this.queuesize+=1;
    }
    
    public void removeLastBlock (){
        Block tempBlock = this.firstBlock;
        Block lastBlock = tempBlock.getNext();
        while (lastBlock.getNext()!=null){
            tempBlock = lastBlock;
            lastBlock = lastBlock.getNext();
        }
        lastBlock.setInfo(null);
        tempBlock.setNext(null);   
        this.queuesize-=1;
    }
    
    public Block popFirstBlock (){
        if (this.queuesize>0){
            Block headBlock = this.firstBlock;
            this.setFirstBlock(headBlock.getNext());
            headBlock.setNext(null);
            return headBlock;
        }else{
            return null;
        }
    }
}
