/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Negocio.LineaPedido;
import Negocio.Pedido;
import Negocio.Producto;
import Negocio.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ferna
 */
public class AgregaProductos extends HttpServlet {

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
        HttpSession sesion = request.getSession(true);
        
        Integer num;
        if (sesion.getAttribute("numeroLinea")== null){
            sesion.setAttribute("numeroLinea",1);
            num=1;
        }
        else{
            num=(Integer) sesion.getAttribute("numeroLinea");
            num=num+1;
            sesion.setAttribute("numeroLinea", num);
        }
        String cantidad = request.getParameter("cantidad");
        if (cantidad.equals("")){
            sesion.setAttribute("error","Error: Ingrese la cantidad a comprar");
            response.sendRedirect("listadoProductos.jsp");
        }
        else if (!cantidad.matches( "[1-9]|[1-2][0-9]|" )){
            sesion.setAttribute("error","Error: Ingrese una cantidad correcta y menor a 30 unidades");
            response.sendRedirect("listadoProductos.jsp");
        }
        else{
            Usuario cliente = (Usuario) sesion.getAttribute("usuarioActual");
            int id = Integer.parseInt(request.getParameter("idProducto"));
            String nombre = request.getParameter("nombreProducto");
            String descripcion = request.getParameter("descripcionProducto");
            String tipo = request.getParameter("tipoProducto");
            Float precio = Float.parseFloat(request.getParameter("precioProducto"));
            String imagen = request.getParameter("imagenProducto");
            int cant = Integer.parseInt(request.getParameter("cantidad"));
        
            Producto producto = new Producto(id,nombre,descripcion,tipo,precio,imagen);
            LineaPedido lp = new LineaPedido(cant , producto, num);
            ArrayList<LineaPedido> lineas = new ArrayList<LineaPedido>();
            Pedido pedActual = new Pedido();
        
            if (sesion.getAttribute("pedidoActual")== null){
                pedActual.setCliente(cliente);
                pedActual.setLineas(lineas);
            }
            else{
                pedActual = (Pedido) sesion.getAttribute("pedidoActual");
            }
            lineas=pedActual.getLineas();
            lineas.add(lp);
            pedActual.setLineas(lineas);
            pedActual.setPrecio();
            sesion.setAttribute("pedidoActual", pedActual);
            sesion.setAttribute("notificacion", "Objeto ingresado al carrito!");
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
