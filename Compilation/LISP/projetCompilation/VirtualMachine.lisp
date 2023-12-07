;; ########### Initialisation des REGISTRES et DRAPEAUX ########### ;
; Définition des registres et des drapeaux
(setq resgistres '(R0 R1 R2 BP SP PC FP))
(setq drapeaux '(FLT FEQ FGT FNIL))
;; ################################################## ;;


;; ########### Getter et Setter REGISTRES ########### ;;
; Vérifie si un registre donné 'reg' est présent dans la liste des registres 
(defun check-register-presence (reg)
    (position reg resgistres))

; Accéder à la valeur du registre
(defun get-register-value (vm reg)
    (if (check-register-presence reg)
        (get vm reg)))

; Modifie la valeur du registre
(defun set-register-value (vm reg val)
    (if (check-register-presence reg)
        (setf (get vm reg) val)))
;; ################################################## ;;


;; ########### Getter et Setter DRAPEAUX ########### ;;
; Vérifie si un drapeau donné 'flag' est présent dans la liste des drapeaux
(defun check-flag-presence (flag)
    (position flag drapeaux))

; Accède à la valeur du drapeau dans la machine virtuelle
(defun get-flag-value (vm flag)
    (if (check-flag-presence flag)
        (get vm flag)))

; Active le drapeau dans la machine virtuelle
(defun set-flag-on (vm flag)
    (if (check-flag-presence flag)
        (setf (get vm flag) T)))

; Désactive le drapeau dans la machine virtuelle
(defun set-flag-off (vm flag)
    (if (check-flag-presence flag)
        (setf (get vm flag) nil)))
;; ################################################## ;;

;; ########### Fonction de la MACHINE VIRTUELLE ########### ;;
; Obtenir la mémoire de la machine virtuelle
(defun get-memoire-vm (vm)
    (get vm :memory))

; Obtenir la taille de la mémoire de la machine virtuelle
(defun get-taille-memoire-vm (vm)
    (get vm :memory_size))

; Définir la mémoire de la machine virtuelle
(defun set-memoire-vm (vm taille)
    (setf (get vm :memory) (make-array taille :initial-element nil))
    (setf (get vm :memory_size) taille))

; État de la mémoire de la machine virtuelle
(defun get-etat-memoire-vm (vm)
    (list 'memory
        (get-memoire-vm vm)))

; Élément de la mémoire de la machine virtuelle à l'indice i
(defun get-etat-element-memoire-vm (vm indice)
    (aref (get-memoire-vm vm) indice))

;; --- Partie des TABLES DE HACHAGE --- ;;
; Initialiser la table de hachage pour les étiquettes résolues
(defun init-hashTab-etq-resolu (vm)
    (setf (get vm :hashTab_etq_resolu) (make-hash-table :test 'equal)))

; Obtenir la table de hachage pour les étiquettes résolues
(defun get-hashTab-etq-resolu (vm)
    (get vm :hashTab_etq_resolu))

; Obtenir la valeur associée à une étiquette résolue dans la table de hachage
(defun get-hashTab-etq-resolu-val (vm etq)
    (gethash etq (get-hashTab-etq-resolu vm)))

; Définir une valeur associée à une étiquette résolue dans la table de hachage
(defun set-hashTab-etq-resolu (vm etq valeur)
    (setf (gethash etq (get-hashTab-etq-resolu vm)) valeur))

; État de la table de hachage pour les étiquettes résolues
(defun state-hashTab-etq-resolu (vm)
    (list 'hashTab_etq (get-hashTab-etq-resolu vm)))

; Initialiser la table de hachage pour les étiquettes non résolues
(defun init-hashTab-etq-non-resolu (vm)
    (setf (get vm :hashTab_etq_non_resolu) (make-hash-table)))

; Obtenir la table de hachage pour les étiquettes non résolues
(defun get-hashTab-etq-non-resolu (vm)
    (get vm :hashTab_etq_non_resolu))

; Obtenir la valeur associée à une étiquette non résolue dans la table de hachage
(defun get-hashTab-etq-non-resolu-val (vm etq)
    (gethash etq (get-hashTab-etq-non-resolu vm)))

; Définir une valeur associée à une étiquette non résolue dans la table de hachage
(defun set-hashTab-etq-non-resolu (vm etq valeur)
    (setf (gethash etq (get-hashTab-etq-non-resolu vm)) valeur))

; État de la table de hachage pour les étiquettes non résolues
(defun state-hashTab-etq-non-resolu (vm)
    (list 'hashTab_etq (get-hashTab-etq-non-resolu vm)))
;; ################################################## ;;


;; ########### Initialisation de la MACHINE VIRTUELLE ########### ;;
(defun vm_init (vm size)
    (set-memoire-vm vm size) ; Initialise la mémoire
    (set-register-value vm 'R0 0) ; Initialise le registre R0
    (set-register-value vm 'R1 0) ; Initialise le registre R1
    (set-register-value vm 'R2 0) ; Initialise le registre R2
    (set-register-value vm 'BP (floor (/ size 10))) ; Initialise le registre du Base Pointer
    (set-register-value vm 'SP (floor (/ size 10))) ; Initialise le registre du Stack Pointer
    (set-register-value vm 'FP (floor (/ size 10))) ; Initialise le registre du Frame Pointer
    (set-register-value vm 'PC 0) ; Initialise le registre du Program Counter
    (set-flag-off vm 'FLT) ; Initialise le drapeau FLT
    (set-flag-off vm 'FEQ) ; Initialise le drapeau FEQ
    (set-flag-off vm 'FGT) ; Initialise le drapeau FGT
    (set-flag-off vm 'FNIL) ; Initialise le drapeau FNIL
    (init-hashTab-etq-resolu vm) ; Initialise la table des références résolues
    (init-hashTab-etq-non-resolu vm)) ; Initialise la table des références non résolues
(trace vm_init)
;; ################################################## ;;