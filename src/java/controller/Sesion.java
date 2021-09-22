/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.ModeloUsuario;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author jsaul
 */
public class Sesion extends HttpServlet {

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
            ModeloUsuario mu = new ModeloUsuario();
            Usuario usuario;
            RequestDispatcher despachador = null;

            //obtención de los parametros
            String user = request.getParameter("usuario");
            String password = request.getParameter("password");
            if (user.isEmpty() || password.isEmpty()) {
                despachador = request.getRequestDispatcher("login.jsp");
            } else {
                String newpass = DigestUtils.md5Hex(password);
                usuario = mu.validaSesion(user, newpass);
                if (usuario != null) {
                    if (usuario.getActivo() == 1) {
                        HttpSession session = request.getSession();
                        session.setAttribute("idusuario", usuario.getId());
                        session.setAttribute("usuario", usuario.getUsuario());
                        session.setAttribute("correo", usuario.getCorreo());
                        session.setAttribute("nivel", usuario.getNivel());
                        session.setAttribute("nombre", usuario.getPersona().getNombre() + " " + usuario.getPersona().getApellido());
                        session.setAttribute("sexo", usuario.getPersona().getSexo());

                        despachador = request.getRequestDispatcher("index.jsp");
                    } else {
                        despachador = request.getRequestDispatcher("login.jsp");
                    }
                } else {
                    despachador = request.getRequestDispatcher("login.jsp");
                }
            }

            despachador.forward(request, response);
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

}
