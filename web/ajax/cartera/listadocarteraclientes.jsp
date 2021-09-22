<%-- 
    Document   : listadocarteraclientes
    Created on : ene 6, 2021, 8:40:28 p.m.
    Author     : Kevin
--%>

<%@page import="entities.Cjuridico"%>
<%@page import="entities.Cnatural"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="entities.Persona"%>
<%@page import="models.ModeloCartera"%>
<%@page import="java.util.ArrayList"%>
<%@page import="entities.Carterageneral"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ModeloCartera mc = new ModeloCartera();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    Cnatural cn = null;
    Cjuridico cj = null;
    ArrayList<Persona> pc = (ArrayList<Persona>) mc.listadoClientes();
    if (pc.size() <= 0) {
%>
<tr>
    <td colspan="9" class="text-center">
        No hay registros
    </td>
</tr>
<%
        return;
    }
%>

<%for (Persona p : pc) {
        if (p.getCjuridicos().size() > 0) {
            cj = mc.recuperaClienteJ(p.getCjuridicos());
        } else if (p.getCnaturals().size() > 0) {
            cn = mc.recuperaClienteN(p.getCnaturals());
        } else {
            cn = null;
            cj = null;
        }
%>
<tr data-id="<%= p.getId()%>">

    <td>
        <div class="custom-control custom-checkbox custom-control-inline">
            <input type="checkbox" class="custom-control-input" id="<%= p.getId()%>">
            <label class="custom-control-label" for="<%= p.getId()%>"></label>
        </div>
    </td>
    <td>
        <%

            if (cj != null) {
                out.print(cj.getCodigo());
            } else if (cn != null) {
                out.print(cn.getCodigo());
            } else {
                out.print("No hay información");
            }

        %>
    </td>
    <td><%= p.getDui()%></td>
    <td><%= p.getNombre() + " " + p.getApellido()%></td>
    <td><%= p.getSexo()%></td>
    <td><%= sdf.format(p.getFechanacimiento())%></td>
    <td><%= p.getTelefono()%></td>
    <td>

        <%

            if (cj != null) {
                out.print(cj.getEmpresa());
            } else if (cn != null) {
                out.print("No hay información");
            } else {
                out.print("No hay información");
            }

        %>
    </td>
    <td>

        <%            if (cj != null) {
                out.print(cj.getDireccion());
            } else if (cn != null) {
                out.print("No hay información");
            } else {
                out.print("No hay información");
            }

        %>
    </td>
    <%        if (cj != null) {
            out.print("<td class='bg-info text-white text-center'>Cliente jurídico</td>");
        } else if (cn != null) {
            out.print("<td class='bg-primary text-white text-center'>Cliente natural</td>");
        } else {
            out.print("<td class='bg-danger text-white text-center'>No hay información</td>");
        }

    %>

</tr>
<% cn = null;
        cj = null;
    }%>