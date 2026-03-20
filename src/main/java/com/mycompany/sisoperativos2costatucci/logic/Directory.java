package com.mycompany.sisoperativos2costatucci.logic;

public class Directory {

    private String directoryName;
    private Queue directories;
    private Queue files;

    // Puntero para encadenar carpetas dentro de la Queue
    private Directory next;

    // ==========================================
    // 1. CONSTRUCTOR
    // ==========================================
    public Directory(String directoryName) {
        this.directoryName = directoryName;
        this.next = null; // Inicializamos el puntero vacío

        // Inicializamos las colas pasándoles un nombre representativo
        this.directories = new Queue("Directorios_de_" + directoryName);
        this.files = new Queue("Archivos_de_" + directoryName);
    }

    // ==========================================
    // 2. MÉTODOS PARA AGREGAR CONTENIDO
    // ==========================================
    // La carpeta simplemente le "delega" el trabajo a su respectiva cola (Queue)
    public void addDirectory(Directory dir) {
        this.directories.addDirectory(dir);
    }

    public void addFile(File file) {
        this.files.addFile(file);
    }

    // ==========================================
    // 3. GETTERS Y SETTERS
    // ==========================================
    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public Queue getDirectories() {
        return directories;
    }

    public void setDirectories(Queue directories) {
        this.directories = directories;
    }

    public Queue getFiles() {
        return files;
    }

    public void setFiles(Queue files) {
        this.files = files;
    }

    public Directory getNext() {
        return next;
    }

    public void setNext(Directory next) {
        this.next = next;
    }

    // ==========================================
    // 4. TOSTRING PARA EL JTREE
    // ==========================================
    @Override
    public String toString() {
        return this.getDirectoryName(); // Para que el JTree pinte el nombre correcto
    }
}
