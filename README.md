# 📚 Challenge Literalura

**Literalura** es una aplicación interactiva de línea de comandos, desarrollada en **Java**, que combina la potencia de la API de Gutendex con la flexibilidad de una base de datos **PostgreSQL**. Te permite explorar un vasto catálogo de libros, guardar tus favoritos y realizar análisis avanzados para descubrir tendencias literarias.

---

## ✨ Características principales:

- 🔍 **Búsqueda inteligente**: Encuentra libros por título desde la API de Gutendex.  
- 💾 **Persistencia de datos**: Guarda los libros seleccionados en una base de datos relacional.  
- 🌍 **Organización por idioma**: Lista libros según su idioma de publicación.  
- 📈 **Estadísticas destacadas**: Descubre el **Top 10** de los libros más descargados.  
- 🧮 **Análisis de datos**: Realiza análisis estadísticos sobre las descargas acumuladas.  

---

## 🚀 Tecnologías utilizadas:

| Tecnología   | Descripción                                                                 |
|--------------|-----------------------------------------------------------------------------|
| **Java**     | Lenguaje principal del proyecto.                                           |
| **Spring Boot** | Framework para la creación de aplicaciones robustas y escalables.       |
| **JPA**      | Gestión de entidades y persistencia de datos.                              |
| **PostgreSQL**| Base de datos relacional para almacenar la información de los libros.     |
| **Jackson**  | Librería para el manejo de datos en formato JSON.                          |

---

## ⚙️ Cómo ejecutar el proyecto:

Sigue estos pasos para configurar y ejecutar la aplicación en tu entorno local:

1. **Clona el repositorio**:
   ```bash
   git clone <URL del repositorio>
   cd <nombre-del-repositorio>
2. **Configura la base de datos**:

  -Modifica el archivo application.properties con tus credenciales de PostgreSQL.

  -Asegúrate de que la base de datos esté activa y configurada

3. **Ejecuta la aplicacon**

  - Usa Maven para iniciar la aplicación:

mvn spring-boot:run

4. **Interacción:** Accede a las opciones del menú y comienza a explorar libros.

📖 Ejemplos de uso:

Buscar libros por título

Elige una opción: 1
Ingresa el título del libro: Don Quixote
Resultado:
1. Don Quixote (Idioma: Español, Descargas: 120,000)
2. The Ingenious Gentleman Don Quixote of La Mancha (Idioma: Inglés, Descargas: 80,000)

Listar libros por idioma

Elige una opción: 3
Selecciona el idioma: Inglés
Libros encontrados:
- Pride and Prejudice
- The Adventures of Sherlock Holmes
- Moby Dick

🛠️ Próximas mejoras:

🛠️ Implementar una interfaz gráfica de usuario (GUI).

🛠️ Integración con más APIs literarias.

---

## 💖 Agradecimientos

Este proyecto no habría sido posible sin el apoyo e inspiración de personas y equipos especiales:

- **Edith**, la mera mera, quien siempre me inspira a dar lo mejor de mí y perseguir mis metas. 🌟  
- **El Pollito**, mi amigo que, aunque a veces me cuesta explicar las cosas, siempre se esmera y da su mejor esfuerzo. 🐤  
- **El equipo de Alura Latam**, por impulsarnos a llegar más lejos y enseñarnos que los límites solo están en nuestra mente. 🚀  

¡Gracias por ser parte de este viaje y por su apoyo incondicional! 🙌

   
## Imagenes ilustrativas del proyecto en consola
![Captura del menu](https://github.com/tavooshhnn/ChallengeLiteraLura/blob/main/Literalura1.jpg)
Ejemplo de busqueda:
![Captura de una busqueda](https://github.com/tavooshhnn/ChallengeLiteraLura/blob/main/literalura2.jpg)


