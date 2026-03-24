/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sisoperativos2costatucci.logic;

/**
 *
 * @author astv06
 */
public class Row {
    private Block beforeChange;
    private Block afterChange;
    private boolean checkpoint;
    private Row nextRow;

    public Block getBeforeChange() {
        return beforeChange;
    }

    public Block getAfterChange() {
        return afterChange;
    }

    public boolean isCheckpoint() {
        return checkpoint;
    }

    public Row getNextRow() {
        return nextRow;
    }

    public void setBeforeChange(Block beforeChange) {
        this.beforeChange = beforeChange;
    }

    public void setAfterChange(Block afterChange) {
        this.afterChange = afterChange;
    }

    public void setCheckpoint(boolean checkpoint) {
        this.checkpoint = checkpoint;
    }

    public void setNextRow(Row nextRow) {
        this.nextRow = nextRow;
    }

    public Row(Block beforeChange, Block afterChange, boolean checkpoint) {
        this.beforeChange = beforeChange;
        this.afterChange = afterChange;
        this.checkpoint = checkpoint;
        this.nextRow = null;
    }
    
}
