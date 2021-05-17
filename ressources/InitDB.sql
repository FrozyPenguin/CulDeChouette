
        
CREATE TABLE action
(
  id_partie INTEGER NOT NULL COMMENT 'identifiant de la partie',
  tour      INTEGER NOT NULL COMMENT 'numéro du tour',
  chouette1 TINYINT NULL     COMMENT 'valeur du dé chouette 1',
  chouette2 TINYINT NULL     COMMENT 'valeur du dé chouette 2',
  cul       TINYINT NULL     COMMENT 'valeur du dé cul',
  PRIMARY KEY (id_partie, tour)
) ENGINE=InnoDB;

CREATE TABLE interraction
(
  id_partie      INTEGER     NOT NULL COMMENT 'identifiant de la partie',
  tour           INTEGER     NOT NULL COMMENT 'numéro du tour',
  effet          CHAR(1)     NULL     COMMENT 'POSITIF ou NEGATIF',
  joueur_affecte VARCHAR(25) NULL     COMMENT 'joueur concerné',
  PRIMARY KEY (id_partie, tour)
) ENGINE=InnoDB;

CREATE TABLE joueur
(
  pseudonyme     VARCHAR(25)  NOT NULL COMMENT 'pseudonyme du joueur',
  email          VARCHAR(254) NOT NULL COMMENT 'email du joueur',
  mot_de_passe   VARCHAR(25)  NOT NULL COMMENT 'mot de passe du joueur',
  date_naissance DATE         NULL     COMMENT 'date de naissance pour calcul dâge',
  sexe           CHAR(3)      NULL     COMMENT 'sexe du joueur',
  ville          VARCHAR(25)  NULL     COMMENT 'ville du joueur',
  PRIMARY KEY (pseudonyme)
) ENGINE=InnoDB;

CREATE TABLE partie
(
  id_partie  INTEGER     NOT NULL AUTO_INCREMENT COMMENT 'identifiant de la partie',
  objectif   INTEGER     NOT NULL DEFAULT 343 COMMENT 'score à atteindre',
  date_debut DATETIME    NULL     COMMENT 'date de début de la partie',
  duree      FLOAT       NULL     COMMENT 'durée de la partie (minutes)',
  hote       VARCHAR(25) NULL     COMMENT 'pseudonyme du joueur hote de la partie',
  vainqueur  VARCHAR(25) NULL     COMMENT 'pseudonyme du vainqueur',
  PRIMARY KEY (id_partie)
) ENGINE=InnoDB;

CREATE TABLE resultats
(
  id_partie                INTEGER     NOT NULL COMMENT 'identifiant de la partie',
  pseudonyme               VARCHAR(25) NOT NULL COMMENT 'pseudonyme du joueur',
  ordre                    INTEGER     NULL     COMMENT 'ordre du joueur dans la partie',
  score                    INTEGER     NOT NULL DEFAULT 0 COMMENT 'score du joueur à la partie',
  suites_gagnees           INTEGER     NULL     COMMENT 'nombre de suites remportées',
  chouettes_velues_perdues INTEGER     NULL     COMMENT 'nombre de chouettes velues perdues',
  PRIMARY KEY (id_partie, pseudonyme)
) ENGINE=InnoDB;

ALTER TABLE resultats
  ADD CONSTRAINT FK_partie_TO_resultats
    FOREIGN KEY (id_partie)
    REFERENCES partie (id_partie);

ALTER TABLE resultats
  ADD CONSTRAINT FK_joueur_TO_resultats
    FOREIGN KEY (pseudonyme)
    REFERENCES joueur (pseudonyme);

ALTER TABLE partie
  ADD CONSTRAINT FK_joueur_TO_partie
    FOREIGN KEY (vainqueur)
    REFERENCES joueur (pseudonyme);

ALTER TABLE partie
  ADD CONSTRAINT FK_joueur_TO_partie1
    FOREIGN KEY (hote)
    REFERENCES joueur (pseudonyme);

ALTER TABLE action
  ADD CONSTRAINT FK_partie_TO_action
    FOREIGN KEY (id_partie)
    REFERENCES partie (id_partie);

ALTER TABLE interraction
  ADD CONSTRAINT FK_action_TO_interraction
    FOREIGN KEY (id_partie, tour)
    REFERENCES action (id_partie, tour);

ALTER TABLE interraction
  ADD CONSTRAINT FK_joueur_TO_interraction
    FOREIGN KEY (joueur_affecte)
    REFERENCES joueur (pseudonyme);

        
      