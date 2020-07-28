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
public class Direccion {

    private String calle;
    private int numero;
    private int piso;
    private int departamento;
    private String bloque;
    private String comentarios;
    private String tipo;

    public Direccion(String ca, int nu, String ti, int pi, int de, String bl, String co) {
        this.setCalle(ca);
        this.setNumero(nu);
        this.setPiso(pi);
        this.setDepartamento(de);
        this.setBloque(bl);
        this.setComentarios(co);
    }
    
    public String getCalle() {
        return calle;
    }

    private void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    private void setNumero(int numero) {
        this.numero = numero;
    }

    public int getPiso() {
        return piso;
    }

    private void setPiso(int piso) {
        this.piso = piso;
    }

    public int getDepartamento() {
        return departamento;
    }

    private void setDepartamento(int departamento) {
        this.departamento = departamento;
    }

    public String getBloque() {
        return bloque;
    }

    private void setBloque(String bloque) {
        this.bloque = bloque;
    }

    public String getComentarios() {
        return comentarios;
    }

    private void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
   
}
