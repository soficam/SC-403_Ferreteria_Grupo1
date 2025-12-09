package ferreteria.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPedido;

    private String nombreCliente;
    private LocalDateTime fecha;
    private String estado;
    
    @ManyToOne
@JoinColumn(name = "id_usuario", referencedColumnName = "idUsuario")
private UsuarioPedido usuario;

}
