# Xib
**Xib(XML Interface Builder)** -> 컴파일 -> Nib(NeXTSTEP Interface Builder)

## 준비
1. 프로젝트 생성
2. Storyboard, ViewController 삭제
3. Info.plist에서 Main storyboard file base name 삭제

## 뷰 생성
1. 폴더 오른쪽 클릭해서 View 파일 생성
2. View의 Files’s Owner의 CustomClass를 생성한 ViewController로 설정해주고 
3. AppDelegate의 func application() 에서 UIWindow 설정하고 rootViewController 를 생성한 View 파일 이름에 대해 설정해준다.
```swift
func application(_ application: UIApplication,
                     didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
       let frame = UIScreen.main.bounds
        window = UIWindow(frame: frame)
        window?.backgroundColor = UIColor.yellow

        // FirstController.xib를 로드함
        // window?.rootViewController = SecondController()

        // nibname.xib를 로드함*
        window?.rootViewController = FirstController(nibName: "FirstView", bundle: nil)
        window?.makeKeyAndVisible()
       
        return true
    }
```
4. 생성한 View의 Reference Outlet을 File’s Owner로 등록해준다.
5. Simulator 실행



#iOS