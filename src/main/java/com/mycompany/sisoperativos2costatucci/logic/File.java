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
        this.owner = owner;
        this.sizeFile = size;
        this.next = null; // Inicializamos el puntero del siguiente archivo vacío

        this.firstBlock = bitMap.popFirstBlock();

        // Si firstBlock no es null, procedemos a sacar los demás
        if (this.firstBlock != null) {
            Block block = this.firstBlock;
            while (size > 1) {
                Block newBlock = bitMap.popFirstBlock();
                if (newBlock == null) {
                    break; // Si ya no hay bloques libres, paramos
                }
                block.setNext(newBlock);
                block = newBlock;
                size -= 1;
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
