<h1>BEACON 📡 | High-Precision Uptime & Latency Engine</h1>

<p>
  <img src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 17" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.2-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" alt="Spring Boot 3" />
  <img src="https://img.shields.io/badge/PostgreSQL-15-4169E1?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL" />
  <img src="https://img.shields.io/badge/Docker-Ready-2496ED?style=for-the-badge&logo=docker&logoColor=white" alt="Docker" />
</p>

<p>
  <strong>BEACON</strong> es un motor de observabilidad y monitorización en tiempo real desarrollado para rastrear la disponibilidad (uptime), latencia de respuesta y estado operativo de servicios web e infraestructura de red.
</p>

<p>
  Diseñado bajo una arquitectura desacoplada en <strong>Java 17</strong> y <strong>Spring Boot 3</strong>, implementa un planificador de sondeo concurrente, persistencia relacional en <strong>PostgreSQL</strong> mediante <strong>Spring Data JPA</strong> y una interfaz visual integrada tipo Status Page con estética minimalista y visualización por barras de latidos (heartbeat bars).
</p>

<hr />

<h2>🎯 Retos y Solución Técnica</h2>

<p>El objetivo del proyecto es resolver la monitorización continua de servicios propios sin recurrir a plataformas de terceros con limitaciones de telemetría:</p>

<ul>
  <li><strong>Planificación no bloqueante y concurrencia:</strong> Ejecución periódica mediante <code>@Scheduled</code> utilizando el cliente HTTP nativo de Java (<code>java.net.http.HttpClient</code>) con control estricto de timeouts para evitar bloqueos de hilos.</li>
  <li><strong>Modelado y consistencia de datos:</strong> Estructura relacional normalizada para separar la entidad monitorizada de sus registros de auditoría y telemetría temporal.</li>
  <li><strong>Observabilidad accesible:</strong> Exposición de endpoints REST documentados y una vista estática en modo oscuro servida directamente por el core de Spring Boot.</li>
</ul>

<hr />

<h2>🏛️ Flujo del Sistema</h2>

<pre>
[UptimeScheduler: Tarea periódica cada 60s]
          │
          ├──> Envía HTTP Ping asíncrono a servicios (PULSE / ATMOS)
          │
          ├──> Registra código HTTP y latencia en milisegundos
          │
          └──> Guarda registro en base de datos (PostgreSQL / H2)
                    │
                    ▼
          [MonitorController: API REST]
                    │
                    ▼
          [Dashboard Web: Interfaz Status Page]
</pre>

<hr />

<h2>✨ Características Principales</h2>

<ul>
  <li><strong>Motor de Health Checks Automático:</strong> Sondeo continuo cada 60 segundos con medición de latencia en milisegundos y captura de códigos de estado HTTP (2xx, 4xx, 5xx).</li>
  <li><strong>Persistencia Transaccional:</strong> Registro histórico de pings mediante Spring Data JPA e Hibernate para auditar la estabilidad temporal.</li>
  <li><strong>API RESTful Completa:</strong>
    <ul>
      <li><code>GET /api/monitors</code>: Listado de servicios con su estado actual y latencia.</li>
      <li><code>GET /api/monitors/{id}/logs</code>: Historial de los últimos registros.</li>
      <li><code>POST /api/monitors</code>: Registro dinámico de nuevas URLs.</li>
    </ul>
  </li>
  <li><strong>Status Page Integrada:</strong> Dashboard responsivo en modo oscuro con indicadores de estado pulsantes (<em>All Systems Operational</em>), métricas de uptime y barras de estado.</li>
  <li><strong>Contenedorización Multi-Stage:</strong> <code>Dockerfile</code> en dos etapas para minimizar el tamaño final de la imagen.</li>
</ul>

<hr />

<h2>🛠️ Stack Tecnológico</h2>

<ul>
  <li><strong>Backend Core:</strong> Java 17</li>
  <li><strong>Framework:</strong> Spring Boot 3.2 (Spring Web, Spring Data JPA, Task Scheduling)</li>
  <li><strong>Persistencia:</strong> Spring Data JPA / Hibernate</li>
  <li><strong>Base de Datos:</strong> PostgreSQL / H2 Database</li>
  <li><strong>Networking:</strong> <code>java.net.http.HttpClient</code> (HTTP/2 nativo)</li>
  <li><strong>Frontend:</strong> HTML5, CSS Grid/Flexbox, JavaScript ES6+</li>
  <li><strong>Contenedores:</strong> Docker</li>
</ul>

<hr />

<h2>📂 Estructura del Repositorio</h2>

<pre>
BEACON/
├── Dockerfile
├── pom.xml
└── src/
    └── main/
        ├── java/com/beacon/
        │   ├── BeaconApplication.java
        │   ├── controller/
        │   │   └── MonitorController.java
        │   ├── model/
        │   │   ├── Monitor.java
        │   │   └── PingLog.java
        │   ├── repository/
        │   │   ├── MonitorRepository.java
        │   │   └── PingLogRepository.java
        │   └── service/
        │       └── UptimeScheduler.java
        └── resources/
            ├── application.properties
            └── static/
                └── index.html
</pre>

<hr />

<h2>🚀 Despliegue y Ejecución Local</h2>

<h3>Opción 1: Con Docker</h3>
<pre>
docker build -t beacon-app .
docker run -d -p 8080:8080 --name beacon beacon-app
</pre>

<h3>Opción 2: Con Maven y Java 17</h3>
<pre>
./mvnw spring-boot:run
</pre>

<p>Accede al panel interactivo en: <code>http://localhost:8080</code></p>

<hr />

<h2>👨‍💻 Autor</h2>

<p>
  Desarrollado por <strong>Roberto Muñoz</strong><br />
  GitHub: <a href="https://github.com/roberttedt-jr">@roberttedt-jr</a>
</p>
