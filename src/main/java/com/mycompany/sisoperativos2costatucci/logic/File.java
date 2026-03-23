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
    private String name;
    private File next;

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

  public File(int size, Queue bitMap, String owner) {
    // VALIDACIÓN PREVIA: Si no hay suficientes bloques, ni siquiera empezamos
    if (bitMap.getQueuesize() < size) {
        this.firstBlock = null;
        this.sizeFile = 0;
        return; // Salimos sin tocar el BitMap
    }

    this.owner = owner;
    this.sizeFile = size;
    this.next = null;

    // Ahora sí, sacamos bloques con seguridad
    this.firstBlock = bitMap.popFirstBlock();
    if (this.firstBlock != null) {
        Block current = this.firstBlock;
        for (int i = 1; i < size; i++) {
            Block nextBlock = bitMap.popFirstBlock();
            if (nextBlock != null) {
                current.setNext(nextBlock);
                current = nextBlock;
            }
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
        // Esto hará que en la interfaz se vea exactamente como en tu foto de referencia
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
