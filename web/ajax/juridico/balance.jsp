<%-- 
    Document   : balance
    Created on : Jan 4, 2021, 10:10:52 AM
    Author     : jsaul
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="entities.Bgeneral"%>
<%@page import="entities.Cjuridico"%>
<%@page import="models.ModeloJuridico"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    int id = Integer.parseInt(request.getParameter("id"));
    ModeloJuridico mj = new ModeloJuridico();
    Cjuridico cj = mj.recuperaJuridico(id);
    DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
    simbolo.setDecimalSeparator('.');
    simbolo.setGroupingSeparator(',');
    DecimalFormat f = new DecimalFormat("###,##0.00", simbolo);
    
    if (cj.getBgenerals().size() == 0) {
        out.print("<tr>");
        out.print("<td colspan='5' class='text-center'>No hay datos</td>");
        out.print("</tr>");
        return;
    }
    
    for (Bgeneral bg : mj.listadoCuentas(cj.getBgenerals())) {
%>
<tr>
    <td><%= bg.getCuenta()%></td>
    <td><%= f.format(bg.getMonto())%></td>
    <td><%= bg.getClasificacion()%></td>
    <td><%= bg.getFecha()%></td>
    <td>
        <span class="action-icon text-warning pointer" data-idbalance="<%= bg.getId()%>" 
              data-cuenta="<%= bg.getCuenta()%>" data-monto="<%= bg.getMonto()%>" 
              data-clasificacion="<%= bg.getClasificacion()%>" data-anio="<%= bg.getFecha()%>"> <i
                class="mdi mdi-pencil mdi-24px" id="editar-balance"></i></span>
        <span class="action-icon text-danger pointer" data-idbalance="<%= bg.getId()%>"> <i
                class="mdi mdi-delete mdi-24px" id="eliminar-balance"></i></span>
    </td>
</tr>
<%
    }
%>