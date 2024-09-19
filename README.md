# Quick cloth - Application Service

Servicio encargado de la lógica de negocio de la aplicación Quick Cloth.

## Variables de Entorno 🔒

Para ejecutar este proyecto, necesitarás agregar las siguientes variables de entorno a tu archivo .env

`DATA_URL`

## Ejecutar Localmente 💻

Clona el proyecto

```bash
  git clone https://github.com/quick-cloth/quick-cloth-app.git
```

Ve al directorio del proyecto

```bash
  cd quick-cloth-application
```

Instala las dependencias

```bash
  mvn install
```

Inicia el servidor

```bash
  mvn spring-boot:run
```

## Ejecutar con Docker 🐳

### Construir la imagen

Abre una terminal en el directorio raíz de tu proyecto (donde se encuentra el Dockerfile) y ejecuta el siguiente comando:

```bash
  docker build --build-arg DATA_URL=myDataServiceURL -t quick-cloth-app .
```

### Correr el contenedor

Una vez creada la imagen, puede ejecutar el contenedor con el siguiente comando:

```bash
  docker run -d -p 8080:8080 --name my-container quick-cloth-app
```