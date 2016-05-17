var stompClient = null;
  var myname,username;
  $(function () {
      myname=$("#myname").text();
      username=$("#username").text();
      connect();
  });
  
  function connect() {
      var socket = new SockJS('/chat');
      stompClient = Stomp.over(socket);
      stompClient.connect({}, function(frame) {
          //setConnected(true);
          console.log('Connected: ' + frame);
          /* stompClient.subscribe('/topic/greetings', function(greeting){
              showGreeting(JSON.parse(greeting.body).content);
          }); */
          stompClient.subscribe('/web/chat' + myname, function(msg){
              showChat(JSON.parse(msg.body),myname);
          });
          /*if(username!=myname){
        	  stompClient.subscribe('/web/chat' + username, function(msg){
                  showChat(JSON.parse(msg.body),myname);
              });
          }*/
          
      });
  }  
  //发送聊天信息
  function sendMessage() {
    var inputValue = $('#chat_input').val();
    var message = {
            'name': encodeURIComponent(myname),
            'chatContent': encodeURIComponent(inputValue),
            'coordinationId': username,
            'sendTime':(new Date()).pattern("yyyy-MM-dd hh:mm:ss")
          };
    stompClient.send("/app/userChat", {}, JSON.stringify(message)); 
    showChat(message,myname);
  }
  
  //显示聊天信息
  function showChat(message,myname) {
      //var text = decodeURIComponent(message.name) + ':' + decodeURIComponent(message.chatContent) + '\n';
	  
      var html=getChatMsg(decodeURIComponent(message.name),message.sendTime,decodeURIComponent(message.chatContent));
      //alert(html);
      var chatMsg = $(html).appendTo('#chat_content');
      console.log(chatMsg);
      if(message.name==myname){
    	  chatMsg.addClass("right");
    	  chatMsg.find(".direct-chat-name").addClass("pull-right");
    	  chatMsg.find(".direct-chat-timestamp").addClass("pull-left");
      }else{
    	  chatMsg.find(".direct-chat-name").addClass("pull-left");
    	  chatMsg.find(".direct-chat-timestamp").addClass("pull-right");
      }
  }
  
  function getChatMsg(name,time,text){
	  var html='<div class="direct-chat-msg"><div class="direct-chat-info clearfix">'+
	  '<span class="direct-chat-name">'+name+'</span>'+
	  '<span class="direct-chat-timestamp">'+time+'</span></div>'+
	  '<img class="direct-chat-img" src="../img/user3-128x128.jpg" alt="Message User Image"/>'+
	  '<div class="direct-chat-text">'+text+'</div></div>';
	  return html;
  }
  function openChat(){
	  //username="0100385";
	  $('#modal_chat').modal({
			backdrop:'static'
		});
	}