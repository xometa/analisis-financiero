<%-- 
    Document   : index
    Created on : Dec 25, 2020, 3:45:14 PM
    Author     : jsaul
--%>

<%@page import="models.ModeloEmpleado"%>
<%@page import="models.ModeloDepartamento"%>
<%@page import="models.ModeloSucursal"%>
<%@page import="models.ModeloJuridico"%>
<%@page import="models.ModeloPersonaNatural"%>
<%@page import="models.ModeloActivoFijo"%>
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

    ModeloActivoFijo maf = new ModeloActivoFijo();
    ModeloPersonaNatural mpn = new ModeloPersonaNatural();
    ModeloJuridico mj = new ModeloJuridico();
    ModeloSucursal msc = new ModeloSucursal();
    ModeloDepartamento md = new ModeloDepartamento();
    ModeloEmpleado me = new ModeloEmpleado();
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <title>Inicio</title>
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
                                            <li class="breadcrumb-item active">Inicio</li>
                                        </ol>
                                    </div>
                                    <h4 class="page-title">Inicio</h4>
                                </div>
                            </div>
                        </div>
                        <!-- end page title -->
                        <div class="row">
                            <div class="col-lg-3">
                                <div class="card widget-flat">
                                    <div class="card-body">
                                        <div class="float-right">
                                            <i class="mdi mdi-account-multiple widget-icon"></i>
                                        </div>
                                        <h5 class="text-muted font-weight-normal mt-0" title="Number of Customers">Clientes naturales</h5>
                                        <h3 class="mt-3 mb-3"><%= mpn.listadoPersonasNaturales().size()%></h3>
                                    </div> <!-- end card-body-->
                                </div> <!-- end card-->
                            </div> <!-- end col-->


                            <div class="col-lg-3">
                                <div class="card widget-flat">
                                    <div class="card-body">
                                        <div class="float-right">
                                            <i class="mdi mdi-account-multiple widget-icon"></i>
                                        </div>
                                        <h5 class="text-muted font-weight-normal mt-0" title="Number of Customers">Clientes jurídicos</h5>
                                        <h3 class="mt-3 mb-3"><%= mj.listadoJuridico().size()%></h3>
                                    </div> <!-- end card-body-->
                                </div> <!-- end card-->
                            </div> <!-- end col-->

                            <div class="col-lg-3">
                                <div class="card widget-flat">
                                    <div class="card-body">
                                        <div class="float-right">
                                            <i class="mdi mdi-account widget-icon alert-success"></i>
                                        </div>
                                        <h5 class="text-muted font-weight-normal mt-0" title="Number of Orders">Sucursales</h5>
                                        <h3 class="mt-3 mb-3"><%= msc.listadoSucursal().size()%></h3>
                                    </div> <!-- end card-body-->
                                </div> <!-- end card-->
                            </div> <!-- end col-->

                            <div class="col-lg-3">
                                <div class="card widget-flat">
                                    <div class="card-body">
                                        <div class="float-right">
                                            <i class="mdi mdi-account widget-icon alert-success"></i>
                                        </div>
                                        <h5 class="text-muted font-weight-normal mt-0" title="Number of Orders">Departamentos</h5>
                                        <h3 class="mt-3 mb-3"><%= md.listadoDepartamento().size()%></h3>
                                    </div> <!-- end card-body-->
                                </div> <!-- end card-->
                            </div> <!-- end col-->


                            <div class="col-lg-3">
                                <div class="card widget-flat">
                                    <div class="card-body">
                                        <div class="float-right">
                                            <i class="mdi mdi-account widget-icon alert-success"></i>
                                        </div>
                                        <h5 class="text-muted font-weight-normal mt-0" title="Number of Orders">Total de activos fijos</h5>
                                        <h3 class="mt-3 mb-3"><%= maf.listadoActivo().size()%></h3>
                                    </div> <!-- end card-body-->
                                </div> <!-- end card-->
                            </div> <!-- end col-->

                            <div class="col-lg-3">
                                <div class="card widget-flat">
                                    <div class="card-body">
                                        <div class="float-right">
                                            <i class="mdi mdi-account widget-icon alert-success"></i>
                                        </div>
                                        <h5 class="text-muted font-weight-normal mt-0" title="Number of Orders">Usuarios</h5>
                                        <h3 class="mt-3 mb-3"><%= me.listadoEmpleados().size()%></h3>
                                    </div> <!-- end card-body-->
                                </div> <!-- end card-->
                            </div> <!-- end col-->

                        </div>
                        <!-- end row -->
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
        <!-- bundle -->
        <script src="assets/js/vendor.min.js"></script>
        <script src="assets/js/app.min.js"></script>
        <script src="assets/js/inicio/inicio.js"></script>
    </body>
</html>
