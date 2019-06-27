package online.ahndwon.beatbox

class Sound(val assetPath: String) {
    val name : String
    var soundId : Int? = null
    var streamId : Int? = null

    init {
        val components = assetPath.split("/")
        val fileName = components[components.size - 1]
        name = fileName.replace(".wav", "")
    }


}