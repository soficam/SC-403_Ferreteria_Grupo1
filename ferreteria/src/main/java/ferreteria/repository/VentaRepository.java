/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ferreteria.repository;

import ferreteria.domain.Venta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author enano
 */
public interface VentaRepository extends JpaRepository<Venta, Long> {
    
    /*public List<Venta> findAllByOrderByPrecioDesc(int precio);
    public List<Venta> findByFechaVentaAsc();
    public List<Venta> findByFechaVentaDesc();
    public List<Venta> findByPrecioAsc();*/
    
    public List<Venta> findAllByOrderByPrecioDesc();
    public List<Venta> findAllByOrderByFechaVentaDesc();
    
}
