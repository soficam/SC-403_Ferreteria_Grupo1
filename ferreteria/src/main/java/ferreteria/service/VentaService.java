/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ferreteria.service;

import ferreteria.domain.Venta;
import ferreteria.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;


/**
 *
 * @author enano
 */

@Service
public class VentaService {
    
    @Autowired
    private VentaRepository ventaRepository;
    
    public List<Venta> getVentas(){
        return ventaRepository.findAll();
    }
    
    public List<Venta> getProductosPrecioDesc(){
        return ventaRepository.findAllByOrderByPrecioDesc();
    }
    
    public List<Venta> getProductosFechaVentaDesc(){
        return ventaRepository.findAllByOrderByFechaVentaDesc();
    }
    
}
