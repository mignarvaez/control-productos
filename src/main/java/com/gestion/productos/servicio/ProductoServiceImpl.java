package com.gestion.productos.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.productos.entidades.Producto;
import com.gestion.productos.repositorios.ProductoRepository;

/**
 * Implementación del servicio de empleados Tener en cuenta que no se va a crear
 * una API rest, sino que se trabajara con un controlador tradicional.
 * 
 * @author Migue
 *
 */
@Service
public class ProductoServiceImpl implements ProductoService {

	// -------------------------------------------------------------
	// Atributos
	// -------------------------------------------------------------

	/**
	 * Vincula el repositorio
	 */
	@Autowired
	private ProductoRepository productoRepository;

	// -------------------------------------------------------------
	// Métodos
	// -------------------------------------------------------------

	/**
	 * Retorna la lista de productos. Se indica con la anotación Transactional
	 * (permite commit rollback) que es solo de lectura
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Producto> findAll() {
		return (List<Producto>) productoRepository.findAll();
	}

	/**
	 * Retorna la lista de productos paginada.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Producto> findAll(Pageable pageable) {
		return productoRepository.findAll(pageable);
	}

	/**
	 * Permite guardar un producto es transaccional y permite operaciones de
	 * escritura lectura
	 */
	@Override
	@Transactional
	public void save(Producto producto) {
		productoRepository.save(producto);
	}

	/**
	 * Busca un producto según id, es transaccional de solo lectura.
	 */
	@Override
	@Transactional(readOnly = true)
	public Producto findOne(Long id) {

		// En caso de no encontrar un producto por el id suministrado retorna null
		return productoRepository.findById(id).orElse(null);
	}

	/**
	 * Elimina un producto según id.
	 */
	@Override
	@Transactional
	public void delete(Long id) {
		productoRepository.deleteById(id);
	}

}
