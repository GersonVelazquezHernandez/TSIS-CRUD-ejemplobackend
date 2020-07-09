package mx.uam.tsis.ejemplobackend.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import mx.uam.tsis.ejemplobackend.datos.AlumnoRepository;
import mx.uam.tsis.ejemplobackend.datos.GrupoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GrupoControllerIntegrationTest {
	@Autowired
	private TestRestTemplate restTemplate; //Permite hablar con el end point directamente
	@Autowired
	private GrupoRepository grupoRepository;
	@Autowired
	private AlumnoRepository alumnoRepository;
	
	@Test
	public void AddStudentToGroup200() {

		Alumno alumno = new Alumno();
		alumno.setMatricula(1234);
		alumno.setCarrera("Computación");
		alumno.setNombre("Pruebin");

		alumnoRepository.save(alumno); // Guardo el alumno original en la BD

		Grupo grupo = new Grupo();
		//Se mete el id en caso de prueba
		grupo.setId(1);
		grupo.setClave("TS01");
		grupoRepository.save(grupo);
		
		List <Alumno> alumnos = new ArrayList<>();
		alumnos.add(alumno);

		// Creo el encabezado
		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type",MediaType.APPLICATION_JSON.toString());
		headers.set("Authorization","Basic");

		// Creo la petición con el alumno como body y el encabezado
		HttpEntity <Alumno> request = new HttpEntity <> (alumno, headers);

		ResponseEntity <Grupo> responseEntity = restTemplate.exchange("/grupos/1/alumnos?matricula=1234", HttpMethod.POST, request, Grupo.class);

		// Corroboro que el endpoint me regresa el estatus esperado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		// Recupero de la BD el alumno
		//Optional <Grupo> optAlumno = grupoRepository.findById(1);

		//Alumno actualizado = optAlumno.get();

		// Aquí corroboro que el alumno que está en la BD ya quedó actualizado
		//List <Alumno>list = optAlumno.get().getAlumnos();
		//log.info(Arrays.toString(list.toArray()));
		//assertEquals(alumnoActualizado, actualizado);
		/*for(int i=0;i<list.size();i++) {
			log.info(list.get(i).toString());
		}*/
		// Debemos borrar al alumno, si no se queda en la BD
		//alumnoRepository.delete(actualizado);
	}
}
