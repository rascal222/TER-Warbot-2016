Ancienne hiérarchie
--------------------------
- TK (disparait après qu'un launcher soit terminé)
  - launch(TKLauncher)

- {Options.community} (Mis de force dans le TKLauncher, Il ne peut pas être modifié}
  - turtles
    - turtle (All Agents)
  - engine
    - scheduler (TKScheduler/WarbotScheduler)
    - viewer (TKViewer/WarViewer)
    - model (TKLauncher)
  - model (?)
    - env (TKEnvironment)

- {userTeam}{>=2} (doit avoir au moins deux équipes)
  - {defaultGroup}
    - WarExplorer
    - WarEngineer
    - WarBase
    - WarRocketLauncher
    - WarKamikaze
    - WarTurret
  - {useDefinedGroup]*
    - {userDefinedRole}*

- {SystemTeam}
  - {defaultGroup}
    - WarFood

Nouvelle hiérarchie
--------------------------
- TK (lié à TurtleKit)
  - launch(TKLauncher)

- Warbot/{ID} {WarbotLauncher hérite de TKLauncher mais autorise la modification de toute la communauté lié}
  - turtles
    - turtle (All Agents)
  - engine
    - scheduler (TKScheduler/WarbotScheduler)
    - viewer (TKViewer/WarViewer) (WarbotLauncher autorise qu'il y est aucun viewer')
    - model (TKLauncher)
  - model (?)
    - env (WarGame) (Il s'agira de l'environnement de la partie basée sur une instance WarGameSettings)

- {ID}/Warbot/{userTeam}{id_team} (on autorise les parties à un joueur, et on ajoute un numéro s'incrémentant afin d'autoriser l'ajout de même équipe)
  - {defaultGroup}
    - WarExplorer
    - WarEngineer
    - WarBase
    - WarRocketLauncher
    - WarKamikaze
    - WarTurret
  - {useDefinedGroup]*
    - {userDefinedRole}*

- {SystemTeam} (TODO : Est-ce qu'on la garde cette équipe ou bien on transfert tout dans l'environnement)
  - {defaultGroup}
    - WarFood