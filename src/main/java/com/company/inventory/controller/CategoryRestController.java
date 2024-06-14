package com.company.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.services.ICategoryService;

//punto de entrada para q cualquier aplicacion externa lo pueda consumir
@RestController
@RequestMapping("/api/v1")
public class CategoryRestController {
	
	@Autowired
	private ICategoryService service;//tipo de la interfaz

	//punta de entrada para todas las categorias.Metodo en particular
	/**
	 * get all categories
	 * @return
	 */
	@GetMapping("/categories")
	public ResponseEntity<CategoryResponseRest> searchCategories(){
		ResponseEntity<CategoryResponseRest> response = service.search();
		return response;
	}
	
	/**
	 * get categories by id
	 * @param id
	 * @return
	 */
	@GetMapping("/categories/{id}")
	public ResponseEntity<CategoryResponseRest> searchCategoriesById(@PathVariable Long id){
		ResponseEntity<CategoryResponseRest> response = service.searchById(id);
		return response;
	}
	
}
