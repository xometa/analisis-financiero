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
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Cnatural;
import entities.Disponibilidad;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.ModeloPersonaNatural;

/**
 *
 * @author jsaul
 */
public class PDFCNI extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try {
            response.setHeader("Content-Disposition", "inline; filename=Clientes naturales con ingresos/egresos");
            response.setContentType("application/pdf; name=\"MyFile.pdf\"");
            OutputStream out = response.getOutputStream();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            ModeloPersonaNatural mpn = new ModeloPersonaNatural();
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
            par1 = new Paragraph(new Phrase("LISTADO DE CLIENTES NATURALES CON SUS RESPECTIVOS", fonttitulo));
            par1.setAlignment(Element.ALIGN_CENTER);
            par1.add(new Phrase(Chunk.NEWLINE));//salto de linea
            documento.add(par1);
            fonttitulo = new Font(Font.FontFamily.COURIER, 10, Font.BOLD, BaseColor.BLACK);
            par1 = new Paragraph(new Phrase("INGRESOS Y EGRESOS", fonttitulo));
            par1.setAlignment(Element.ALIGN_CENTER);
            par1.add(new Phrase(Chunk.NEWLINE));//salto de linea
            par1.add(new Phrase(Chunk.NEWLINE));//salto de linea
            par1.add(new Phrase(Chunk.NEWLINE));//salto de linea
            documento.add(par1);

            //inicio de la tabla de datos
            PdfPTable tabla = new PdfPTable(6);//tabla
            PdfPCell cell;//para generar las celdas
            tabla.setWidths(new int[]{1, 2, 2, 2, 2, 2});
            tabla.setWidthPercentage(100f);

            //emcabezado
            cell = new PdfPCell(new Paragraph("TABLA DE INFORMACIÓN SOBRE INGRESOS Y EGRESOS POR CLIENTE", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(6);
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph("Código", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph("DUI", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph("Nombre", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph("Dirección", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph("Estado civil", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph("Lugar de trabajo", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            tabla.addCell(cell);

            //generando la lista de depreciaciones
            for (Cnatural cn : mpn.listadoPersonasNaturales()) {
                //cuerpo de datos
                cell = new PdfPCell(new Paragraph(cn.getCodigo(), FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setRowspan(2);
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(cn.getPersona().getDui(), FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setRowspan(2);
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(cn.getPersona().getNombre() + " " + cn.getPersona().getApellido(), FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setRowspan(2);
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(cn.getPersona().getDireccion(), FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setRowspan(2);
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(cn.getPersona().getEstadocivil(), FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setRowspan(2);
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(cn.getLugartrabajo(), FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setRowspan(2);
                tabla.addCell(cell);

                //encabezado de la tabla ingresos y egresos
                cell = new PdfPCell(new Paragraph("Descripción", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setColspan(2);
                tabla.addCell(cell);
                
                cell = new PdfPCell(new Paragraph("Monto", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setColspan(2);
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph("Tipo", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setColspan(2);
                tabla.addCell(cell);
                
                if (cn.getDisponibilidadsForIdnatural().size() > 0) {
                    for (Disponibilidad dp : cn.getDisponibilidadsForIdnatural()) {
                        cell = new PdfPCell(new Paragraph(dp.getOperacion(), FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setColspan(2);
                        tabla.addCell(cell);
                        
                        cell = new PdfPCell(new Paragraph(f.format(dp.getMonto()), FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setColspan(2);
                        tabla.addCell(cell);
                        
                        cell = new PdfPCell(new Paragraph(dp.getTipo(), FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setColspan(2);
                        tabla.addCell(cell);
                    }
                } else {
                    cell = new PdfPCell(new Paragraph("No hay registros", FontFactory.getFont("courier", 12, Font.NORMAL, BaseColor.BLACK)));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setColspan(6);
                    tabla.addCell(cell);
                }
                
                cell = new PdfPCell(new Paragraph(" ", FontFactory.getFont("courier", 12, Font.BOLD, BaseColor.BLACK)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setColspan(6);
                cell.setRowspan(6);
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
