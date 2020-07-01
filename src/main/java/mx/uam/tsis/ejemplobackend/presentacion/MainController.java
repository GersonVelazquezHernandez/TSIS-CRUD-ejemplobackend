package mx.uam.tsis.ejemplobackend.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Controlador web para la capa de presentacion
 * @author Gerson-VH**/

@Controller //Controlador de la capa de presentación 
@Slf4j
public class MainController {
	
	@ApiOperation(value="Recupera el index",notes="Obtiene la URL raíz de la aplicación.")//Documentacion de api
	@GetMapping("/") //Mapea la raiz del programa
	public String index() {
		log.info("*********EN INDEX**********");
		return "index";
	}
	
	@RequestMapping("/ejemplo")
	@ResponseBody
	public String ejemplo() {
		return "Esto es un ejemplo";
	}
}
