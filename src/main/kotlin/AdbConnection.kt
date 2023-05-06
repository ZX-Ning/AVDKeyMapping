import java.io.*
import java.lang.Process

class AdbConnection private constructor (adbCmd: List<String>){
    companion object{
        private var instance :AdbConnection? = null
        fun initConnection(path: List<String>) = instance?: AdbConnection(path).also { instance = it }
    }
    private val process: Process
    init {
        try {
            val pb = ProcessBuilder(adbCmd)
            pb.redirectErrorStream(true)
            pb.redirectOutput(File("adb_out.txt"))
            process = pb.start()
            Thread.sleep(500)
            if (!process.isAlive){
                throw Exception("Is the android device active?")
            }
        }
        catch (e:Exception){
            throw Exception("Error starting adb\n A exception caught: \n  ${e.message}\n")
        }
    }
    private val input = PrintWriter(OutputStreamWriter(process.outputStream))
    private fun sendCmd(cmd: String) = input.println(cmd).also { input.flush() }
    fun touch(p: Point) = sendCmd("input tap ${p.x} ${p.y}")
    fun keyEvent(num :Int) = sendCmd("input keyevent $num")
}