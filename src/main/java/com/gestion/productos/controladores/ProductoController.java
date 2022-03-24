package com.gestion.productos.controladores;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gestion.productos.entidades.Producto;
import com.gestion.productos.servicio.ProductoService;
import com.gestion.productos.util.paginacion.PageRender;
import com.gestion.productos.util.reportes.ProductoExporterExcel;
import com.gestion.productos.util.reportes.ProductoExporterPDF;
import com.lowagie.text.DocumentException;

/**
 * Clase que representa al controlador
 * 
 * @author Migue
 *
 */
@Controller
public class ProductoController {
	
	// -------------------------------------------------------------
	// Atributos
	// -------------------------------------------------------------
	
	/**
	 * El servicio del producto inyectado
	 */
	@Autowired
	private ProductoService productoService;
	
	// -------------------------------------------------------------
	// Métodos
	// -------------------------------------------------------------
	
	/**
	 * Lista los productos Con GetMapping se indica que respondera a una petición
	 * get en las rutas suministradas.
	 * 
	 * @param page   la pagina
	 * @param modelo el modelo
	 * @return la lista de productos
	 */
	@GetMapping({ "/", "/listar", "" })
	public String listarProductos(@RequestParam(name = "page", defaultValue = "0") int page, Model modelo) {
		// Se indica que nos muestre 5 elementos por pagina.
		Pageable pageRequest = PageRequest.of(page, 5);
		// Se pasa el pageRequest indicando cuantas paginas va a mostrar.
		Page<Producto> productos = productoService.findAll(pageRequest);
		// Se genera el pagerender con la ruta y los productos
		PageRender<Producto> pageRender = new PageRender<>("/listar", productos);

		// Se pasa al modelo los atributos que seranmostrados por thymeleaf
		modelo.addAttribute("titulo", "Listado de productos");
		modelo.addAttribute("productos", productos);
		modelo.addAttribute("page", pageRender);
		return "listar";
	}

	/**
	 * Método que permite visualizar los detalles de un producto
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/ver/{id}")
	public String verDetallesDelProducto(@PathVariable(value = "id") Long id, Map<String, Object> modelo,
			RedirectAttributes flash) {

		Producto producto = productoService.findOne(id);
		if (producto == null) {
			// Si no se encuentra el producto muestra un error y redirecciona al listar
			flash.addFlashAttribute("error", "El producto no existe en la base de datos");
			return "redirect:/listar";
		}
		// Si se encuentra el producto
		modelo.put("producto", producto);
		modelo.put("titulo", "Detalles del producto " + producto.getNombre());

		return "ver";

	}
	
	/**
	 * Método que muestra el formulario para registrar nuevo producto
	 * @param modelo
	 * @return
	 */
	@GetMapping("/form")
	public String mostrarFormularioRegistrarProducto(Map<String, Object> modelo) {
		Producto producto = new Producto();
		modelo.put("producto",producto);
		modelo.put("titulo", "Registro de productos");
		return "form";
	}
	
	/**
	 * Método para guardar un producto, el cuál valida y realiza un binding de lo que se le pase.
	 * @param producto
	 * @param result
	 * @param modelo
	 * @param flash
	 * @return
	 */
	@PostMapping("/form")
	public String guardarProducto(@Valid Producto producto, BindingResult result, Model modelo, RedirectAttributes flash, SessionStatus status) {
		// Si se presentan errores
		if (result.hasErrors()) {
			modelo.addAttribute("titulo","Registro de producto");
			return "form";
		}
		//Si el id es diferente de nulo significa que se edito, caso contrario se creo
		String mensaje = producto.getId() != null ? "El producto ha sido editado" : "El producto ha sido registrado";
		
		//Se guarda el producto, se comleta la sesion limpiando variables de sesion, se manda un mensaje y se redirecciona
		productoService.save(producto);
		status.setComplete();
		flash.addFlashAttribute("success",mensaje);
		return "redirect:listar";
		
	}
	
	/**
	 * Método que lleva al formulario de editar un producto
	 * @param id
	 * @param modelo
	 * @param flash
	 * @return
	 */
	@GetMapping("/form/{id}")
	public String editarProducto(@PathVariable(value = "id")Long id, Map<String, Object> modelo, RedirectAttributes flash  ) {
	
		Producto producto = null;
		if(id > 0) {
			producto = productoService.findOne(id);
			if(producto == null) {
				flash.addFlashAttribute("error", "el id del producto no existe en la base de datos");
				return "redirect:/listar";
			}
		}
		else
		{
			flash.addFlashAttribute("error", "El id del producto no puede ser cero");
			return "redirect:/listar";
		}
		
		modelo.put("producto",producto);
		modelo.put("titulo", "Edición de producto");
		return "form";
	}
	
	/**
	 * Método que elimina un producto según su id.
	 * @param id
	 * @param flash
	 * @return
	 */
	@GetMapping("/eliminar/{id}")
	public String elminarProducto(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			productoService.delete(id);
			flash.addFlashAttribute("success","Producto eliminado con exito");
		}
		return "redirect:/listar";
	}
	
	/**
	 * Método encargado de exportar a PDF el listado de productos
	 * @param response
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	@GetMapping("/exportarPDF")
	public void exportarListadoProductosPDF(HttpServletResponse response) throws DocumentException, IOException
	{	
		// Se especifica el tipo de respuesta
		response.setContentType("application/pdf");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyy-MM-dd_HH:mm:ss");
		
		// Se agrega el encabezado y el valor, información para la generacion del archivo
		String fechaActual = dateFormatter.format(new Date());
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Productos_"+fechaActual+".pdf";
		response.setHeader(cabecera, valor);
		
		// Se obtienen todos los productos y se pasan al exporter junto con la respuesta
		List<Producto> productos = productoService.findAll();
		ProductoExporterPDF exporter = new ProductoExporterPDF(productos);
		exporter.exportar(response);
	}
	
	/**
	 * Método encargado de exportar a Excel el listado de productos
	 * @param response
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	@GetMapping("/exportarExcel")
	public void exportarListadoProductosExcel(HttpServletResponse response) throws DocumentException, IOException
	{	
		// Se especifica el tipo de respuesta
		response.setContentType("application/octec-stream");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyy-MM-dd_HH:mm:ss");
		
		// Se agrega el encabezado y el valor, información para la generacion del archivo
		String fechaActual = dateFormatter.format(new Date());
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Productos_"+fechaActual+".xlsx";
		response.setHeader(cabecera, valor);
		
		// Se obtienen todos los productos y se pasan al exporter junto con la respuesta
		List<Producto> productos = productoService.findAll();
		ProductoExporterExcel exporter = new ProductoExporterExcel(productos);
		exporter.exportar(response);
	}
}
