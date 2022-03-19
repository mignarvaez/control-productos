package com.gestion.productos.servicio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gestion.productos.entidades.Producto;

/**
 * Interfaz que representa el servicio de productos
 * 
 * @author Migue
 *
 */
public interface ProductoService {

	/**
	 * Se declara el método que retorna los productos
	 * 
	 * @return
	 */
	public List<Producto> findAll();

	/**
	 * Método que retorna de manera paginada los productos
	 * 
	 * @param pageable
	 * @return
	 */
	public Page<Producto> findAll(Pageable pageable);

	/**
	 * Método que permite el guardado de un producto
	 * 
	 * @param producto
	 */
	public void save(Producto producto);

	/**
	 * Método que busca un producto según su id
	 * 
	 * @param id
	 * @return
	 */
	public Producto findOne(Long id);

	/**
	 * Método que permite borrar un producto según su id
	 * 
	 * @param id
	 */
	public void delete(Long id);
}
