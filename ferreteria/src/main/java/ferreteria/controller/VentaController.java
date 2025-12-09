/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ferreteria.controller;

import ferreteria.domain.Venta;
import ferreteria.service.VentaService;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.List;
import java.util.Optional;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfPTable;

/**
 *
 * @author enano
 */

@Controller
@RequestMapping("/ventas")
public class VentaController {
    int order = 0;
    
    @Autowired
    private VentaService ventaService;
    @Autowired 
    private MessageSource messageSource;
    
    @GetMapping
    public String listarVentas(Model model) {
        model.addAttribute("ventas", ventaService.getVentas());
        return "ventas/historialVentas";
    }
    
    @GetMapping("/ordenarFechaVentaDesc")
    public String listarVentasFechaVentaDesc(Model model) {
        order = 1;
        model.addAttribute("ventas", ventaService.getProductosFechaVentaDesc());
        return "ventas/historialVentas";
    }    
    
    @GetMapping("/ordenarPrecioDesc")
    public String listarVentasPrecioDesc(Model model) {
        order = 2;
        model.addAttribute("ventas", ventaService.getProductosPrecioDesc());
        return "ventas/historialVentas";
}
    
    @GetMapping("/reporte")
public void generarReporteVentas(HttpServletResponse response) throws IOException, DocumentException {

    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=Reporte_de_Ventas.pdf");

    List<Venta> ventas = ventaService.getVentas();
    if (order == 1){
        ventas = ventaService.getProductosFechaVentaDesc();
    }else if (order == 2){
        ventas = ventaService.getProductosPrecioDesc();    
    }else{
        ventas = ventaService.getVentas();
    }  
    
    Document pdf = new Document();
    PdfWriter.getInstance(pdf, response.getOutputStream());
    pdf.open();
    
    pdf.add(new Paragraph("Reporte de Ventas"));
    pdf.add(new Paragraph(" "));
    pdf.add(new Paragraph("---------------------------------------------------------"));
    pdf.add(new Paragraph(" "));
 
    PdfPTable tabla = new PdfPTable(4);
    tabla.addCell("ID");
    tabla.addCell("Producto");
    tabla.addCell("Precio");
    tabla.addCell("Fecha de Venta");
    int total = 0;
   
    for (Venta v : ventas) {
        tabla.addCell(String.valueOf(v.getIdVenta()));
        tabla.addCell(v.getProducto() != null ? v.getProducto() : "Sin nombre");
        tabla.addCell("₡" + v.getPrecio());
        tabla.addCell(v.getFechaVenta().toString());
        total += v.getPrecio();
    }
    pdf.add(tabla);
    pdf.add(new Paragraph(" "));
    pdf.add(new Paragraph("---------------------------------------------------------"));
    pdf.add(new Paragraph(" "));
    pdf.add(new Paragraph("TOTAL: ₡" + total));
    pdf.close();
}
}
