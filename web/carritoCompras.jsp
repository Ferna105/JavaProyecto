<%-- 
    Document   : carritoCompras
    Created on : 28-dic-2016, 3:04:32
    Author     : Ferna
--%>

<%@page import="Datos.DireccionConsultasSQL"%>
<%@page import="Negocio.Direccion"%>
<%@page import="Negocio.LineaPedido"%>
<%@page import="Negocio.Pedido"%>
<%@page import="Negocio.Usuario"%>
<%@page import="Datos.ProductoConsultasSQL"%>
<%@page import="Negocio.Producto"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    HttpSession objSesion = request.getSession(false);
    Pedido pedido = (Pedido) objSesion.getAttribute("pedidoActual");
    Usuario userActual = (Usuario) objSesion.getAttribute("usuarioActual");
    objSesion.removeAttribute("error");
    String notificacion = "";
    if (objSesion.getAttribute("notificacion") != null){
        notificacion = (String) objSesion.getAttribute("notificacion");
    }
    if(userActual == null){
        response.sendRedirect("index.jsp");
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <link rel="stylesheet" href="Estilo/estilo.css">
        <title>Rotiseria Imperio</title>
    </head>
    <body>
        <img src="Imagenes/logo imperio.png" class="logo">
        <div class="login">
            <form action="Logout" method="post">
                <label>Hola, <%=userActual.getNombreUsuario()%>!</label>
                <input type="submit" value="Cerrar Sesión">
            </form>
        </div>
         <br>
        <br>
        <div class="menu">
            <%if (userActual.getTipoUsuario().equals("Administrador")){ %>
            <ul class="nav">
                <li><a href="cambiaDatosCuenta.jsp">Modificar datos Personales</a></li>
                <li><a href="listadoPedidos.jsp">Todos los Pedidos</a></li>
                <li><a href="listadoPedidosPendientes.jsp">Pedidos Pendientes</a></li>  
                <li><a href="listadoPedidosRechazados.jsp">Pedidos Rechazados</a></li>
                <li><a href="listadoPedidosConfirmados.jsp">Pedidos Confirmados</a></li>
            </ul>
            <%}%>
               
            <%if (userActual.getTipoUsuario().equals("Cliente")){ %>
            <ul class="nav">
                <li><a href="miCuenta.jsp">Mi Cuenta</a>
                    <ul>
                        <li><a href="agregarDireccion.jsp">Agregar Dirección</a></li>
                        <li><a href="eliminarDireccion.jsp">Eliminar Dirección</a></li>
                        <li><a href="cambiaDatosCuenta.jsp">Modificar datos Personales</a></li>
                        <li><a href="listadoPedidosUsuario.jsp">Ver mis Pedidos</a></li>
                    </ul>
                </li>
                <li><a href="listadoProductos.jsp">Productos</a>
                    <ul>
                        <li><a href="listadoBebidas.jsp">Bebidas</a></li>
                        <li><a href="listadoEnsaladas.jsp">Ensaladas</a></li>
                        <li><a href="listadoOtros.jsp">Otros</a></li>
                        <li><a href="listadoPastas.jsp">Pastas</a></li>
                        <li><a href="listadoPizzanesas.jsp">Pizzanesas</a></li>
                        <li><a href="listadoPizzas.jsp">Pizzas</a></li>
                        <li><a href="listadoPostres.jsp">Postres</a></li>
                        <li><a href="listadoSandwiches.jsp">Sandwiches</a></li>
                        <li><a href="listadoCarne.jsp">Variedades en Carne</a></li>
                        <li><a href="listadoPescado.jsp">Variedades en Pescado</a></li>
                        <li><a href="listadoPollo.jsp">Variedades en Pollo</a></li>
                    </ul>
                </li>
                <li><a href="carritoCompras.jsp">Mi pedido</a></li>
                <li><a href="imperio.jsp">Sobre Nosotros</a></li>
            <%}%>
            </ul>
        </div>
        
        <br>
        <br>
        <br>
        <br>
        <br>
        
        <label align="right" style="color:yellow;"><%=notificacion%></label>
        <%objSesion.removeAttribute("notificacion");%>
        

        <div style="border: 1px solid black; background: #004466">
            <nav style="background: #000" align="center""><h1>Pedido en el Carrito de Compras</h1></nav>
            <% if (objSesion.getAttribute("pedidoActual")!=null){%>
            <table border="1" style="margin: 0 auto">  
                <thead>
                    <td><label>Nombre Producto</label>
                    <td><label>Precio</label>
                    <td><label>Cantidad</label>
                    <td><label>Subtotal</label>
                </thead>
            <%ArrayList<LineaPedido> lineas = new ArrayList<LineaPedido>();
            lineas = pedido.getLineas();
            for (int i=0;i<lineas.size();i++){%>
            <form action="EliminarProductoCarrito" method="post">
                <tr>
                    <td><%=lineas.get(i).getProducto().getNombre()%>
                    <td>$<%=lineas.get(i).getProducto().getPrecio()%>
                    <td><%=lineas.get(i).getCantidad()%>
                    <td>$<%=lineas.get(i).getCantidad()*lineas.get(i).getProducto().getPrecio()%>
                        <input type="hidden" name="numeroLinea" value="<%=lineas.get(i).getNumeroLinea()%>"
                    <td><input type="submit" value="Eliminar">
                </tr>
            </form>
            <%}%>
            <tr>
                <td colspan="3"><label>Envío </label>
                <td>$<%=pedido.getEnvio()%>
            </tr>
            <tr>
                <td colspan="3"><label>Total </label>
                <td>$<%=pedido.getPrecio()%>               
            </tr>
            </table>
            
            <br>
            <table style="margin: 0 auto">
            <form action="RegistraPedido" method="post">
            
                <td><label align="center">Seleccione dirección: </label>
                <td><select name="indice">
                <% 
                    ArrayList<Direccion> direcciones = new ArrayList<Direccion>();
                    DireccionConsultasSQL dir = new DireccionConsultasSQL();
                    direcciones= dir.listadoDirecciones(userActual.getUsuario());
                    for ( int i=0;i<direcciones.size();i++){ %>
                        <option value="<%=i%>"> <%=direcciones.get(i).getCalle()%> <%=direcciones.get(i).getNumero()%>
                            <%if (!direcciones.get(i).getBloque().equals("")){%>
                            - Bloque: <%=direcciones.get(i).getBloque()%> -
                            <%}%>
                            <%if (direcciones.get(i).getPiso()!= 0){%>
                                <%=direcciones.get(i).getPiso()%>º 
                            <%}%>
                            <%if (direcciones.get(i).getDepartamento()!= 0){%>
                                <%=direcciones.get(i).getDepartamento()%> 
                            <%}%>
                            <%if (!direcciones.get(i).getComentarios().equals("")){%>
                                -<%=direcciones.get(i).getComentarios()%>- 
                            <%}%>    
                        </option>
                    <%}%>
                </select>
                <td><input type="hidden" name="usuario" value="<%=userActual.getUsuario()%>">
                <input type="submit" value="Realizar Pedido">
            
            </form>
            </table>
            <br>
        </div> 
        <%}else{%>
            <label>No tiene ningún pedido pendiente</label>
        <%}%>
        