<%-- 
    Document   : listadoSandwiches
    Created on : 28-dic-2016, 0:21:44
    Author     : Ferna
--%>

<%@page import="Negocio.Usuario"%>
<%@page import="Datos.ProductoConsultasSQL"%>
<%@page import="Negocio.Producto"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    HttpSession objSesion = request.getSession(false);
    Usuario userActual = (Usuario) objSesion.getAttribute("usuarioActual");
    objSesion.removeAttribute("error");
    String tipoUsuario = userActual.getTipoUsuario();
    if(userActual.getNombreUsuario().equals("")|| tipoUsuario.equals("Administrador")){
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
        
        <div style="border: 1px solid black; background: #004466;">
        <table border="1">
            <tr>
                <nav style="background: #000"><h1 align="center">Listado de Sandwiches</h1></nav>
            </tr>
            <% ArrayList<Producto> prod = new ArrayList<Producto>();
            ProductoConsultasSQL pc = new ProductoConsultasSQL();
            prod = pc.listadoProductos("Sandwich"); %>
            
            <tr>
            <%int cantPorFila = 0;
            for (int i=0;i<prod.size();i++){
                String rutaImagen = "Imagenes/"+prod.get(i).getImagen();
                if ( cantPorFila == 4 ){
                    cantPorFila = 0;
                %>
                <tr></tr>
                 <%}
                    cantPorFila = cantPorFila + 1;
                %>
                <td align="center"><img height="100" width="100" src=<%=rutaImagen%>><br>
                    <%=prod.get(i).getNombre()%><br>
                    <% if (prod.get(i).getDescripcion()!=null){%>
                        (<%=prod.get(i).getDescripcion()%>)<br>
                    <%}%>
                    $<%=prod.get(i).getPrecio()%><br>
                    <form action="AgregaProductos" method="post">
                        <input type="hidden" name="idProducto" value="<%=prod.get(i).getId()%>">
                        <input type="hidden" name="nombreProducto" value="<%=prod.get(i).getNombre()%>">
                        <input type="hidden" name="descripcionProducto" value="<%=prod.get(i).getDescripcion()%>">
                        <input type="hidden" name="tipoProducto" value="<%=prod.get(i).getTipo()%>">
                        <input type="hidden" name="precioProducto" value="<%=prod.get(i).getPrecio()%>">
                        <input type="hidden" name="imagenProducto" value="<%=prod.get(i).getImagen()%>">
                        <label>Cantidad: </label><input type="text" name="cantidad" id="cantidad" size="3">
                        <input type="submit" value="Agregar al carrito">
                    </form>
                </td>     
            <%}%>
            </tr>
        </table>
        </div>
    </body>
</html>
