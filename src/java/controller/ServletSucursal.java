/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Sucursal;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.ModeloSucursal;

/**
 *
 * @author Kevin
 */
public class ServletSucursal extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            String accion = request.getParameter("accion");
            String json;
            ModeloSucursal ms = new ModeloSucursal();
            Sucursal su, aux;

            if (!accion.isEmpty() && accion != null) {
                if (accion.equals("eliminar")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    su = ms.recuperaSucursal(id, "", "su.id=?");
                    if (ms.eliminar(su) == 1) {
                        json = muestramensaje("Eliminar sucursal", "La sucursal se ha eliminado correctamente.", "success", su);
                        out.print(json);
                    } else {
                        json = muestramensaje("Eliminar sucursal", "La sucursal no se ha eliminado.", "success", null);
                        out.print(json);
                    }
                } else if (accion.equals("guardar") || accion.equals("editar")) {
                    String nombre = request.getParameter("nombre");
                    String codigo = request.getParameter("codigo");
                    su = new Sucursal(nombre, codigo);
                    if (accion.equals("guardar")) {
                        if (ms.recuperaSucursal(0, nombre, "su.nombre=?") == null) {
                            if (ms.recuperaSucursal(0, codigo, "su.codigo=?") == null) {
                                if (ms.guardarSucursal(su) == 1) {
                                    json = muestramensaje("Registrar sucursal", "La sucursal se ha registrado correctamente.", "success", su);
                                    out.print(json);
                                } else {
                                    json = muestramensaje("Registrar sucursal", "La sucursal no se ha registrado.", "error", null);
                                    out.print(json);
                                }
                            } else {
                                json = muestramensaje("Registrar sucursal", "El codigo de la sucursal ingresado ya existe.", "info", null);
                                out.print(json);
                            }
                        } else {
                            json = muestramensaje("Registrar sucursal", "El nombre de la sucursal ingresado ya existe.", "info", null);
                            out.print(json);
                        }
                    } else {
                        int id = Integer.parseInt(request.getParameter("id"));
                        su.setId(id);
                        aux = ms.recuperaSucursal(id, "", "su.id=?");
                        if (!aux.getCodigo().equals(codigo)) {
                            aux = ms.recuperaSucursal(0, codigo, "su.codigo=?");
                            if (aux != null) {
                                if (aux.getCodigo().equals(codigo)) {
                                    json = muestramensaje("Modificar sucursal", "El codigo ingresado para la sucursal ya existe.", "info", null);
                                    out.print(json);
                                    return;
                                }
                            }
                        }
                        if (!aux.getNombre().equals(nombre)) {
                            aux = ms.recuperaSucursal(0, nombre, "su.nombre=?");
                            if (aux != null) {
                                if (aux.getNombre().equals(nombre)) {
                                    json = muestramensaje("Modificar sucursal", "El nombre ingresado para la sucursal ya existe.", "info", null);
                                    out.print(json);
                                    return;
                                }
                            }
                        }
                        aux = ms.recuperaSucursal(id, "", "su.id=?");
                        if (aux != null) {
                            if (ms.modificarSucursal(su) == 1) {
                                json = muestramensaje("Modificar sucursal", "La sucursal se ha modificado correctamente.", "success", su);
                                out.print(json);
                            } else {
                                json = muestramensaje("Modificar sucursal", "La sucursal no se ha modificado.", "error", null);
                                out.print(json);
                            }
                        } else {
                            json = muestramensaje("Alteraciones en el Servidor", "La operación ha fallado, al parecer han habido alteraciones de datos en el servidor.", "error", null);
                            out.print(json);
                        }
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
