package com.company.inventory.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.inventory.dao.ICategoryDao;
import com.company.inventory.dao.IProductDao;
import com.company.inventory.model.Category;
import com.company.inventory.model.Product;
import com.company.inventory.response.ProductResponseRest;
import com.company.inventory.util.Util;



//detalle de los metodps definidos en la interface
@Service
public class ProductServiceImpl implements IProductService {
	
	//Definiendo variebles y constructor
	private ICategoryDao categoryDao;
	private IProductDao  productDao;
	public ProductServiceImpl(ICategoryDao categoryDao,
							  IProductDao  productDao) { 
		super();
		this.categoryDao = categoryDao;  //se inyecta en el objeto anterior privado
		this.productDao  = productDao;   //se inyecta en el objeto anterior privado
	}
	


	@Override
	@Transactional
	public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId) {
		
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		
		try {
			//search category to set in the product object
			Optional<Category> category = categoryDao.findById(categoryId); //busqueda de categoria
			if (category.isPresent()) {
				product.setCategory(category.get());
				
			} else {
				response.setMetadata("Respuesta KO", "-1", "Categoria no encontrada asociada al producto");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
			//save product
			Product productSaved = productDao.save(product);
			if (productSaved != null) { //si se guardo el producto
				list.add(productSaved);
				response.getProduct().setProduct(list);
				
				response.setMetadata("Respuesta OK", "00", "Producto guardado");
			} else {
				response.setMetadata("Respuesta KO", "-1", "Producto no guardado");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.BAD_REQUEST);
			}
			
			
		} catch(Exception e) {
			response.setMetadata("Respuesta KO", "-1", "Error al guardar producto");
			e.getStackTrace();
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);	
	}



	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<ProductResponseRest> searchById(Long id) {
		
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		
		try {
			//search product by id
			Optional<Product> product = productDao.findById(id);
			if (product.isPresent()) {
				//descomprimir la imagen q esta en base64
				byte[] imageDescompressed = Util.decompressZLib(product.get().getPicture()); 
				
				product.get().setPicture(imageDescompressed);
				list.add(product.get());
				response.getProduct().setProduct(list);
				
				response.setMetadata("Respuesta OK", "00", "Producto por ID encontrada");
				
			} else {
				response.setMetadata("Respuesta KO", "-1", "Producto no encontrado");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}
						
			
		} catch(Exception e) {
			response.setMetadata("Respuesta KO", "-1", "Error al buscar producto");
			e.getStackTrace();
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);	
	}


	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<ProductResponseRest> searchByName(String name) {
		
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		List<Product> listAux = new ArrayList<>();
		
		try {
			//search product by name
			listAux = productDao.findByNameContainingIgnoreCase(name);
			if (listAux.size() > 0) {
				//recorrer la lista para poder descomprimir la imagen
				listAux.stream().forEach( p -> {
					//descomprimir la imagen q esta en base64
					byte[] imageDescompressed = Util.decompressZLib(p.getPicture()); 
					
					p.setPicture(imageDescompressed);
					list.add(p);					
				});
				
				response.getProduct().setProduct(list);
				
				response.setMetadata("Respuesta OK", "00", "Producto(s) por Nombre encontrado");
				
			} else {
				response.setMetadata("Respuesta KO", "-1", "Producto(s) por Nombre no encontrado");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}
						
			
		} catch(Exception e) {
			response.setMetadata("Respuesta KO", "-1", "Error al buscar producto(s) por Nombre");
			e.getStackTrace();
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);	
	}


	@Override
	@Transactional
	public ResponseEntity<ProductResponseRest> deleteById(Long id) {
		
		ProductResponseRest response = new ProductResponseRest();
		
		try {
			//delete product by id
			productDao.deleteById(id);
			response.setMetadata("Respuesta OK", "00", "Producto por ID eliminado");
						
			
		} catch(Exception e) {
			response.setMetadata("Respuesta KO", "-1", "Error al eliminar producto");
			e.getStackTrace();
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);	
	}




	
	


}
