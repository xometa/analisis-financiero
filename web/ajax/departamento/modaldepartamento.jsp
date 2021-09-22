<%-- 
    Document   : modaldepartamento
    Created on : ene 6, 2021, 8:08:42 p.m.
    Author     : Kevin
--%>

<%@page import="process.GeneraCodigos"%>
<%@page import="entities.Departamento"%>
<%@page import="models.ModeloDepartamento"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    ModeloDepartamento mdp = new ModeloDepartamento();
    GeneraCodigos cg = new GeneraCodigos();
    int valor = cg.autoincrementableDepartamento(mdp.listadoDepartamento());
    String codigo = cg.codigok("DP", valor);
    Departamento departamento;
    departamento = mdp.recuperaDepartamento(id, "", "dp.id=?");
%>

<%    if (departamento != null) {
%>
<input type="hidden" class="form-control" id="id" name="id" value="<%=departamento.getId()%>">
<%
    }
%>

<div class="form-group px-3" style="<%=(departamento == null) ? "display:block" : "display:none"%>">
    <label class="font-weight-bold">Código<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-copyright"></span>
            </div>
        </div>
        <input type="text" class="form-control" id="codigo" readonly="readonly" onkeypress="return false;" name="codigo" value="<%=(departamento == null) ? codigo : departamento.getCodigo()%>">
    </div>
</div>

<div class="form-group px-3">
    <label class="font-weight-bold">Nombre<span class="text-danger">*</span></label>
    <div class="input-group">
        <div class="input-group-prepend">
            <div class="input-group-text">
                <span class="uil-sitemap"></span>
            </div>
        </div>
        <input type="text" class="form-control" id="nombre" name="nombre" value="<%=(departamento == null) ? "" : departamento.getNombre()%>">
    </div>
</div>
