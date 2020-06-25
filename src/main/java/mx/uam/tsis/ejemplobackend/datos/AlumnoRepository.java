package mx.uam.tsis.ejemplobackend.datos;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

@Component
public class AlumnoRepository {
	//La base de datos"En memoria"
		private Map<Integer, Alumno> alumnoRepository = new HashMap<>();
		
		/****ALMACENA LA PERCISTENCIA SE ENCARGA DE ALMACENAR Y RECUPERAR****/
		
		
		/*Guarda en la base de datos*/
		public Alumno save(Alumno nuevoAlumno) {
			alumnoRepository.put(nuevoAlumno.getMatricula(), nuevoAlumno);
			return nuevoAlumno;
		}
		/*Recupera de la BD*/
		public Alumno findByID(Integer matricula) {
			return alumnoRepository.get(matricula);
		}
		public List <Alumno> find(){
			return new ArrayList<>(alumnoRepository.values());
		}
		
		//Update
		public void actualizar (Integer matricula, Alumno alumnoUpdate) {
			alumnoRepository.replace(matricula, alumnoUpdate);
		}
		//delete
		public void eliminar(Integer matricula) {
			alumnoRepository.remove(matricula);

		}
		public Alumno updateAlumno(Integer matricula, Alumno alumnoUpdate) {
			alumnoRepository.replace(matricula, alumnoUpdate);
			return alumnoUpdate;
		}
		public void deleteAlumno(Integer matricula) {
			// TODO Auto-generated method stub
			alumnoRepository.remove(matricula);
		}
}
