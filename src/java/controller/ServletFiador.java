/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Cnatural;
import entities.Disponibilidad;
import entities.Persona;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.ModeloFiador;

/**
 *
 * @author jsaul
 */
public class ServletFiador extends HttpServlet {

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
            RequestDispatcher despachador = null;
            String accion = request.getParameter("accion");
            String json;
            //clases
            ModeloFiador mf = new ModeloFiador();
            Cnatural cn;
            Persona persona, aux;
            Disponibilidad disponibilidad, auxDis;
            if (!accion.isEmpty() && accion != null) {
                if (accion.equals("eliminar")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    cn = mf.recuperaFiador(id);
                    if (cn.getDisponibilidadsForIdfiador().size() > 0) {
                        json = muestramensaje("Eliminar fiador", "El fiador no se debe eliminar, ya que posee ingresos o egresos.", "success", null);
                        out.print(json);
                        return;
                    }
                    if (mf.eliminar(cn) == 1) {
                        if (mf.eliminar(cn.getPersona()) == 1) {
                            json = muestramensaje("Eliminar fiador", "El fiador se ha eliminado correctamente.", "success", cn);
                            out.print(json);
                        } else {
                            json = muestramensaje("Eliminar fiador", "El fiador no se ha eliminado.", "success", null);
                            out.print(json);
                        }
                    } else {
                        json = muestramensaje("Eliminar fiador", "El fiador no se ha eliminado.", "success", null);
                        out.print(json);
                    }
                } else if (accion.equals("guardar") || accion.equals("editar")) {
                    //variables del formulario
                    String codigo = request.getParameter("codigo");
                    String dui = request.getParameter("dui");
                    String nombre = request.getParameter("nombre");
                    String apellido = request.getParameter("apellido");
                    String sexo = request.getParameter("sexo");
                    Date fechanacimiento = Date.valueOf(request.getParameter("fechanacimiento"));
                    String telefono = request.getParameter("telefono");
                    String direccion = request.getParameter("direccion");
                    String estadocivil = request.getParameter("estadocivil");
                    String lugartrabajo = request.getParameter("lugartrabajo");
                    String tipo = request.getParameter("tipo");
                    //fin variable del formulario
                    persona = new Persona(dui, nombre, apellido, sexo, telefono, direccion, estadocivil, fechanacimiento, tipo);
                    cn = new Cnatural();
                    cn.setClasificacion(null);
                    cn.setTipo(tipo);
                    cn.setCodigo(codigo);
                    cn.setLugartrabajo(lugartrabajo);
                    cn.setPersona(persona);
                    if (accion.equals("guardar")) {
                        if (mf.recuperaPersona(0, dui, "p.dui=?") == null) {
                            if (mf.guardarFiador(persona, cn) == 1) {
                                json = muestramensaje("Registrar fiador", "El fiador se ha registrado correctamente.", "success", cn);
                                out.print(json);
                            } else {
                                json = muestramensaje("Registrar fiador", "El fiador no se ha registrado.", "error", null);
                                out.print(json);
                            }
                        } else {
                            json = muestramensaje("Registrar fiador", "El número de DUI ingresado para el fiador ya existe.", "info", null);
                            out.print(json);
                        }
                    } else {
                        int id = Integer.parseInt(request.getParameter("id"));
                        int idpersona = Integer.parseInt(request.getParameter("idpersona"));
                        persona.setId(idpersona);
                        cn.setId(id);
                        aux = mf.recuperaPersona(idpersona, "", "p.id=?");
                        if (!aux.getDui().equals(dui)) {
                            aux = mf.recuperaPersona(0, dui, "p.dui=?");
                            if (aux != null) {
                                if (aux.getDui().equals(dui)) {
                                    json = muestramensaje("Modificar fiador", "El número de DUI ingresado ya existe.", "info", null);
                                    out.print(json);
                                    return;
                                }
                            }
                        }

                        if (mf.recuperaFiador(id) != null) {
                            if (mf.modificarFiador(persona, cn) == 1) {
                                json = muestramensaje("Modificar fiador", "El fiador se ha modificado correctamente.", "success", cn);
                                out.print(json);
                            } else {
                                json = muestramensaje("Modificar fiador", "El fiador no se ha modificado.", "error", null);
                                out.print(json);
                            }
                        } else {
                            json = muestramensaje("Alteraciones en el Servidor", "La operación ha fallado, al parecer han habido alteraciones de datos en el servidor.", "error", null);
                            out.print(json);
                        }
                    }
                } else if (accion.equals("eliminarDisponibilidad")) {
                    int id = Integer.parseInt(request.getParameter("iddisponibilidad"));
                    String tipo = request.getParameter("tipo");
                    disponibilidad = mf.recuperaDisponibilidad(id, "", "d.id=?");
                    if (mf.eliminar(disponibilidad) == 1) {
                        json = muestramensaje("Eliminar " + tipo.toLowerCase(), "El " + tipo.toLowerCase() + " se ha eliminado correctamente.", "success", disponibilidad.getCnaturalByIdfiador().getId());
                        out.print(json);
                    } else {
                        json = muestramensaje("Eliminar " + tipo.toLowerCase(), "El fiador no se ha eliminado.", "success", null);
                        out.print(json);
                    }
                } else if (accion.equals("guardarDisponibilidad") || accion.equals("editarDisponibilidad")) {
                    String descripcion = request.getParameter("descripcion");
                    Double monto = Double.parseDouble(request.getParameter("monto"));
                    String tipo = request.getParameter("tipo");
                    int idfiador = Integer.parseInt(request.getParameter("idfiador"));
                    cn = mf.recuperaFiador(idfiador);
                    disponibilidad = new Disponibilidad(null, cn, descripcion, monto, tipo);
                    if (accion.equals("guardarDisponibilidad")) {
                        if (mf.guardarOperacion(disponibilidad) == 1) {
                            json = muestramensaje("Registrar " + tipo.toLowerCase(), "El " + tipo.toLowerCase() + " se ha registrado correctamente.", "success", idfiador);
                            out.print(json);
                        } else {
                            json = muestramensaje("Registrar " + tipo.toLowerCase(), "El " + tipo.toLowerCase() + " no se ha registrado.", "error", null);
                            out.print(json);
                        }
                    } else {
                        int id = Integer.parseInt(request.getParameter("id"));
                        disponibilidad.setId(id);
                        auxDis = mf.recuperaDisponibilidad(id, "", "d.id=?");
                        if (!auxDis.getOperacion().equals(descripcion)) {
                            auxDis = mf.recuperaDisponibilidad(0, descripcion, "d.operacion=?");
                            if (auxDis != null) {
                                if (auxDis.getOperacion().equals(descripcion)) {
                                    json = muestramensaje("Modificar " + tipo.toLowerCase(), "La descripción del " + tipo.toLowerCase() + " ya ha sido registrada.", "info", null);
                                    out.print(json);
                                    return;
                                }
                            }
                        }

                        if (mf.recuperaDisponibilidad(id, "", "d.id=?") != null) {
                            if (mf.modificarOperacion(disponibilidad) == 1) {
                                json = muestramensaje("Modificar " + tipo.toLowerCase(), "El " + tipo.toLowerCase() + " se ha modificado correctamente.", "success", idfiador);
                                out.print(json);
                            } else {
                                json = muestramensaje("Modificar " + tipo.toLowerCase(), "El " + tipo.toLowerCase() + " no se ha modificado.", "error", null);
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
