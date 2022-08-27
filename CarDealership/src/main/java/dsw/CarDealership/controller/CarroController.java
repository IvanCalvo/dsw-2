package dsw.CarDealership.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.engine.AttributeName;

import dsw.CarDealership.domain.Usuario;
import dsw.CarDealership.imageUtils.FileGetterUtil;
import dsw.CarDealership.imageUtils.FileUploadUtil;
import dsw.CarDealership.security.UsuarioDetails;
import dsw.CarDealership.domain.Carro;
import dsw.CarDealership.domain.Loja;
import dsw.CarDealership.service.spec.ICarroService;
import dsw.CarDealership.service.spec.ILojaService;

@Controller
@RequestMapping("/carros")
public class CarroController {
	public static String uploadDirectory = System.getProperty("user.dir")+"/upload";

	@Autowired
	private ICarroService carroService;

	@Autowired
	private ILojaService lojaService;
	
	
	@GetMapping("/cadastrar")
	public String cadastrar(Carro carro, ModelMap model) {
		carro.setLoja(lojaService.buscarPorEmail(getUsuario().getEmail()));
		model.addAttribute("carro", carro);
		return "carro/cadastro";
	}
	
	private Usuario getUsuario() {
		UsuarioDetails usuarioDetails = (UsuarioDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return usuarioDetails.getUsuario();
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("carros", carroService.buscarTodos());
		return "carro/lista";
	}
	
	@RequestMapping(path = "/salvar", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public String salvar(@ModelAttribute Carro carro, BindingResult result, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return "carro/cadastro";
		}

		List<MultipartFile> images = carro.getFotos();

		for(MultipartFile foto :images) {
			Path nomeDoArquivoEPath = Paths.get(uploadDirectory+"/"+carro.getId().toString(), foto.getOriginalFilename() );
			File imageFile = new File(nomeDoArquivoEPath.toString());
			try {
				foto.transferTo(imageFile);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		carroService.salvar(carro);

		attr.addFlashAttribute("sucess", "carro.create.sucess");
		if(this.getUsuario().getPapel().equals("LOJA")) {
			return "redirect:/lojas/listarCarro";
		}
		return "redirect:/carros/listar";
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		Carro carro = carroService.buscarPorId(id);
		model.addAttribute("carro", carro);
		model.addAttribute("loja", carro.getLoja());
		return "carro/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Carro carro, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "carro/cadastro";
		}

		List<MultipartFile> images = carro.getFotos();

		for(MultipartFile foto :images) {
			Path nomeDoArquivoEPath = Paths.get(uploadDirectory+"/"+carro.getId().toString(), foto.getOriginalFilename() );
			File imageFile = new File(nomeDoArquivoEPath.toString());
			try {
				foto.transferTo(imageFile);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		carroService.salvar(carro);
		attr.addFlashAttribute("sucess", "carro.edit.sucess");
		if(this.getUsuario().getPapel().equals("LOJA")) {
			return "redirect:/lojas/listarCarro";
		}
		return "redirect:/carros/listar";
		
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		carroService.excluir(id);
		attr.addFlashAttribute("sucess", "carro.delete.sucess");
		if(this.getUsuario().getPapel().equals("LOJA")) {
			return "redirect:/lojas/listarCarro";
		}
		return "redirect:/carros/listar";
	}

	@ModelAttribute("lojas")
	public List<Loja> listaLojas() {
		return lojaService.buscarTodos();
	}
}
