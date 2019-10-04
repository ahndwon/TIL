Window, Surface

# Window, Surface
[What is an Android window? - Stack Overflow](https://stackoverflow.com/questions/9451755/what-is-an-android-window#targetText=A%20window%20is%20basically%20like,to%20the%20application%20for%20drawing.)

## Window
데스크탑의 윈도우와 비슷.
윈도우에 컨텐츠가 렌더링되는 단 하나의 `Surface`. 
어플리케이션은  `WindowManager`와 상호작용하여 window를 생성한다. 이때 Window Manager는 각각의 window를 위해 Surface를 하나씩 생성하고 drawing을 위해 어플리케이션에게 제공한다.
어플리케이션은 Surface에 아무거나 그릴 수 있다. WindowManager에게 Surface는 단순히 불투명한 직사각형이다.

## Surface
화면에 보여질 픽셀들을 가지는 Object.
화면에 보여지는 모든 window (dialog, full-screen activity, status bar)는 모두 각각의 surface를 가지며 거기에 뷰를 그린다. `Surface Flinger` 는 그려진 Surface들을 Z-order에 알맞게 디스플레이에 렌더링한다.
Surface는 double-buffered rendering을 위해 주로 여러개의 버퍼를 (주로 2개) 갖는다. 이로 인해 어플리케이션은 어플리케이션이 drawing 작업 완수하길 기다릴 필요없이  surface flinger가 마지막 버퍼를 통해 화면을 그리는 동안 다음 UI state를 그릴 수 있다.


## View
Window 안의 상호작용 가능한 UI Element. Window는 하나의 뷰 계층이 부착되어 있으며 window의 모든 행동을 제공한다. 언제든 Window 가 다시 그려져야 할 경우 (뷰가 invalidate 되었을시 등) , 이것은 window의 surface에 일어난다. Surface가 lock 되면 그릴 수 있는 Canvas를 반환한다. 뷰 계층을 따라 `draw traversal`가 일어나는데 이때 Canvas가 각각의 뷰가 그릴 수 있도록 계층을 따라 전달된다.
순회가 끝날 시 Surface는 다시 unlock 되고 post 되어 방금 그린  buffer가 Surface Flinger에 의해 composite 될 수 있도록 foreground로 swap 된다.



## 정리
Window 는 하나의 Surface를 가지고 있으며 View는 UI element로서 Surface에 그려진다.  Surface는  Canvas를 가지고 있으며  뷰 계층을 따라 View가 캔버스에 그려진다.

 -> 고로 Window는 Surface와 View를 가지고 있음

#네이버-인턴