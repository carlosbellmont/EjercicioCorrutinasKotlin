import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

var cubosActuales = 0
var lenaActual = 0
var ramasActuales = 0
var comidaActual = 0

const val CUBOS_NECESARIOS = 4
const val LENA_NECESARIA  = 2
const val RAMA_NECESARIA  = 1
const val COMIDA_NECESARIA  = 1

var hamaca = Mutex()
var hacha = Mutex()

fun main() {
    comenzar()
    Thread.sleep(80000)
}


fun comenzar(){
    GlobalScope.launch {
        coroutineScope {
            // Amigo A
            launch {
                repeat(CUBOS_NECESARIOS) {
                    irAPorAgua("Amigo A")
                    descansar(1000, "Amigo A")
                }
            }
            // Amigo B
            launch {
                repeat(LENA_NECESARIA){
                    irAPorLena("Amigo B")
                    descansar(3000, "Amigo B")
                }
            }
            // Amigo C
            launch {
                irAPorRamas("Amigo C")
                irACazar("Amigo C")
            }

        }
        if (cubosActuales == CUBOS_NECESARIOS && lenaActual == LENA_NECESARIA && ramasActuales == RAMA_NECESARIA && comidaActual == COMIDA_NECESARIA){
            println("Barca construida y aprovisionada con exito")
        } else {
            println("Algo ha fallado")
        }
    }
}

suspend fun irACazar(nombre : String) {
    println("El amigo $nombre va a Cazar")
    hacha.withLock {
        println("El amigo $nombre coge el hacha")
        delay(4000)
        comidaActual++
        println("El amigo $nombre deja el hacha")
    }
    println("El amigo $nombre va a por leña")
}

suspend fun descansar(tiempo : Long, nombre : String) {
    println("El amigo $nombre, quiere descansar")
    hamaca.withLock {
        println("El amigo $nombre, se tumba en la hamaca")
        delay(tiempo)
        println("El amigo $nombre, se levanta de la hamaca")
    }
    println("El amigo $nombre, deja de descansar")
}

suspend fun irAPorLena(nombre : String) {
    println("El amigo $nombre va a por leña")
    hacha.withLock {
        println("El amigo $nombre coge el hacha")
        delay(5000)
        lenaActual++
        println("El amigo $nombre deja el hacha")
    }
    println("El amigo $nombre vuelve con la leña")
}

suspend fun irAPorRamas(nombre : String) {
    println("El amigo $nombre va a por ramas")
    delay(3000)
    ramasActuales++
    println("El amigo $nombre vuelve con ramas")
}

suspend fun irAPorAgua(nombre : String) {
    println("El amigo $nombre va a por un cubo de agua")
    delay(3000)
    cubosActuales++
    println("El amigo $nombre vuelve con un cubo de agua")
}
