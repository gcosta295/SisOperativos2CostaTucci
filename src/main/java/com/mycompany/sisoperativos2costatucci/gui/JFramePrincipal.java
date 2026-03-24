/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.sisoperativos2costatucci.gui;

import com.mycompany.sisoperativos2costatucci.logic.Block;
import com.mycompany.sisoperativos2costatucci.logic.Directory;
import com.mycompany.sisoperativos2costatucci.logic.File;
import com.mycompany.sisoperativos2costatucci.logic.GestorDisco;
import com.mycompany.sisoperativos2costatucci.logic.Queue;
import com.mycompany.sisoperativos2costatucci.logic.Recovery;
import com.mycompany.sisoperativos2costatucci.logic.Request;
import com.mycompany.sisoperativos2costatucci.logic.Row;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.util.Iterator;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.TreePath;

/**
 *
 * @author gabri
 */
public class JFramePrincipal extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(JFramePrincipal.class.getName());
    private GestorDisco miDisco;
    private Queue generalRequests;
    private int header;
    private String mode;
    private Queue log;
    private boolean simularFalloProximo = false;

    /**
     * Creates new form JFramePrincipal
     */
    public JFramePrincipal() throws Exception {
        initComponents();
        configurarTablaAsignacion();
        miDisco = new GestorDisco(181, 50);
        header = 0;
        generalRequests = new Queue("Requests");
        log = new Queue("log");
        log.addRow(new Row(null, null, true));
        mode = null;
        panelContenedorDisco.setLayout(new java.awt.BorderLayout());
        panelContenedorDisco.removeAll();
        panelContenedorDisco.add(miDisco.getVistaDisco(), java.awt.BorderLayout.CENTER);
        panelContenedorDisco.revalidate();
        panelContenedorDisco.repaint();
        iniciarDatosDePrueba();
    }

// ==========================================
// 1. GENERADOR DE COLORES ALEATORIOS
// ==========================================
    private Color obtenerColorAleatorio() {
        // Generamos valores entre 0.2 y 0.8 
        // Esto evita el negro total (0.0) y el blanco total (1.0)
        float r = 0.2f + (float) Math.random() * 0.6f;
        float g = 0.2f + (float) Math.random() * 0.6f;
        float b = 0.2f + (float) Math.random() * 0.6f;

        // Retornamos el color. .brighter() ayuda a que resalten en el gris del disco.
        return new Color(r, g, b).brighter();
    }

// ==========================================
    // MÉTODO PARA PREPARAR LA TABLA DE ASIGNACIÓN
    // ==========================================
    private DefaultTableModel modeloTabla;

    private void configurarTablaAsignacion() {
        // 1. Creamos las columnas (sin Propietario)
        String[] columnas = {"Archivo", "Bloques", "Primer Bloque", "Color"};
        modeloTabla = new DefaultTableModel(columnas, 0);

        // 2. Se lo aplicamos a la tabla de NetBeans
        tablaAsignacion.setModel(modeloTabla);

        // 3. Le decimos a la columna 3 (la de Color) que use nuestro pintor especial
        tablaAsignacion.getColumnModel().getColumn(3).setCellRenderer(new ColorRenderer());
    }

// ==========================================
// 2. MÉTODO RECURSIVO PARA PINTAR EL DISCO
// ==========================================
    // ==========================================
// 2. MÉTODO RECURSIVO PARA PINTAR EL DISCO Y LLENAR LA TABLA
// ==========================================
    private void pintarArchivosEnDisco(Directory carpetaLogica, PanelSD panelDisco) {

        // A. Recorrer y procesar los ARCHIVOS de esta carpeta
        if (carpetaLogica.getFiles() != null) {
            File actualArchivo = carpetaLogica.getFiles().getFirstFile();

            while (actualArchivo != null) {
                Color colorArchivo = obtenerColorAleatorio();
                int cantBloques = 0;
                int primerBloque = -1;

                Block actualBloque = actualArchivo.getFirstBlock();

                // Guardamos cuál es el primer bloque para la tabla
                if (actualBloque != null) {
                    primerBloque = actualBloque.getId();
                }

                while (actualBloque != null) {
                    panelDisco.asignarBloqueVisual(actualBloque.getId(), colorArchivo);
                    cantBloques++; // Contamos los bloques
                    actualBloque = actualBloque.getNext();
                }

                // ¡NUEVO! Agregamos los datos de este archivo a la tabla
                modeloTabla.addRow(new Object[]{
                    actualArchivo.getName(),
                    cantBloques,
                    primerBloque,
                    colorArchivo // Pasamos el objeto Color directo
                });
                System.out.println("Fila agregada a la tabla para: " + actualArchivo.getName());

                actualArchivo = actualArchivo.getNext();
            }
        }

        // B. Recorrer las SUBCARPETAS (Magia recursiva)
        if (carpetaLogica.getDirectories() != null) {
            Directory actualDir = carpetaLogica.getDirectories().getFirstDirectory();
            while (actualDir != null) {
                pintarArchivosEnDisco(actualDir, panelDisco);
                actualDir = actualDir.getNext();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        PanelControles = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jusuario = new javax.swing.JRadioButton();
        jadminisrtador1 = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        comboPolitica = new javax.swing.JComboBox<>();
        crear = new javax.swing.JButton();
        eliminar = new javax.swing.JButton();
        eliminar1 = new javax.swing.JButton();
        crear1 = new javax.swing.JButton();
        update = new javax.swing.JButton();
        Json = new javax.swing.JButton();
        botonPrueba = new javax.swing.JButton();
        botonFallo = new javax.swing.JButton();
        paneArbol = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        arbolDirectorios = new javax.swing.JTree();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        panelContenedorDisco = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaAsignacion = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 255, 204));
        jPanel1.setToolTipText("");

        PanelControles.setBackground(new java.awt.Color(153, 255, 153));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel1.setText("Controles");
        jLabel1.setOpaque(true);

        buttonGroup1.add(jusuario);
        jusuario.setText("Usuario");
        jusuario.addActionListener(this::jusuarioActionPerformed);

        buttonGroup1.add(jadminisrtador1);
        jadminisrtador1.setText("Administrador");
        jadminisrtador1.addActionListener(this::jadminisrtador1ActionPerformed);

        jLabel2.setBackground(new java.awt.Color(204, 204, 255));
        jLabel2.setText("Modo:");
        jLabel2.setOpaque(true);

        jLabel3.setBackground(new java.awt.Color(204, 204, 255));
        jLabel3.setText("Planificador:");
        jLabel3.setOpaque(true);

        comboPolitica.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FIFO", "SSTF", "SCAN", "C-SCAN" }));
        comboPolitica.addActionListener(this::comboPoliticaActionPerformed);

        crear.setText("Crear Archivo");
        crear.addActionListener(this::crearActionPerformed);

        eliminar.setText("Eliminar");
        eliminar.addActionListener(this::eliminarActionPerformed);

        eliminar1.setText("Leer");
        eliminar1.addActionListener(this::eliminar1ActionPerformed);

        crear1.setText("Crear Directorio");
        crear1.addActionListener(this::crear1ActionPerformed);

        update.setText("Update");
        update.addActionListener(this::updateActionPerformed);

        Json.setText("Cargar JSON");
        Json.addActionListener(this::JsonActionPerformed);

        botonPrueba.setText("Prueba de Requests");
        botonPrueba.addActionListener(this::botonPruebaActionPerformed);

        botonFallo.setText("Simular Fallo");
        botonFallo.addActionListener(this::botonFalloActionPerformed);

        javax.swing.GroupLayout PanelControlesLayout = new javax.swing.GroupLayout(PanelControles);
        PanelControles.setLayout(PanelControlesLayout);
        PanelControlesLayout.setHorizontalGroup(
            PanelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelControlesLayout.createSequentialGroup()
                .addGroup(PanelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelControlesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(PanelControlesLayout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addGroup(PanelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jusuario)
                            .addGroup(PanelControlesLayout.createSequentialGroup()
                                .addGroup(PanelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jadminisrtador1))
                                .addGap(63, 63, 63)
                                .addGroup(PanelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addGroup(PanelControlesLayout.createSequentialGroup()
                                        .addComponent(comboPolitica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(35, 35, 35)
                                        .addComponent(crear)
                                        .addGap(15, 15, 15)
                                        .addComponent(crear1)
                                        .addGap(18, 18, 18)
                                        .addComponent(eliminar)
                                        .addGap(18, 18, 18)
                                        .addComponent(eliminar1)
                                        .addGap(18, 18, 18)
                                        .addComponent(update)
                                        .addGap(18, 18, 18)
                                        .addComponent(Json)
                                        .addGap(18, 18, 18)
                                        .addGroup(PanelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(botonFallo)
                                            .addComponent(botonPrueba))))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelControlesLayout.setVerticalGroup(
            PanelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelControlesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboPolitica, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jadminisrtador1)
                        .addComponent(crear)
                        .addComponent(eliminar)
                        .addComponent(eliminar1)
                        .addComponent(crear1)
                        .addComponent(update)
                        .addComponent(Json)
                        .addComponent(botonPrueba)))
                .addGroup(PanelControlesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelControlesLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jusuario))
                    .addGroup(PanelControlesLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(botonFallo)))
                .addGap(32, 32, 32))
        );

        paneArbol.setBackground(new java.awt.Color(51, 102, 255));

        jScrollPane1.setViewportView(arbolDirectorios);

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Sistema de Archivos");
        jLabel4.setOpaque(true);

        javax.swing.GroupLayout paneArbolLayout = new javax.swing.GroupLayout(paneArbol);
        paneArbol.setLayout(paneArbolLayout);
        paneArbolLayout.setHorizontalGroup(
            paneArbolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneArbolLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneArbolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(paneArbolLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 321, Short.MAX_VALUE)))
                .addContainerGap())
        );
        paneArbolLayout.setVerticalGroup(
            paneArbolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneArbolLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 153, 153));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Cola de Procesos");
        jLabel5.setOpaque(true);

        jScrollPane2.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jScrollPane2ComponentShown(evt);
            }
        });

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane3.setViewportView(jTextArea1);

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Log de eventos");
        jLabel6.setOpaque(true);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane2.setBackground(new java.awt.Color(255, 51, 102));

        panelContenedorDisco.setLayout(new java.awt.BorderLayout());
        jTabbedPane2.addTab("tab1", panelContenedorDisco);

        tablaAsignacion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tablaAsignacion);

        jTabbedPane2.addTab("tab3", jScrollPane5);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelControles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(paneArbol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2)
                .addGap(18, 18, 18))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelControles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(paneArbol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jusuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jusuarioActionPerformed
        if (mode == "admin" || mode == null) {
            mode = "user";
            System.out.println("cambio de modo");
        }
    }//GEN-LAST:event_jusuarioActionPerformed

    private void jadminisrtador1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jadminisrtador1ActionPerformed
        if (mode == "user" || mode == null) {
            mode = "admin";
            System.out.println("cambio de modo");
        }
    }//GEN-LAST:event_jadminisrtador1ActionPerformed

    private void crearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crearActionPerformed
    // 1. Validaciones de Administrador y Selección (Tu código base)
    if (!jadminisrtador1.isSelected()) {
        JOptionPane.showMessageDialog(this, "Acceso denegado. Use el modo Administrador.", "Error de Permisos", JOptionPane.ERROR_MESSAGE);
        return;
    }
    DefaultMutableTreeNode nodoSeleccionado = (DefaultMutableTreeNode) arbolDirectorios.getLastSelectedPathComponent();
    if (nodoSeleccionado == null || !(nodoSeleccionado.getUserObject() instanceof Directory)) {
        JOptionPane.showMessageDialog(this, "Por favor, seleccione una CARPETA en el árbol para crear el archivo.");
        return;
    }

    Directory dirPadre = (Directory) nodoSeleccionado.getUserObject();
    String nombre = JOptionPane.showInputDialog(this, "Nombre del nuevo archivo:");
    if (nombre == null || nombre.trim().isEmpty()) return;

    String bloquesStr = JOptionPane.showInputDialog(this, "Cantidad de bloques a ocupar:");
    if (bloquesStr == null) return;

    // Declaramos el archivo fuera del try para poder acceder a él en el catch
    File nuevoFile = null; 

    try {
        int tamano = Integer.parseInt(bloquesStr);
        Color colorNuevo = obtenerColorAleatorio();
        
        // --- A. RESERVA LÓGICA ---
        // Aquí miDisco saca los bloques de la cola de libres.
        nuevoFile = miDisco.crearArchivo(tamano, "Admin", colorNuevo, log);

        // ============================================================
        // B. SIMULACIÓN DE FALLO (Ajustada a tu petición)
        // ============================================================
        if (this.simularFalloProximo) {
            this.simularFalloProximo = false; 
            botonFallo.setBackground(null);
            botonFallo.setText("Simular Fallo");
            
            // Lanzamos el fallo ANTES de pintar y ANTES de registrar en el árbol.
            // Esto garantiza que el display no cambie.
            throw new Exception("FALLO_SIMULADO"); 
        }
        // ============================================================

        // C. OPERACIÓN SEGURA Y COMMIT
        Recovery resultado = miDisco.ejecutarOperacionSegura(nuevoFile, "CREAR");

        if (resultado.success) {
            nuevoFile.setName(nombre.trim());
            dirPadre.addFile(nuevoFile);
            
            // Actualizar JTree
            DefaultTreeModel modeloArbol = (DefaultTreeModel) arbolDirectorios.getModel();
            DefaultMutableTreeNode nuevoNodo = new DefaultMutableTreeNode(nuevoFile);
            modeloArbol.insertNodeInto(nuevoNodo, nodoSeleccionado, nodoSeleccionado.getChildCount());
            
            // Actualizar Tabla
            modeloTabla.addRow(new Object[]{ nuevoFile.getName(), tamano, nuevoFile.getFirstBlock().getId(), colorNuevo });

            // ACTUALIZAR DISPLAY VISUAL (Solo ocurre si no hubo fallo)
            Block bloqueActual = nuevoFile.getFirstBlock();
            while (bloqueActual != null) {
                miDisco.getVistaDisco().asignarBloqueVisual(bloqueActual.getId(), colorNuevo);
                bloqueActual = bloqueActual.getNext();
            }
            panelContenedorDisco.repaint();
            JOptionPane.showMessageDialog(this, "Archivo creado exitosamente.");
        }

    } catch (Exception e) {
        if ("FALLO_SIMULADO".equals(e.getMessage())) {
            // --- D. RECUPERACIÓN (ROLLBACK) ---
            // Como el fallo ocurrió, devolvemos los bloques a la cola de libres 
            // para que no se queden "ocupados" u huérfanos.
            if (nuevoFile != null) {
                liberarArchivoVisualYTabla(nuevoFile); 
            }
            
            JOptionPane.showMessageDialog(this, 
                "Fallo de Sistema: Operación cancelada.\nLos bloques han sido liberados y no se alteró el disco.", 
                "Journal Rollback", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_crearActionPerformed

    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarActionPerformed
    if (!jadminisrtador1.isSelected()) {
        JOptionPane.showMessageDialog(this, "Acceso denegado. Use el modo Administrador.", "Error de Permisos", JOptionPane.ERROR_MESSAGE);
        return;
    }

    DefaultMutableTreeNode nodoSeleccionado = (DefaultMutableTreeNode) arbolDirectorios.getLastSelectedPathComponent();
    if (nodoSeleccionado == null) {
        JOptionPane.showMessageDialog(this, "Seleccione un archivo o carpeta en el árbol para eliminar.");
        return;
    }

    if (nodoSeleccionado.isRoot()) {
        JOptionPane.showMessageDialog(this, "No se puede eliminar la raíz del disco duro.");
        return;
    }

    int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de que desea eliminar '" + nodoSeleccionado.toString() + "' y todo su contenido?",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

    if (confirmacion == JOptionPane.YES_OPTION) {
        try {
            // ============================================================
            // SIMULACIÓN DE FALLO (Para eliminación)
            // ============================================================
            if (this.simularFalloProximo) {
                this.simularFalloProximo = false; // Resetear flag
                botonFallo.setBackground(null);
                botonFallo.setText("Simular Fallo");

                // Lanzamos el error ANTES de tocar nada.
                // Al lanzarlo aquí, no se llama a eliminarNodoRecursivo,
                // por lo tanto los bloques NO se liberan y el JTree NO cambia.
                throw new Exception("FALLO_SIMULADO_ELIMINAR");
            }
            // ============================================================

            // Si no hay fallo, procedemos con la eliminación real (COMMIT)
            eliminarNodoRecursivo(nodoSeleccionado); 
            
            DefaultTreeModel modeloArbol = (DefaultTreeModel) arbolDirectorios.getModel();
            modeloArbol.removeNodeFromParent(nodoSeleccionado);
            
            panelContenedorDisco.repaint();
            JOptionPane.showMessageDialog(this, "Eliminado exitosamente y bloques liberados.");

        } catch (Exception e) {
            if ("FALLO_SIMULADO_ELIMINAR".equals(e.getMessage())) {
                JOptionPane.showMessageDialog(this, 
                    "Fallo de Sistema: La eliminación fue interrumpida.\nNo se realizaron cambios en el disco ni en el índice.", 
                    "Journal Rollback", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }
    }//GEN-LAST:event_eliminarActionPerformed

    private void eliminar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminar1ActionPerformed

    }//GEN-LAST:event_eliminar1ActionPerformed

    private void crear1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crear1ActionPerformed
    // 1. Validaciones de Administrador
    if (!jadminisrtador1.isSelected()) {
        JOptionPane.showMessageDialog(this, "Acceso Denegado: Solo el Administrador puede crear directorios.", "Error de Permisos", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // 2. Obtener y validar el nodo seleccionado
    DefaultMutableTreeNode nodoSeleccionado = (DefaultMutableTreeNode) arbolDirectorios.getLastSelectedPathComponent();
    if (nodoSeleccionado == null || !(nodoSeleccionado.getUserObject() instanceof Directory)) {
        JOptionPane.showMessageDialog(this, "Por favor, seleccione una carpeta válida en el árbol.");
        return;
    }

    Directory carpetaPadre = (Directory) nodoSeleccionado.getUserObject();

    // 3. Pedir el nombre
    String nombre = JOptionPane.showInputDialog(this, "Nombre del nuevo directorio:");
    if (nombre == null || nombre.trim().isEmpty()) return;

    try {
        // --- A. PREPARACIÓN LÓGICA ---
        Directory nuevaCarpeta = new Directory(nombre.trim());

        // ============================================================
        // B. SIMULACIÓN DE FALLO
        // ============================================================
        if (this.simularFalloProximo) {
            this.simularFalloProximo = false; // Resetear flag
            botonFallo.setBackground(null);
            botonFallo.setText("Simular Fallo");

            // Lanzamos el error ANTES de añadirlo a la carpeta padre 
            // y ANTES de refrescar el JTree.
            throw new Exception("FALLO_SISTEMA_DIRECTORIO");
        }
        // ============================================================

        // --- C. COMMIT (Si no hay fallo) ---
        carpetaPadre.addDirectory(nuevaCarpeta);

        // Refrescar el JTree
        DefaultTreeModel modelo = (DefaultTreeModel) arbolDirectorios.getModel();
        DefaultMutableTreeNode raizNodo = (DefaultMutableTreeNode) modelo.getRoot();
        refrescarArbolUI((Directory) raizNodo.getUserObject());

        JOptionPane.showMessageDialog(this, "Directorio '" + nombre + "' creado exitosamente.");

    } catch (Exception e) {
        if ("FALLO_SISTEMA_DIRECTORIO".equals(e.getMessage())) {
            JOptionPane.showMessageDialog(this, 
                "Fallo detectado: Error al escribir en la tabla de directorios.\nLa operación ha sido abortada.", 
                "Journal Rollback", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_crear1ActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        if (!jadminisrtador1.isSelected()) {
            JOptionPane.showMessageDialog(this,
                    "Acceso Denegado: Solo el Administrador puede modificar directorios/archivos.",
                    "Error de Permisos",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        DefaultMutableTreeNode nodoSeleccionado = (DefaultMutableTreeNode) arbolDirectorios.getLastSelectedPathComponent();

        if (nodoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un archivo o carpeta para renombrar.");
            return;
        }
        Object objeto = nodoSeleccionado.getUserObject();
        String nombreActual = objeto.toString();

        // 3. Pedir el nuevo nombre
        String nuevoNombre = JOptionPane.showInputDialog(this, "Ingrese el nuevo nombre:", nombreActual);

        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            // 4. Actualizar la lógica según el tipo de objeto
            if (objeto instanceof File) {
                ((File) objeto).setName(nuevoNombre.trim());
            } else if (objeto instanceof Directory) {
                ((Directory) objeto).setDirectoryName(nuevoNombre.trim());
            }

            // 5. Refrescar visualmente el árbol sin perder la estructura
            DefaultTreeModel modelo = (DefaultTreeModel) arbolDirectorios.getModel();
            modelo.nodeChanged(nodoSeleccionado);

            JOptionPane.showMessageDialog(this, "Nombre actualizado correctamente.");
        }
    }//GEN-LAST:event_updateActionPerformed

    private void JsonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JsonActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos JSON", "json");
        fileChooser.setFileFilter(filtro);
        int seleccion = fileChooser.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            java.io.File archivo = fileChooser.getSelectedFile();
            try {
                String contenidoJson = new String(Files.readAllBytes(Paths.get(archivo.getAbsolutePath())));

                // Esta función ahora hace TODO: valida, crea y llena la tabla visualmente.
                boolean esValido = validarEstructuraJSON(contenidoJson);

                if (esValido) {
                    // Solo mostramos el mensaje, ¡ya no borramos la tabla!
                    JOptionPane.showMessageDialog(this, "Archivo JSON leído y validado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "El archivo JSON no tiene la estructura correcta (faltan campos o hay tipos de datos incorrectos).", "Error de Formato", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al leer el archivo:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_JsonActionPerformed

    private void comboPoliticaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPoliticaActionPerformed
        if (mode == "admin") {
            //planificador
        }
    }//GEN-LAST:event_comboPoliticaActionPerformed

    private void jScrollPane2ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jScrollPane2ComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane2ComponentShown

    private void botonPruebaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPruebaActionPerformed
        // 1. Verificamos que la cola exista y tenga elementos
        if (generalRequests == null || generalRequests.getQueuesize() == 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor, carga un archivo JSON primero.", "Faltan datos", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Cabezal inicial (leído de tu planificador)
        int cabezalInicial = miDisco.getPlanificador().getPosicionCabezal();

        // 3. Obtenemos el tamaño y las peticiones
        int cantidadPeticiones = generalRequests.getQueuesize();
        int[] peticiones = new int[cantidadPeticiones];

        Request tempRequest = generalRequests.getFirstRequest();
        int indice = 0;

        while (tempRequest != null && indice < cantidadPeticiones) {
            peticiones[indice] = tempRequest.getPos();
            tempRequest = tempRequest.getNextRequest();
            indice++;
        }
        String politica = comboPolitica.getSelectedItem().toString();
    

        int[] secuencia = calcularSecuencia(politica, peticiones, cabezalInicial);

        // 6. Animación del panel visual
        javax.swing.Timer timerAnimacion = new javax.swing.Timer(800, new java.awt.event.ActionListener() {
            int pasoActual = 0;

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (pasoActual >= secuencia.length || secuencia[pasoActual] == -1) {
                    ((javax.swing.Timer) e.getSource()).stop();
                    javax.swing.JOptionPane.showMessageDialog(null, "¡Simulación finalizada con política " + politica + "!");
                    return;
                }

                int pistaDestino = secuencia[pasoActual];
                miDisco.getVistaDisco().moverCabezalVisual(pistaDestino);
                pasoActual++;
            }
        });

        timerAnimacion.start();
    }//GEN-LAST:event_botonPruebaActionPerformed

    private void botonFalloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonFalloActionPerformed
    this.simularFalloProximo = !this.simularFalloProximo; // Alterna el estado
    
    if (this.simularFalloProximo) {
        botonFallo.setBackground(Color.RED);
        botonFallo.setText("FALLO ACTIVO");
        System.out.println("JOURNAL: El sistema fallará en la próxima escritura.");
    } else {
        botonFallo.setBackground(null);
        botonFallo.setText("Simular Fallo");
        System.out.println("JOURNAL: Simulación de fallo desactivada.");
    }        // TODO add your handling code here:
    }//GEN-LAST:event_botonFalloActionPerformed
// Método auxiliar para ordenar arreglos (Bubble Sort)

    private void ordenarArreglo(int[] arr, int n) {
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // Calcula el orden de las pistas usando solo arreglos nativos
    private int[] calcularSecuencia(String politica, int[] peticiones, int cabezalInicial) {
        int n = peticiones.length;
        // El tamaño máximo será n + 2 (por si SCAN/C-SCAN añaden los extremos 0 y 199)
        int[] secuencia = new int[n + 2];

        // Inicializamos el arreglo con -1 para identificar qué espacios están vacíos
        for (int i = 0; i < secuencia.length; i++) {
            secuencia[i] = -1;
        }

        int MAX_PISTA = 199;
        int indiceSecuencia = 0;

        switch (politica.toUpperCase()) {
            case "FIFO":
                for (int i = 0; i < n; i++) {
                    secuencia[indiceSecuencia++] = peticiones[i];
                }
                break;

            case "SSTF":
                int posActual = cabezalInicial;
                boolean[] visitado = new boolean[n]; // Por defecto se inicializa en false

                for (int i = 0; i < n; i++) {
                    int indiceMasCercano = -1;
                    int menorDistancia = Integer.MAX_VALUE;

                    for (int j = 0; j < n; j++) {
                        if (!visitado[j]) {
                            int distancia = Math.abs(peticiones[j] - posActual);
                            if (distancia < menorDistancia) {
                                menorDistancia = distancia;
                                indiceMasCercano = j;
                            }
                        }
                    }
                    visitado[indiceMasCercano] = true;
                    secuencia[indiceSecuencia++] = peticiones[indiceMasCercano];
                    posActual = peticiones[indiceMasCercano];
                }
                break;

            case "SCAN":
                // Creamos un arreglo con las peticiones + el cabezal
                int[] tempScan = new int[n + 1];
                for (int i = 0; i < n; i++) {
                    tempScan[i] = peticiones[i];
                }
                tempScan[n] = cabezalInicial;

                ordenarArreglo(tempScan, tempScan.length);

                // Buscamos dónde quedó el cabezal
                int indexScan = 0;
                for (int i = 0; i < tempScan.length; i++) {
                    if (tempScan[i] == cabezalInicial) {
                        indexScan = i;
                    }
                }

                // Hacia arriba
                for (int i = indexScan + 1; i < tempScan.length; i++) {
                    secuencia[indiceSecuencia++] = tempScan[i];
                }
                // Toca el final
                if (indiceSecuencia == 0 || secuencia[indiceSecuencia - 1] != MAX_PISTA) {
                    secuencia[indiceSecuencia++] = MAX_PISTA;
                }
                // Hacia abajo
                for (int i = indexScan - 1; i >= 0; i--) {
                    secuencia[indiceSecuencia++] = tempScan[i];
                }
                break;

            case "C-SCAN":
                int[] tempCScan = new int[n + 1];
                for (int i = 0; i < n; i++) {
                    tempCScan[i] = peticiones[i];
                }
                tempCScan[n] = cabezalInicial;

                ordenarArreglo(tempCScan, tempCScan.length);

                int indexCScan = 0;
                for (int i = 0; i < tempCScan.length; i++) {
                    if (tempCScan[i] == cabezalInicial) {
                        indexCScan = i;
                    }
                }

                // Hacia arriba
                for (int i = indexCScan + 1; i < tempCScan.length; i++) {
                    secuencia[indiceSecuencia++] = tempCScan[i];
                }
                // Toca el final, salta al principio
                secuencia[indiceSecuencia++] = MAX_PISTA;
                secuencia[indiceSecuencia++] = 0;

                // Sigue subiendo desde el inicio
                for (int i = 0; i < indexCScan; i++) {
                    secuencia[indiceSecuencia++] = tempCScan[i];
                }
                break;

            default:
                for (int i = 0; i < n; i++) {
                    secuencia[indiceSecuencia++] = peticiones[i];
                }
                break;
        }

        return secuencia; // Ojo: los espacios no usados tendrán -1
    }

    public void refrescarArbolUI(Directory carpetaRaizLogica) {
        DefaultMutableTreeNode nodoRaizVisual = new DefaultMutableTreeNode(carpetaRaizLogica);
        poblarNodoRecursivo(carpetaRaizLogica, nodoRaizVisual);
        DefaultTreeModel modelo = new DefaultTreeModel(nodoRaizVisual);
        arbolDirectorios.setModel(modelo);
    }

// ==========================================
// EL MÉTODO RECURSIVO (CORREGIDO)
// ==========================================
    private void poblarNodoRecursivo(Directory carpetaLogica, DefaultMutableTreeNode nodoPadreVisual) {
        if (carpetaLogica.getDirectories() != null) {
            Directory actualDir = carpetaLogica.getDirectories().getFirstDirectory();
            while (actualDir != null) {
                DefaultMutableTreeNode nodoSubCarpeta = new DefaultMutableTreeNode(actualDir);
                nodoPadreVisual.add(nodoSubCarpeta);
                poblarNodoRecursivo(actualDir, nodoSubCarpeta);
                actualDir = actualDir.getNext();
            }
        }
        if (carpetaLogica.getFiles() != null) {
            File actualArchivo = carpetaLogica.getFiles().getFirstFile();
            while (actualArchivo != null) {
                DefaultMutableTreeNode nodoArchivo = new DefaultMutableTreeNode(actualArchivo);
                nodoPadreVisual.add(nodoArchivo);
                actualArchivo = actualArchivo.getNext();
            }
        }
    }

    private void pintarArchivoEnPanel(File archivo, Color color) {
        if (archivo == null) {
            return;
        }

        Block aux = archivo.getFirstBlock();
        while (aux != null) {
            // Marcamos el bloque en la vista visual del PanelSD
            miDisco.getVistaDisco().asignarBloqueVisual(aux.getId(), color);
            aux = aux.getNext();
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new JFramePrincipal().setVisible(true);
            } catch (Exception ex) {
                System.getLogger(JFramePrincipal.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });
    }

    private void iniciarDatosDePrueba() throws Exception {
        Queue colaReal = miDisco.getColaLibres();
        if (colaReal == null) {
            return;
        }
        Directory carpetaRaiz = new Directory("Disco C:");
        Directory carpetaFotos = new Directory("Fotos_Vacaciones");
        Directory carpetaDocumentos = new Directory("Mis_Documentos");
        carpetaRaiz.addDirectory(carpetaFotos);
        carpetaRaiz.addDirectory(carpetaDocumentos);
        File informe = new File(8, colaReal, "Admin", log);
        informe.setName("informe_final.pdf");
        carpetaDocumentos.addFile(informe);
        Color colorRojo = new Color(255, 51, 51);
        Block b1 = informe.getFirstBlock();
        while (b1 != null) {
            miDisco.getVistaDisco().asignarBloqueVisual(b1.getId(), colorRojo);
            b1 = b1.getNext();
        }

        File foto = new File(12, colaReal, "Usuario", log);
        foto.setName("foto_en_la_playa.jpg");
        carpetaFotos.addFile(foto);
        Color colorAmarillo = new Color(255, 204, 0);
        Block b2 = foto.getFirstBlock();
        while (b2 != null) {
            miDisco.getVistaDisco().asignarBloqueVisual(b2.getId(), colorAmarillo);
            b2 = b2.getNext();
        }
        File log1 = new File(4, colaReal, "System", log);
        log1.setName("sistema.log");
        carpetaRaiz.addFile(log1);
        Color colorAzul = new Color(51, 153, 255);
        Block b3 = log1.getFirstBlock();
        while (b3 != null) {
            miDisco.getVistaDisco().asignarBloqueVisual(b3.getId(), colorAzul);
            b3 = b3.getNext();
        }
        refrescarArbolUI(carpetaRaiz);
    }

    public boolean validarEstructuraJSON(String contenidoJson) throws Exception {
        try {
            System.out.println("--- INICIANDO LECTURA DE JSON ---");
            JSONObject raiz = new JSONObject(contenidoJson);

            modeloTabla.setRowCount(0);
            System.out.println("1. Tabla limpiada.");

            miDisco.reiniciarEstructura();
            generalRequests = new Queue("Requests");
            Directory directory = new Directory(raiz.getString("test_id"));
            int cabezalInicial = raiz.getInt("initial_head");
            miDisco.getPlanificador().setPosicionCabezal(cabezalInicial);

            JSONObject systemFiles = raiz.getJSONObject("system_files");
            System.out.println("2. JSON detecta " + systemFiles.length() + " archivos en system_files.");

            Iterator<String> keys = systemFiles.keys();

            while (keys.hasNext()) {
    String key = keys.next(); // Esta es la posición (ej: "11")
    int posicionInicial = Integer.parseInt(key); 
    JSONObject fileData = systemFiles.getJSONObject(key);
    String nombreArchivo = fileData.getString("name");
    int cantBloques = fileData.getInt("blocks");

    System.out.println("-> Cargando en posición " + posicionInicial + ": " + nombreArchivo);

    Color colorArchivo = obtenerColorAleatorio();
    
    // CAMBIO CLAVE: Usamos un nuevo método que crearemos en GestorDisco
    File tempFile = miDisco.cargarArchivoEnPosicionEspecifica(posicionInicial, cantBloques, "Admin", colorArchivo, log);

    if (tempFile != null) {
        tempFile.setName(nombreArchivo);
        directory.addFile(tempFile);

        // Pintar visualmente los bloques específicos
        pintarArchivoEnPanel(tempFile, colorArchivo);

        int primerBloqueId = tempFile.getFirstBlock().getId();
        modeloTabla.addRow(new Object[]{
            tempFile.getName(),
            cantBloques,
            primerBloqueId,
            colorArchivo
        });
    }
}

            JSONArray requests = raiz.getJSONArray("requests");
            System.out.println("3. Cargando " + requests.length() + " peticiones...");
            for (int i = 0; i < requests.length(); i++) {
                JSONObject request = requests.getJSONObject(i);
                Request request1 = new Request(request.getInt("pos"), request.getString("op"));
                generalRequests.addRequest(request1);
            }

            refrescarArbolUI(directory);
            panelContenedorDisco.repaint();

            System.out.println("--- LECTURA DE JSON FINALIZADA ---");
            refrescarArbolUI(directory);
            panelContenedorDisco.repaint();

            System.out.println("--- LECTURA DE JSON FINALIZADA ---");

            // ==========================================
            // AGREGA ESTAS 3 LÍNEAS AQUÍ:
            // ==========================================
            modeloTabla.fireTableDataChanged(); // Avisa que los datos cambiaron
            tablaAsignacion.revalidate();       // Recalcula el tamaño
            tablaAsignacion.repaint();          // Fuerza el dibujo en pantalla
            // ==========================================
            return true;
        } catch (JSONException e) {
            System.out.println("Error de validación (JSONException): " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Error general Exception: " + e.getMessage());
            return false;
        }
    }

    private DefaultMutableTreeNode getSelectedNode() {
        return (DefaultMutableTreeNode) arbolDirectorios.getLastSelectedPathComponent();
    }

    // ==========================================
    // CLASE PARA PINTAR LA CELDA DE COLOR EN LA TABLA
    // ==========================================
    class ColorRenderer extends JLabel implements TableCellRenderer {

        public ColorRenderer() {
            setOpaque(true); // Necesario para que el color de fondo se vea
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof Color) {
                setBackground((Color) value); // Pintamos el fondo
                setText(""); // Borramos el texto
            }
            return this;
        }
    }
// === MÉTODO PARA RECORRER EL ÁRBOL Y LIBERAR BLOQUES ===
private void eliminarNodoRecursivo(DefaultMutableTreeNode nodo) throws Exception {
    Object userObj = nodo.getUserObject();
    DefaultMutableTreeNode nodoPadre = (DefaultMutableTreeNode) nodo.getParent();

    if (userObj instanceof File) {
        File archivo = (File) userObj;
        
        // 1. Liberamos bloques y tabla (Lo que ya hacíamos)
        liberarArchivoVisualYTabla(archivo); 

        // 2. ¡CRÍTICO! Eliminar el archivo de la lista de la CARPETA LÓGICA
        if (nodoPadre != null && nodoPadre.getUserObject() instanceof Directory) {
            Directory carpetaPadre = (Directory) nodoPadre.getUserObject();
            // Suponiendo que tu clase Directory tiene un método para quitar archivos
            carpetaPadre.getFiles().removeFile(archivo); 
        }
    } 
    else if (userObj instanceof Directory) {
        // Si es carpeta, procesar hijos primero
        for (int i = nodo.getChildCount() - 1; i >= 0; i--) {
            eliminarNodoRecursivo((DefaultMutableTreeNode) nodo.getChildAt(i));
        }
        
        // Al final, quitar la subcarpeta de la carpeta padre
        if (nodoPadre != null && nodoPadre.getUserObject() instanceof Directory) {
            Directory padre = (Directory) nodoPadre.getUserObject();
            padre.getDirectories().removeDirectory((Directory) userObj);
        }
    }
}

// === MÉTODO QUE DEVUELVE LOS BLOQUES AL BITMAP (COLA DE LIBRES) ===
private void liberarArchivoVisualYTabla(File archivo) {
    Block bloqueActual = archivo.getFirstBlock();
    if (bloqueActual == null) return;

    while (bloqueActual != null) {
        Block siguienteSeguro = bloqueActual.getNext();

        // 1. INTERACCIÓN CON EL GESTOR DE DISCO (Vital para la Cola de Libres)
        // Aquí es donde el GestorDisco recupera el control del bloque
        if (miDisco.getColaLibres() != null) {
            // Limpiamos el bloque antes de devolverlo
            bloqueActual.setNext(null); 
            
            // USAR PUSH para que sea el primero en reasignarse
            miDisco.getColaLibres().pushBlock(bloqueActual); 
            
            // 2. ACTUALIZACIÓN VISUAL (A través del gestor o su vista)
            miDisco.getVistaDisco().asignarBloqueVisual(bloqueActual.getId(), Color.WHITE);
        }

        bloqueActual = siguienteSeguro;
    }
    
    archivo.setFirstBlock(null);

    // 3. LIMPIAR TABLA DE ASIGNACIÓN
    eliminarFilaDeTabla(archivo.getName());
}  
    
// === MÉTODO PARA LIMPIAR LA FILA EN LA TABLA DE ASIGNACIÓN ===
private void eliminarFilaDeTabla(String nombreArchivo) {
    DefaultTableModel modelo = (DefaultTableModel) tablaAsignacion.getModel();
    for (int i = 0; i < modelo.getRowCount(); i++) {
        if (modelo.getValueAt(i, 0).toString().equals(nombreArchivo)) {
            modelo.removeRow(i);
            break; 
        }
    }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Json;
    private javax.swing.JPanel PanelControles;
    private javax.swing.JTree arbolDirectorios;
    private javax.swing.JButton botonFallo;
    private javax.swing.JButton botonPrueba;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> comboPolitica;
    private javax.swing.JButton crear;
    private javax.swing.JButton crear1;
    private javax.swing.JButton eliminar;
    private javax.swing.JButton eliminar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JRadioButton jadminisrtador1;
    private javax.swing.JRadioButton jusuario;
    private javax.swing.JPanel paneArbol;
    private javax.swing.JPanel panelContenedorDisco;
    private javax.swing.JTable tablaAsignacion;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables
}
