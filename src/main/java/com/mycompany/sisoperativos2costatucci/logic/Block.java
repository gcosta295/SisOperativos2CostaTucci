package com.mycompany.sisoperativos2costatucci.logic;

public class Block {
    private int id;
    private int size;
    private String info;
    private Block next;

    // ==========================================
    // 1. CONSTRUCTORES
    // ==========================================
    
    // Constructor Original (Para cuando guardas datos directamente)
    public Block(String info, int id) {
        this.size = 8;
        this.info = info;
        this.next = null;
        this.id = id;
    }

    // ¡NUEVO! Constructor para Bloques Vacíos (Ideal para iniciar tu BitMap)
    public Block(int id) {
        this.size = 8;
        this.info = ""; // Inicia sin información
        this.next = null;
        this.id = id;
    }

    // ==========================================
    // 2. GETTERS Y SETTERS
    // ==========================================
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}