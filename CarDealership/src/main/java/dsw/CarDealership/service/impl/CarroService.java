package dsw.CarDealership.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import dsw.CarDealership.dao.CarroDAO;
import dsw.CarDealership.domain.Carro;
import dsw.CarDealership.service.spec.ICarroService;

@Service
@Transactional(readOnly = false)
public class CarroService implements ICarroService{
	@Autowired
	CarroDAO dao;

	public MultipartFile _createMultipartFile(File file) {

		DiskFileItem fileItem = new DiskFileItem("fotos", "image/*", false, file.getName(),
				(int) file.length(), file.getParentFile());
		fileItem.getOutputStream();
		MultipartFile multipartFile = new CommonsMultipartFile((FileItem) fileItem);
		return multipartFile;
	}
	
	public Carro salvar(Carro carro) {
		dao.save(carro);
		carro = dao.findByChassi(carro.getChassi());
		return carro;
	}
	
	public void excluir(Long id) {
		dao.deleteById(id);
	}
	

	@Transactional(readOnly = true)
	public Carro buscarPorId(Long id) {
		return dao.findById(id.longValue());
	}

	@Transactional(readOnly = true)
	public List<Carro> buscarTodos() {
		return dao.findAll();
	}
}
