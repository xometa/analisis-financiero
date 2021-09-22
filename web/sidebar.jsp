<%-- 
    Document   : sidebar
    Created on : Dec 29, 2020, 9:13:34 AM
    Author     : jsaul
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    int nivel = Integer.parseInt(String.valueOf(session.getAttribute("nivel")));
%>
<div class="left-side-menu">

    <!-- LOGO -->
    <a href="index.jsp" class="logo text-center logo-light">
        <span class="logo-lg">
            <img src="assets/images/logo.png" alt="" height="16">
        </span>
        <span class="logo-sm">
            <img src="assets/images/logo_sm.png" alt="" height="16">
        </span>
    </a>

    <!-- LOGO -->
    <a href="index.jsp" class="logo text-center logo-dark">
        <span class="logo-lg">
            <img src="assets/images/logo-dark.png" alt="" height="16">
        </span>
        <span class="logo-sm">
            <img src="assets/images/logo_sm_dark.png" alt="" height="16">
        </span>
    </a>

    <div class="h-100" id="left-side-menu-container" data-simplebar>

        <!--- Sidemenu -->
        <ul class="metismenu side-nav">

            <li class="side-nav-title side-nav-item">Navegación</li>

            <li class="side-nav-item">
                <a href="index.jsp" class="side-nav-link">
                    <i class="uil-home-alt"></i>
                    <span> Inicio</span>
                </a>
            </li>

            <li class="side-nav-title side-nav-item">Operaciones</li>
                <% if (nivel == 1 || nivel == 2) { %>
            <li class="side-nav-item">
                <a href="javascript: void(0);" class="side-nav-link">
                    <i class="uil-archive"></i>
                    <span> Activo fijo</span>
                    <span class="menu-arrow"></span>
                </a>
                <ul class="side-nav-second-level" aria-expanded="false">
                    <% if (nivel == 1 || nivel == 2) { %>
                    <li>
                        <a href="activofijo.jsp">Listado</a>
                    </li>
                    <%}%>

                    <% if (nivel == 1) { %>
                    <li>
                        <a href="depreciacion.jsp">Depreciación</a>
                    </li>
                    <li>
                        <a href="amortizacion.jsp">Amortización</a>
                    </li>
                    <%}%>
                </ul>
            </li>
            <%}%>

            <% if (nivel == 1) { %>
            <li class="side-nav-item">
                <a href="prestamos.jsp" class="side-nav-link">
                    <i class="uil-newspaper"></i>
                    <span> Prestamos</span>
                </a>
            </li>

            <%}%>
            <li class="side-nav-title side-nav-item">Registros</li>


            <% if (nivel == 1 || nivel == 3) { %>
            <li class="side-nav-item">
                <a href="sucursal.jsp" class="side-nav-link">
                    <i class="uil-map-pin-alt"></i>
                    <span> Sucursal</span>
                </a>
            </li>

            <li class="side-nav-item">
                <a href="departamento.jsp" class="side-nav-link">
                    <i class="uil-sitemap"></i>
                    <span> Departamento</span>
                </a>
            </li>
            <%}%>

            <% if (nivel == 1) { %>
            <li class="side-nav-item">
                <a href="tipoactivo.jsp" class="side-nav-link">
                    <i class="uil-bookmark"></i>
                    <span> Tipo activo</span>
                </a>
            </li>
            <%}%>

            <% if (nivel == 1 || nivel == 2) { %>
            <li class="side-nav-item">
                <a href="javascript: void(0);" class="side-nav-link">
                    <i class="uil-user-square"></i>
                    <span> Clientes</span>
                    <span class="menu-arrow"></span>
                </a>
                <ul class="side-nav-second-level" aria-expanded="false">
                    <li>
                        <a href="clientenatural.jsp">Clientes naturales</a>
                    </li>
                    <li>
                        <a href="clientejuridico.jsp">Clientes jurídicos</a>
                    </li>
                    <li>
                        <a href="fiador.jsp">Fiadores</a>
                    </li>
                    <li>
                        <a href="carteras.jsp">Carteras</a>
                    </li>
                </ul>
            </li>

            <%}%>

            <% if (nivel == 1) { %>
            <li class="side-nav-item">
                <a href="tipoprestamo.jsp" class="side-nav-link">
                    <i class="uil-newspaper"></i>
                    <span> Tipo prestamo</span>
                </a>
            </li>

            <li class="side-nav-item">
                <a href="empleado.jsp" class="side-nav-link">
                    <i class="uil-users-alt"></i>
                    <span> Empleados</span>
                </a>
            </li>
            <%}%>


            <% if (nivel == 1 || nivel == 2 || nivel == 3) { %>
            <li class="side-nav-item">
                <a href="reportes.jsp" class="side-nav-link">
                    <i class="uil-file-alt"></i>
                    <span> Reportes</span>
                </a>
            </li>

            <%}%>
            <li class="side-nav-item">
                <a href="acercade.jsp" class="side-nav-link">
                    <i class="uil-comment-exclamation"></i>
                    <span> Acerca de</span>
                </a>
            </li>

        </ul>
        <!-- End Sidebar -->

        <div class="clearfix"></div>

    </div>
    <!-- Sidebar -left -->

</div>
