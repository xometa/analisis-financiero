/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Persona;
import entities.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.ModeloUsuario;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author BONIFACIO
 */
public class ServletUsuario extends HttpServlet {

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
            ModeloUsuario mu = new ModeloUsuario();
            // ModeloEmpleado memple = new ModeloEmpleado();
            Usuario u, usaux;
            Persona persona, aux;
            if (!accion.isEmpty() && accion != null) {
                if (accion.equals("eliminar")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    u = mu.recuperarUsuario(id, "u.id=?");
                    if (mu.eliminarUsuario(u) == 1) {
                        json = muestramensaje("Eliminar Usuario", "El usuario se ha eliminado correctamente.", "success", u);
                        out.print(json);
                    } else {
                        json = muestramensaje("Eliminar Usuario", "El usuario no se ha eliminado.", "success", null);
                        out.print(json);
                    }
                } else if (accion.equals("modificarestado")) {
                    String message = "";
                    int id = Integer.parseInt(request.getParameter("id"));
                    int estado = Integer.parseInt(request.getParameter("estado"));
                    u = mu.recuperarUsuario(id, "u.id=?");
                    u.setActivo(estado);
                    if (mu.modificarUsuario(u) == 1) {
                        if (estado == 0) {
                            message = "deshabilitado";
                        } else {
                            message = "habilitado";
                        }
                        json = muestramensaje("Estado del Usuario", "El usuario se ha " + message, "success", -1);
                        out.print(json);
                    } else {
                        json = muestramensaje("Estado del Usuario", "El del usuario no se pudo actualizar.", "success", null);
                        out.print(json);
                    }
                } else if (accion.equals("guardar") || accion.equals("editar")) {
                    //variables del formulario
                    int idempleado = Integer.parseInt(request.getParameter("idempleado"));
                    String usuario = request.getParameter("usuario");
                    String clave = request.getParameter("clave");
                    String correo = request.getParameter("correo");
                    int nivel = Integer.parseInt(request.getParameter("nivel"));

                    persona = mu.recuperaPersona(idempleado, "", "p.id=?");//recuperando el empleado
                    u = new Usuario();
                    u.setPersona(persona);
                    u.setUsuario(usuario);
                    u.setCorreo(correo);
                    u.setNivel(nivel);
                    u.setActivo(0);

                    if (accion.equals("guardar")) {
                        if (mu.buscarUserName(usuario, "u.usuario=?") != null) {
                            json = muestramensaje("Registrar usuario", "El nombre de usuario ya esta asignado, aún empleado.", "info", null);
                            out.print(json);
                            return;
                        }

                        if (mu.buscarUserName(correo, "u.correo=?") != null) {
                            json = muestramensaje("Registrar usuario", "El correo del usuario ya esta asignado, aún empleado.", "info", null);
                            out.print(json);
                            return;
                        }

                        if (mu.recuperaPersona(idempleado, "", "p.id=?") != null) {
                            //ENCRIPTANDO CONTRASEÑA
                            String textoEncriptadoConMD5 = DigestUtils.md5Hex(clave);
                            u.setClave(textoEncriptadoConMD5);
                            if (mu.guardarUsuario(u) == 1) {
                                json = muestramensaje("Registrar usuario", "El usuario se ha registrado correctamente.", "success", u);
                                out.print(json);
                            } else {
                                json = muestramensaje("Registrar usuario", "El usuario no se ha registrado.", "error", null);
                                out.print(json);
                            }
                        } else {
                            json = muestramensaje("Registrar usuario", "Este empleado no existe.", "info", null);
                            out.print(json);
                        }

                    } else {
                        int id = Integer.parseInt(request.getParameter("idusuario"));
                        usaux = mu.recuperarUsuario(id, "u.id=?");
                        if (!usaux.getUsuario().equals(usuario)) {
                            if (mu.buscarUserName(usuario, "u.usuario=?") != null) {
                                json = muestramensaje("Registrar usuario", "El nombre de usuario ya esta asignado, aún empleado.", "info", null);
                                out.print(json);
                                return;
                            }
                        }

                        if (!usaux.getCorreo().equals(correo)) {
                            if (mu.buscarUserName(correo, "u.correo=?") != null) {
                                json = muestramensaje("Registrar usuario", "El correo del usuario ya esta asignado, aún empleado.", "info", null);
                                out.print(json);
                                return;
                            }
                        }

                        usaux.setUsuario(usuario);
                        usaux.setCorreo(correo);
                        usaux.setNivel(nivel);
                        if (!clave.isEmpty()) {
                            //ENCRIPTANDO CONTRASEÑA
                            String textoEncriptadoConMD5 = DigestUtils.md5Hex(clave);
                            u.setClave(textoEncriptadoConMD5);
                            usaux.setClave(textoEncriptadoConMD5);
                        }

                        if (mu.recuperarUsuario(id, "u.id=?") != null) {
                            if (mu.modificarUsuario(usaux) == 1) {
                                json = muestramensaje("Modificar usuario", "El usuario se ha modificado correctamente.", "success", u);
                                out.print(json);
                            } else {
                                json = muestramensaje("Modificar usuario", "El usuario no se ha modificado.", "error", null);
                                out.print(json);
                            }
                        } else {
                            json = muestramensaje("Alteraciones en el servidor", "La operación ha fallado, al parecer han habido alteraciones de datos en el servidor.", "error", null);
                            out.print(json);
                        }
                    }
                } else {
                    json = muestramensaje("Alteraciones en el servidor", "La operación ha fallado, al parecer han habido alteraciones de datos en el servidor.", "error", null);
                    out.print(json);
                }
            } else {
                json = muestramensaje("Alteraciones en el servidor", "La operación ha fallado, al parecer han habido alteraciones de datos en el servidor.", "error", null);
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
