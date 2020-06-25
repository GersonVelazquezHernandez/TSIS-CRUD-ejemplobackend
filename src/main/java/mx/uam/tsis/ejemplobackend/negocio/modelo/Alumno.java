package mx.uam.tsis.ejemplobackend.negocio.modelo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;
@Builder
@Data //para genera getters y setters de alumno
public class Alumno {
	@NotNull
	private Integer matricula;
	@NotBlank
	private String nombre;
	@NotBlank
	private String carrera;
}
