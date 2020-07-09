package mx.uam.tsis.ejemplobackend.negocio.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * La clase que representa la entidad
 */


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data //para genera getters y setters de alumno
@Entity//Indica que hay que persistir en BD
public class Grupo {
	@Id
	@GeneratedValue //Autogenera un ID único
	private Integer id;
	@NotBlank
	private String clave;
	@Builder.Default//inicializa para que no esté en null
	@OneToMany(targetEntity = Alumno.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	//Relación uno a muchos (un gurpo esta conectado a muchos alumnos) cardinalida 0..* ó 1..* NOTACION DE JPA CREA UNA TABLA PARA CONECTAR LOS ID, DE CADA QUIEN
	@JoinColumn(name = "id")// no crea tabla intermedia que haría con onetomany
	private List <Alumno> alumnos = new ArrayList<>();
	//Se crea un método porque lombok no puede insertar a esa lista
	public boolean addAlumno(Alumno alumno) {
		return alumnos.add(alumno);
	}
}
