<%-- 
    Document   : modalsucursal
    Created on : ene 6, 2021, 8:28:58 p.m.
    Author     : Kevin
--%>

<%@page import="process.GeneraCodigos"%>
<%@page import="entities.Sucursal"%>
<%@page import="models.ModeloSucursal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    ModeloSucursal msu = new ModeloSucursal();
    GeneraCodigos cg = new GeneraCodigos();
    int valor = cg.autoincrementableSucursal(msu.listadoSucursal());
    String codigo = cg.codigok("SC", valor);
    Sucursal sucursal;
    sucursal = msu.recuperaSucursal(id, "", "su.id=?");
%>

<%    if (sucursal != null) {
%>
<input type="hidden" class="form-control" id="id" name="id" value="<%=sucursal.getId()%>">
<%
    }
%>

<div class="form-group px-3" style="<%=(sucursal == null) ? "display:block" : "display:none"%>">
    <label class="font-weight-bold">Código<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-copyright"></span>
            </div>
        </div>
        <input type="text" class="form-control" id="codigo" readonly="readonly" onkeypress="return false;" name="codigo" value="<%=(sucursal == null) ? codigo : sucursal.getCodigo()%>">
    </div>
</div>

<div class="form-group px-3">
    <label class="font-weight-bold">Nombre<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-map-pin-alt"></span>
            </div>
        </div>
        <input type="text" class="form-control" id="nombre" name="nombre" value="<%=(sucursal == null) ? "" : sucursal.getNombre()%>">
    </div>
</div>
