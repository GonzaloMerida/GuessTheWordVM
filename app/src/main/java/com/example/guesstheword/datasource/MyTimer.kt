package com.example.guesstheword.datasource

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
//productor de un flujo de datos
//Clase temporizador. El parámetro son: el valor hasta el que emite valores, el incremento y el tiempo de parada del flujo.
class MyTimer(private val max: Float, private val step: Float, private val refreshIntervalTime : Long) {

    val timer : Flow<Float> = flow<Float>  {
        var i=0f
        while (i<=max) {
            emit(i)
            i+=step
            delay(refreshIntervalTime)
        }
    }
}