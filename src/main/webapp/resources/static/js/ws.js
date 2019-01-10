var connection = new WebSocket('ws://localhost:8080/websocket');

var sendButton = document.getElementById("send-message-btn");
var messageInput = document.getElementById("message-input");

connection.onmessage = function (e) {
    addMessage(e.data);
};

function addMessage(data) {
  var messages = document.getElementById("messages-list");
  var message = document.createElement("li");
  var div = document.createElement("div");
  var p = document.createElement("p");

  message.classList.add("outbox")
  div.appendChild(document.createTextNode("YOU"));
  p.appendChild(document.createTextNode(data));

  message.appendChild(div);
  message.appendChild(p);
  messages.appendChild(message);
}

function sendMessage() {
    var payload = messageInput.value.trim();
        if(payload) {
            connection.send(payload);
            messageInput.value = '';
        }
}

sendButton.addEventListener('click', sendMessage, true);