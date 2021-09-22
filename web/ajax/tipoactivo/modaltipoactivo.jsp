<%-- 
    Document   : modaltipoactivo
    Created on : ene 6, 2021, 8:18:42 p.m.
    Author     : Kevin
--%>

<%@page import="process.GeneraCodigos"%>
<%@page import="entities.Tipoactivo"%>
<%@page import="models.ModeloTipoActivo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    ModeloTipoActivo mta = new ModeloTipoActivo();
    GeneraCodigos cg = new GeneraCodigos();
    int valor = cg.autoincrementableTipo(mta.listadoTipo());
    String codigo = cg.codigok("TP", valor);
    Tipoactivo tipo;
    tipo = mta.recuperaTipo(id, "", "ta.id=?");
%>

<%    if (tipo != null) {
%>
<input type="hidden" class="form-control" id="id" name="id" value="<%=tipo.getId()%>">
<%
    }
%>

<div class="form-group px-3" style="<%=(tipo == null) ? "display:block" : "display:none"%>">
    <label class="font-weight-bold">Código<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-copyright"></span>
            </div>
        </div>
        <input type="text" class="form-control" id="codigo" readonly="readonly" onkeypress="return false;" name="codigo" value="<%=(tipo == null) ? codigo : tipo.getCodigo()%>">
    </div>
</div>

<div class="form-group px-3">
    <label class="font-weight-bold">Nombre<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-bookmark"></span>
            </div>
        </div>
        <input type="text" class="form-control" id="nombre" name="nombre" value="<%=(tipo == null) ? "" : tipo.getNombre()%>">
    </div>
</div>

<div class="form-group px-3">
    <label class="font-weight-bold">Tipo activo<span class="text-danger">*</span></label>
    <div class="mt-2">
        <%if (tipo != null) {%>

        <%if (tipo.getActivo().equals("Tangible")) {%>
        <div class="custom-control custom-radio custom-control-inline">
            <input type="radio" id="tangible" name="activo" class="custom-control-input" checked value="Tangible">
            <label class="custom-control-label" for="tangible">Tangible</label>
        </div>
        <div class="custom-control custom-radio custom-control-inline">
            <input type="radio" id="intangible" name="activo" class="custom-control-input" value="Intangible">
            <label class="custom-control-label" for="intangible">Intangible</label>
        </div>
        <%} else {%>
        <div class="custom-control custom-radio custom-control-inline">
            <input type="radio" id="tangible" name="activo" class="custom-control-input" value="Tangible">
            <label class="custom-control-label" for="tangible">Tangible</label>
        </div>
        <div class="custom-control custom-radio custom-control-inline">
            <input type="radio" id="intangible" name="activo" class="custom-control-input" checked value="Intangible">
            <label class="custom-control-label" for="intangible">Intangible</label>
        </div>
        <%}%>
        <%} else {%>
        <div class="custom-control custom-radio custom-control-inline">
            <input type="radio" id="tangible" name="activo" class="custom-control-input" value="Tangible">
            <label class="custom-control-label" for="tangible">Tangible</label>
        </div>
        <div class="custom-control custom-radio custom-control-inline">
            <input type="radio" id="intangible" name="activo" class="custom-control-input" value="Intangible">
            <label class="custom-control-label" for="intangible">Intangible</label>
        </div>
        <%}%>
    </div>
</div>