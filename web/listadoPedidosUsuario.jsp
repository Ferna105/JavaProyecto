<%-- 
    Document   : listadoPedidosUsuario
    Created on : 28-nov-2016, 0:06:04
    Author     : Ferna
--%>

<%@page import="Datos.PedidoConsultasSQL"%>
<%@page import="Negocio.Pedido"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Negocio.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    HttpSession objSesion = request.getSession(false);
    Usuario userActual = (Usuario) objSesion.getAttribute("usuarioActual");
    String nombreUsuario = userActual.getNombreUsuario();
    String notificacion = "";
    if (objSesion.getAttribute("notificacion") != null){
        notificacion = (String) objSesion.getAttribute("notificacion");
    }
    if(nombreUsuario.equals("") || userActual.getTipoUsuario().equals("Administrador")){
        response.sendRedirect("index.jsp");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
        
        <div  style="border: 1px solid black; background: #004466">

            <nav style="background: #000"><h1 align="center">Pedidos Realizados</h1></nav>
            <table border="1" style="margin: 0 auto;">  
                
            <% 
                ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
                PedidoConsultasSQL ped = new PedidoConsultasSQL();
                pedidos= ped.listadoPedidosCliente(userActual.getUsuario());
                if (!pedidos.isEmpty()){%>
                    <th>Datos
                    <th>Productos
                <%}else{%>
                    <label>Aún no se han realizado pedidos</label>
                <%}
                for ( int i=0;i<pedidos.size();i++){%>
            
            <tr>
                <td>Fecha: <%=pedidos.get(i).getFecha()%>
                    <br>Hora: <%=pedidos.get(i).getHora()%>
                    <br>Estado: <%=pedidos.get(i).getEstado()%>
                    <br>Dirección: <%=pedidos.get(i).getCalleDireccion()%> <%=pedidos.get(i).getNumeroDireccion()%>
                    <%if (pedidos.get(i).getFechaConfirmacion()!=null){%>
                        <br>Fecha de respuesta: <%=pedidos.get(i).getFechaConfirmacion()%>
                        <br>Hora de respuesta: <%=pedidos.get(i).getHoraConfirmacion()%>
                        <br>Administrador: <%=pedidos.get(i).getUsuarioAdministrador()%>
                    <%}%>
                <td>
                    <% for (int j=0;j<pedidos.get(i).getLineas().size();j++){%>
                    <%=pedidos.get(i).getLineas().get(j).getCantidad()%>
                    <%=pedidos.get(i).getLineas().get(j).getProducto().getNombre()%><br>
                    <%}%>
                    Total: <%pedidos.get(i).setPrecio();%>
                            $<%=pedidos.get(i).getPrecio()%>
                <%}%>
                </td>  
            </tr>
            </table>
            <br>
        </div>   
    </body>
</html>
