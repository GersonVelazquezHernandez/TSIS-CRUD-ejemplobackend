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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.negocio.AlumnoService;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

/**
 * Controlador para API rest
 * @author Gerson-VH*/

@RestController
//@RequestMapping("/v1") //se puede agregar versionamientos http://localhost:8080/v1/alumnos
@Slf4j
public class AlumnoController {
	
	@Autowired //Cableado entre el servicio y el controlador, no estamos definiendo un contructor ni usando new
	private AlumnoService alumnoService;
	/***Crea el alumno con una matrícula unica si ya está registrado no se podrá crear al alumno***/
	@ApiOperation(value="Crear al alumno",notes="Permite crear a un alumno, la matrícula debe ser única.")//Documentacion de api
	@PostMapping(path ="/alumnos",consumes = MediaType.APPLICATION_JSON_VALUE) //cuando se invoca post recibe datos en formato JSON
	public ResponseEntity <?> create(@RequestBody @Valid Alumno nuevoAlumno) {   //Regresa ResponseEntity //En el cuerpo de la peticion Recibe un objeto de tipo Alumno 
		log.info("Recibí llamada a create con "+nuevoAlumno);
		Alumno alumno = alumnoService.create(nuevoAlumno);
		if (alumno != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(alumno);//regresa un status create
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede crear alumno.");
		}
	}
	
	/***OBTIENE A TODOS LOS ALUMNOS REGISTRADOS***/
	@ApiOperation(value="Obtener Alumnos",notes="Obtiene la colección de los alumnos registrados.")//Documentacion de api
	@GetMapping(path="/alumnos", produces = MediaType.APPLICATION_JSON_VALUE) //en lugar de consumir lo produce el JSON
	public ResponseEntity<?> retrieveAll() {
		Iterable <Alumno> result = alumnoService.retrieveAll();
		return ResponseEntity.status(HttpStatus.OK).body(result);//.body(alumnoRepository.values());
	}
	
	
	/***RECUPERAMOS UN ALUMNO A TRAVÉZ DE SU ID EN LA BASE DE DATOS***/
	@ApiOperation(value="Obtener Alumno por ID",notes="Obtiene al alumno registrado con la matrícula dada.")//Documentacion de api
	@GetMapping(path="/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE) //en lugar de consumir lo produce el JSON
	public ResponseEntity<?> retrieve(@PathVariable("matricula") Integer matricula) {   //Path variable conecta la variable que recibe la cuncion con el path de GetMapping
		log.info("Buscando al alumno con matrícula "+matricula);
		Optional<Alumno> alumno = alumnoService.retrieveByID(matricula);
		if(alumno.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(alumno);//.body(alumno);
		}else{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	/***Actualiza los datos del alumno con la matricula dada***/
	@ApiOperation(value="Actualizar alumno",notes="Actualiza los datos del alumno con la matrícula dada.")//Documentacion de api
	@PutMapping(path="/alumnos/{matricula}")
	public ResponseEntity<?> update(@PathVariable("matricula") Integer matricula, @RequestBody @Valid Alumno alumnoUpdate) {
		log.info("Actualizando al alumno: "+matricula);
		Alumno updtAlumno = alumnoService.update(matricula,alumnoUpdate);
		if(updtAlumno != null) {
			return ResponseEntity.status(HttpStatus.OK).body(updtAlumno);//.body(alumno);
		}else{
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
	}
	
	/***Elimina al alumno con matricula dada***/
	@ApiOperation(value="Elimina alumno",notes="Elimina al alumno con la matrícula dada.")//Documentacion de api
	@DeleteMapping(path="/alumnos/{matricula}")
	public ResponseEntity<?> delete(@PathVariable("matricula") Integer matricula) {
		log.info("Eliminando Alumno");
		Optional<Alumno> deleteAlumno = alumnoService.delete(matricula);
		if(deleteAlumno.isPresent()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Se ha eliminado el alumno: "+deleteAlumno);//.body(alumno);
		}else{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
}
