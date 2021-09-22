/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Bgeneral;
import entities.Cjuridico;
import entities.Eresultado;
import entities.Persona;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.ModeloJuridico;

/**
 *
 * @author jsaul
 */
public class ServletJuridico extends HttpServlet {

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
            ModeloJuridico mj = new ModeloJuridico();
            Cjuridico cj;
            Bgeneral bg, auxBg;
            Eresultado er, auxEr;
            Persona persona, aux;

            if (!accion.isEmpty() && accion != null) {
                if (accion.equals("eliminar")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    cj = mj.recuperaJuridico(id);
                    if (cj.getBgenerals().size() > 0 || cj.getEresultados().size() > 0) {
                        json = muestramensaje("Eliminar cliente jurídico", "El cliente jurídico no se debe eliminar, ya que posee registros de balance general o estado de resultados.", "success", null);
                        out.print(json);
                        return;
                    }
                    if (mj.eliminar(cj) == 1) {
                        if (mj.eliminar(cj.getPersona()) == 1) {
                            json = muestramensaje("Eliminar cliente jurídico", "El cliente jurídico se ha eliminado correctamente.", "success", cj);
                            out.print(json);
                        } else {
                            json = muestramensaje("Eliminar cliente jurídico", "El cliente jurídico no se ha eliminado.", "success", null);
                            out.print(json);
                        }
                    } else {
                        json = muestramensaje("Eliminar cliente jurídico", "El cliente jurídico no se ha eliminado.", "success", null);
                        out.print(json);
                    }
                } else if (accion.equals("eliminarBalance")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    bg = mj.recuperaBG(id, "", "bg.id=?");
                    if (mj.eliminar(bg) == 1) {
                        json = muestramensaje("Eliminar cuenta", "La cuenta del balance general ha sido eliminada.", "success", bg.getCjuridico().getId());
                        out.print(json);
                    } else {
                        json = muestramensaje("Eliminar cuenta", "La cuenta del balance general no ha sido eliminada.", "error", null);
                        out.print(json);
                    }
                } else if (accion.equals("eliminarEstado")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    er = mj.recuperaER(id, "", "er.id=?");
                    if (mj.eliminar(er) == 1) {
                        json = muestramensaje("Eliminar cuenta", "La cuenta del estado de resultados ha sido eliminada.", "success", er.getCjuridico().getId());
                        out.print(json);
                    } else {
                        json = muestramensaje("Eliminar cuenta", "La cuenta del estado de resultados no ha sido eliminada.", "error", null);
                        out.print(json);
                    }
                } else if (accion.equals("guardar") || accion.equals("editar")) {

                    //listado de paramentros recibidos vía ajax
                    //representante
                    String dui = request.getParameter("dui");
                    String nombre = request.getParameter("nombre");
                    String apellido = request.getParameter("apellido");
                    String sexo = request.getParameter("sexo");
                    Date fechanacimiento = Date.valueOf(request.getParameter("fechanacimiento"));
                    String telefono = request.getParameter("telefono");
                    String direccion = request.getParameter("direccion");
                    String estadocivil = request.getParameter("estadocivil");
                    String tipo = request.getParameter("tipo");

                    //entidad
                    String codigo = request.getParameter("codigo");
                    String empresa = request.getParameter("empresa");
                    String telefonoempresa = request.getParameter("telefonoempresa");
                    String direccionempresa = request.getParameter("direccionempresa");

                    persona = new Persona(dui, nombre, apellido, sexo, telefono, direccion, estadocivil, fechanacimiento, tipo);
                    cj = new Cjuridico();
                    cj.setCodigo(codigo);
                    cj.setPersona(persona);
                    cj.setEmpresa(empresa);
                    cj.setDireccion(direccionempresa);
                    cj.setTelefono(telefonoempresa);
                    cj.setClasificacion(null);

                    if (accion.equals("guardar")) {
                        if (mj.recuperaPersona(0, dui, "p.dui=?") == null) {
                            if (mj.guardarFiador(persona, cj) == 1) {
                                json = muestramensaje("Registrar cliente jurídico", "El cliente jurídico se ha registrado correctamente.", "success", cj);
                                out.print(json);
                            } else {
                                json = muestramensaje("Registrar cliente jurídico", "El cliente jurídico no se ha registrado.", "error", null);
                                out.print(json);
                            }
                        } else {
                            json = muestramensaje("Registrar cliente jurídico", "El número de DUI ingresado para el representante de la entidad ya existe.", "info", null);
                            out.print(json);
                        }
                    } else {
                        int id = Integer.parseInt(request.getParameter("id"));
                        int idpersona = Integer.parseInt(request.getParameter("idpersona"));
                        persona.setId(idpersona);
                        cj.setId(id);
                        aux = mj.recuperaPersona(idpersona, "", "p.id=?");
                        if (!aux.getDui().equals(dui)) {
                            aux = mj.recuperaPersona(0, dui, "p.dui=?");
                            if (aux != null) {
                                if (aux.getDui().equals(dui)) {
                                    json = muestramensaje("Modificar cliente jurídico", "El número de DUI ingresado para el representante de la entidad ya existe.", "info", null);
                                    out.print(json);
                                    return;
                                }
                            }
                        }

                        if (mj.recuperaJuridico(id) != null) {
                            if (mj.modificarJuridico(persona, cj) == 1) {
                                json = muestramensaje("Modificar cliente jurídico", "El cliente jurídico se ha modificado correctamente.", "success", cj);
                                out.print(json);
                            } else {
                                json = muestramensaje("Modificar cliente jurídico", "El cliente jurídico no se ha modificado.", "error", null);
                                out.print(json);
                            }
                        } else {
                            json = muestramensaje("Alteraciones en el Servidor", "La operación ha fallado, al parecer han habido alteraciones de datos en el servidor.", "error", null);
                            out.print(json);
                        }
                    }
                } else if (accion.equals("guardarBalance") || accion.equals("editarBalance")) {

                    int idjuridico = Integer.parseInt(request.getParameter("idjuridico"));
                    String cuenta = request.getParameter("cuenta");
                    Double monto = Double.parseDouble(request.getParameter("monto"));
                    int anio = Integer.parseInt(request.getParameter("anio").replace(" ", ""));
                    String clasificacion = request.getParameter("clasificacion");
                    cj = mj.recuperaJuridico(idjuridico);
                    bg = new Bgeneral(cj, cuenta, monto, anio, clasificacion);
                    if (accion.equals("guardarBalance")) {
                        auxBg = mj.recuperaBG(0, cuenta, "bg.cuenta=?");
                        if (auxBg == null) {
                            if (mj.guardarInformacion(bg) == 1) {
                                json = muestramensaje("Registrar cuenta", "La cuenta ha sido registrada.", "success", idjuridico);
                                out.print(json);
                            } else {
                                json = muestramensaje("Registrar cuenta", "La cuenta no se ha registrado.", "error", null);
                                out.print(json);
                            }
                        } else {
                            json = muestramensaje("Registrar cuenta", "El nombre de la cuenta ingresado ya existe.", "info", null);
                            out.print(json);
                        }
                    } else {

                        int id = Integer.parseInt(request.getParameter("id"));
                        bg.setId(id);
                        auxBg = mj.recuperaBG(id, "", "bg.id=?");
                        if (!auxBg.getCuenta().equals(cuenta)) {
                            auxBg = mj.recuperaBG(0, cuenta, "bg.cuenta=?");
                            if (auxBg != null) {
                                if (auxBg.getCuenta().equals(cuenta)) {
                                    json = muestramensaje("Modificar cuenta", "El nombre de la cuenta ingresado ya existe.", "info", null);
                                    out.print(json);
                                    return;
                                }
                            }
                        }

                        if (mj.recuperaBG(id, "", "bg.id=?") != null) {
                            if (mj.modificarInformacion(bg) == 1) {
                                json = muestramensaje("Modificar cuenta", "La cuenta se ha modficado.", "success", idjuridico);
                                out.print(json);
                            } else {
                                json = muestramensaje("Modificar cuenta", "La cuenta no se ha modificado.", "error", null);
                                out.print(json);
                            }
                        } else {
                            json = muestramensaje("Alteraciones en el Servidor", "La operación ha fallado, al parecer han habido alteraciones de datos en el servidor.", "error", null);
                            out.print(json);
                        }
                    }

                } else if (accion.equals("guardarEstado") || accion.equals("editarEstado")) {

                    int idjuridico = Integer.parseInt(request.getParameter("idjuridico"));
                    String cuenta = request.getParameter("cuentaer");
                    Double monto = Double.parseDouble(request.getParameter("montoer"));
                    int anio = Integer.parseInt(request.getParameter("anioer").replace(" ", ""));
                    cj = mj.recuperaJuridico(idjuridico);
                    er = new Eresultado(cj, cuenta, monto, anio);
                    if (accion.equals("guardarEstado")) {
                        auxEr = mj.recuperaER(0, cuenta, "er.cuenta=?");
                        if (auxEr == null) {
                            if (mj.guardarInformacion(er) == 1) {
                                json = muestramensaje("Registrar cuenta", "La cuenta ha sido registrada.", "success", idjuridico);
                                out.print(json);
                            } else {
                                json = muestramensaje("Registrar cuenta", "La cuenta no se ha registrado.", "error", null);
                                out.print(json);
                            }
                        } else {
                            json = muestramensaje("Registrar cuenta", "El nombre de la cuenta ingresado ya existe.", "info", null);
                            out.print(json);
                        }
                    } else {
                        int id = Integer.parseInt(request.getParameter("id"));
                        er.setId(id);
                        auxEr = mj.recuperaER(id, "", "er.id=?");
                        if (!auxEr.getCuenta().equals(cuenta)) {
                            auxEr = mj.recuperaER(0, cuenta, "er.cuenta=?");
                            if (auxEr != null) {
                                if (auxEr.getCuenta().equals(cuenta)) {
                                    json = muestramensaje("Modificar cuenta", "El nombre de la cuenta ingresado ya existe.", "info", null);
                                    out.print(json);
                                    return;
                                }
                            }
                        }

                        if (mj.recuperaER(id, "", "er.id=?") != null) {
                            if (mj.modificarInformacion(er) == 1) {
                                json = muestramensaje("Modificar cuenta", "La cuenta se ha modficado.", "success", idjuridico);
                                out.print(json);
                            } else {
                                json = muestramensaje("Modificar cuenta", "La cuenta no se ha modificado.", "error", null);
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
