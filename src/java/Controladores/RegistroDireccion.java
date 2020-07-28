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
public class RegistroDireccion extends HttpServlet {

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
        
        boolean ban=true;
        String usuario = request.getParameter("usuario");
        String calle = request.getParameter("calle");
        String numero =request.getParameter("numero");
        String piso = request.getParameter("piso");
        String departamento = request.getParameter("departamento");
        String bloque = request.getParameter("bloque");
        String comentarios = request.getParameter("comentarios");
        String tipoDireccion;
        int altMax=0;
        int altMin=0;
        int n,p,d;
        HttpSession objSesion = request.getSession(false);
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
        
        if ( usuario.equals( "" ) || calle.equals( "" ) || numero.equals( "" )){
            dir.setError("Error: Complete todos los campos obligatorios (*)");
            objSesion.setAttribute("error", dir.getError());
            response.sendRedirect("agregarDireccion.jsp");
        }
        else if ( !numero.matches( "[0-9]|[0-9][0-9]|[0-9][0-9][0-9]|[0-9][0-9][0-9][0-9]" )){
            dir.setError("Error: Ingrese un número correcto");
            objSesion.setAttribute("error", dir.getError());
            response.sendRedirect("agregarDireccion.jsp");
        }
        else if ( !piso.matches( "[0-9]|[0-9][0-9]|" )){
            dir.setError("Error: Ingrese un piso correcto");
            objSesion.setAttribute("error", dir.getError());
            response.sendRedirect("agregarDireccion.jsp");
        }
        else if ( !departamento.matches( "[0-9]|[0-9][0-9]|" )){
            dir.setError("Error: Ingrese un departamento numérico correcto");
            objSesion.setAttribute("error", dir.getError());
            response.sendRedirect("agregarDireccion.jsp");
        } 
        else if ( !bloque.matches( "[0-9a-zA-Z]|")){
            dir.setError("Error: Ingrese un bloque numérico/alfabético correcto");
            objSesion.setAttribute("error", dir.getError());
            response.sendRedirect("agregarDireccion.jsp");
        }   
        else{
            n = Integer.parseInt(numero);
            if (n<altMin || n>altMax){
                dir.setError("Error: El servicio de cadetería no llega hasta la altura solicitada");
                ban = false;
            }
            if (piso.equals("")){
                p = 0;
            }
            else{
                p = Integer.parseInt(piso);
            }
            
            if (departamento.equals("")){
                d = 0;
            }
            else{
                d = Integer.parseInt(departamento);
            }
            
            if (piso.equals("")){
                tipoDireccion="Casa";
            } else {
                tipoDireccion="Departamento";
            }
        
            if (ban==true){
                if(dir.insertDireccionCliente(calle,n,tipoDireccion,p,d,bloque,usuario,comentarios)){
                    objSesion.setAttribute("notificacion", "Dirección registrada!");
                    response.sendRedirect("miCuenta.jsp");
                }
                else{
                    objSesion.setAttribute("error", dir.getError());
                    response.sendRedirect("agregarDireccion.jsp");
                }
            }
            else{  
                objSesion.setAttribute("error", dir.getError());
                response.sendRedirect("agregarDireccion.jsp");
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
            Logger.getLogger(RegistroDireccion.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RegistroDireccion.class.getName()).log(Level.SEVERE, null, ex);
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
