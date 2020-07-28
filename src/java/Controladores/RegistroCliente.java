/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Datos.CalleConsultasSQL;
import Datos.DireccionConsultasSQL;
import Datos.UsuarioConsultasSQL;
import Negocio.Calle;
import Negocio.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ferna
 */
public class RegistroCliente extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        HttpSession objSesion = request.getSession(true);
        boolean ban= true;
        int p,d,n,altMax=0,altMin=0;
        String usuario= request.getParameter("usuario");
        String contraseña = request.getParameter("pass");
        String contraseña2= request.getParameter("pass2");
        String nombre= request.getParameter("nombreUsuario");
        String apellido = request.getParameter("apellidoUsuario");
        String mail = request.getParameter("mailUsuario");
        String telefono = request.getParameter("telefonoUsuario");
        String calle = request.getParameter("calle");
        String numero = request.getParameter("numero");
        String piso = request.getParameter("piso");
        String departamento = request.getParameter("departamento");
        String bloque = request.getParameter("bloque");
        String comentarios = request.getParameter("comentarios");
        String tipoDireccion;
        
        DireccionConsultasSQL dir = new DireccionConsultasSQL();
        CalleConsultasSQL cal = new CalleConsultasSQL();
        ArrayList<Calle> calList = new ArrayList<Calle>();
        calList = cal.listadoCalles();
        
        for(int i=0;i<calList.size();i++){
            if (calList.get(i).getNombre().equals(calle)){
                altMax=calList.get(i).getAlturaMax();
                altMin=calList.get(i).getAlturaMin();
            }
        }
        
        if ( usuario.equals("") || contraseña.equals("") || contraseña2.equals("") || 
                nombre.equals("") || apellido.equals("") || mail.equals("") || telefono.equals("") || 
                calle.equals( "" ) || numero.equals( "" )){
            dir.setError("Error: Complete todos los campos obligatorios");
            objSesion.setAttribute("errorRegistro",dir.getError());
            response.sendRedirect("index.jsp");
        }
        else if ( !usuario.matches( "^[^\\s]{4,25}")){
            dir.setError("Error: El nombre de usuario debe tener entre 4 y 25 caracteres (NO espacios)");
            objSesion.setAttribute("errorRegistro",dir.getError());
            response.sendRedirect("index.jsp");
        }
        else if (contraseña.length()<6){
            dir.setError("Error: La contraseña debe tener POR LO MENOS 6 caracteres");
            objSesion.setAttribute("errorRegistro",dir.getError());
            response.sendRedirect("index.jsp");
        }
        else if (!contraseña.equals(contraseña2)){
                dir.setError("Error: Repita correctamente la contraseña");
                objSesion.setAttribute("errorRegistro",dir.getError());
                response.sendRedirect("index.jsp");
        }
        else if(!nombre.matches("^[A-Za-z][a-z]+")){
            dir.setError("Error: Ingrese un nombre correcto");
            objSesion.setAttribute("errorRegistro",dir.getError());
            response.sendRedirect("index.jsp");
        }
         else if(!apellido.matches("^[A-Za-z][a-z]+")){
            dir.setError("Error: Ingrese un apellido correcto");
            objSesion.setAttribute("errorRegistro",dir.getError());
            response.sendRedirect("index.jsp");
        }
        else if(!mail.matches("^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,4})$")){
            dir.setError("Error: Ingrese un mail correcto");
            objSesion.setAttribute("errorRegistro",dir.getError());
            response.sendRedirect("index.jsp");
        }
        else if ( !telefono.matches( "[0-9][0-9][0-9][0-9][0-9][0-9][0-9]+" )){
            dir.setError("Error: Ingrese un número de teléfono correcto (Sólo dígitos)");
            objSesion.setAttribute("errorRegistro",dir.getError());
            response.sendRedirect("index.jsp");
        }
        else if ( !numero.matches( "[0-9]|[0-9][0-9]|[0-9][0-9][0-9]|[0-9][0-9][0-9][0-9]" )){
            dir.setError("Error: Ingrese un número correcto en la dirección");
            objSesion.setAttribute("errorRegistro",dir.getError());
            response.sendRedirect("index.jsp");
        }
        else if ( !piso.matches( "[0-9]|[0-9][0-9]|" )){
            dir.setError("Error: Ingrese un piso numérico correcto");
            objSesion.setAttribute("errorRegistro",dir.getError());
            response.sendRedirect("index.jsp");
        }
        else if ( !departamento.matches( "[0-9]|[0-9][0-9]|" )){
            dir.setError("Error: Ingrese un departamento numérico correcto");
            objSesion.setAttribute("errorRegistro",dir.getError());
            response.sendRedirect("index.jsp");
        } 
        else if ( !bloque.matches( "[0-9a-zA-Z]|")){
            dir.setError("Error: Ingrese un bloque alfabético/numérico correcto");
            objSesion.setAttribute("errorRegistro",dir.getError());
            response.sendRedirect("index.jsp");  
        }
        else{
            n = Integer.parseInt(numero);
            if (n<altMin || n>altMax){
                dir.setError("Error: El servicio de cadetería no llega hasta la altura solicitada");
                objSesion.setAttribute("errorRegistro",dir.getError());
                ban = false;
            }
            if (piso.equals("")){p = 0;}
                else{p = Integer.parseInt(piso);}
            if (departamento.equals("")){d = 0;}
                else{d = Integer.parseInt(departamento);}
            if (piso.equals("")){tipoDireccion="Casa";} 
                else{tipoDireccion="Departamento";}
            UsuarioConsultasSQL con = new UsuarioConsultasSQL();
            
            if (ban==true){
                if(con.insertUsuarioCliente(usuario, contraseña,"Cliente",nombre, apellido,telefono,mail,calle,n,tipoDireccion,p,d,bloque,comentarios)){
                    objSesion.setAttribute("notificacion", "Usuario Registrado!");
                    response.sendRedirect("index.jsp");
                }
                else{
                    objSesion.setAttribute("errorRegistro",con.getError());
                    response.sendRedirect("index.jsp");
                }
            }
            else{  
                if(con.getError()!=null){
                    objSesion.setAttribute("errorRegistro", con.getError());
                }
                response.sendRedirect("index.jsp");
            }
            
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(RegistroCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(RegistroCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
