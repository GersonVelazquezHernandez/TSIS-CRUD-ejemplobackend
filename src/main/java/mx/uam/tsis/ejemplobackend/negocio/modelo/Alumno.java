package mx.uam.tsis.ejemplobackend.negocio.modelo;

import lombok.Builder;
import lombok.Data;
@Builder
@Data //para genera getters y setters de alumno
public class Alumno {
	private Integer matricula;
	private String nombre;
	private String carrera;
}
