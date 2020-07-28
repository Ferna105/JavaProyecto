/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

/**
 *
 * @author Ferna
 */
public class Calle {
    
    private String nombre;
    private int alturaMax;
    private int alturaMin;

    public Calle(String nom, int min, int max) {
       nombre=nom;
       alturaMax=max;
       alturaMin=min;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAlturaMax() {
        return alturaMax;
    }

    public void setAlturaMax(int alturaMax) {
        this.alturaMax = alturaMax;
    }

    public int getAlturaMin() {
        return alturaMin;
    }

    public void setAlturaMin(int alturaMin) {
        this.alturaMin = alturaMin;
    }
    
    
}
