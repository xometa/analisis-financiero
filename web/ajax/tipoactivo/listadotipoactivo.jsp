<%-- 
    Document   : listadotipoactivo
    Created on : ene 6, 2021, 8:18:10 p.m.
    Author     : Kevin
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="entities.Tipoactivo"%>
<%@page import="models.ModeloTipoActivo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ModeloTipoActivo mta = new ModeloTipoActivo();
    ArrayList<Tipoactivo> ta = (ArrayList<Tipoactivo>) mta.listadoTipo();
    if (ta.size() <= 0) {
%>
<tr>
    <td colspan="4" class="text-center">
        No hay registros
    </td>
</tr>
<%
        return;
    }
%>

<%for (Tipoactivo t : ta) {
%>
<tr>
    <td><%= t.getCodigo()%></td>
    <td><%= t.getNombre()%></td>
    <td><%= t.getActivo()%></td>
    <td>
        <div class="btn-group">
            <button type="button" class="btn btn-warning btn-rounded"><i
                    class="mdi mdi-pencil" id="editar" data-idtipo="<%= t.getId()%>"></i> </button>

            <button type="button" class="btn btn-danger btn-rounded"><i
                    class="mdi mdi-delete" id="eliminar" data-idtipo="<%= t.getId()%>"></i> </button>
        </div>
    </td>
</tr>
<%}%>
