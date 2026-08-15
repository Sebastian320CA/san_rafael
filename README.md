## Sistema de Monitoreo de Estado Operacional (E.S.E. Hospital Universitario San Rafael)

Este proyecto representa la entrega final e integradora para la materia **Desarrollo Orientado a Servicios**. Se enfoca en resolver una problemática real de la infraestructura tecnológica hospitalaria: 
la falta de centralización en el monitoreo de plataformas críticas.

## Impacto del Proyecto (Enfoque Profesional)
El sistema está diseñado para actuar como el núcleo de observabilidad de la institución. Centraliza de forma nativa la disponibilidad de servicios esenciales como
**Admisiones, Laboratorio e Historias Clínicas**, calculando en tiempo real y de forma jerárquica el nivel de gravedad operacional global del hospital para prevenir caídas sistémicas.


## Stack Tecnológico e Infraestructura
Para asegurar un consumo mínimo de recursos y una compilación de alta eficiencia, el entorno nativo de desarrollo fue configurado sobre **Arch Linux** utilizando herramientas de última generación:

*   **Lenguaje & Runtime:** Java 21 (Eclipse Temurin JDK) para aprovechar los últimos estándares de rendimiento.
*   **Framework Base:** Spring Boot  para la autoconfiguración de servicios y backend ágil.
*   **Capa de Persistencia:** PostgreSQL 16 contenerizado de manera aislada para una gestión limpia de datos relacionales.
*   **Virtualización y Orquestación:** Docker y Docker Compose para garantizar la portabilidad absoluta del sistema.
*   **Análisis de Calidad:** Integración con SonarQube para auditoría estática de código.


## Patrón Arquitectónico: Arquitectura Hexagonal
El núcleo del proyecto fue construido bajo la estricta implementación de **Arquitectura Hexagonal (Puertos y Adaptadores)**. Esta decisión de diseño garantiza:
*   **Desacoplamiento Total:** La lógica de negocio pura (`core`) no conoce los detalles de la base de datos ni de la interfaz gráfica.
*   **Alta Mantenibilidad:** Es posible cambiar la base de datos relacional por una NoSQL o en memoria sin tocar una sola línea de lógica empresarial.
*   **Testabilidad:** Permite realizar pruebas unitarias robustas aislando los servicios externos.

### Estructura de Paquetes (Árbol Limpio)
*   **`core/`**: Aloja las reglas de negocio (Entidades, Puertos/Interfaces, Servicios y DTOs). Es inmune a tecnologías externas.
*   **`adapter/`**: Contiene la infraestructura técnica. Aquí residen los controladores JPA, la persistencia en base de datos y pasarelas API REST.
*   **`ui/`**: Maneja la interacción web del usuario final mediante vistas dinámicas en Thymeleaf y controladores de Spring MVC.

## Guía de Despliegue Local

### Requisitos Técnicos Previos
*   Java JDK 21 (Temurin recomendado).
*   Herramienta de construcción Gradle.
*   Motor de contenedores Docker con Docker Compose activo.

### 1. Clonación e Infraestructura de Datos
Levante la base de datos PostgreSQL 16 aislada para evitar instalaciones globales densas en su sistema operativo:
```bash
# Iniciar contenedor de base de datos relacional
docker compose up -d
```
*La base de datos se configurará automáticamente con el nombre `bd_san_rafael` en el puerto `5432`.*

### 2. Simulación de Tráfico e Infraestructura Externa
Para validar la resiliencia del negocio ante latencia, alertas, respuestas asíncronas y simulación de errores del entorno de red hospitalario, inicie el contenedor de
rendimiento externo provisto por la cátedra:
```bash
sudo docker run --name servicios_health_containers \
  -p 8081:8081 -p 8082:8082 -p 8083:8083 -p 8084:8084 -p 8085:8085 \
  harrysoler/rnghealthapis
```

### 3. Ejecución del Servidor Web
Abra el proyecto en su IDE (Recomendado: **IntelliJ IDEA Ultimate**). Asegúrese de ajustar en `Settings > Build Tools > Gradle` que las opciones 
*Build and run using* y *Run tests using* apunten a **IntelliJ IDEA** para optimizar los tiempos de compilación de Java 21.

Inicie la aplicación mediante la clase `StatusApplication.java` o use la terminal:
```bash
./gradlew bootRun
```
Abra su navegador web e ingrese a la interfaz en tiempo real: `http://localhost:8080/`


## Calidad de Código y Deuda Técnica
El proyecto cuenta con configuración lista para auditorías estáticas. Puede ejecutar el análisis de cobertura y vulnerabilidades lanzando el siguiente comando con su respectivo token local:

```bash
./gradlew sonar \
  -Dsonar.projectKey=SanRafael \
  -Dsonar.projectName='SanRafael' \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=[TU_TOKEN_AQUÍ]
```
