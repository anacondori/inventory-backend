package com.company.inventory.response;

import lombok.Getter;
import lombok.Setter;

//Respuesta del servicio de Categoria
@Getter
@Setter
public class CategoryResponseRest extends ResponseRest{
	private CategoryResponse categoryResponse = new CategoryResponse();
}
