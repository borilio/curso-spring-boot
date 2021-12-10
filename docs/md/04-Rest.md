# Índice

[TOC]

------



# Servicios

## ¿Qué es un servicio?

Los servicios dentro de una aplicación son clases normales, que se usan para separar la lógica de negocio de nuestra aplicación en una capa distinta, separado estos procesos del controlador o de la capa de datos. Así estamos facilitando separando responsabilidades, mejorando la escalabilidad y facilitando la detección de errores. 

La idea es que **la lógica de negocio la hagan los servicios**, y los **controladores consuman el servicio**. Esto es que serán los encargados de hacer las llamadas a los métodos de la clase servicio.

Podríamos hacer toda la lógica de, por ejemplo, conectarnos con la base de datos, extraer la información y llevarla hasta la vista, todo en el controlador. Sería mejor separar toda esa lógica en una clase normal a la llamaremos servicio y que tendrá tantos métodos como trabajos queramos que realicen. De esta forma también estamos encapsulando el trabajo realizado, abstrayéndonos del CÓMO ha sido realizado. 

Una cocina sería un servicio. El camarero le pide a la cocina un plato de comida de una determinada forma, la cocina se lo devuelve y el camarero tiene lo que ha pedido. El camarero no tiene que preocuparse del procedimiento que se realizó para obtener el plato. Lo pide y se lo dan. Y ya lo puede servir, o hacer lo que quiera con él.

Un patrón de diseño que se suele usar para crear servicios es el **patrón fachada** (*facade*). Consiste en crear una interfaz con los métodos que queremos que tenga el servicio, y después crear una clase que implemente dicha interfaz. Así nos aseguraremos que el servicio tenga obligatoriamente todos los métodos que se han definido en la interfaz. Esto también permite crear especificaciones y que un mismo servicio pueda implementar varias interfaces, lo cual flexibiliza las opciones. Ejemplo: Podemos tener una interfaz que tenga métodos sólo disponibles para un rol concreto, y un servicio podría implementar interfaces de rol usuario y de rol administrador, teniendo los métodos por separado.

## Creando un servicio

Veamos un servicio creado usando dicho patrón.

**Diseño de la interfaz del ArticulosService**

```java
public interface ArticulosService {
	public Articulo getArticuloAleatorio();
	public Articulo getArticuloById(Integer id);
	public List<Articulo> getArticulos(int numero);
	public List<Articulo> getArticulosBy(String descripcion);
}
```

**Implementación de la interfaz**

```java
public class ArticulosServiceImpl implements ArticulosService {
	
	@Override
	public Articulo getArticuloAleatorio() {
		Random r = new Random();
		int id = Math.abs(r.nextInt());
		int cantidadRandom = r.nextInt(10)+1;
		double precioRandom = r.nextDouble(50);
		boolean congeladoRandom = r.nextBoolean();
		
		return new Articulo(id, cantidadRandom, "Artículo nº" + id, precioRandom, congeladoRandom);
	}

	@Override
	public Articulo getArticuloById(Integer id) {
		//Generamos uno al azar y le ponemos la id recibida
		Articulo articuloCreado = this.getArticuloAleatorio();
		articuloCreado.setId(id); 
		articuloCreado.setDescripcion("Artículo nº" + id);
		
		return articuloCreado;
	}
	
	@Override
	public List<Articulo> getArticulos(int numero) {
		//Generamos una lista con tantos artículos nos hayan pedido
		List<Articulo> lista = new ArrayList<Articulo>();
		for (int i=0; i<=numero-1; i++) {
			lista.add(this.getArticuloAleatorio());
		}
		return lista;
	}

	@Override
	public List<Articulo> getArticulosByDescripcion(String descripcion) {
		// TODO Auto-generated method stub
		return null;
	}
}
```

Vemos como al **implementar** la interfaz `ArticulosService`, Java nos obliga a sobrescribir los métodos abstractos heredados, teniendo que desarrollar lo que hace cada uno. El último método `.getArticulosByDescripcion()` lo hemos dejado intacto tal cual nos lo deja el IDE, y ya lo implementaremos cuando nos haga falta.

Ahora mismo el servicio de artículos, no los está recuperando de una base de datos, los está creando aleatoriamente. Pero al tener toda la lógica de negocio separada por capas, en el momento que usemos el servicio que REALMENTE SI devuelva los artículos obtenidos de una base de datos, los controladores seguirán funcionando exactamente igual, ya que se limitan a hacer llamadas a los servicios, uno falso como ahora (*mock*), o reales (más adelante).

## Añadir funcionalidades al servicio

Si queremos añadir funcionalidades al servicio, habría que añadirle un nuevo método abstracto a la interfaz `ArticulosService`

```java
public interface ArticulosService {
	...
	public boolean deleteArticulo(int id); //Añadimos un nuevo método para borrar un artículo
}
```

Y automáticamente el IDE nos pedirá que `ArticulosServiceImpl` debe implementar ese método abstracto heredado. 

> 💡Recordemos que en java, todos los métodos definidos en una interfaz son implícitamente `public abstract`.

![Implementar un método nuevo en el servicio](img/04/01.png)

Al hacer clic en la opción de “*Add unimplemented methods*” nos heredará automáticamente los métodos nuevos, creando la estructura del mismo, y ahí es donde tendremos que desarrollar la implementación del nuevo método, programando la nueva funcionalidad. 

```java
public class ArticulosServiceImpl implements ArticulosService {

    ...
        
	@Override
	public boolean deleteArticulo(int id) {
		// TODO Auto-generated method stub
		return false;
	}
}
```

Nosotros decidimos como funcionan los métodos, lo argumentos que reciben, lo que hace en su interior y los valores que devuelven. Por ejemplo, aquí hemos decidido devolver un `boolean` que indicará si pudo borrar el artículo o no. Otra opción sería devolver una instancia del artículo borrado (para poder mostrar información del mismo) o un `null` en caso de que no hubiese sido posible borrar el artículo. 

## Servicios en Spring Boot

Ya que sabemos que es un servicio, como crearlos y usarlos en cualquier aplicación JavaEE, veremos como integrarlos en una aplicación con Spring Boot.

Spring nos facilita el uso de los servicios con la anotación **`@Service`**. Así le estamos diciendo a Spring que la clase va a poder ser inyectada. 

```java
@Service
public class ArticulosServiceImpl implements ArticulosService {
	...
}
```

Ahora necesitamos en el controlador que usemos instanciar un objeto de la clase `ArticulosServiceImpl` y se hace usando la inyección de dependencias. Usando la anotación **`@Autowired`** sobre la **interfaz** `ArticulosService`, Spring inyectará un objeto que implemente dicha interfaz. 

```java
@Controller
@RequestMapping("/api")
public class APIController {
	
	@Autowired
	ArticulosService articuloService;
	
	@GetMapping("/articulo/{id}")
	public String getArticuloPorId(
			@PathVariable Integer id,
			Model model
			) {
		Articulo a = articuloService.getArticuloById(id);
		model.addAttribute("listaArticulos", a);
		return "ficha-articulo";	
	}
    ...
}	
```

Ya podemos usar de una forma simple y eficiente los servicios de `ArticuloService` en todos los métodos del controlador, sin tener que inyectarlo método por método.

⚠**Atención:** Un “error ” muy común es querer inyectar un objeto de la clase con la implementación de la interfaz . Hay que **inyectar la interfaz del servicio**, y Spring hará todo el trabajo de crear una única instancia del objeto que implementa esa interfaz. En nuestro ejemplo lo correcto sería inyectar `ArticulosService`, y no `ArticulosServiceImpl`. De hecho, funcionará de ambas maneras, pero es una buena práctica codificar las interfaces en general, por el mismo motivo por el que se hace `List<Articulo> lista = new ArrayList<Articulo>()`.

> 🤓Técnicamente, el alcance de todas las anotaciones de Spring (`@Service`, `@Controller`, etc.) es un *Singleton*. Eso es otro patrón de diseño que consiste que crear una única instancia del objeto, que es la que se inyecta. De forma que no estamos creando múltiples instancias del mismo objeto en cada método.

# REST

## ¿Qué es REST?

REST es un acrónimo de ***RE**epresentational **S**tate **T**ransfer*. En pocas palabras, si HTTP es transferencia de archivos, REST se basa en transferencia de recursos. Aunque ambos siguen usando el mismo protocolo, el HTTP. Lo que cambia es la respuesta ofrecida.

Mientras que una respuesta HTTP estándar, es texto que crea otra página web que representa el navegador, una respuesta REST tiene el formato de un archivo XML o JSON. Se usan principalmente para el intercambio de datos, de una manera ligera y legible.

Es una forma de separar el cliente del servidor y hacer esa separación de forma independiente de la plataforma y tecnología usadas. Por ejemplo, podemos tener el backend en Java usando Spring y emitir las respuestas en JSON, de forma que el frontend en Angular haga las peticiones al backend e interprete esas respuestas en JSON. Si el día de mañana cambiamos de Angular a React o bien, el backend lo cambiamos a PHP, todo seguirá funcionando igual, siempre el API REST siga funcionando bajo las mismas rutas y devolviendo los objetos con la misma estructura. 

## REST en Spring Boot

Podemos construir sin mucho esfuerzo un controlador REST en Spring Boot, de forma que una petición a una URL nos devuelva casi de forma automática una respuesta en formato JSON:

**Ejemplo**: Una petición a http://localhost:8080/proyecto/api/articulos, en lugar de redireccionar a una vista con Thymeleaf, podemos emitir una respuesta con la lista completa de artículos que tengamos en nuestra base de datos:

```json
[
    {
        "id": 1,
        "cantidad": 9,
        "descripcion": "Bolsa Patatas Prefritas 1kg",
        "precio": 0.79,
        "congelado": true
    },
    {
        "id": 2,
        "cantidad": 5,
        "descripcion": "Garbanzos precocidos 500ml",
        "precio": 0.80,
        "congelado": false
    },
    {
        "id": 3,
        "cantidad": 9,
        "descripcion": "Leche en polvo 1l",
        "precio": 1.00,
        "congelado": false
    },
    {
        "id": 4,
        "cantidad" : 3,
        "descripcion": "Pollo asado 700gr",
        "precio": 4.30,
        "congelado": false
    },
    {
        "id": 5,
        "cantidad": 2,
        "descripcion": "Nuggets de pollo 500gr",
        "precio": 2.50,
        "congelado": true
    }
]
```

>💡Para que la conversión no de errores, el objeto a convertir deberá ser un bean (tener un constructor por defecto, tener todos los *getters* y *setters* públicos bien construidos, etc.)

Vamos a ver todas la anotaciones que debemos usar para crear un controlador REST con muy poco código.

## Clase Articulo

En los siguientes ejemplos, usaremos la siguiente clase **Articulo.java**, por lo que exponemos el código completo.

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



## @ResponseBody

La anotación `@ResponseBody` le indica a un controlador que el objeto que retornemos se debe convertir automáticamente en JSON y eso será lo que se envíe como respuesta, en lugar de ir a una vista con Thymeleaf.

```java
@Controller
@RequestMapping("/api")
public class APIController {
	@Autowired
	ArticulosService articulosService;

    ...
        
   	@ResponseBody
	@GetMapping("/articulo/{id}")
	public Articulo getArticulo(@PathVariable Integer id) {
		return articulosService.getArticuloById(id);
	}
}
```

Tenemos un controlador `APIController`, que será el encargado de recibir todas las peticiones REST. Destacamos:

- Le inyectamos el servicio `ArticulosService`, que será el encargado de usar la lógico de negocio para obtener la información que se le pide (obtener artículos por id, por precio, por descripción, todos los artículos, etc.)
- Con la anotación `@ResponseBody` le indicamos al controlador que no vamos a redireccionar a una vista HTML, si no que lo que vamos a devolver es un objeto, el cual será convertido a JSON por Spring Boot. Por eso tenemos que indicar en la firma del método la clase del objeto que vamos a retornar.
- El método `.getArticulo()` será llamado cada vez que realicemos una consulta a `/api/articulo/{id}`, donde `{id}` es la id del artículo a consultar. El servicio llamará al método que ya tiene para tal fin `articulosService.getArticuloById()`, el cual retornará el artículo cuya id sea {id} o bien `null` si esa id no se encuentra.

Obtendríamos la siguiente respuesta en JSON al hacer la petición GET a la URL `/api/articulo/3`

```json
{
    "id": 3,
    "cantidad": 9,
    "descripcion": "Leche en polvo 1l",
    "precio": 1.00,
    "congelado": false
}
```

Con esa petición obtendremos UN único artículo. Si queremos obtener un array de artículos, tan sólo deberemos retornar una colección de objetos y Spring Boot hará el trabajo de conversión a JSON.

```java
@ResponseBody
@GetMapping("/articulos")
public List<Articulo> getArticulos() {
    return articulosService.getAllArticulos();	
}
```

```json
[
    {
        "id": 1,
        "cantidad": 9,
        "descripcion": "Bolsa Patatas Prefritas 1kg",
        "precio": 0.79,
        "congelado": true
    },
    {
        "id": 2,
        "cantidad": 5,
        "descripcion": "Garbanzos precocidos 500ml",
        "precio": 0.80,
        "congelado": false
    },
    {...},
    {...}
]
```



## @RestController

Hemos visto que para hacer que los métodos de un controlador devuelvan objetos JSON, debemos usar `@ResponseBody`. Por otro lado, es muy frecuente que todos los métodos que devuelvan JSON estén agrupados en un mismo controlador, por lo que hay una anotación que se usa como combinación de `@Controller`  + `@ResponseBody`, y es **`@RestController`**.

Si usamos la anotación especializada `@RestController` en lugar de `@Controller`, le estamos diciendo explícitamente que TODOS los métodos de ese controlador devolverán JSON, por lo que no es necesario indicarle `@ResponseBody`.

```java
@RestController
@RequestMapping("/api")
public class APIController {
    ...
        
	@GetMapping("/articulo/{id}")
	public Articulo getArticulo(@PathVariable Integer id) {
		return articulosService.getArticuloById(id);
	}
}
```



------

# Práctica 6

Hacer un proyecto Spring Boot, con un REST de usuarios funcional como el siguiente. El servicio será un mock que actuará sobre una colección.

![Vista página principal](img/04/02.png)

En https://github.com/borilio/curso-spring-boot/tree/master/assets/clases/practica-6 encontrarás los siguientes recursos para reutilizar:

**Lo que ya está hecho:**

- `vistas/home.html` -> Es la página principal de la aplicación (la captura de arriba). En ella se encuentran las url que debemos satisfacer en nuestro Rest Controller y la descripción de lo que debe hacer cada una.
- `controllers/HomeController.java` -> El controlador principal que nos lleva a `home.html`. Ya encontrarás definidos e implementados en su interior formas para crear usuarios manualmente.
- `users/User.java` -> Una clase POJO que representa un usuario. Examina los constructores que tiene.
- `users/UserService.java` -> La interfaz `UserService` donde están definidos los métodos que deberemos desarrollar en `UserServiceImpl`.
- ⚠**Aviso:** En los archivos proporcionados, deberás cambiar el nombre del paquete por el definido en tu proyecto.

**Lo que hay que hacer:**

1. Primero deberemos completar el servicio. En orden haremos:
   1. Creamos en el paquete `users`, una clase llamada `UserServiceImpl`.
   2. Convertimos la clase en un servicio, usando la anotación correspondiente.
   3. La clase tendrá un sólo atributo privado, `private List<User> listaUsuarios;`. En el constructor de la clase inicializaremos ese atributo a un nuevo `ArrayList<User>` vacío.
   4. La clase implementará la interfaz `UserService`, sobrescribiendo todos los métodos abstractos heredados por la interfaz.
   5. Cada método heredado actuará sobre el atributo de la clase `listaUsuarios`, como si de una base de datos se tratara. De forma que nuestro servicio “imitará” el funcionamiento de una base de datos. Desde fuera del servicio, parecerá que está tratando internamente con una base de datos.
   6. En `UserService`, cada método tiene un comentario explicando lo que debería hacer cada método del servicio. Esto deberá implementarse en `UserServiceImpl`. Son acciones simples, entre 1 y 4 líneas cada uno como máximo. Si tenéis dudas en alguna preguntar y se aclarará.
2. Crear el controlador `APIController`. Aquí es donde se definirán las url que se pueden ver en `home.html` (o en la captura).
   1. Usar las anotaciones oportunas para crear un REST.
   2. Inyectar el servicio.
   3. Hacer los 4 métodos necesarios para satisfacer las necesidades del API definidas, haciendo uso del servicio `UserService`.
3. Comprobar que todo lo indicado en `home.html` funciona.

💡Verás que haciendo uso del servicio, las acciones para interactuar con la base de datos se resumen a UNA LINEA DE CÓDIGO. Y lo mejor es que el este servicio *mock*, puede ser fácilmente sustituido por uno real que si acceda a una base de datos, sin tener que modificar prácticamente nada de nuestra aplicación.



