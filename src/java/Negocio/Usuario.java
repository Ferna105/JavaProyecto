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
public class Usuario {

    
    String usuario;
    String contraseña;
    String tipoUsuario;
    String nombreUsuario;
    String apellidoUsuario;
    String telefono;
    String mail;

    public Usuario(String usu, String con, String tipoUsu, String nombre, String apellido, String telefono, String mail) {
        this.usuario = usu;
        this.contraseña = con;
        this.tipoUsuario = tipoUsu;
        this.nombreUsuario = nombre;
        this.apellidoUsuario = apellido;
        this.telefono = telefono;
        this.mail = mail;
    }

    public Usuario() {
       
    }
    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombre) {
        this.nombreUsuario = nombre;
    }

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public void setApellidoUsuario(String apellido) {
        this.apellidoUsuario = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail(){
        return mail;
    }
    
    public void setMail(String mail) {
        this.mail = mail;
    }
    
}
