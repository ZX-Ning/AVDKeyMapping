import java.io.File
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.Socket
import java.util.concurrent.TimeUnit

class MonkeyConnection private constructor(private val adbPath: String) {
    companion object {
        const val PORT = 1080
        private var instance: MonkeyConnection? = null
        fun initConnection(path: String) = instance ?: MonkeyConnection(path).also { instance = it }
    }

    private val socket: Socket
    private val input: PrintWriter

    init {
        Runtime.getRuntime().exec("$adbPath shell killall com.android.commands.monkey")
            .waitFor(2000,TimeUnit.MILLISECONDS)
        Runtime.getRuntime().exec("$adbPath forward tcp:$PORT tcp:$PORT")
            .waitFor(1000,TimeUnit.MILLISECONDS)
        val process = ProcessBuilder(adbPath,"shell","monkey","--port", PORT.toString())
            .redirectErrorStream(true)
            .redirectOutput(File("out.txt"))
            .start()
            .also { it.waitFor(300, TimeUnit.MILLISECONDS) }
        if (!process.isAlive){
            throw Exception("Failed to connect. Is the android device active?")
        }
        socket = Socket("127.0.0.1", PORT)
        input = PrintWriter(OutputStreamWriter(socket.getOutputStream()), true)
    }

    private fun sendMonkeyCmd(cmd: String) {
        input.println(cmd)
    }

    fun touch(p: AdbInput.Point) {
        sendMonkeyCmd("tap ${p.x} ${p.y}")
    }

    fun sendKey(key: AdbInput.KeyPress) {
        sendMonkeyCmd("press ${key.key}")
    }

}