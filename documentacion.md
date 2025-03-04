# Calculadora JavaFX - Documentación del Proyecto

## Índice
1. [Introducción](#1-introducción)
2. [Características](#2-características)
3. [Estructura de la Aplicación](#4-estructura-de-la-aplicación)
   1. [Menú Modo](#31-menú-modo)
   2. [Menú Edición](#32-menú-edición)
   3. [Menú Ayuda](#33-menú-ayuda)
4. [Historial de Operaciones](#4-historial-de-operaciones)
5. [Ejemplo de Uso](#5-ejemplo-de-uso)
6. [Conclusión](#6-conclusión)

## 1. Introducción
Esta aplicación es una calculadora desarrollada en JavaFX, diseñada para ofrecer diversas funcionalidades de cálculo, conversión y graficación. La aplicación se organiza a través de un menú que permite acceder a distintos modos de operación y opciones adicionales, facilitando una experiencia de usuario intuitiva y completa.

## 2. Características
- **Calculadora Normal:** Realiza operaciones básicas como suma, resta, multiplicación y división.
- **Calculadora Científica:** Permite operaciones matemáticas avanzadas, incluyendo funciones trigonométricas, logarítmicas y exponenciales.
- **Conversión de Monedas:** Convierte montos entre diferentes monedas.
- **Graficador de Funciones:** Permite al usuario introducir y graficar funciones matemáticas.
- **Historial de Operaciones:** Guarda cada operación realizada en la calculadora normal y científica. El historial se presenta en un `ListView` y permite acciones como borrar, guardar en un archivo de texto y cargar datos desde un archivo.

## 3. Estructura de la Aplicación

### 3.1 Menú Modo
El menú **Modo** permite al usuario seleccionar el modo de funcionamiento de la calculadora:
- **Calculadora Normal:** Para operaciones básicas.
- **Calculadora Científica:** Para operaciones y funciones matemáticas avanzadas.
- **Conversión de Monedas:** Para realizar conversiones entre diferentes monedas.
- **Graficador de Funciones:** Para graficar funciones matemáticas ingresadas por el usuario.

### 3.2 Menú Edición
El menú **Edición** está dedicado a la gestión del historial de operaciones:
- **Visualización del Historial:** Cada operación realizada se añade a un `ListView`.
- **Acciones Disponibles:**
  - **Borrar:** Elimina el historial actual.
  - **Guardar:** Exporta el historial a un archivo de texto (.txt).
  - **Cargar:** Permite cargar un archivo de texto con un historial previamente guardado.

### 3.3 Menú Ayuda
El menú **Ayuda** ofrece información general sobre el proyecto:
- **Presentación del Proyecto:** Describe el propósito de la aplicación, sus funcionalidades y una breve guía de uso.

## 4. Historial de Operaciones
El historial recoge todas las operaciones efectuadas en los modos de calculadora normal y científica. Esta funcionalidad mejora la experiencia del usuario al permitir:
- **Revisar** las operaciones anteriores.
- **Guardar** el historial para futuras consultas.
- **Cargar** un historial guardado previamente para retomar sesiones anteriores.

## 5. Ejemplo de Uso
1. **Inicio de la Aplicación:**  
   Al arrancar la aplicación, se muestra la ventana principal con un menú en la parte superior.

2. **Selección de Modo:**  
   El usuario selecciona el modo deseado (por ejemplo, Calculadora Científica) a través del menú **Modo**.

3. **Realización de Operaciones:**  
   Se efectúan operaciones y los resultados se muestran en la pantalla, mientras que cada operación se guarda en el historial.

4. **Gestión del Historial:**  
   Desde el menú **Edición**, el usuario puede:
   - **Borrar** el historial si se desea reiniciar la sesión.
   - **Guardar** el historial en un archivo de texto para conservar un registro.
   - **Cargar** un historial previamente guardado para consultar operaciones pasadas.

5. **Consulta de Ayuda:**  
   Seleccionando el menú **Ayuda**, el usuario obtiene una descripción general del proyecto y de sus funcionalidades.

## 6. Conclusión
Esta aplicación de calculadora en JavaFX es una herramienta multifuncional que integra cálculos básicos y avanzados, conversión de monedas y graficado de funciones, todo en una interfaz amigable. La implementación del historial de operaciones añade un valor adicional, permitiendo gestionar y consultar las operaciones realizadas de forma sencilla.  

*Documento elaborado por Paúl Carvajal*
