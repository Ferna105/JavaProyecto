<%-- 
    Document   : agregarDireccion
    Created on : 27-nov-2016, 21:45:36
    Author     : Ferna
--%>

<%@page import="Datos.CalleConsultasSQL"%>
<%@page import="Negocio.Calle"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Negocio.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    HttpSession objSesion = request.getSession(false);
    Usuario userActual = (Usuario) objSesion.getAttribute("usuarioActual");
    String error = "";   
    if (objSesion.getAttribute("error") != null){
        error = (String) objSesion.getAttribute("error");
    }
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
       
        <div  style="border: 1px solid black; background: #004466">
            <nav style="background: #000"><h1 align="center">Información domiciliaria</h1></nav>
            <form action="RegistroDireccion" method="post">
            <table style="margin: 0 auto">
                <tr>
                    <td><label>*Calle:</label>
                    <td align="right"><select name="calle">
                            <%ArrayList<Calle> calles = new ArrayList<Calle>();
                            CalleConsultasSQL cc = new CalleConsultasSQL();
                            calles = cc.listadoCalles();
                            for (int i=0;i<calles.size();i++){%>
                                <option value="<%=calles.get(i).getNombre()%>"><%=calles.get(i).getNombre()%></option>
                            <%}%>    
                        </select>
                </tr>
                <tr>
                    <td><label>*Número:</label>
                    <td align="right"><input type="text" name="numero" id="numero" size="4">
                </tr>
                <tr>
                    <td colspan="2"><h4>Complete solo si corresponde</h4>
                </tr>
                <tr>
                    <td colspan="2"><label>Piso:</label>
                        <input type="text" name="piso" id="piso" size="2">
                        <label>Departamento (numérico):</label>
                        <input type="text" name="departamento" id="departamento" size="2">
                </tr>
                <tr>
                    <td colspan="2"><label>Bloque/Torre (alfabético/numérico):</label>
                        <input type="text" name="bloque" id="bloque" size="2">
                </tr>
                <tr>
                    <td colspan="2"><label>Ingrese otra información que considere importante:</label>
                </tr>
                <tr>
                    <td colspan="2"><input type="text" name="comentarios" id="comentarios" size="45">
                </tr>
                <tr>
                    <td colspan="2" align="right"><br><label style="color: red;"><%=error%></label>
                        <%objSesion.removeAttribute("error");%>
                        <input type="submit" value="Registrar Dirección">
                        <input type="hidden" name="usuario" value="<%=userActual.getUsuario()%>">
                        <br>
                        <br>
                </tr>
            </table>
            </form>
            
    </body>
</html>
