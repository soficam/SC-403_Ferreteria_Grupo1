/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ferreteria.controller;

import ferreteria.domain.DetallePedido;
import ferreteria.domain.Pedido;
import ferreteria.service.DetallePedidoService;
import ferreteria.service.PedidoService;
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
import java.math.BigDecimal;

/**
 *
 * @author enano
 */

@Controller
@RequestMapping("/ventas")
public class VentaController {
    int order = 0;
    
    @Autowired
    private DetallePedidoService detallePedidoService;
    
    
    @GetMapping
    public String listarVentas(Model model) {
        model.addAttribute("detallePedidos", detallePedidoService.getDetallePedido());
        return "ventas/historialVentas";
    }

    @GetMapping("/ordenarPrecioDesc")
    public String listarVentasPrecioDesc(Model model) {
        order = 1;
        model.addAttribute("detallePedidos", detallePedidoService.getProductosSubtotalDesc());
        return "ventas/historialVentas";
}
    
    @GetMapping("/ordenarFechaDesc")
    public String listarVentasFechaDesc(Model model) {
        order = 2;
        model.addAttribute("detallePedidos", detallePedidoService.getProductosFechaDesc());
        return "ventas/historialVentas";
}
    
    @GetMapping("/reporte")
public void generarReporteVentas(HttpServletResponse response) throws IOException, DocumentException {

    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=Reporte_de_Ventas.pdf");
    
    List<DetallePedido> detallePedidos = detallePedidoService.getDetallePedido();
    if (order == 1){
        detallePedidos = detallePedidoService.getProductosSubtotalDesc();    
    }else if (order == 2){
        detallePedidos = detallePedidoService.getProductosFechaDesc();    
    }else{
        detallePedidos = detallePedidoService.getDetallePedido();
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
    tabla.addCell("Fecha");
    tabla.addCell("Producto");
    tabla.addCell("Monto");    
    BigDecimal total = BigDecimal.ZERO;
   
    for (DetallePedido dp : detallePedidos) {
        
        tabla.addCell(String.valueOf(dp.getPedido().getIdPedido()));        
        tabla.addCell(String.valueOf(dp.getPedido().getFecha()));        
        tabla.addCell(dp.getProducto().getNombre() != null ? dp.getProducto().getNombre() : "Sin nombre");
        tabla.addCell("₡" + dp.getSubtotal());        
        total = total.add(dp.getSubtotal());
    }
    pdf.add(tabla);
    pdf.add(new Paragraph(" "));
    pdf.add(new Paragraph("---------------------------------------------------------"));
    pdf.add(new Paragraph(" "));
    pdf.add(new Paragraph("TOTAL: ₡" + total));
    pdf.close();
}
}
