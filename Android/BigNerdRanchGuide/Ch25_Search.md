# Ch25_Search
## SearchView 생성
메뉴 리소스에 메뉴 생성
`menu/fragment_photo_gallery_menu.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android" xmlns:app="http://schemas.android.com/apk/res-auto">
    <item android:id="@+id/menu_item_search"
          android:title="검색"
          app:actionViewClass="androidx.appcompat.widget.SearchView"
          app:showAsAction="ifRoom"
    />

    <item android:id="@+id/menu_item_clear"
          android:title="검색 값 지움"
          app:showAsAction="never"
    />
</menu>

```

사용할 프래그먼트의 onCreate()에서 `setHasOptionsMenu(true)`를 통해 옵션메뉴를 키고 `onCreateOptionsMenu()`를 통해 menu를 inflate 해준다

```kotlin
override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
    super.onCreateOptionsMenu(menu, inflater)
    inflater?.inflate(R.menu.fragment_photo_gallery, menu)

    val searchItem = menu?.findItem(R.id.menu_item_searc/)
    val searchView = searchItem?.actionViewas SearchView?

    searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String?): Boolean {
            Log.d(PhotoGalleryFragment::class.java.name, " QueryTextChange $newText")
            return false
        }

        override fun onQueryTextSubmit(query: String?): Boolean {
            Log.d(PhotoGalleryFragment::class.java.name, " QueryTextSubmit $query")
            updateItems()
            return true
        }

    })
}

```



## 공유 프레퍼런스를 사용한 간단한 데이터 보존
공유 프레퍼런스는 파일 시스템의 파일이며, **SharedPreferences** 클래스를 사용해서 데이터를 읽거나 수정한다.
**SharedPreference**는 Bundle과 흡스하게 키-값으로 이루어져 있다.
파일은 xml 파일로 애플리케이션의 샌드박스 









#android/실무에바로적용하는안드로이드