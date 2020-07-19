# Computación Orientada a Servicios
### Sistema de gestión para la condición corporal de bovinos 
> Trabajo final de catedra

![icon](/src/main/resources/icon.png)

La condición corporal es la evaluación visual de la cantidad de músculo y grasa que cubren los huesos del cuerpo, existiendo una alta correlación entre la clasificación de condición corporal y el porcentaje de grasa corporal de una vaca. Es una puntuación numérica, que toma valores entre 1 y 9 la cual se  realiza observando puntos específicos del cuerpo del animal, por lo que se evalúa independientemente del peso corporal o llenado del abdomen por alimentación y/o preñado. Este es uno de los mejores indicadores del estado nutricional de la vaca, con lo cual es un importante determinante del desempeño reproductivo las mismas. 

El objetivo de este trabajo final es utilizar los conocimientos de aprendidos en la cátedra _‘Computación Orientada a Servicios’_ para implementar una API REST, utilizando Spring Boot, que provea los servicios suficientes para el manejo y administración de los valores de condición corporal de bovinos, tanto de forma individual así como también el promedio de los valores de las vacas que pertenezcan a un mismo rodeo. Adicionalmente se mantendrá persistencia del historial de estos valores para cada animal y se permitirá programar alertas que notifican cuando el valor de condición corporal sobrepase o disminuya determinado límite, tanto para un determinado animal como valor promedio de un rodeo.
