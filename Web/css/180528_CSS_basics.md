# 180528_CSS_basics
- class와 id를 제외한 Attribute는 []로 CSS에서 다룬다.

```javascript

/*class*/
.name[status=disable]{

}

/*id*/
#loginButton {

}

* {
	box-sizing: border-box;
}
```


## 크기
- px : 픽셀
- % : 부모의 크기에 대한 상대적 비율
- vw : view width
- vh : view height

`calc() : 단위가 다른 값을 계산`

box-sizing : border-box;  -> 뷰의 폭과 높이를 border 기준으로 맞춰줌

```javascript
*/ .parent 을 상속하는 .sub-parent만 적용*/
.parent > .sub-parent {
	width : 50%;
	padding : 8px;
}
```


- <a>, <tr>, <li> -> <div>에 스타일을 추가한 태그들
- display : block; // 가로 전체를 width로 가진다.
- display : inline-block; // 내가 정해진, 혹은 필요한 크기만큼을 width로 가진다.
- vertical-align : top;

```javascript

/* 항상 같이 써야할 속성들 */
.class {
	display : inline-block;
	vertical-align : top;
}
```

position : relative; ->
position : static;
position : absolute; -> 전체 브라우져에서 절대 좌표
	-position: static 속성을 가지고 있지 않은 부모를 기준으로 움직입니다. 만약 부모 	중에 포지션이 relative, absolute, fixed인 태그가 없다면 가장 위의 태그(body)	
	가 기준이 됩니다.


position : fix; -> 스크린에 고정 , ex) floating button

flex :   -> 자신을 더해서 width를 결정함  (비율)
flex를 사용하기 위해선 부모에서 display: flex; 를 해야됨ff]

요즘은 inline-block을 사용하지 않고 flex 사용

margin과 padding은 명시하지 안항도 기본값이 들어간다.

transform: scale(1.1) -> 확대
transition: transform .3s ->  transform을 천천히 함 , tip : transition은 변경이 일어나는 대상에 부여하는게 좋다.

#더더랩WEB
