/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

const websocket = new WebSocket("ws://localhost:8081/test");

websocket.onopen = function(evt) {
    console.log(evt);
    console.log('Connexion établie');
};

websocket.onmessage = function(evt) {
    console.log(`Message : %c${evt.data}`, 'color: orange; font-size: bold;');
    
    if(evt.data.includes('compteur:')) {
        let [name, value] = evt.data.split(':');
        document.querySelector('#connected').innerHTML = value;
    }
};

websocket.onerror = function(evt) {
    console.error(evt.data);
};

function sendMessage(message) {
    websocket.send(message);
}
