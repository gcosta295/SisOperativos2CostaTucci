/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sisoperativos2costatucci.logic;

import java.awt.Color;

/**
 *
 * @author astv06
 */
public class File {

    private int sizeFile;
    private Block firstBlock;
    private String owner;
    private String name;
    private File next;
    public Color color; 

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

public File(int size, Queue bitMap, String owner, Queue log) throws Exception {
    this.owner = owner;
    this.sizeFile = size;
    this.next = null;
    synchronized(log) {
        int initialLogSize = log.getQueuesize(); 
        try {
            if (size <= 0) return;
            Block first = bitMap.popFirstBlock();
            if (first == null) {
                throw new Exception(" No hay bloques disponibles para iniciar el archivo.");
            }
            this.firstBlock = first;
            log.addRow(new Row(null, first, false));
            Block current = first;
            for (int i = 1; i < size; i++) {
                Block nextBlock = bitMap.popFirstBlock();
                if (nextBlock == null) {
                    throw new Exception(" Espacio insuficiente para completar el archivo.");
                }
                current.setNext(nextBlock);
                current = nextBlock;
                log.addRow(new Row(null, current, false));
            }
            log.addRow(new Row(null, null, true));
        } catch (Exception e) {
            while (log.getQueuesize() > initialLogSize) {
                Row lastAction = log.getFirstRow();
                if (lastAction != null && lastAction.getAfterChange() != null) {
                    Block blockToReturn = lastAction.getAfterChange();
                    blockToReturn.setNext(null); 
                    bitMap.pushBlock(blockToReturn); 
                }
                log.deleteRow(); 
            }
            this.firstBlock = null; 
            throw e; 
        }
    } 
}
    public File getNext() {
        return next;
    }

    public void setNext(File next) {
        this.next = next;
    }

    @Override
    public String toString() {
        int cantidad = 0;
        Block actual = this.getFirstBlock();
        while (actual != null) {
            cantidad++;
            actual = actual.getNext();
        }
        return this.getName() + " [" + cantidad + " bloques]";
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
