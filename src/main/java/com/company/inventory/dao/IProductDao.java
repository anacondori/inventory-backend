package com.company.inventory.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.company.inventory.model.Product;



public interface IProductDao extends CrudRepository<Product, Long>{

	//por defecto CrudRepository SOLO busca por ID
	//para buscar por nombre hay q implementar el siguiente codigo
	//consulta compleja
	@Query("select p from Product p where p.name like %?1%")
	List<Product> findByNameLike(String name);
	
	//https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html#jpa.query-methods.query-creation
	//para consultas simples
	List<Product> findByNameContainingIgnoreCase(String name);
}
