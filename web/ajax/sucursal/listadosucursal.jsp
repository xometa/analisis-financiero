<%-- 
    Document   : listadosucursal
    Created on : ene 6, 2021, 8:28:24 p.m.
    Author     : Kevin
--%>

<%@page import="models.ModeloSucursal"%>
<%@page import="entities.Sucursal"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ModeloSucursal msu = new ModeloSucursal();
    ArrayList<Sucursal> su = (ArrayList<Sucursal>) msu.listadoSucursal();
    if (su.size() <= 0) {
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

<%for (Sucursal s : su) {
%>
<div class="col-md-3">
    <div class="card border border-radius">
        <div class="card-body">
            <div class="card-title px-2">
                <div class="row">
                    <h5 class="font-weigth-bold col-lg-10"><%= s.getCodigo() + " - " + s.getNombre()%></h5>

                    <%if (s.getCarteragenerals().size() > 0 || s.getActivofijos().size() > 0) {%>
                    <div class="dropdown float-right col-lg-2">
                        <a href="javascript:void(0);" class="dropdown-toggle text-muted arrow-none" data-toggle="dropdown" aria-expanded="false">
                            <i class="mdi mdi-dots-vertical font-18"></i>
                        </a>
                        <div class="dropdown-menu dropdown-menu-right" style="">
                            <!-- item-->
                            <%if (s.getActivofijos().size() > 0) {%>
                            <a href="PDFSAF?id=<%= s.getId()%>" target="blank" class="dropdown-item"><i class="mdi mdi mdi-file-pdf-box mr-1"></i>Imprimir listado activos fijos</a>
                            <%}%>
                            <!-- item-->
                            <%if (s.getCarteragenerals().size() > 0) {%>
                            <a href="PDFSCNJ?id=<%= s.getId()%>" target="blank" class="dropdown-item"><i class="mdi mdi mdi-file-pdf-box mr-1"></i>Imprimir listado clientes</a>
                            <%}%>
                        </div>
                    </div>
                    <%}%>
                </div>
            </div>
            <div class="btn-group mb-2 col-md-12">
                <%if (s.getCarteragenerals().size() > 0) {%>
                <button type="button" class="btn btn-info btn-rounded"><span data-idsucursal="<%= s.getId()%>" id="ver" class="h4 mdi mdi-eye"></span></button>
                    <%}%>
                <button type="button" class="btn btn-warning btn-rounded"><span data-idsucursal="<%= s.getId()%>" id="editar" class="h4 mdi mdi-pencil"></span></button>
                <button type="button" class="btn btn-danger btn-rounded"><span data-idsucursal="<%= s.getId()%>" id="eliminar" class="h4 mdi mdi-delete"></span></button>
            </div><!-- end btn-group-->
        </div> <!-- end card-body-->
    </div> <!-- end card-->
</div> <!-- end col-->
<%}%>
