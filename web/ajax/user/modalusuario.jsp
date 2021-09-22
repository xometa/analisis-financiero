<%-- 
    Document   : modalusuario
    Created on : 02-ene-2021, 12:13:45
    Author     : BONIFACIO
--%>

<%@page import="entities.Persona"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entities.Usuario"%>
<%@page import="models.ModeloUsuario"%>
<%@page import="models.ModeloEmpleado"%>
<%@page import="entities.Cnatural"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    ModeloUsuario mu = new ModeloUsuario();
    Usuario usuario;
    usuario = mu.recuperarUsuario(id,"u.persona.id=?");
%>

<%    if (usuario != null) {
%>
<input type="hidden" class="form-control" id="idusuario" name="idusuario" value="<%=usuario.getId()%>">
<%
    }
%>
<input type="hidden" class="form-control" id="idpersona" name="idempleado" value="<%=id%>">
<div class="form-group px-3">
    <label class="font-weight-bold">Usuario<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-archive"></span>
            </div>
        </div>
        <input type="text" class="form-control" id="usuario" name="usuario" value="<%=(usuario == null) ? "" : usuario.getUsuario()%>">
    </div>
</div>

<div class="form-group px-3">
    <label class="font-weight-bold">Contraseña <%=(usuario == null) ? "<span class='text-danger'>*</span>" : ""%></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-archive"></span>
            </div>
        </div>
        <input type="password" class="form-control" id="clave" name="clave" >
    </div>
</div>

<div class="form-group px-3">
    <label class="font-weight-bold">Correo<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-archive"></span>
            </div>
        </div>
        <input type="text" class="form-control" id="correo" name="correo" value="<%=(usuario == null) ? "" : usuario.getCorreo()%>">
    </div>
</div>


<div class="form-group px-3">
    <label class="font-weight-bold">Nivel<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-archive"></span>
            </div>
        </div>
        <select id="nivel" name="nivel" class="custom-select form-control" required>
            <option value=" ">--- Seleccionar opción ---</option>
            <%if (usuario != null) {%>
            <option value="1" <%=(usuario.getNivel() == 1) ? "selected" : ""%>>1</option>
            <option value="2" <%=(usuario.getNivel() == 2) ? "selected" : ""%>>2</option>
            <option value="3" <%=(usuario.getNivel() == 3) ? "selected" : ""%>>3</option>
            <%} else {%>
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <%}%>
        </select>
    </div>
</div>
