# REUNION
Projet de gestion des réunions - Formation openclassrooms Programmation Java Andoïd

1.	QUELQUES ELEMENTS PREPARATOIRES

De manière arbitraire j’ai fait quelques hypothèses :
-	Nous ne gérons qu’un seul fuseau horaire et même qu’une seule entreprise localisée géographiquement en un seul lieu.
-	Les salles de réunion sont suffisamment peu éloignées les unes des autres pour permettre à chaque salarié de s’y rendre facilement
-	10 salles de réunion définies portent des noms de régions de France
-	Une trentaine d’utilisateurs prédéfinis
-	Une douzaine de réunions prédéfinies autour du moment de création afin de faciliter les tests
-	Une réunion durera moins de 8 jours
-	Des réunions ne peuvent pas se chevaucher dans le temps dans la même salle, par contre le même utilisateur peut être inscrit à plusieurs réunions simultanées.
-	Pour filtrer les réunions par date, on utilise le jour du début de la réunion (une seule date)
-	Pour filtrer les réunions par salle, on ne sélectionne qu’une seule salle dans le filtre
-	Quand le filtre de date n’est pas actif, toutes les réunions terminées ne sont pas visualisées
-	Quand le filtre de date est actif, toutes les réunions qui commencent à cette date apparaissent, terminées ou non

2.	CODAGE DU PROJET
D’un point de vue technique, les principaux fichiers sources (.java) sont organisés ainsi :
Model :
-	User
-	Room
-	Meeting
- InitData qui prédéfinit des exemples de données (comme si on avait chargé ces données dans l’application). InitData contient aussi les formats de dates et heures utilisés dans l’application
Controller :
-	MainActivity :
o	Affiche (RecyclerView) la liste des réunions triées par date et heure de début, éventuellement filtrées par date et salle. 

	Sans filtre de date, les réunions terminées ne sont pas affichées. 

	Une couleur arbitraire est attribuée à chaque salle, matérialisée par le disque coloré en début de ligne pour visualiser les salles occupées d’un coup d’œil. 

o	Permet de rafraichir la liste des réunions (swipe). Les réunions terminées disparaissent
o	Permet d’activer les filtres de date et salle via l’icone filtre du menu. 

	Un badge indique en rouge les filtres positionnés (R pour Room, D pour Date)

o	Permet d’appeler la fenêtre de création de réunions (icone « plus » flottante)
o	Permet d’appeler la fenêtre d’effacement d’une réunion (icone corbeille en fin de ligne).

-	FilterMeetings :
o	Donne une liste de réunions filtrée le cas échéant pas date et/ou salle.

-	MainListener :
o	Interfaces pour définir dans MainActivity les Listeners utilisés dans les fenêtres de dialogue

Views :
-	MeetingsAdapter :

o	Adapte la liste des réunions à afficher dans un RecyclerView 

-	FilterMeetingsDialog :
-
o	Fenêtre de dialogue pour définir les filtres éventuels (salle et/ou date)

	En entrée : filtres courants via Bundle

	En sortie : filtres mis à jour utilisés par le listener défini via interface.

-	DeleteMeetingDialog :

o	Fenêtre de dialogue avec détails de la réunion à effacer (confirmation/annulation)

	Si effacement validé, le listener défini via l’interface MainListener est utilisé

-	MultiPicker :

o	Fenêtre générale de sélection multiple utilisée ici pour sélectionner plusieurs utilisateurs en création de nouvelle réunion.

	En entrée (via Bundle) :  liste de noms, liste de booléens (sélection courante).

	 Retour géré par le listener passé par la méthode setListener.

o	Gestion bascule « tout sélectionner/rien sélectionner »

-	NewMeetingDialog :
-
o	Crée une nouvelle réunion

	Pas de paramètre d’entrée. 

	Sortie : la nouvelle réunion est ajoutée par le listener passé via l’interface MainListener, dans la liste (maintenue triée par date de début) des réunions et l’affichage est mis à jour.
o	Fonctionnalités particulières :
	Seuls 2 champs sont marqués obligatoires : nom de la réunion et salle. En cas de validation avec champ manquant, ce dernier a un fond rouge et validation impossible
	La réunion est définie par défaut à la date et heure du moment.
	La durée est définie par défaut (ressource) à 45mn modifiable
	L’heure de fin est calculée et comporte (+n) si la date de fin est augmentée de n jours
	La liste des salles ne comporte que les salles inoccupées pour le créneau défini : modifier le créneau peu effacer la salle choisie si elle devient indisponible
	Des icones, croix et flèche enroulée, permettent respectivement d’effacer une valeur ou la réinitialiser.
	Le bouton « Annuler » fait sortir sans rien garder
	Le bouton réinitialiser remet la fenêtre dans son état initial
	Le bouton OK, après contrôle des champs obligatoires, enregistre la nouvelle réunion et rafraîchit l’affichage 

3.	FONCTIONS COMPLEMENTAIRES
-	Localisation :
o	Les messages ont été définis en Français, Anglais et Arabe (pour vérifier le fonctionnement correct d’écriture de droite à gauche)
o	Les dates et heures suivent la locale (Anglais, Français)
o	La locale utilisée est celle du téléphone.

-	Ecran sombre ou clair (dark/light) :
o	Les icones et textes respectent le paramètre sombre ou clair pour l’affichage

-	Ecran tourné
o	Les données ne sont pas conservées (réinitialisées) 
o	Le scroll éventuel dans les fenêtres de dialogue autorise l’utilisation sans restriction.
