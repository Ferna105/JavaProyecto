<%-- 
    Document   : miCuenta
    Created on : 26-nov-2016, 5:15:29
    Author     : Ferna
--%>

<%@page import="Datos.DireccionConsultasSQL"%>
<%@page import="Negocio.Direccion"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Negocio.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    HttpSession objSesion = request.getSession(false);
    Usuario userActual = (Usuario) objSesion.getAttribute("usuarioActual");
    String nombreUsuario = userActual.getNombreUsuario();
    String tipoUsuario = userActual.getTipoUsuario();
    String notificacion = "";
    if (objSesion.getAttribute("notificacion") != null){
        notificacion = (String) objSesion.getAttribute("notificacion");
    }
    if(nombreUsuario.equals("")|| tipoUsuario.equals("Administrador")){
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
            <table  style="margin: 0 auto">
                <nav style="background: #000"><h1 align="center">Datos Personales</h1></nav>
              
                <tr>
                    <td>Nombre: <label><%=userActual.getNombreUsuario()%> <%=userActual.getApellidoUsuario()%></label>
                </tr>
                <tr>
                    <td><% if (userActual.getTelefono()!= null){ %>
                    Teléfono: <label><%=userActual.getTelefono()%></label>
                    <% } %>
                </tr>
                <tr>
                    <td><% if (userActual.getMail() != null){ %>
                    E-Mail: <label><%=userActual.getMail()%></label>
            <% } %>
                </tr>
                <tr>
                    <td><h2>Direcciones Registradas</h2>
                </tr>
                    <% 
                    ArrayList<Direccion> direcciones = new ArrayList<Direccion>();
                    DireccionConsultasSQL dir = new DireccionConsultasSQL();
                    direcciones= dir.listadoDirecciones(userActual.getUsuario());
                    for ( int i=0;i<direcciones.size();i++){
                    %>
                <tr>
                    <td><%=direcciones.get(i).getCalle()%> <%=direcciones.get(i).getNumero()%>
                    <%if (!direcciones.get(i).getBloque().equals("")){%>
                    - Bloque/Torre: <%=direcciones.get(i).getBloque()%> -
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
                    <%}%>
                    <br>
                    <br>
                </tr>
            </table>   
        </div>
    </body>
</html>
