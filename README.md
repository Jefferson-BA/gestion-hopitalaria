# Sistema de Gestión Hospitalaria

Sistema web completo para la gestión de pacientes, médicos, citas, especialidades, habitaciones y hospitalizaciones.

## 🚀 Inicio Rápido

### Prerrequisitos
- **Java 17+** - [Descargar](https://adoptium.net/)
- **Maven 3.6+** - [Descargar](https://maven.apache.org/download.cgi)
- **MySQL 8.0+** - [Descargar](https://dev.mysql.com/downloads/)

### Instalación

1. **Clonar el repositorio**
   ```bash
   git clone <url-del-repositorio>
   cd gestion-hospitalaria
   ```

2. **Configurar base de datos**
   - Crear base de datos: `hospital_db`
   - Usuario: `root` (sin contraseña)
   - Ejecutar: `database_setup.sql`

3. **Instalar dependencias**
   ```bash
   mvn clean install
   ```

4. **Ejecutar aplicación**
   ```bash
   java -jar target/gestion-hospitalaria-0.0.1-SNAPSHOT.jar
   ```

5. **Acceder al sistema**
   - **Web**: http://localhost:8082
   - **API**: http://localhost:8082/api

## 📁 Estructura del Proyecto

```
gestion-hospitalaria/
├── src/main/java/com/hospital/gestion/
│   ├── model/          # Entidades JPA
│   ├── dto/            # Data Transfer Objects
│   ├── repository/     # Repositorios Spring Data
│   ├── service/        # Lógica de negocio
│   ├── controller/     # Controladores REST
│   └── config/         # Configuraciones
├── src/main/resources/
│   ├── static/         # Frontend (index.html)
│   └── application.properties
├── database_setup.sql  # Script de base de datos
└── pom.xml            # Dependencias Maven
```

## 🛠️ Tecnologías

- **Backend**: Spring Boot 3.5.6, JPA/Hibernate, MySQL
- **Frontend**: HTML5, CSS3, JavaScript (Vanilla)
- **Build**: Maven

## 📋 Funcionalidades

- ✅ Gestión de Pacientes (CRUD completo)
- ✅ Gestión de Médicos y Especialidades
- ✅ Programación y gestión de Citas
- ✅ Gestión de Habitaciones
- ✅ Control de Hospitalizaciones
- ✅ Interfaz web responsiva

## 🔧 Configuración

**Base de datos** (`application.properties`):
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db
spring.datasource.username=root
spring.datasource.password=
```

**Puerto**: 8082

## 📊 Datos de Prueba

Ejecutar `datos_prueba_completos.sql` para cargar datos de ejemplo.

---
**Desarrollado con Spring Boot** | **Puerto: 8082** | **Base de datos: MySQL**