package com.mycompany.sisoperativos2costatucci.logic;

public class Queue {

    private Block firstBlock;
    private Process firstProcess;
    private File firstFile;
    private Directory firstDirectory;
    private Request firstRequest;
    private Row firstRow;
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

    public Request getFirstRequest() {
        return firstRequest;
    }

    public Row getFirstRow() {
        return firstRow;
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

    public void setFirstRequest(Request firstRequest) {
        this.firstRequest = firstRequest;
    }

    public void setFirstRow(Row firstRow) {
        this.firstRow = firstRow;
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
    
    public void addRequest(Request request) {
        if (this.firstRequest == null) {
            this.firstRequest = request;
        } else {
            Request tempRequest = this.firstRequest;
            while (tempRequest.getNextRequest()!= null) {
                tempRequest = tempRequest.getNextRequest();
            }
            tempRequest.setNextRequest(request);
        }
        this.queuesize += 1;
    }
    
    public Block popBlockById(int id) {
    if (this.firstBlock == null) return null;

    // Caso 1: Es el primero de la lista
    if (this.firstBlock.getId() == id) {
        return popFirstBlock();
    }

    // Caso 2: Buscar en el resto de la lista
    Block prev = this.firstBlock;
    Block current = this.firstBlock.getNext();

    while (current != null) {
        if (current.getId() == id) {
            prev.setNext(current.getNext()); // Saltamos el nodo actual
            current.setNext(null);           // Lo aislamos
            this.queuesize -= 1;
            return current;
        }
        prev = current;
        current = current.getNext();
    }
    return null; // No se encontró o ya estaba ocupado
}
    // ==========================================
// MÉTODO PUSH PARA BLOQUES (Inserta al inicio)
// ==========================================
public void pushBlock(Block block) {
    if (block == null) return;
    if (this.firstBlock == null) {
        this.firstBlock = block;
        block.setNext(null); // Aseguramos que no traiga punteros viejos
    } else {
        // El nuevo bloque apunta al que antes era el primero
        block.setNext(this.firstBlock);
        // El nuevo bloque pasa a ser la cabeza de la lista
        this.firstBlock = block;
    }
    this.queuesize += 1;
}
    public void addRow(Row row) {
        if (this.firstRow == null) {
            this.firstRow = row;
        } else {
            row.setNextRow(this.firstRow);
            this.firstRow = row;
        this.queuesize += 1;
    }
    }
    
    public void deleteRow(){
        if (this.firstRow != null) {
            Row tempRow = this.firstRow;
            this.firstRow=this.firstRow.getNextRow();
            this.queuesize -= 1;
        }
        
    }
}