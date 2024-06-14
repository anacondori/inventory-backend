package com.company.inventory.dao;

import org.springframework.data.repository.CrudRepository;

import com.company.inventory.model.Category;


//Esta Interfaz tiene todos los metodos para acceder a los datos
public interface ICategoryDao extends CrudRepository<Category, Long>{
	
}
