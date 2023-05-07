import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import java.util.concurrent.ArrayBlockingQueue

class KeyListener private constructor(private val keyEventsList: ArrayBlockingQueue<String>) : NativeKeyListener {
    companion object {
        private var instance: KeyListener? = null
        fun initialKeyListener(keyEventsList: ArrayBlockingQueue<String>): KeyListener {
            if (instance == null) {
                GlobalScreen.registerNativeHook()
                instance = KeyListener(keyEventsList)
                GlobalScreen.addNativeKeyListener(instance)
                return instance!!
            } else return instance!!
        }
    }

    private val NativeKeyEvent.keyText: String get() = NativeKeyEvent.getKeyText(this.keyCode)
    private val keyPressedSet = HashSet<String>()
    override fun nativeKeyPressed(keyEvent: NativeKeyEvent) {
        val key = keyEvent.keyText.uppercase()
        if (!keyPressedSet.contains(key)) {
            keyPressedSet.add(key)
            if (Config.keymaps.containsKey(key)) {
                keyEventsList.add(key)
            }
        }
    }

    override fun nativeKeyReleased(keyEvent: NativeKeyEvent) {
        val key = keyEvent.keyText.uppercase()
        if (keyPressedSet.contains(key)) {
            keyPressedSet.remove(key)
        }
    }
}