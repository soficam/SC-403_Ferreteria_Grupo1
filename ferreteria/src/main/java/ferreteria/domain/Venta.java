/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ferreteria.domain;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
/**
 *
 * @author enano
 */


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "venta")
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Long idVenta;

    @Column(name = "producto", nullable = false, length = 30)
    @NotBlank(message = "El nombre del producto no puede estar vacío.")
    @Size(max = 30, message = "El nombre del producto no puede tener más de 30 caracteres.")
    private String producto;

    @Column(name = "precio", nullable = false)
    @NotNull(message = "El precio no puede estar vacío.")
    @Min(value = 1, message = "El precio debe ser mayor o igual a 1.")
    private int precio; 

    @Column(name = "fecha_venta", nullable = false)
    @NotNull(message = "La fecha de venta no puede estar vacía.")
    private LocalDateTime fechaVenta; 
    
    /*public int getPrecio(){
        return precio;
    }*/
}

