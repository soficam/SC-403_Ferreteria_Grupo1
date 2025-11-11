package ferreteria.service;

import ferreteria.domain.Pedido;
import ferreteria.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepo;

    public List<Pedido> listarTodos() {
        return pedidoRepo.findAll();
    }

    public Optional<Pedido> buscarPorId(Integer id) {
        return pedidoRepo.findById(id);
    }

    public Pedido guardar(Pedido pedido) {
        return pedidoRepo.save(pedido);
    }
}
