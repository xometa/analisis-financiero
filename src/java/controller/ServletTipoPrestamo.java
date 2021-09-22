/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Tipoprestamo;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.ModeloTipoPrestamo;

/**
 *
 * @author jsaul
 */
public class ServletTipoPrestamo extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            String accion = request.getParameter("accion");
            String json;
            ModeloTipoPrestamo mtp = new ModeloTipoPrestamo();
            Tipoprestamo tp, aux;

            if (!accion.isEmpty() && accion != null) {
                if (accion.equals("guardar") || accion.equals("editar")) {

                    //listado de paramentros recibidos vía ajax
                    String tipo = request.getParameter("tipoprestamo");
                    Double porcentaje = Double.parseDouble(request.getParameter("porcentaje"));

                    tp = new Tipoprestamo(tipo, porcentaje);
                    if (accion.equals("guardar")) {
                        if (mtp.recuperaTipoPrestamo(0, tipo, "tp.tipo=?") == null) {
                            if (mtp.guardar(tp) == 1) {
                                json = muestramensaje("Registro tipo de prestamo", "El tipo de prestamo se ha registrado correctamente.", "success", tp);
                                out.print(json);
                            } else {
                                json = muestramensaje("Registro tipo de prestamo", "El activo fijo no se ha registrado.", "error", null);
                                out.print(json);
                            }
                        } else {
                            json = muestramensaje("Registro tipo de prestamo", "El nombre del tipo de prestamo ingresado ya existe.", "info", null);
                            out.print(json);
                        }
                    } else {
                        int id = Integer.parseInt(request.getParameter("id"));
                        tp.setId(id);
                        aux = mtp.recuperaTipoPrestamo(id, "", "tp.id=?");
                        if (!aux.getTipo().equals(tipo)) {
                            aux = mtp.recuperaTipoPrestamo(0, tipo, "tp.tipo=?");
                            if (aux != null) {
                                if (aux.getTipo().equals(tipo)) {
                                    json = muestramensaje("Modificar tipo de prestamo", "El nombre del tipo de prestamo ingresado ya existe.", "info", null);
                                    out.print(json);
                                    return;
                                }
                            }
                        }
                        aux = mtp.recuperaTipoPrestamo(id, "", "tp.id=?");
                        if (aux != null) {
                            if (mtp.modificar(tp) == 1) {
                                json = muestramensaje("Modificar tipo de prestamo", "El tipo de prestamo se ha modificado correctamente.", "success", tp);
                                out.print(json);
                            } else {
                                json = muestramensaje("Modificar tipo de prestamo", "El tipo de prestamo no se ha modificado.", "error", null);
                                out.print(json);
                            }
                        } else {
                            json = muestramensaje("Alteraciones en el Servidor", "La operación ha fallado, al parecer han habido alteraciones de datos en el servidor.", "error", null);
                            out.print(json);
                        }
                    }
                } else if (accion.equals("eliminar")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    tp = mtp.recuperaTipoPrestamo(id, "", "tp.id=?");
                    if (tp.getPrestamos().size() > 0) {
                        json = muestramensaje("Eliminar tipo de prestamo", "El tipo de prestamo no se debe eliminar, ya que ha sido asignado a prestamos realizado a los clientes.", "success", null);
                        out.print(json);
                        return;
                    }
                    if (mtp.eliminar(tp) == 1) {
                        json = muestramensaje("Eliminar tipo de prestamo", "El tipo de prestamo se ha eliminado correctamente.", "success", tp);
                        out.print(json);
                    } else {
                        json = muestramensaje("Eliminar tipo de prestamo", "El tipo de prestamo no se ha eliminado.", "success", null);
                        out.print(json);
                    }
                } else {
                    json = muestramensaje("Alteraciones en el Servidor", "La operación ha fallado, al parecer han habido alteraciones de datos en el servidor.", "error", null);
                    out.print(json);
                }
            } else {
                json = muestramensaje("Alteraciones en el Servidor", "La operación ha fallado, al parecer han habido alteraciones de datos en el servidor.", "error", null);
                out.print(json);
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

    public String muestramensaje(String title, String text, String icon, Object obj) {
        String json = "{\"titulo\":\"" + title + "\",\"texto\":\"" + text + "\",\"icono\":\"" + icon + "\",\"objeto\":\"" + obj + "\"}";
        return json;
    }
}
