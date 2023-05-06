class Config{
    companion object{
        val adbCmd = listOf("adb", "-e", "shell")
        val keymaps: Map<String,AdbInput> = mapOf(
            Pair("SPACE",Point(1225.0,600.0)),
            Pair("TAB",Point(870.0,320.0)),
            Pair("R",Point(1650.0,80.0)),
            Pair("E",Point(1800.0,80.0)),
            Pair("W",Point(1840.0,990.0)),
            Pair("Q",AdbKeyEvent(4))
        )
    }
}
interface AdbInput
data class AdbKeyEvent(val num:Int):AdbInput
data class Point(val x :Double, val y :Double):AdbInput