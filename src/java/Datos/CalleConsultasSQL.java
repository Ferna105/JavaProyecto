/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Negocio.Calle;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Ferna
 */
public class CalleConsultasSQL extends Conexion {
    
    private String error=null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    public ArrayList<Calle> listadoCalles() throws SQLException, SQLException{
        Statement st= null;
        ArrayList<Calle> call = new ArrayList<Calle>();
        Calle calleAct = null;
        ResultSet rs= null;
        try {
            st= getConexion().createStatement();
            rs = st.executeQuery("SELECT * FROM calles order by nombre_calle");
            
            while (rs.next()){
                int min,max;
                String nom;
                nom = rs.getString("nombre_calle");
                min = rs.getInt("numero_minimo");
                max= rs.getInt("numero_maximo");
                calleAct = new Calle(nom,min,max);
                call.add(calleAct);
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
        return call;
        
     
    }
    
}
