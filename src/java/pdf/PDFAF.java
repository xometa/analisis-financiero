/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Activofijo;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.ModeloActivoFijo;

/**
 *
 * @author Kevin
 */
public class PDFAF extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setHeader("Content-Disposition", "inline; filename=Activos fijo");
            response.setContentType("application/pdf; name=\"MyFile.pdf\"");
            OutputStream out = response.getOutputStream();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            String cadena = "", codigo = "", uso = "";
            ModeloActivoFijo maf = new ModeloActivoFijo();
            ArrayList<Activofijo> af = (ArrayList<Activofijo>) maf.listadoActivo();
            DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
            simbolo.setDecimalSeparator('.');
            simbolo.setGroupingSeparator(',');
            DecimalFormat f = new DecimalFormat("###,##0.00", simbolo);

            URL resUrl = this.getClass().getResource("logo.png");
            Image imagen = Image.getInstance(resUrl);
            Document documento = new Document();
            documento.setMargins(72f, 72f, 72f, 72f);
            documento.setPageSize(PageSize.A3.rotate());
            PdfWriter writer = PdfWriter.getInstance(documento, out);
            HeaderFooterPageEvent event = new HeaderFooterPageEvent();
            writer.setPageEvent(event);
            documento.open();
            event.setHeader(String.valueOf(sdf.format(new Date())));

            imagen.scaleAbsolute(60f, 10f);//tamaño imagen
            imagen.setAbsolutePosition(70f, 750f);
            //imagen.setAlignment(Element.ALIGN_TOP);//alineacion de la imagen 
            imagen.setBorder(0);
            imagen.setSpacingAfter(0);//antes de la imagen
            imagen.setSpacingBefore(10);//despues de la imagen
            documento.add(imagen);

            Paragraph par1;//para el titulo
            //definiendo el titulo del documento
            Font fonttitulo;
            fonttitulo = new Font(Font.FontFamily.COURIER, 14, Font.BOLD, BaseColor.BLACK);
            par1 = new Paragraph(new Phrase("HYPER S.A DE C.V", fonttitulo));
            par1.setAlignment(Element.ALIGN_CENTER);
            par1.add(new Phrase(Chunk.NEWLINE));//salto de linea
            documento.add(par1);
            fonttitulo = new Font(Font.FontFamily.COURIER, 10, Font.BOLD, BaseColor.BLACK);
            par1 = new Paragraph(new Phrase("REPORTE DE ACTIVOS FIJOS", fonttitulo));
            par1.setAlignment(Element.ALIGN_CENTER);
            par1.add(new Phrase(Chunk.NEWLINE));//salto de linea
            par1.add(new Phrase(Chunk.NEWLINE));//salto de linea
            par1.add(new Phrase(Chunk.NEWLINE));//salto de linea
            documento.add(par1);

            //inicio de la tabla de datos
            PdfPTable tabla = new PdfPTable(11);//tabla
            PdfPCell cell;//para generar las celdas
            tabla.setWidths(new int[]{3, 2, 3, 2, 2, 2, 2, 2, 2, 2, 2});
            tabla.setWidthPercentage(100f);

            //emcabezado
            cell = new PdfPCell(new Paragraph("TABLA DE INFORMACIÓN SOBRE TODOS LOS ACTIVOS FIJOS", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(11);
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph("Código", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph("Nombre", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph("Descripción", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph("Procedencia", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph("Precio", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph("Fecha adquisición", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph("Vida útil anual", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph("Sucursal", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph("Departamento", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph("Tipo", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph("Uso", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            tabla.addCell(cell);

            if (maf.listadoActivo().size() > 0) {

                for (Activofijo ff : af) {
                    cadena = String.valueOf(ff.getId());
                    codigo = ff.getSucursal().getCodigo() + "-" + ff.getDepartamento().getCodigo() + "-" + ff.getTipoactivo().getCodigo();
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

                    if (ff.getActivobajas().size() > 0) {
                        uso = "Dado de baja";
                    } else if (ff.getUso() == 1) {
                        uso = "En uso";
                    } else if (ff.getUso() == 0) {
                        uso = "Sin usar";
                    }

                    cell = new PdfPCell(new Paragraph(codigo, FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setRowspan(2);
                    tabla.addCell(cell);

                    cell = new PdfPCell(new Paragraph(ff.getNombre(), FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setRowspan(2);
                    tabla.addCell(cell);

                    cell = new PdfPCell(new Paragraph(ff.getDescripcion(), FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setRowspan(2);
                    tabla.addCell(cell);

                    cell = new PdfPCell(new Paragraph(ff.getProcedencia(), FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setRowspan(2);
                    tabla.addCell(cell);

                    cell = new PdfPCell(new Paragraph(f.format(ff.getPrecio()), FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setRowspan(2);
                    tabla.addCell(cell);

                    cell = new PdfPCell(new Paragraph(sdf.format(ff.getFechaadquisicion()), FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setRowspan(2);
                    tabla.addCell(cell);

                    cell = new PdfPCell(new Paragraph("" + ff.getVidautil(), FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setRowspan(2);
                    tabla.addCell(cell);

                    cell = new PdfPCell(new Paragraph(ff.getSucursal().getNombre(), FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setRowspan(2);
                    tabla.addCell(cell);

                    cell = new PdfPCell(new Paragraph(ff.getDepartamento().getNombre(), FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setRowspan(2);
                    tabla.addCell(cell);

                    cell = new PdfPCell(new Paragraph(ff.getTipoactivo().getNombre(), FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setRowspan(2);
                    tabla.addCell(cell);

                    cell = new PdfPCell(new Paragraph(uso, FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setRowspan(2);
                    tabla.addCell(cell);
                }

            } else {
                cell = new PdfPCell(new Paragraph("No hay registros", FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setColspan(10);
                tabla.addCell(cell);
            }
            documento.add(tabla);
            documento.close();
        } catch (DocumentException ex) {
            Logger.getLogger(PDFD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
