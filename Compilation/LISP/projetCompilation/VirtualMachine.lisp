;; ########### Initialisation des REGISTRES et DRAPEAUX ########### ;
; Définition des registres et des drapeaux
(setq resgistres '(R0 R1 R2 BP SP PC PCO FP))
(setq drapeaux '(FLT FEQ FGT FNIL))
;; ################################################## ;;


;; ########### Getter et Setter REGISTRES ########### ;;
; Vérifie si un registre donné 'reg' est présent dans la liste des registres 
(defun check-register-presence (reg)
    (member reg resgistres))

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
    (list 'hashTab-etq (get-hashTab-etq-non-resolu vm)))

;; --- Affichage de la machine virtuelle --- ;;
; Obtenir l'état de la machine virtuelle (registres, drapeaux, mémoire, tables de hachage)
(defun etat-vm (vm)
    (progn (print "#### Machine Virtuelle ####")
        (print "- Registres généraux")
        (print (list 'R0 (get-register-value vm 'R0)))
        (print (list 'R1 (get-register-value vm 'R1)))
        (print (list 'R2 (get-register-value vm 'R2)))
        (print "- Registres spéciaux")
        (print (list 'SP (get-register-value vm 'SP)))
        (print (list 'BP (get-register-value vm 'BP)))
        (print (list 'FP (get-register-value vm 'FP)))
        (print (list 'PC (get-register-value vm 'PC)))
        (print (list 'PCO (get-register-value vm 'PCO)))
        (print "- Drapeaux :")
        (print (list 'FLT (get-flag-value vm 'FLT)))
        (print (list 'FEQ (get-flag-value vm 'FEQ)))
        (print (list 'FGT (get-flag-value vm 'FGT)))
        (print (list 'FNIL (get-flag-value vm 'FNIL)))
        (print "- Mémoire : ")
        (print (list 'memory (get-etat-memoire-vm vm)))
        (print "- Table de hachage pour les étiquettes : ")
        (print (list 'hashTab_etq (state-hashTab-etq-resolu vm)))
        (print (list 'hashTab_etq (state-hashTab-etq-non-resolu vm)))))
(trace etat-vm)

;; ################################################## ;;


;; ########### Initialisation de la MACHINE VIRTUELLE ########### ;;
;; Initialise la machine virtuelle avec une mémoire de taille 'size' 
;; et initialise les registres et les drapeaux à 0 
;; et initialise les tables de hachage pour les étiquettes résolues et non résolues
(defun init-vm (vm size)
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
(trace init-vm)
;; ################################################## ;;



;; ########### Instruction de la MACHINE VIRTUELLE ########### ;;
;; Instruction de la machine virtuelle qui permettent de 
;; manipuler les registres et les drapeaux de la machine virtuelle

;; --- Instructions de manipulation des REGISTRES --- ;;
; # LOAD : Charge une valeur depuis une source vers une destination dans la machine virtuelle
(defun load-vm (vm src dest)
    ; Vérifie si dest est un registre
    (if (not (check-register-presence dest))
        (warn "❌ LOAD : dest '~s' n'est pas un registre !" dest)
        ; Vérifie si src n'est pas une valeur atomique (peut être une expression)
        (if (not (atom src))
            ; Si src est une expression, calcule l'adresse à partir de la base et du décalage
            (let* (
                (base (if (eq (first src) ':REF) ; Si la source est une référence (étiquette)
                            (get-register-value vm (second src))
                            src)) ; Si la base est un registre, utilise sa valeur, sinon utilise la base directe
                (offset (if (integerp (third src))
                                (third src)
                                (error "❌ LOAD : Le décalage doit être un entier relatif"))))
                ; Calcule l'adresse totale en ajoutant la base et le décalage
                (let ((address (+ base offset)))
                    ; Vérifie si l'adresse est dans les limites de la mémoire
                    (if (or (< (- (get-taille-memoire-vm vm) 1) address) (> 0 address))
                        (warn "❌ LOAD : l'adresse mémoire @~s est hors limite [~s , ~s]" address 0 (- (get-taille-memoire-vm vm) 1))
                        ; Charge la valeur à l'adresse calculée dans le registre de destination
                        (set-register-value vm dest (svref (get-memoire-vm vm) address)))))
        ; Si src est une valeur atomique (entier ou registre), charge la valeur correspondante
        (if (not (or (integerp src) (check-register-presence src)))
            (warn "❌ LOAD : src '~s' n'est ni registre, ni adresse mémoire (int) !" src)
            (if (check-register-presence src) ; Si src est un registre
                (if (or (< (- (get-taille-memoire-vm vm) 1) (get-register-value vm src)) (> 0 (get-register-value vm src)))
                    ; Si src est un registre et si son adresse mémoire est dans les limites
                    (warn "❌ LOAD : l'adresse mémoire @~s est hors limite [~s , ~s]" (get-register-value vm src) 0 (- (get-taille-memoire-vm vm) 1))
                    ; Charge la valeur à l'adresse du registre src dans le registre de destination
                    (set-register-value vm dest (svref (get-memoire-vm vm) (get-register-value vm src))))
                (if (or (< (- (get-taille-memoire-vm vm) 1) src) (> 0 src)) ; Si src est une adresse mémoire directe
                    (warn "❌ LOAD : l'adresse mémoire @~s est hors limite [~s , ~s]" src 0 (- (get-taille-memoire-vm vm) 1))
                    ; Charge la valeur à l'adresse directe src dans le registre de destination
                    (set-register-value vm dest (svref (get-memoire-vm vm) src))))))))

; # STORE : Stocke une valeur depuis une source vers une destination dans la machine virtuelle
(defun store-vm (vm src dest)
    ; Vérifie si src est un registre
    (if (not (check-register-presence src))
        (warn "❌ STORE : src '~s' n'est pas un registre !" src)
        ; Vérifie si dest n'est pas une valeur atomique (peut être une expression)
        (if (not (or (integerp dest) (eq (car dest) ':REF)))
            (warn "❌ STORE : dest '~s' n'est ni un registre, ni une adresse mémoire (int) ou une étiquette (:REF) !" dest)
            ; Si la destination est un registre, stocke la valeur du registre src à l'adresse mémoire spécifiée
            (if (eq (car dest) ':REF)
                (setf (aref (get-memoire-vm vm) (get-register-value vm (cadr dest))) (get-register-value vm src))
                ; Si la destination est une adresse mémoire directe et si elle est dans les limites
                (if (or (< (- (get-taille-memoire-vm vm) 1) dest) (> -1 dest))
                    ; Si l'adresse est hors limite, affiche un avertissement
                    (warn "❌ STORE : l'adresse mémoire @~s est hors limite [~s , ~s]" dest 0 (- (get-taille-memoire-vm vm) 1))
                    ; Sinon, stocke la valeur du registre src à l'adresse mémoire spécifiée
                    (setf (aref (get-memoire-vm vm) dest) (get-register-value vm src)))))))

; # MOVE :  Déplacer une valeur d'un emplacement source vers un emplacement destination dans la machine virtuelle
(defun move-vm (vm src dest)
    ; Vérifie si dest est un registre
    (if (not (check-register-presence dest))
        (warn "❌ MOVE : dest '~s' n'est pas un registre !" dest)
        (if (check-register-presence src)
            ; Si src est un registre, charge la valeur du registre src dans le registre dest
            (set-register-value vm dest (get-register-value vm src))
            ; Si src est une constante (:CONST) ou une étiquette (:REF)
            (if (or (integerp src) (eq (car src) ':CONST))
                ; Si src est une constante, charge la constante dans le registre dest
                (set-register-value vm dest (if (integerp src) src (cadr src)))
                (if (eq (car src) ':REF)
                    ; Si src est une étiquette, charge la valeur de l'étiquette dans le registre dest
                    (set-register-value vm dest (aref (get-memoire-vm vm) (get-register-value vm (cadr src))))
                    ; Si src n'est ni un registre, ni une constante, ni une étiquette, affiche un avertissement
                    (warn "❌ MOVE : <src> doit être un registre, une constante (:CONST) ou une étiquette (:REF)"))))))

;; -------------------------------------------------- ;;

;; --- Instruction d'opérations arithmétiques sur les REGISTRES --- ;;
; # ADD : Addition de registres dans la machine virtuelle
(defun add-vm (vm src dest)
    (cond
        ; Vérifie si dest est un registre
        ((not (check-register-presence dest))
        (warn "❌ ADD : dest '~s' n'est pas un registre !" dest))
        ; Si src est une valeur atomique (entier), ajoute la valeur à celle du registre dest
        ((atom src)
        (if (not (check-register-presence src))
            ; Vérifie si src est un registre
            (if (not (integerp src))
                (warn "❌ ADD : src n'est ni un registre ni un entier !")
                ; Si src est un entier, ajoute la valeur du registre dest avec l'entier src
                (move-vm vm (+ (get-register-value vm dest) src) dest))
            ; Si src est un registre, ajoute la valeur du registre src à celle du registre dest
            (move-vm vm (+ (get-register-value vm src) (get-register-value vm dest)) dest)))
        ; Si src est une expression
        ((not (atom src))
        (if (not (and (eq (car src) ':LIT) (integerp (cdr src))))
            ; Si src n'est pas une expression de la forme <:LIT entier>, affiche un avertissement
            (warn "❌ ADD : src n'est pas une paire de la forme <:LIT entier>")
            ; Sinon ajoute la valeur entière n à celle du registre dest
            (move-vm vm (+ (cdr src) (get-register-value vm dest)) dest)))))

; # SUB : Soustraction de registres dans la machine virtuelle
(defun sub-vm (vm src dest)
    (cond
        ; Vérifie si dest est un registre
        ((not (check-register-presence dest))
        (warn "❌ SUB : dest '~s' n'est pas un registre !" dest))
        ; Si src est une valeur atomique (entier), soustrait la valeur à celle du registre dest
        ((atom src)
        (if (not (check-register-presence src))
            ; Vérifie si src est un registre
            (if (not (integerp src))
                (warn "❌ SUB : src n'est ni un registre ni un entier !")
                ; Si src est un entier, soustrait la valeur du registre dest avec l'entier src
                (move-vm vm (- (get-register-value vm dest) src) dest))
            ; Si src est un registre, soustrait la valeur du registre src à celle du registre dest
            (move-vm vm (- (get-register-value vm dest) (get-register-value vm src)) dest)))
        ; Si src est une expression
        ((not (atom src))
        (if (not (and (eq (car src) ':LIT) (integerp (cdr src))))
            ; Si src n'est pas une expression de la forme <:LIT entier>, affiche un avertissement
            (warn "❌ SUB : src n'est pas une paire de la forme <:LIT entier>")
            ; Sinon soustrait la valeur entière n à celle du registre dest
            (move-vm vm (- (get-register-value vm dest) (cdr src)) dest)))))

; # MUL : Multiplication de registres dans la machine virtuelle
(defun mul-vm (vm src dest)
    (cond
        ; Vérifie si dest est un registre
        ((not (check-register-presence dest))
        (warn "❌ MUL : dest '~s' n'est pas un registre !" dest))
        ; Si src est une valeur atomique (entier), multiplie la valeur à celle du registre dest
        ((atom src)
        (if (not (check-register-presence src))
            ; Vérifie si src est un registre
            (if (not (integerp src))
                (warn "❌ MUL : src n'est ni un registre ni un entier !")
                ; Si src est un entier, multiplie la valeur du registre dest avec l'entier src
                (move-vm vm (* (get-register-value vm dest) src) dest))
            ; Si src est un registre, multiplie la valeur du registre src à celle du registre dest
            (move-vm vm (* (get-register-value vm src) (get-register-value vm dest)) dest)))
        ; Si src est une expression
        ((not (atom src))
        (if (not (and (eq (car src) ':LIT) (integerp (cdr src))))
            ; Si src n'est pas une expression de la forme <:LIT entier>, affiche un avertissement
            (warn "❌ MUL : src n'est pas une paire de la forme <:LIT entier>")
            ; Sinon multiplie la valeur entière n à celle du registre dest
            (move-vm vm (* (cdr src) (get-register-value vm dest)) dest)))))


; # DIV : Division de registres dans la machine virtuelle
(defun div-vm (vm src dest)
    (cond
        ; Vérifie si dest est un registre
        ((not (check-register-presence dest))
        (warn "❌ DIV : dest '~s' n'est pas un registre !" dest))
        ; Si src est une valeur atomique (entier), divise la valeur à celle du registre dest
        ((atom src)
        (if (not (check-register-presence src))
            ; Vérifie si src est un registre
            (if (not (integerp src))
                (warn "❌ DIV : src n'est ni un registre ni un entier !")
                ; Si src est un entier, divise la valeur du registre dest avec l'entier src
                (move-vm vm (/ (get-register-value vm dest) src) dest))
            ; Si src est un registre, divise la valeur du registre dest par la valeur du registre src
            (move-vm vm (/ (get-register-value vm dest) (get-register-value vm src)) dest)))
        ; Si src est une expression
        ((not (atom src))
        (if (not (and (eq (car src) ':LIT) (integerp (cdr src))))
            ; Si src n'est pas une expression de la forme <:LIT entier>, affiche un avertissement
            (warn "❌ DIV : src n'est pas une paire de la forme <:LIT entier>")
            ; Sinon divise la valeur entière n à celle du registre dest
            (move-vm vm (/ (get-register-value vm dest) (cdr src)) dest)))))


; # INCR : Incrémente la valeur d'un registre dans la machine virtuelle
(defun incr-vm (vm dest)
    (cond
        ; Vérifie si dest est un registre
        ((not (check-register-presence dest))
        (warn "❌ INCR : dest '~s' n'est pas un registre !" dest))
        ; Si dest est un registre, incrémente la valeur du registre dest
        (t
        (if (>= (get-register-value vm dest) (get-register-value vm 'BP)) ; Vérifie si dest est dans les limites
            (warn "❌ INCR : ~s est hors limite [~s , ~s]" dest (get-register-value vm 'BP) (get-register-value vm dest))
            (set-register-value vm dest (+ (get-register-value vm dest) 1))))))

; # DECR : Décrémente la valeur d'un registre dans la machine virtuelle
(defun decr-vm (vm dest)
    (cond
        ; Vérifie si dest est un registre
        ((not (check-register-presence dest))
        (warn "❌ DECR : dest '~s' n'est pas un registre !" dest))
        ; Si dest est un registre, décrémente la valeur du registre dest
        (t
        (if (<= (get-register-value vm dest) 0) ; Vérifie si dest est dans les limites
            (warn "❌ DECR : ~s est hors limite [~s , ~s]" dest 0 (get-register-value vm dest))
            (set-register-value vm dest (- (get-register-value vm dest) 1))))))

;; -------------------------------------------------- ;;

;; --- Inscruction d'empilement et de dépilement de la PILE --- ;;
; # PUSH : Empile une valeur dans la pile de la machine virtuelle
(defun push-vm (vm src)
    (cond
        ; Si src est un registre
        ((check-register-presence src)
        (progn
        (incr-vm vm 'SP)          ; On incrémente SP
        (store-vm vm src 'SP)))   ; On écrit la valeur de src à la nouvelle adresse de SP
        ; Si src est une valeur entière
        ((integerp src)
        (progn
        (incr-vm vm 'SP)          ; On incrémente SP
        (move-vm vm src 'R0)      ; On déplace la valeur de src dans R0
        (store-vm vm 'R0 'SP)))   ; On écrit la valeur de R0 à la nouvelle adresse de SP
        ; Si src n'est ni un registre ni une valeur entière, affiche un avertissement
        (t (warn "❌ PUSH : src '~s' n'est ni un registre ni une valeur entière" src))))


; # POP : Dépile une valeur de la pile de la machine virtuelle
(defun pop-vm (vm dest)
    (cond
        ; Si dest est un registre
        ((check-register-presence dest)
        (progn
        (load vm 'SP dest)   ; On charge la valeur à l'adresse de SP dans dest
        (decr-vm vm 'SP)))   ; On décrémente SP
        ; Si dest n'est pas un registre, affiche un avertissement
        (t (warn "❌ POP : dest '~s' n'est pas un registre" dest))))

;; -------------------------------------------------- ;;

;; --- Instructions d'étiqutage et de saut --- ;;
; # LABEL : Définit une étiquette dans la machine virtuelle
(defun label-vm (vm etq)
    ; Vérifie si etq est un symbole (nom de l'étiquette)
    (if (symbolp etq)
        (progn
            ; Utilise le nom de l'étiquette comme clé pour la table de hachage
            (unless (get-hashtab-etq-resolu-val vm etq)
            ; Définit l'adresse de l'étiquette dans le registre PCO
            (set-hashtab-etq-resolu vm etq (get-register-value vm 'PCO)))
            ; Ajoute d'autres étapes si nécessaire
        )
        (warn "❌ LABEL : etq '~s' n'est pas un symbole (nom de l'étiquette) !" etq)))


; # JMP : Saut inconditionnel vers une étiquette ou une adresse dans la machine virtuelle
(defun jump-vm (vm label)
    (if (integerp label)
        (move-vm vm label 'PC)   ; Si label est une adresse, on déplace la valeur de label dans PC
        (when (get-hashtab-etq-resolu-val vm label)
            (move-vm vm (get-hashtab-etq-resolu-val vm label) 'PC)   ; Si label est une étiquette, on déplace la valeur de label dans PC
            (set-hashtab-etq-non-resolu vm label (get-register-value vm 'SP)))))   ; On ajoute l'adresse de l'instruction JMP dans la table des étiquettes non résolues 
    ; pour conserver l'adresse actuelle de la pile avant de sauter à une autre partie du programme.

    
; # JSR : Saut vers une sous-routine dans la machine virtuelle
(defun jsr-vm (vm label)
    (push-vm vm (+ (get-register-value vm 'PC) 1)) ; On pousse l'adresse courante sur la pile
    (jump-vm vm label)) ; On effectue le saut

; # RTN : Retour d'une sous-routine dans la machine virtuelle
(defun rtn-vm (vm)
    (load vm 'SP 'R0) ; On charge le contenu du sommet de pile dans R0
    (decr-vm vm 'SP) ; On décrémente SP
    (jump-vm vm (get-register-value vm 'R0))) ; On saute à l'adresse contenue dans R0
;; -------------------------------------------------- ;;

;; --- Instructions de comparaison --- ;;
; # CMP : Compare deux registres dans la machine virtuelle
(defun cmp-vm (vm src1 src2)
    (cond
        ((check-register-presence src1)
            (setf src1 (get-register-value vm src1))) ; Si src1 est un registre, on récupère sa valeur
        ((and (not (atom src1)) (eq (car src1) ':LIT) (integerp (cdr src1)))
            (setf src1 (cdr src1)))) ; Si src1 est une expression <:LIT entier>, on récupère la valeur entière
    (cond
        ((check-register-presence src2)
            (setf src2 (get-register-value vm src2))) ; Si src2 est un registre, on récupère sa valeur
        ((and (not (atom src2)) (eq (car src1) ':LIT) (integerp (cdr src2)))
            (setf src2 (cdr src2)))) ; Si src2 est une expression <:LIT entier>, on récupère la valeur entière
    (cond
        ((< src1 src2) ; Si src1 < src2
            (set-flag-on vm 'FLT) ; On active le drapeau FLT
            (set-flag-off vm 'FEQ)
            (set-flag-off vm 'FGT))
        ((= src1 src2) ; Si src1 = src2
            (set-flag-off vm 'FLT)
            (set-flag-on vm 'FEQ) ; On active le drapeau FEQ
            (set-flag-off vm 'FGT))
        ((> src1 src2) ; Si src1 > src2
            (set-flag-off vm 'FLT)
            (set-flag-off vm 'FEQ)
            (set-flag-on vm 'FGT)))) ; On active le drapeau FGT

; # JGT : Saut si le drapeau FGT est activé 'Greater Than' (!FEQ !FLT FGT)
(defun jgt-vm (vm label)
    (if (and (not (get-flag-value vm 'FEQ)) (not (get-flag-value vm 'FLT)) (get-flag-value vm 'FGT))
        (jump-vm vm label)
        (incr-vm vm 'PC)))

; # JGE : Saut si le drapeau FGT ou FEQ est activé 'Greater or Equal' (FEQ !FLT FGT)
(defun jge-vm (vm label)
    (if (and (not (get-flag-value vm 'FLT))
    (or (get-flag-value vm 'FEQ) (get-flag-value vm 'FGT)))
        (jump-vm vm label)
        (incr-vm vm 'PC)))

; # JLT : Saut si le drapeau FLT est activé 'Lesser Than' (!FEQ !FGT FLT)
(defun jlt-vm (vm label)
    (if (and (not (get-flag-value vm 'FEQ)) (not (get-flag-value vm 'FGT)) (get-flag-value vm 'FLT))
        (jump-vm vm label)
        (incr-vm vm 'PC)))

; # JLE : Saut si le drapeau FLT ou FEQ est activé 'Lesser or Equal' (FEQ !FGT FLT)
(defun jle-vm (vm label)
    (if (and (not (get-flag-value vm 'FGT))
    (or (get-flag-value vm 'FEQ) (get-flag-value vm 'FLT)))
        (jump-vm vm label)
        (incr-vm vm 'PC)))

; # JEQ : Saut si le drapeau FEQ est activé 'Equal' (FEQ !FGT !FLT)
(defun jeq-vm (vm label)
    (if (and (not (get-flag-value vm 'FLT)) (not (get-flag-value vm 'FGT)) (get-flag-value vm 'FEQ))
        (jump-vm vm label)
        (incr-vm vm 'PC)))

; # JNE : Saut si le drapeau FEQ n'est pas activé 'Not Equal' (!FEQ FGT FLT)
(defun jne-vm (vm label)
    (if (not (get-flag-value vm 'FEQ))
        (jump-vm vm label)
        (incr-vm vm 'PC)))
;; -------------------------------------------------- ;;

;; --- Inscrutction de comparaison de NIL --- ;;
; # TEST : Teste si un registre est NIL dans la machine virtuelle
(defun test-vm (vm src)
    (cond
        ((check-register-presence src)
            (setf src (get-register-value vm src))) ; Si src est un registre, on récupère sa valeur
        ((and (not (atom src)) (eq (car src) ':LIT) (integerp (cdr src)))
            (setf src (cdr src)))) ; Si src est une expression <:LIT entier>, on récupère la valeur entière
    (if (eq src 0) ; Si src est NIL
        (set-flag-on vm 'FNIL) ; On active le drapeau FNIL
        (set-flag-off vm 'FNIL))) ; Sinon on désactive le drapeau FNIL

; # JTRUE : Saut si le drapeau FNIL est désactivé
(defun jtrue-vm (vm label)
    (if (not (get-flag-value vm 'FNIL))
        (jump-vm vm label)
        (incr-vm vm 'PC)))

; # JFALSE : Saut si le drapeau FNIL est activé
(defun jfalse-vm (vm label)
    (if (get-flag-value vm 'FNIL)
        (jump-vm vm label)
        (incr-vm vm 'PC)))
;; -------------------------------------------------- ;;

;; --- Instruction de fin --- ;;
; # NOP : Instruction vide
(defun nop-vm (vm)
    (incr-vm vm 'PC))

; # HALT : Arrête l'exécution de la machine virtuelle
(defun halt-vm (vm)
    (set-register-value vm 'PC -1))
;; -------------------------------------------------- ;;
;; ################################################## ;;



;; ########### Chargeur de la MACHINE VIRTUELLE ########### ;;
;; ######################################################## ;;


;; ########### Exectuer la MACHINE VIRTUELLE ########### ;;
;; --- Fonction d'exécution de la MACHINE VIRTUELLE --- ;;
;; Définition de la fonction exec-vm qui exécute la machine virtuelle
(defun vm-execute (vm)
    ; Tant que PC (Program Counter) est supérieur ou égal à BP (Base Pointer) (début de la pile)
    (loop while (<= (get-register-value vm 'BP) (get-register-value vm 'PC)) 
    do
        (let ((ist (get-etat-element-memoire-vm vm (get-register-value vm 'PC)))) ; On récupère l'instruction à l'adresse PC
        (cond ; On exécute l'instruction en fonction de son type
            ((equal (first ist) 'LOAD) (load-vm vm ist)) ; LOAD
            ((equal (first ist) 'STORE) (store-vm vm ist)) ; STORE
            ((equal (first ist) 'MOVE) (move-vm vm ist)) ; MOVE
            ((equal (first ist) 'ADD) (add-vm vm ist)) ; ADD
            ((equal (first ist) 'SUB) (sub-vm vm ist)) ; SUB
            ((equal (first ist) 'MUL) (mul-vm vm ist)) ; MUL
            ((equal (first ist) 'DIV) (div-vm vm ist)) ; DIV
            ((equal (first ist) 'INCR) (incr-vm vm ist)) ; INCR
            ((equal (first ist) 'DECR) (decr-vm vm ist)) ; DECR
            ((equal (first ist) 'PUSH) (push-vm vm ist)) ; PUSH
            ((equal (first ist) 'POP) (pop-vm vm ist)) ; POP
            ((equal (first ist) 'LABEL) (label-vm vm ist)) ; LABEL
            ((equal (first ist) 'JMP) (jump-vm vm ist)) ; JMP
            ((equal (first ist) 'JSR) (jsr-vm vm ist)) ; JSR
            ((equal (first ist) 'RTN) (rtn-vm vm)) ; RTN
            ((equal (first ist) 'CMP) (cmp-vm vm ist)) ; CMP
            ((equal (first ist) 'JGT) (jgt-vm vm ist)) ; JGT (Plus grand que)
            ((equal (first ist) 'JGE) (jge-vm vm ist)) ; JGE (Plus grand ou égal)
            ((equal (first ist) 'JLT) (jlt-vm vm ist)) ; JLT (Plus petit que)
            ((equal (first ist) 'JLE) (jle-vm vm ist)) ; JLE (Plus petit ou égal)
            ((equal (first ist) 'JEQ) (jeq-vm vm ist)) ; JEQ (Égal)
            ((equal (first ist) 'JNE) (jne-vm vm ist)) ; JNE (Différent)
            ((equal (first ist) 'TEST) (test-vm vm ist)) ; TEST
            ((equal (first ist) 'JTRUE) (jtrue-vm vm ist)) ; JTRUE
            ((equal (first ist) 'JFALSE) (jfalse-vm vm ist)) ; JFALSE
            ((equal (first ist) 'NOP) (nop-vm vm)) ; NOP
            ((equal (first ist) 'HALT) (halt-vm vm)) ; HALT
            (t (warn "Instruction non reconnue : ~A" ist))
        (decr-vm vm 'PC))))) ; On décrémente PC

;; ######################################################## ;;
