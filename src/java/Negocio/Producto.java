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
public class Producto {
    private int id;
    private String nombre;
    private String descripcion;
    private String tipo;
    private Float precio;
    private String imagen;

    public Producto(int idProd, String nombreProducto, String descProducto, String tipoProducto, Float precioProducto,String imagenProducto) {
        this.setId(idProd);
        this.setNombre(nombreProducto);
        this.setDescripcion(descProducto);
        this.setTipo(tipoProducto);
        this.setPrecio(precioProducto);
        this.setImagen(imagenProducto);
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String ima) {
        this.imagen = ima;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nom) {
        this.nombre = nom;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String desc) {
        this.descripcion = desc;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tip) {
        this.tipo = tip;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float prec) {
        this.precio = prec;
    }
   
}
