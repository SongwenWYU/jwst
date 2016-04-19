<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>java websocket test</title>
<script type="text/javascript">
	
	var connection = null;
	var loc = window.location;
	function startWebSocket(){
		var ws = null;
		if (loc.protocol === "https:") {
			ws = "wss:";
        } else {
        	ws = "ws:";
        }
		ws += '//' + loc.host + loc.pathname + '/../websocket.ws?t=' + new Date().getTime();
		ws = "ws://localhost:8080/jwst/websocket";
		if('WebSocket' in window){
			connection = new WebSocket(ws)
		}else if('MozWebSocket' in window){
			connection =  new MozWebSocket(ws);
		}else{
			alert("you browser is not support websock!")
		}
		var div = document.getElementById("msg");
		connection.onmessage = function(evt) {    
	        alert(evt.data);
	       
	        div.innerHTML += "<span>"+evt.data+"</span>"
	    };    
	        
	    connection.onclose = function(evt) {    
	        alert("close");    
	        div.innerHTML += "<span>"+evt.data+"</span>"
	    };    
	        
	    connection.onopen = function(evt) {    
	        alert("open");
	        div.innerHTML += "<span>"+evt.data+"</span>"
	    };
	    connection.onerror = function (error) {
            console.log('WebSocket Error ' + error);
        };
	}
	function sendMsg() {    
		connection.send(document.getElementById('writeMsg').value);    
	}
</script>
</head>
<body onload="startWebSocket();">    
<input type="text" id="writeMsg"></input>    
<input type="button" value="send" onclick="sendMsg()"></input>  
<div id="msg"></div>  
</body>
</html>