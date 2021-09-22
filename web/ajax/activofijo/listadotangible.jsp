<%-- 
    Document   : listadotangible
    Created on : Jan 14, 2021, 3:53:26 PM
    Author     : jsaul
--%>

<%@page import="entities.Activofijo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entities.Activobaja"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="models.ModeloActivoFijo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String cadena = "", codigo = "";
    String opcion = request.getParameter("tipo");
    ModeloActivoFijo maf = new ModeloActivoFijo();
    DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
    simbolo.setDecimalSeparator('.');
    simbolo.setGroupingSeparator(',');
    DecimalFormat f = new DecimalFormat("###,##0.00", simbolo);
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    ArrayList<Activofijo> af = (ArrayList<Activofijo>) maf.listadoActivoTangible(opcion);
    if (af.size() <= 0) {
%>
<tr>
    <td colspan="11" class="text-center">
        No hay registros
    </td>
</tr>
<%
        return;
    }
%>

<%for (Activofijo a : af) {
%>
<tr data-id="<%= a.getId()%>" class="pointer">
    <td>
        <%
            cadena = String.valueOf(a.getId());
            codigo = a.getSucursal().getCodigo() + "-" + a.getDepartamento().getCodigo() + "-" + a.getTipoactivo().getCodigo();
            if (cadena.length() == 1) {
                cadena = "-000" + cadena;
                codigo += cadena;
            } else if (cadena.length() == 2) {
                cadena = "-00" + cadena;
                codigo += cadena;
            } else if (cadena.length() == 3) {
                cadena = "-0" + cadena;
                codigo += cadena;
            } else {
                cadena = "-" + cadena;
                codigo += cadena;
            }
            out.print(codigo);
        %>
    </td>
    <td><%= a.getNombre()%></td>
    <td><%= a.getProcedencia()%></td>
    <td><%= "$ " + f.format(a.getPrecio())%></td>
    <td><%= sdf.format(a.getFechaadquisicion())%></td>
    <td><%= a.getVidautil()%></td>
    <td><%= a.getSucursal().getNombre()%></td>
    <td><%= a.getDepartamento().getNombre()%></td>
    <td><%= a.getTipoactivo().getNombre()%></td>
    <%if (a.getUso() == 1) {%>
    <td class="bg-success"></td>
    <%
    } else {
    %>
    <td class="bg-danger"></td>
    <%
        }
    %>
    <%if (a.getActivobajas().size() > 0) {
    %>
    <td class="bg-danger"></td>
    <%} else {%>
    <td class="bg-info"></td>
    <%}%>
</tr>
<%}%>
