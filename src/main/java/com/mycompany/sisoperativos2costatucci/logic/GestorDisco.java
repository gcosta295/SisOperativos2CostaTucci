package com.mycompany.sisoperativos2costatucci.logic;

import com.mycompany.sisoperativos2costatucci.gui.PanelSD;
import java.awt.Color;

public class GestorDisco {
    
    private PanelSD vistaDisco;
    private Queue colaLibres;
    private int totalBloques;
    private PlanificadorDisco planificador; 

    public GestorDisco(int totalBloques, int cabezalInicial) {
        this.totalBloques = totalBloques;
        this.vistaDisco = new PanelSD(totalBloques);
        this.colaLibres = new Queue("BitMap_Libres");        
        this.planificador = new PlanificadorDisco(cabezalInicial);
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

public File crearArchivo(int size, String owner, Color color,Queue log) throws Exception {
    File nuevoArchivo = new File(size, colaLibres, owner, log);
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
    
public void reiniciarEstructura() {
    if (this.vistaDisco != null) {
        this.vistaDisco.limpiarTodosLosBloques();
    }
    this.colaLibres = new Queue("Libres");
    for (int i = 0; i < 181; i++) {
        this.colaLibres.addBlock(new Block(i));
    }
}

public Recovery ejecutarOperacionSegura(File archivo, String operacion) {
    Recovery status = new Recovery();
    try {
        if (operacion.equalsIgnoreCase("CREAR")) {
            Block aux = archivo.getFirstBlock();
            while (aux != null) {
                this.vistaDisco.asignarBloqueVisual(aux.getId(), Color.BLUE);
                aux = aux.getNext();
                status.processedCount++;
            }
        } else if (operacion.equalsIgnoreCase("ELIMINAR")) {
            this.eliminarArchivo(archivo); 
        }
        status.success = true;
    } catch (Exception e) {
        System.out.println("Falla detectada. Iniciando recuperación semiautomática...");
        status = iniciarRecuperacionSemiautomatica("Fallo en " + operacion + ": " + e.getMessage());
    }
    return status;
}

private Recovery iniciarRecuperacionSemiautomatica(String causa) {
    Recovery rs = recuperarDesdeLog(this.colaLibres, Color.ORANGE); 
    rs.errorMessage = causa;
    rs.success = false;    
    return rs;
}
public Recovery recuperarDesdeLog(Queue logQueue, Color colorRecup) {
    Recovery status = new Recovery();
    Row actual = logQueue.getFirstRow();
    
    try {
        while (actual != null) {
            if (actual.getAfterChange() != null) {
                Block b = actual.getAfterChange();
                vistaDisco.asignarBloqueVisual(b.getId(), colorRecup);                
                colaLibres.popBlockById(b.getId());
                status.processedCount++;
            }
            actual = actual.getNextRow();
        }
        status.success = true;
    } catch (Exception e) {
        status.success = false;
        status.errorMessage = e.getMessage();
    }
    return status;
}

public File cargarArchivoEnPosicionEspecifica(int inicio, int cantidad, String owner, Color color, Queue log) throws Exception {
    File nuevoArchivo = new File(0, colaLibres, owner, log);
    nuevoArchivo.color = color; 
    Block anterior = null;
    for (int i = 0; i < cantidad; i++) {
        int idBuscado = inicio + i;
        Block bloqueActual = this.colaLibres.extraerBloquePorId(idBuscado);
        if (bloqueActual != null) {
            vistaDisco.asignarBloqueVisual(bloqueActual.getId(), color);
            if (i == 0) {
                nuevoArchivo.setFirstBlock(bloqueActual);
            } else {
                if (anterior != null) {
                    anterior.setNext(bloqueActual);
                }
            }
            anterior = bloqueActual;
        } else {
            System.out.println("Error: El bloque " + idBuscado + " ya no está en la cola de libres.");
        }
    }
    
    return nuevoArchivo; 
}}