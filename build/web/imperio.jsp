<%@page import="Negocio.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    HttpSession objSesion = request.getSession(false);
    Usuario userActual = (Usuario) objSesion.getAttribute("usuarioActual");
    objSesion.removeAttribute("error");
    if(userActual == null){
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
        <div style="border: 1px solid black; background: #004466; float: next">
            <table style="margin: 0 auto">
                <nav style="background: #000"><h1 align="center">Rotiseria Imperio</h1></nav>
                <tr>
                    <td>Dirección: Urquiza 1054
                </tr>
                <tr>
                    <td>Número de teléfono: 440-5244/411-0355
                </tr>
                <tr>
                    <td>Buscanos en nuestro Facebook! <a href="http://www.facebook.com/RotiseriaImperioRosario/"><img src="Imagenes/fb.png" height="15" width="15"></a>
                </tr>
            </table>
        </div>    
            <br>
            
        <div style="border: 1px solid black; background: #004466; float: next; float: left">
            <table>
                <nav style="background: #000"><h1 align="center">Zona de Reparto</h1></nav>
                <tr><td><img height="300" width="300" src="Imagenes/zonareparto.png"></tr>
            </table>
        </div>    
            
        <div style="border: 1px solid black; background: #004466; float: next; float: right">    
            <table>
                <nav style="background: #000"><h1 align="center">Horarios</h1></nav>
                <tr>
                    <td>Lunes: 
                    <td>11:30-14:15
                </tr>
                <tr>
                    <td>Martes:
                    <td>Cerrado
                </tr>    
                <tr>
                    <td>Miércoles: 
                    <td>11:30-14:15 /
                    <td>20:00-22:15
                </tr>  
                <tr>
                    <td>Jueves: 
                    <td>11:30-14:15 /   
                    <td>20:00-22:15
                </tr>  
                <tr>
                    <td>Viernes: 
                    <td>11:30-14:15 / 
                    <td>20:00-22:15
                </tr>  
                <tr>
                    <td>Sábado: 
                    <td>11:30-14:15 /  
                    <td>20:00-22:15
                </tr>  
                <tr>
                    <td>Domingo: 
                    <td>11:30-14:15 /  
                    <td>20:00-22:15
                </tr>  
            </table>
        </div>   
            <br>
            <br>
            <br>
        
        
    </body>
</html>
