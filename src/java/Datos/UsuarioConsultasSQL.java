/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Negocio.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 *
 * @author Ferna
 */
public class UsuarioConsultasSQL extends Conexion {
    
    private String error=null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    
    
    public Usuario autenticacion(String usuario, String contraseña){
        PreparedStatement pst = null;
        ResultSet rs = null;
        Usuario usuarioAct = new Usuario();
        
        try {
            String consulta = "SELECT * FROM usuarios WHERE usuario = ? AND password = ?";
            pst = getConexion().prepareStatement(consulta);
            pst.setString(1, usuario);
            pst.setString(2, contraseña);
            rs = pst.executeQuery();
            
            if (rs.absolute(1)){
                usuarioAct.setUsuario(rs.getString("usuario"));
                usuarioAct.setContraseña(rs.getString("password"));
                usuarioAct.setNombreUsuario(rs.getString("nombre_usuario"));
                usuarioAct.setApellidoUsuario(rs.getString("apellido_usuario"));
                usuarioAct.setTipoUsuario(rs.getString("tipo_usuario"));
                usuarioAct.setTelefono(rs.getString("telefono"));
                usuarioAct.setMail(rs.getString("mail"));
                return usuarioAct;
            }
        } catch (Exception e) {
            error = "Error: "+e;
        } finally{
            try {
                 if (getConexion()!= null) getConexion().close();
                 if (pst!=null) pst.close();
                 if (rs!= null) rs.close();
            } catch (Exception e) {
                error = "Error: "+e;
            }   
        }
        usuarioAct=null;
        return usuarioAct;
    }

    public boolean insertUsuarioCliente(String usuario, String contraseña, String tipoUsuario, String nombre, String apellido, String telefono, String mail, String calle, int numero, String tipoDireccion, int piso, int departamento, String bloque, String comentarios) {
        PreparedStatement pst= null;
        try {
            getConexion().setAutoCommit(false);
            String consulta= "INSERT INTO usuarios (usuario,password,tipo_usuario,nombre_usuario,apellido_usuario,telefono,mail) VALUES (?,?,?,?,?,?,?);";
            pst = getConexion().prepareStatement(consulta);
            pst.setString(1, usuario);
            pst.setString(2, contraseña);
            pst.setString(3, tipoUsuario);
            pst.setString(4, nombre);
            pst.setString(5, apellido);
            pst.setString(6, telefono);
            pst.setString(7, mail);
            int ban=pst.executeUpdate();
            
            String consulta2= "INSERT INTO direcciones (calle_direccion,numero_direccion,tipo_direccion,piso_direccion,departamento_direccion,bloque_direccion,usuario_direccion,comentarios) VALUES (?,?,?,?,?,?,?,?);";
            pst = getConexion().prepareStatement(consulta2);
            pst.setString(1, calle);
            pst.setInt(2, numero);
            pst.setString(3, tipoDireccion);
            pst.setInt(4, piso);
            pst.setInt(5, departamento);
            pst.setString(6, bloque);
            pst.setString(7, usuario);
            pst.setString(8, comentarios);
            int ban2=pst.executeUpdate();
            
            getConexion().commit();
            if(ban==1 && ban2==1){
                return true;
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            error = "Error: Nombre de usuario existente";
        } catch(SQLException e){
            error= "Error: "+e;
        }finally {
            try {
                if (getConexion()!=null) getConexion().close();
                if (pst!=null) pst.close();
            } catch (SQLException e) {
                error = "Error: "+e;
            }
        }
        return false;
    }

    public boolean updateCliente(String usuario, String nombre, String apellido, String telefono, String mail, String contraseña, String tipoUsuario) {
        PreparedStatement pst = null;
        ResultSet rs= null;
        try {
            pst = getConexion().prepareStatement("UPDATE usuarios SET password=?, tipo_usuario=?, nombre_usuario =?, apellido_usuario=?, telefono=?,mail=? WHERE usuario = ?");
            pst.setString(1,contraseña);
            pst.setString(2,tipoUsuario);
            pst.setString(3,nombre);
            pst.setString(4,apellido);
            pst.setString(5,telefono);
            pst.setString(6,mail);
            pst.setString(7,usuario);
            
            if(pst.executeUpdate()==1){
                return true;
            }
        } catch (Exception e) {
            error = "Error: "+e;
        } finally{
            try{
                if (pst!=null) pst.close();
                if (getConexion()!=null) getConexion().close();
                if (rs!= null) rs.close();
            } catch(Exception e) {
                error = "Error: "+e;
                }
        }
        return false;
    }
}
