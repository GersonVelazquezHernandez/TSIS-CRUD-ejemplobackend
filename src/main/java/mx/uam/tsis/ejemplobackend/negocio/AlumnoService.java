package mx.uam.tsis.ejemplobackend.negocio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.tsis.ejemplobackend.datos.AlumnoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

@Service
public class AlumnoService {
	@Autowired /*no hay ambigüedad porque solo hay unas anotaciones controller Service y Componenet*/
	private AlumnoRepository alumnoRepository;
	public Alumno create(Alumno nuevoAlumno) {
		/*Reglas de negocio:
		 * no se puede crear dos alumnos con la misma matricula
		 * */
		Alumno alumno = alumnoRepository.findByID(nuevoAlumno.getMatricula());
		if(alumno == null) {
			alumnoRepository.save(nuevoAlumno);
			return alumnoRepository.save(nuevoAlumno);
		}else {
			return null;
		}
	}
	
	public List<Alumno> retrieveAll() {
		return alumnoRepository.find();
	}

	public Alumno retrieveByID(Integer matricula) {
		// TODO Auto-generated method stub
		Alumno alumno = alumnoRepository.findByID(matricula);
		if(alumno != null) {
			return alumno;
		}else {
			return null;
		}
	}

	public Alumno update(Integer matricula, Alumno alumnoUpdate) {
		// TODO Auto-generated method stub
		Alumno alumno = alumnoRepository.findByID(matricula);
		if(alumno != null) {
			return alumnoRepository.updateAlumno(matricula,alumnoUpdate);
		}else {
			return null;
		}
	}

	public Alumno delete(Integer matricula) {
		Alumno alumno = alumnoRepository.findByID(matricula);
		if(alumno != null) {
			alumnoRepository.deleteAlumno(matricula);
			return alumno;
		}else {
			return null;
		}
	}
}
