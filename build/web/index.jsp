<%-- 
    Document   : index
    Created on : 26-nov-2016, 3:29:35
    Author     : Ferna
--%>

<%@page import="Datos.CalleConsultasSQL"%>
<%@page import="Negocio.Calle"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%    
    String errorLogin = "";
    String errorRegistro = "";
    String notificacion = "";
    HttpSession objSesion = request.getSession(true);
    if (objSesion.getAttribute("errorLogin") != null){
        errorLogin = (String) objSesion.getAttribute("errorLogin");
    }
    if (objSesion.getAttribute("errorRegistro") != null){
        errorRegistro = (String) objSesion.getAttribute("errorRegistro");
    }
    if (objSesion.getAttribute("notificacion") != null){
        notificacion = (String) objSesion.getAttribute("notificacion");
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
            <form action="Login" method="post">
                <label> &nbsp&nbsp Usuario: </label>
                <input type="text" name="usuario" id="usuario">
                <label> Contraseña: </label>
                <input type="password" name="pass" id="pass">
                <input type="submit" value="Ingresar"> &nbsp&nbsp
            </form>
            <label align="right" style="color: red;"><%=errorLogin%></label>
            <%objSesion.removeAttribute("errorLogin");%>
            &nbsp&nbsp
        </div>
        <br>        
        <br>
        
        <br>
        <div style="border: 1px solid black; background: #004466; float: right">
            <form action="RegistroCliente" method="post">
                
                <table>
                    <tr>
                        <nav style="background: #000"><h1 align="center">Registrate y Pedí!</h1></nav>
                    </tr>
                    <tr>
                        <td><label>*Usuario:</label>
                        <td><input type="text" name="usuario" id="usuario">
                    </tr>
                    <tr>
                        <td><label>*Contraseña:</label>
                        <td><input type="password" name="pass" id="pass">
                    </tr>
                    <tr>
                        <td><label>*Ingrese contraseña nuevamente:</label>
                        <td><input type="password" name="pass2" id="pass2">
                    </tr>
                    <tr>
                        <td><br><label>*Nombre:</label>
                        <td><br><input type="text" name="nombreUsuario" id="nombreUsuario">
                    </tr>
                    <tr>
                        <td><label>*Apellido:</label>
                        <td><input type="text" name="apellidoUsuario" id="apellidoUsuario">
                    </tr>
                    <tr>
                        <td><label>*Correo Electrónico:</label>
                        <td><input type="text" name="mailUsuario" id="mailUsuario">
                    </tr>
                    <tr>
                        <td><label>*Número de teléfono:</label>
                        <td><input type="text" name="telefonoUsuario" id="telefonoUsuario">
                    </tr>
                    <tr>
                        <td><h3>Información domiciliaria</h3>
                    </tr>
                    <tr>
                        <td><label>*Calle:</label>
                        <td><select name="calle">
                            <%  ArrayList<Calle> calles = new ArrayList<Calle>();
                            CalleConsultasSQL cc = new CalleConsultasSQL();
                            calles = cc.listadoCalles();
                            for (int i=0;i<calles.size();i++){%>
                                <option value="<%=calles.get(i).getNombre()%>"><%=calles.get(i).getNombre()%></option>
                            <%}%>    
                            </select>
                    </tr>
                    <tr>
                        <td><label>*Número:</label>
                        <td><input type="text" name="numero" id="numero" size="3">
                    </tr>
                    <tr>
                        <td><h4>Complete solo si corresponde</h4>
                    </tr>
                    <tr>        
                        <td colspan="2"><label>Piso:</label>
                            <input type="text" name="piso" id="piso" size="2">
                    
                            <label>Departamento (numérico):</label>
                            <input type="text" name="departamento" id="departamento" size="2">
                    </tr>
                    <tr>
                        <td><label>Bloque/Torre (alfabético/numérico):</label>
                            <input type="text" name="bloque" id="bloque" size="2">
                    </tr>
                    <tr>
                        <td colspan="2"><label>Ingrese otra información que considere importante:</label>
                    </tr>
                    <tr>
                        <td colspan="2"><input type="text" name="comentarios" id="comentarios" size="47">
                    </tr>
                    <tr>
                        <td><label align="right" style="color: red;"><%=errorRegistro%></label>
                        <%objSesion.removeAttribute("errorRegistro");%>
                        <label align="right" style="color:yellow;"><%=notificacion%></label>
                        <%objSesion.removeAttribute("notificacion");%>
                        <td align="right"><input type="submit" value="Registrarse">
                    </tr>
                </table>
            </form>
                
        </div>
        <br>
        <br>
        <br>
        <br>
        <div style="border: 1px solid black; background: #004466; float: left">
            <nav style="background: #000"><h1 align="center">Zona de Reparto</h1></nav>
            <img src="Imagenes/zonareparto.png" width="350" height="350">
            <nav style="background: #000"><h3 align="center">Bv. Oroño - Av. Pellegrini - Costanera</h3></nav>
        </div>
    </body>
</html>
