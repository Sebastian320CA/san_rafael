Sistema de Monitoreo de Estado Operacional - E.S.E. Hospital Universitario San Rafael de Tunja
Este proyecto corresponde a la entrega final de la materia Desarrollo Orientado a Servicios.
Consiste en un sistema de monitoreo e infraestructura en tiempo real diseñado para el Hospital Universitario San Rafael de Tunja, 
cuyo objetivo principal es centralizar y visualizar la disponibilidad de las plataformas críticas de la institución, tales como Admisiones, 
Laboratorio e Historias Clínicas.

La aplicación se construyó bajo los principios de la Arquitectura Hexagonal, garantizando un desacoplamiento total entre la lógica de negocio 
y los agentes externos. El sistema evalúa periódicamente el estado de salud de los servicios mediante tareas programadas, calcula de manera jerárquica
el nivel de gravedad global del hospital y persiste tanto las métricas de rendimiento como la bitácora de incidentes en una base de datos relacional.

URL del Repositorio GitHub
Repositorio Oficial: https://github.com/Sebastian320CA/san_rafael
Stack Tecnológico y Entorno de Desarrollo
El sistema fue desarrollado, compilado y probado de manera nativa en un entorno Arch Linux,
garantizando la máxima eficiencia en el uso de recursos del sistema mediante el siguiente conjunto de herramientas: 
IDE Recomendado: IntelliJ IDEA Ultimate Kit de Desarrollo (JDK): Eclipse Temurin 21 (Java 21) 
Framework Principal: Spring Boot 3.x Motor de Base de Datos: PostgreSQL Versión 16 (Contenerizado) Virtualización y 
Pruebas: Docker y Docker Compose.

Pasos para Correr el Proyecto Localmente
Con estos pasos podrás desplegar la infraestructura de contenedores y correr correctamente el código en tu entorno local.

```bash
./gradlew sonar \
  -Dsonar.projectKey=SanRafael \
  -Dsonar.projectName='SanRafael' \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=[TU_TOKEN_AQUÍ]
```

Requisitos Previos
Antes de iniciar, asegúrate de tener instaladas las siguientes herramientas en tu sistema operativo: 
Java JDK: Eclipse Temurin 21 (Java 21). Herramienta de Construcción: Gradle. 
Motor de Contenedores: Docker y Docker Compose activos. Navegador
Para comprobar el correcto funcionamiento de la lógica de negocio ante respuestas asíncronas, alertas y errores simulados de infraestructura,
inicia el contenedor de rendimiento externo provisto por la cátedra mediante la terminal:

sudo docker run --name servicios_health_containers \ -p 8081:8081 -p 8082:8082 -p 8083:8083 -p 8084:8084 -p 8085:8085 \ harrysoler/rnghealthapis

IDE Recomendado y Configuración Básica
IDE: IntelliJ IDEA Ultimate.
Configuración Obligatoria: 1. Abre el proyecto en el IDE y dirígete a Settings > Build, Execution, Deployment > Build Tools > Gradle.
Asegúrate de que la opción Build and run using y Run tests using estén configuradas con IntelliJ IDEA para agilizar los tiempos de compilación.
Verifica que la Gradle JVM esté apuntando explícitamente a tu instalación de Java 21 (Temurin-21).
1. Despliegue de la Base de Datos Relacional (PostgreSQL)
   La persistencia de datos del hospital se encuentra contenerizada de manera aislada para evitar instalaciones globales densas, para ejecutar el codigo  
   coloca: http://localhost:8080/
   El archivo docker-compose.yml estructurado dentro de la carpeta postgres-spring/ contiene la siguiente definición de servicio:

```yaml services: postgres: image: postgres:16

container_name: postgres_san_rafael

restart: always

environment:
  POSTGRES_DB: bd_san_rafael
  POSTGRES_USER: user_java
  POSTGRES_PASSWORD: 0000

ports:
  - "5432:5432"

volumes:
  - postgres_data:/var/lib/postgresql/data
volumes: postgres_data:


Estructura del proyecto con diagrama de arbol.

A continuación, se presenta el árbol jerárquico real del proyecto generado desde la terminal mediante la herramienta `tree`.
 La disposición de paquetes evidencia la implementación estricta de la **Arquitectura Hexagonal**, dividiendo la aplicación en infraestructura (`adapter`), 
 interfaz de usuario (`ui`) y las reglas de negocio puras (`core`):

```text
sanrafael-status/
├── build.gradle
├── gradlew
├── gradlew.bat
├── settings.gradle
└── src
    ├── main
    │   ├── java
    │   │   └── dev
    │   │       └── sebastian
    │   │           └── sanrafael
    │   │               ├── adapter
    │   │               │   ├── api
    │   │               │   │   └── rest
    │   │               │   │       └── RestHealthReportGateway.java
    │   │               │   └── repository
    │   │               │       ├── inmemory
    │   │               │       │   ├── InMemoryHealthReportGateway.java
    │   │               │       │   └── InMemoryProductRepository.java
    │   │               │       └── jpa
    │   │               │           ├── entity
    │   │               │           │   ├── EventJpaEntity.java
    │   │               │           │   ├── ProductJpaEntity.java
    │   │               │           │   └── ReportJpaEntity.java
    │   │               │           ├── EventSpringDataRepository.java
    │   │               │           ├── JPAEventRepository.java
    │   │               │           ├── JPAProductRepository.java
    │   │               │           ├── JPAReportRepositoryAdapter.java
    │   │               │           ├── JPARepository.java
    │   │               │           ├── JPARepositoryProduct.java
    │   │               │           ├── ProductSpringDataRepository.java
    │   │               │           ├── SpringDataEventRepository.java
    │   │               │           ├── SpringDataReportRepository.java
    │   │               │           └── SpringDataRepository.java
    │   │               ├── ui
    │   │               │   └── spring
    │   │               │       ├── controller
    │   │               │       │   └── ProductController.java
    │   │               │       ├── ProductAPI.java
    │   │               │       ├── SpringAPI.java
    │   │               │       └── SpringConfig.java
    │   │               ├── core
    │   │               │   ├── constant
    │   │               │   │   └── Condition.java
    │   │               │   ├── dto
    │   │               │   │   ├── ProductDTO.java
    │   │               │   │   ├── ProductRequest.java
    │   │               │   │   ├── ProductStatusDTO.java
    │   │               │   │   └── ReportDTO.java
    │   │               │   ├── entity
    │   │               │   │   ├── Event.java
    │   │               │   │   ├── Product.java
    │   │               │   │   └── Report.java
    │   │               │   ├── port
    │   │               │   │   ├── EventRepository.java
    │   │               │   │   ├── HealthReportGateway.java
    │   │               │   │   ├── ProductRepository.java
    │   │               │   │   └── ReportRepository.java
    │   │               │   └── service
    │   │               │       ├── EventService.java
    │   │               │       ├── ProductService.java
    │   │               │       ├── ReportService.java
    │   │               │       └── StatusRequestService.java
    │   │               └── StatusApplication.java
    │   └── resources
    │       ├── application.properties
    │       ├── static
    │       │   ├── css
    │       │   │   └── Style.css
    │       │   └── images
    │       │       ├── Admin.jpeg
    │       │       ├── Logo2.jpeg
    │       │       ├── LogoUp.png
    │       │       ├── Nuevo1.png
    │       │       └── Nuevo2.png
    │       └── templates
    │           ├── common
    │           │   └── layout.html
    │           └── products
    │               ├── eventos.html
    │               ├── index.html
    │               ├── login.html
    │               ├── productos.html
    │               └── reportes.html
    └── test
        └── java
            └── dev
                └── sebastian
                    └── sanrafael
                        └── StatusApplicationTests.java


