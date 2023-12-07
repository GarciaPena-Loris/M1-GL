# Registres

- **Registres Généraux :**
  - R0
  - R1
  - R2

- **Registres Dédiés :**
  - BP (Base Pointer) : Indique le début de la pile d'exécution.
  - SP (Stack Pointer) : Indique le sommet de la pile d'exécution.
  - PC (Program Counter) : Contient le compteur de programme ou compteur ordinal.

- **Registre de Cadre (Introduit ultérieurement) :**
  - FP (Frame Pointer) : Pointeur de cadre utilisé pour structurer les blocs de la pile.

- **Drapeaux (1 bit chacun) :**
  - FLT (Flag Less Than) : Drapeau plus petit pour les comparaisons.
  - FEQ (Flag Equal) : Drapeau égal pour les comparaisons.
  - FGT (Flag Greater Than) : Drapeau plus grand pour les comparaisons.

---

# Mot clés
1. **`setq`** :
   - **Description** : Assignation de valeurs à des variables spécifiques.
   - **Utilisation dans le code** : Utilisé pour définir les valeurs de `liste_registre`, `liste_drapeau`, et `mem_size_default`.

2. **`'(R0 R1 R2 SP BP FP PC PCO)`** et **`'(FLT FEQ FGT FNIL)`** :
   - **Description** : Listes de symboles représentant les noms des registres et des drapeaux.
   - **Utilisation dans le code** : Définition des listes `liste_registre` et `liste_drapeau`.

3. **`defun`** :
   - **Description** : Définition d'une nouvelle fonction Lisp.
   - **Utilisation dans le code** : Définition des fonctions `is_register_?`, `vm_get_register`, `vm_set_register`, `is_flag_?`, `vm_get_flag`, `vm_set_flag_ON`, `vm_set_flag_OFF`, et `vm_init`.

4. **`&optional`** :
   - **Description** : Permet de définir des arguments optionnels dans la signature d'une fonction.
   - **Utilisation dans le code** : Utilisé dans la signature de la fonction `vm_init` pour définir l'argument optionnel `size`.

5. **`if`** :
   - **Description** : Structure de contrôle conditionnelle.
   - **Utilisation dans le code** : Utilisé pour effectuer des actions conditionnelles dans les fonctions `vm_get_register`, `vm_set_register`, `vm_get_flag`, `vm_set_flag_ON`, `vm_set_flag_OFF`, et `vm_init`.

6. **`position`** :
   - **Description** : Recherche la position d'un élément dans une liste.
   - **Utilisation dans le code** : Utilisé dans les fonctions `is_register_?` et `is_flag_?` pour vérifier si un symbole est un registre ou un drapeau valide.

7. **`get`** et **`setf`** :
   - **Description** : `get` permet d'extraire la valeur associée à une clé dans une structure de données, et `setf` permet de modifier cette valeur.
   - **Utilisation dans le code** : Utilisé dans les fonctions `vm_get_register`, `vm_set_register`, `vm_get_flag`, `vm_set_flag_ON`, et `vm_set_flag_OFF` pour accéder et modifier les valeurs des registres et drapeaux dans la machine virtuelle.

8. **`trace`** :
   - **Description** : Active la traçabilité pour une fonction spécifique, ce qui signifie que chaque appel de cette fonction est affiché pendant l'exécution du programme.
   - **Utilisation dans le code** : Utilisé pour activer la traçabilité pour les fonctions `vm_set_register`, `is_register_?`, `vm_get_flag`, `vm_set_flag_ON`, `vm_set_flag_OFF`, et `vm_init`.

9. **`floor`** :
   - **Description** : Arrondit un nombre à l'entier inférieur le plus proche.
   - **Utilisation dans le code** : Utilisé dans la fonction `vm_init` pour calculer les valeurs initiales des registres `SP`, `BP`, et `FP` en fonction de la taille de la mémoire.

10. **`null`** :
    - **Description** : Teste si une expression est nulle.
    - **Utilisation dans le code** : Utilisé dans la fonction `vm_init` pour vérifier si la taille (`size`) est spécifiée.

11. **`warn`** :
    - **Description** : Affiche un avertissement pendant l'exécution du programme.
    - **Utilisation dans le code** : Utilisé pour émettre des avertissements dans les fonctions `vm_get_register`, `vm_set_register`, `vm_get_flag`, `vm_set_flag_ON`, et `vm_set_flag_OFF` lorsque des erreurs sont rencontrées.

12. **`vm_init_memory`**, **`vm_init_hashTab_etq_resolu`**, et **`vm_init_hashTab_etq_non_resolu`** :
    - **Description** : Fonctions qui ne sont pas fournies dans le code partagé, mais dont l'utilisation est impliquée.
    - **Utilisation dans le code** : Utilisé dans la fonction `vm_init` pour initialiser différents aspects de la machine virtuelle.