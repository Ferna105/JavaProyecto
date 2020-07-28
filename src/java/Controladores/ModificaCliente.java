/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Datos.UsuarioConsultasSQL;
import Negocio.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ferna
 */
public class ModificaCliente extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession objSesion = request.getSession(false);
        
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String telefono = request.getParameter("telefono");
        String mail = request.getParameter("mail");
        String contActual = request.getParameter("contActual");//Contraseña real actual
        String oldpass = request.getParameter("oldpass");//Contraseña actual ingresada
        String nuevaPass = request.getParameter("pass");//Contraseña nueva
        String nuevaPass2= request.getParameter("pass2");//Contraseña nueva 2
        String usuario = request.getParameter("usuario");
        String tipoUsuario= request.getParameter("tipoUsuario");
        
        boolean ban;
        UsuarioConsultasSQL usu = new UsuarioConsultasSQL();
        
        if (oldpass.equals("")||nombre.equals("")||apellido.equals("")||
                mail.equals("")||telefono.equals("")){
            usu.setError("Error: Complete todos los campos obligatorios (*)");
            objSesion.setAttribute("error",usu.getError());
            response.sendRedirect("cambiaDatosCuenta.jsp");
        }
        else if(!contActual.equals(oldpass)){
            usu.setError("Error: Ingrese correctamente la contraseña actual");
            objSesion.setAttribute("error",usu.getError());
            response.sendRedirect("cambiaDatosCuenta.jsp");
        }
        else if(!nuevaPass.equals(nuevaPass2)){
            usu.setError("Error: Los dos campos de la nueva contraseña no coinciden");
            objSesion.setAttribute("error",usu.getError());
            response.sendRedirect("cambiaDatosCuenta.jsp");
        }
        else if(nuevaPass.length()<6 && !nuevaPass.equals("")){
            usu.setError("Error: La nueva contraseña debe tener por lo menos 6 caracteres");
            objSesion.setAttribute("error",usu.getError());
            response.sendRedirect("cambiaDatosCuenta.jsp");
        }
        else if(!nombre.matches("^[A-Za-z][a-z]+")){
            usu.setError("Error: Ingrese un nombre correcto");
            objSesion.setAttribute("error",usu.getError());
            response.sendRedirect("cambiaDatosCuenta.jsp");
        }
        else if(!apellido.matches("^[A-Za-z][a-z]+")){
            usu.setError("Error: Ingrese un apellido correcto");
            objSesion.setAttribute("error",usu.getError());
            response.sendRedirect("cambiaDatosCuenta.jsp");
        }
        else if(!mail.matches("^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,4})$")){
            usu.setError("Error: Ingrese un mail correcto");
            objSesion.setAttribute("error",usu.getError());
            response.sendRedirect("cambiaDatosCuenta.jsp");
        }
        else if ( !telefono.matches( "[0-9][0-9][0-9][0-9][0-9][0-9][0-9]+" )){
            usu.setError("Error: Ingrese un número de teléfono correcto (Sólo dígitos)");
            objSesion.setAttribute("error",usu.getError());
            response.sendRedirect("cambiaDatosCuenta.jsp");
        }
        else {
            if(nuevaPass.equals("") && nuevaPass2.equals("")){
                ban=usu.updateCliente(usuario,nombre,apellido,telefono,mail,contActual,tipoUsuario);
            }
            else{
                ban=usu.updateCliente(usuario,nombre,apellido,telefono,mail,nuevaPass,tipoUsuario);
                contActual = nuevaPass;
            }
            
            if(ban){
                Usuario userActual = (Usuario) objSesion.getAttribute("usuarioActual");
                userActual = new Usuario(usuario,contActual,tipoUsuario,nombre,apellido,telefono,mail);
                objSesion.setAttribute("usuarioActual", userActual);
                objSesion.setAttribute("notificacion", "Datos actualizados!");
                response.sendRedirect("menu.jsp");
            }
            else{
                objSesion.setAttribute("error",usu.getError());
                response.sendRedirect("cambiaDatosCuenta.jsp");
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
        processRequest(request, response);
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
        processRequest(request, response);
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
