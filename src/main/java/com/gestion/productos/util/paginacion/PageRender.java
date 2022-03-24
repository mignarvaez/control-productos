package com.gestion.productos.util.paginacion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

/**
 * Renderiza una pagina, es una clase de tipo generico <T>: Indica que hara
 * referencia a una clase
 * 
 * @author Migue
 *
 */
public class PageRender<T> {

	/**
	 * La url
	 */
	private String url;

	/**
	 * La pagina
	 */
	private Page<T> page;

	/**
	 * El total de paginas
	 */
	private int totalPaginas;

	/**
	 * El número de elemtnos por pagina, ojo no corresponde a la cantidad de
	 * elementos que se veran por pagina, eso se hace desde el repositorio. Esta
	 * paginacion hace referencia a los botones de la parte superior o inferior que
	 * se indican en el paginador. (... 1 2 3 4 5 ...)
	 */
	private int numElementosPorPagina;

	/**
	 * Pagina actual en la que se encuentra el usuario
	 */
	private int paginaActual;

	/**
	 * Las paginas a renderizar.
	 */
	private List<PageItem> paginas;

	/**
	 * Constructor del page render
	 * 
	 * @param url  la url de la pagina
	 * @param page la pagina a renderizar
	 */
	public PageRender(String url, Page<T> page) {

		// Se inicializa url y pagina
		this.url = url;
		this.page = page;

		// Se inicializan las paginas
		this.paginas = new ArrayList<PageItem>();

		// Se especifica el número de elementos por página.
		numElementosPorPagina = 5;

		// Se obtiene el total de paginas
		totalPaginas = page.getTotalPages();

		// Se obtiene la página uno, como los indices inician desde 0 se agrega 1
		paginaActual = page.getNumber() + 1;

		/**
		 * Algoritmo que actualiza el paginador
		 */
		int desde, hasta;
		if (totalPaginas <= numElementosPorPagina) {
			desde = 1;
			hasta = totalPaginas;
		} else {
			if (paginaActual <= numElementosPorPagina / 2) {
				desde = 1;
				hasta = numElementosPorPagina;
			} else if (paginaActual >= totalPaginas - numElementosPorPagina / 2) {
				desde = totalPaginas - numElementosPorPagina + 1;
				hasta = numElementosPorPagina;
			} else {
				desde = paginaActual - numElementosPorPagina / 2;
				hasta = numElementosPorPagina;
			}
		}

		/**
		 * Crea las paginas de manera actualizada
		 */
		for (int i = 0; i < hasta; i++) {
			paginas.add(new PageItem(desde + i, paginaActual == desde + i));
		}

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getTotalPaginas() {
		return totalPaginas;
	}

	public void setTotalPaginas(int totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	public int getPaginaActual() {
		return paginaActual;
	}

	public void setPaginaActual(int paginaActual) {
		this.paginaActual = paginaActual;
	}

	public List<PageItem> getPaginas() {
		return paginas;
	}

	public void setPaginas(List<PageItem> paginas) {
		this.paginas = paginas;
	}

	/**
	 * Método que indica si es la ultima pagina
	 * 
	 * @return
	 */
	public boolean isLast() {
		return page.isLast();
	}

	/**
	 * Método que indica si la página tiene siguiente
	 * 
	 * @return
	 */
	public boolean isHasNext() {
		return page.hasNext();
	}

	/**
	 * Método que indica si la página tiene anterior
	 * @return
	 */
	public boolean isHasPrevious() {
		return page.hasPrevious();
	}
	
	/**
	 * Método que indica si se esta en la primera página
	 * 
	 * @return
	 */
	public boolean isFirst() {
		return page.isFirst();
	}

}
