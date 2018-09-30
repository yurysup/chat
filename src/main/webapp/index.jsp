<html>
<body>
<h2>Hello World!</h2>

<a href="servlet">Click here to see servlet</a>
<p><button id="sendWSMessage" type="button" onclick="sendDummyMessage();">Start</button></p>
<script>
var connection = new WebSocket('ws://localhost:8080/websocket');

function sendDummyMessage() {
    connection.send("Hello from UI");
}
</script>
</body>
</html>
