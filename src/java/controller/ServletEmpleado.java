/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Cnatural;
import entities.Persona;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.ModeloEmpleado;
import models.ModeloFiador;

/**
 *
 * @author BONIFACIO
 */
public class ServletEmpleado extends HttpServlet {

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
            //clases
            ModeloEmpleado me = new ModeloEmpleado();
            Persona persona, aux;
            if (!accion.isEmpty() && accion != null) {
                if (accion.equals("eliminar")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    if (me.recupPer(id)) {
                        persona = me.recuperaPersona(id, "", "p.id=?");
                        if (persona.getUsuarios().size() > 0) {
                            json = muestramensaje("Eliminar empleado", "El empleado no se debe eliminar, ya que posee un usuario.", "info", null);
                            out.print(json);
                        }

                        if (me.eliminarEmpleado(persona) == 1) {
                            json = muestramensaje("Eliminar empleado", "El empleado se ha eliminado correctamente.", "success", persona);
                            out.print(json);
                        } else {
                            json = muestramensaje("Eliminar empleado", "El empleado no se ha eliminado.", "error", null);
                            out.print(json);
                        }
                    } else {
                        json = muestramensaje("Eliminar empleado", "El empleado no se debe eliminar.", "info", null);
                        out.print(json);
                    }
                } else if (accion.equals("guardar") || accion.equals("editar")) {
                    //variables del formulario
                    //String codigo = "";
                    String dui = request.getParameter("dui");
                    String nombre = request.getParameter("nombre");
                    String apellido = request.getParameter("apellido");
                    String sexo = request.getParameter("sexo");
                    Date fechanacimiento = Date.valueOf(request.getParameter("fechanacimiento"));
                    String telefono = request.getParameter("telefono");
                    String direccion = request.getParameter("direccion");
                    String estadocivil = request.getParameter("estadocivil");
                    //String lugartrabajo = request.getParameter("lugartrabajo");
                    String tipo = request.getParameter("tipo");
                    //fin variable del formulario
                    persona = new Persona(dui, nombre, apellido, sexo, telefono, direccion, estadocivil, fechanacimiento, tipo);
                    if (accion.equals("guardar")) {
                        if (me.recuperaPersona(0, dui, "p.dui=?") == null) {
                            if (me.guardarEmpleado(persona) == 1) {
                                json = muestramensaje("Registrar empleado", "El empleado se ha registrado correctamente.", "success", persona);
                                out.print(json);
                            } else {
                                json = muestramensaje("Registrar empleado", "El empleado no se ha registrado.", "error", null);
                                out.print(json);
                            }
                        } else {
                            json = muestramensaje("Registrar empleado", "El número de DUI ingresado para el empleado ya existe.", "info", null);
                            out.print(json);
                        }
                    } else {
                        int id = Integer.parseInt(request.getParameter("id"));
                        persona.setId(id);
                        aux = me.recuperaPersona(id, "", "p.id=?");
                        if (!aux.getDui().equals(dui)) {
                            aux = me.recuperaPersona(0, dui, "p.dui=?");
                            if (aux != null) {
                                if (aux.getDui().equals(dui)) {
                                    json = muestramensaje("Modificar empleado", "El número de DUI ingresado para el empleado ya existe.", "info", null);
                                    out.print(json);
                                    return;
                                }
                            }
                        }

                        if (me.recuperaPersona(id, "", "p.id=?") != null) {
                            if (me.modificarEmpleado(persona) == 1) {
                                json = muestramensaje("Modificar empleado", "El empleado se ha modificado correctamente.", "success", persona);
                                out.print(json);
                            } else {
                                json = muestramensaje("Modificar empleado", "El empleado no se ha modificado.", "error", null);
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
