import { Message } from './Message.js';

const container = document.querySelector('.container');
container.style.display = 'none';

let websocket;

document.querySelector('#register').addEventListener('click', () => {
    const pseudo = document.querySelector('#pseudo');
    if(!pseudo.value) return;
    
    websocket = createWebSocket(`ws://localhost:8081/menu/${pseudo.value}`);
});

function createWebSocket(url) {
    const websocket = new WebSocket(url);
    
    websocket.onopen = (evt) => {
        console.log(evt);
        console.log('Connexion établie');
        container.style.display = 'block';
    };

    websocket.onmessage = (evt) => {
        let data = JSON.parse(evt.data);
        console.log(`Message : %c${evt.data}`, 'color: orange; font-size: bold;');
        if(data['CONNECTEDPLAYERS']) initMenu(data['CONNECTEDPLAYERS']);
        else if(data['ADDTOLIST']) addPlayerToList(data['ADDTOLIST']);
        else if(data['REMOVEFROMLIST']) removePlayerFromList(data['REMOVEFROMLIST']);
    };

    websocket.onerror = (evt) => {
        handleWebSocketError(evt);
    };

    websocket.onclose = (evt) => {
        handleWebSocketError(evt);
    };

    function handleWebSocketError(event) {
        document.body.disabled = true;
        console.error(event.error);
    }
    
    return websocket;
}

function createElementFromHTML(htmlString, baseElement = 'div') {
    var div = document.createElement(baseElement);
    div.innerHTML = htmlString.trim();

    // Change this to div.childNodes to support multiple top-level nodes
    return div.firstChild;
}

function addPlayerToList(player) {
    console.log(player);
    const row = document.querySelector('table tbody').insertRow();
    const joueur = row.insertCell(0);
    const check = row.insertCell(1);
    const order = row.insertCell(2);
    
    joueur.innerHTML = `<td>${player}</td>`;
    
    check.classList.add('text-center');
    check.innerHTML = `
        <td class="text-center">
            <input type="checkbox" value="${player}">
        </td>`;
    
    check.querySelector('input[type="checkbox"').addEventListener('click', function(event) {
        document.querySelectorAll('table tbody tr td input[type="number"]').forEach(input => {
            input.max = document.querySelectorAll('table tbody tr td input[type="checkbox"]:checked').length + 1;
        });
        this.closest('tr').querySelector('input[type="number"]').disabled = !this.checked;
    });

    check.classList.add('text-center');
    order.innerHTML = `
        <td class="text-center">
            <input type="number" min="2" step="1" max="0" disabled>
        </td>`;

    console.log('added');
}

function removePlayerFromList(player) {
    document.querySelector(`input[type="checkbox"][value="${player}"]`).closest('tr').remove();
}

function initMenu(players) {
    console.log(players);
    players.forEach(player => addPlayerToList(player));

    const beginButton = document.querySelector('#gameBegin');
    
    beginButton.addEventListener('click', (event) => {
        const positions = Array.from(document.querySelectorAll('table tbody tr td input[type="number"]:not([disabled])')).map(element => element.value);

        if(!positions.length) {
            console.log('Vous devez d\'abord sélectionner d\'autre participants');
            return;
        }

        if(positions.findIndex(element => !element || element < 1 || element > positions.length) >= 0 || positions.length !== new Set(positions).size) {
            console.log('Plusieurs personne possède le même ordre de jeu ou une personne n\'a pas d\'ordre définie !');
            return;
        }

        const selectedCheckbox = document.querySelectorAll('table tbody tr td input[type="checkbox"]:checked');

        // Null sera ici le joueur courant
        const users = [ null ];
        selectedCheckbox.forEach((checkbox, index) => {
            let user = {
                pseudo: checkbox.value,
                position: positions[index]
            };
            users.push(user);
        });

        console.log(users);
    });
}
