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
public class LineaPedido {

    
    private int cantidad;
    private Producto producto;
    private int numeroLinea;

    public LineaPedido(int cant,Producto prodAct, int num) {
        this.setCantidad(cant);
        this.setProducto(prodAct);
        this.setNumeroLinea(num);
    }
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cant) {
        this.cantidad = cant;
    }

    public void setProducto(Producto prodAct) {
        this.producto=prodAct;
    }
    
    public Producto getProducto(){
        return producto;
    }

    public void setNumeroLinea(int num) {
        this.numeroLinea=num;
    }
    
    public int getNumeroLinea(){
        return numeroLinea;
    }
}
