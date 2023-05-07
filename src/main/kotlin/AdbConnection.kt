import java.io.*
import java.lang.Process

class AdbConnection private constructor (adbPath: String){
    companion object{
        private var instance :AdbConnection? = null
        fun initConnection(path: String) = instance?: AdbConnection(path).also { instance = it }
    }
    private val process: Process
    init {
        try {
            val pb = ProcessBuilder(listOf(adbPath,"-e","shell"))
            pb.redirectErrorStream(true)
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
    private val input = PrintWriter(OutputStreamWriter(process.outputStream),true)
    private fun sendCmd(cmd: String) = input.println(cmd)
    fun touch(p: AdbInput.Point) = sendCmd("input tap ${p.x} ${p.y}")
    fun keyEvent(num :Int) = sendCmd("input keyevent $num")
}