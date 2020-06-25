package mx.uam.tsis.ejemplobackend.servicio;
import java.util.List;

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
	
	@Autowired /*Cableado entre el servicio y el controlador, no estamos definiendo un contructor ni usando new*/
	private AlumnoService alumnoService;
	
	
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
	
	
	@GetMapping(path="/alumnos", produces = MediaType.APPLICATION_JSON_VALUE) //en lugar de consumir lo produce el JSON
	public ResponseEntity<?> retrieveAll() {
		List <Alumno> result = alumnoService.retrieveAll();
		return ResponseEntity.status(HttpStatus.OK).body(result);//.body(alumnoRepository.values());
	}
	
	
	/***RECUPERAMOS UN ALUMNO A TRAVÉZ DE SU ID EN LA BASE DE DATOS***/
	@GetMapping(path="/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE) //en lugar de consumir lo produce el JSON
	public ResponseEntity<?> retrieve(@PathVariable("matricula") Integer matricula) {   //Path variable conecta la variable que recibe la cuncion con el path de GetMapping
		log.info("Buscando al alumno con matrícula "+matricula);
		Alumno alumno = alumnoService.retrieveByID(matricula);
		if(alumno != null) {
			return ResponseEntity.status(HttpStatus.OK).body(alumno);//.body(alumno);
		}else{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	
	
	@PutMapping(path="/alumnos/{matricula}")
	public ResponseEntity<?> update(@PathVariable("matricula") Integer matricula, @RequestBody @Valid Alumno alumnoUpdate) {
		log.info("Actualizando al alumno: "+matricula);
		Alumno updtAlumno = alumnoService.update(matricula,alumnoUpdate);
		if(updtAlumno != null) {
			return ResponseEntity.status(HttpStatus.OK).body(updtAlumno);//.body(alumno);
		}else{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
	}
	
	
	@DeleteMapping(path="/alumnos/{matricula}")
	public ResponseEntity<?> delete(@PathVariable("matricula") Integer matricula) {
		log.info("Eliminando Alumno");
		Alumno deleteAlumno = alumnoService.delete(matricula);
		if(deleteAlumno != null) {
			return ResponseEntity.status(HttpStatus.OK).body("Se ha eliminado el alumno: "+deleteAlumno.getNombre());//.body(alumno);
		}else{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
}
