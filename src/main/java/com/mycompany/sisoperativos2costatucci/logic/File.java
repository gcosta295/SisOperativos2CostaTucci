/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sisoperativos2costatucci.logic;

/**
 *
 * @author astv06
 */
public class File {
    private int sizeFile;
    private Block firstBlock;
    private String owner;

    public void setSizeFile(int sizeFile) {
        this.sizeFile = sizeFile;
    }

    public void setFirstBlock(Block firstBlock) {
        this.firstBlock = firstBlock;
    }

    public int getSizeFile() {
        return sizeFile;
    }

    public Block getFirstBlock() {
        return firstBlock;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }
    
    public File (int size, Queue bitMap, String owner){
        this.owner=owner;
        this.sizeFile = size;
        this.firstBlock = bitMap.popFirstBlock();
        Block block = this.firstBlock;
        while (this.firstBlock !=null &&  size > 1){
            Block newBlock = bitMap.popFirstBlock();
            block.setNext(newBlock);
            block = newBlock;
            size -= 1;
        }
    }
}
