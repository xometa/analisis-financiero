<%-- 
    Document   : listadoclientesnaturales
    Created on : 02-ene-2021, 12:13:12
    Author     : BONIFACIO
--%>

<%@page import="entities.Persona"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="models.ModeloEmpleado"%>
<%@page import="java.util.ArrayList"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    ModeloEmpleado me = new ModeloEmpleado();
    ArrayList<Persona> pr = (ArrayList<Persona>) me.listadoEmpleados();
    if (pr.size() <= 0) {
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

<%for (Persona p : pr) {
%>
<tr>
    <td><%= p.getDui()%></td>
    <td><%= p.getNombre() + " " + p.getApellido()%></td>
    <td><%= p.getSexo()%></td>
    <td><%= sdf.format(p.getFechanacimiento())%></td>
    <td><%= p.getTelefono()%></td>
    <td><%= p.getDireccion()%></td>
    <td><%= p.getEstadocivil()%></td>
    <td>
        <%if (p.getUsuarios().size() > 0) {%>

        <div class="btn-group">
            <button type="button" class="btn btn-secondary btn-rounded" data-idempleado="<%= p.getId()%>"><i
                    class="mdi mdi-account-edit"></i> Modificar usuario</button>
            <button type="button" class="btn btn-info btn-rounded" data-idempleado="<%= p.getId()%>"><i class="mdi mdi-eye"></i>
                Ver
                usuario</button>
        </div>
        <%} else {%>
        <button type="button" class="btn btn-primary btn-rounded" data-idempleado="<%= p.getId()%>"><i
                class="mdi mdi-account-plus"></i>
            Agregar usuario</button>
            <%}%>
    </td>
    <td>
        <div class="btn-group">
            <button type="button" class="btn btn-warning btn-rounded"><i
                    class="mdi mdi-pencil" id="editar" data-idempleado="<%= p.getId()%>"></i> </button>

            <button type="button" class="btn btn-danger btn-rounded"><i
                    class="mdi mdi-delete" id="eliminar" data-idempleado="<%= p.getId()%>"></i> </button>
        </div>
    </td>
</tr>
<%}%>
