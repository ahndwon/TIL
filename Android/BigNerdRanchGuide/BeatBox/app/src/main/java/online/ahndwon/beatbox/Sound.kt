package online.ahndwon.beatbox

class Sound(private val assetPath: String) {
    val name : String
    init {
        val components = assetPath.split("/")
        val fileName = components[components.size - 1]
        name = fileName.replace(".wav", "")
    }


}