class Config{
    companion object{
        const val adbPath = "adb"
        val keymaps: Map<String,AdbInput> = mapOf(
            Pair("SPACE",AdbInput.Point(1225,600)),
            Pair("TAB",AdbInput.Point(870,320)),
            Pair("R",AdbInput.Point(1650,80)),
            Pair("E",AdbInput.Point(1800,80)),
            Pair("W",AdbInput.Point(1840,990)),
            Pair("Q", AdbInput.KeyPress("BACK"))
        )
    }
}
sealed interface AdbInput{
    data class KeyPress(val key :String):AdbInput
    data class Point(val x :Int, val y :Int):AdbInput
}
