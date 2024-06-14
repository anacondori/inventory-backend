package com.company.inventory.services;

import org.springframework.http.ResponseEntity;

import com.company.inventory.response.CategoryResponseRest;


//Esta Interfaz se declarar todos los metodos de la clase d servicio q luego se van a implementar
public interface ICategoryService {
	public ResponseEntity<CategoryResponseRest> search();
	public ResponseEntity<CategoryResponseRest> searchById(Long id);
}
