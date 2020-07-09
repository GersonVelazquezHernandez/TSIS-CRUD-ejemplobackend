package mx.uam.tsis.ejemplobackend.negocio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.datos.GrupoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;

@Slf4j
@Service
public class GrupoService {
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private AlumnoService alumnoService;
	
	@ApiOperation(value="Crear Grupo",notes="Permite crear un grupo en la BD")//Documentacion de api
	public Grupo create(Grupo nuevo) {
		log.info("LLamada con grupo "+nuevo);
		//Validar reglas de negocio previas a la creación
		return grupoRepository.save(nuevo);
	}
	@ApiOperation(value="Ver Grupos",notes="Recupera la coleccion de los grupos guardados en ls BD.")//Documentacion de api
	public Iterable <Grupo> retrieveAll(){
		return grupoRepository.findAll();
	}
	
	@ApiOperation(value="Buscar grupo",notes="Verifica si el id está registrado y devuelve el grupo, de lo contrario devuelve null.")//Documentacion de api
	public Optional<Grupo> retrieveByID(Integer id) {
		// TODO Auto-generated method stub
		
		Optional <Grupo> grupoOpt = grupoRepository.findById(id);
		
		if(grupoOpt.isPresent()) {
			log.info("Sí esta el id: "+id);
			return grupoOpt;
		}else {
			log.info("No esta el id: "+id);
			return null;
		}
	}
	
	@ApiOperation(value="Sobreescribe al grupo",notes="Verifica si el id del grupo está registrado y sobreescribe los datos del grupo.")//Documentacion de api
	public boolean update(Integer id, Grupo grupoUpdate) {
		// TODO Auto-generated method stub
		Optional <Grupo> grupoOpt = grupoRepository.findById(id);
		log.info(grupoOpt.toString());
		if(grupoOpt.isPresent()) {
			Grupo grupo = grupoOpt.get();
			log.info("Cambió al grupo: "+grupo.getClave()+" por:"+grupoUpdate.getClave());
			grupo.setId(id);
			grupo.setClave(grupoUpdate.getClave());
			grupo.setAlumnos(grupo.getAlumnos());
			grupoRepository.save(grupo);
			return true;
		}else {
			return false;
		}
	}
	@ApiOperation(value="Eliminar grupo",notes="Verifica si el id del grupo está registrado y elimina al grupo, si no devuelve null.")//Documentacion de api
	public Optional<Grupo> delete(Integer id) {
		Optional <Grupo> grupoOpt = grupoRepository.findById(id);
		log.info(grupoOpt.toString());
		if(grupoOpt.isPresent()) {
			log.info("Voy a eliminar id: "+id);
			grupoRepository.deleteById(id);
			return grupoOpt;
		}else {
			return null;
		}
	}
	
	
	/**
	 * Método que permite agregar un alumno a un grupo
	 *  @param groupId
	 *  @param matricula
	 *  @return true si agregó correctamente o false si no
	 *  
	 */
	public boolean addStudentToGroup(Integer groupId, Integer matricula) {
		
		log.info("*************Agregando al alumno con matricula: "+matricula+" al grupo con ID: "+groupId+" ****************");
		//1.-Recuperamos al alumno
		Alumno alumno = alumnoService.retrieveByID(matricula).get();
		// 2.-Recuperamos al grupo
		Optional<Grupo> grupoOpt = grupoRepository.findById(groupId);
		//Verficamos que exista el grupo y el alumno
		if(!grupoOpt.isPresent()||alumno == null) {
			log.info("No se encontró alumno o grupo");
			return false;
		}else {
			//Agrego el alumno al grupo
		Grupo grupo = grupoOpt.get();
		grupo.addAlumno(alumno);
		
		//Persistir el cambio
				grupoRepository.save(grupo);
			return true;
		}	
	}
}