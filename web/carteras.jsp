<%-- 
    Document   : carteras
    Created on : Jan 17, 2021, 1:59:15 PM
    Author     : jsaul
--%>

<%@page import="entities.Persona"%>
<%@page import="entities.Sucursal"%>
<%@page import="models.ModeloCartera"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    response.setHeader("Pragma", "no-cache");
    response.addHeader("Cache-control", "must-revalidate");
    response.addHeader("Cache-control", "no-cache");
    response.addHeader("Cache-control", "no-store");
    response.setDateHeader("Expires", 0);
    try {
        if (session.getAttribute("idusuario") == null) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    } catch (Exception e) {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
%>
<!DOCTYPE html>
<html lang="es">

    <head>
        <title>Carteras</title>
        <%@include file="head.jsp" %>
    </head>
    <body class="loading"
          data-layout-config='{"leftSideBarTheme":"dark","layoutBoxed":false, "leftSidebarCondensed":false, "leftSidebarScrollable":false,"darkMode":false, "showRightSidebarOnStart": true}'>
        <!-- Begin page -->
        <div class="wrapper">
            <!-- ========== Left Sidebar Start ========== -->
            <%@include file="sidebar.jsp" %>
            <!-- Left Sidebar End -->

            <!-- ============================================================== -->
            <!-- Start Page Content here -->
            <!-- ============================================================== -->

            <div class="content-page">
                <div class="content">
                    <!-- Topbar Start -->
                    <%@include file="navbar.jsp" %>
                    <!-- end Topbar -->

                    <!-- Start Content-->
                    <div class="container-fluid">
                        <!--Aquí va el contenido-->
                        <!-- start page title -->
                        <div class="row">
                            <div class="col-12">
                                <div class="page-title-box">
                                    <div class="page-title-right">
                                        <ol class="breadcrumb m-0">
                                            <li class="breadcrumb-item"><a href="javascript: void(0);">Análisis Financiero</a></li>
                                            <li class="breadcrumb-item"><a href="javascript: void(0);">Página</a></li>
                                            <li class="breadcrumb-item active">Carteras</li>
                                        </ol>
                                    </div>
                                    <h4 class="page-title">Carteras de clientes</h4>
                                </div>
                            </div>
                        </div>
                        <!-- end page title -->

                        <div class="row">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <div class="form-group text-right">
                                                    <div class="btn btn-group" id="addnew">
                                                        <a href="clientenatural.jsp" class="btn btn-primary btn-rounded"><span
                                                                class="uil-user-square"></span> Registrar Cliente Natural</a>
                                                        <a href="fiador.jsp" class="btn btn-info btn-rounded"><span
                                                                class="uil-user-square"></span> Registrar Fiador</a>
                                                        <a href="clientejuridico.jsp" class="btn btn-danger btn-rounded"><span class="uil-building"></span> Registrar
                                                            Cliente Jurídico</a>
                                                    </div>
                                                </div>
                                            </div>                    
                                            <form id="formCartera" name="formCartera" class="col-lg-12" method="POST" autocomplete="off">
                                                <div class="row">
                                                    <div class="col-lg-12 pb-2">
                                                        <div class="border border-secondary-100 rounded">
                                                            <div class="modal-colored-header bg-dark text-center py-3">
                                                                <h4 class="modal-title">Datos de la cartera</h4>
                                                            </div>
                                                            <div class="modal-body">
                                                                <div class="row">
                                                                    <div class="col-lg-6">
                                                                        <div class="form-group px-2">
                                                                            <label class="font-weight-bold">Sucursal<span
                                                                                    class="text-danger">*</span></label>
                                                                            <div class="input-group">
                                                                                <div class="input-group-prepend">
                                                                                    <div class="input-group-text">
                                                                                        <span class="uil-map-pin-alt"></span>
                                                                                    </div>
                                                                                </div>
                                                                                <%
                                                                                    ModeloCartera mc = new ModeloCartera();
                                                                                %>
                                                                                <select class="custom-select form-control" required id="idsucursal">
                                                                                    <option value=" ">--- Seleccionar sucursal ---</option>
                                                                                    <%
                                                                                        for (Sucursal s : mc.listadoSucursal()) {
                                                                                    %>
                                                                                    <option value="<%= s.getId()%>"><%= s.getNombre()%></option>
                                                                                    <%
                                                                                        }
                                                                                    %>
                                                                                </select>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-lg-6">
                                                                        <div class="form-group px-2">
                                                                            <label class="font-weight-bold">Asesor<span class="text-danger">*</span></label>
                                                                            <div class="input-group">
                                                                                <div class="input-group-prepend">
                                                                                    <div class="input-group-text">
                                                                                        <span class="uil-users-alt"></span>
                                                                                    </div>
                                                                                </div>
                                                                                <select class="custom-select form-control" required id="idasesor">
                                                                                    <option value=" ">--- Seleccionar asesor ---</option>
                                                                                    <%
                                                                                        for (Persona p : mc.listadoPersonaAsesor()) {
                                                                                    %>
                                                                                    <option value="<%= p.getId()%>"><%= p.getNombre() + " " + p.getApellido()%></option>
                                                                                    <%
                                                                                        }
                                                                                    %>
                                                                                </select>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div><!-- end modal-body -->
                                                        </div>
                                                    </div>
                                                    <div class="col-lg-12">
                                                        <div class="border border-secondary-100 rounded py-2 px-3">
                                                            <div class="row pt-2">
                                                                <div class="col-lg-12 pb-2">
                                                                    <h4 class="text-lg-left text-muted font-weight-bold">Clientes (Naturales y Jurídicos)</h4>
                                                                </div>
                                                            </div>
                                                            <div class="row pb-4">
                                                                <div class="col-lg-12">
                                                                    <div class="row">
                                                                        <div class="col-lg-12">
                                                                            <div class="table-responsive">
                                                                                <table class="table table-borderless table-centered mb-0" id="basic-datatable">
                                                                                    <thead class="thead-light">
                                                                                        <tr>
                                                                                            <th></th>
                                                                                            <th>Código</th>
                                                                                            <th>DUI</th>
                                                                                            <th>Nombre</th>
                                                                                            <th>Sexo</th>
                                                                                            <th>Fecha nacimiento</th>
                                                                                            <th>Teléfono</th>
                                                                                            <th>Empresa</th>
                                                                                            <th>Dirección</th>
                                                                                            <th>Tipo</th>
                                                                                        </tr>
                                                                                    </thead>
                                                                                    <tbody id="data-body">
                                                                                    </tbody>
                                                                                </table>
                                                                            </div> <!-- end table-responsive-->
                                                                        </div>
                                                                        <!-- end col -->

                                                                    </div> <!-- end row -->
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-lg-12 py-3">
                                                        <div class="new-footer d-flex">
                                                            <button type="button" class="btn btn-light font-weight-bold btn-rounded mr-1" id="btn-cancelshopping"><i
                                                                    class="mdi mdi-close"></i> Cancelar</button>
                                                            <button type="submit" class="btn btn-dark font-weight-bold btn-rounded ml-1"><i
                                                                    class="mdi mdi-content-save"></i> Guardar</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </form>
                                        </div><!-- end row-->
                                    </div><!-- end car-body-->
                                </div><!-- end card-->
                            </div><!-- end col-->
                        </div><!-- end row-->

                    </div>
                    <!-- container -->
                </div> <!-- content -->
                <!-- Footer Start -->
                <%@include file="footer.jsp" %>
                <!-- end Footer -->
            </div>
            <!-- ============================================================== -->
            <!-- End Page content -->
            <!-- ============================================================== -->
        </div>
        <!-- END wrapper -->

        <div class="rightbar-overlay"></div>


        <!-- bundle -->
        <script src="assets/js/vendor.min.js"></script>
        <script src="assets/js/app.min.js"></script>
        <!-- third party js -->
        <script src="assets/js/vendor/jquery.dataTables.min.js"></script>
        <script src="assets/js/vendor/dataTables.bootstrap4.js"></script>
        <script src="assets/js/vendor/dataTables.responsive.min.js"></script>
        <script src="assets/js/vendor/responsive.bootstrap4.min.js"></script>
        <!-- third party js ends -->


        <script src="assets/js/demo.toastr.js"></script>
        <script src="assets/js/query/query.js"></script>
        <script src="assets/js/cartera/cartera.js"></script>
    </body>
</html>




