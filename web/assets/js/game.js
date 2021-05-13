/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// Faudra test si l'utilisateur est autorisé dans la room au niveau du serveur ou ici un des deux

console.log('ok');

const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
console.log(urlParams);
for (let [name, value] of urlParams) {
  console.log(name, value);
}

if(!(urlParams.has('pseudo') && urlParams.get('id'))) {
    window.history.back();
}

let websocketUrl = `ws://localhost:8081/game/${urlParams.get('pseudo')}/${urlParams.get('id')}`;

if(urlParams.has('game')) {
    websocketUrl += `?game=${urlParams.get('game')}`;
}

// Créer websocket qui envoie les infos de la game
// Au niveau du serveur, implémenter la logique des room + créer une game
// Quand un joueur rejoint, on vérifie si il est dans la game

// Quand la websocket ferme, on fait un historyback

const websocket = new WebSocket(websocketUrl);

websocket.onopen = (evt) => {
    console.log(evt);
    console.log('Connexion établie');
    //container.style.display = 'block';
    iziToast.info({
        title: 'Info',
        message: 'Connexion à la websocket établie !',
        layout: 2,
        theme: 'light'
    });
};

websocket.onmessage = (evt) => {
    let data = JSON.parse(evt.data);
    console.log(`Message : %c${evt.data}`, 'color: orange; font-size: bold;');
    if(data['CONNECTED']) connected(data['CONNECTED']);
    else if(data['DISCONNECTED']) disconnected(data['DISCONNECTED']);
    else if(data['error']) handleWebSocketError(data);
};

websocket.onerror = (evt) => {
    handleWebSocketError(evt);
};

websocket.onclose = (evt) => {
    console.log(evt);
    iziToast.error({
        title: 'Erreur',
        message: evt.reason,
        layout: 2,
        theme: 'light'
    });
    setTimeout(() => {
        window.history.back();
    }, 5000);
};

function handleWebSocketError({ error }) {
    document.body.disabled = true;
    console.error(error);
    iziToast.error({
        title: 'Erreur',
        message: error,
        layout: 2,
        theme: 'light'
    });
    setTimeout(() => {
        window.history.back();
    }, 5000);
}

function connected(pseudo) {
    console.log(`%cUtilisateur ${pseudo} vient de se connecter !`, 'color: orange; font-weight: bold');
}

function disconnected(pseudo) {
    console.log(`%cUtilisateur ${pseudo} vient de se déconnecter !`, 'color: red; font-weight: bold');
}