/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Tipoactivo;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.ModeloTipoActivo;

/**
 *
 * @author Kevin
 */
public class ServletTipoActivo extends HttpServlet {

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
            ModeloTipoActivo mt = new ModeloTipoActivo();
            Tipoactivo ta, aux;

            if (!accion.isEmpty() && accion != null) {
                if (accion.equals("eliminar")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    ta = mt.recuperaTipo(id, "", "ta.id=?");
                    if (mt.eliminar(ta) == 1) {
                        json = muestramensaje("Eliminar tipo de activo", "El tipo de activo se ha eliminado correctamente.", "success", ta);
                        out.print(json);
                    } else {
                        json = muestramensaje("Eliminar tipo de activo", "El tipo de activo se ha eliminado.", "success", null);
                        out.print(json);
                    }
                } else if (accion.equals("guardar") || accion.equals("editar")) {
                    String nombre = request.getParameter("nombre");
                    String codigo = request.getParameter("codigo");
                    String activo = request.getParameter("activo");
                    ta = new Tipoactivo(nombre, codigo, activo);
                    if (accion.equals("guardar")) {
                        if (mt.recuperaTipo(0, codigo, "ta.codigo=?") == null) {
                            if (mt.recuperaTipo(0, nombre, "ta.nombre=?") == null) {
                                if (mt.guardarTipo(ta) == 1) {
                                    json = muestramensaje("Registrar tipo de activo", "El tipo de activo se ha registrado correctamente.", "success", ta);
                                    out.print(json);
                                } else {
                                    json = muestramensaje("Registrar tipo de activo", "El tipo de activo no se ha registrado.", "error", null);
                                    out.print(json);
                                }
                            } else {
                                json = muestramensaje("Registrar tipo de activo", "El nombre del tipo de activo ingresado ya existe.", "info", null);
                                out.print(json);
                            }
                        } else {
                            json = muestramensaje("Registrar departamento", "El codigo del tipo de activo ingresado ya existe.", "info", null);
                            out.print(json);
                        }
                    } else {
                        int id = Integer.parseInt(request.getParameter("id"));
                        ta.setId(id);
                        aux = mt.recuperaTipo(id, "", "ta.id=?");
                        if (!aux.getCodigo().equals(codigo)) {
                            aux = mt.recuperaTipo(0, codigo, "ta.codigo=?");
                            if (aux != null) {
                                if (aux.getCodigo().equals(codigo)) {
                                    json = muestramensaje("Modificar tipo de activo", "El codigo ingresado para el tipo de activo ya existe.", "info", null);
                                    out.print(json);
                                    return;
                                }
                            }
                        }
                        if (!aux.getNombre().equals(nombre)) {
                            aux = mt.recuperaTipo(0, nombre, "ta.nombre=?");
                            if (aux != null) {
                                if (aux.getNombre().equals(nombre)) {
                                    json = muestramensaje("Modificar tipo de activo", "El nombre ingresado para el tipo de activo ya existe.", "info", null);
                                    out.print(json);
                                    return;
                                }
                            }
                        }
                        aux = mt.recuperaTipo(id, "", "ta.id=?");
                        if (aux != null) {
                            if (mt.modificarTipo(ta) == 1) {
                                json = muestramensaje("Modificar tipo de activo", "El tipo de activo se ha modificado correctamente.", "success", ta);
                                out.print(json);
                            } else {
                                json = muestramensaje("Modificar tipo de activo", "El tipo de activo no se ha modificado.", "error", null);
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
