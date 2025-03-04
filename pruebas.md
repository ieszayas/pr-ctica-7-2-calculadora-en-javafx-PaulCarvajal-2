
## Pruebas (testing)


| ID Caso Prueba | Descripción Caso de Prueba                         | Entrada            | Salida Esperada | Resultado |
|---------------|-------------------------------------------------|--------------------|----------------|-----------|
| CP-001       | Suma de dos números positivos                    | 5 + 3 =            | 8              |      8     |
| CP-002       | Suma de número positivo y negativo               | 7 + (-2) =         | 5              |      5     |
| CP-003       | Resta de dos números                             | 9 - 4 =            | 5              |      5     |
| CP-004       | Multiplicación de dos números positivos          | 6 * 3 =            | 18             |      18     |
| CP-005       | Multiplicación con un número negativo            | 4 * (-2) =         | -8             |     -8      |
| CP-006       | División de dos números                          | 8 / 2 =            | 4              |      4     |
| CP-007       | División entre cero                              | 5 / 0 =            | Error          |      ERROR     |
| CP-008       | Agregar decimal a un número                      | 3.5                | 3.5            |      3.5     |
| CP-009       | Operación con números decimales                  | 2.5 + 1.5 =        | 4.0            |       4    |
| CP-010       | Borrar resultados                                | 5 + 3, luego C     | Pantalla vacía |      Pantalla vacía     |
| CP-011       | Uso del botón igual sin operación                | =                  | Mismo número   |      Mismo numero     |
| CP-012       | Guardar/sumar número en memoria (M+)             | 6, M+              | Memoria = +6   |       M = 6    |
| CP-013       | Borrar número en memoria (MR)                    | MC                 | Memoria = 0    |    0       |
| CP-014       | Recuperar número de memoria                      | MR                 | Recupera último memoria |    Recupera ultima numero       |
| CP-015       | Cambio de signo positivo a negativo              | 5, +/-             | -5             |      -5     |
| CP-016       | Cambio de signo negativo a positivo              | -7, +/-            | 7              |      7     |
| CP-017       | Presionar dos veces botón igual                  | = =                | Mantiene valor actual |     mensaje Alerta      |
| CP-018       | Presionar botón operación dos veces              | + *                | Cambia + a *   |      cambia de operación     |
| CP-019       | Los colores y tamaños de los botones cumplen criterios de usabilidad/accesibilidad    | N/D           |  Cumple WCAG nivel A y criterios de accesibilidad básicos   |  Cumplen    |
| CP-020       | Seleccionar el modo en el menú                   | normal, científica, conversión       | cambiar modo   |      cambia de modo     |
| CP-021       | Seleccionar el menú edición>borrar               |                    | Borrar campos   |    borra el historial       |
| CP-021       | Seleccionar el menú ayuda>acerca                 |                    | Mostrar ventana modal alert-info   |      ventana de acerca de     |
| CP-022       | Elegir combobox de conversiones                  | Desplegar opción   | Ajuste combobox origen-destino   |       Combobox de conversiones    |
| CP-023       | Realizar conversiones                            | Valor conversión   | Conversión en unidades de destino   |    Realiza conversiones       |
| CP-024       | Estructura del proyecto                          | N/D                | Se utiliza la división por paquetes `MVC`; Vista Controlador Modelo para organizar las clases. |  Utilizado el modelo MVC   |
| CP-025       | Comprobación fichero `jar`                       | Proyecto compilado | Se genera y prueba el fichero `jar` empaquetado. | JAR generado |