package com.gestion.productos.util.reportes;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gestion.productos.entidades.Producto;

/**
 * Clase usada para exportar en excel la lista de productos
 * @author Migue
 *
 */
public class ProductoExporterExcel {
	
	// -------------------------------------------------------------
	// Atributos
	// -------------------------------------------------------------
	
	/**
	 * El libro a usar
	 */
	private XSSFWorkbook libro;
	
	/**
	 * La hoja
	 */
	private XSSFSheet hoja;
	
	/**
	 * La lista de productos
	 */
	private List<Producto> productos;
	
	// -------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------

	/**
	 * El constructor de la clase.
	 */
	public ProductoExporterExcel(List<Producto> productos) {
		
		//Inicializa la lista de productos, el libro y la hoja
		this.productos = productos;
		libro = new XSSFWorkbook();
		hoja = libro.createSheet("Productos");
	}
	
	/**
	 * Método que escribe la cabecera de la tabla
	 */
	private void escribirCabeceraTabla() {
		
		// La fila 0
		Row fila = hoja.createRow(0);
		
		//El estilo de celda
		CellStyle estilo = libro.createCellStyle();
		
		//La fuente usada en la celda y que se asigna al estilo
		XSSFFont fuente = libro.createFont();
		fuente.setBold(true);
		fuente.setFontHeight(16);
		estilo.setFont(fuente);
		
		// Las celdas del encabezado
		Cell celda = fila.createCell(0);
		celda.setCellValue("ID");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(1);
		celda.setCellValue("Nombre");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(2);
		celda.setCellValue("Categoria");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(3);
		celda.setCellValue("Presentacion");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(4);
		celda.setCellValue("Fabricante");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(5);
		celda.setCellValue("Importado");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(6);
		celda.setCellValue("Cantidad");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(7);
		celda.setCellValue("Precio");
		celda.setCellStyle(estilo);
		
		
		celda = fila.createCell(8);
		celda.setCellValue("Fecha Ingreso");
		celda.setCellStyle(estilo);
	}
	
	/**
	 * Método que escribe los datos de productos en la hoja
	 */
	private void escribirDatosTabla() {
		
		//La fila en la que comienza
		int numeroFilas = 1;
		
		//El estilo de celdas
		CellStyle estilo = libro.createCellStyle();
		
		//La fuente usada en las celdas y que se asignan al estilo
		XSSFFont fuente = libro.createFont();
		fuente.setFontHeight(14);
		estilo.setFont(fuente);
		
		for (Producto producto : productos) {
			
			// Crea las filas según se recorran los productos
			Row fila = hoja.createRow(numeroFilas ++);
			
			//Agrega la informacion de cada producto en las celdas correspondientes, ajustando cada celda de manera automatica y aplicando el estilo
			Cell celda = fila.createCell(0);
			celda.setCellValue(producto.getId());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(1);
			celda.setCellValue(producto.getNombre());
			hoja.autoSizeColumn(1);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(2);
			celda.setCellValue(producto.getCategoria());
			hoja.autoSizeColumn(2);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(3);
			celda.setCellValue(producto.getPresentacion());
			hoja.autoSizeColumn(3);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(4);
			celda.setCellValue(producto.getFabricante());
			hoja.autoSizeColumn(4);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(5);
			celda.setCellValue(producto.isEsImportado() ? "Si" : "No");
			hoja.autoSizeColumn(5);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(6);
			celda.setCellValue(producto.getCantidad());
			hoja.autoSizeColumn(6);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(7);
			celda.setCellValue(producto.getPrecio());
			hoja.autoSizeColumn(7);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(8);
			celda.setCellValue(producto.getFechaIngreso().toString());
			hoja.autoSizeColumn(8);
			celda.setCellStyle(estilo);
			
		}
		
	}
	
	/**
	 * Método que escribe los datos y exporta el archivo excel
	 * @param response
	 * @throws IOException 
	 */
	public void exportar (HttpServletResponse response) throws IOException {
		
		escribirCabeceraTabla();
		escribirDatosTabla();
		
		// Escribe la información con el output de la respuesta
		ServletOutputStream outPutStream = response.getOutputStream();
		libro.write(outPutStream);
		libro.close();
		outPutStream.close();
	}
}
