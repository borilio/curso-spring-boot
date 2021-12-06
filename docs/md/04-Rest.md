# Índice

[TOC]

------



# REST

Podemos construir sin mucho esfuerzo un controlador REST en Spring Boot, de forma que una petición a una URL nos devuelva casi de forma automática una respuesta en formato JSON:

**Ejemplo**: Una petición a http://localhost:8080/proyecto/rest/users, en lugar de redireccionar a una vista con Thymeleaf, podemos emitir una respuesta del tipo:

```json
[
    {
        "id" : 1,
        "nombre" : "Administrador",
        "correo" : "admin@empresa.es",
        "clave" : "654321"
    },
    {
        "id" : 2,
        "nombre" : "Usuario",
        "correo" : "user@empresa.es",
        "clave" : "123456"
    },
    {
        "id" : 3,
        "nombre" : "Visor",
        "correo" : "visor@empresa.es",
        "clave" : "123456"
    }
]
```

>💡Para que la conversión no de errores, el objeto a convertir deberá ser un bean (tener un constructor por defecto, tener todos los *getters* y *setters* públicos bien construidos, etc.)

Vamos a ver todas la anotaciones que podríamos usar para crear un controlador REST con muy poco código.

# Clase Usuario

En los siguientes ejemplos, usaremos la siguiente clase **Usuario.java**, por lo que exponemos el código completo.

```java
public class Articulo {
	//Atributos
	private long id;
	private int cantidad;
	private String descripcion;
	private double precio;
	private boolean congelado;
	
	//Constructores
	public Articulo() {}

	public Articulo(long id, int cantidad, String descripcion, double precio, boolean congelado) {
		super();
		this.id = id;
		this.cantidad = cantidad;
		this.descripcion = descripcion;
		this.precio = precio;
		this.congelado = congelado;
    }
    
    public Articulo(int cantidad, String descripcion, double precio) {
		super();
		this.id = 0;
		this.cantidad = cantidad;
		this.descripcion = descripcion;
		this.precio = precio;
		this.congelado = false;
	}

	//Métodos
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public boolean isCongelado() {
		return congelado;
	}

	public void setCongelado(boolean requiereFrio) {
		this.congelado = requiereFrio;
	}

	@Override
	public String toString() {
		return "Articulo [id=" + id + 
				", cantidad=" + cantidad + 
				", descripcion=" + descripcion + 
				", precio=" + precio + 
				", congelado=" + congelado + 
				"]";
	}
}
```

# @ResponseBody

La anotación `@ResponseBody` le indica a un controlador que el objeto que retornemos se debe convertir automáticamente en JSON y eso será lo que se envíe como respuesta, en lugar de ir a una vista con Thymeleaf.

