import java.util.concurrent.ArrayBlockingQueue
import kotlin.system.exitProcess

private val keyEvents = ArrayBlockingQueue<String>(30)
private lateinit var adb: AdbConnection
fun main(){
    println("hello world!")
    KeyListener.initialKeyListener(keyEvents)
    try{
        adb = AdbConnection.initConnection(Config.adbCmd)
        println("adb connected")
        mainEventLoop()
    }
    catch (e:Exception){
        System.err.println(e.message)
        exitProcess(1)
    }
}

fun mainEventLoop(){
    while (true){
        if (keyEvents.isEmpty())
            continue
        val key = keyEvents.remove()!!.uppercase()
        print("key: $key is triggered: ")
        onKeyPressed(key)
    }
}

fun onKeyPressed(key: String){
    val input = Config.keymaps[key]!!
    if (input is Point){
       println("touching $input")
        adb.touch(input)
    }
    else if (input is AdbKeyEvent){
        println("sending key event ${input.num}")
        adb.keyEvent(input.num)
    }
}
