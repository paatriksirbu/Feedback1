
# **Feedback1 - Aplicación de Gestión de Novelas con Reseñas**

## **Descripción General**

**Feedback1** es una aplicación desarrollada para gestionar novelas y sus reseñas. A través de esta aplicación, los usuarios pueden crear, ver y seleccionar novelas, así como añadir reseñas a las mismas. El objetivo de la app es proporcionar una interfaz limpia y fácil de usar para gestionar tanto las novelas como sus comentarios de una manera fluida y eficiente.

La aplicación está construida en **Kotlin** utilizando **Room** como base de datos local para el almacenamiento persistente de novelas y reseñas. Además, se hace uso de **MVVM** (Model-View-ViewModel) para mantener una separación clara entre la lógica de negocio y la interfaz de usuario, asegurando un código más modular y fácil de mantener.

## **Arquitectura y Componentes**

El proyecto sigue la arquitectura **MVVM**, con la incorporación de una base de datos local usando **Room** para persistir los datos. A continuación se describe cada componente relevante del proyecto:

---

## **Clases Principales**

### 1. **`Novel`**
Esta clase representa una **entidad** en la base de datos que modela las novelas en la aplicación.

- **Atributos**:
    - `id`: Identificador único de la novela (Primary Key).
    - `titulo`: El título de la novela.
    - `autor`: El autor de la novela.
    - `descripcion`: Descripción breve de la novela.

- **Uso**: Esta entidad se utiliza para almacenar y gestionar la información relacionada con las novelas que el usuario añade en la app.

### 2. **`Review`**
Representa una **entidad** que modela las reseñas asociadas a cada novela.

- **Atributos**:
    - `id`: Identificador único de la reseña (Primary Key).
    - `novelId`: El ID de la novela a la que se asocia la reseña (Foreign Key).
    - `comentario`: El comentario que el usuario escribe para la novela.
    - `rating`: Calificación numérica de la reseña.

- **Uso**: Permite que los usuarios añadan opiniones y valoraciones a las novelas seleccionadas.

---

## **Capas del Proyecto**

### **1. Data Layer (Capa de Datos)**

#### 1.1. **`NovelDAO`**
Es la interfaz DAO que define las **operaciones de acceso a datos** sobre las novelas.

- **Funciones principales**:
    - `insert(novel: Novel)`: Inserta una novela en la base de datos.
    - `delete(novel: Novel)`: Elimina una novela de la base de datos.
    - `getAllNovels()`: Obtiene una lista de todas las novelas almacenadas.

#### 1.2. **`ReviewDAO`**
Define las operaciones de acceso a datos para las reseñas.

- **Funciones principales**:
    - `insert(review: Review)`: Inserta una reseña.
    - `getReviewsByNovel(novelId: Int)`: Obtiene todas las reseñas asociadas a una novela.

#### 1.3. **`NovelDatabase`**
Clase que implementa la **Room Database** para gestionar las novelas y las reseñas.

- **Uso**:
    - Define la base de datos con las entidades `Novel` y `Review`.
    - Proporciona una instancia singleton de la base de datos para evitar múltiples conexiones.

### **2. Domain Layer (Capa de Dominio)**

#### 2.1. **`NovelRepository`**
El repositorio actúa como el **puente** entre el DAO y la capa de presentación.

- **Funciones principales**:
    - `insert(novel: Novel)`: Inserta una novela utilizando un hilo de fondo.
    - `delete(novel: Novel)`: Elimina una novela en segundo plano.
    - `getAllNovels()`: Devuelve todas las novelas en forma de lista.
    - Implementa un `ExecutorService` para gestionar las operaciones en segundo plano.

#### 2.2. **`ReviewRepository`**
Maneja la interacción con el DAO de reseñas.

- **Funciones principales**:
    - `insert(review: Review)`: Inserta una reseña en segundo plano.
    - `getReviewsByNovel(novelId: Int)`: Recupera todas las reseñas para una novela específica.

### **3. Presentation Layer (Capa de Presentación)**

#### 3.1. **`NovelViewModel`**
Clase que sigue el patrón **ViewModel** y gestiona la lógica de presentación relacionada con las novelas.

- **Funciones principales**:
    - `insert(novel: Novel)`: Llama al repositorio para insertar una novela.
    - `delete(novel: Novel)`: Llama al repositorio para eliminar una novela.
    - `getAllNovels()`: Obtiene todas las novelas de la base de datos a través del repositorio.

#### 3.2. **`ReviewViewModel`**
Clase ViewModel para la gestión de las reseñas.

- **Funciones principales**:
    - `insert(review: Review)`: Llama al repositorio para insertar una reseña.
    - `getReviewsByNovel(novelId: Int)`: Recupera todas las reseñas para una novela.

### **4. UI (Interfaz de Usuario)**

#### 4.1. **`MainActivity`**
Pantalla principal de la aplicación donde los usuarios pueden **visualizar** las novelas disponibles y acceder a otras funcionalidades.

- **Funciones principales**:
    - Carga y muestra la lista de novelas desde la base de datos.
    - Permite al usuario seleccionar una novela para verla en detalle o añadir reseñas.

#### 4.2. **`ReviewsActivity`**
Pantalla secundaria donde el usuario puede **visualizar y añadir reseñas** a una novela seleccionada.

- **Uso**:
    - Muestra todas las reseñas relacionadas con la novela seleccionada.
    - Permite al usuario añadir una nueva reseña.
    - Implementa la lógica para obtener el ID de la novela a través de `Intent` y carga la información asociada.

- **Error handling**: Notifica al usuario si no hay reseñas o si hubo algún problema al cargar los datos.

#### 4.3. **Popup para Selección de Novelas**
En la pantalla principal se incluye un popup que permite al usuario seleccionar una novela para ver sus reseñas o añadir una nueva.

- **Funcionalidad**:
    - Este popup muestra los títulos de las novelas en una lista.
    - Al seleccionar una novela, el ID de la misma se pasa a la actividad de reseñas mediante un `Intent`.

---

## **Características Clave**

1. **Persistencia de Datos**: Gracias a Room, las novelas y sus reseñas se almacenan localmente, lo que permite acceder a ellas incluso cuando la app se cierra y vuelve a abrir.

2. **Gestión en Segundo Plano**: Todas las operaciones de inserción, eliminación y consulta de la base de datos se realizan en hilos de fondo para garantizar un rendimiento fluido sin bloquear la UI.

3. **Arquitectura MVVM**: La aplicación está diseñada siguiendo el patrón MVVM para una clara separación de responsabilidades y para facilitar la escalabilidad.

4. **Interfaz de Usuario Simple y Eficaz**: La UI es minimalista, lo que proporciona una experiencia de usuario amigable y sencilla, con botones que resaltan sobre el fondo beige suave.

---

## **Conclusión**

Este proyecto proporciona una solución robusta para la gestión de novelas y sus reseñas, utilizando tecnologías modernas como Room, Kotlin Coroutines y MVVM. El diseño modular y las operaciones en segundo plano aseguran un rendimiento óptimo y una interfaz de usuario eficiente.

---


## **Contacto** 

Patrik Paul Sirbu - https://github.com/paatriksirbu

Mail: patrikpsirbu@gmail.com

Enlace del proyecto: https://github.com/paatriksirbu/Feedback1.git
