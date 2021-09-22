<%-- 
    Document   : listadoclientessucursal
    Created on : Jan 18, 2021, 4:18:40 AM
    Author     : jsaul
--%>

<%@page import="entities.Detallecartera"%>
<%@page import="entities.Cjuridico"%>
<%@page import="entities.Cnatural"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="models.ModeloCartera"%>
<%@page import="entities.Carterageneral"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    int id = Integer.parseInt(request.getParameter("id"));
    ModeloCartera mc = new ModeloCartera();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    Cnatural cn = null;
    Cjuridico cj = null;
    Carterageneral cg = mc.carteraGeneral(id, "cg.sucursal.id=?");
    if (cg == null) {
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

<%for (Detallecartera p : cg.getDetallecarteras()) {
        if (p.getPersona().getCjuridicos().size() > 0) {
            cj = mc.recuperaClienteJ(p.getPersona().getCjuridicos());
        } else if (p.getPersona().getCnaturals().size() > 0) {
            cn = mc.recuperaClienteN(p.getPersona().getCnaturals());
        } else {
            cn = null;
            cj = null;
        }
%>
<tr data-id="<%= p.getPersona().getId()%>">

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
    <td><%= p.getPersona().getDui()%></td>
    <td><%= p.getPersona().getNombre() + " " + p.getPersona().getApellido()%></td>
    <td><%= p.getPersona().getSexo()%></td>
    <td><%= sdf.format(p.getPersona().getFechanacimiento())%></td>
    <td><%= p.getPersona().getTelefono()%></td>
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
    <td>
        <span class="action-icon text-danger pointer" data-idpersona="<%= p.getPersona().getId()%>" 
              data-idsucursal="<%= cg.getSucursal().getId()%>" data-idcartera="<%= cg.getId()%>"> <i
                class="mdi mdi-delete mdi-24px" id="eliminar-cliente"></i></span>
    </td>
</tr>
<% cn = null;
        cj = null;
    }%>
<tr class="alert-success">
    <td class="font-weight-bold"  colspan="2">Asesor de la cartera:</td>
    <td colspan="2"><%= cg.getPersona().getNombre() + " " + cg.getPersona().getApellido()%></td>
    <td class="font-weight-bold">DUI:</td>
    <td><%= cg.getPersona().getDui()%> </td>
    <td class="font-weight-bold">Teléfono:</td>
    <td><%= cg.getPersona().getTelefono()%> </td>
    <td class="font-weight-bold">Dirección:</td>
    <td><%= cg.getPersona().getDireccion()%> </td>
</tr>
