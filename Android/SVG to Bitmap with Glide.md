SVG to Bitmap with Glide

# SVG to Bitmap with Glide
Glide는 Uri를 통해 안드로이드 뷰에 이미지를 띄울 수 있다. 
비트맵일 경우엔 문제가 되지 않지만 SVG 포맷을 뷰에 띄우기 위해선 복잡해진다.
그저 SVG를 그대로 뷰에 띄우는건 Glide의 깃헙에 올라와 있는 예제를 그대로 사용하면 되지만 이미지를 자르기 위해선 SVG파일을 비트맵으로 변환하는 과정에서 처리해주어야 한다.


## 필요 클래스
SVG를 띄우기 위해선 총 4가지의 클래스가 필요하다. 이 클래스들은 Glide의 github에 올라와 있다. [glide svg](https://github.com/bumptech/glide/tree/master/samples/svg)


```java
class SvgDecoder : ResourceDecoder<InputStream, SVG> {

    override fun handles(source: InputStream, options: Options): Boolean {
        // *TODO: Can we tell?*
return true
    }

    @Throws(IOException::class)
    override fun decode(source: InputStream, width: Int, height: Int,
                        options: Options): Resource<SVG> {
        try {
            val svg = SVG.getFromInputStream(source)
            return SimpleResource(svg)
        } catch (ex: SVGParseException) {
            throw IOException("Cannot load SVG from stream", ex)
        }
    }
}
```



```java
class SvgDrawableTranscoder : ResourceTranscoder<SVG, Bitmap> {
    @Nullable
    override fun transcode(toTranscode: Resource<SVG>,
                           options: Options): Resource<Bitmap>? {
        val svg = toTranscode.get()
        val picture = svg.renderToPicture()

        val drawable = Bitmap.createBitmap(picture.*width*, picture.*height*+ 5, Bitmap.Config.ARGB_8888)
        val bitmap = Bitmap.createBitmap(drawable, 0,0,
                drawable.*width*/ 2, drawable.*height*)
        val canvas = Canvas(bitmap)
        canvas.translate(-drawable.*width*/ 2f, 5f)
        svg.renderToCanvas(canvas)

        return SimpleResource(bitmap)
    }
}
```



```java
class SvgModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide,
                           registry: Registry) {
        registry.register(SVG::class.*java*, Bitmap::class.*java*, SvgDrawableTranscoder())
                .append(InputStream::class.*java*, SVG::class.*java*, SvgDecoder())
    }

    // Disable manifest parsing to avoid adding similar modules twice.
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}
```



```java
class SvgSoftwareLayerSetter : RequestListener<Bitmap> {

    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Bitmap>,
                              isFirstResource: Boolean): Boolean {
        val view = (target as ImageViewTarget<*>).*view*
view.setLayerType(ImageView.*LAYER_TYPE_NONE*, null)
        return false
    }

    override fun onResourceReady(resource: Bitmap, model: Any,
                                 target: Target<Bitmap>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
        val view = (target as ImageViewTarget<*>).*view*
view.setLayerType(ImageView.*LAYER_TYPE_SOFTWARE*, null)
        return false
    }
}
```

#android