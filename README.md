BEACON 📡 | High-Precision Uptime & Latency EngineBEACON es un motor de observabilidad y monitorización en tiempo real desarrollado para rastrear la disponibilidad (uptime), latencia de respuesta y estado operativo de servicios web e infraestructura de red.Diseñado bajo una arquitectura desacoplada en Java 17 y Spring Boot 3, implementa un planificador de sondeo concurrente, persistencia relacional en PostgreSQL mediante Spring Data JPA y una interfaz visual integrada tipo Status Page con estética minimalista y visualización por barras de latidos (heartbeat bars).🎯 Retos y Solución TécnicaEl objetivo del proyecto es resolver la monitorización continua de servicios propios (como PULSE o ATMOS) sin recurrir a plataformas de terceros con limitaciones de telemetría:Planificación no bloqueante y concurrencia: Ejecución periódica mediante @Scheduled utilizando el cliente HTTP nativo de Java (java.net.http.HttpClient) con control estricto de timeouts para evitar fugas de memoria o bloqueo de hilos.Modelado y consistencia de datos: Estructura relacional normalizada para separar la entidad monitorizada de sus registros de auditoría y telemetría temporal.Observabilidad accesible: Exposición de endpoints REST documentados y una vista estática en modo oscuro servida directamente por el core de Spring Boot sin dependencias de frameworks frontend pesados.🏛️ Arquitectura del SistemaFragmento de códigoflowchart TD
    subgraph Engine [BEACON Core Engine]
        A[UptimeScheduler: @Scheduled 60s] -->|HTTP Ping Asíncrono| B(Servicios Objetivo: PULSE / ATMOS)
        B -->|Respuesta HTTP & Latencia ms| A
        A -->|Persistencia Transaccional| C[(PostgreSQL / H2: Monitors & PingLogs)]
    end

    subgraph API & UI [Observability Layer]
        D[MonitorController: REST Endpoints] -->|Consultas JPA| C
        E[Dashboard Web: static/index.html] -->|Polling Reactivo cada 15s| D
    end
✨ Características Principales⏱️ Motor de Health Checks Automático: Sondeo continuo cada 60 segundos con medición de latencia en milisegundos y captura de códigos de estado HTTP (2xx, 4xx, 5xx).💾 Persistencia Transaccional: Registro histórico de pings mediante Spring Data JPA e Hibernate, permitiendo auditar la estabilidad temporal de cada endpoint.🌐 API RESTful Completa:GET /api/monitors: Listado de todos los servicios registrados con su último estado y tiempo de respuesta.GET /api/monitors/{id}/logs: Obtención de los últimos 50 registros para renderizar el historial de disponibilidad.POST /api/monitors: Registro dinámico de nuevas URLs a monitorizar.📊 Status Page Integrada: Dashboard responsivo en modo oscuro con indicadores de estado pulsantes (All Systems Operational), métricas de disponibilidad (Uptime %) y barras de estado por servicio.🐳 Contenedorización Multi-Stage: Dockerfile estructurado en dos etapas (compilación con Maven y ejecución sobre Alpine JRE) para minimizar el tamaño final de la imagen y optimizar el consumo de recursos.🛠️ Stack TecnológicoCapa / ComponenteTecnologíaRol TécnicoBackend CoreJava 17Lenguaje con tipado estricto, gestión de hilos y concurrencia moderna.FrameworkSpring Boot 3.2Inversión de control (IoC), inyección de dependencias y configuración declarativa.PersistenciaSpring Data JPA / HibernateAbstracción de acceso a datos y mapeo objeto-relacional (ORM).Base de DatosPostgreSQL / H2 DatabasePostgreSQL para producción y base de datos H2 en memoria para pruebas rápidas.Networkingjava.net.http.HttpClientPeticiones HTTP eficientes con soporte HTTP/2 y gestión de tiempos de espera.Frontend / DashboardHTML5 Semántico, CSS Grid/Flexbox, ES6+Interfaz visual ligera y reactiva sin sobrecarga de dependencias.DespliegueDockerEmpaquetado portátil y reproducible para entornos cloud.📂 Estructura del RepositorioPlaintextBEACON/
├── Dockerfile                                 # Compilación y runtime optimizado en contenedor
├── pom.xml                                    # Dependencias del proyecto (Spring Web, Data JPA, PostgreSQL)
└── src/
    └── main/
        ├── java/com/beacon/
        │   ├── BeaconApplication.java         # Clase principal con @EnableScheduling
        │   ├── controller/
        │   │   └── MonitorController.java     # Endpoints REST expuestos
        │   ├── model/
        │   │   ├── Monitor.java               # Entidad del servicio monitorizado
        │   │   └── PingLog.java               # Entidad del registro histórico de pings
        │   ├── repository/
        │   │   ├── MonitorRepository.java     # Consultas Spring Data JPA para monitores
        │   │   └── PingLogRepository.java     # Consultas Spring Data JPA para logs históricos
        │   └── service/
        │       └── UptimeScheduler.java       # Vigilante de red (Health check engine)
        └── resources/
            ├── application.properties         # Configuración de base de datos y puertos
            └── static/
                └── index.html                 # Status Page y Dashboard visual
🚀 Despliegue y Ejecución LocalOpción 1: Con Docker (Recomendado)Bash# 1. Clonar el repositorio
git clone https://github.com/roberttedt-jr/BEACON.git
cd BEACON

# 2. Construir la imagen Docker
docker build -t beacon-app .

# 3. Levantar el contenedor en el puerto 8080
docker run -d -p 8080:8080 --name beacon beacon-app
Opción 2: Con Maven y Java 17Bash# Ejecutar directamente con el plugin de Spring Boot
./mvnw spring-boot:run
Una vez iniciado, accede al dashboard interactivo en tu navegador:http://localhost:8080🔮 Roadmap / Próximas Mejoras[ ] Integración de notificaciones automáticas mediante Webhooks hacia Telegram / Discord ante eventos de caída de servicio.[ ] Soporte para comprobaciones de sockets TCP e ICMP (Ping de red) directo a direcciones IP.[ ] Pipeline de integración continua (CI/CD) con GitHub Actions para validación de compilación y publicación de imagen en Docker Hub.👨‍💻 AutorRoberto MuñozGitHub: @roberttedt-jr
