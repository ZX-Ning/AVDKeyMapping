import java.util.concurrent.ArrayBlockingQueue
import kotlin.system.exitProcess

private val keyEvents = ArrayBlockingQueue<String>(30)
//private lateinit var adb: AdbConnection
private lateinit var monkey: MonkeyConnection
fun main(){
    println("Hello World!\nAVDTool")
    KeyListener.initialKeyListener(keyEvents)
    try{
//        adb = AdbConnection.initConnection(Config.adbPath)
        monkey = MonkeyConnection.initConnection(Config.adbPath)
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
    if (input is AdbInput.Point){
       println("touching $input")
       monkey.touch(input)
    }
    else if (input is AdbInput.KeyPress){
        println("sending key ${input.key}")
        monkey.sendKey(input)
    }
}
