package mx.uam.tsis.ejemplobackend.servicio;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.negocio.GrupoService;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;

@RestController
//@RequestMapping("/v1") //se puede agregar versionamientos http://localhost:8080/v1/alumnos
@Slf4j
public class GrupoController {
	
	@Autowired //Cableado entre el servicio y el controlador, no estamos definiendo un contructor ni usando new
	private GrupoService grupoService;
	
	
	/***Crea el grupo con una matrícula unica  autogenerada para evitar duplicado de llave primaria***/
	@ApiOperation(value="Crear grupo",notes="Permite crear un nuevo grupo.El Id es autogenerado.")//Documentacion de api
	@PostMapping(path ="/grupos",consumes = MediaType.APPLICATION_JSON_VALUE) //cuando se invoca post recibe datos en formato JSON
	
	public ResponseEntity <?> create(@RequestBody @Valid Grupo nuevoGrupo) {   //Regresa ResponseEntity //En el cuerpo de la peticion Recibe un objeto de tipo Alumno 
		log.info("Recibí llamada a create con "+nuevoGrupo);
		Grupo grupo = grupoService.create(nuevoGrupo);
		if (grupo != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(grupo);//regresa un status create
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede crear grupo.");
		}
	}
	
	/***OBTIENE A TODOS LOS GRUPOS REGISTRADOS***/
	@ApiOperation(value="Obtener todos los Grupos",notes="Obtiene la colección de los grupos registrados.")//Documentacion de api
	@GetMapping(path="/grupos", produces = MediaType.APPLICATION_JSON_VALUE) //en lugar de consumir lo produce el JSON
	
	public ResponseEntity<?> retrieveAll() {
		Iterable<Grupo> result = grupoService.retrieveAll();
		return ResponseEntity.status(HttpStatus.OK).body(result);//.body(alumnoRepository.values());
	}
	
	/***RECUPERAMOS UN ALUMNO A TRAVÉZ DE SU ID EN LA BASE DE DATOS***/
	@ApiOperation(value="Obtener Grupo por ID",notes="Obtiene el grupo registrado con el id autogenerado.")//Documentacion de api
	@GetMapping(path="/grupos/{id}", produces = MediaType.APPLICATION_JSON_VALUE) //en lugar de consumir lo produce el JSON
	public ResponseEntity<?> retrieve(@PathVariable("id") Integer id) {   //Path variable conecta la variable que recibe la cuncion con el path de GetMapping
		log.info("Buscando el grupo con ID: "+id);
		Optional<Grupo> grupo = grupoService.retrieveByID(id);
		if(grupo.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(grupo);//.body(alumno);
		}else{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	/***Actualiza los datos del grupo con el id dado***/
	@ApiOperation(value="Actualizar grupo",notes="Actualiza los datos del grupo con el id especificado.")//Documentacion de api
	@PutMapping(path="/grupos/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody @Valid Grupo grupoUpdate) {
		log.info("Actualizando al grupo: "+id);
		boolean updtGrupo = grupoService.update(id,grupoUpdate);
		if(updtGrupo != false) {
			log.info("Actualizado .......");
			return ResponseEntity.status(HttpStatus.OK).body(updtGrupo);//.body(alumno);
		}else{
			log.info("NO LO  .......");
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
	} 
	
	/***Elimina el grupo con el id dado***/
	@ApiOperation(value="Elimina grupo",notes="Elimina el grupo con el id especificado.")//Documentacion de api
	@DeleteMapping(path="/grupos/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
		log.info("Eliminando Grupo");
		Optional<Grupo> deleteGrupo = grupoService.delete(id);
		if(deleteGrupo.isPresent()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Se ha eliminado el alumno: "+deleteGrupo);//.body(alumno);
		}else{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	/**
	 * POST /grupos/{id}/alumnos?matricula=1234
	 * 
	 * */
	
	@PostMapping(path ="/grupos/{id}/alumnos",produces = MediaType.APPLICATION_JSON_VALUE) //cuando se invoca post recibe datos en formato JSON
	public ResponseEntity<?> addStundenToGroup(
			@PathVariable("id") Integer id,
			@RequestParam("matricula")Integer matricula){
		boolean result = grupoService.addStudentToGroup(id, matricula);
		if(result) {
			return ResponseEntity.status(HttpStatus.OK).build();//.body(alumnoRepository.values());
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();//.body(alumnoRepository.values());
		}
	}
}
