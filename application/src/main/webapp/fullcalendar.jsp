<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='utf-8' />
<link href='<%=request.getContextPath()%>/fullcalendar/fullcalendar.min.css' rel='stylesheet' />
<link href='<%=request.getContextPath()%>/fullcalendar/fullcalendar.print.min.css' rel='stylesheet' media='print' />
<script src='<%=request.getContextPath()%>/fullcalendar/lib/moment.min.js'></script>
<script src='<%=request.getContextPath()%>/fullcalendar/lib/jquery.min.js'></script>
<script src='<%=request.getContextPath()%>/fullcalendar/fullcalendar.min.js'></script>
<script src='<%=request.getContextPath()%>/fullcalendar/locale-all.js'></script>
<style type="text/css">
.fc-content{
    cursor:pointer;
}
.fc-center{
    margin-left: 220px;
}
<%--
.fc-sat {color:#007dc3}/*토요일*/
.fc-sun {color:#e31b23}/*일요일*/
--%>
</style>

<script>
var empId;
var orgCd;

$(document).ready(function() {
});


function init(){
	makeCalendar();
}

function makeCalendar(data){
	
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1; //January is 0!
	var yyyy = today.getFullYear();
	
    $('#calendar').fullCalendar({
   	    customButtons: {
   		    myCustomButton: {
   		        text: '새로고침',
   		        click: function() {
   		        	var fn = window.parent.myApp.client.utils.JSCaller.returnValue();
   		        	fn();
//    		        	getViewDate(empId, orgCd);
   		        }
   		    }
   		},
        header: {
        left:'',
        center: 'title',
        right: 'prev,next today myCustomButton',
        //right: 'month,basicWeek,basicDay'
        },
        locale: 'ko',
        defaultDate: moment(today),
        navLinks: false, // can click day/week names to navigate views
        editable: false,
        eventLimit: true, // allow "more" link when too many events
        events: [{
        	title: '',
            start: ''
          }],
        displayEventTime: false,
        eventClick: function(calEvent, jsEvent, view) {

        	var moment = calEvent.start;
            var yy=moment.format("YYYY");
            var mm=moment.format("MM");
            var dd=moment.format("DD");
            date = yy + "-" + mm + "-" + dd;

            var fn = window.parent.myApp.client.utils.JSCaller.returnValue();
        	fn(date);
        },
        eventAfterAllRender: function(view) {
        	var fn = window.parent.myApp.client.utils.JSCaller.getHtml();
        	fn();
        },
	});
    
    //getViewDate();
    $(".fc-prev-button").on("click", function() {//이전 달 버튼
   	    getViewDate(empId, orgCd);
   	});
   	$(".fc-next-button").on("click", function() {//다음 달 버튼
   	    getViewDate(empId, orgCd);
   	});
   	$(".fc-today-button").on("click", function() {//오늘 버튼
   	    getViewDate(empId, orgCd);
   	});
   	$(".fc-month-button").on("click", function() {//월 버튼
     	getViewDate(empId, orgCd);
    });
   	$(".fc-basicWeek-button").on("click", function() {//주 버튼
     	getViewDate(empId, orgCd);
    });
   	$(".fc-basicDay-button").on("click", function() {//일 버튼
     	getViewDate(empId, orgCd);
    });

}

function getViewDate(userId, orgCode) {
	  empId = userId;
	  orgCd = orgCode;
	
	  var date = $('#calendar').fullCalendar('getView');
	  //console.log(date.intervalStart.format('YYYY-MM-DD'));
	  //console.log(date.intervalEnd.subtract(1, "days").format('YYYY-MM-DD'));
	  var monthObj = {
	    startDate: date.intervalStart.format('YYYY-MM-DD'),
		endDate: date.intervalEnd.subtract(1, "days").format('YYYY-MM-DD')
	  };
	  $('#calendar').fullCalendar('removeEvents');
	  date.intervalEnd.subtract(-1, "days").format('YYYY-MM-DD')//오늘 버튼 클릭시 날짜 줄어드는 현상 방지용 
	  calEvent(monthObj, '');
	}

function calEvent(dayObj, locationIdx) {
	var jsonParam = new Object();
	jsonParam.startDate = dayObj.startDate;
	jsonParam.endDate   = dayObj.endDate;
	jsonParam.empId 	= empId;
	jsonParam.orgCd		= orgCd;
	
  $.ajax({
	url: '<%=request.getContextPath()%>/Calendar',
    dataType: "json",
    data: jsonParam,
    method: "POST",
    async: false,
    success: function(data) {
        renderCalcEvent(data);
    },
    error: function(request, status, error) {
      alert("code:" + request.status + "\n" + "message:" +
        request.responseText + "\n" + "error:" +
        error);
    }
  });
}


function renderCalcEvent(data) {
	for (var i = 0; i < data.length; i++) {
	    var locIdx = data[i].locationIdx;
	    var eventIdx = data[i].eventIdx;
	    var sdate = new Date(data[i].regDt).toString();
	    var edate = new Date(data[i].regDt).toString();
	    var date = data[i].regDt;
	  
	    var event = {
		    //id: data[i].eventIdx,
		    id: moment(data[i].start),
		    title: data[i].title,
		    //url: '/',
		    //start: data[i].start,
		    //end: data[i].end,
		    start: moment(data[i].start),
		    end: moment(data[i].end),
		    allDay: false,
		    color: data[i].color,
		    textColor:data[i].textColor
	    };
	     
	    $('#calendar').fullCalendar('renderEvent', event);
    }
    makeTodaybtnActive();
}
	
function makeTodaybtnActive() {
   $('#calendar button.fc-today-button').removeAttr('disabled');
   $('#calendar button.fc-today-button').removeClass('fc-state-disabled');
 }
</script>
<style>

  body {
    margin: 40px 10px;
    padding: 0;
    font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
    font-size: 14px;
  }

  #calendar {
    max-width: 900px;
    margin: 0 auto;
  }

</style>
</head>
<body onload="init()">

  <div id='calendar'></div>

</body>
</html>
