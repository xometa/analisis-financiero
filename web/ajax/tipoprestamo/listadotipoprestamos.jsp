<%-- 
    Document   : listadotipoprestamos
    Created on : Jan 6, 2021, 1:14:43 PM
    Author     : jsaul
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="entities.Tipoprestamo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="models.ModeloTipoPrestamo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    int i = 1;
    DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
    simbolo.setDecimalSeparator('.');
    simbolo.setGroupingSeparator(',');
    DecimalFormat f = new DecimalFormat("###,##0.00", simbolo);
    ModeloTipoPrestamo mtp = new ModeloTipoPrestamo();
    ArrayList<Tipoprestamo> tp = (ArrayList<Tipoprestamo>) mtp.listadoTipoPrestamos();
    if (tp.size() <= 0) {
%>
<div class="col-md-12">
    <div class="alert alert-info text-center font-weight-bold">
        <h5>No hay registros</h5>
    </div>
</div>
<%
        return;
    }
    for (Tipoprestamo t : tp) {
%>
<div class="col-md-3">
    <div class="card border border-radius">
        <div class="card-body">
            <div class="card-title px-2">
                <div class="row">
                    <h5 class="font-weigth-bold col-lg-7"><%= i + " - " + t.getTipo()%></h5>
                    <div class="col-lg-5">
                        <label class="font-weight-bold h4"><%= f.format(t.getPorcentaje())%> %</label>
                    </div>
                </div>
            </div>
            <div class="btn-group mb-2 col-md-12">
                <button type="button" class="btn btn-warning btn-rounded"><span data-id="<%= t.getId()%>" id="editar" class="h4 mdi mdi-pencil"></span></button>
                <button type="button" class="btn btn-danger btn-rounded"><span data-id="<%= t.getId()%>" id="eliminar" class="h4 mdi mdi-delete"></span></button>
            </div><!-- end btn-group-->
        </div> <!-- end card-body-->
    </div> <!-- end card-->
</div> <!-- end col-->
<% i++;
    }%>