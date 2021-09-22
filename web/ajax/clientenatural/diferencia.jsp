

<%@page import="models.ModeloPersonaNatural"%>
<%@page import="entities.Disponibilidad"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    double total = 0, ingresos = 0, egresos = 0;
    DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
    simbolo.setDecimalSeparator('.');
    simbolo.setGroupingSeparator(',');
    DecimalFormat f = new DecimalFormat("###,##0.00", simbolo);
    ModeloPersonaNatural mpn = new ModeloPersonaNatural();
    ArrayList<Disponibilidad> listaIngresos = mpn.listaDisponibilidad(id, "Ingreso");
    ArrayList<Disponibilidad> listaEgresos = mpn.listaDisponibilidad(id, "Egreso");
%>
<div class="col-lg-6" style="padding-right: 0px">
    <table class="table table-centered mb-0">
        <thead class="font-weight-bold alert-info">
            <tr>
                <td>Ingreso</td>
                <td>Monto ($)</td>
            </tr>
        </thead>
        <tbody>
            <%
                if (listaIngresos.size() == 0) {
                    out.print("<tr>");
                    out.print("<td colspan='2' class='text-center'>No hay datos</td>");
                    out.print("</tr>");
                }

                for (Disponibilidad in : listaIngresos) {
            %>
            <tr>
                <td><%= in.getOperacion()%></td>
                <td>$ <%= f.format(in.getMonto())%></td>
            </tr>
            <%
                    ingresos += in.getMonto();
                }
            %>
        </tbody>
    </table>
</div>
<div class="col-lg-6" style="padding-left: 0px">
    <table class="table table-centered mb-0">
        <thead class="font-weight-bold alert-warning">
            <tr>
                <td>Egreso</td>
                <td>Monto ($)</td>
            </tr>
        </thead>
        <tbody>
            <%
                if (listaEgresos.size() == 0) {
                    out.print("<tr>");
                    out.print("<td colspan='2' class='text-center'>No hay datos</td>");
                    out.print("</tr>");
                }

                for (Disponibilidad eg : listaEgresos) {
            %>
            <tr>
                <td><%= eg.getOperacion()%></td>
                <td>$ <%= f.format(eg.getMonto())%></td>
            </tr>
            <%
                    egresos += eg.getMonto();
                }
                total = ingresos - egresos;
            %>
        </tbody>
    </table>
</div>
<div class="col-lg-6 font-weight-bold">
    <div class="row px-2 py-2">
        <div class="col-lg-6">
            Total ingresos
        </div>
        <div class="col-lg-6">
            $ <%= f.format(ingresos)%>
        </div>
    </div>
</div>
<div class="col-lg-6 font-weight-bold">
    <div class="row py-2">
        <div class="col-lg-6">
            Total egresos
        </div>
        <div class="col-lg-6">
            $ <%= f.format(egresos)%>
        </div>
    </div>
</div>
<div class="col-lg-12">
    <div class="<%=(total <= 0) ? "alert-danger" : "alert-success"%> px-2 py-2 h4">
        Diferencia: $ <%= f.format(total)%>
    </div>
</div>
