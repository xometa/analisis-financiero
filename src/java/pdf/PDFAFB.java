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
import entities.Activobaja;
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
public class PDFAFB extends HttpServlet {

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
            response.setHeader("Content-Disposition", "inline; filename=Activos dados de baja");
            response.setContentType("application/pdf; name=\"MyFile.pdf\"");
            OutputStream out = response.getOutputStream();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            String cadena = "", codigo = "";
            //Llamamos los activos de baja
            ModeloActivoFijo maf = new ModeloActivoFijo();
            ArrayList<Activobaja> ab = (ArrayList<Activobaja>) maf.listadoActivoBaja();

            DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
            simbolo.setDecimalSeparator('.');
            simbolo.setGroupingSeparator(',');
            DecimalFormat f = new DecimalFormat("###,##0.00", simbolo);

            URL resUrl = this.getClass().getResource("logo.png");
            Image imagen = Image.getInstance(resUrl);
            Document documento = new Document();
            documento.setMargins(72f, 72f, 72f, 72f);
            //Pagina horizontal
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
            par1 = new Paragraph(new Phrase("REPORTE DE ACTIVOS DADOS DE BAJA", fonttitulo));
            par1.setAlignment(Element.ALIGN_CENTER);
            par1.add(new Phrase(Chunk.NEWLINE));//salto de linea
            par1.add(new Phrase(Chunk.NEWLINE));//salto de linea
            par1.add(new Phrase(Chunk.NEWLINE));//salto de linea
            documento.add(par1);

            //inicio de la tabla de datos
            PdfPTable tabla = new PdfPTable(9);//tabla
            PdfPCell cell;//para generar las celdas
            tabla.setWidths(new int[]{3, 2, 2, 2, 2, 2, 2, 2, 2});
            tabla.setWidthPercentage(100f);

            //emcabezado
            cell = new PdfPCell(new Paragraph("TABLA DE INFORMACIÓN SOBRE ACTIVOS FIJOS DADOS DE BAJA", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(9);
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph("Código", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph("Nombre", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph("Tipo", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
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
            cell = new PdfPCell(new Paragraph("Fecha dado de baja", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph("Motivo de baja", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            tabla.addCell(cell);            

            for (Activobaja a : ab) {
                cadena = String.valueOf(a.getId());
                codigo = a.getActivofijo().getSucursal().getCodigo() + "-" + a.getActivofijo().getDepartamento().getCodigo()
                        + "-" + a.getActivofijo().getTipoactivo().getCodigo();
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
                cell = new PdfPCell(new Paragraph(codigo, FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setRowspan(2);
                tabla.addCell(cell);

                cell = new PdfPCell(new Paragraph(a.getActivofijo().getNombre(), FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setRowspan(2);
                tabla.addCell(cell);

                cell = new PdfPCell(new Paragraph(a.getActivofijo().getTipoactivo().getNombre(), FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setRowspan(2);
                tabla.addCell(cell);

                cell = new PdfPCell(new Paragraph(sdf.format(a.getActivofijo().getFechaadquisicion()), FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setRowspan(2);
                tabla.addCell(cell);

                cell = new PdfPCell(new Paragraph("" + a.getActivofijo().getVidautil(), FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setRowspan(2);
                tabla.addCell(cell);
                
                cell = new PdfPCell(new Paragraph(a.getActivofijo().getSucursal().getNombre(), FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setRowspan(2);
                tabla.addCell(cell);

                cell = new PdfPCell(new Paragraph(a.getActivofijo().getDepartamento().getNombre(), FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setRowspan(2);
                tabla.addCell(cell);
                
                cell = new PdfPCell(new Paragraph(sdf.format(a.getFecha()), FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setRowspan(2);
                tabla.addCell(cell);
                
                cell = new PdfPCell(new Paragraph(a.getMotivo(), FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setRowspan(2);
                tabla.addCell(cell);
            }

            documento.add(tabla);
            documento.close();
        } catch (DocumentException ex) {
            Logger.getLogger(PDFAFB.class.getName()).log(Level.SEVERE, null, ex);
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
