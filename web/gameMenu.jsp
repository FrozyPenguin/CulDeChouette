<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>selectPage</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.0.0-beta3/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/marcelodolza/iziToast@1.4/dist/css/iziToast.min.css"/>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Quicksand">
    <link rel="stylesheet" href="assets/css/OcOrato---Login-form.css">
    <link rel="stylesheet" href="assets/css/styles.css">
</head>

<body style="padding-top: 20px;" class="container-image">
    <div class="container box" style="position: relative">
    <%
        String pseudo = (String) session.getAttribute("pseudo");
        String pseudoReq = request.getParameter("pseudo");
        if(pseudo == null || pseudoReq == null || !pseudo.equals(pseudoReq)) {
    %>
        <div class="alert alert-danger" role="alert" id="alert">
            Vous n'êtes pas connecté !
            <%= pseudo + "      " + pseudoReq %>
        </div>
    <%
        }
        else {
    %>
    <a class="linkas" href="update.jsp?pseudo=<%= pseudo %>" id="update">Mettre à jour ses informations</a>
        <h1 class="text-center">Avec qui voulez-vous jouer ?</h1>
        <div class="table-responsive">
            <table class="table table-striped table-hover table-bordered table-responsive table-dark">
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
        <label for="reach">Points de fin de partie :</label>
        <input name="reach" class="form-control w-100 mb-2 formum" type="number" id="gamePoint" min="0" max="343" value="343" />
        <button class="btn btn-light text-center w-100 butonas" id="gameBegin" type="button">Commencer une partie</button>
    <%
        }
    %>
    </div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.0.0-beta3/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/gh/marcelodolza/iziToast@1.4/dist/js/iziToast.min.js"></script>
    <script src="assets/js/gameMenu.js" type="module"></script>
</body>

</html>