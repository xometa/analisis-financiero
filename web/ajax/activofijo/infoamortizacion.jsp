<%-- 
    Document   : infoamortización
    Created on : Jan 14, 2021, 3:49:49 PM
    Author     : jsaul
--%>

<%@page import="java.math.RoundingMode"%>
<%@page import="process.Acumulador"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="process.Operaciones"%>
<%@page import="models.ModeloActivoFijo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    String cadena = "", codigo = "";
    DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
    simbolo.setDecimalSeparator('.');
    simbolo.setGroupingSeparator(',');
    DecimalFormat f = new DecimalFormat("###,##0.00", simbolo);
    ModeloActivoFijo ma = new ModeloActivoFijo();
    Operaciones dp = ma.obtenerAmortizacion(id, "Anual");
    if (dp != null) {
        cadena = String.valueOf(dp.getActivofijo().getId());
        codigo = dp.getActivofijo().getSucursal().getCodigo() + "-" + dp.getActivofijo().getDepartamento().getCodigo() + "-" + dp.getActivofijo().getTipoactivo().getCodigo();
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
%>
<div class="col-md-4">
    <!-- project card -->
    <div class="card d-block">
        <div class="card-body">
            <div class="dropdown card-widgets">
                <a href="#" class="dropdown-toggle arrow-none" data-toggle="dropdown" aria-expanded="false">
                    <i class="dripicons-dots-3"></i>
                </a>
                <div class="dropdown-menu dropdown-menu-right">
                    <!-- item-->
                    <a href="PDFA?idactivofijo=<%= dp.getActivofijo().getId()%>&opcion=Diaria" target="blank" class="dropdown-item"><i class="mdi mdi mdi-file-pdf-box mr-1 h4"></i>Imprimir</a>
                </div>
            </div>
            <!-- project title-->
            <h4 class="mt-0">
                <span class="text-title">Amortización por día</span>
            </h4>
            <%if (dp.isProceso()) {%>
            <div class="badge badge-success mb-3">En proceso</div>
            <%} else {%>
            <div class="badge badge-danger mb-3">Finalizada</div>
            <%}%>

            <p class="text-muted font-13 mb-3 text-justify">
                <b><%= codigo%></b> <%= dp.getActivofijo().getNombre()%>
            </p>

            <!-- project detail-->
            <p class="mb-1">
                <span class="pr-2 text-nowrap mb-2 d-inline-block">
                    <b><i class="mdi mdi-format-list-bulleted-type"></i> Días transcurridos:</b> <%= dp.getDias()%>
                </span>
            </p>
            <p class="mb-1">
                <span class="pr-2 text-nowrap mb-2 d-inline-block">
                    <b><i class="uil-dollar-sign"></i> Monto diario:</b> $ <%= f.format(dp.getMontoDias().floatValue())%>
                </span>
            </p>
            <p class="mb-1">
                <span class="pr-2 text-nowrap mb-2 d-inline-block">
                    <b><i class="uil-dollar-sign"></i> Amortización actual:</b> $ <%= f.format(dp.getAcumuladoDias().floatValue())%>
                </span>
            </p>
        </div> <!-- end card-body-->
        <ul class="list-group list-group-flush">
            <li class="list-group-item p-3">
                <!-- project progress-->
                <p class="mb-2 font-weight-bold">Progreso <span class="float-right"><%= f.format(dp.getProgresoDias().floatValue())%>%</span></p>
                <div class="progress progress-sm">
                    <div class="progress-bar" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: <%= f.format(dp.getProgresoDias().floatValue())%>%;">
                    </div><!-- /.progress-bar -->
                </div><!-- /.progress -->
            </li>
        </ul>
    </div> <!-- end card-->
</div>

<div class="col-md-4">
    <!-- project card -->
    <div class="card d-block">
        <div class="card-body">
            <div class="dropdown card-widgets">
                <a href="#" class="dropdown-toggle arrow-none" data-toggle="dropdown" aria-expanded="false">
                    <i class="dripicons-dots-3"></i>
                </a>
                <div class="dropdown-menu dropdown-menu-right">
                    <!-- item-->
                    <a href="PDFA?idactivofijo=<%= dp.getActivofijo().getId()%>&opcion=Mensual" target="blank" class="dropdown-item"><i class="mdi mdi mdi-file-pdf-box mr-1 h4"></i>Imprimir</a>
                </div>
            </div>
            <!-- project title-->
            <h4 class="mt-0">
                <span class="text-title">Amortización por mes</span>
            </h4>
            <%if (dp.isProceso()) {%>
            <div class="badge badge-success mb-3">En proceso</div>
            <%} else {%>
            <div class="badge badge-danger mb-3">Finalizada</div>
            <%}%>

            <p class="text-muted font-13 mb-3 text-justify">
                <b><%= codigo%></b> <%= dp.getActivofijo().getNombre()%>
            </p>

            <!-- project detail-->
            <p class="mb-1">
                <span class="pr-2 text-nowrap mb-2 d-inline-block">
                    <b><i class="mdi mdi-format-list-bulleted-type"></i> Meses transcurridos:</b> <%= dp.getMeses()%>
                </span>
            </p>
            <p class="mb-1">
                <span class="pr-2 text-nowrap mb-2 d-inline-block">
                    <b><i class="uil-dollar-sign"></i> Monto mensual:</b> $ <%= f.format(dp.getMontoMeses().floatValue())%>
                </span>
            </p>
            <p class="mb-1">
                <span class="pr-2 text-nowrap mb-2 d-inline-block">
                    <b><i class="uil-dollar-sign"></i> Amortización actual:</b> $ <%= f.format(dp.getAcumuladoMeses().floatValue())%>
                </span>
            </p>
        </div> <!-- end card-body-->
        <ul class="list-group list-group-flush">
            <li class="list-group-item p-3">
                <!-- project progress-->
                <p class="mb-2 font-weight-bold">Progreso <span class="float-right"><%= f.format(dp.getProgresoMeses().floatValue())%>%</span></p>
                <div class="progress progress-sm">
                    <div class="progress-bar" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: <%= f.format(dp.getProgresoMeses().floatValue())%>%;">
                    </div><!-- /.progress-bar -->
                </div><!-- /.progress -->
            </li>
        </ul>
    </div> <!-- end card-->
</div>

<div class="col-md-4">
    <!-- project card -->
    <div class="card d-block">
        <div class="card-body">
            <div class="dropdown card-widgets">
                <a href="#" class="dropdown-toggle arrow-none" data-toggle="dropdown" aria-expanded="false">
                    <i class="dripicons-dots-3"></i>
                </a>
                <div class="dropdown-menu dropdown-menu-right">
                    <!-- item-->
                    <a href="PDFA?idactivofijo=<%= dp.getActivofijo().getId()%>&opcion=Anual" target="blank" class="dropdown-item"><i class="mdi mdi mdi-file-pdf-box mr-1 h4"></i>Imprimir</a>
                </div>
            </div>
            <!-- project title-->
            <h4 class="mt-0">
                <span class="text-title">Amortización por año</span>
            </h4>
            <%if (dp.isProceso()) {%>
            <div class="badge badge-success mb-3">En proceso</div>
            <%} else {%>
            <div class="badge badge-danger mb-3">Finalizada</div>
            <%}%>

            <p class="text-muted font-13 mb-3 text-justify">
                <b><%= codigo%></b> <%= dp.getActivofijo().getNombre()%>
            </p>

            <!-- project detail-->
            <p class="mb-1">
                <span class="pr-2 text-nowrap mb-2 d-inline-block">
                    <b><i class="mdi mdi-format-list-bulleted-type"></i> Años transcurridos:</b> <%= dp.getYear()%>
                </span>
            </p>
            <p class="mb-1">
                <span class="pr-2 text-nowrap mb-2 d-inline-block">
                    <b><i class="uil-dollar-sign"></i> Monto anual:</b> $ <%= f.format(dp.getMontoYears().floatValue())%>
                </span>
            </p>
            <p class="mb-1">
                <span class="pr-2 text-nowrap mb-2 d-inline-block">
                    <b><i class="uil-dollar-sign"></i> Amortización actual:</b> $ <%= f.format(dp.getAcumuladoYears().floatValue())%>
                </span>
            </p>
        </div> <!-- end card-body-->
        <ul class="list-group list-group-flush">
            <li class="list-group-item p-3">
                <!-- project progress-->
                <p class="mb-2 font-weight-bold">Progreso <span class="float-right"><%= f.format(dp.getProgresoYear().floatValue())%>%</span></p>
                <div class="progress progress-sm">
                    <div class="progress-bar" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: <%= f.format(dp.getProgresoYear().floatValue())%>%;">
                    </div><!-- /.progress-bar -->
                </div><!-- /.progress -->
            </li>
        </ul>
    </div> <!-- end card-->
</div>

<div class="col-lg-12">
    <div class="card">
        <div class="card-body">
            <h4 class="header-title mb-3">Amortización anual</h4>

            <div class="table-responsive">
                <table class="table mb-0">
                    <thead class="alert-success">
                        <tr>
                            <th>Año</th>
                            <th>Amortización anual</th>
                            <th>Amortización acumulada</th>
                            <th>Valores en libros</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%for (Acumulador a : dp.getAcumulador()) {
                        %>
                        <tr>
                            <td><%= a.getAnio()%></td>
                            <td>$ <%= f.format(a.getdAnual().floatValue())%></td>
                            <td>$ <%= f.format(a.getdAcumulada().floatValue())%></td>
                            <td>$ <%= f.format(a.getvLibros().floatValue())%></td>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
            </div>
            <!-- end table-responsive -->
            <div class="row pt-2 float-right">
                <a href="PDFA?idactivofijo=<%= dp.getActivofijo().getId()%>&opcion=Anual" class="btn btn-primary btn-rounded" target="blank">Imprimir</a>
            </div>
        </div>
    </div>

</div>

<%} else {%>
<div class="col-lg-12">
    <div class="alert-danger py-3 text-center rounded">
        La información del <strong>servidor</strong> ha sido alterada.
    </div>
</div>
<%}%>