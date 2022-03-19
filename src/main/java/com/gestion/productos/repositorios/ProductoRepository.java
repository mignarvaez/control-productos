package com.gestion.productos.repositorios;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.gestion.productos.entidades.Producto;

/**
 * Interfaz que representa el repositorio de productos
 * Hereda de Paging and Sorting Repository
 * @author Migue
 *
 */
public interface ProductoRepository extends PagingAndSortingRepository<Producto, Long> {

}
