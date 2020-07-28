/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;


/**
 *
 * @author Ferna
 */
public class Pedido {

    private int id;
    private Date fecha;
    private Time hora;
    private String estado;
    private Float precio;
    private Time espera;
    private Direccion direccion;
    private Usuario cliente;
    private ArrayList<LineaPedido> lineas;    
    private String usuarioAdministrador;
    private Date fechaConfirmacion;
    private Time horaConfirmacion; 
    
    private static float envio = 15.0f;

    public static float getEnvio() {
        return envio;
    }

    public Pedido(int idpedido,Date fechaPed, Time horaPed, String estadoPed, Time esperaPed, Usuario cli, Direccion direccion, String adm, Date fechaConf, Time horaConf, ArrayList<LineaPedido> lin) {
        this.setId(idpedido);
        this.setFecha(fechaPed);
        this.setHora(horaPed);
        this.setEstado(estadoPed);
        this.setPrecio();
        this.setEspera(esperaPed);
        this.setDireccion(direccion);
        this.setCliente(cli);
        this.setUsuarioAdministrador(adm);
        this.setLineas(lin);
        this.setFechaConfirmacion(fechaConf);
        this.setHoraConfirmacion(horaConf);
    }

    public Pedido() {
       
    }

    public Date getFechaConfirmacion() {
        return fechaConfirmacion;
    }

    public void setFechaConfirmacion(Date fechaConf) {
        this.fechaConfirmacion = fechaConf;
    }

    public Time getHoraConfirmacion() {
        return horaConfirmacion;
    }

    public void setHoraConfirmacion(Time horaConf) {
        this.horaConfirmacion = horaConf;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio() {
        Float total = 0f;
        if (lineas!=null){
            for (int i=0;i<lineas.size();i++){
                total= total+lineas.get(i).getCantidad()*lineas.get(i).getProducto().getPrecio();
            }
        total=total + envio;
        }
        precio = total;
    }

    public Time getEspera() {
        return espera;
    }

    public void setEspera(Time espera) {
        this.espera = espera;
    }
    
    public void setDireccion (Direccion direccion){
        this.direccion = direccion;
    }

    public String getCalleDireccion() {
        return direccion.getCalle();
    }

    public int getNumeroDireccion() {
        return direccion.getNumero();
    }

    public String getUsuarioAdministrador() {
        return usuarioAdministrador;
    }

    public void setUsuarioAdministrador(String usuarioAdm) {
        this.usuarioAdministrador = usuarioAdm;
    }

    public ArrayList<LineaPedido> getLineas() {
        return lineas;
    }

    public void setLineas(ArrayList<LineaPedido> lineas) {
        this.lineas = lineas;
    }

    public String getUsuarioCliente() {
        return cliente.getUsuario();
    }
    public String getApellidoCliente() {
        return cliente.getApellidoUsuario();
    }
    public String getNombreCliente() {
        return cliente.getNombreUsuario();
    }
    
    public String getTelefonoCliente(){
        return cliente.getTelefono();
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }
   
    public void borrarLinea(int num){
        for (int i=0;i<lineas.size();i++){
            if (lineas.get(i).getNumeroLinea()==num){
                lineas.remove(i);
            }
        }
    }

 
}
