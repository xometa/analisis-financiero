<%-- 
    Document   : listadofiadores
    Created on : Dec 29, 2020, 9:35:19 AM
    Author     : jsaul
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="entities.Cjuridico"%>
<%@page import="models.ModeloJuridico"%>
<%@page import="entities.Cnatural"%>
<%@page import="java.util.ArrayList"%>
<%@page import="models.ModeloFiador"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    ModeloJuridico mj = new ModeloJuridico();
    ArrayList<Cjuridico> cj = (ArrayList<Cjuridico>) mj.listadoJuridico();
    if (cj.size() <= 0) {
%>
<tr>
    <td colspan="10" class="text-center">
        No hay registros
    </td>
</tr>
<%
        return;
    }
%>

<%for (Cjuridico c : cj) {
%>
<tr>
    <td><%= c.getCodigo()%></td>
    <td><%= c.getPersona().getDui()%></td>
    <td><%= c.getPersona().getNombre() + " " + c.getPersona().getApellido()%></td>
    <td><%= c.getPersona().getSexo()%></td>
    <td><%= sdf.format(c.getPersona().getFechanacimiento()) %></td>
    <td><%= c.getPersona().getTelefono()%></td>
    <td><%= c.getEmpresa()%></td>
    <td><%= c.getDireccion()%></td>
    <td><%= c.getTelefono()%></td>
    <td>
        <div class="btn-group">
            <button type="button" class="btn btn-info btn-rounded"><i
                    class="mdi mdi-post" id="balance" data-idjuridico="<%= c.getId()%>"></i></button>

            <button type="button" class="btn btn-primary btn-rounded"><i
                    class="mdi mdi-post-outline" id="estado" data-idjuridico="<%= c.getId()%>"></i></button>
        </div>
    </td>
    <td>
        <div class="btn-group">
            <button type="button" class="btn btn-warning btn-rounded"><i
                    class="mdi mdi-pencil" id="editar" data-idjuridico="<%= c.getId()%>"></i> </button>

            <button type="button" class="btn btn-danger btn-rounded"><i
                    class="mdi mdi-delete" id="eliminar" data-idjuridico="<%= c.getId()%>"></i> </button>
        </div>
    </td>
</tr>
<%}%>
