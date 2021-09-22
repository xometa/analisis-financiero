<%-- 
    Document   : reportes
    Created on : Jan 16, 2021, 12:21:17 PM
    Author     : kevin
--%>

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
        <title>Reportes</title>
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
                                            <li class="breadcrumb-item active">Reportes</li>
                                        </ol>
                                    </div>
                                    <h4 class="page-title">Listado de reportes</h4>
                                </div>
                            </div>
                        </div>
                        <!-- end page title -->






                        <div class="row">

                            <div class="tasks col-lg-4">
                                <h5 class="mt-0 task-header">Activo fijo</h5>

                                <div id="task-list-one" class="task-list-items">

                                    <!-- Query Item -->
                                    <div class="card mb-1 shadow-none border">
                                        <div class="p-2">
                                            <div class="row align-items-center">
                                                <div class="col-auto">
                                                    <div class="avatar-sm">
                                                        <span class="avatar-title rounded"><i class="uil-archive h3"></i></span>
                                                    </div>
                                                </div>
                                                <div class="col pl-0">
                                                    <span class="text-muted font-weight-bold">Listado de activos</span>
                                                </div>
                                                <div class="col-auto">
                                                    <!-- Button -->
                                                    <a href="PDFAF" target="blank" class="btn btn-link btn-lg text-muted">
                                                        <i class="mdi mdi-eye"></i>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Query Item End -->
                                    
                                    <!-- Query Item -->
                                    <div class="card mb-1 shadow-none border">
                                        <div class="p-2">
                                            <div class="row align-items-center">
                                                <div class="col-auto">
                                                    <div class="avatar-sm">
                                                        <span class="avatar-title rounded"><i class="uil-archive h3"></i></span>
                                                    </div>
                                                </div>
                                                <div class="col pl-0">
                                                    <span class="text-muted font-weight-bold">Activos que termino su vida útil pero aún esta funcionando economicamente</span>
                                                </div>
                                                <div class="col-auto">
                                                    <!-- Button -->
                                                    <a href="PDFAFF" target="blank" class="btn btn-link btn-lg text-muted">
                                                        <i class="mdi mdi-eye"></i>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Query Item End -->
                                    
                                    <!-- Query Item -->
                                    <div class="card mb-1 shadow-none border">
                                        <div class="p-2">
                                            <div class="row align-items-center">
                                                <div class="col-auto">
                                                    <div class="avatar-sm">
                                                        <span class="avatar-title rounded"><i class="uil-archive h3"></i></span>
                                                    </div>
                                                </div>
                                                <div class="col pl-0">
                                                    <span class="text-muted font-weight-bold">Activos que han sido dados de baja</span>
                                                </div>
                                                <div class="col-auto">
                                                    <!-- Button -->
                                                    <a href="PDFAFB" target="blank" class="btn btn-link btn-lg text-muted">
                                                        <i class="mdi mdi-eye"></i>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Query Item End -->

                                </div> <!-- end company-list-1-->
                            </div>


                            <div class="tasks col-lg-4">
                                <h5 class="mt-0 task-header">Clientes</h5>

                                <div id="task-list-two" class="task-list-items">

                                    <!-- Query Item -->
                                    <div class="card mb-1 shadow-none border">
                                        <div class="p-2">
                                            <div class="row align-items-center">
                                                <div class="col-auto">
                                                    <div class="avatar-sm">
                                                        <span class="avatar-title rounded"><i class="uil-users-alt h3"></i></span>
                                                    </div>
                                                </div>
                                                <div class="col pl-0">
                                                    <span class="text-muted font-weight-bold">Clientes naturales</span>
                                                </div>
                                                <div class="col-auto">
                                                    <!-- Button -->
                                                    <a href="PDFCN" target="blank" class="btn btn-link btn-lg text-muted">
                                                        <i class="mdi mdi-eye"></i>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Query Item End -->
                                    
                                    <!-- Query Item -->
                                    <div class="card mb-1 shadow-none border">
                                        <div class="p-2">
                                            <div class="row align-items-center">
                                                <div class="col-auto">
                                                    <div class="avatar-sm">
                                                        <span class="avatar-title bg-info rounded"><i class="uil-user-square h3"></i></span>
                                                    </div>
                                                </div>
                                                <div class="col pl-0">
                                                    <span class="text-muted font-weight-bold">Clientes naturale con sus respectivo ingresos y egresos</span>
                                                </div>
                                                <div class="col-auto">
                                                    <!-- Button -->
                                                    <a href="PDFCNI" target="blank" class="btn btn-link btn-lg text-muted">
                                                        <i class="mdi mdi-eye"></i>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Query Item End -->
                                    
                                    <!-- Query Item -->
                                    <div class="card mb-1 shadow-none border">
                                        <div class="p-2">
                                            <div class="row align-items-center">
                                                <div class="col-auto">
                                                    <div class="avatar-sm">
                                                        <span class="avatar-title rounded"><i class="uil-user h3"></i></span>
                                                    </div>
                                                </div>
                                                <div class="col pl-0">
                                                    <span class="text-muted font-weight-bold">Clientes jurídicos</span>
                                                </div>
                                                <div class="col-auto">
                                                    <!-- Button -->
                                                    <a href="PDFCJ" target="blank" class="btn btn-link btn-lg text-muted">
                                                        <i class="mdi mdi-eye"></i>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Query Item End -->

                                </div> <!-- end company-list-2-->
                            </div>


                            <div class="tasks col-lg-4">
                                <h5 class="mt-0 task-header">Empleados</h5>
                                <div id="task-list-three" class="task-list-items">

                                    <!-- Query Item -->
                                    <div class="card mb-1 shadow-none border">
                                        <div class="p-2">
                                            <div class="row align-items-center">
                                                <div class="col-auto">
                                                    <div class="avatar-sm">
                                                        <span class="avatar-title rounded"><i class="uil-user h3"></i></span>
                                                    </div>
                                                </div>
                                                <div class="col pl-0">
                                                    <span class="text-muted font-weight-bold">Listado de empleados</span>
                                                </div>
                                                <div class="col-auto">
                                                    <!-- Button -->
                                                    <a href="PDFEM" target="blank" class="btn btn-link btn-lg text-muted">
                                                        <i class="mdi mdi-eye"></i>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Query Item End -->

                                </div> <!-- end company-list-3-->
                            </div>

                            <div class="tasks col-lg-4">
                                <h5 class="mt-0 task-header">Sucursal</h5>
                                <div id="task-list-four" class="task-list-items">

                                    <!-- Query Item -->
                                    <div class="card mb-1 shadow-none border">
                                        <div class="p-2">
                                            <div class="row align-items-center">
                                                <div class="col-auto">
                                                    <div class="avatar-sm">
                                                        <span class="avatar-title rounded"><i class="uil-home-alt h3"></i></span>
                                                    </div>
                                                </div>
                                                <div class="col pl-0">
                                                    <span class="text-muted font-weight-bold">Listado de sucursales</span>
                                                </div>
                                                <div class="col-auto">
                                                    <!-- Button -->
                                                    <a href="PDFSC" target="blank" class="btn btn-link btn-lg text-muted">
                                                        <i class="mdi mdi-eye"></i>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Query Item End -->
                                    
                                </div> <!-- end company-list-4-->
                            </div>

                            <div class="tasks col-lg-4">
                                <h5 class="mt-0 task-header">Departamentos</h5>
                                <div id="task-list-four" class="task-list-items">

                                    <!-- Query Item -->
                                    <div class="card mb-1 shadow-none border">
                                        <div class="p-2">
                                            <div class="row align-items-center">
                                                <div class="col-auto">
                                                    <div class="avatar-sm">
                                                        <span class="avatar-title rounded"><i class="uil-home-alt h3"></i></span>
                                                    </div>
                                                </div>
                                                <div class="col pl-0">
                                                    <span class="text-muted font-weight-bold">Listado de departamentos</span>
                                                </div>
                                                <div class="col-auto">
                                                    <!-- Button -->
                                                    <a href="PDFDP" target="blank" class="btn btn-link btn-lg text-muted">
                                                        <i class="mdi mdi-eye"></i>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Query Item End -->

                                </div> <!-- end company-list-4-->
                            </div>

                            <div class="tasks col-lg-4">
                                <h5 class="mt-0 task-header">Prestamos</h5>
                                <div id="task-list-four" class="task-list-items">

                                    

                                </div> <!-- end company-list-4-->
                            </div>

                        </div>

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
        <script src="assets/js/demo.toastr.js"></script>

    </body>
</html>



