package mx.uam.tsis.ejemplobackend.servicio;

import java.util.HashMap;
import java.util.Map;

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
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

/**
 * Controlador para API rest
 * @author Gerson-VH*/

@RestController
@Slf4j
public class AlumnoController {
	
	//La base de datos"En memoria"
	private Map<Integer, Alumno> alumnoRepository = new HashMap<>();
	
	

	
	@PostMapping(path ="/alumnos",consumes = MediaType.APPLICATION_JSON_VALUE) //cuando se invoca post recibe datos en formato JSON
	public ResponseEntity <?> create(@RequestBody Alumno nuevoAlumno) {   //Regresa ResponseEntity //En el cuerpo de la peticion Recibe un objeto de tipo Alumno 
		log.info("Recibí llamada a create con "+nuevoAlumno);
		alumnoRepository.put(nuevoAlumno.getMatricula(), nuevoAlumno);
		
		return ResponseEntity.status(HttpStatus.CREATED).build()/*Contruya el objeto*/; //regresa un status create
	}
	@GetMapping(path="/alumnos", produces = MediaType.APPLICATION_JSON_VALUE) //en lugar de consumir lo produce el JSON
	public ResponseEntity<?> retrieveAll() {
		return ResponseEntity.status(HttpStatus.OK).body(alumnoRepository.values());
	}
	/***RECUPERAMOS UN ALUMNO A TRAVÉZ DE SU ID EN LA BASE DE DATOS***/
	@GetMapping(path="/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE) //en lugar de consumir lo produce el JSON
	public ResponseEntity<?> retrieve(@PathVariable("matricula") Integer matricula) {   //Path variable conecta la variable que recibe la cuncion con el path de GetMapping
		log.info("Buscando al alumno con matrícula "+matricula);
		Alumno alumno = alumnoRepository.get(matricula);
		if(alumno != null) {
			return ResponseEntity.status(HttpStatus.OK).body(alumno);
		}else{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	@PutMapping(path="/alumnos/{matricula}")
	public ResponseEntity<?> update(@PathVariable("matricula") Integer matricula, @RequestBody Alumno alumnoUpdate) {
		Alumno alumno = alumnoRepository.get(matricula);
		//log.info("Estoy en update: "+matricula);
		if(alumno != null) {
			alumnoRepository.replace(matricula, alumnoUpdate);
			return ResponseEntity.status(HttpStatus.OK).body(alumnoUpdate);
		}else{
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
	}
	@DeleteMapping(path="/alumnos/{matricula}")
	public ResponseEntity<?> delete(@PathVariable("matricula") Integer matricula) {
		Alumno alumno = alumnoRepository.get(matricula);
		//log.info("Estoy en update: "+matricula);
		if(alumno != null) {
			alumnoRepository.remove(matricula);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}else{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
}
