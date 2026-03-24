/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sisoperativos2costatucci.logic;

/**
 *
 * @author astv06
 */
public class Recovery {
    public int processedCount = 0;   // Cuántas filas del Log se aplicaron
    public String errorMessage = ""; // Qué causó la interrupción
    public boolean success = false;  // ¿Se completó todo el Log?

    public Recovery() {
        this.processedCount = 0;
        this.success = false;
        this.errorMessage = "";
    }
}

