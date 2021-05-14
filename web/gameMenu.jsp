<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>selectPage</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.0.0-beta3/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/marcelodolza/iziToast@1.4/dist/css/iziToast.min.css"/>
    <link rel="stylesheet" href="assets/css/styles.css">
</head>

<body style="margin-top: 20px;">
    <input type="text" id="pseudo" />
    <button id="register">Register</button>
    <div class="container">
        <h1 class="text-center">Avec qui voulez-vous jouer ?</h1>
        <div class="table-responsive">
            <table class="table table-striped table-hover table-bordered">
                <thead>
                    <tr>
                        <th>Joueur</th>
                        <th class="text-center" style="max-width: 200px;width: 120px;">Sélectionner</th>
                        <th class="text-center" style="max-width: 200px;width: 120px;">Ordre de jeu</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        <button class="btn btn-primary text-center w-100" id="gameBegin" type="button">Commencer une partie</button>
    </div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.0.0-beta3/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/gh/marcelodolza/iziToast@1.4/dist/js/iziToast.min.js"></script>
    <script src="assets/js/gameMenu.js" type="module"></script>
</body>

</html>