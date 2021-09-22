<%-- 
    Document   : infousuario
    Created on : Jan 19, 2021, 1:03:50 PM
    Author     : jsaul
--%>

<%@page import="entities.Usuario"%>
<%@page import="models.ModeloUsuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Object idusuario = session.getAttribute("idusuario");
    int id = Integer.parseInt(request.getParameter("id"));
    String imagen = "";
    ModeloUsuario mu = new ModeloUsuario();
    Usuario usuario;
    usuario = mu.recuperarUsuario(id, "u.persona.id=?");
    if (usuario != null) {
        if (usuario.getPersona().getSexo().equals("Masculino")) {
            imagen = "assets/images/avatar1.jpg";
        } else {
            imagen = "assets/images/avatar2.jpg";
        }
%>

<img src="<%= imagen %>" class="rounded-circle avatar-lg img-thumbnail" alt="imagen-perfil">

<h4 class="mb-0 mt-2"><%= usuario.getPersona().getNombre() + " " + usuario.getPersona().getApellido()%></h4>
<p class="text-muted font-14">Rol</p>

<div class="text-left mt-3">
    <p class="text-muted mb-2 font-13"><strong>Usuario :</strong> <span class="ml-2"><%= usuario.getUsuario()%></span></p>

    <p class="text-muted mb-2 font-13"><strong>Correo :</strong> <span class="ml-2 ">
            <%if (!usuario.getCorreo().isEmpty()) {
                    out.print(usuario.getCorreo());
                } else {
                    out.print("Sin información");

                }%>
        </span></p>
    <div class="row">
        <div class="col-sm-4">
            <p class="text-muted mb-1 font-13 d-inline-block"><strong>Estado :</strong>
            </p>
        </div>
        <div class="col-sm-8">

            <%if (usuario.getId() != Integer.parseInt(String.valueOf(idusuario))) {%>

            <%if (usuario.getActivo() == 1) {%>
            <input type="checkbox" id="switch3" checked data-switch="success"
                   data-idusuario="<%= usuario.getId()%>" />
            <%} else {%>
            <input type="checkbox" id="switch3" data-switch="success" data-idusuario="<%= usuario.getId()%>" />
            <%}%>
            <label for="switch3" data-on-label="" data-off-label=""></label>
            <%} else {
                    if (usuario.getActivo() == 1) {
                        out.print("<span class='badge badge-success'>Activo</span>");
                    } else {
                        out.print("<span class='badge badge-danger'>Inactivo</span>");
                    }

                }%>
        </div>
    </div>
</div>
<%if (usuario.getId() != Integer.parseInt(String.valueOf(idusuario))) {%>
<button type="button" class="btn btn-danger btn-sm mb-2" data-idusuario="<%= usuario.getId()%>"><i
        class="mdi mdi-delete"></i> Eliminar usuario</button>

<%}
} else {%>
La información del servidor se ha alterado.
<%}%>