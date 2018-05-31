# 180529
**HTML <img> 와 CSS background-image 의 차이점**
1. 접근성  (HTML > CSS)
	<img> 태그를 사용하면 alt text와 title attribute를 추가할 수 있으므로 스크린 리더가 읽을 수도 있고 구글 검색에도 포함될 수 있게 해준다.
2. 성능  (HTML > CSS)
	CSS에 많은 이미지가 선언되어 있으면 한번에 많은 이미지들이 실행되어야 한다. 하지만 <img>태그를 사용하면 필요할때 실행되어서 부하가 적다. 그리고 또한 picturefill이나 lazy loading으로 더 성능 향상을 꾀할 수 있다.
3. 조작 가능성 (HTML < CSS)
	background를 사용하면 background-color, background-repeats, background-attachment, background-position, background-blend-mode를 혼합해서 사용가능하므로 많은 가능성을 열어준다.
그리고 이미지 위에 텍스트를 보이게 할 경우 CSS 가 더 쉽다.

- 요약 : 접근성과  SEO(Search Engine Optimization)이 중요할 땐 HTML <img> 태그를 사용하는 것이 좋다. 

참고 - [Should I use an HTML img tag or a CSS background-image? | Build Awesome Websites](http://buildawesomewebsites.com/blog/html-img-tags-vs-css-background-images)



[A Complete Guide to Flexbox | CSS-Tricks](https://css-tricks.com/snippets/css/a-guide-to-flexbox/)

**style css 파일로 빼기**
html head에 `<link href="./index.css" rel="stylesheet">` 추가 

**overflow**
overflow-y : hide -> 외부 div보다 내부 컨텐츼의  height가 크면, 외부 div를 중심으로 스크롤 생긴다. (y축)
overflow: auto


공부할 것 : 카드 뷰 배경에 이미지 넣는 방법 두가지 조사
	- <img> 태그 사용
	- css 속성으로 background: url(link)     or background-image: url()
#더더랩WEB