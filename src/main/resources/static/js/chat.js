var stompClient = null;
  var myname;
  var username=null;
  var message_count=0;
  $(function () {
      myname=$("#myname").text();
      //username=$("#username").text();
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
        	  updateMessageList(JSON.parse(msg.body));
        	  if(username!=null){
        		  showChat(JSON.parse(msg.body),myname);
        	  }
        	  
          });
          
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
    $('#chat_input').val("");
  }
  
  //显示聊天信息
  function showChat(message,myname) {
      var html=getChatMsg(decodeURIComponent(message.name),message.sendTime,decodeURIComponent(message.chatContent));
      //alert(html);
      var chatMsg = $(html).appendTo('#chat_content');
      //console.log(chatMsg);
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
  function openChat(name){
	  //username="0100385";
	  username=name;
	  $('#modal_chat').modal({
			backdrop:'static'
		});
	}
  function updateMessageList(message){
	  message_count++;
	  $(".message_count").text(message_count).show();
	  var html=getMessageHtml(decodeURIComponent(message.name),message.sendTime,decodeURIComponent(message.chatContent));
	  $(html).prependTo('#message_list');
  }
  function getMessageHtml(name,time,text){
	  var html='<li><a href="javascript:;" onclick="openChat(\''+name+'\')">'+
	  '<div class="pull-left"><img src="../img/user2-160x160.jpg" class="img-circle" alt="User Image"/></div>'+
	  '<h4>'+name+'<small><i class="fa fa-clock-o"></i> '+time+'</small></h4>'+
	  '<p>'+text+'</p></a></li>';
	  return html;
  }