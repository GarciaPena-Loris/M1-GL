# Registres

- **Registres Généraux :**
  - R0
  - R1
  - R2

- **Registres Dédiés :**
  - BP (Base Pointer) : Indique le début de la pile d'exécution.
  - SP (Stack Pointer) : Indique le sommet de la pile d'exécution.
  - PC (Program Counter) : Contient le compteur de programme ou compteur ordinal.
  - PCO (Counter Ordinal)

- **Registre de Cadre (Introduit ultérieurement) :**
  - FP (Frame Pointer) : Pointeur de cadre utilisé pour structurer les blocs de la pile.

- **Drapeaux (1 bit chacun) :**
  - FLT (Flag Less Than) : Drapeau plus petit pour les comparaisons.
  - FEQ (Flag Equal) : Drapeau égal pour les comparaisons.
  - FGT (Flag Greater Than) : Drapeau plus grand pour les comparaisons.

---

# Explication code
- **Tables de hachages :**
  La table de hachage dans le contexte de la machine virtuelle facilite la résolution efficace des références en avance, telles que les étiquettes et les symboles, en associant rapidement les noms aux adresses mémoire correspondantes. Elle optimise la recherche et la récupération d'informations clés, contribuant ainsi à la performance globale de la gestion des instructions, des données, et des symboles pendant l'exécution du programme.
- **Étiquette:**
  Dans le contexte des machines virtuelles et de l'assembleur, les étiquettes sont des marques ou des labels utilisés pour identifier des emplacements spécifiques dans le code source. Elles sont généralement associées à des adresses mémoire ou à des positions dans le programme. Les étiquettes sont utilisées pour faciliter la lecture et la compréhension du code, ainsi que pour permettre des sauts conditionnels ou inconditionnels vers des parties spécifiques du programme.

    Dans le code assembleur, une étiquette est généralement placée à une position particulière dans le programme, et elle peut être utilisée comme destination pour des instructions de saut (comme JMP, JEQ, etc.). Lorsque le programme est assemblé, les étiquettes sont généralement associées à des adresses mémoire spécifiques.

    Dans le contexte de votre machine virtuelle, l'utilisation des étiquettes semble suivre un modèle similaire. Les fonctions que vous avez fournies semblent traiter la gestion des étiquettes, en maintenant une table de hachage pour les étiquettes résolues (vm_get_hashTab_etq_resolu), en mettant à jour les registres PC et PCO lors de l'utilisation d'une étiquette, et en émettant des avertissements en cas d'erreurs liées aux étiquettes.

    En résumé, les étiquettes dans votre machine virtuelle sont des marques symboliques qui représentent des positions ou des adresses dans le programme, facilitant la gestion du flux d'exécution du programme et des sauts conditionnels ou inconditionnels.

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

12. **`progn`** :
    - **Description** : évaluer plusieurs expressions séquentiellement et de renvoyer la valeur de la dernière expression..
    - **Utilisation dans le code** : progn est une construction Lisp permettant d'évaluer plusieurs expressions séquentiellement et de renvoyer la valeur de la dernière expression. Elle est utilisée pour regrouper des expressions sans introduire de nouvelle portée, souvent dans le contexte où l'ordre d'évaluation est crucial

13. **`eq`** :
    - **Description** : Opérateur de comparaison pour tester l'égalité.
    - **Utilisation dans le code** : Utilisé pour comparer la première partie de src (opérateur arithmétique) avec différents symboles ('-', '+', '*', '/').

14. **`first, second, et third`** :
    - **Description** : Fonctions pour accéder aux éléments d'une liste.
    - **Utilisation dans le code** : Utilisées pour extraire les composants d'une expression arithmétique de src (par exemple, (first src) donne l'opérateur arithmétique).

15. **`integerp`** :
    - **Description** : Fonction qui renvoie vrai si l'argument est un entier, sinon faux.
    - **Utilisation dans le code** : Utilisée pour vérifier si src est un entier.

16. **`svref`** :
    - **Description** : Fonction pour extraire une valeur spécifique d'un simple vecteur.
    - **Utilisation dans le code** : Utilisée pour accéder à la mémoire virtuelle de la machine (vm_get_memory) à une position spécifiée.

17. **`setf`** :
    - **Description** : setf est un opérateur puissant en Lisp utilisé pour assigner des valeurs à des emplacements spécifiques, que ce soit des variables, des éléments de listes, des champs de structures, etc. Il est également utilisé pour la modification de valeurs.
    - **Utilisation dans le code** : Dans le contexte du code Lisp, setf est utilisé pour modifier la valeur d'un emplacement mémoire spécifique, souvent une cellule de la mémoire virtuelle (svref (vm_get_memory vm) ...) ou la valeur d'un registre de la machine virtuelle ((vm_set_register vm dest ...), par exemple).

19. **`aref`** :
    - **Description** : aref est une fonction qui permet d'accéder à la valeur d'un élément particulier dans un tableau multidimensionnel (vecteur ou tableau). L'indice de l'élément à extraire est spécifié pour chaque dimension du tableau.
    - **Utilisation dans le code** :  Dans le code Lisp, aref est utilisé pour accéder à la valeur d'un élément spécifique dans un vecteur (svref) représentant la mémoire virtuelle de la machine. Par exemple, (svref (vm_get_memory vm) index) utilise aref pour obtenir la valeur à l'indice index dans le vecteur représentant la mémoire virtuelle.

20. **`car`** :
    - **Description** : car est une fonction Lisp qui renvoie le premier élément d'une liste.
    - **Utilisation dans le code** :  Dans l'expression (car src), car est utilisé pour obtenir le premier élément de la liste src. Par exemple, (car src) renverra le symbole :LIT si src est une liste de la forme (:LIT . n).

21. **`cdr`** :
    - **Description** : cdr est une fonction Lisp qui renvoie tous les éléments d'une liste sauf le premier (c'est-à-dire, la partie de la liste après le premier élément).
    - **Utilisation dans le code** :  Dans l'expression (cdr src), cdr est utilisé pour obtenir la partie de la liste src après le premier élément. Par exemple, (cdr src) renverra n si src est une liste de la forme (:LIT . n).

22. **`cond`** :
    - **Description** : cond est une construction Lisp utilisée pour évaluer plusieurs conditions de manière séquentielle et exécuter le bloc de code de la première clause dont la condition est vraie.
    - **Utilisation dans le code** :  Chaque clause de cond est une paire de la forme (condition bloc-de-code). La première condition qui est vraie détermine la clause à exécuter.
