package mx.uam.tsis.ejemplobackend.negocio.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/*
 * La clase que representa los alumnos
 */


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data //para genera getters y setters de alumno
@Entity//Indica que hay que persistir en BD
@EqualsAndHashCode
public class Alumno {
	@NotNull
	@ApiModelProperty(notes="Matricula del alumno", required=true)
	@Id// indica que es la llave primaria
	private Integer matricula;
	@NotBlank
	@ApiModelProperty(notes="Nombre del alumno", required=true)
	private String nombre;
	@NotBlank
	@ApiModelProperty(notes="Carrera del alumno", required=true)
	private String carrera;
}
