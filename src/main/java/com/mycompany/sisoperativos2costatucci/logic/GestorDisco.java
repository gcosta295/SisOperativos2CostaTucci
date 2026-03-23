package com.mycompany.sisoperativos2costatucci.logic;

import com.mycompany.sisoperativos2costatucci.gui.PanelSD;
import java.awt.Color;

public class GestorDisco {
    
    private PanelSD vistaDisco;
    private Queue colaLibres;
    private int totalBloques;
    
    // OPCIÓN A: El Gestor es dueño del Planificador
    private PlanificadorDisco planificador; 

    // Constructor actualizado
    public GestorDisco(int totalBloques, int cabezalInicial) {
        this.totalBloques = totalBloques;
        this.vistaDisco = new PanelSD(totalBloques);
        this.colaLibres = new Queue("BitMap_Libres");
        
        // Inicializamos el "brazo mecánico"
        this.planificador = new PlanificadorDisco(cabezalInicial);

        // Llenamos la memoria libre
        for (int i = 0; i < totalBloques; i++) {
            this.colaLibres.addBlock(new Block("Vacio", i)); 
        }
    }

    public PanelSD getVistaDisco() {
        return vistaDisco;
    }

    public int getBloquesLibres() {
        return colaLibres.getQueuesize();
    }

    public Queue getColaLibres() {
        return colaLibres;
    }
    
    public PlanificadorDisco getPlanificador() {
        return planificador;
    }

    // =====================================
    // OPERACIONES CRUD DEL DISCO
    // =====================================

public File crearArchivo(int size, String owner, Color color) {
    File nuevoArchivo = new File(size, colaLibres, owner);

    // Si el archivo no pudo obtener sus bloques (espacio insuficiente)
    if (nuevoArchivo.getFirstBlock() == null) {
        System.out.println("Error: No hay bloques libres suficientes.");
        return null; // Esto hará que tu JFrame muestre el error al usuario
    }

    // Si llegó aquí, los bloques ya fueron EXTRAÍDOS de colaLibres
    // Ahora solo pintamos lo que el archivo ya posee
    Block bloqueActual = nuevoArchivo.getFirstBlock();
    while (bloqueActual != null) {
        vistaDisco.asignarBloqueVisual(bloqueActual.getId(), color);
        bloqueActual = bloqueActual.getNext();
    }

    return nuevoArchivo; 
}

    public void leerArchivo(File archivo, String politicaPlanificacion) {
        if (archivo == null || archivo.getFirstBlock() == null) return;
        
        Queue peticionesLectura = new Queue("Req_Lectura");
        Block bloqueActual = archivo.getFirstBlock();
        
        while (bloqueActual != null) {
            peticionesLectura.addBlock(new Block("Req", bloqueActual.getId()));
            bloqueActual = bloqueActual.getNext();
        }
        
        System.out.println("\n[GestorDisco] Solicitando LECTURA de archivo de " + archivo.getOwner());
        
        // El usuario de la interfaz puede elegir qué política usar al leer
        if (politicaPlanificacion.equalsIgnoreCase("SSTF")) {
            planificador.ejecutarSSTF(peticionesLectura);
        } else {
            planificador.ejecutarFIFO(peticionesLectura);
        }
    }

    public void eliminarArchivo(File archivo) {
        if (archivo == null || archivo.getFirstBlock() == null) return;

        Queue peticionesBorrado = new Queue("Req_Borrado");
        Block bloqueActual = archivo.getFirstBlock();
        
        while (bloqueActual != null) {
            // Guardamos petición para el planificador
            peticionesBorrado.addBlock(new Block("Req", bloqueActual.getId()));
            
            Block siguiente = bloqueActual.getNext(); 
            vistaDisco.liberarBloqueVisual(bloqueActual.getId()); 
            
            bloqueActual.setInfo("Vacio");
            bloqueActual.setNext(null); 
            colaLibres.addBlock(bloqueActual); 
            
            bloqueActual = siguiente;
        }
        
        System.out.println("\n[GestorDisco] Solicitando BORRADO de archivo de " + archivo.getOwner());
        planificador.ejecutarFIFO(peticionesBorrado); // Simula que el brazo va a borrar los sectores
        
        archivo.setFirstBlock(null);
        archivo.setSizeFile(0);
    }
    
    // Dentro de la clase GestorDisco
public void reiniciarEstructura() {
    // 1. Limpiamos visualmente los cuadritos
    if (this.vistaDisco != null) {
        this.vistaDisco.limpiarTodosLosBloques();
    }

    // 2. Reiniciamos la cola de bloques libres (suponiendo que son 181 bloques)
    this.colaLibres = new Queue("Libres");
    for (int i = 0; i < 181; i++) {
        this.colaLibres.addBlock(new Block(i));
    }
}
}