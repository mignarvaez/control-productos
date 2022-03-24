package com.gestion.productos.entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Clase que representa una entidad asociada al producto Con las etiquetas se
 * indica la estructura que tendrá la clase con respecto al modelo de la base de
 * datos
 * 
 * @author Migue
 *
 */
@Entity
@Table(name = "productos")
public class Producto {

	// -------------------------------------------------------------
	// Atributos
	// -------------------------------------------------------------

	/**
	 * El identificador del producto, con una etiqueta ID y con una estrategia
	 * Es de tipo Long para que pueda usarse en el repositorio como ID.
	 * identity (autoincrementable)
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * El nombre del producto
	 */
	@NotEmpty
	private String nombre;

	/**
	 * La categoria del producto
	 */
	@NotEmpty
	private String categoria;

	/**
	 * La presentación del producto
	 */
	@NotEmpty
	private String presentacion;

	/**
	 * El fabricante del producto
	 */
	@NotEmpty
	private String fabricante;

	/**
	 * La representacion sobre si el producto es importado o no
	 */
	@NotNull
	private boolean esImportado;

	/**
	 * La cantidad en stock del producto
	 */
	@NotNull
	private int cantidad;

	/**
	 * El precio unitario del producto
	 */
	@NotNull
	private double precio;

	/**
	 * La fecha de ingreso al sistema, se agrega anotacion de tipo date y formato
	 * de fecha
	 */
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaIngreso;

	// -------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------

	/**
	 * El constructor de la clase.
	 */
	public Producto() {
		super();
	}

	// -------------------------------------------------------------
	// Métodos
	// -------------------------------------------------------------

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getPresentacion() {
		return presentacion;
	}

	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public boolean isEsImportado() {
		return esImportado;
	}

	public void setEsImportado(boolean esImportado) {
		this.esImportado = esImportado;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

}
