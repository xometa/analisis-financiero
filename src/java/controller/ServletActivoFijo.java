/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Activobaja;
import entities.Activofijo;
import entities.Departamento;
import entities.Sucursal;
import entities.Tipoactivo;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.ModeloActivoFijo;

/**
 *
 * @author jsaul
 */
public class ServletActivoFijo extends HttpServlet {

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
            ModeloActivoFijo ma = new ModeloActivoFijo();
            Activofijo af, aux;
            Activobaja ab;
            Sucursal sucursal;
            Departamento departamento;
            Tipoactivo tipoactivo;
            if (!accion.isEmpty() && accion != null) {
                if (accion.equals("darbajaactivo")) {
                    int idactivofijo = Integer.parseInt(request.getParameter("idactivofijo"));
                    String motivo = request.getParameter("motivo");
                    Date fecha = Date.valueOf(request.getParameter("fecha"));
                    int estado = Integer.parseInt(request.getParameter("estado"));

                    if (idactivofijo <= 0) {
                        json = muestramensaje("Dar de baja activo fijo", "Los datos del servidor han sido alterados.", "error", null);
                        out.print(json);
                        return;
                    }

                    af = ma.recuperaActivo(idactivofijo, "", "af.id=?");
                    if (af == null) {
                        json = muestramensaje("Dar de baja activo fijo", "El activo fijo no se ha dado de baja.", "error", null);
                        out.print(json);
                        return;
                    }

                    af.setUso(estado);
                    ab = new Activobaja(af, motivo, fecha);
                    //actualizar el activo fijo
                    if (ma.modificarActivo(af) == 1) {
                        //guardar la baja del activo
                        if (ma.guardarActivo(ab) == 1) {
                            json = muestramensaje("Dar de baja activo fijo", "El activo fijo se ha dado de baja correctamente.", "success", af);
                            out.print(json);
                        } else {
                            json = muestramensaje("Dar de baja activo fijo", "El activo fijo no se ha dado de baja.", "error", null);
                            out.print(json);
                        }
                    } else {
                        json = muestramensaje("Dar de baja activo fijo", "El activo fijo no se ha dado de baja.", "error", null);
                        out.print(json);
                    }
                } else if (accion.equals("eliminar")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    af = ma.recuperaActivo(id, "", "af.id=?");
                    if (af.getActivobajas().size() > 0) {
                        json = muestramensaje("Eliminar activo fijo", "El activo fijo no se debe eliminar, ya que ha sido dado de baja.", "success", null);
                        out.print(json);
                        return;
                    }
                    if (ma.eliminar(af) == 1) {
                        json = muestramensaje("Eliminar activo fijo", "El activo fijo se ha eliminado correctamente.", "success", af);
                        out.print(json);
                    } else {
                        json = muestramensaje("Eliminar activo fijo", "El activo fijo no se ha eliminado.", "success", null);
                        out.print(json);
                    }
                } else if (accion.equals("modificarestado")) {
                    String message = "";
                    int id = Integer.parseInt(request.getParameter("id"));
                    int estado = Integer.parseInt(request.getParameter("estado"));
                    af = ma.recuperaActivo(id, "", "af.id=?");
                    af.setUso(estado);
                    if (ma.modificarActivo(af) == 1) {
                        if (estado == 0) {
                            message = "deshabilitado";
                        } else {
                            message = "habilitado";
                        }
                        json = muestramensaje("Estado del activo fijo", "El activo fijo se ha " + message, "success", -1);
                        out.print(json);
                    } else {
                        json = muestramensaje("Estado del activo fijo", "El estado del activo fijo no se pudo actualizar.", "success", null);
                        out.print(json);
                    }
                } else if (accion.equals("guardar") || accion.equals("editar")) {

                    //listado de paramentros recibidos vía ajax
                    String nombre = request.getParameter("nombre");
                    String descripcion = request.getParameter("descripcion");
                    String procedencia = request.getParameter("procedencia");
                    BigDecimal precio = new BigDecimal(request.getParameter("precio"));
                    Date fechaadquisicion = Date.valueOf(request.getParameter("fechaadquisicion"));
                    int vidautil = Integer.parseInt(request.getParameter("vidautil"));
                    int idsucursal = Integer.parseInt(request.getParameter("sucursal"));
                    int iddepartamento = Integer.parseInt(request.getParameter("departamento"));
                    int idtipoactivo = Integer.parseInt(request.getParameter("tipoactivo"));

                    sucursal = ma.recuperaSucursal(idsucursal);
                    departamento = ma.recuperaDepartamento(iddepartamento);
                    tipoactivo = ma.recuperaTipoactivo(idtipoactivo);

                    if (sucursal == null || departamento == null || tipoactivo == null) {
                        json = muestramensaje("Alteraciones en el Servidor", "La operación ha fallado, al parecer han habido alteraciones de datos en el servidor.", "error", null);
                        out.print(json);
                        return;
                    }

                    af = new Activofijo(departamento, sucursal, tipoactivo, nombre, descripcion, procedencia, precio, fechaadquisicion, vidautil);
                    if (accion.equals("guardar")) {
                        af.setUso(1);
                        if (ma.recuperaActivo(0, nombre, "af.nombre=?") == null) {
                            if (ma.guardarActivo(af) == 1) {
                                json = muestramensaje("Registrar activo fijo", "El activo fijo se ha registrado correctamente.", "success", af);
                                out.print(json);
                            } else {
                                json = muestramensaje("Registrar activo fijo", "El activo fijo no se ha registrado.", "error", null);
                                out.print(json);
                            }
                        } else {
                            json = muestramensaje("Registrar activo fijo", "El nombre del activo fijo ingresado ya existe.", "info", null);
                            out.print(json);
                        }
                    } else {
                        int id = Integer.parseInt(request.getParameter("id"));
                        af.setId(id);
                        aux = ma.recuperaActivo(id, "", "af.id=?");
                        if (!aux.getNombre().equals(nombre)) {
                            aux = ma.recuperaActivo(0, nombre, "af.nombre=?");
                            if (aux != null) {
                                if (aux.getNombre().equals(nombre)) {
                                    json = muestramensaje("Modificar activo fijo", "El nombre ingresado para el activo fijo ya existe.", "info", null);
                                    out.print(json);
                                    return;
                                }
                            }
                        }
                        aux = ma.recuperaActivo(id, "", "af.id=?");
                        af.setUso(aux.getUso());
                        if (aux != null) {
                            if (ma.modificarActivo(af) == 1) {
                                json = muestramensaje("Modificar activo fijo", "El activo fijo se ha modificado correctamente.", "success", af);
                                out.print(json);
                            } else {
                                json = muestramensaje("Modificar activo fijo", "El activo fijo no se ha modificado.", "error", null);
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
