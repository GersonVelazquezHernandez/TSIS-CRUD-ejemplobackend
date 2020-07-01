package mx.uam.tsis.ejemplobackend.datos;

import org.springframework.data.repository.CrudRepository;
import io.swagger.annotations.ApiOperation;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

@ApiOperation(value="Persitencia a la BD",notes="Interfaz de comunicación con la BD.")//Documentacion de api
public interface AlumnoRepository extends CrudRepository <Alumno, Integer>{ //Entidad, LLave Primaria
	
}

