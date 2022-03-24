package com.gestion.productos.util.reportes;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.gestion.productos.entidades.Producto;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Clase que representa el exportador a PDF del programa
 * 
 * @author Migue
 *
 */
public class ProductoExporterPDF {

	// -------------------------------------------------------------
	// Atributos
	// -------------------------------------------------------------

	/**
	 * La lista de productos
	 */
	private List<Producto> productos;

	// -------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------

	/**
	 * El constructor de la clase
	 * 
	 * @param producto
	 */
	public ProductoExporterPDF(List<Producto> productos) {
		super();
		this.productos = productos;
	}

	// -------------------------------------------------------------
	// Métodos
	// -------------------------------------------------------------

	/**
	 * Método que escribe la cabecera del PDF
	 * 
	 * @param tabla
	 */
	private void escribirCabeceraTabla(PdfPTable tabla) {

		// Celda
		PdfPCell celda = new PdfPCell();
		// Se pone color y configura la celda
		celda.setBackgroundColor(Color.BLUE);
		celda.setPadding(5);

		// Se configura la fuente a usar
		Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
		fuente.setColor(Color.WHITE);

		// Parte del encabezado
		celda.setPhrase(new Phrase("ID", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Nombre", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Categoria", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Presentación", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Fabricante", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Importado", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Cant.", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Precio", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Fecha ingreso sistema", fuente));
		tabla.addCell(celda);
	}

	/**
	 * Método que escribe los datos de los productos
	 * 
	 * @param tabla
	 */
	private void escribirDatosTabla(PdfPTable tabla) {

		for (Producto producto : productos) {
			tabla.addCell(String.valueOf(producto.getId()));
			tabla.addCell(producto.getNombre());
			tabla.addCell(producto.getCategoria());
			tabla.addCell(producto.getPresentacion());
			tabla.addCell(producto.getFabricante());
			tabla.addCell(producto.isEsImportado() ? "Sí" : "No");
			tabla.addCell(String.valueOf(producto.getCantidad()));
			tabla.addCell(String.valueOf(producto.getPrecio()));
			tabla.addCell(producto.getFechaIngreso().toString());
		}
	}

	/**
	 * Método que genera el pdf para exportar
	 * 
	 * @param response
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void exportar(HttpServletResponse response) throws DocumentException, IOException {

		// Especifica el documento y su tipo
		Document documento = new Document(PageSize.A4);

		// Obtiene una instancia del documento con base en el output stream de la
		// respuesta
		PdfWriter.getInstance(documento, response.getOutputStream());

		// Se trabaja y configura el documento
		documento.open();

		Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fuente.setColor(Color.BLUE);
		fuente.setSize(18);

		Paragraph titulo = new Paragraph("LISTA DE PRODUCTOS", fuente);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		documento.add(titulo);

		// El número de columnas de la tabla
		PdfPTable tabla = new PdfPTable(9);

		// Se configura el ancho y espaciado
		tabla.setWidthPercentage(100);
		tabla.setSpacingBefore(15);

		// Se configura el ancho por columna
		tabla.setWidths(new float[] { 1f, 2.5f, 2.4f, 4.3f, 2.6f, 2.5f, 1.5f, 2.2f, 2.9f });

		tabla.setWidthPercentage(110);
		
		// Se escribe la cabezera y los datos, se añade la tabla y se cierra el documento
		escribirCabeceraTabla(tabla);
		escribirDatosTabla(tabla);
		documento.add(tabla);
		documento.close();
	}
}
