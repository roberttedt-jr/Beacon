# 📡 BEACON — High-Precision Uptime & Latency Engine

<div align="center">

### **Motor de Observabilidad, Latencia y Disponibilidad en Tiempo Real.**

[![Java 17](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot 3](https://img.shields.io/badge/Spring_Boot-3.2.3-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Hibernate](https://img.shields.io/badge/Hibernate-ORM-59666C?style=for-the-badge&logo=hibernate&logoColor=white)](https://hibernate.org/)
[![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![License MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)](LICENSE)

[Características](#-características-principales) • [Arquitectura del Sistema](#-arquitectura-del-sistema) • [API RESTful](#-referencia-de-la-api-rest) • [Ejecución Local & Docker](#-ejecución-y-despliegue) • [Estructura](#-estructura-del-proyecto)

</div>

---

## 📖 Descripción del Proyecto

**BEACON** es un motor de telemetría y observabilidad en tiempo real desarrollado para rastrear de forma autónoma la **disponibilidad (*uptime*)**, **latencia de red** y el **estado operativo** de servicios web, APIs y microservicios.

Construido sobre **Java 17** y **Spring Boot 3.2**, implementa un planificador de sondeo concurrente no bloqueante mediante el cliente nativo `java.net.http.HttpClient` (HTTP/2), persistencia transaccional en **PostgreSQL** mediante **Spring Data JPA**, y sirve una interfaz visual moderna (*Status Page*) en modo oscuro con indicadores de latidos en vivo (*heartbeat bars*).

---

## ✨ Características Principales

- **Sondeo Periódico Automático:** Ejecución programada periódica (`@Scheduled`) con temporizadores configurables y gestión rigurosa de timeouts para evitar el bloqueo de hilos de trabajo.
- **Medición Precisa de Latencia:** Registro en milisegundos del tiempo de ida y vuelta (*round-trip time*) y almacenamiento de códigos HTTP de respuesta (`2xx`, `4xx`, `5xx`).
- **Persistencia Transaccional con Spring Data JPA:** Histórico normalizado en PostgreSQL con soporte para base de datos H2 en memoria durante desarrollo y tests automatizados.
- **Status Page Integrada:** Dashboard visual servido directamente por el backend con estética *dark mode*, métricas acumuladas de uptime y barras pulsantes de estado (*All Systems Operational*).
- **API REST Completa:** Endpoints para consultar estados consolidados, historiales de logs y registrar nuevos endpoints de monitorización dinámicamente.
- **Contenedorización Multi-Stage:** `Dockerfile` optimizado en dos fases (build con Maven + runtime con Eclipse Temurin JRE) para una imagen final ligera y segura.

---

## 🏛️ Arquitectura del Sistema

```
┌────────────────────────────────────────────────────────┐
│               UptimeScheduler (Cron 60s)               │
│      • Sondeo concurrente asíncrono no bloqueante      │
│      • java.net.http.HttpClient (HTTP/2 nativo)        │
└──────────────────────────┬─────────────────────────────┘
                           │
            Pings HTTP / HTTPS periódicos
                           │
                           ▼
              [ Servicios Monitorizados ]
             (Pulse, Atmos, APIs externas)
                           │
                           │  Código HTTP + Latencia (ms)
                           ▼
┌────────────────────────────────────────────────────────┐
│                  Capa de Persistencia                  │
│            Spring Data JPA / Hibernate Core            │
│            • PostgreSQL (Producción / Render)          │
│            • H2 In-Memory (Desarrollo / Tests)         │
└──────────────────────────┬─────────────────────────────┘
                           │
                           ▼
┌────────────────────────────────────────────────────────┐
│            MonitorController (REST API)                │
│     GET /api/monitors  •  GET /api/monitors/{id}/logs  │
│                   POST /api/monitors                   │
└──────────────────────────┬─────────────────────────────┘
                           │
                           ▼
┌────────────────────────────────────────────────────────┐
│             Dashboard Web (Status Page)                │
│    Interfaz HTML5 / CSS Grid / JS ES6+ integrada       │
└────────────────────────────────────────────────────────┘
```

---

## 🛠️ Stack Tecnológico

| Capa | Tecnología | Función |
|---|---|---|
| **Lenguaje Core** | [Java 17 (LTS)](https://www.oracle.com/java/) | Lenguaje tipado con soporte moderno para registros y concurrencia |
| **Framework** | [Spring Boot 3.2.3](https://spring.io/projects/spring-boot) | Framework base (Spring Web, Spring Data JPA, Task Scheduling) |
| **Persistencia** | [Hibernate](https://hibernate.org/) / JPA | ORM transaccional para mapeo objeto-relacional |
| **Bases de Datos** | [PostgreSQL 15](https://www.postgresql.org/) + [H2 Database](https://www.h2database.com/) | Base de datos de producción y base de datos en memoria para pruebas |
| **Networking** | `java.net.http.HttpClient` | Cliente HTTP/2 asíncrono nativo de Java 11+ |
| **Frontend** | HTML5, CSS Grid/Flexbox, JavaScript ES6+ | Status Page reactiva servida como activo estático |
| **Contenedores** | [Docker](https://www.docker.com/) | Contenedorización multi-stage para despliegue en la nube |

---

## 📡 Referencia de la API REST

### 1. Listar Monitores y Estado Actual
```http
GET /api/monitors
```
**Respuesta:**
```json
[
  {
    "id": 1,
    "name": "Pulse Web App",
    "url": "https://pulse-app.vercel.app",
    "status": "UP",
    "lastLatency": 142,
    "uptimePercentage": 99.98,
    "lastCheck": "2026-09-08T19:30:00Z"
  }
]
```

### 2. Obtener Historial de Pings de un Monitor
```http
GET /api/monitors/{id}/logs
```
**Respuesta:**
```json
[
  {
    "id": 105,
    "statusCode": 200,
    "latencyMs": 142,
    "timestamp": "2026-09-08T19:30:00Z"
  }
]
```

### 3. Registrar un Nuevo Servicio a Monitorizar
```http
POST /api/monitors
Content-Type: application/json

{
  "name": "Atmos Experience",
  "url": "https://atmos-web.vercel.app"
}
```

---

## 📂 Estructura del Proyecto

```text
BEACON/
├── Dockerfile                        # Imagen multi-stage (Maven build + Eclipse Temurin JRE)
├── pom.xml                           # Descriptor Maven con dependencias Spring Boot 3.2
└── src/
    ├── main/
    │   ├── java/com/beacon/
    │   │   ├── BeaconApplication.java        # Punto de entrada de la aplicación Spring Boot
    │   │   ├── controller/
    │   │   │   └── MonitorController.java    # Controlador REST (/api/monitors)
    │   │   ├── model/
    │   │   │   ├── Monitor.java              # Entidad JPA para servicios monitorizados
    │   │   │   └── PingLog.java              # Entidad JPA para registros de auditoría y latencia
    │   │   ├── repository/
    │   │   │   ├── MonitorRepository.java    # Interfaz Spring Data JPA para Monitores
    │   │   │   └── PingLogRepository.java    # Interfaz Spring Data JPA para logs de telemetría
    │   │   └── service/
    │   │       └── UptimeScheduler.java      # Motor concurrente de sondeo con @Scheduled
    │   └── resources/
    │       ├── application.properties        # Configuración de base de datos y puertos
    │       └── static/
    │           └── index.html                # Status Page integrada en modo oscuro
    └── test/                                 # Pruebas unitarias y de integración
```

---

## 🚀 Ejecución y Despliegue

### Opción 1: Ejecución con Docker (Recomendada)

```bash
# 1. Clonar el repositorio
git clone https://github.com/roberttedt-jr/beacon.git
cd beacon

# 2. Construir la imagen Docker multi-etapa
docker build -t beacon-app .

# 3. Ejecutar el contenedor
docker run -d -p 8080:8080 --name beacon beacon-app
```

Accede al dashboard en [http://localhost:8080](http://localhost:8080).

---

### Opción 2: Ejecución Local con Maven y Java 17

#### Prerrequisitos:
- JDK 17 o superior instalado.
- Maven 3.8+ (o el wrapper de Maven).

```bash
# Compilar y ejecutar
mvn spring-boot:run
```

Para ejecutar contra PostgreSQL en lugar de H2, define las variables de entorno correspondientes:
```bash
export SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/beacon_db"
export SPRING_DATASOURCE_USERNAME="postgres"
export SPRING_DATASOURCE_PASSWORD="password_segura"
mvn spring-boot:run
```

---

## 👨‍💻 Autor y Licencia

Desarrollado y mantenido por **Roberto** ([@roberttedt-jr](https://github.com/roberttedt-jr)).

Distribuido bajo la licencia [MIT](LICENSE).
