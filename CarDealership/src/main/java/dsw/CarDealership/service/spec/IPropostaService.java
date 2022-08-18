package dsw.CarDealership.service.spec;

import java.util.List;

import dsw.CarDealership.domain.Proposta;
import dsw.CarDealership.domain.Usuario;

public interface IPropostaService {
	Proposta buscarPorId(Long id);
	
	List<Proposta> buscarTodos();

	List<Proposta> buscarTodosPorUsuario(Usuario u);
	
	void salvar(Proposta proposta);
	
	void excluir(Long id);
}
