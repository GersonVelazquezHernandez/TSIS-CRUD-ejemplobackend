package mx.uam.tsis.ejemplobackend.negocio;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import mx.uam.tsis.ejemplobackend.datos.AlumnoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
@ExtendWith(MockitoExtension.class) //Uso de MOCkito
public class AlumnoServiceTest {
	@Mock//Mock generado por Mockito
	private AlumnoRepository alumnoRepositoryMock; 
	@InjectMocks//Genera MockDependency´s
	private AlumnoService alumnoService;//unidad a probar
	
	
	//Caso para crear un alumno
	@Test
	public void testSeccesfulCreate() {
		Alumno alumno = new Alumno();
		alumno.setCarrera("Computación");
		alumno.setMatricula(12345678);
		alumno.setNombre("Prueba");
		//Simula lo que el alumno repository con la base de datos se guarda si no esta en la base de datos
		when(alumnoRepositoryMock.findById(12345678)).thenReturn(Optional.ofNullable(null));
		when(alumnoRepositoryMock.save(alumno)).thenReturn(alumno);

		//Ejecucion de unidad de prueba
		alumno = alumnoService.create(alumno);
		assertNotNull(alumno); //Permite que la refierencia a alumno no se nula
	}
//Caso para que falle la creacion de un alumno porque ya existe	
	@Test
	public void testUnseccesfulCreate() {
		Alumno alumno = new Alumno();
		alumno.setCarrera("Computación");
		alumno.setMatricula(12345678);
		alumno.setNombre("Prueba");
		//Simula lo que el alumno repository con la base de datos se guarda si no esta en la base de datos
		when(alumnoRepositoryMock.findById(12345678)).thenReturn(Optional.ofNullable(alumno));
		//Analiza el código y no entra al if donde esta el create porque el alumno sí existía 
		//when(alumnoRepositoryMock.save(alumno)).thenReturn(alumno);

		//Ejecucion de unidad de prueba
		alumno = alumnoService.create(alumno);
		assertNull(alumno); //Permite que la refierencia a alumno no se nula
	}
	
	//Caso para obtener un alumno por matricula porque ya existe	
			@Test
			public void testSuccesfulretrieveById() {
				Alumno alumno = new Alumno();
				alumno.setCarrera("Computación");
				alumno.setMatricula(12345678);
				alumno.setNombre("Prueba");
				//Simula lo que el alumno repository con la base de datos se guarda si no esta en la base de datos
				when(alumnoRepositoryMock.findById(alumno.getMatricula())).thenReturn(Optional.of(alumno));
				//Ejecucion de unidad de prueba
				alumno = alumnoService.retrieveByID(alumno.getMatricula()).get();
				assertNotNull(alumno); //Permite que la refierencia a alumno no se nula
			}
			//Caso para actualizar un alumno porque ya existe	
			@Test
			public void testSuccesfulupdate() {
				Alumno alumno = new Alumno();
				alumno.setCarrera("Computación");
				alumno.setMatricula(12345678);
				alumno.setNombre("Prueba");
				
				Alumno alumnoUpdt = new Alumno();
				alumnoUpdt.setCarrera("Electrónica");
				alumnoUpdt.setMatricula(12345678);
				alumnoUpdt.setNombre("Prueba");;
				
				//Simula lo que el alumno repository con la base de datos se guarda si no esta en la base de datos
				when(alumnoRepositoryMock.findById(alumno.getMatricula())).thenReturn(Optional.ofNullable(alumno));
				when(alumnoRepositoryMock.save(alumno)).thenReturn(alumno);
				//Ejecucion de unidad de prueba
				boolean result = alumnoService.update(alumno.getMatricula(), alumnoUpdt);
				assertTrue(result); //Permite que la refierencia a alumno no se nula
			}
			//Caso para eliminar un alumno
			@Test
			public void testSuccesfuldelete() {
				Alumno alumno = new Alumno();
				alumno.setCarrera("Computación");
				alumno.setMatricula(12345678);
				alumno.setNombre("Prueba");
				//Simula lo que el alumno repository con la base de datos se guarda si no esta en la base de datos
				when(alumnoRepositoryMock.findById(alumno.getMatricula())).thenReturn(Optional.of(alumno));
				//Ejecucion de unidad de prueba
				alumno = alumnoService.delete(alumno.getMatricula()).get();
				assertNotNull(alumno); //Permite que la refierencia a alumno no se nula
			}
	
}
