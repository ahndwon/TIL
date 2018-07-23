# 텍스트 일부분만 진하게
하나의 뷰 안에서 텍스트의 특정한 부분만 진하게 하고 싶은 경우 
HTML 태그를 이용하거나 SpannableStringBuilder()를 통해 구현하면 된다.


##  HTML 태그를 이용한 방법
진하게를 나타내는 <b> 태그를 이용하여 String을 만들면 진하게를 구현할 수 있다.
단, HTML 태그 변환에 대한 비용이 발생하므로 짧은 텍스트에만 사용하는 것이 좋다.
```java
String sourceString = "<b>" + id + "</b> " + name; 
mytextview.setText(Html.fromHtml(sourceString));

// API 24 이후부턴 deprecated 되었으므로 아래와 같이 사용해야 한다.
String unspanned = String.format(Locale.US, "%s%s", getResources().getString(R.string. welcome_messages), 99);

Spanned spanned;
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
    spanned = Html.fromHtml(unspanned, Html.FROM_HTML_MODE_LEGACY);
} else {
    spanned = Html.fromHtml(unspanned);
}

textView.setText(spanned);
```


## KTX를 사용하지 않는 경우
HTML 태그를 이용한 방법보다 좀 더 native 한 방법이다.
SpannableStringBuilder에서 setSpan을 통해 BOLD로 스타일을 정해주고 시작 위차와 끝나는 위치를 정해주어야 한다. 
```java
SpannableStringBuilder str = new SpannableStringBuilder("Your awesome text");
str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), INT_START, INT_END, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
TextView tv=new TextView(context);
tv.setText(str);
```

진하게 해주어야 하는 부분이 드문드문 있는 경우 텍스트를 나누어 각자의SpannableStringBuilder()를 만든후 하나의 SpannableStringBuilder()로 만들어주면 된다.
```java
private fun makeEventString(firstText: String, middleText: String, lastText: String)
        : SpannableStringBuilder {
    val firstString = SpannableStringBuilder(firstText)
    firstString.setSpan(android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
            0, firstText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    val middleString = SpannableStringBuilder(middleText)

    val lastString = SpannableStringBuilder(lastText)
    lastString.setSpan(android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
            0, lastText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    firstString.append(" ")
            .append(middleString)
            .append(" ")
            .append(lastString)

     return firstString
}

```


## KTX를 이용한 방법
가장 쉬운 방법이다.
이번에 안드로이드 JetPack 포함된 KTX를 사용할 경우 SpannableStringBuilder를 통해 정말 간단하게 구현할 수 있다.

```java
val s = SpannableStringBuilder()
          .bold { append(id) } 
          .append(name)
txtResult.setText(s) 
```


#android