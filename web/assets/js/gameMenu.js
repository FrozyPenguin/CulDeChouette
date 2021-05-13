import { Message } from './Message.js';

const container = document.querySelector('.container');
container.style.display = 'none';

let websocket;

const pseudo = document.querySelector('#pseudo');

/* TEMP */
document.querySelector('#register').addEventListener('click', (e) => {
    pseudo.style.display = 'none';
    e.target.style.display = 'none';
    if(!pseudo.value) return;
    
    websocket = createWebSocket(`ws://localhost:8081/menu/${pseudo.value}`);
});
/* FIN TEMP */

function createWebSocket(url) {
    const websocket = new WebSocket(url);
    
    websocket.onopen = (evt) => {
        console.log(evt);
        console.log('Connexion établie');
        container.style.display = 'block';
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
        if(data['CONNECTEDPLAYERS']) initMenu(data['CONNECTEDPLAYERS']);
        else if(data['ADDTOLIST']) addPlayerToList(data['ADDTOLIST']);
        else if(data['REMOVEFROMLIST']) removePlayerFromList(data['REMOVEFROMLIST']);
        else if(data['GAMEREQUEST']) gameRequest(data['GAMEREQUEST']);
        else if(data['ACK']) redirect(data['ACK'].gameUrl);
        else if(data['error']) handleWebSocketError(data);
    };

    websocket.onerror = (evt) => {
        handleWebSocketError(evt);
    };

    websocket.onclose = (evt) => {
        handleWebSocketError(evt);
        document.location.reload();
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

        if(positions.findIndex(element => !element || element <= 1 || element > positions.length + 1) >= 0 || positions.length !== new Set(positions).size) {
            console.log('Plusieurs personne possède le même ordre de jeu ou une personne n\'a pas d\'ordre définie !');
            return;
        }

        const selectedCheckbox = document.querySelectorAll('table tbody tr td input[type="checkbox"]:checked');

        const users = [];
        selectedCheckbox.forEach((checkbox, index) => {
            let user = {
                pseudo: checkbox.value,
                position: positions[index]
            };
            users.push(user);
        });

        // Je pense que ce serait plus simple de changer de page et apres lancer la game
        // Comme ca on aurait deux connexion sur la seconde page une pour le retour d'info des demande accepter ou non
        // et l'autre pour gérer la game en elle même avec les gens qui ont rejoint etc
        // Sinon on garde cette logique et on attend juste que les gens rejoignent on aura pas de retour sur les refus
        // Et genre on lance un timeout et si au bout de se timeout, il y toujours personne on redirige sur la page Menu (on annule la game)
        // On mettra un ecran d'attente avec un bouton annulé la game (pour le leader) qui supprimera la room et redirigera tout le monde sur la page Menu
        console.log(users);
        websocket.send(new Message(users));
    });
}

function gameRequest(sender) {
    console.log(sender);
    
    // Demander si on veut join
    // Si oui
    iziToast.question({
        close: false,
        overlay: true,
        displayMode: 'once',
        id: 'question',
        zindex: 999,
        title: `Demande de ${sender.pseudo} !`,
        message: 'Voulez-vous le rejoindre ?',
        position: 'center',
        buttons: [
            ['<button><b>Oui</b></button>', function (instance, toast, button, events, inputs) {
                instance.hide({ transitionOut: 'fadeOut' }, toast, 'button');  
                redirect(sender.gameUrl);
            }, true /* Focus on load ? */],
            ['<button>Non</button>', function (instance, toast, button, events, inputs) {
                instance.hide({ transitionOut: 'fadeOut' }, toast, 'button');
            }]
        ]
    });
    
    // Sinon envoyer une info de refus ou on fait rien suivant le scénario choisi
}

function redirect(url) {
    console.log(`gameUrl : %c${url}`, 'color: blue; font-size: bold;');    
    // Rediriger vers la bonne page
    window.location.replace(url);
}