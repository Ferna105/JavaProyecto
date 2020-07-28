/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Datos.DireccionConsultasSQL;
import Datos.PedidoConsultasSQL;
import Negocio.Direccion;
import Negocio.Pedido;
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
public class RegistraPedido extends HttpServlet {

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
        
        String usuario = request.getParameter("usuario");
        
        ArrayList<Direccion> direcciones = new ArrayList<Direccion>();
        DireccionConsultasSQL dir = new DireccionConsultasSQL();
        direcciones= dir.listadoDirecciones(usuario);
        
        HttpSession objSesion = request.getSession(false);
        Pedido pedido = (Pedido) objSesion.getAttribute("pedidoActual");
        
        int indice = Integer.parseInt(request.getParameter("indice"));
        Direccion direccion = direcciones.get(indice);
        
        PedidoConsultasSQL ped = new PedidoConsultasSQL();
        if (ped.RegistroPedido(usuario,direccion,pedido)){
            objSesion.removeAttribute("pedidoActual");
            objSesion.removeAttribute("numeroLinea");
            objSesion.setAttribute("notificacion", "Solicitud de pedido enviada!");
            response.sendRedirect("listadoPedidosUsuario.jsp");
        }
        else{
            objSesion.setAttribute("error", ped.getError());
            response.sendRedirect("carritoCompras.jsp");
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
            Logger.getLogger(RegistraPedido.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RegistraPedido.class.getName()).log(Level.SEVERE, null, ex);
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
