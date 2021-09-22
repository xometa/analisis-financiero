<%-- 
    Document   : egresos
    Created on : Jan 4, 2021, 2:13:10 PM
    Author     : jsaul
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="entities.Cnatural"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="entities.Disponibilidad"%>
<%@page import="models.ModeloFiador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
    simbolo.setDecimalSeparator('.');
    simbolo.setGroupingSeparator(',');
    DecimalFormat f = new DecimalFormat("###,##0.00", simbolo);
    ModeloFiador mf = new ModeloFiador();
    ArrayList<Disponibilidad> listaEgresos = mf.listaDisponibilidad(id, "Egreso");
    if (listaEgresos.size() == 0) {
        out.print("<tr>");
        out.print("<td colspan='3' class='text-center'>No hay datos</td>");
        out.print("</tr>");
        return;
    }
    
    for (Disponibilidad item : listaEgresos) {
%>
<tr>
    <td><%= item.getOperacion()%></td>
    <td>$ <%= f.format(item.getMonto())%></td>
    <td>
        <span class="action-icon text-warning pointer" data-id="<%= item.getId()%>" 
              data-descripcion="<%= item.getOperacion()%>" data-monto="<%= item.getMonto()%>" 
              data-tipo="<%= item.getTipo()%>"> <i
                class="mdi mdi-pencil mdi-24px" id="editar-egreso"></i></span>
        <span class="action-icon text-danger pointer" data-id="<%= item.getId()%>"> <i
                class="mdi mdi-delete mdi-24px" id="eliminar-egreso"></i></span>
    </td>
</tr>
<%
    }
%>
