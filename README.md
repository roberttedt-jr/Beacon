# BEACON 📡 | High-Precision Uptime & Latency Engine

[![Java 17](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot 3](https://img.shields.io/badge/Spring_Boot-3.2-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)

**BEACON** es un motor de monitorización y observabilidad en tiempo real diseñado para rastrear la disponibilidad, latencia y rendimiento de microservicios y aplicaciones web. Desarrollado con **Java 17**, **Spring Boot 3** y persistencia relacional en **PostgreSQL**, incluye un planificador concurrente asíncrono para *health checks* y un dashboard interactivo de baja latencia con estética de producto minimalista.

---

## 🏛️ Arquitectura del Sistema

El motor opera bajo un ciclo desacoplado de sondeo y consulta:

```mermaid
flowchart TD
    subgraph Engine [BEACON Core Engine]
        A[UptimeScheduler: @Scheduled 60s] -->|HTTP Ping Asíncrono| B(Servicios Objetivo: PULSE / ATMOS)
        B -->|Respuesta HTTP & Latencia ms| A
        A -->|Persistencia Transaccional| C[(PostgreSQL: Monitors & PingLogs)]
    end

    subgraph API & UI [Observability Layer]
        D[MonitorController: REST Endpoints] -->|Lectura JPA| C
        E[Frontend Dashboard: Status Page] -->|Polling Reactivo| D
    end
✨ Características Técnicas⏱️ Motor de Sondeo Concurrente: Health checks periódicos programados (@Scheduled) sobre java.net.http.HttpClient con resolución de milisegundos y gestión de timeouts.💾 Persistencia Relacional Optimizada: Modelado ORM con Hibernate / Spring Data JPA estructurado en dos entidades clave (Monitor y PingLog) con índices para consultas de series temporales.🌐 API RESTful Abierta: Endpoints desacoplados para consulta de telemetría y registro dinámico de nuevos servicios vigilados.📊 Status Page Integrada: Interfaz en modo oscuro con visualización de estado de servicio, latencia promedio y barras de latidos (heartbeat bars) en tiempo real.🐳 Contenedorización Multi-Stage: Dockerfile optimizado en dos etapas (build con Maven y ejecución sobre Alpine JRE) para reducir la superficie del contenedor y optimizar el consumo de RAM.🛠️ Stack TecnológicoComponenteTecnologíaJustificaciónLenguaje & FrameworkJava 17 / Spring Boot 3Tipado estricto, robustez empresarial y ecosistema maduro para servicios críticos.PersistenciaSpring Data JPA / PostgreSQLConsistencia ACID en registros históricos y mapeo declarativo de entidades.PlanificadorSpring Task Execution (@Scheduled)Ejecución periódica no bloqueante para muestreo continuo de infraestructura.DespliegueDocker & Docker ComposeAislamiento completo del entorno y compatibilidad directa con nubes modernas.🚀 Despliegue LocalCon Docker:Bash# Construir y arrancar el contenedor
docker build -t beacon .
docker run -p 8080:8080 beacon
Accede al panel en: http://localhost:8080👨‍💻 AutorDesarrollado por Roberto MuñozGitHub: @roberttedt-jr
4. Dale a **Commit changes...**.

---

Con esto, el repositorio de **BEACON** queda con el backend, el frontend y la documentación listos. Tu trilogía de proyectos queda perfectamente equilibrada:
1. **PULSE:** Producto completo, analítica y diseño de interfaz móvil.
2. **ATMOS:** Consumo ágil de APIs externas, canvas y filosofía PWA.
3. **BEACON:** Ingeniería de backend con Java/Spring Boot, persistencia relacional y monit
