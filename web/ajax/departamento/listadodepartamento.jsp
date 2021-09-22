<%-- 
    Document   : listadodepartamento
    Created on : ene 6, 2021, 8:08:30 p.m.
    Author     : Kevin
--%>

<%@page import="models.ModeloDepartamento"%>
<%@page import="entities.Departamento"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ModeloDepartamento mdp = new ModeloDepartamento();
    ArrayList<Departamento> dp = (ArrayList<Departamento>) mdp.listadoDepartamento();
    if (dp.size() <= 0) {
%>
<div class="col-md-12">
    <div class="alert alert-info text-center font-weight-bold">
        <h5>No hay registros</h5>
    </div>
</div>
<%
        return;
    }
%>

<%for (Departamento d : dp) {
%>
<div class="col-md-3">
    <div class="card border border-radius">
        <div class="card-body">
            <div class="card-title px-2">
                <div class="row">
                    <h5 class="font-weigth-bold col-lg-12"><%= d.getCodigo() + " - " + d.getNombre()%></h5>
                </div>
            </div>
            <div class="btn-group mb-2 col-md-12">
                <button type="button" class="btn btn-warning btn-rounded"><span data-iddepartamento="<%= d.getId()%>" id="editar" class="h4 mdi mdi-pencil"></span></button>
                <button type="button" class="btn btn-danger btn-rounded"><span data-iddepartamento="<%= d.getId()%>" id="eliminar" class="h4 mdi mdi-delete"></span></button>
            </div><!-- end btn-group-->
        </div> <!-- end card-body-->
    </div> <!-- end card-->
</div> <!-- end col-->
<%}%>
