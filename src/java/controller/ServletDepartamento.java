/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Departamento;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.ModeloDepartamento;

/**
 *
 * @author Kevin
 */
public class ServletDepartamento extends HttpServlet {

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
            ModeloDepartamento md = new ModeloDepartamento();
            Departamento dp, aux;

            if (!accion.isEmpty() && accion != null) {
                if (accion.equals("eliminar")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    dp = md.recuperaDepartamento(id, "", "dp.id=?");
                    if (md.eliminar(dp) == 1) {
                        json = muestramensaje("Eliminar departamento", "El departamento se ha eliminado correctamente.", "success", dp);
                        out.print(json);
                    } else {
                        json = muestramensaje("Eliminar departamento", "El departamento no se ha eliminado.", "success", null);
                        out.print(json);
                    }
                } else if (accion.equals("guardar") || accion.equals("editar")) {
                    String codigo = request.getParameter("codigo");
                    String nombre = request.getParameter("nombre");
                    dp = new Departamento(nombre, codigo);
                    if (accion.equals("guardar")) {
                        if (md.recuperaDepartamento(0, codigo, "dp.codigo=?") == null) {
                            if (md.recuperaDepartamento(0, nombre, "dp.nombre=?") == null) {
                                if (md.guardarDepartamento(dp) == 1) {
                                    json = muestramensaje("Registrar departamento", "El departamento se ha registrado correctamente.", "success", dp);
                                    out.print(json);
                                } else {
                                    json = muestramensaje("Registrar departamento", "El departamento no se ha registrado.", "error", null);
                                    out.print(json);
                                }
                            } else {
                                json = muestramensaje("Registrar departamento", "El nombre del departamento ingresado ya existe.", "info", null);
                                out.print(json);
                            }
                        } else {
                            json = muestramensaje("Registrar departamento", "El codigo del departamento ingresado ya existe.", "info", null);
                            out.print(json);
                        }
                    } else {
                        int id = Integer.parseInt(request.getParameter("id"));
                        dp.setId(id);
                        aux = md.recuperaDepartamento(id, "", "dp.id=?");
                        if (!aux.getCodigo().equals(codigo)) {
                            aux = md.recuperaDepartamento(0, codigo, "dp.codigo=?");
                            if (aux != null) {
                                if (aux.getCodigo().equals(codigo)) {
                                    json = muestramensaje("Modificar departamento", "El codigo ingresado para el departamento ya existe.", "info", null);
                                    out.print(json);
                                    return;
                                }
                            }
                        }
                        if (!aux.getNombre().equals(nombre)) {
                            aux = md.recuperaDepartamento(0, nombre, "dp.nombre=?");
                            if (aux != null) {
                                if (aux.getNombre().equals(nombre)) {
                                    json = muestramensaje("Modificar departamento", "El nombre ingresado para el departamento ya existe.", "info", null);
                                    out.print(json);
                                    return;
                                }
                            }
                        }
                        aux = md.recuperaDepartamento(id, "", "dp.id=?");
                        if (aux != null) {
                            if (md.modificarDepartamento(dp) == 1) {
                                json = muestramensaje("Modificar departamento", "El departamento se ha modificado correctamente.", "success", dp);
                                out.print(json);
                            } else {
                                json = muestramensaje("Modificar departamento", "El departamento no se ha modificado.", "error", null);
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
