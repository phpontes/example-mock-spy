package com.paulo.example_mock_spy.services;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.paulo.example_mock_spy.dto.ProductDTO;
import com.paulo.example_mock_spy.entities.Product;
import com.paulo.example_mock_spy.repositories.ProductRepository;
import com.paulo.example_mock_spy.services.exceptions.InvalidDataException;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository repository;
	
	private Long existingId, nonExistingId;
	private Product product;
	private ProductDTO productDTO;
	
	@BeforeEach
	void setUp() throws Exception {
		
		existingId = 1L;
		nonExistingId = 2L;
		
		product = new Product(1L, "Preisteishon", 2000.0);
		productDTO = new ProductDTO(product);
		
		Mockito.when(repository.save(any())).thenReturn(product);
		
		Mockito.when(repository.getReferenceById(existingId)).thenReturn(product);
		Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
	}
	
	@Test
	public void insertShouldReturnProductDTOWhenValidData() {
		
		ProductService serviceSpy = Mockito.spy(service);
		Mockito.doNothing().when(serviceSpy).validateData(productDTO);
		
		ProductDTO result = serviceSpy.insert(productDTO);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getName(), "Preisteishon");
	}
	
	@Test
	public void insertShouldReturnInvalidDataExceptionWhenProductNameIsBlank() {
		
		productDTO.setName("");
		
		ProductService serviceSpy = Mockito.spy(service);
		Mockito.doThrow(InvalidDataException.class).when(serviceSpy).validateData(productDTO);
		
		Assertions.assertThrows(InvalidDataException.class, () -> {
			@SuppressWarnings("unused")
			ProductDTO result = serviceSpy.insert(productDTO);
		});
	}
	
	@Test
	public void insertShouldReturnInvalidDataExceptionWhenProductPriceIsNegativeOrZero() {
		
		productDTO.setPrice(-5.0);
		
		ProductService serviceSpy = Mockito.spy(service);
		Mockito.doThrow(InvalidDataException.class).when(serviceSpy).validateData(productDTO);
		
		Assertions.assertThrows(InvalidDataException.class, () -> {
			@SuppressWarnings("unused")
			ProductDTO result = serviceSpy.insert(productDTO);
		});
	}
	
	@Test
	public void updateShouldReturnProductDTOWhenIdExistsAndDataIsValid() {
		
		ProductService serviceSpy = Mockito.spy(service);
		Mockito.doNothing().when(serviceSpy).validateData(productDTO);
		
		ProductDTO result = serviceSpy.update(existingId, productDTO);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingId);
	}
	
	@Test
	public void updateShouldReturnInvalidDataExceptionWhenIdExistsAndProductNameIsBlank() {
		
		productDTO.setName("");
		
		ProductService serviceSpy = Mockito.spy(service);
		Mockito.doThrow(InvalidDataException.class).when(serviceSpy).validateData(productDTO);
		
		Assertions.assertThrows(InvalidDataException.class, () -> {
			@SuppressWarnings("unused")
			ProductDTO result = serviceSpy.update(existingId, productDTO);
		});
	}
	
	@Test
	public void updateShouldReturnInvalidDataExceptionWhenIdExistsAndProductPriceIsNegativeOrZero() {
		
		productDTO.setPrice(-5.0);
		
		ProductService serviceSpy = Mockito.spy(service);
		Mockito.doThrow(InvalidDataException.class).when(serviceSpy).validateData(productDTO);
		
		Assertions.assertThrows(InvalidDataException.class, () -> {
			@SuppressWarnings("unused")
			ProductDTO result = serviceSpy.update(existingId, productDTO);
		});
	}
}
