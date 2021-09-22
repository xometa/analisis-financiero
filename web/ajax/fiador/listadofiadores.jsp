<%-- 
    Document   : listadofiadores
    Created on : Dec 29, 2020, 9:35:19 AM
    Author     : jsaul
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="entities.Cnatural"%>
<%@page import="java.util.ArrayList"%>
<%@page import="models.ModeloFiador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    ModeloFiador mf = new ModeloFiador();
    ArrayList<Cnatural> cn = (ArrayList<Cnatural>) mf.listadoFiadores();
    if (cn.size() <= 0) {
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

<%for (Cnatural c : cn) {
%>
<tr>
    <td><%= c.getCodigo()%></td>
    <td><%= c.getPersona().getDui()%></td>
    <td><%= c.getPersona().getNombre() + " " + c.getPersona().getApellido()%></td>
    <td><%= c.getPersona().getSexo()%></td>
    <td><%= sdf.format(c.getPersona().getFechanacimiento())%></td>
    <td><%= c.getPersona().getTelefono()%></td>
    <td><%= c.getPersona().getDireccion()%></td>
    <td><%= c.getPersona().getEstadocivil()%></td>
    <td><%= c.getLugartrabajo()%></td>
    <td>
        <div class="btn-group">
            <button type="button" class="btn btn-info btn-rounded" data-idfiador="<%= c.getId()%>"><i
                    class="mdi mdi-timeline-plus" id="ing"></i> </button>
            <button type="button" class="btn btn-primary btn-rounded" data-idfiador="<%= c.getId()%>"><i
                    class="mdi mdi-timeline-text" id="egr"></i></button>
            <button type="button" class="btn btn-secondary btn-rounded" data-idfiador="<%= c.getId()%>"><i
                    class="mdi mdi-eye" id="ver"></i></button>
        </div>
    </td>
    <td>
        <div class="btn-group">
            <button type="button" class="btn btn-warning btn-rounded"><i
                    class="mdi mdi-pencil" id="editar" data-idfiador="<%= c.getId()%>"></i> </button>

            <button type="button" class="btn btn-danger btn-rounded"><i
                    class="mdi mdi-delete" id="eliminar" data-idfiador="<%= c.getId()%>"></i> </button>
        </div>
    </td>
</tr>
<%}%>
