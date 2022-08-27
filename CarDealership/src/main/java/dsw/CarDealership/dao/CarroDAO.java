package dsw.CarDealership.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import dsw.CarDealership.domain.Carro;

@SuppressWarnings("unchecked")
public interface CarroDAO extends CrudRepository<Carro, Long>{
	
	Carro findById(long id);
	
	List<Carro> findAll();
	
	Carro save(Carro c);
	
	void deleteById(Long id);

	@Query("SELECT c FROM Carro c WHERE c.chassi = :chassi")
	Carro findByChassi(@Param("chassi") String chassi);
}

