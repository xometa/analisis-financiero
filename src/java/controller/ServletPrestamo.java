/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Bgeneral;
import entities.Cjuridico;
import entities.Persona;
import entities.Prestamo;
import entities.Tipoprestamo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.ModeloPrestamo;

/**
 *
 * @author BONIFACIO
 */
public class ServletPrestamo extends HttpServlet {

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
            ModeloPrestamo mp = new ModeloPrestamo();
            // ModeloEmpleado memple = new ModeloEmpleado();
            Prestamo p;
            Persona persona = null, aux, objfiador = null, objjuridico = null;
            if (!accion.isEmpty() && accion != null) {
                if (accion.equals("guardar")) {
                    //variables del formulario
                    String tprestamo = request.getParameter("tprestamo");
                    String natural = request.getParameter("natural");
                    String fiador = request.getParameter("fiador");
                    String juridico = request.getParameter("juridico");
                    float monto = Float.parseFloat(request.getParameter("monto"));
                    int numcuotas = Integer.parseInt(request.getParameter("numcuotas"));

                    int idpersona = 0;
                    if (!natural.equals("0")) {
                        idpersona = Integer.parseInt(natural);
                        persona = mp.recuperaPersona(idpersona, "p.id=? and p.tipo='Natural'");//recuperando la persona
                    }

                    int idfiador = 0;
                    if (!fiador.equals("0")) {
                        idfiador = Integer.parseInt(fiador);
                        objfiador = mp.recuperaPersona(idfiador, "p.id=? and p.tipo='Fiador'");//recuperando la persona
                    }

                    int idjuridico;
                    if (!juridico.equals("0")) {
                        idjuridico = Integer.parseInt(juridico);
                        objjuridico = mp.recuperarRepresentanteLegal(idjuridico);//recuperando la persona juridica
                    }

                    Tipoprestamo objtprestamo = null;
                    int idprestamo;
                    if (!tprestamo.equals('0')) {
                        idprestamo = Integer.parseInt(tprestamo);
                        objtprestamo = mp.recuperaTipoPrestamo(idprestamo);
                    }

                    Date objfecha = new Date();

                    p = new Prestamo();
                    boolean esNatural = false;

                    if (objjuridico != null) {
                        p.setPersona(objjuridico);
                    } else {
                        esNatural = true;
                        p.setPersona(persona);
                    }

                    p.setTipoprestamo(objtprestamo);
                    p.setFecha(objfecha);
                    p.setMonto(monto);
                    p.setNumcuotas(numcuotas);
                    p.setEstado(1);//activo

                    if (esNatural) {
                        int anioPrestamo = numcuotas / 12;
                        int anioActual = objfecha.getYear() + 1900;
                        int anioNacimiento = persona.getFechanacimiento().getYear() + 1900;
                        if ((anioActual - anioNacimiento) + anioPrestamo > 80) {
                            json = muestramensaje("Registrar Prestamo", "La edad del Cliente más el tiempo del prestamo tienen que ser menor a o igual a 80 años.", "error", null);
                            out.print(json);
                        } else {
                            if (objfiador != null) {
                                if (mp.guardarPrestamo(p) == 1) {
                                    if (mp.guardarDetFiador(objfiador) == 1) {
                                        json = muestramensaje("Registrar Prestamo", "El prestamo se ha registrado correctamente.", "success", p);
                                        out.print(json);
                                    }
                                } else {
                                    json = muestramensaje("Registrar Prestamo", "El prestamo no se ha registrado.", "error", null);
                                    out.print(json);
                                }
                            } else {
                                json = muestramensaje("Registrar Prestamo", "El prestamo no se ha registrado problemas con el fiador.", "error", null);
                                out.print(json);
                            }
                        }
                    } else if (objjuridico != null) {//si  se esta guardando un prestamo de tipo juridico
                        //validar razones financieras
                        Cjuridico cj =  objjuridico.getCjuridicos().iterator().next();
                        int totalActivosC = mp.obtnerTotalActivos(cj.getId());
                        int totalPasivosC = mp.obtnerTotalPasivos(cj.getId());
                        float liquidez = (float) (totalActivosC / totalPasivosC );
                        if (liquidez > 1) {

                           if (mp.guardarPrestamo(p) == 1) {
                                json = muestramensaje("Registrar Prestamo", "El prestamo se ha registrado correctamente.", "success", p);
                                out.print(json);
                            } else {
                                json = muestramensaje("Registrar Prestamo", "El prestamo no se ha registrado.", "error", null);
                                out.print(json);
                            }
                        } else {
                            json = muestramensaje("Registrar Prestamo", "Este cliente juridico no es apto para prestamo", "warning", null);
                            out.print(json);
                        }
                    } else {
                        json = muestramensaje("Registrar Prestamo", "El prestamo no se ha registrado.", "error", null);
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
