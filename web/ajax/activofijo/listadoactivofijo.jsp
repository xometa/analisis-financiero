<%-- 
    Document   : listadoactivofijo
    Created on : Jan 1, 2021, 3:10:47 PM
    Author     : jsaul
--%>


<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="entities.Activobaja"%>
<%@page import="entities.Activofijo"%>
<%@page import="models.ModeloActivoFijo"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String cadena = "", codigo = "";
    ModeloActivoFijo maf = new ModeloActivoFijo();
    DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
    simbolo.setDecimalSeparator('.');
    simbolo.setGroupingSeparator(',');
    DecimalFormat f = new DecimalFormat("###,##0.00", simbolo);
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    Activobaja ab;
    ArrayList<Activofijo> af = (ArrayList<Activofijo>) maf.listadoActivo();
    if (af.size() <= 0) {
%>
<tr>
    <td colspan="12" class="text-center">
        No hay registros
    </td>
</tr>
<%        
        return;
    }
%>

<%for (Activofijo a : af) {
%>
<tr>
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
    <td><%= a.getDescripcion()%></td>
    <td><%= a.getProcedencia()%></td>
    <td><%= "$ " +f.format(a.getPrecio()) %></td>
    <td><%= sdf.format(a.getFechaadquisicion()) %></td>
    <td><%= a.getVidautil()%></td>
    <td><%= a.getSucursal().getNombre()%></td>
    <td><%= a.getDepartamento().getNombre()%></td>
    <td><%= a.getTipoactivo().getNombre()%></td>
    <td>
        <%if (a.getActivobajas().size() > 0) {
        %>
        <span class="badge badge-info badge-pill">Dado de baja</span>
        <%    
        } else {
        %>
        <%if (a.getUso() == 1) {%>
        <input type="checkbox" id="<%= a.getId()%>" checked data-switch="success" data-idactivo="<%= a.getId()%>">
        <label for="<%= a.getId()%>" data-on-label="Si" data-off-label="No" class="mb-0 d-block"></label>
        <%
        } else {
        %>
        <input type="checkbox" id="<%= a.getId()%>" data-switch="success" data-idactivo="<%= a.getId()%>">
        <label for="<%= a.getId()%>" data-on-label="Si" data-off-label="No" class="mb-0 d-block"></label>
        <%
                }
            }%>
    </td>
    <td>
        <div class="btn-group">
            <%if (a.getActivobajas().size() > 0) {
                    ab = maf.recuperaActivobaja(a.getId());
            %>
            <button type="button" class="btn btn-info btn-rounded"><i
                    class="mdi mdi-eye" id="verbaja" data-idbaja="<%= ab.getId()%>" 
                    data-motivo="<%= ab.getMotivo()%>" data-fecha="<%= sdf.format(ab.getFecha())%>" 
                    data-nombreactivo="<%= a.getNombre()%>"></i></button>
                <%} else {%>
            <button type="button" class="btn btn-primary btn-rounded"><i
                    class="mdi mdi-shield-check" id="darbaja" data-idactivo="<%= a.getId()%>" 
                    data-nombreactivo="<%= a.getNombre()%>"></i></button>
                <%}%>
        </div>
    </td>
    <td>
        <div class="btn-group">
            <button type="button" class="btn btn-warning btn-rounded"><i
                    class="<%= (a.getActivobajas().size() > 0) ? "mdi mdi-pen-lock" : "mdi mdi-pencil"%>" id="editar" data-idactivo="<%= a.getId()%>"></i> </button>

            <button type="button" class="btn btn-danger btn-rounded"><i
                    class="mdi mdi-delete" id="eliminar" data-idactivo="<%= a.getId()%>"></i> </button>
        </div>
    </td>
</tr>
<%}%>
