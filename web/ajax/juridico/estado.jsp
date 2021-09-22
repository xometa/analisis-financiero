<%-- 
    Document   : estado
    Created on : Jan 4, 2021, 10:10:59 AM
    Author     : jsaul
--%>
<%@page import="entities.Eresultado"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.DecimalFormatSymbols"%>
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
    if (cj.getEresultados().size() == 0) {
        out.print("<tr>");
        out.print("<td colspan='4' class='text-center'>No hay datos</td>");
        out.print("</tr>");
        return;
    }

    for (Eresultado er : mj.listadoCuentasER(cj.getEresultados())) {
%>
<tr>
    <td><%= er.getCuenta()%></td>
    <td><%= f.format(er.getMonto())%></td>
    <td><%= er.getFecha()%></td>
    <td>
        <span class="action-icon text-warning pointer" data-idestado="<%= er.getId()%>" 
              data-cuenta="<%= er.getCuenta()%>" data-monto="<%= er.getMonto()%>" data-anio="<%= er.getFecha()%>"> <i
                class="mdi mdi-pencil mdi-24px" id="editar-estado"></i></span>
        <span class="action-icon text-danger pointer" data-idestado="<%= er.getId()%>"> <i
                class="mdi mdi-delete mdi-24px" id="eliminar-estado"></i></span>
    </td>
</tr>
<%
    }
%>