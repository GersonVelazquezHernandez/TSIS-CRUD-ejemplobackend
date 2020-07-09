package mx.uam.tsis.ejemplobackend.negocio;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.Optional;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import mx.uam.tsis.ejemplobackend.datos.GrupoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;
//Caso para crear un alumno
@ExtendWith(MockitoExtension.class) //Uso de MOCkito
public class GrupoServiceTest {
	
	//Dependencia
	@Mock
	private GrupoRepository grupoRepositoryMock;
	//Unidad de prueba
	@Mock
	private AlumnoService alumnoServiceMock;
	
	@InjectMocks
	private GrupoService grupoService;
	
	
	//Caso para que falle la creacion de un grupo porque ya existe	
	@Test
	public void testSeccesfulCreate() {
		Grupo grupo = new Grupo();
		//Se mete el id en caso de prueba
		grupo.setId(1);
		grupo.setClave("TS01");
		//Simula lo que el alumno repository con la base de datos se guarda si no esta en la base de datos
		when(grupoRepositoryMock.save(grupo)).thenReturn(grupo);

		//Ejecucion de unidad de prueba
		grupo = grupoService.create(grupo);
		assertNotNull(grupo); //Permite que la refierencia a alumno no se nula
	}
	//Caso para obtener un grupo por ID porque ya existe	
		@Test
		public void testSuccesfulretrieveById() {
			Grupo grupo = new Grupo();
			//Se mete el id en caso de prueba
			grupo.setId(1);
			grupo.setClave("TS01");
			//Simula lo que el alumno repository con la base de datos se guarda si no esta en la base de datos
			when(grupoRepositoryMock.findById(grupo.getId())).thenReturn(Optional.of(grupo));
			//Ejecucion de unidad de prueba
			grupo = grupoService.retrieveByID(grupo.getId()).get();
			assertNotNull(grupo); //Permite que la refierencia a alumno no se nula
		}
		/*@Test
		public void testUnsuccesfulretrieveById() {
			Grupo grupo = new Grupo();
			//Se mete el id en caso de prueba
			grupo.setId(1);
			grupo.setClave("TS01");
			//Simula lo que el alumno repository con la base de datos se guarda si no esta en la base de datos
			when(grupoRepositoryMock.findById(grupo.getId())).thenReturn(Optional.ofNullable(null));
			//Ejecucion de unidad de prueba
			grupo = grupoService.retrieveByID(grupo.getId()).get();
			assertNull(grupo); //Permite que la refierencia a alumno no se nula
		}*/
		//Caso para actualizar un grupo porque ya existe	
				@Test
				public void testSuccesfulupdate() {
					Grupo grupo = new Grupo();
					//Se mete el id en caso de prueba
					grupo.setId(1);
					grupo.setClave("TS01");
					
					Grupo Updategrupo = new Grupo();
					//Se mete el id en caso de prueba
					Updategrupo.setId(1);
					Updategrupo.setClave("Actualizado");
					
					//Simula lo que el alumno repository con la base de datos se guarda si no esta en la base de datos
					when(grupoRepositoryMock.findById(grupo.getId())).thenReturn(Optional.ofNullable(grupo));
					when(grupoRepositoryMock.save(grupo)).thenReturn(grupo);
					//Ejecucion de unidad de prueba
					boolean result = grupoService.update(grupo.getId(), Updategrupo);
					assertTrue(result); //Permite que la refierencia a alumno no se nula
				}
		//Caso para eliminar un grupo porque ya existe	
		@Test
		public void testSuccesfuldelete() {
			Grupo grupo = new Grupo();
			//Se mete el id en caso de prueba
			grupo.setId(1);
			grupo.setClave("TS01");
			//Simula lo que el alumno repository con la base de datos se guarda si no esta en la base de datos
			when(grupoRepositoryMock.findById(grupo.getId())).thenReturn(Optional.of(grupo));
			//Ejecucion de unidad de prueba
			grupo = grupoService.delete(grupo.getId()).get();
			assertNotNull(grupo); //Permite que la refierencia a alumno no se nula
		}
	
	@Test
	public void testSuccesfulAddStudentToGroup(){
		Grupo grupo = new Grupo();
		//Se mete el id en caso de prueba
		grupo.setId(1);
		grupo.setClave("TS01");
		
		Alumno alumno = new Alumno();
		alumno.setCarrera("Computación");
		alumno.setMatricula(12345678);
		alumno.setNombre("Prueba");
		
		when(alumnoServiceMock.retrieveByID(alumno.getMatricula())).thenReturn(Optional.of(alumno));
		when(grupoRepositoryMock.findById(grupo.getId())).thenReturn(Optional.of(grupo));
		
		boolean result = grupoService.addStudentToGroup(1, 12345678);
		
		assertEquals(true, result);
		assertEquals(grupo.getAlumnos().get(0),alumno);
	}
	@Test
	public void testUnsuccesfulAddStudentToGroup(){
		
		Alumno alumno = new Alumno();
		alumno.setCarrera("Computación");
		alumno.setMatricula(12345678);
		alumno.setNombre("Prueba");
		
		when(alumnoServiceMock.retrieveByID(alumno.getMatricula())).thenReturn(Optional.of(alumno));
		when(grupoRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(null));
		
		boolean result = grupoService.addStudentToGroup(1, 12345678);
		
		assertEquals(false, result);
	}
}
