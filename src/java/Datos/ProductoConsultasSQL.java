/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Negocio.Producto;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Ferna
 */
public class ProductoConsultasSQL extends Conexion {
    
    String error=null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    public ArrayList<Producto> listadoProductos(String condicionListado){
        PreparedStatement pst = null;
        ArrayList<Producto> productos = new ArrayList<Producto>();
        Producto prodActual = null;
        ResultSet rs = null;
        String consulta;
        try {
            consulta = "SELECT * FROM productos where tipo_producto like ? order by nombre_producto";
            pst= getConexion().prepareStatement(consulta);
            pst.setString(1,condicionListado);
            rs = pst.executeQuery();

            while (rs.next()){
                int id;
                String nombre, desc , tipo, imagen;
                Float precio;
                id = rs.getInt("id_producto");
                nombre = rs.getString("nombre_producto");
                desc= rs.getString("descripcion_producto");
                tipo=rs.getString("tipo_producto");
                precio=rs.getFloat("precio_producto");
                imagen = rs.getString("imagen_producto");
                prodActual = new Producto(id,nombre,desc,tipo,precio,imagen);
                productos.add(prodActual);
            }
            
        } catch (Exception e) {
            error = "Error: "+e;
        }finally{
           try {
                if (getConexion()!=null) getConexion().close();
                if (pst!=null) pst.close();
                if (rs!=null) rs.close();
            } catch (SQLException e) {
                error = "Error: "+e;
            }
        }
        return productos;
        
    }
}
