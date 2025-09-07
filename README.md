# 🏦 Banking System - Microservicios (Prueba Técnica)

Este proyecto implementa un **sistema bancario distribuido** basado en **microservicios**, con comunicación asincrónica mediante **RabbitMQ**, persistencia en **PostgreSQL** y empaquetado con **Docker**.  

---

## 🚀 Tecnologías utilizadas
- **Java 17**
- **Spring Boot 3**
- **Maven / Gradle**
- **PostgreSQL 15**
- **RabbitMQ 3** (con Management UI)
- **Docker & Docker Compose**

---

## 📂 Estructura del proyecto
```
banking-system/
├── clientes-service/              # Microservicio Clientes
├── cuentas-service/               # Microservicio Cuentas
├── schema.sql                     # Script de base de datos
├── docker-compose.yml             # Orquestación de servicios
├── Microservicios Prueba Tecnica/ # Colección Postman (endpoints)
└── README.md                      # Instrucciones
```

---

## 🛠️ Despliegue con Docker

1. **Clonar el repositorio:**
```bash
git clone https://github.com/tuusuario/banking-system.git
cd banking-system
```

2. **Construir y levantar los servicios:**
```bash
docker compose up -d --build
```

3. **Verificar contenedores:**
```bash
docker ps
```

Deberías tener en ejecución:

- **postgres-db** → PostgreSQL en `localhost:5432`  
- **rabbitmq** → RabbitMQ en `localhost:5672` y UI en [http://localhost:15672](http://localhost:15672)  
- **clientes-service** → [http://localhost:8081](http://localhost:8081)  
- **cuentas-service** → [http://localhost:8082](http://localhost:8082)  

Para detener todo:
```bash
docker compose down
```

---

## 🗄️ Script de Base de Datos (`schema.sql`)
```sql
CREATE TABLE persona (
    id_persona SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    genero VARCHAR(10),
    edad INT,
    identificacion VARCHAR(20) UNIQUE NOT NULL,
    direccion VARCHAR(200),
    telefono VARCHAR(20)
);

CREATE TABLE cliente (
    id_cliente BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    id_persona INT NOT NULL,
    contrasena VARCHAR(100) NOT NULL,
    estado BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_cliente_persona FOREIGN KEY (id_persona) REFERENCES persona (id_persona) ON DELETE CASCADE
);

CREATE TABLE cuenta (
    id_cuenta BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    numero_cuenta VARCHAR(50) UNIQUE NOT NULL,
    tipo_cuenta VARCHAR(20) NOT NULL,
    saldo_inicial DECIMAL(15,2) NOT NULL,
    estado BOOLEAN DEFAULT TRUE,
    id_cliente BIGINT NOT NULL,
    CONSTRAINT fk_cuenta_cliente FOREIGN KEY (id_cliente) REFERENCES cliente (id_cliente) ON DELETE CASCADE
);

CREATE TABLE movimiento (
    id_movimiento BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tipo_movimiento VARCHAR(20) NOT NULL,
    valor DECIMAL(15,2) NOT NULL,
    saldo DECIMAL(15,2) NOT NULL,
    id_cuenta BIGINT NOT NULL,
    CONSTRAINT fk_movimiento_cuenta FOREIGN KEY (id_cuenta) REFERENCES cuenta (id_cuenta) ON DELETE CASCADE
);
```

---

## 📡 Endpoints disponibles

### 📍 Clientes Service → `http://localhost:8081/clientes`

- **POST /clientes** → Crear cliente  
```json
{
  "persona": {
    "nombre": "Carlos Perez",
    "genero": "M",
    "edad": 30,
    "identificacion": "1234567890",
    "direccion": "Av. Siempre Viva",
    "telefono": "0987654321"
  },
  "contrasena": "abc123",
  "estado": true
}
```

- **GET /clientes** → Listar clientes  
- **GET /clientes/{id}** → Obtener cliente por ID  
- **PUT /clientes/{id}** → Actualizar cliente  
- **DELETE /clientes/{id}** → Eliminar cliente  

---

### 📍 Cuentas Service → `http://localhost:8082/cuentas`

- **POST /cuentas** → Crear cuenta  
```json
{
  "idCliente": 1,
  "numeroCuenta": "585435",
  "tipoCuenta": "Ahorros",
  "saldoInicial": 1000,
  "estado": true
}
```

- **GET /cuentas** → Listar cuentas  
- **GET /cuentas/{id}** → Obtener cuenta por ID  
- **PUT /cuentas/{id}** → Actualizar cuenta  
- **DELETE /cuentas/{id}** → Eliminar cuenta  

---

### 📍 Movimientos → `http://localhost:8082/movimientos`

- **POST /movimientos** → Registrar movimiento  
```json
{
  "tipoMovimiento": "Deposito",
  "valor": 500,
  "idCuenta": 1
}
```

- **GET /movimientos/cuenta/{idCuenta}** → Listar movimientos de una cuenta  

---

## 📨 RabbitMQ
- Cuando se crea un cliente, se publica un evento en RabbitMQ: **`cliente.creado`**  
- El microservicio **Cuentas** escucha este evento para asociar cuentas automáticamente.  
- Puedes visualizar los mensajes en la UI: [http://localhost:15672](http://localhost:15672)  
  - Usuario: `guest`  
  - Contraseña: `guest`  

---

## ✅ Pruebas Unitarias e Integración
Ejecutar pruebas:  
```bash
# En clientes-service
mvn test

# En cuentas-service
gradle test
```

---

## 📦 Entregables
- `schema.sql` con el modelo de datos  
- Código fuente completo (`clientes-service`, `cuentas-service`)  
- `docker-compose.yml` para levantar todo el sistema  
- `README.md` con instrucciones, SQL y endpoints  
- Colección Postman con endpoints (`Microservicios Prueba Tecnica`)  

---
