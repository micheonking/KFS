<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>


<script type="text/javascript" src="<%=request.getContextPath()%>/smarteditor/js/lib/jquery3.3.1.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/smarteditor/js/service/HuskyEZCreator.js" charset="utf-8"></script>

<style type="text/css">
html, body { height:100%; overflow:hidden }
 <%--
 .h-div {height:96%; border:3px #f99 solid} 
--%>
</style>


<script>
var editor_object = [];
var isOnload = "0";
$(function(){
	//전역변수선언
    nhn.husky.EZCreator.createInIFrame({
        oAppRef: editor_object,
        elPlaceHolder: "smarteditor",
        sSkinURI: "<%=request.getContextPath()%>"+"/smarteditor/SmartEditor2Skin.html",
        htParams : {
        	fOnBeforeUnload : function(){
        	},	//창 닫을때 뜨는 alert창 제거
        	// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
            bUseToolbar : true,            
            // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
            bUseVerticalResizer : false,    
            // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
            bUseModeChanger : false,
            // client-side xss filter 무시 여부 (true:사용하지 않음 / 그외:사용)
            bSkipXssFilter : true,
            //기본글꼴지정
            //SE2M_FontName : {
        	//	htMainFont: {'id': '굴림','name': '굴림','size': '10','url': '','cssUrl': ''} // 기본 글꼴 설정
        	//},
        	SE_EditingAreaManager : {
	        	sDefaultEditingMode : 'TEXT'
        		//Editor 모드 : WYSIWYG, HTML 모드 : HTMLSrc, TEXT 모드 : TEXT
        	}
        },
        fOnAppLoad : function(){
        	editorLoad(); // 페이지 전체 Load 후 호출된다. 
        } 
    });

	<%--
    nhn.husky.EZCreator.createInIFrame({
        oAppRef: editor_object,
        elPlaceHolder: "test1",
        sSkinURI: "<%=request.getContextPath()%>"+"/smarteditor/SmartEditor2Skin.html",
        htParams : {
        	// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
            bUseToolbar : true,            
            // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
            bUseVerticalResizer : false,    
            // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
            bUseModeChanger : true,
            // client-side xss filter 무시 여부 (true:사용하지 않음 / 그외:사용)
            bSkipXssFilter : true,
        },
        fOnAppLoad : function(){
        	editorLoad2();
        } 
    });
	--%>
    //전송버튼 클릭이벤트
    $("#savebutton").click(function(){
    	//id가 smarteditor인 textarea에 에디터에서 대입
        editor_object.getById["smarteditor"].exec("UPDATE_CONTENTS_FIELD", []);
        //alert($("#smarteditor").text());     
        // 이부분에 에디터 validation 검증
        //폼 submit
        //$("#frm").submit();
    });
});
    
function sendToServer(){
	//id가 smarteditor인 textarea에 에디터에서 대입
    editor_object.getById["smarteditor"].exec("UPDATE_CONTENTS_FIELD", []);
    //var bbb = a.getElementById("htmlTemplate").contentWindow;
    // 이부분에 에디터 validation 검증
    //폼 submit(필요하면 쓰기)
    //$("#frm").submit();
    
    var htmlText = document.getElementById("smarteditor").value;
    
    //htmlText = htmlText.replace(/[<]div[>]/gi, '');
    
    //alert(htmlText); 
    
 //   이미지 업로드시 개행문자가 들어감, 그래서 문자 치환함
    htmlText = htmlText.replace(/&#10;/gi, '');

//    htmlText = htmlText.replace(/[<][^>]*[>]/gi, '');
//    htmlText = htmlText.replace(/[<][^>]*[>]/gi, '');
    
    //htmlText = htmlText.replacea(/&nbsp;/gi, ''); 
//    alert("text is " + htmlText); 
    
    return htmlText;
}

function setText(str){
	$("#smarteditor").text([str]);
	editor_object.getById["smarteditor"].exec("LOAD_CONTENTS_FIELD");
}

function setHtmlText(str){
	editor_object.getById["smarteditor"].exec("SET_IR", [""]); //초기화(기존내용삭제)
//     editor_object.getById["smarteditor"].exec("EVENT_CHANGE_EDITING_MODE_CLICKED", ["WYSIWYG", false]); 
    editor_object.getById["smarteditor"].exec("PASTE_HTML", [str]); //내용밀어넣기
}

function editorLoad(){
	var fn = window.parent.myApp.client.utils.JSCaller.getHtml();
	fn();
}

function changeHeight(height){
	editor_object.getById["smarteditor"].exec("RESIZE_EDITING_AREA", [0, height]); 
	//에디터 사이즈 변경 
	editor_object.getById["smarteditor"].exec("MSG_EDITING_AREA_RESIZE_STARTED", []); 
	editor_object.getById["smarteditor"].exec("RESIZE_EDITING_AREA", [0, height]); //타입은 px단위의 Number입니다. 
	editor_object.getById["smarteditor"].exec("MSG_EDITING_AREA_RESIZE_ENDED", []); 
	//에디터를 둘러싼 iframe 사이즈 변경 
	editor_object.getById["smarteditor"].exec("SE_FIT_IFRAME", []); 
}

function changeMode(str){
	 //- Editor 모드 : WYSIWYG 
	 // - HTML 모드 : HTMLSrc 
	 //- TEXT 모드 : TEXT 
	editor_object.getById["smarteditor"].exec("EVENT_CHANGE_EDITING_MODE_CLICKED", [str, false]); 
}
function readOnly(str){
	//alert(1);
	$("#smarteditor").attr("readonly", true);
	$("#se2_inputarea").attr("readonly",true);
	$("#se2_inputarea").attr("disable",true);

	//$("#frm").hide();
	//document.getElementById('fff').contentWindow.setHtml(str);
}

//사진 버튼 숨기기
function noImage(){
	var a = $("#frm").get(0).contentWindow;
	var d = $('#frm').contents();
	d = d.get(2).contentDocument;
	var s = d.getElementById("imageButton").remove();
}

function resize(arg){
}

</script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <form action="naverReceiveEditor.jsp" method="post" id="frm" style="width:100%x; height:100%;">
        <textarea name="smarteditor" id="smarteditor" rows="10" cols="100" style="width:100%; height:100%;"></textarea>
    </form>
    <!-- 
	<iframe id="fff" style="width:100%; height:150px;" src="./naverEditor2.jsp"></iframe>    
     <div id="test1"></div>
     -->
</body>
</html>