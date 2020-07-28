/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Negocio.Direccion;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Ferna
 */
public class DireccionConsultasSQL extends Conexion{
    
    String error=null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    public ArrayList<Direccion> listadoDirecciones(String usuario) throws SQLException, SQLException{
        PreparedStatement st= null;
        ArrayList<Direccion> direcciones = new ArrayList<Direccion>();
        Direccion dirAct = null;
        ResultSet rs= null;
        try {
            String consulta = "SELECT * FROM direcciones where usuario_direccion = ?";
            st= getConexion().prepareStatement(consulta);
            st.setString(1, usuario);
            rs = st.executeQuery();
            while (rs.next()){
                String calle,tipo,bloque,comentarios;
                int numero,piso,departamento;
                calle = rs.getString("calle_direccion");
                numero = rs.getInt("numero_direccion");
                tipo= rs.getString("tipo_direccion");
                bloque=rs.getString("bloque_direccion");
                comentarios=rs.getString("comentarios");
                piso = rs.getInt("piso_direccion");
                departamento = rs.getInt("departamento_direccion");
                dirAct= new Direccion(calle,numero,tipo,piso,departamento,bloque,comentarios);
                direcciones.add(dirAct);
            }    
        } catch (Exception e) {
            error = "Error: "+e;
        }finally{
           try {
                if (getConexion()!=null) getConexion().close();
                if (st!=null) st.close();
                if (rs!=null) rs.close();
            } catch (SQLException e) {
                error = "Error: "+e;
            }
        }
        return direcciones;
        
     
    }

    public boolean insertDireccionCliente(String calle, int numero, String tipoDireccion, int piso, int departamento, String bloque,String usuario, String comentarios) {
        PreparedStatement pst= null;
        try {
            getConexion().setAutoCommit(false);
            
            String consulta= "INSERT INTO direcciones (calle_direccion,numero_direccion,tipo_direccion,piso_direccion,departamento_direccion,bloque_direccion,usuario_direccion,comentarios) VALUES (?,?,?,?,?,?,?,?);";
            pst = getConexion().prepareStatement(consulta);
            pst.setString(1, calle);
            pst.setInt(2, numero);
            pst.setString(3, tipoDireccion);
            pst.setInt(4, piso);
            pst.setInt(5, departamento);
            pst.setString(6, bloque);
            pst.setString(7, usuario);
            pst.setString(8, comentarios);
            int ban=pst.executeUpdate();
            
            getConexion().commit();
            if(ban==1){
                return true;
            }
        } catch (SQLException e){
            error = "Error: La dirección ingresada ya se encuentra registrada";
        } finally {
            try {
                if (getConexion()!=null) getConexion().close();
                if (pst!=null) pst.close();
            } catch (SQLException e) {
                error = "Error: "+e;
            }
        }
        return false;
    }

    public boolean deleteDireccionCliente(String calle, int numero, String usuario) {
        PreparedStatement pst= null;
        
        try {
            String consulta= "DELETE FROM direcciones WHERE calle_direccion = ? and numero_direccion = ? and usuario_direccion = ?";
            pst = getConexion().prepareStatement(consulta);
            pst.setString(1, calle);
            pst.setInt(2, numero);
            pst.setString(3, usuario);
           
            if(pst.executeUpdate()>=1){
                return true;
            }
            
        } catch(SQLIntegrityConstraintViolationException e){
            error = "Error: Ya se realizó un pedido con esta dirección";
        }catch (SQLException e) {
            error = "Error: "+e;
        } finally {
            try {
                if (getConexion()!=null) getConexion().close();
                if (pst!=null) pst.close();
            } catch (SQLException e) {
                error = "Error: "+e;
            }
        }
        return false;
    }
    
}
