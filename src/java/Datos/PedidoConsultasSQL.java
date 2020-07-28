/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Negocio.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 *
 * @author Ferna
 */
public class PedidoConsultasSQL extends Conexion{
    
    private String error=null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    public ArrayList<Pedido> listadoPedidosCliente(String usuario) throws SQLException, SQLException{
        PreparedStatement st= null;
        ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
        Pedido pedidoActual = null;
        Usuario cliente = null;
        Direccion direccionPedidoActual = null;
        ArrayList<LineaPedido> lineasPedido  = new ArrayList<LineaPedido>();
        LineaPedido lineaActual = null;
        Producto prodAct = null;
        ResultSet rs= null;
        try {
            String consulta = "select p.id_pedido,p.fecha_pedido,p.hora_pedido,p.fecha_confirmacion,p.hora_confirmacion,p.estado,p.precio_pedido,p.espera_estimada,p.usuario_cliente,"+
                " p.calle_direccion,p.numero_direccion,dir.tipo_direccion,dir.piso_direccion,dir.departamento_direccion,"+
                " dir.bloque_direccion,dir.comentarios,p.usuario_administrador,lp.id_linea,lp.cantidad,pr.id_producto,"+
                " u.password,u.tipo_usuario,u.nombre_usuario,u.apellido_usuario,u.telefono,u.mail,"+
                " pr.nombre_producto,pr.descripcion_producto,pr.tipo_producto, pr.precio_producto, pr.imagen_producto from pedidos p"+
                " inner join lineaspedidos lp on p.id_pedido = lp.id_pedido"+
                " inner join productos pr on lp.id_producto = pr.id_producto"+ 
                " inner join direcciones dir on dir.calle_direccion = p.calle_direccion and dir.numero_direccion = p.numero_direccion and dir.usuario_direccion = p.usuario_cliente"+
                " inner join usuarios u on p.usuario_cliente = u.usuario"+
                " where p.usuario_cliente = ? order by p.id_pedido desc , lp.id_linea desc";
            st= getConexion().prepareStatement(consulta);
            st.setString(1, usuario);
            rs = st.executeQuery();
            int idPed = 0;
            while (rs.next()){
                int idlinea = rs.getInt("id_linea");
                int idpedido = rs.getInt("id_pedido");
                int cant = rs.getInt("cantidad");
                int idProd = rs.getInt("id_producto");
                String nombreProducto = rs.getString("nombre_producto");
                String descProducto= rs.getString("descripcion_producto");
                String tipoProducto =rs.getString("tipo_producto");
                Float precioProducto = rs.getFloat("precio_producto");
                String imagenProducto = rs.getString("imagen_producto");
                prodAct = new Producto(idProd,nombreProducto,descProducto,tipoProducto,precioProducto,imagenProducto);
                if (idpedido!= idPed){
                    lineasPedido = new ArrayList<LineaPedido>();
                }  
                lineaActual = new LineaPedido(cant,prodAct,idlinea);
                lineasPedido.add(lineaActual);
                java.sql.Date fechaConf = rs.getDate("fecha_confirmacion");
                java.sql.Time horaConf = rs.getTime("hora_confirmacion");
                java.sql.Date fechaPed = rs.getDate("fecha_pedido");
                java.sql.Time horaPed = rs.getTime("hora_pedido");
                String estadoPed =rs.getString("estado");
                java.sql.Time esperaPed = rs.getTime("espera_estimada");
                String usuarioAdministrador= rs.getString("usuario_administrador");
                String calleDireccion = rs.getString("calle_direccion");
                int numeroDireccion = rs.getInt("numero_direccion");
                String tipoDireccion = rs.getString("tipo_direccion");
                int pisoDireccion = rs.getInt("piso_direccion");
                int departamentoDireccion = rs.getInt("departamento_direccion");
                String bloqueDireccion = rs.getString("bloque_direccion");
                String comentarios = rs.getString("comentarios");
                String usuCli = rs.getString("usuario_cliente");
                String conCli = rs.getString("password");
                String tipoCli = rs.getString("tipo_usuario");
                String nomCli = rs.getString("nombre_usuario");
                String apeCli = rs.getString("apellido_usuario");
                String telCli = rs.getString("telefono");
                String mailCli = rs.getString("mail");
                
                if (idpedido!= idPed){
                    cliente = new Usuario(usuCli, conCli, tipoCli, nomCli, apeCli, telCli, mailCli);
                    direccionPedidoActual = new Direccion(calleDireccion,numeroDireccion,tipoDireccion,pisoDireccion,departamentoDireccion,bloqueDireccion,comentarios);
                    pedidoActual = new Pedido(idpedido,fechaPed,horaPed,estadoPed,esperaPed,cliente,direccionPedidoActual,usuarioAdministrador,fechaConf,horaConf,lineasPedido);
                    pedidos.add(pedidoActual);
                    idPed=idpedido;
                }
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
        return pedidos;
    }

    public boolean RegistroPedido(String usuario, Direccion direccion, Pedido pedido) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        Calendar c2 = new GregorianCalendar();
        String fec = Integer.toString(c2.get(Calendar.YEAR))+"-"+Integer.toString(c2.get(Calendar.MONTH))+"-"+Integer.toString(c2.get(Calendar.DATE));
        String hor = Integer.toString(c2.get(Calendar.HOUR_OF_DAY))+":"+Integer.toString(c2.get(Calendar.MINUTE))+":"+Integer.toString(c2.get(Calendar.SECOND));
        java.sql.Date fecha = java.sql.Date.valueOf(fec);
        java.sql.Time hora = java.sql.Time.valueOf(hor);
        ArrayList<LineaPedido> lineas = new ArrayList<LineaPedido>();
        lineas = pedido.getLineas();
         
 
        try{
            getConexion().setAutoCommit(false);
            String consulta= "INSERT INTO pedidos (precio_pedido,usuario_cliente,calle_direccion,numero_direccion,fecha_pedido,hora_pedido) VALUES (?,?,?,?,?,?);";
            pst = getConexion().prepareStatement(consulta);
            pst.setFloat(1, pedido.getPrecio());
            pst.setString(2, usuario);
            pst.setString(3, direccion.getCalle());
            pst.setInt(4, direccion.getNumero());
            pst.setDate(5, fecha);
            pst.setTime(6, hora);
            int ban=pst.executeUpdate();
            
            String consulta2="select p.id_pedido from pedidos p where usuario_cliente=? and fecha_pedido = ? and hora_pedido = ?;";
            pst =getConexion().prepareStatement(consulta2);
            pst.setString(1, usuario);
            pst.setDate(2, fecha);
            pst.setTime(3, hora);
            rs = pst.executeQuery();
            int numPedido=0;
            while (rs.next()){
                numPedido=rs.getInt("id_pedido");
            }
            
            int ban2=1;
            for (int i=0;i<lineas.size();i++){
                String consulta3="insert into lineaspedidos (id_pedido,id_linea,id_producto,cantidad) values (?,?,?,?)";
                pst = getConexion().prepareStatement(consulta3);
                pst.setInt(1, numPedido);
                pst.setInt(2, lineas.get(i).getNumeroLinea());
                pst.setInt(3, lineas.get(i).getProducto().getId());
                pst.setInt(4, lineas.get(i).getCantidad());
                int band=pst.executeUpdate();
                if (band==0){
                    ban2=0;
                }
            }
            
            getConexion().commit();
            if(ban==1 && ban2==1){
                return true;
            }

        }catch (Exception e) {
            error = "Error: "+e;
        } finally {
            try {
                if (getConexion()!=null) getConexion().close();
                if (pst!=null) pst.close();
                if (rs!=null) rs.close();
            } catch (SQLException e) {
                error = "Error: "+e;
            }
        }
        
        
        return false;  
    }

    public ArrayList<Pedido> listadoPedidosPorEstado(String estado) throws SQLException, SQLException{
        PreparedStatement st= null;
        ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
        Pedido pedidoActual = null;
        Direccion direccionPedidoActual= null;
        Usuario cliente = null;
        ArrayList<LineaPedido> lineasPedido  = new ArrayList<LineaPedido>();
        LineaPedido lineaActual = null;
        Producto prodAct = null;
        ResultSet rs= null;
        try {
            String consulta = "select p.id_pedido,p.fecha_pedido,p.hora_pedido,p.fecha_confirmacion,p.hora_confirmacion,p.estado,p.precio_pedido,p.espera_estimada,p.usuario_cliente,"+
                " p.calle_direccion,p.numero_direccion,dir.tipo_direccion,dir.piso_direccion,dir.departamento_direccion,"+
                " p.usuario_administrador,u.password,u.tipo_usuario,u.nombre_usuario,u.apellido_usuario,u.telefono,u.mail,"+
                " dir.bloque_direccion,dir.comentarios,lp.id_linea,lp.cantidad,pr.id_producto,"+
                " pr.nombre_producto,pr.descripcion_producto,pr.tipo_producto, pr.precio_producto, pr.imagen_producto from pedidos p"+
                " inner join lineaspedidos lp on p.id_pedido = lp.id_pedido"+
                " inner join productos pr on lp.id_producto = pr.id_producto"+ 
                " inner join direcciones dir on dir.calle_direccion = p.calle_direccion and dir.numero_direccion = p.numero_direccion and dir.usuario_direccion = p.usuario_cliente"+
                " inner join usuarios u on p.usuario_cliente = u.usuario"+
                " where p.estado like ? order by p.id_pedido desc , lp.id_linea desc";
            st= getConexion().prepareStatement(consulta);
            st.setString(1, estado);
            rs = st.executeQuery();
            int idPed = 0;
            while (rs.next()){

                int idlinea = rs.getInt("id_linea");
                int idpedido = rs.getInt("id_pedido");
                int cant = rs.getInt("cantidad");
                int idProd = rs.getInt("id_producto");
                String nombreProducto = rs.getString("nombre_producto");
                String descProducto= rs.getString("descripcion_producto");
                String tipoProducto =rs.getString("tipo_producto");
                Float precioProducto = rs.getFloat("precio_producto");
                String imagenProducto = rs.getString("imagen_producto");
                prodAct = new Producto(idProd,nombreProducto,descProducto,tipoProducto,precioProducto,imagenProducto);
                if (idpedido!= idPed){
                    lineasPedido = new ArrayList<LineaPedido>();
                }  
                lineaActual = new LineaPedido(cant,prodAct,idlinea);
                lineasPedido.add(lineaActual);
                java.sql.Date fechaPed = rs.getDate("fecha_pedido");
                java.sql.Time horaPed = rs.getTime("hora_pedido");
                java.sql.Date fechaConf = rs.getDate("fecha_confirmacion");
                java.sql.Time horaConf = rs.getTime("hora_confirmacion");
                String estadoPed =rs.getString("estado");
                java.sql.Time esperaPed = rs.getTime("espera_estimada");
                String usuarioAdministrador= rs.getString("usuario_administrador");
                String calleDireccion = rs.getString("calle_direccion");
                int numeroDireccion = rs.getInt("numero_direccion");
                String tipoDireccion = rs.getString("tipo_direccion");
                int pisoDireccion = rs.getInt("piso_direccion");
                int departamentoDireccion = rs.getInt("departamento_direccion");
                String bloqueDireccion = rs.getString("bloque_direccion");
                String comentarios = rs.getString("comentarios");
                String usuCli = rs.getString("usuario_cliente");
                String conCli = rs.getString("password");
                String tipoCli = rs.getString("tipo_usuario");
                String nomCli = rs.getString("nombre_usuario");
                String apeCli = rs.getString("apellido_usuario");
                String telCli = rs.getString("telefono");
                String mailCli = rs.getString("mail");
                
                if (idpedido!= idPed){
                    cliente = new Usuario(usuCli, conCli, tipoCli, nomCli, apeCli, telCli, mailCli);
                    direccionPedidoActual = new Direccion(calleDireccion,numeroDireccion,tipoDireccion,pisoDireccion,departamentoDireccion,bloqueDireccion,comentarios);
                    pedidoActual = new Pedido(idpedido,fechaPed,horaPed,estadoPed,esperaPed,cliente,direccionPedidoActual,usuarioAdministrador,fechaConf,horaConf,lineasPedido);
                    pedidos.add(pedidoActual);
                    idPed=idpedido;
                }
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
        return pedidos;
    }

    public boolean actualizaEstadoPedido(String usuario, String fecha, String hora, String valor, String espera,String usuAdmin) {
        
        PreparedStatement pst = null;
        Calendar c2 = new GregorianCalendar();
        String fechaConfirmacion = Integer.toString(c2.get(Calendar.YEAR))+"-"+Integer.toString(c2.get(Calendar.MONTH))+"-"+Integer.toString(c2.get(Calendar.DATE));
        String horaConfirmacion = Integer.toString(c2.get(Calendar.HOUR_OF_DAY))+":"+Integer.toString(c2.get(Calendar.MINUTE))+":"+Integer.toString(c2.get(Calendar.SECOND));
        java.sql.Date fec = java.sql.Date.valueOf(fecha);
        java.sql.Time hor = java.sql.Time.valueOf(hora);
        java.sql.Time esp = java.sql.Time.valueOf(espera);
        java.sql.Date fecConf = java.sql.Date.valueOf(fechaConfirmacion);
        java.sql.Time horConf = java.sql.Time.valueOf(horaConfirmacion);
        
        try{
            String consulta="UPDATE pedidos set fecha_confirmacion = ?, hora_confirmacion = ?, estado=?, espera_estimada=?, usuario_administrador=? where usuario_cliente = ? and fecha_pedido = ? and hora_pedido = ?";
            pst = getConexion().prepareStatement(consulta);
            pst.setDate(1, fecConf);
            pst.setTime(2,horConf);
            pst.setString(3, valor);
            pst.setTime(4, esp);
            pst.setString(5, usuAdmin);
            pst.setString(6, usuario);
            pst.setDate(7, fec);
            pst.setTime(8, hor);
            if(pst.executeUpdate()==1){
                    return true;
            }  
            
        } catch (Exception e) {
            error = "Error: "+e;
            
        } finally{
            try{
                if (pst!=null) pst.close();
                if (getConexion()!=null) getConexion().close();
            } catch(Exception e) {
                    error = "Error: "+e;
                }
        }
        return false;
    }
 
}