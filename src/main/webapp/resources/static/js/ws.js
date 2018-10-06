var connection = new WebSocket('ws://localhost:8080/websocket');

var sendButton = document.getElementById("send-message-btn");
var messageInput = document.getElementById("message");

connection.onmessage = function (e) {
    addMessage(e.data);
};

function addMessage(data) {
  var messages = document.getElementById("messages-list");
  var message = document.createElement("li");
  message.appendChild(document.createTextNode(data));
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