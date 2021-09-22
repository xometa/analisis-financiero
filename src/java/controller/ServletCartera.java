/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Carterageneral;
import entities.Detallecartera;
import entities.Persona;
import entities.Sucursal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.ModeloCartera;
import models.ModeloEmpleado;
import models.ModeloSucursal;

/**
 *
 * @author Kevin
 */
public class ServletCartera extends HttpServlet {

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
            String clientes[];
            ModeloCartera mc = new ModeloCartera();
            ModeloSucursal mds = new ModeloSucursal();
            ModeloEmpleado me = new ModeloEmpleado();
            Carterageneral cartera, aux;
            Persona persona, paux;
            Sucursal sucursal;
            ArrayList<Detallecartera> dca = new ArrayList<>();
            Detallecartera dc;
            int estado = 0;
            if (!accion.isEmpty() && accion != null) {
                if (accion.equals("guardar")) {
                    //recibiendo variables
                    int idsucursal = Integer.parseInt(request.getParameter("idsucursal"));
                    int idasesor = Integer.parseInt(request.getParameter("idasesor"));
                    clientes = request.getParameterValues("clientes[]");
                    sucursal = mds.recuperaSucursal(idsucursal, "", "su.id=?");
                    persona = me.recuperaPersona(idasesor, "", "p.id=?");
                    aux = mc.carteraGeneral(idsucursal, "cg.sucursal.id=?");
                    if (sucursal == null) {
                        json = muestramensaje("Registrar cartera", "La sucursal seleccionada no existe.", "error", null);
                        out.print(json);
                        return;
                    }

                    if (persona == null) {
                        json = muestramensaje("Registrar cartera", "El asesor seleccionado, para la cartera no existe.", "error", null);
                        out.print(json);
                        return;
                    }

                    if (clientes.length == 0) {
                        json = muestramensaje("Registrar cartera", "La cartera no posee ningun cliente seleccionado.", "info", null);
                        out.print(json);
                        return;
                    }

                    int cantidadcl = clientes.length;
                    cartera = new Carterageneral(persona, sucursal, 0);
                    for (int i = 0; i < cantidadcl; i++) {
                        paux = mc.recuperaCliente(Integer.parseInt(clientes[i]));
                        if (paux == null) {
                            json = muestramensaje("Registrar cartera", "Uno de los clientes seleccionados, para la cartera no existe.", "error", null);
                            out.print(json);
                            return;
                        }
                        dc = new Detallecartera();
                        dc.setPersona(paux);
                        dca.add(dc);
                    }

                    //si es primera vez que se registra la cartera, se procede
                    //a registrar el encabezado. Caso contrario la cartera ya 
                    //existe solo se agregan más clientes
                    if (aux == null) {
                        if (mc.guardarCartera(cartera) == 1) {
                            cartera.setId(mc.carteraGeneral(sucursal.getId(), "cg.sucursal.id=?").getId());
                            for (Detallecartera detallecartera : dca) {
                                detallecartera.setCarterageneral(cartera);
                                estado = mc.guardarDetalleCartera(detallecartera);
                            }
                            if (estado == 1) {
                                json = muestramensaje("Registrar cartera", "La cartera se ha registrado correctamente.", "success", cartera);
                                out.print(json);
                            } else {
                                json = muestramensaje("Registrar cartera", "La cartera no se ha registrado.", "error", null);
                                out.print(json);
                            }
                        } else {
                            json = muestramensaje("Registrar cartera", "La cartera no se ha registrado.", "error", null);
                            out.print(json);
                        }
                    } else {
                        aux.setPersona(persona);
                        mc.modificarCartera(aux);
                        for (Detallecartera detallecartera : dca) {
                            detallecartera.setCarterageneral(aux);
                            estado = mc.guardarDetalleCartera(detallecartera);
                        }
                        if (estado == 1) {
                            json = muestramensaje("Registrar cartera", "La cartera se ha registrado correctamente.", "success", cartera);
                            out.print(json);
                        } else {
                            json = muestramensaje("Registrar cartera", "La cartera no se ha registrado.", "error", null);
                            out.print(json);
                        }
                    }
                } else if (accion.equals("eliminarclientecartera")) {
                    int idcartera = Integer.parseInt(request.getParameter("idcartera"));
                    int idcliente = Integer.parseInt(request.getParameter("idcliente"));
                    int idsucursal = Integer.parseInt(request.getParameter("idsucursal"));
                    dc = mc.recuperaClienteCartera(idcartera, idcliente);
                    if (dc != null) {
                        if (mc.eliminarCartera(dc) == 1) {
                            aux = mc.carteraGeneral(idcartera, "cg.id=?");
                            if (aux.getDetallecarteras().isEmpty()) {
                                mc.eliminarCartera(aux);
                                idsucursal = 0;
                            }
                            json = muestramensaje("Eliminar cliente", "El cliente se ha eliminado correctamente.", "success", idsucursal);
                            out.print(json);
                        } else {
                            json = muestramensaje("Eliminar cliente", "El cliente no se ha eliminado.", "success", null);
                            out.print(json);
                        }
                    } else {
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
