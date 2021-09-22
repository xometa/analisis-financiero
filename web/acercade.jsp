<%-- 
    Document   : carcade
    Created on : Jan 20, 2021, 11:05:08 AM
    Author     : jsaul
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
        <title>Acerca de</title>
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
                                            <li class="breadcrumb-item active">Acerca de</li>
                                        </ol>
                                    </div>
                                    <h4 class="page-title">Acerca de</h4>
                                </div>
                            </div>
                        </div>
                        <!-- end page title -->


                        <div class="row">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card bg-danger">
                                        <div class="card-body profile-user-box">
                                            <div class="row">
                                                <div class="col-sm-12">
                                                    <div class="media">
                                                        <span class="float-left m-2 mr-4"><img src="assets/images/ues.jpg"
                                                                                               style="height: 150px;" alt="ues" class="rounded-circle img-thumbnail"></span>
                                                        <div class="media-body">

                                                            <h2 class="mt-1 mb-1 text-white">Universidad de El Salvador</h2>
                                                            <p class="font-13 text-white-50"> Facultad Multidisciplinaria Paracentral</p>
                                                        </div> <!-- end media-body-->
                                                    </div>
                                                </div> <!-- end col-->
                                            </div> <!-- end row -->

                                        </div> <!-- end card-body/ profile-user-box-->
                                    </div>
                                    <div class="card-body">
                                        <p class="text-muted text-justify pt3">
                                            Agradecemos las oportunidades de agregar valor al negocio de nuestros clientes.
                                            Buscamos activamente oportunidades de colaboración y solicitamos la opinión de 
                                            los empleados y la comunidad para responder a sus inquietudes. Nos comunicamos 
                                            con la comunidad y entre nosotros para gestionar las expectativas y asegurar 
                                            los más altos niveles de satisfacción. 
                                            <br>
                                            <br>
                                            Encuentranos en:
                                            <br>
                                            - <span class="font-weight-bold">Facebook:</span> Hyper Institución Financiera
                                            <br>
                                            - <span class="font-weight-bold">Whatsapp:</span> +503 7954-3245

                                        </p>
                                        <br>
                                        <p class="text-muted text-justify">
                                            <span class="font-weight-bold">Objetivos</span>
                                            <br>
                                            La misión de Hyper Serviciones financieros es proporcionar productos 
                                            financieros innovadores, hacerlos fácilmente accesibles y ofrecer esos 
                                            productos y servicios de una manera simple y eficiente, aprovechando la
                                            tecnología para lograrlo. La institución se esfuerza por ser transparente 
                                            en sus ofertas, responsable en la forma en que opera y también garantiza 
                                            un alto nivel de seguridad para salvaguardar su propio interés y el de 
                                            sus clientes.
                                        </p>
                                        <ul>
                                            <li class="text-justify">
                                                <span class="font-weight-bold">Igualdad de acceso a las oportunidades económicas: </span> nuestro trabajo debe reflejar un compromiso con un acceso justo e igualitario a las oportunidades económicas que la vida tiene para ofrecer.
                                            </li>
                                            <li class="text-justify">
                                                <span class="font-weight-bold">Excelencia: </span>las personas y entidades a las que servimos tienen derecho a esperar excelencia, y tenemos la responsabilidad de proporcionarla. Un compromiso con la excelencia es una declaración de respeto por nuestros clientes, depositantes, financiadores, inversores, consejos de administración y entre sí.
                                            </li>
                                            <li class="text-justify">
                                                <span class="font-weight-bold">Sentido humano: </span>reconociendo tanto las limitaciones de nuestros propios recursos humanos y financieros como la necesidad de un amplio apoyo para abordar las necesidades de desarrollo que enfrentan las personas y comunidades en dificultades, Hyper tratará de apoyar y participar, atraer e influir en el apoyo de otros para lograr nuestra misión.
                                            </li>
                                        </ul>
                                    </div>
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


        <!-- bundle -->
        <script src="assets/js/vendor.min.js"></script>
        <script src="assets/js/app.min.js"></script>

    </body>
</html>
