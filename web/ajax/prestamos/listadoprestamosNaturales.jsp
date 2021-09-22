<%-- 
    Document   : listadoprestamosNaturales
    Created on : 17-ene-2021, 12:13:12
    Author     : BONIFACIO
--%>

<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="models.ModeloPrestamo"%>
<%@page import="entities.Prestamo"%>
<%@page import="entities.Usuario"%>
<%@page import="models.ModeloUsuario"%>
<%@page import="java.util.ArrayList"%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ModeloPrestamo mp = new ModeloPrestamo();
    ArrayList<Prestamo> prestamos = (ArrayList<Prestamo>) mp.listadoPrestamosNatural();
    SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormatSymbols dfs = new DecimalFormatSymbols();
    dfs.setDecimalSeparator('.');
    dfs.setGroupingSeparator(',');
    DecimalFormat fd = new DecimalFormat("#,###.00",dfs);
    if (prestamos.size() <= 0) {
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

<%for (Prestamo pres : prestamos) {
    double cuota = (pres.getMonto()/pres.getNumcuotas());
%>
<tr>
    <td> <%= pres.getPersona().getNombre() + " " + pres.getPersona().getApellido() %> </td>
    <td><%= pres.getTipoprestamo().getTipo() %></td>
    <td><%= "$ "+fd.format(pres.getMonto()) %></td>
    <td> <%= "$ "+ fd.format(cuota) %> </td>
    <td><%= pres.getNumcuotas() +" Meses" %></td>
    <td><%= f.format(pres.getFecha()) %></td>
</tr>
<%}%>
