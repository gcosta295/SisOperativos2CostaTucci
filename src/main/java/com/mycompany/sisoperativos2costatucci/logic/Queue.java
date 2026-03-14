/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sisoperativos2costatucci.logic;

/**
 *
 * @author astv06
 */
public class Queue {

    private Block firstBlock;
    private Process firstProcess;
    private File firstFile;
    private Directory firstDirectory;
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

    public File getFirstFile() {
        return firstFile;
    }

    public Directory getFirstDirectory() {
        return firstDirectory;
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

    public void setFirstFile(File firstFile) {
        this.firstFile = firstFile;
    }

    public void setFirstDirectory(Directory firstDirectory) {
        this.firstDirectory = firstDirectory;
    }

    public Queue(String name) {
        this.firstBlock = null;
        this.firstProcess = null;
        this.firstDirectory = null;
        this.firstFile = null;
        this.queueName = name;
        this.queuesize = 0;
    }

    public void addBlock(Block block) {
        if (this.firstBlock == null) {
            this.firstBlock = block;
        } else {
            // Si ya hay bloques, buscamos el último
            Block tempBlock = this.firstBlock;
            while (tempBlock.getNext() != null) {
                tempBlock = tempBlock.getNext();
            }
            tempBlock.setNext(block);
        }
        this.queuesize += 1;
    }

    public void removeLastBlock() {
        if (this.firstBlock == null) {
            return; // Si está vacía, no hace nada
        }
        if (this.firstBlock.getNext() == null) {
            // Si solo hay un elemento
            this.firstBlock = null;
        } else {
            Block tempBlock = this.firstBlock;
            Block lastBlock = tempBlock.getNext();
            while (lastBlock.getNext() != null) {
                tempBlock = lastBlock;
                lastBlock = lastBlock.getNext();
            }
            tempBlock.setNext(null);
        }
        this.queuesize -= 1;
    }

    public Block popFirstBlock() {
        if (this.firstBlock != null) {
            Block headBlock = this.firstBlock;
            this.firstBlock = headBlock.getNext();
            headBlock.setNext(null);
            this.queuesize -= 1; // ¡Faltaba esto!
            return headBlock;
        } else {
            return null;
        }
    }

}
