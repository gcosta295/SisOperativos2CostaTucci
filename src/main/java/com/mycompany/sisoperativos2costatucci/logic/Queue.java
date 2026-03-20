package com.mycompany.sisoperativos2costatucci.logic;

public class Queue {

    private Block firstBlock;
    private Process firstProcess;
    private File firstFile;
    private Directory firstDirectory;
    private String queueName;
    private int queuesize;

    // ==========================================
    // CONSTRUCTOR
    // ==========================================
    public Queue(String name) {
        this.firstBlock = null;
        this.firstProcess = null;
        this.firstDirectory = null;
        this.firstFile = null;
        this.queueName = name;
        this.queuesize = 0;
    }

    // ==========================================
    // GETTERS Y SETTERS
    // ==========================================
    public Block getFirstBlock() {
        return firstBlock;
    }

    public void setFirstBlock(Block firstBlock) {
        this.firstBlock = firstBlock;
    }

    public Process getFirstProcess() {
        return firstProcess;
    }

    public void setFirstProcess(Process firstProcess) {
        this.firstProcess = firstProcess;
    }

    public File getFirstFile() {
        return firstFile;
    }

    public void setFirstFile(File firstFile) {
        this.firstFile = firstFile;
    }

    public Directory getFirstDirectory() {
        return firstDirectory;
    }

    public void setFirstDirectory(Directory firstDirectory) {
        this.firstDirectory = firstDirectory;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public int getQueuesize() {
        return queuesize;
    }

    public void setQueuesize(int queuesize) {
        this.queuesize = queuesize;
    }

    // ==========================================
    // MÉTODOS PARA BLOQUES
    // ==========================================
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
            this.queuesize -= 1; 
            return headBlock;
        } else {
            return null;
        }
    }

    // ==========================================
    // MÉTODOS PARA DIRECTORIOS Y ARCHIVOS
    // ==========================================
    public void addDirectory(Directory dir) {
        if (this.firstDirectory == null) {
            this.firstDirectory = dir;
        } else {
            Directory tempDir = this.firstDirectory;
            while (tempDir.getNext() != null) {
                tempDir = tempDir.getNext();
            }
            tempDir.setNext(dir);
        }
        this.queuesize += 1;
    }

    public void addFile(File file) {
        if (this.firstFile == null) {
            this.firstFile = file;
        } else {
            File tempFile = this.firstFile;
            while (tempFile.getNext() != null) {
                tempFile = tempFile.getNext();
            }
            tempFile.setNext(file);
        }
        this.queuesize += 1;
    }
}