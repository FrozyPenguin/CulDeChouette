/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(() => {
    const alert = document.querySelector('#alert');
    if(alert) return;

    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);

    if(!(urlParams.has('pseudo') && urlParams.has('id'))) {
        window.history.back();
    }

    let websocketUrl = `ws://${window.location.hostname}:${window.location.port}/game/${urlParams.get('pseudo')}/${urlParams.get('id')}`;

    if(urlParams.has('game')) {
        websocketUrl += `?game=${urlParams.get('game')}`;
    }

    // Créer websocket qui envoie les infos de la game
    // Au niveau du serveur, implémenter la logique des room + créer une game
    // Quand un joueur rejoint, on vérifie si il est dans la game

    // Quand la websocket ferme, on fait un historyback

    const websocket = new WebSocket(websocketUrl);
    let currentUsers = [];
    const playerPseudo = urlParams.get('pseudo');
    
    // Boutons
    const lancerDesButton = document.querySelector('#lancerDes');
    const suiteButton = document.querySelector('#suite');
    const chouetteVelueButton = document.querySelector('#chouetteVelue');
    
    // Liste des joueurs
    const documentList = document.querySelector('#onlinePlayersContainer .list');

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

        if(launchButton) createAction(`La partie vient d'être créée par ${playerPseudo}.`, 'color: green');
    };

    websocket.onmessage = (evt) => {
        let data = {};
        try {
            data = JSON.parse(evt.data);
        }
        catch(ex) {
            data = evt.data;
        }

        console.log(`Message : %c${evt.data}`, 'color: orange; font-size: bold;');
        if(typeof(data) === "string") {
            if(evt.data === 'STARTCOUNTDOWN') startCountdown(5);
        }
        else {
            if('CONNECTED' in data) updateUserList(data['CONNECTED']);
            else if('DISCONNECTED' in data) updateUserList(data['DISCONNECTED']);
            else if('error' in data) handleWebSocketError(data);
            else if('REACH' in data) setReachPoint(data['REACH']);
            else if('START' in data) startGame(data['START']);
            else if('TURN' in data) playerTurn(data['TURN']);
            else if('ROLL' in data) showDice(data['ROLL']);
            else if('RESULT' in data) processResults(data['RESULT']);
        }
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

        createAction(`Partie déconnectée : ${evt.reason}.`, 'color: red');

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

    const launchButton = document.querySelector("#loader #launchGame");
    if(launchButton) {
        launchButton.addEventListener('click', (event) => {
            launchButton.style.display = "none";
            websocket.send('startCountdown');
        });
    }

    function updateUserList(list) {
        document.querySelector('#actualPlayers').innerHTML = list.connected.length;
        document.querySelector('#totalPlayers').innerHTML = list.total;

        let differenceUsers = list.connected
                                .filter(x => !currentUsers.map(obj => obj.pseudo).includes(x.pseudo))
                                .concat(currentUsers.filter(x => !list.connected.map(obj => obj.pseudo).includes(x.pseudo)));

        if(currentUsers.length < list.connected.length) {
            differenceUsers.forEach(user => { 
                createAction(`${user.pseudo} vient de se connecter.`);
            });
        }
        else {
            differenceUsers.forEach(user => { 
                createAction(`${user.pseudo} vient de se déconnecter.`, 'color: red; font-weight: bold;');
            });
        }

        currentUsers = list.connected;

        let users = new Array(list.total).fill('En attente');

        list.connected.forEach(user => {
            users[user.position - 1] = user.pseudo;
        });

        documentList.innerHTML = "";

        users.forEach(user => {
            const li = document.createElement('li');
            li.innerHTML = user;
            li.classList.add(user === 'En attente' ? 'disconnected' : 'connected');
            documentList.appendChild(li);
        });

        if(list.connected.length === list.total && list.connected[0].pseudo === playerPseudo && launchButton) {
            launchButton.disabled = false;
        }
        else if(list.connected[0].pseudo === playerPseudo && launchButton) launchButton.disabled = true;
    }

    function setReachPoint(point) {
        document.querySelector('#reachPoint').innerHTML = point;
        createAction(`Score de fin de partie défini sur ${point} points.`, 'color: orange; font-weight: bold;');
    }

    function createAction(actions, css) {
        let actionsBox = document.querySelector('#actions');
        if(actions instanceof Array) actions.forEach(action => singleLine(action));
        else singleLine(actions);
        
        function singleLine(action) {
            let p = document.createElement('p');
            p.innerHTML = action;
            if(css) p.setAttribute('style', css);
            actionsBox.appendChild(p);
        }

        actionsBox.scrollTop = actionsBox.scrollHeight;
    }

    /* 1 */
    function startCountdown(count) {
        if(!count && count !== 0) return;
        if(count <= 0) {
            if(launchButton) websocket.send('start');
            return;
        }
        createAction(`La partie commence dans ${count} secondes.`, 'color: orange');
        setTimeout(() => {
            startCountdown(--count);
        }, 1000);
    }

    /* 2 */
    function startGame(startObj) {
        console.log('start');
        createAction(`La partie commence ! Préparez-vous !`, 'color: black; font-weight: bold; font-size: 1.5em;');
    }
    
    function playerTurn(player) {
        if(player === playerPseudo) createAction(`C'est à vous de jouer !`, 'color: orange');
        else createAction(`${player} est en train de jouer !`);
        
        documentList.querySelectorAll('li').forEach(element => {
            element.classList.remove('actualTurn');            
        });
        
        const pos = currentUsers.filter(user => user.pseudo === player).position;
        documentList.querySelector(`li:nth-of-type(${pos})`).classList.add('actualTurn');
    }
    
    lancerDesButton.addEventListener('click', (event) => {
        websocket.send('ROLL');
    });
    
    function showDice(results) {
        if(results.pseudo === playerPseudo) {
            createAction([`${results.pseudo} à obtenu les dès :`, `- Chouette : ${results.chouette[0]} et ${results.chouette[1]}`, `- Cul ${results.cul}`]);
        }
    }
    
    function processResults(results) {
        
    }
})();
