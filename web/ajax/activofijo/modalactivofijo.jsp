<%-- 
    Document   : modalactivofijo
    Created on : Jan 1, 2021, 3:11:03 PM
    Author     : jsaul
--%>
<%@page import="entities.Tipoactivo"%>
<%@page import="entities.Departamento"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entities.Sucursal"%>
<%@page import="java.util.List"%>
<%@page import="entities.Activofijo"%>
<%@page import="models.ModeloActivoFijo"%>
<%

    int id = Integer.parseInt(request.getParameter("id"));
    ModeloActivoFijo maf = new ModeloActivoFijo();
    Activofijo activo;
    activo = maf.recuperaActivo(id, "", "af.id=?");
    List<Sucursal> sucursales = (ArrayList< Sucursal>) maf.listadoSucursal();
    List<Departamento> departamentos = (ArrayList< Departamento>) maf.listadoDepartamento();
    List<Tipoactivo> tipoactivos = (ArrayList< Tipoactivo>) maf.listadoTipoActivo();
%>

<%    if (activo != null) {
%>
<input type="hidden" class="form-control" id="id" name="id" value="<%=activo.getId()%>">
<%
    }
%>

<div class="form-group px-3">
    <label class="font-weight-bold">Nombre<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-archive"></span>
            </div>
        </div>
        <input type="text" class="form-control" id="nombre" name="nombre" value="<%=(activo == null) ? "" : activo.getNombre()%>">
    </div>
</div>

<div class="form-group px-3">
    <label class="font-weight-bold">Descripción<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-comment-plus"></span>
            </div>
        </div>
        <textarea type="text" class="form-control" id="descripcion" name="descripcion"><%= (activo == null) ? "" : activo.getDescripcion()%></textarea>
    </div>
</div>

<div class="form-group px-3">
    <label class="font-weight-bold">Procedencia<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-invoice"></span>
            </div>
        </div>
        <select id="procedencia" name="procedencia" class="custom-select form-control" required>
            <option value=" ">--- Seleccionar opción ---</option>
            <%if (activo != null) {%>

            <option value="Nuevo" <%=(activo.getProcedencia().equals("Nuevo")) ? "selected" : ""%> >Nuevo</option>
            <option value="Usado" <%=(activo.getProcedencia().equals("Usado")) ? "selected" : ""%> >Usado</option>
            <option value="Donado nuevo" <%=(activo.getProcedencia().equals("Donado nuevo")) ? "selected" : ""%> >Donado nuevo</option>
            <option value="Donado usado" <%=(activo.getProcedencia().equals("Donado usado")) ? "selected" : ""%> >Donado usado</option>
            <%} else {%>
            <option value="Nuevo">Nuevo</option>
            <option value="Usado">Usado</option>
            <option value="Donado nuevo">Donado nuevo</option>
            <option value="Donado usado">Donado usado</option>
            <%}%>
        </select>
    </div>
</div>

<div class="form-group px-3">
    <label class="font-weight-bold">Precio<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-dollar-sign"></span>
            </div>
        </div>
        <input type="text" class="form-control" id="precio" name="precio" value="<%=(activo == null) ? "" : activo.getPrecio()%>">
    </div>
</div>

<div class="form-group px-3">
    <label class="font-weight-bold">Fecha adquisición<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-schedule"></span>
            </div>
        </div>
        <input type="date" class="form-control" id="fechaadquisicion" name="fechaadquisicion" value="<%=(activo == null) ? "" : activo.getFechaadquisicion()%>">
    </div>
</div>

<div class="form-group px-3">
    <label class="font-weight-bold">Vida útil<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-hunting"></span>
            </div>
        </div>
        <input type="number" class="form-control" id="vidautil" name="vidautil" value="<%=(activo == null) ? "" : activo.getVidautil()%>">
    </div>
</div>

<div class="form-group px-3">
    <label class="font-weight-bold">Sucursal<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-map-pin-alt"></span>
            </div>
        </div>
        <select id="sucursal" name="sucursal" class="custom-select form-control" required>
            <option value=" ">--- Seleccionar opción ---</option>
            <%
                for (Sucursal s : sucursales) {
                    if (activo != null) {
                        if (s.getNombre().equals(activo.getSucursal().getNombre())) {
            %>
            <option value="<%= s.getId()%>" selected><%= s.getNombre()%></option>
            <%
            } else {
            %>

            <option value="<%= s.getId()%>"><%= s.getNombre()%></option>
            <%
                }
            } else {
            %>
            <option value="<%= s.getId()%>"><%= s.getNombre()%></option>
            <%
                    }
                }
            %>
        </select>
    </div>
</div>   

<div class="form-group px-3">
    <label class="font-weight-bold">Departamento<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-sitemap"></span>
            </div>
        </div>
        <select id="departamento" name="departamento" class="custom-select form-control" required>
            <option value=" ">--- Seleccionar opción ---</option>
            <%
                for (Departamento d : departamentos) {
                    if (activo != null) {
                        if (d.getNombre().equals(activo.getDepartamento().getNombre())) {
            %>
            <option value="<%= d.getId()%>" selected><%= d.getNombre()%></option>
            <%
            } else {
            %>

            <option value="<%= d.getId()%>"><%= d.getNombre()%></option>
            <%
                }
            } else {
            %>
            <option value="<%= d.getId()%>"><%= d.getNombre()%></option>
            <%
                    }
                }
            %>
        </select>
    </div>
</div>

<div class="form-group px-3">
    <label class="font-weight-bold">Tipo activo<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-file-copy-alt"></span>
            </div>
        </div>
        <select id="tipoactivo" name="tipoactivo" class="custom-select form-control" required>
            <option value=" ">--- Seleccionar opción ---</option>
            <%
                for (Tipoactivo ta : tipoactivos) {
                    if (activo != null) {
                        if (ta.getNombre().equals(activo.getTipoactivo().getNombre())) {
            %>
            <option value="<%= ta.getId()%>" selected><%= ta.getNombre()%></option>
            <%
            } else {
            %>

            <option value="<%= ta.getId()%>"><%= ta.getNombre()%></option>
            <%
                }
            } else {
            %>
            <option value="<%= ta.getId()%>"><%= ta.getNombre()%></option>
            <%
                    }
                }
            %>
        </select>
    </div>
</div>