package dsw.CarDealership.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dsw.CarDealership.domain.Loja;
import dsw.CarDealership.service.spec.ILojaService;
import dsw.CarDealership.service.spec.ICarroService;

@Controller
@RequestMapping("/lojas")
public class LojaController {
	
	@Autowired
	private ILojaService service;
	
	@Autowired
	private ICarroService serviceCarro;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Loja loja) {
		return "loja/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("lojas", service.buscarTodos());
		return "loja/lista";
	}
	
	@GetMapping("/listarCarro")
	public String listarCarro(ModelMap model) {
		model.addAttribute("carros", serviceCarro.buscarTodos());
		return "loja/listaCarro";
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Loja loja, BindingResult result, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return "loja/cadastro";
		}
		
		service.salvar(loja);
		attr.addFlashAttribute("sucess", "loja.create.sucess");
		return "redirect:/lojas/listar";
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("loja", service.buscarPorId(id));
		return "loja/cadastro";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		service.excluir(id);
		attr.addFlashAttribute("sucess", "loja.delete.sucess");
		return "redirect:/lojas/listar";
	}
	
	@PostMapping("/editar")
	public String editar(@Valid Loja loja, BindingResult result, RedirectAttributes attr) {
		
		// Apenas rejeita se o problema não for com o CNPJ (CNPJ campo read-only) 
		
		if (result.getFieldErrorCount() > 1 || result.getFieldError("CNPJ") == null) {
			return "loja/cadastro";
		}

		service.salvar(loja);
		attr.addFlashAttribute("sucess", "loja.edit.sucess");
		return "redirect:/lojas/listar";
	}
	
}
