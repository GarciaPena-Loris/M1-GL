(require "VirtualMachine.lisp")

; ########### Fonction de creation d'une machine vitruelle ###########
(defun create-vm-instance (size) ; Fonction de creation d'une instance de la machine virtuelle
    (let ((vm))
        (init-vm vm size)  ; Initialisation de la machine virtuelle
    vm))
; ################################################## ;;

;; ########### Fonction de test getter and setter ###########
;; Fonction de test pour les getter et setter de la machine virtuelle
(defun test-getters-setters-vm ()
    (format t "### Test des getters et setters ###~%")
    (let ((vm (create-vm-instance 100))) ;; Creer une machine virtuelle de taille 100
    (set-memoire-vm vm 100) ; Initialiser la machine virtuelle

    (format t "- Etat vm~%")
    (format t "   Etat vm general : ~A~%" (etat-vm vm))
    (format t "   Taille initiale de la memoire : ~A~%" (get-taille-memoire-vm vm))
    (format t "   Etat initial de la memoire : ~A~%" (get-etat-memoire-vm vm))

    ;; Test de la modification de la memoire
    (set-memoire-vm vm 200)
    (format t "   Taille de la memoire apres modification : ~A~%" (get-taille-memoire-vm vm))

    ;; Test de l'acces a un element de la memoire
    (setf (aref (get-memoire-vm vm) 0) 42)
    (format t "   Etat de l'element de memoire a l'indice 0 : ~A~%" (get-etat-element-memoire-vm vm 0))

    ;; Tester les registres
    (format t "- Test des registres~%")
    (format t "   Valeur du registre R0 : ~A~%" (get-register-value vm 'R0))
    (set-register-value vm 'R0 42)
    (if (not (= (get-register-value vm 'R0) 42))
        (return-from test-getters-setters-vm nil)) ; Echec du test
    (format t "   Valeur du registre R0 : ~A~%" (get-register-value vm 'R0))
    
    ;; Tester les drapeaux
    (format t "- Test des drapeaux~%")
    (format t "   Valeur du drapeau FEQ : ~A~%" (get-flag-value vm 'FEQ))
    (set-flag-on vm 'FEQ)
    (if (not (get-flag-value vm 'FEQ))
        (return-from test-getters-setters-vm nil)) ; Echec du test
    (format t "   Valeur du drapeau FEQ : ~A~%" (get-flag-value vm 'FEQ))

    ;; Tester les tables de hachage des etiquettes resolues
    (format t "- Test des tables de hachage~%")
    ;; Test des fonctions liees aux tables de hachage des etiquettes resolues
    (let ((vm (create-vm-instance 100)))
    (format t "   Etat initial de la table des etiquettes resolues : ~A~%" (state-hashTab-label-resolu vm))
    
    ;; Test de l'ajout et de l'acces a une valeur dans la table des etiquettes resolues
    (set-hashTab-label-resolu vm '(LABEL1) 10)
    (format t "   Etat de la table des etiquettes resolues apres ajout : ~A~%" (state-hashTab-label-resolu vm))
    (format t "   Valeur associee a LABEL1 dans la table des etiquettes resolues : ~A~%" (get-hashTab-label-resolu-val vm '(LABEL1))))

    ;; Test des fonctions liees aux tables de hachage des etiquettes non resolues
    (let ((vm (create-vm-instance 100)))
    (format t "   Etat initial de la table des etiquettes non resolues : ~A~%" (state-hashTab-label-non-resolu vm))
    
    ;; Test de l'ajout et de l'acces a une valeur dans la table des etiquettes non resolues
    (set-hashTab-label-non-resolu vm '(LABEL2) 20)
    (format t "   Etat de la table des etiquettes non resolues apres ajout : ~A~%" (state-hashTab-label-non-resolu vm))
    (format t "   Valeur associee a LABEL2 dans la table des etiquettes non resolues : ~A~%" (get-hashTab-label-non-resolu-val vm '(LABEL2))))
    
    (format t "### Tests reussis pour les getters et setters de la machine virtuelle ###~%~%")
    t)) ; La fonction renvoie t si le test reussit, nil sinon

;; ################################################## ;;

;; ########### Fonction de test pour LABEL ###########
;; Fonction de test pour la fonction label-vm et les etiquettes
(defun test-label-vm ()
    (format t "### Test de la fonction label-vm et des etiquettes ###~%")

    ;; Creation de la machine virtuelle
    (let ((vm (create-vm-instance 100)))
    
    ;; Test de l'ajout d'une etiquette et de l'acces a sa valeur dans la table des etiquettes resolues
    (label-vm vm '(LABEL1))
    (incr-vm vm 'PCc)
    (format t "Adresse associee a LABEL1 dans la table des etiquettes resolues : ~A~%" (get-hashTab-label-resolu-val vm 'LABEL1))
    
    ;; Test de l'ajout d'une etiquette et de l'acces a sa valeur dans la table des etiquettes non resolues
    (label-vm vm '(LABEL2))
    (incr-vm vm 'PCc)
    (format t "Adresse associee a LABEL2 dans la table des etiquettes resolues : ~A~%" (get-hashTab-label-resolu-val vm 'LABEL2))
    
    ;; Test de la reutilisation d'une etiquette deja presente dans la table des etiquettes resolues
    (label-vm vm '(LABEL1))
    (format t "Adresse associee a LABEL1 apres reutilisation : ~A~%" (get-hashTab-label-resolu-val vm 'LABEL1))

    (format t "### Tests reussis pour la fonction label-vm et les etiquettes ###~%~%")
    t)) ; La fonction renvoie t si le test reussit, nil sinon
;; ################################################## ;;

;; ########### Fonction de test pour ADD ###########
;; Fonction de test pour la fonction add-vm
(defun test-add-vm ()
    (format t "### Test de la fonction add-vm ###~%")

    ;; Creation de la machine virtuelle
    (let ((vm (create-vm-instance 100)))

    ;; Initialisation des registres
    (set-register-value vm 'R1 10)
    (set-register-value vm 'R2 5)
    (format t "   Valeur du registre R1 : ~A~%" (get-register-value vm 'R1))
    (format t "   Valeur du registre R2 : ~A~%" (get-register-value vm 'R2))

    ;; Test d'addition avec une constante
    (format t "- Additionne la valeur du registre R1 avec une constante (7)~%")
    (add-vm vm 7 'R1)
    (if (not (= (get-register-value vm 'R1) 17))
        (return-from test-add-vm nil)) ; Echec du test
    (format t "   Valeur du registre R1 : ~A~%" (get-register-value vm 'R1))

    ;; Test d'addition avec un registre
    (format t "- Additionne la valeur du registre R2 avec la valeur du registre R1~%")
    (add-vm vm 'R2 'R1)
    (if (not (= (get-register-value vm 'R1) 22))
        (return-from test-add-vm nil)) ; Echec du test
    (format t "   Valeur du registre R1 : ~A~%" (get-register-value vm 'R1))

    ;; Test d'addition avec une expression
    (format t "- Additionne la valeur du registre R1 avec une expression (3)~%")
    (add-vm vm '(:LIT . 3) 'R1)
    (if (not (= (get-register-value vm 'R1) 25))
        (return-from test-add-vm nil)) ; Echec du test
    (format t "   Valeur du registre R1 : ~A~%" (get-register-value vm 'R1))

    ;; Test d'addition avec une expression mal formee (non de la forme <:LIT entier>)
    (format t "- Additionne la valeur du registre R1 avec une expression mal formee~%")
    (add-vm vm '(:LIT) 'R1)

    (format t "### Tests reussis pour la fonction add-vm ###~%~%")
    t)) ; La fonction renvoie t si le test reussit, nil sinon
;; ################################################## ;;

;; ########### Fonction de test pour SUB ###########
;; Fonction de test pour la fonction sub-vm
(defun test-sub-vm ()
    (format t "### Test de la fonction sub-vm ###~%")

    ;; Creation de la machine virtuelle
    (let ((vm (create-vm-instance 100)))

    ;; Initialisation des registres
    (set-register-value vm 'R1 10)
    (set-register-value vm 'R2 5)
    (format t "   Valeur du registre R1 : ~A~%" (get-register-value vm 'R1))
    (format t "   Valeur du registre R2 : ~A~%" (get-register-value vm 'R2))

    ;; Test d'soustrait avec une constante
    (format t "- Soustrait la valeur du registre R1 avec une constante (7)~%")
    (sub-vm vm 7 'R1)
    (if (not (= (get-register-value vm 'R1) 3))
        (return-from test-sub-vm nil)) ; Echec du test
    (format t "   Valeur du registre R1 : ~A~%" (get-register-value vm 'R1))

    ;; Test d'soustrait avec un registre
    (format t "- Soustrait la valeur du registre R2 avec la valeur du registre R1~%")
    (sub-vm vm 'R2 'R1)
    (if (not (= (get-register-value vm 'R1) -2))
        (return-from test-sub-vm nil)) ; Echec du test
    (format t "   Valeur du registre R1 : ~A~%" (get-register-value vm 'R1))

    ;; Test d'soustrait avec une expression
    (format t "- Soustrait la valeur du registre R1 avec une expression (3)~%")
    (sub-vm vm '(:LIT . 3) 'R1)
    (if (not (= (get-register-value vm 'R1) -5))
        (return-from test-sub-vm nil)) ; Echec du test
    (format t "   Valeur du registre R1 : ~A~%" (get-register-value vm 'R1))

    ;; Test d'soustrait avec une expression mal formee (non de la forme <:LIT . entier>)
    (format t "- Soustrait la valeur du registre R1 avec une expression mal formee~%")
    (sub-vm vm '(:LIT) 'R1)

    (format t "### Tests reussis pour la fonction sub-vm ###~%~%")
    t)) ; La fonction renvoie t si le test reussit, nil sinon
;; ################################################## ;;

;; ########### Fonction de test pour MUL ###########
;; Fonction de test pour la fonction mul-vm
(defun test-mul-vm ()
    (format t "### Test de la fonction mul-vm ###~%")

    ;; Creation de la machine virtuelle
    (let ((vm (create-vm-instance 100)))

    ;; Initialisation des registres
    (set-register-value vm 'R1 10)
    (set-register-value vm 'R2 5)
    (format t "   Valeur du registre R1 : ~A~%" (get-register-value vm 'R1))
    (format t "   Valeur du registre R2 : ~A~%" (get-register-value vm 'R2))

    ;; Test de multiplication avec une constante
    (format t "- Multiplie la valeur du registre R1 avec une constante (7)~%")
    (mul-vm vm 7 'R1)
    (if (not (= (get-register-value vm 'R1) 70))
        (return-from test-mul-vm nil)) ; Echec du test
    (format t "   Valeur du registre R1 : ~A~%" (get-register-value vm 'R1))

    ;; Test de multiplication avec un registre
    (format t "- Multiplie la valeur du registre R2 avec la valeur du registre R1~%")
    (mul-vm vm 'R2 'R1)
    (if (not (= (get-register-value vm 'R1) 350))
        (return-from test-mul-vm nil)) ; Echec du test
    (format t "   Valeur du registre R1 : ~A~%" (get-register-value vm 'R1))

    ;; Test de multiplication avec une expression
    (format t "- Multiplie la valeur du registre R1 avec une expression (3)~%")
    (mul-vm vm '(:LIT . 3) 'R1)
    (if (not (= (get-register-value vm 'R1) 1050))
        (return-from test-mul-vm nil)) ; Echec du test
    (format t "   Valeur du registre R1 : ~A~%" (get-register-value vm 'R1))

    ;; Test de multiplication avec une expression mal formee (non de la forme <:LIT . entier>)
    (format t "- Multiplie la valeur du registre R1 avec une expression mal formee~%")
    (mul-vm vm '(:LIT) 'R1)

    (format t "### Tests reussis pour la fonction mul-vm ###~%~%")
    t)) ; La fonction renvoie t si le test reussit, nil sinon
;; ################################################## ;;

;; ########### Fonction de test pour DIV ###########
;; Fonction de test pour la fonction div-vm
(defun test-div-vm ()
    (format t "### Test de la fonction div-vm ###~%")

    ;; Creation de la machine virtuelle
    (let ((vm (create-vm-instance 100)))

    ;; Initialisation des registres
    (set-register-value vm 'R1 55)
    (set-register-value vm 'R2 5)
    (format t "   Valeur du registre R1 : ~A~%" (get-register-value vm 'R1))
    (format t "   Valeur du registre R2 : ~A~%" (get-register-value vm 'R2))

    ;; Test de division avec une constante
    (format t "- Divise la valeur du registre R1 avec une constante (7)~%")
    (div-vm vm 7 'R1)
    (if (not (= (get-register-value vm 'R1) 7))
        (return-from test-div-vm nil)) ; Echec du test
    (format t "   Valeur du registre R1 : ~A~%" (get-register-value vm 'R1))

    ;; Test de division par 0
    (format t "- Divise la valeur du registre R1 par 0~%")
    (div-vm vm 0 'R1)
    (if (not (= (get-register-value vm 'R1) 7))
        (return-from test-div-vm nil)) ; Echec du test
    (format t "   Valeur du registre R1 : ~A~%" (get-register-value vm 'R1))

    ;; Test de division avec un registre
    (format t "- Divise la valeur du registre R2 avec la valeur du registre R1~%")
    (div-vm vm 'R2 'R1)
    (if (not (= (get-register-value vm 'R1) 1))
        (return-from test-div-vm nil)) ; Echec du test
    (format t "   Valeur du registre R1 : ~A~%" (get-register-value vm 'R1))

    ;; Test de division avec une expression
    (format t "- Divise la valeur du registre R1 avec une expression (3)~%")
    (div-vm vm '(:LIT . 3) 'R1)
    (if (not (= (get-register-value vm 'R1) 0))
        (return-from test-div-vm nil)) ; Echec du test
    (format t "   Valeur du registre R1 : ~A~%" (get-register-value vm 'R1))

    ;; Test de division avec une expression mal formee (non de la forme <:LIT . entier>)
    (format t "- Divise la valeur du registre R1 avec une expression mal formee~%")
    (div-vm vm '(:LIT) 'R1)
    
    (format t "### Tests reussis pour la fonction div-vm ###~%~%")
    t)) ; La fonction renvoie t si le test reussit, nil sinon
;; ################################################## ;;

;; ########### Fonction de test pour INCR ###########
(defun test-incr-vm ()
    (format t "### Test de la fonction incr-vm ###~%")
    (let ((vm (create-vm-instance 10))) ; Cree une nouvelle instance de la machine virtuelle
    ; Initialise les registres et la memoire avec des valeurs specifiques pour les tests
    (set-register-value vm 'R0 41)
    (set-register-value vm 'PC 7)
    (set-register-value vm 'BP 2)
    (format t "   Valeur du registre R0 : ~A~%" (get-register-value vm 'R0))
    (format t "   Valeur du registre PC : ~A~%" (get-register-value vm 'PC))
    (format t "   Valeur du registre BP : ~A~%" (get-register-value vm 'BP))
    
    ; Test avec un registre valide
    (format t "- Incremente le registre R0 ~%")
    (incr-vm vm 'R0)
    (if (not (= (get-register-value vm 'R0) 42))
        (return-from test-incr-vm nil)) ; Echec du test
    (format t "   Valeur du registre R0 : ~A~%" (get-register-value vm 'R0))
    
    ; Test avec un registre depassant les limites
    (format t "- Incremente le registre PC ~%")
    (incr-vm vm 'PC)
    (if (not (= (get-register-value vm 'PC) 8))
        (return-from test-incr-vm nil)) ; Echec du test
    (format t "   Valeur du registre PC : ~A~%" (get-register-value vm 'PC))
    (format t "- Incremente le registre PC ~%")
    (incr-vm vm 'PC)
    (if (not (= (get-register-value vm 'PC) 8))
        (return-from test-incr-vm nil)) ; Echec du test
    (format t "   Valeur du registre PC : ~A~%" (get-register-value vm 'PC))
    
    (format t "### Tests reussis pour la fonction incr-vm ###~%~%")
    t)) ; La fonction renvoie t si le test reussit, nil sinon
;; ################################################## ;;

;; ########### Fonction de test pour DECR ###########
(defun test-decr-vm ()
    (format t "### Test de la fonction decr-vm ###~%")
    (let ((vm (create-vm-instance 15))) ; Cree une nouvelle instance de la machine virtuelle
    ; Initialise les registres et la memoire avec des valeurs specifiques pour les tests
    (set-register-value vm 'R0 10)
    (set-register-value vm 'PC 3)
    (set-register-value vm 'BP 2)
    (format t "   Valeur du registre R0 : ~A~%" (get-register-value vm 'R0))
    (format t "   Valeur du registre PC : ~A~%" (get-register-value vm 'PC))
    (format t "   Valeur du registre BP : ~A~%" (get-register-value vm 'BP))
    
    ; Test avec un registre valide
    (format t "- Decremente le registre R0 ~%")
    (decr-vm vm 'R0)
    (if (not (= (get-register-value vm 'R0) 9))
        (return-from test-decr-vm nil)) ; Echec du test
    (format t "   Valeur du registre R0 : ~A~%" (get-register-value vm 'R0))
    
    ; Test avec un registre depassant les limites
    (format t "- Decremente le registre PC ~%")
    (decr-vm vm 'PC)
    (if (not (= (get-register-value vm 'PC) 2))
        (return-from test-decr-vm nil)) ; Echec du test
    (format t "   Valeur du registre PC : ~A~%" (get-register-value vm 'PC))
    (format t "- Decremente le registre PC ~%")
    (decr-vm vm 'PC)
    (if (not (= (get-register-value vm 'PC) 2))
        (return-from test-decr-vm nil)) ; Echec du test
    (format t "   Valeur du registre PC : ~A~%" (get-register-value vm 'PC))
    
    (format t "### Tests reussis pour la fonction decr-vm ###~%~%")
    t)) ; La fonction renvoie t si le test reussit, nil sinon
;; ################################################## ;;

;; ########### Fonction de test pour STORE ###########
;; Fonction de test pour la fonction store-vm
(defun test-store-vm ()
    (format t "### Test de la fonction store-vm ###~%")
    ;; Creation de la machine virtuelle
    (let ((vm (create-vm-instance 600)))

        ;; Initialisation des registres
        (set-register-value vm 'R0 42)
        (set-register-value vm 'R1 100)
        (set-register-value vm 'R2 2)
        (format t "   Valeur du registre R0 : ~A~%" (get-register-value vm 'R0))
        (format t "   Valeur du registre R1 : ~A~%" (get-register-value vm 'R1))

        ;; Test 1: Stocker la valeur du registre R0 a l'adresse memoire 500
        (format t "- Stocker la valeur du registre R0 a l'adresse memoire 500~%")
        (store-vm vm 'R0 500)
        (if (not (= (get-etat-element-memoire-vm vm 500) 42))
            (return-from test-store-vm nil)) ; Echec du test
        (format t "   Valeur a l'adresse memoire 500 : ~A~%" (get-etat-element-memoire-vm vm 500))

        ;; Test 2: Stocker la valeur 5 a l'adresse memoire contenue dans le registre R1
        (format t "- Stocker la valeur 5 a l'adresse memoire contenue dans le registre R1~%")
        (store-vm vm 5 'R1)
        (if (not (= (get-etat-element-memoire-vm vm 100) 5))
            (return-from test-store-vm nil)) ; Echec du test
        (format t "   Valeur a l'adresse memoire 100 : ~A~%" (get-etat-element-memoire-vm vm 100))

        ;; Test 3: Stocker la valeur du registre R0 a l'adresse memoire contenue dans le registre R1
        (format t "- Stocker la valeur du registre R0 a l'adresse memoire contenue dans le registre R1~%")
        (store-vm vm 'R0 'R1)
        (if (not (= (get-etat-element-memoire-vm vm 100) 42))
            (return-from test-store-vm nil)) ; Echec du test
        (format t "   Valeur a l'adresse memoire 100 : ~A~%" (get-etat-element-memoire-vm vm 100))

        ;; Test 4: Stocker la valeur (:CONST 12) a l'adresse memoire (:CONST 5)
        (format t "- Stocker la valeur (:CONST 12) a l'adresse memoire (:CONST 5)~%")
        (store-vm vm 12 5)
        (if (not (= (get-etat-element-memoire-vm vm 5) 12))
            (return-from test-store-vm nil)) ; Echec du test
        (format t "   Valeur a l'adresse memoire 5 : ~A~%" (get-etat-element-memoire-vm vm 5))

        ;; Test 5: Stocker la valeur du registre R2 + 3 a l'adresse memoire contenue dans le registre R1 + 20
        (format t "   Valeur du registre R1 : ~A~%" (get-register-value vm 'R1))
        (format t "   Valeur du registre R2 : ~A~%" (get-register-value vm 'R2))
        (format t "- Stocker la valeur du registre R2 + 3 a l'adresse memoire contenue dans le registre R1 + 20~%")
        (store-vm vm '(+ R2 3) '(+ R1 20))
        (if (not (= (get-etat-element-memoire-vm vm 120) 5))
            (return-from test-store-vm nil)) ; Echec du test
        (format t "   Valeur a l'adresse memoire 120 : ~A~%" (get-etat-element-memoire-vm vm 120))

        ;; Test 6: Tentative de stocker a une adresse memoire hors limite
        (format t "- Tentative de stocker a une adresse memoire hors limite~%")
        (store-vm vm 'R0 1000) ; Devrait afficher un avertissement

        ; # Test 7: Test avec des litteraux
        (format t "- Stocke la valeur 5 a l'adresse memoire 42~%")
        (store-vm vm '(:LIT . 5) '(:LIT . 42)) ; Stocke la valeur 5 a l'adresse memoire 42
        (if (not (= (aref (get-memoire-vm vm) 42) 5))
            (return-from test-store-vm nil)) ; Echec du test
        (format t "   Valeur a l'adresse memoire 5 : ~A~%" (aref (get-memoire-vm vm) 5))

    (format t "### Tests reussis pour la fonction store-vm ###~%~%")
    t)) ; La fonction renvoie t si le test reussit, nil sinon
;; ################################################## ;;

;; ########### Fonction de test pour LOAD ###########
;; Fonction de test pour la fonction load-vm
(defun test-load-vm ()
    (format t "### Test de la fonction load-vm ###~%")
    ;; Creation de la machine virtuelle
    (let ((vm (create-vm-instance 600)))

        ;; Initialisation des registres
        (set-register-value vm 'R0 42)
        (set-register-value vm 'R1 100)
        (set-register-value vm 'R2 2)
        (format t "   Valeur du registre R0 : ~A~%" (get-register-value vm 'R0))
        (format t "   Valeur du registre R1 : ~A~%" (get-register-value vm 'R1))

        (store-vm vm 10 50) ; Met une valeur quelconque a l'adresse 50 pour les tests

        ;; Test 1: Charger la valeur a l'adresse memoire 50 dans le registre R0
        (format t "- Charger la valeur a l'adresse memoire 50 dans le registre R0~%")
        (load-vm vm 50 'R0)
        (if (not (= (get-register-value vm 'R0) 10))
            (return-from test-load-vm nil)) ; Echec du test
        (format t "   Valeur dans le registre R0 : ~A~%" (get-register-value vm 'R0))

        ;; Test 2: Charger la valeur a l'adresse memoire contenue dans le registre R1 dans le registre R2
        (format t "- Charger la valeur a l'adresse memoire contenue dans le registre R1 dans le registre R2~%")
        (store-vm vm 20 'R1) ; Met une valeur quelconque a l'adresse contenue dans R1 pour les tests
        (load-vm vm 'R1 'R2)
        (if (not (= (get-register-value vm 'R2) 20))
            (return-from test-load-vm nil)) ; Echec du test
        (format t "   Valeur dans le registre R2 : ~A~%" (get-register-value vm 'R2))

        ;; Test 3: Charge la valeur du registre R2 + 3 a l'adresse memoire contenue dans le registre R1 + 20
        (format t "   Valeur du registre R1 : ~A~%" (get-register-value vm 'R1))
        (format t "   Valeur du registre R2 : ~A~%" (get-register-value vm 'R2))
        (store-vm vm 999 '(+ R2 3))
        (format t "- Charger la valeur du registre R2 + 3 dans le registre R1~%")
        (load-vm vm '(+ R2 3) 'R1)
        (if (not (= (get-register-value vm 'R1) 999))
            (return-from test-load-vm nil)) ; Echec du test
        (format t "   Valeur dans le registre R1 : ~A~%" (get-register-value vm 'R1))
        
        ;; Test 4: Tentative de chargement a une adresse memoire hors limite
        (format t "- Tentative de chargement a une adresse memoire hors limite~%")
        (load-vm vm 1000 'R0) ; Devrait afficher un avertissement

        ;; Test 5: Charger la valeur (LIT . 5) dans le registre R0
        (format t "- Charger la valeur <LIT . 5> dans le registre R0~%")
        (store-vm vm 50 5) ; Met une valeur quelconque a l'adresse 50 pour les tests
        (load-vm vm '(:LIT . 5) 'R1)
        (if (not (= (get-register-value vm 'R1) 50))
            (return-from test-load-vm nil)) ; Echec du test
        (format t "   Valeur dans le registre R1 : ~A~%" (get-register-value vm 'R1))

    (format t "### Tests reussis pour la fonction load-vm ###~%~%")
    t)) ; La fonction renvoie t si le test reussit, nil sinon
;; ################################################## ;;

;; ########### Fonction de test pour MOVE ###########
;; Fonction de test pour la fonction move-vm
(defun test-move-vm ()
    (format t "### Test de la fonction move-vm ###~%")
    ;; Creation de la machine virtuelle
    (let ((vm (create-vm-instance 600)))

        ;; Initialisation des registres
        (set-register-value vm 'R0 42)
        (set-register-value vm 'R1 100)
        (set-register-value vm 'R2 2)
        (format t "   Valeur du registre R0 : ~A~%" (get-register-value vm 'R0))
        (format t "   Valeur du registre R1 : ~A~%" (get-register-value vm 'R1))

        ;; Test 1: Copier la valeur du registre R0 dans le registre R1
        (format t "- Copier la valeur du registre R0 dans le registre R1~%")
        (move-vm vm 'R0 'R1)
        (if (not (= (get-register-value vm 'R1) 42))
            (return-from test-move-vm nil)) ; Échec du test
        (format t "   Valeur dans le registre R1 : ~A~%" (get-register-value vm 'R1))

        ;; Test 2: Copier la constante 50 dans le registre R0
        (format t "- Copier la constante 50 dans le registre R0~%")
        (move-vm vm '(:CONST . 50) 'R0)
        (if (not (= (get-register-value vm 'R0) 50))
            (return-from test-move-vm nil)) ; Échec du test
        (format t "   Valeur dans le registre R0 : ~A~%" (get-register-value vm 'R0))

        ;; Test 3: Copier 88 dans le registre R2
        (format t "- Copier 88 dans le registre R2~%")
        (move-vm vm 88 'R2)
        (if (not (= (get-register-value vm 'R2) 88))
            (return-from test-move-vm nil)) ; Échec du test
        (format t "   Valeur dans le registre R2 : ~A~%" (get-register-value vm 'R2))

        ;; Test 4: Copier la valeur du registre R2 dans le registre R1
        (format t "- Copier la valeur du registre R2 dans le registre R1~%")
        (move-vm vm 'R2 'R1)
        (if (not (= (get-register-value vm 'R1) 88))
            (return-from test-move-vm nil)) ; Échec du test
        (format t "   Valeur dans le registre R1 : ~A~%" (get-register-value vm 'R1))

        ;; Test 5: Deplacement dans une constante (non supporte)
        (format t "- Deplacement dans une constante (non supporte)~%")
        (move-vm vm 'R0 '(:CONST . 50))
    (format t "### Tests reussis pour la fonction move-vm ###~%~%")
    t)) ; La fonction renvoie t si le test reussit, nil sinon
;; ################################################## ;;

;; ########### Fonction de test pour PUSH ###########
;; Fonction de test pour la fonction push-vm
(defun test-push-vm ()
    (format t "### Test de la fonction push-vm ###~%")
    (let ((vm (create-vm-instance 100))) ; Cree une nouvelle instance de la machine virtuelle
    ; Initialise les registres et la memoire avec des valeurs specifiques pour les tests
    (set-register-value vm 'R0 42)
    (format t "   Valeur du registre SP : ~A~%" (get-register-value vm 'SP))
    (format t "   Valeur du registre R0 : ~A~%" (get-register-value vm 'R0))
    
    ; Test avec une valeur entiere
    (format t "- Empile la valeur 99 ~%")
    (push-vm vm 99)
    (if (not (= (get-register-value vm 'SP) 11))
        (return-from test-push-vm nil)) ; Échec du test
    (if (not (= (get-etat-element-memoire-vm vm (get-register-value vm 'SP)) 99))
        (return-from test-push-vm nil)) ; Échec du test
    (format t "   Valeur du registre SP : ~A~%" (get-register-value vm 'SP))
    (format t "   Valeur memoire a l'adresse SP : ~A~%" (get-etat-element-memoire-vm vm (get-register-value vm 'SP)))
    
    ; Test avec la valeur d'un registre
    (format t "- Empile la valeur du registre R0 ~%")
    (push-vm vm 'R0)
    (if (not (= (get-register-value vm 'SP) 12))
        (return-from test-push-vm nil)) ; Échec du test
    (if (not (= (get-etat-element-memoire-vm vm (get-register-value vm 'SP)) 42))
        (return-from test-push-vm nil)) ; Échec du test
    (format t "   Valeur du registre SP : ~A~%" (get-register-value vm 'SP))
    (format t "   Valeur memoire a l'adresse SP : ~A~%" (get-etat-element-memoire-vm vm (get-register-value vm 'SP)))

    ; Test avec une expression arithmetique
    (format t "- Empile la valeur de l'expression (+ 2 3) ~%")
    (push-vm vm '(+ 2 3))
    (if (not (= (get-register-value vm 'SP) 13))
        (return-from test-push-vm nil)) ; Échec du test
    (if (not (= (get-etat-element-memoire-vm vm (get-register-value vm 'SP)) 5))
        (return-from test-push-vm nil)) ; Échec du test
    (format t "   Valeur du registre SP : ~A~%" (get-register-value vm 'SP))
    (format t "   Valeur memoire a l'adresse SP : ~A~%" (get-etat-element-memoire-vm vm (get-register-value vm 'SP)))

    ; Test avec une constante (:CONST entier)
    (format t "- Empile la valeur de l'expression (:CONST 3) ~%")
    (push-vm vm '(:CONST . 3))
    (if (not (= (get-register-value vm 'SP) 14))
        (return-from test-push-vm nil)) ; Échec du test
    (if (not (= (get-etat-element-memoire-vm vm (get-register-value vm 'SP)) 3))
        (return-from test-push-vm nil)) ; Échec du test
    (format t "   Valeur du registre SP : ~A~%" (get-register-value vm 'SP))
    (format t "   Valeur memoire a l'adresse SP : ~A~%" (get-etat-element-memoire-vm vm (get-register-value vm 'SP)))

    ; Test avec une expression arithmetique (+ R0 entier)
    (format t "- Empile la valeur de l'expression (+ R0 2) ~%")
    (push-vm vm '(+ R0 2))
    (if (not (= (get-register-value vm 'SP) 15))
        (return-from test-push-vm nil)) ; Échec du test
    (if (not (= (get-etat-element-memoire-vm vm (get-register-value vm 'SP)) 44))
        (return-from test-push-vm nil)) ; Échec du test
    (format t "   Valeur du registre SP : ~A~%" (get-register-value vm 'SP))
    (format t "   Valeur memoire a l'adresse SP : ~A~%" (get-etat-element-memoire-vm vm (get-register-value vm 'SP)))

    (format t "### Tests reussis pour la fonction push-vm ###~%~%")
    t)) ; La fonction renvoie t si le test reussit, nil sinon
;; ################################################## ;;

;; ########### Fonction de test pour POP ###########
;; Fonction de test pour la fonction pop-vm
(defun test-pop-vm ()
    (format t "### Test de la fonction pop-vm ###~%")
    ;; Creation de la machine virtuelle
    (let ((vm (create-vm-instance 100)))

        ;; Initialisation des registres
        (set-register-value vm 'R0 42)
        (set-register-value vm 'R1 100)
        (set-register-value vm 'R2 2)
        (format t "   Valeur du registre R0 : ~A~%" (get-register-value vm 'R0))
        (format t "   Valeur du registre R1 : ~A~%" (get-register-value vm 'R1))

        ;; Initialisation de la pile
        (push-vm vm 1)
        (push-vm vm 2)
        (push-vm vm 3)
        (push-vm vm 4)
        (push-vm vm 5)
        (push-vm vm 6)
        (push-vm vm 7)
        (push-vm vm 8)
        (push-vm vm 9)
        (push-vm vm 10)

        ;; Test 1: Depiler une valeur de la pile dans le registre R0
        (format t "- Depiler une valeur de la pile dans le registre R0 ~%")
        (pop-vm vm 'R0)
        (if (not (= (get-register-value vm 'R0) 10))
            (return-from test-pop-vm nil)) ; Echec du test
        (if (not (= (get-register-value vm 'SP) 19))
            (return-from test-pop-vm nil)) ; Echec du test
        (format t "   Valeur du registre R0 : ~A~%" (get-register-value vm 'R0))
        (format t "   Valeur du registre SP : ~A~%" (get-register-value vm 'SP))

        ;; Test 2: Tentative de depiler dans un registre qui n'est pas un registre
        (format t "- Tentative de depiler dans un registre qui n'est pas un registre ~%")
        (pop-vm vm 123) ; Devrait afficher un avertissement

        ;; Test 3: Depillement de plusieur element a la suite avec un move (MOVE (:CONST 10) R1) (SUB R1 SP)
        (format t "- Depillement de 5 elements a la suite avec un move ~%")
        (move-vm vm '(:CONST . 5) 'R1)
        (format t "   Valeur du registre R1 : ~A~%" (get-register-value vm 'R1))
        (sub-vm vm 'R1 'SP)
        (pop-vm vm 'R0)
        (if (not (= (get-register-value vm 'R0) 4))
            (return-from test-pop-vm nil)) ; Echec du test
        (if (not (= (get-register-value vm 'SP) 13))
            (return-from test-pop-vm nil)) ; Echec du test
        (format t "   Valeur du registre R0 : ~A~%" (get-register-value vm 'R0))
        (format t "   Valeur du registre SP : ~A~%" (get-register-value vm 'SP))

    (format t "### Tests reussis pour la fonction pop-vm ###~%~%")
    t)) ; La fonction renvoie t si le test reussit, nil sinon
;; ################################################## ;;

;; ########### Fonction de test pour JUMP ###########
(defun test-jump-vm ()
    (format t "### Test de la fonction jump-vm ###~%")
    ;; Creation de la machine virtuelle
    (let ((vm (create-vm-instance 600)))
    
        ;; Initialisation des registres
        (set-register-value vm 'PC 0)
        (set-register-value vm 'SP 210)
    
        ;; Test 1: Saut inconditionnel vers une adresse (mode adresse)
        (format t "- Saut inconditionnel vers une adresse (mode adresse)~%")
        (jump-vm vm 150)
        (if (not (= (get-register-value vm 'PC) 150))
            (return-from test-jump-vm nil)) ; Échec du test
        (format t "   Valeur du registre PC apres le saut : ~A~%" (get-register-value vm 'PC))
    
        ;; Test 2: Saut inconditionnel vers une etiquette (mode etiquette)
        (format t "- Saut inconditionnel vers une etiquette (mode etiquette)~%")
        (set-register-value vm 'PCc 25)
        (label-vm vm '(etiquette1))
        (jump-vm vm 'etiquette1)
        (if (not (= (get-register-value vm 'PC) 25))
            (return-from test-jump-vm nil)) ; Échec du test
        (format t "   Valeur du registre PC apres le saut : ~A~%" (get-register-value vm 'PC))
    
    (format t "### Tests reussis pour la fonction jump-vm ###~%~%")
    t)) ; La fonction renvoie t si le test reussit, nil sinon
;; ######################################################### ;;

;; ########### Fonction de test pour JSR ###########
(defun test-jsr-vm ()
    (format t "### Test de la fonction jsr-vm ###~%")
    ;; Creation de la machine virtuelle
    (let ((vm (create-vm-instance 600)))
    
        ;; Initialisation des registres
        (set-register-value vm 'PC 100)
        (set-register-value vm 'SP 200)
    
        ;; Test 1: Saut avec sauvegarde de l'adresse courante sur la pile
        (format t "- Saut avec sauvegarde de l'adresse courante sur la pile~%")
        (set-register-value vm 'PCc 25)
        (label-vm vm '(etiquette1))
        (jsr-vm vm 'etiquette1)
        (if (not (= (get-etat-element-memoire-vm vm 201) 101))
            (return-from test-jsr-vm nil)) ; Échec du test
        (if (not (= (get-register-value vm 'PC) 25))
            (return-from test-jsr-vm nil)) ; Échec du test
        (format t "   Valeur memoire a l'adresse 201 : ~A~%" (get-etat-element-memoire-vm vm 201))
        (format t "   Valeur du registre PC apres le saut : ~A~%" (get-register-value vm 'PC))
    
    (format t "### Tests reussis pour la fonction jsr-vm ###~%~%")
    t)) ; La fonction renvoie t si le test reussit, nil sinon
;; ######################################################### ;;

;; ########### Fonction de test pour RTN ###########
(defun test-rtn-vm ()
    (format t "### Test de la fonction rtn-vm ###~%")
    ;; Creation de la machine virtuelle
    (let ((vm (create-vm-instance 600)))

        ;; Initialisation des registres
        (set-register-value vm 'SP 200)

        ;; Initialisation de la pile avec une adresse de retour
        (push-vm vm 150) ; Adresse de retour de la sous-routine

        ;; Test: Retour d'une sous-routine
        (format t "- Retour d'une sous-routine~%")
        (rtn-vm vm)
        
        ;; Verification de la nouvelle valeur de PC apres le retour
        (if (not (= (get-register-value vm 'PC) 150))
            (return-from test-rtn-vm nil)) ; Échec du test
        (format t "   Valeur du registre PC apres le retour : ~A~%" (get-register-value vm 'PC))

        ;; Verification que la pile a ete correctement decrementee
        (if (not (= (get-register-value vm 'SP) 200))
            (return-from test-rtn-vm nil)) ; Échec du test
        (format t "   Valeur du registre SP apres le retour : ~A~%" (get-register-value vm 'SP))

    (format t "### Tests reussis pour la fonction rtn-vm ###~%~%")
    t)) ; La fonction renvoie t si le test reussit, nil sinon
;; ######################################################### ;;

;; ########### Fonction de test pour CMP ###########
(defun test-cmp-vm ()
    (format t "### Test de la fonction cmp-vm ###~%")
    ;; Creation de la machine virtuelle
    (let ((vm (create-vm-instance 600)))

        ;; Initialisation des registres
        (set-register-value vm 'R0 10)
        (set-register-value vm 'R1 5)
        (set-register-value vm 'R2 10)

        ;; Test 1: src1 < src2
        (format t "- src1 < src2~%")
        (cmp-vm vm 'R1 'R0)
        (if (not (get-flag-value vm 'FLT))
            (return-from test-cmp-vm nil)) ; Échec du test
        (format t "   Valeur du drapeau FLT : ~A~%" (get-flag-value vm 'FLT))

        ;; Test 2: src1 = src2
        (format t "- src1 = src2~%")
        (cmp-vm vm 'R0 'R2)
        (if (not (get-flag-value vm 'FEQ))
            (return-from test-cmp-vm nil)) ; Échec du test
        (format t "   Valeur du drapeau FEQ : ~A~%" (get-flag-value vm 'FEQ))

        ;; Test 3: src1 > src2
        (format t "- src1 > src2~%")
        (cmp-vm vm 'R2 'R1)
        (if (not (get-flag-value vm 'FGT))
            (return-from test-cmp-vm nil)) ; Échec du test
        (format t "   Valeur du drapeau FGT : ~A~%" (get-flag-value vm 'FGT))

    (format t "### Tests reussis pour la fonction cmp-vm ###~%~%")
    t)) ; La fonction renvoie t si le test reussit, nil sinon
;; ######################################################### ;;

;; ########### Fonction de test pour TEST ###########
(defun test-test-vm ()
    (format t "### Test de la fonction test-vm ###~%")
    ;; Creation de la machine virtuelle
    (let ((vm (create-vm-instance 600)))

        ;; Test 1: src est NIL
        (format t "Si src est NIL~%")
        (test-vm vm 'R2)
        (if (not (get-flag-value vm 'FNIL))
            (return-from test-test-vm nil)) ; Échec du test
        (format t "   Valeur du drapeau FNIL : ~A~%" (get-flag-value vm 'FNIL))

        ;; Test 2: src n'est pas NIL
        (format t "Si src n'est pas NIL~%")
        (set-register-value vm 'R0 10)

        (test-vm vm 'R0)
        (if (get-flag-value vm 'FNIL)
            (return-from test-test-vm nil)) ; Échec du test
        (format t "   Valeur du drapeau FNIL : ~A~%" (get-flag-value vm 'FNIL))

    (format t "### Tests reussis pour la fonction test-vm ###~%~%")
    t)) ; La fonction renvoie t si le test reussit, nil sinon
;; ######################################################### ;;

;; ########### Fonction de test pour NOP ###########
(defun test-nop-vm ()
    (format t "### Test de la fonction nop-vm ###~%")
    ;; Creation de la machine virtuelle
    (let ((vm (create-vm-instance 600)))
        (format t "### Tests reussis pour la fonction nop-vm ###~%~%")
    t)) ; La fonction renvoie t si le test reussit, nil sinon
;; ######################################################### ;;

;; ########### Fonction de test pour HALT ###########
(defun test-halt-vm ()
    (format t "### Test de la fonction halt-vm ###~%")
    ;; Creation de la machine virtuelle
    (let ((vm (create-vm-instance 600)))

        ;; Fait un halt-vm
        (format t "Appel de halt~%")
        (halt-vm vm)
        (if (not (= (get-register-value vm 'PC) -1))
            (return-from test-test-vm nil)) ; Échec du test
        (format t "   Valeur du registre PC : ~A~%" (get-register-value vm 'PC))


        (format t "### Tests reussis pour la fonction halt-vm ###~%~%")
    t)) ; La fonction renvoie t si le test reussit, nil sinon
;; ######################################################### ;;

;; ########### Fonction de test pour vm-apply ###########
(defun test-apply-vm ()
    (format t "### Test de la fonction vm-apply ###~%")
    ;; Creation de la machine virtuelle
    (let ((vm (create-vm-instance 600)))
    
        (format t "   Valeur du registre FP : ~A~%" (get-register-value vm 'SP))

        ;; Test 1: Appliquer une fonction avec 2 arguments
        (format t "   Appliquer une fonction avec 2 arguments~%")
        ;; Ajouter les arguments sur la pile
        (push-vm vm 5)
        (push-vm vm 7)

        (move-vm vm 'SP 'FP)
        (format t "   Valeur du registre FP : ~A~%" (get-register-value vm 'SP))
        ;; Definir une fonction qui additionne deux nombres
        (defun additionner (a b) (+ a b))
        ;; Appliquer la fonction avec les arguments sur la pile
        (apply-vm vm #'additionner 2)
        ;; Verifier le resultat dans le registre R0
        (if (not (= (get-register-value vm 'R0) 12))
            (return-from test-vm-apply nil)) ; Échec du test
        (format t "   Resultat de l'addition dans le registre R0 : ~A~%" (get-register-value vm 'R0))

    (format t "### Tests reussis pour la fonction vm-apply ###~%~%")
    t)) ; La fonction renvoie t si le test reussit, nil sinon
;; ######################################################### ;;

;; ########### Fonction de test pour vm-chargeur ###########
(defun test-chargeur-asm-vm ()
    (format t "### Test du chargeur de la machine virtuelle ###~%")
    ;; Creation de la machine virtuelle
    (let ((vm (create-vm-instance 600)))
    
        ;; Test 1: Programme simple avec des instructions de base
        (format t "- Programme simple avec des instructions de base~%")
        (let ((programme1 '((ADD R1 R2))))
        (chargeur-asm-vm vm programme1)
        (if (not (= (get-register-value vm 'PCc) 2))
            (return-from test-chargeur-asm-vm nil)) ; Échec du test
        (format t "   Succes du test :~%"))
        ; Affiche le 1 premier emplacement de ma memoire
        (format t "   Memoire : ~A~%" (subseq (get-memoire-vm vm) 0 2))
        
        ;; Test 2: Programme avec des sauts et des labels
        (format t "- Programme avec des sauts et des labels~%")
        (let ((programme2 '((LABEL debut)
                            (ADD R1 R2)
                            (JMP debut))))
        (chargeur-asm-vm vm programme2)
        (if (not (= (get-register-value vm 'PCc) 6))
            (return-from test-chargeur-asm-vm nil)) ; Échec du test
        (format t "   Succes du test :~%"))
        ; Affiche les 1 premiere emplacement de ma memoire6
        (format t "   Memoire : ~A~%" (subseq (get-memoire-vm vm) 2 6))

    (format t "### Tests reussis pour le chargeur de la machine virtuelle ###~%~%")
    t)) ; La fonction renvoie t si le test reussit, nil sinon
; ######################################################### ;;

;; ########### Tests pour la fonction execute-vm ###########
(defun test-execute-vm ()
    (format t "### Test de la fonction execute-vm ###~%")
    ;; Creation de la machine virtuelle
    (let ((vm (create-vm-instance 600)))

        ;; Test 1: Execution d'un programme simple
        (format t "- Execution d'un programme simple~%")
        (let ((programme-simple '((STORE (:LIT . 42) (:LIT . 5)) ; Stocke la valeur 42 a l'adresse 5
                                (LOAD (:LIT . 5) R0) ; Charge la valeur a l'adresse 5 dans R0
                                (HALT))))
        (execute-vm vm programme-simple)
        ;; Verifiez ici les resultats en fonction de la logique de votre programme simple
        (if (not (= (get-register-value vm 'R0) 42))
            (return-from test-execute-vm nil)) ; Échec du test
        (format t "   Succes du test~%"))

        (reset-vm vm 600) ; Reinitialise la machine virtuelle pour le prochain test

        ;; Test 2: Execution d'un programme avec des sauts conditionnels
        (format t "- Execution d'un programme avec des sauts conditionnels~%")
        (let ((programme-sauts '((STORE (:LIT . 888) (:LIT . 20)) ; Stocke la valeur 888 a l'adresse 20
                                (STORE (:LIT . 999) (:LIT . 21)) ; Stocke la valeur a l'adresse 20 a l'adresse 10
                                (LOAD (:LIT . 20) R0) ; Charge la valeur 20 dans R0
                                (LOAD (:LIT . 21) R1) ; Charge la valeur 10 dans R1
                                (CMP R0 R1) ; Compare R0 et R1
                                (JGE LABEL1) ; Saut a :LABEL1 si R0 >= R1
                                (STORE 1 (:LIT . 100)) ; Charge la valeur dans R0
                                (LOAD (:LIT . 100) R0) ; Charge la valeur 1 a l'adresse 100
                                (HALT)
                                (LABEL LABEL1) ; Label :LABEL1
                                (STORE 0 (:LIT . 100)) ; Charge la valeur 0 a l'adresse 100
                                (LOAD (:LIT . 100) R0) ; Charge la valeur dans R0
                                (HALT))))
        (execute-vm vm programme-sauts)
        ;; Verifiez ici les resultats en fonction de la logique de votre programme avec des sauts conditionnels
        (if (not (= (get-register-value vm 'R0) 1))
            (return-from test-execute-vm nil)) ; Échec du test
        (format t "   Succes du test~%"))

    ;(format t "### Tests reussis pour la fonction execute-vm ###~%~%")
    t)) ; La fonction renvoie t si le test reussit, nil sinon
;; ######################################################### ;;


;; ~~~~~~~~~~~~~~~~~~~~~~ Appel des fonctions test ~~~~~~~~~~~~~~~~~~~~~~ ;;
(defun run-all-tests ()
    (format t "~~~~~~~~~~~~~~~~~~~~~~ Lancement des tests ~~~~~~~~~~~~~~~~~~~~~~~%")
    (let ((total-tests 0)
        (passed-tests 0)
        (failed-tests '()))

    ; Fonction pour executer un test et enregistrer le resultat
    (defun run-test (test-function)
        (incf total-tests)
        (if (funcall test-function)
            (incf passed-tests)
            (push test-function failed-tests)))

    ; Executer tous les tests
    (run-test 'test-getters-setters-vm)
    (run-test 'test-label-vm)
    (run-test 'test-add-vm)
    (run-test 'test-sub-vm)
    (run-test 'test-mul-vm)
    (run-test 'test-div-vm)
    (run-test 'test-incr-vm)
    (run-test 'test-decr-vm)
    (run-test 'test-store-vm)
    (run-test 'test-load-vm)
    (run-test 'test-move-vm)
    (run-test 'test-push-vm)
    (run-test 'test-pop-vm)
    (run-test 'test-jump-vm)
    (run-test 'test-jsr-vm)
    (run-test 'test-rtn-vm)
    (run-test 'test-cmp-vm)
    (run-test 'test-test-vm)
    (run-test 'test-nop-vm)
    (run-test 'test-halt-vm)
    (run-test 'test-apply-vm)
    (run-test 'test-chargeur-asm-vm)
    (run-test 'test-execute-vm)

    (format t "~~~~~~~~~~~~~~~~~~~~~~ Fin des tests ~~~~~~~~~~~~~~~~~~~~~~~%~%")

    ; Afficher le resultat global
    (format t "Tests passes ~A/~A~%" passed-tests total-tests)

    ; Afficher les tests echoues
    (when failed-tests
        (format t "Tests echoues : ~A~%" failed-tests))

    ; Renvoyer vrai si tous les tests ont reussi, faux sinon
    (= passed-tests total-tests)))
;; ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ;;

; Executer tous les tests et afficher le resultat
(run-all-tests)

