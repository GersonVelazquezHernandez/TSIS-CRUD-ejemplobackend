package mx.uam.tsis.ejemplobackend.negocio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.datos.AlumnoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
/*
 * Clase que contiene la lógica de negocio para el manejo de alumnos
 * 
 * @author Humberto Cervantez
 * 
 * */
@Slf4j
@Service
public class AlumnoService {
	@Autowired /*no hay ambigüedad porque solo hay unas anotaciones controller Service y Componenet*/
	private AlumnoRepository alumnoRepository;
	
	/*
	 * @param nuevoAlumno el alumno que desea crear en el sistema 
	 * @return el alumno que se acaba de crear si la creacion es exitosa, null de lo contrario*/
	@ApiOperation(value="Crear al alumno",notes="Permite validar si existe el alumno si no lo registra con una nueva matrícula, la matrícula debe ser única.")//Documentacion de api
	public Alumno create(Alumno nuevoAlumno) {
		/*Reglas de negocio:
		 * no se puede crear dos alumnos con la misma matricula.
		 */
		log.info("En create servi "+nuevoAlumno.getMatricula().toString());
		Optional <Alumno> alumnoOpt = alumnoRepository.findById(nuevoAlumno.getMatricula());
		log.info(alumnoOpt.toString());
		if(!alumnoOpt.isPresent()) {
			alumnoRepository.save(nuevoAlumno);
			return alumnoRepository.save(nuevoAlumno);
		}else {
			return null;
		}
	}
	/*
	 * @return
	 * @throws NullPointer Exception 
	 * 
	 */
	@ApiOperation(value="Recupera Alumnos",notes="Devuelve todos los alumnos registrados como una colección.")//Documentacion de api
	public Iterable<Alumno> retrieveAll() throws NullPointerException{
		return alumnoRepository.findAll();
	}
	
	@ApiOperation(value="Buscar alumno",notes="Verifica si la matricula está registrada y devuelve al alumno, de lo contrario devuelve null.")//Documentacion de api
	public Optional<Alumno> retrieveByID(Integer matricula) {
		// TODO Auto-generated method stub
		Optional <Alumno> alumnoOpt = alumnoRepository.findById(matricula);
		
		if(alumnoOpt.isPresent()) {
			return alumnoOpt;
		}else {
			return null;
		}
	}
	@ApiOperation(value="Sobreescribe al alumno",notes="Verifica si la matricula está registrada y sobreescribe los datos del alumno.")//Documentacion de api
	public Alumno update(Integer matricula, Alumno alumnoUpdate) {
		// TODO Auto-generated method stub
		Optional <Alumno> alumnoOpt = alumnoRepository.findById(matricula);
		log.info(alumnoOpt.toString());
		if(alumnoOpt.isPresent()) {
			return alumnoRepository.save(alumnoUpdate);
		}else {
			return null;
		}
	}
	@ApiOperation(value="Eliminar alumno",notes="Verifica si la matrícula está registrada y elimina al alumno, si no devuelve null.")//Documentacion de api
	public Optional<Alumno> delete(Integer matricula) {
		Optional <Alumno> alumnoOpt = alumnoRepository.findById(matricula);
		log.info(alumnoOpt.toString());
		if(alumnoOpt.isPresent()) {
			alumnoRepository.deleteById(matricula);
			return alumnoOpt;
		}else {
			return null;
		}
	}
}
