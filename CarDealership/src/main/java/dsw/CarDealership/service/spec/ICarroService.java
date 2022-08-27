package dsw.CarDealership.service.spec;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import dsw.CarDealership.domain.Carro;

public interface ICarroService {
	Carro buscarPorId(Long id);
	
	List<Carro> buscarTodos();
	
	Carro salvar(Carro carro);
	
	void excluir(Long id);

    MultipartFile _createMultipartFile(File file);
}
