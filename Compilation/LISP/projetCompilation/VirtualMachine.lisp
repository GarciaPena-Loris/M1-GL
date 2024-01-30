;; ########### Initialisation des REGISTRES et DRAPEAUX ########### ;
; Definition des registres et des drapeaux
(setq resgistres '(R0 R1 R2 BP SP PC FP PCc MAXPILE))
(setq drapeaux '(FLT FEQ FGT FNIL))
;; ################################################## ;;


;; ########### Getter et Setter REGISTRES ########### ;;
; Verifie si un registre donne 'reg' est present dans la liste des registres 
(defun check-register-presence (reg)
    (member reg resgistres))

; Acceder a la valeur du registre
(defun get-register-value (vm reg)
    (if (check-register-presence reg)
        (get vm reg)))

; Modifie la valeur du registre
(defun set-register-value (vm reg val)
    (if (check-register-presence reg)
        (setf (get vm reg) val)))
;; ################################################## ;;


;; ########### Getter et Setter DRAPEAUX ########### ;;
; Verifie si un drapeau donne 'flag' est present dans la liste des drapeaux
(defun check-flag-presence (flag)
    (position flag drapeaux))

; Accede a la valeur du drapeau dans la machine virtuelle
(defun get-flag-value (vm flag)
    (if (check-flag-presence flag)
        (get vm flag)))

; Active le drapeau dans la machine virtuelle
(defun set-flag-on (vm flag)
    (if (check-flag-presence flag)
        (setf (get vm flag) T)))

; Desactive le drapeau dans la machine virtuelle
(defun set-flag-off (vm flag)
    (if (check-flag-presence flag)
        (setf (get vm flag) nil)))
;; ################################################## ;;

;; ########### Fonction de la MACHINE VIRTUELLE ########### ;;
; Obtenir la memoire de la machine virtuelle 
(defun get-memoire-vm (vm)
    (or (get vm :memory) 0))

; Obtenir la taille de la memoire de la machine virtuelle
(defun get-taille-memoire-vm (vm)
    (get vm :memory_size))

; Definir la memoire de la machine virtuelle
(defun set-memoire-vm (vm taille)
    (setf (get vm :memory) (make-array taille :initial-element nil))
    (setf (get vm :memory_size) taille))

; etat de la memoire de la machine virtuelle
(defun get-etat-memoire-vm (vm)
    (list 'memory
        (get-memoire-vm vm)))

; element de la memoire de la machine virtuelle a l'indice i
(defun get-etat-element-memoire-vm (vm indice)
    (aref (get-memoire-vm vm) indice))


;; --- Partie des TABLES DE HACHAGE --- ;;
; Initialiser la table de hachage pour les etiquettes resolues
(defun init-hashTab-label-resolu (vm)
    (setf (get vm :hashTab_etq_resolu) (make-hash-table :test 'equal)))

; Obtenir la table de hachage pour les etiquettes resolues
(defun get-hashTab-label-resolu (vm)
    (get vm :hashTab_etq_resolu))

; Obtenir la valeur associee a une etiquette resolue dans la table de hachage
(defun get-hashTab-label-resolu-val (vm label)
    (gethash label (get-hashTab-label-resolu vm)))

; Definir une valeur associee a une etiquette resolue dans la table de hachage
(defun set-hashTab-label-resolu (vm label valeur)
    (setf (gethash label (get-hashTab-label-resolu vm)) valeur))

; etat de la table de hachage pour les etiquettes resolues
(defun state-hashTab-label-resolu (vm)
    (list 'hashTab_etq (get-hashTab-label-resolu vm)))

; Initialiser la table de hachage pour les etiquettes non resolues
(defun init-hashTab-label-non-resolu (vm)
    (setf (get vm :hashTab_etq_non_resolu) (make-hash-table)))

; Obtenir la table de hachage pour les etiquettes non resolues
(defun get-hashTab-label-non-resolu (vm)
    (get vm :hashTab_etq_non_resolu))

; Obtenir la valeur associee a une etiquette non resolue dans la table de hachage
(defun get-hashTab-label-non-resolu-val (vm label)
    (gethash label (get-hashTab-label-non-resolu vm)))

; Definir une valeur associee a une etiquette non resolue dans la table de hachage
(defun set-hashTab-label-non-resolu (vm label valeur)
    (setf (gethash label (get-hashTab-label-non-resolu vm)) valeur))

; etat de la table de hachage pour les etiquettes non resolues
(defun state-hashTab-label-non-resolu (vm)
    (list 'hashTab-label (get-hashTab-label-non-resolu vm)))

;; --- Affichage de la machine virtuelle --- ;;
; Obtenir l'etat de la machine virtuelle (registres, drapeaux, memoire, tables de hachage)
(defun etat-vm (vm)
    (progn (print "#### Machine Virtuelle ####")
        (print "- Registres generaux") ; Affiche les registres generaux
        (print (list 'R0 (get-register-value vm 'R0)))
        (print (list 'R1 (get-register-value vm 'R1)))
        (print (list 'R2 (get-register-value vm 'R2)))
        (print "- Registres speciaux") ; Affiche les registres speciaux
        (print (list 'SP (get-register-value vm 'SP)))
        (print (list 'BP (get-register-value vm 'BP)))
        (print (list 'PC (get-register-value vm 'PC)))
        (print (list 'FP (get-register-value vm 'FP)))
        (print (list 'PCc (get-register-value vm 'PCc)))
        (print (list 'MAXPILE (get-register-value vm 'MAXPILE)))
        (print "- Drapeaux :") ; Affiche les drapeaux
        (print (list 'FLT (get-flag-value vm 'FLT)))
        (print (list 'FEQ (get-flag-value vm 'FEQ)))
        (print (list 'FGT (get-flag-value vm 'FGT)))
        (print (list 'FNIL (get-flag-value vm 'FNIL)))
        (print "- Memoire : ") ; Affiche la memoire
        (print (list 'memory (get-etat-memoire-vm vm)))
        (print "- Table de hachage pour les etiquettes : ")
        (print (list 'hashTab_etq (state-hashTab-label-resolu vm)))
        (print (list 'hashTab_etq (state-hashTab-label-non-resolu vm)))))
;; ################################################## ;;


;; ########### Initialisation de la MACHINE VIRTUELLE ########### ;;
;; Initialise la machine virtuelle avec une memoire de taille 'size' 
;; et initialise les registres et les drapeaux a 0 
;; et initialise les tables de hachage pour les etiquettes resolues et non resolues
(defun init-vm (vm size)
    (set-memoire-vm vm size) ; Initialise la memoire
    (set-register-value vm 'R0 0) ; Initialise le registre R0
    (set-register-value vm 'R1 0) ; Initialise le registre R1
    (set-register-value vm 'R2 0) ; Initialise le registre R2
    (set-register-value vm 'SP (floor (/ size 10))) ; Initialise le registre du Stack Pointer
    (set-register-value vm 'BP (floor (/ size 10))) ; Initialise le registre du Base Pointer
    (set-register-value vm 'PC 0) ; Initialise le registre du Program Counter
    (set-register-value vm 'FP (floor (/ size 10))) ; Initialise le registre du Frame Pointer
    (set-register-value vm 'PCc 0) ; Initialise le registre du Program Counter Offset
    (set-register-value vm 'MAXPILE (floor (* size 0.8))) ; Initialise le registre du Max Pile
    (set-flag-off vm 'FLT) ; Initialise le drapeau FLT
    (set-flag-off vm 'FEQ) ; Initialise le drapeau FEQ
    (set-flag-off vm 'FGT) ; Initialise le drapeau FGT
    (set-flag-off vm 'FNIL) ; Initialise le drapeau FNIL
    (init-hashTab-label-resolu vm) ; Initialise la table des references resolues
    (init-hashTab-label-non-resolu vm)) ; Initialise la table des references non resolues

(defun reset-vm (vm size)
    (set-memoire-vm vm size) ; Reinitialise la memoire
    (set-register-value vm 'R0 0) ; Reinitialise le registre R0
    (set-register-value vm 'R1 0) ; Reinitialise le registre R1
    (set-register-value vm 'R2 0) ; Reinitialise le registre R2
    (set-register-value vm 'SP (floor (/ size 10))) ; Reinitialise le registre du Stack Pointer
    (set-register-value vm 'BP (floor (/ size 10))) ; Reinitialise le registre du Base Pointer
    (set-register-value vm 'PC 0) ; Reinitialise le registre du Program Counter
    (set-register-value vm 'FP (floor (/ size 10))) ; Reinitialise le registre du Frame Pointer
    (set-register-value vm 'PCc 0) ; Reinitialise le registre du Program Counter Offset
    (set-register-value vm 'MAXPILE (floor (* size 0.8))) ; Reinitialise le registre du Max Pile
    (set-flag-off vm 'FLT) ; Reinitialise le drapeau FLT
    (set-flag-off vm 'FEQ) ; Reinitialise le drapeau FEQ
    (set-flag-off vm 'FGT) ; Reinitialise le drapeau FGT
    (set-flag-off vm 'FNIL) ; Reinitialise le drapeau FNIL
    (clrhash (get-hashTab-label-resolu vm)) ; Reinitialise la table des references resolues
    (clrhash (get-hashTab-label-non-resolu vm))) ; Reinitialise la table des references non resolues
;; ################################################## ;;


;; ########### Instruction de la MACHINE VIRTUELLE ########### ;;
;; Instruction de la machine virtuelle qui permettent de 
;; manipuler les registres et les drapeaux de la machine virtuelle

; # EVAL-LI : Evalue une expression arithmetique
(defun eval-li (expr vm)
    (cond ((numberp expr) expr) ; Si expr est un nombre, renvoie expr
            ((symbolp expr) (get-register-value vm expr)) ; Si expr est un symbole, renvoie la valeur du registre
            ((listp expr) ; Si expr est une liste, evalue l'expression
            (let ((op (car expr))
                (args (mapcar #'(lambda (x) (eval-li x vm)) (cdr expr))))
            (apply op args))) ; Applique l'operateur sur les arguments
            (t (error "EVAL-LI : expression invalide ~s" expr)))) ; Si expr n'est ni un nombre, ni un symbole, ni une liste, affiche une erreur

;; --- Instructions de manipulation des REGISTRES --- ;;
; # LOAD : Charge le contenu de l'adresse <src> en memoire dans le registre <dest>
(defun load-vm (vm src dest)
        (let* ((src-address (cond
                        ((check-register-presence src) (get-register-value vm src)) ; Si src est un registre, recupere sa valeur
                        ((and (listp src) (eq (car src) ':CONST)) (cdr src)) ; Si src est une constante, recupere sa valeur
                        ((and (listp src) (eq (car src) ':LIT)) (cdr src)) ; Si src est une expression <:LIT . entier>, recupere la valeur entiere
                        (t (eval-li src vm)))) ; Si src est une expression, evalue l'expression
            (dest-register (if (check-register-presence dest)
                                dest ; Si dest est un registre, recupere sa valeur
                                (warn "LOAD : dest '~s' n'est pas un registre !" dest)))) ; Si dest n'est pas un registre, affiche un avertissement
        ;(format t "LOAD : src-address = ~s, dest-register = ~s~%" src-address dest-register)
        (if (and (integerp src-address) (<= 0 src-address (- (get-taille-memoire-vm vm) 1)))
            ; Verifie si src-address est une adresse memoire dans les limites
            (set-register-value vm dest-register (aref (get-memoire-vm vm) src-address)) ; Charge le contenu dans dest-register
            (warn "LOAD : l'adresse memoire @~s est hors limite [~s , ~s]" src-address 0 (- (get-taille-memoire-vm vm) 1)))))


; # STORE : Stocke une valeur depuis une source vers une destination dans la machine virtuelle
(defun store-vm (vm src dest)
    (let* ((src-value (cond ; Verifie si src est un registre, une constante ou une expression
                        ((check-register-presence src) (get-register-value vm src)) ; Si src est un registre, recupere sa valeur
                        ((and (listp src) (eq (car src) ':CONST)) (cdr src)) ; Si src est une constante, recupere sa valeur
                        ((and (listp src) (eq (car src) ':LIT)) (cdr src)) ; Si src est une expression <:LIT . entier>, recupere la valeur entiere
                        (t (eval-li src vm)))) ; Si src est une expression, evalue l'expression
            (dest-address (cond ; Verifie si dest est un registre, une constante ou une expression
                        ((check-register-presence dest) (get-register-value vm dest)) ; Si dest est un registre, recupere sa valeur
                        ((and (listp dest) (eq (car dest) ':CONST)) (cdr dest)) ; Si dest est une constante, recupere sa valeur
                        ((and (listp dest) (eq (car dest) ':LIT)) (cdr dest)) ; Si dest est une expression <:LIT . entier>, recupere la valeur entiere
                        (t (eval-li dest vm))))) ; Si dest est une expression, evalue l'expression
    ;(format t "STORE : src-value = ~s, dest-address = ~s~%" src-value dest-address)
    (if (and (integerp dest-address) (<= 0 dest-address (- (get-taille-memoire-vm vm) 1))) 
        ; Verifie si dest-address est une adresse memoire dans les limites
        (setf (aref (get-memoire-vm vm) dest-address) src-value) ; Ecrit src-value a l'adresse dest-address
        (warn "STORE : l'adresse memoire @~s est hors limite [~s , ~s]" dest-address 0 (- (get-taille-memoire-vm vm) 1)))))
    

; # MOVE : Transfere des valeurs entre registres dans la machine virtuelle
(defun move-vm (vm src dest)
    (let* ((src-value (cond ; Verifie si src est un registre, une constante ou une expression
                        ((check-register-presence src) (get-register-value vm src)) ; Si src est un registre, recupere sa valeur
                        ((and (listp src) (eq (car src) ':CONST)) (cdr src)) ; Si src est une constante, recupere sa valeur
                        ((and (listp src) (eq (car src) ':LIT)) (cdr src)) ; Si src est une expression <:LIT . entier>, recupere la valeur entiere
                        ((integerp src) src) ; Si src est un entier, recupere sa valeur
                        (src))) ; Sinon on recupere simplement la valeur de src
            (dest-register (if (check-register-presence dest)
                            dest ; Si dest est un registre, recupere sa valeur
                            (warn "MOVE : dest '~s' n'est pas un registre !" dest)))) ; Si dest n'est pas un registre, affiche un avertissement
    ;(format t "MOVE : src-value = ~s, dest-register = ~s~%" src-value dest-register)
    (set-register-value vm dest-register src-value))) ; Copie src-value dans dest
;; -------------------------------------------------- ;;

;; --- Instruction d'operations arithmetiques sur les REGISTRES --- ;;
; # ADD : Addition de registres dans la machine virtuelle
(defun add-vm (vm src dest)
    (cond
        ; Verifie si dest est un registre
        ((not (check-register-presence dest))
        (warn "ADD : dest '~s' n'est pas un registre !" dest))
        ; Si src est une valeur atomique (entier), ajoute la valeur a celle du registre dest
        ((atom src)
        (if (not (check-register-presence src))
            ; Verifie si src est un registre
            (if (not (integerp src))
                (warn "ADD : src n'est ni un registre ni un entier !")
                ; Si src est un entier, ajoute la valeur du registre dest avec l'entier src
                (move-vm vm (+ (get-register-value vm dest) src) dest))
            ; Si src est un registre, ajoute la valeur du registre src a celle du registre dest
            (move-vm vm (+ (get-register-value vm src) (get-register-value vm dest)) dest)))
        ; Si src est une expression
        ((not (atom src))
        (if (not (and (eq (car src) ':LIT) (integerp (cdr src))))
            ; Si src n'est pas une expression de la forme <:LIT . entier>, affiche un avertissement
            (warn "ADD : src n'est pas une paire de la forme <:LIT . entier>")
            ; Sinon ajoute la valeur entiere n a celle du registre dest
            (move-vm vm (+ (cdr src) (get-register-value vm dest)) dest)))))

; # SUB : Soustraction de registres dans la machine virtuelle (dest - src)
(defun sub-vm (vm src dest)
    (cond
        ; Verifie si dest est un registre
        ((not (check-register-presence dest))
        (warn "SUB : dest '~s' n'est pas un registre !" dest))
        ; Si src est une valeur atomique (entier), soustrait la valeur a celle du registre dest
        ((atom src)
        (if (not (check-register-presence src))
            ; Verifie si src est un registre
            (if (not (integerp src))
                (warn "SUB : src n'est ni un registre ni un entier !")
                ; Si src est un entier, soustrait la valeur du registre dest avec l'entier src
                (move-vm vm (- (get-register-value vm dest) src) dest))
            ; Si src est un registre, soustrait la valeur du registre src a celle du registre dest
            (move-vm vm (- (get-register-value vm dest) (get-register-value vm src)) dest)))
        ; Si src est une expression
        ((not (atom src))
        (if (not (and (eq (car src) ':LIT) (integerp (cdr src))))
            ; Si src n'est pas une expression de la forme <:LIT . entier>, affiche un avertissement
            (warn "SUB : src n'est pas une paire de la forme <:LIT . entier>")
            ; Sinon soustrait la valeur entiere n a celle du registre dest
            (move-vm vm (- (get-register-value vm dest) (cdr src)) dest)))))

; # MUL : Multiplication de registres dans la machine virtuelle
(defun mul-vm (vm src dest)
    (cond
        ; Verifie si dest est un registre
        ((not (check-register-presence dest))
        (warn "MUL : dest '~s' n'est pas un registre !" dest))
        ; Si src est une valeur atomique (entier), multiplie la valeur a celle du registre dest
        ((atom src)
        (if (not (check-register-presence src))
            ; Verifie si src est un registre
            (if (not (integerp src))
                (warn "MUL : src n'est ni un registre ni un entier !")
                ; Si src est un entier, multiplie la valeur du registre dest avec l'entier src
                (move-vm vm (* (get-register-value vm dest) src) dest))
            ; Si src est un registre, multiplie la valeur du registre src a celle du registre dest
            (move-vm vm (* (get-register-value vm src) (get-register-value vm dest)) dest)))
        ; Si src est une expression
        ((not (atom src))
        (if (not (and (eq (car src) ':LIT) (integerp (cdr src))))
            ; Si src n'est pas une expression de la forme <:LIT . entier>, affiche un avertissement
            (warn "MUL : src n'est pas une paire de la forme <:LIT . entier>")
            ; Sinon multiplie la valeur entiere n a celle du registre dest
            (move-vm vm (* (cdr src) (get-register-value vm dest)) dest)))))


; # DIV : Division de registres dans la machine virtuelle
(defun div-vm (vm src dest)
    (cond
        ; Verifie si dest est un registre
        ((not (check-register-presence dest))
        (warn "DIV : dest '~s' n'est pas un registre !" dest))
        ; Si src est une valeur atomique (entier), divise la valeur a celle du registre dest
        ((atom src)
        (if (not (check-register-presence src))
            ; Verifie si src est un registre
            (if (not (integerp src))
                (warn "DIV : src n'est ni un registre ni un entier !")
                ; Si src est un entier, effectue une division entiere
                (if (zerop src)
                    (warn "DIV : division par zero !")
                    (move-vm vm (floor (/ (get-register-value vm dest) src)) dest)))
            ; Si src est un registre, effectue une division entiere
            (let ((src-value (get-register-value vm src)))
            (if (zerop src-value)
                (error "DIV : division par zero !")
                (move-vm vm (floor (/ (get-register-value vm dest) src-value)) dest)))))
        ; Si src est une expression
        ((not (atom src))
        (if (not (and (eq (car src) ':LIT) (integerp (cdr src))))
            ; Si src n'est pas une expression de la forme <:LIT . entier>, affiche un avertissement
            (warn "DIV : src n'est pas une paire de la forme <:LIT . entier>")
            ; Sinon effectue une division entiere
            (let ((src-value (cdr src)))
            (if (zerop src-value)
                (warn "DIV : division par zero !")
                (move-vm vm (floor (/ (get-register-value vm dest) src-value)) dest)))))))


; # INCR : Incremente la valeur d'un registre dans la machine virtuelle
(defun incr-vm (vm dest)
    (if (not (check-register-presence dest))
        (warn "INCR : dest '~s' n'est pas un registre !" dest)
        (if (eq dest 'PC) ; Verifie si dest est le registre PC
            (if (>= (get-register-value vm dest) (get-register-value vm 'MAXPILE)) ; Utilise MAXPILE comme limite superieure
                (warn "INCR : ~s est hors limite [~s , ~s]" dest (get-register-value vm 'BP) (get-register-value vm 'MAXPILE))
                (set-register-value vm dest (+ (get-register-value vm dest) 1)))
            (set-register-value vm dest (+ (get-register-value vm dest) 1)))))


; # DECR : Decremente la valeur d'un registre dans la machine virtuelle
(defun decr-vm (vm dest)
    (if (not (check-register-presence dest))
        (warn "DECR : dest '~s' n'est pas un registre !" dest)
        (if (eq dest 'PC) ; Verifie si dest est le registre PC
            (if (<= (get-register-value vm dest) (get-register-value vm 'BP)) ; Utilise BP comme limite inferieure
                (warn "DECR : ~s est hors limite [~s , ~s]" dest (get-register-value vm 'BP) (get-register-value vm 'MAXPILE))
                (set-register-value vm dest (- (get-register-value vm dest) 1)))
            (set-register-value vm dest (- (get-register-value vm dest) 1)))))
;; -------------------------------------------------- ;;

;; --- Inscruction d'empilement et de depilement de la PILE --- ;;
; # PUSH : Empile une valeur dans la pile de la machine virtuelle
(defun push-vm (vm src)
    (cond
        ; Si src est un registre
        ((check-register-presence src)
            (incr-vm vm 'SP)          ; On incremente SP
            (store-vm vm src 'SP))    ; On ecrit la valeur de src a la nouvelle adresse de SP
        ; Si src est une valeur entiere ou une expression
        ((or (integerp src) (and (consp src) (eq (car src) ':CONST)))
            (incr-vm vm 'SP)          ; On incremente SP
            (store-vm vm src 'SP))    ; On ecrit la valeur de src a la nouvelle adresse de SP
        ; Si src est une expression arithmetique, evalue l'expression et empile le resultat
        ((consp src)
            (let ((result (eval-li src vm)))
                (incr-vm vm 'SP)
                (store-vm vm result 'SP)))
        ; Si src n'est ni un registre ni une valeur entiere ni une expression arithmetique, affiche un avertissement
        (t (warn "PUSH : src '~s' n'est ni un registre ni une valeur entiere ni une expression arithmetique" src))))

; # POP : Depile une valeur de la pile de la machine virtuelle
(defun pop-vm (vm dest)
    (cond
        ; Si dest est un registre
        ((check-register-presence dest)
        (progn
        (load-vm vm 'SP dest)   ; On charge la valeur a l'adresse de SP dans dest
        (decr-vm vm 'SP)))   ; On decremente SP
        ; Si dest n'est pas un registre, affiche un avertissement
        (t (warn "POP : dest '~s' n'est pas un registre" dest))))
;; -------------------------------------------------- ;;

;; --- Instructions d'etiqutage et de saut --- ;;
; # LABEL : Definit une etiquette dans la machine virtuelle
(defun label-vm (vm label)
    ; Verifie si label est un symbole (nom de l'etiquette)
    (if (consp label)
        (progn
            (setf label (car label)) ; Recupere le nom de l'etiquette
            ; Utilise le nom de l'etiquette comme cle pour la table de hachage
            (unless (get-hashtab-label-resolu-val vm label)
            ; Definit l'adresse de l'etiquette dans le registre PCc
            (set-hashtab-label-resolu vm label (get-register-value vm 'PCc)))
            ; Valeur de la table
            ;(format t "LABEL : label: '~s', valeur: '~s'~%" label (get-hashtab-label-resolu-val vm label))
        )
        (warn "LABEL : label '~s' n'est pas un symbole (nom de l'etiquette) !" label)))


; # JMP : Saut inconditionnel vers une etiquette ou une adresse dans la machine virtuelle
(defun jump-vm (vm dest)
    (if (integerp dest)
        (move-vm vm dest 'PC)   ; Si dest est une adresse, on deplace la valeur de dest dans PC
        (if (get-hashtab-label-resolu-val vm dest)
            (move-vm vm (get-hashtab-label-resolu-val vm dest) 'PC) ; Si dest est une etiquette, on deplace la valeur de label dans PC
            (warn "JMP : dest '~s' n'est ni une adresse ni une etiquette !" dest))))

    
; # JSR : Saut vers une sous-routine dans la machine virtuelle
(defun jsr-vm (vm label)
    (push-vm vm (+ (get-register-value vm 'PC) 1)) ; On pousse l'adresse courante sur la pile
    (jump-vm vm label)) ; On effectue le saut

; # RTN : Retour d'une sous-routine dans la machine virtuelle
(defun rtn-vm (vm)
    (load-vm vm 'SP 'R0) ; On charge le contenu du sommet de pile dans R0
    (decr-vm vm 'SP) ; On decremente SP
    (jump-vm vm (get-register-value vm 'R0))) ; On saute a l'adresse contenue dans R0
;; -------------------------------------------------- ;;

;; --- Instructions de comparaison --- ;;
; # CMP : Compare deux registres dans la machine virtuelle
(defun cmp-vm (vm src1 src2)
    (cond
        ((check-register-presence src1)
            (setf src1 (get-register-value vm src1))) ; Si src1 est un registre, on recupere sa valeur
        ((and (not (atom src1)) (eq (car src1) ':LIT) (integerp (cdr src1)))
            (setf src1 (cdr src1)))) ; Si src1 est une expression <:LIT . entier>, on recupere la valeur entiere
    (cond
        ((check-register-presence src2)
            (setf src2 (get-register-value vm src2))) ; Si src2 est un registre, on recupere sa valeur
        ((and (not (atom src2)) (eq (car src1) ':LIT) (integerp (cdr src2)))
            (setf src2 (cdr src2)))) ; Si src2 est une expression <:LIT . entier>, on recupere la valeur entiere
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

; # JGT : Saut si le drapeau FGT est active 'Greater Than' (!FEQ !FLT FGT)
(defun jgt-vm (vm label)
    (if (and (not (get-flag-value vm 'FEQ)) (not (get-flag-value vm 'FLT)) (get-flag-value vm 'FGT))
        (jump-vm vm label)))

; # JGE : Saut si le drapeau FGT ou FEQ est active 'Greater or Equal' (FEQ !FLT FGT)
(defun jge-vm (vm label)
    (if (and (not (get-flag-value vm 'FLT))
    (or (get-flag-value vm 'FEQ) (get-flag-value vm 'FGT)))
        (jump-vm vm label)))

; # JLT : Saut si le drapeau FLT est active 'Lesser Than' (!FEQ !FGT FLT)
(defun jlt-vm (vm label)
    (if (and (not (get-flag-value vm 'FEQ)) (not (get-flag-value vm 'FGT)) (get-flag-value vm 'FLT))
        (jump-vm vm label)))

; # JLE : Saut si le drapeau FLT ou FEQ est active 'Lesser or Equal' (FEQ !FGT FLT)
(defun jle-vm (vm label)
    (if (and (not (get-flag-value vm 'FGT))
    (or (get-flag-value vm 'FEQ) (get-flag-value vm 'FLT)))
        (jump-vm vm label)))

; # JEQ : Saut si le drapeau FEQ est active 'Equal' (FEQ !FGT !FLT)
(defun jeq-vm (vm label)
    (if (and (not (get-flag-value vm 'FLT)) (not (get-flag-value vm 'FGT)) (get-flag-value vm 'FEQ))
        (jump-vm vm label)))

; # JNE : Saut si le drapeau FEQ n'est pas active 'Not Equal' (!FEQ FGT FLT)
(defun jne-vm (vm label)
    (if (not (get-flag-value vm 'FEQ))
        (jump-vm vm label)))
;; -------------------------------------------------- ;;

;; --- Inscrutction de comparaison de NIL --- ;;
; # TEST : Teste si un registre est NIL dans la machine virtuelle
(defun test-vm (vm src)
    (cond
        ((check-register-presence src)
            (setf src (get-register-value vm src))) ; Si src est un registre, on recupere sa valeur
        ((and (not (atom src)) (eq (car src) ':LIT) (integerp (cdr src)))
            (setf src (cdr src)))) ; Si src est une expression <:LIT . entier>, on recupere la valeur entiere
    (if (or (eq src 0) (eq src nil)) ; Si src est NIL
        (set-flag-on vm 'FNIL) ; On active le drapeau FNIL
        (set-flag-off vm 'FNIL))) ; Sinon on desactive le drapeau FNIL

; # JTRUE : Saut si le drapeau FNIL est desactive
(defun jtrue-vm (vm label)
    (if (not (get-flag-value vm 'FNIL))
        (jump-vm vm label)
        (incr-vm vm 'PC)))

; # JFALSE : Saut si le drapeau FNIL est active
(defun jfalse-vm (vm label)
    (if (get-flag-value vm 'FNIL)
        (jump-vm vm label)
        (incr-vm vm 'PC)))
;; -------------------------------------------------- ;;

;; --- Instruction de fin --- ;;
; # NOP : Instruction vide
(defun nop-vm (vm))

; # HALT : Arrete l'execution de la machine virtuelle
(defun halt-vm (vm)
    (format t "HALT : Fin de l'execution de la machine virtuelle~%")
    (set-register-value vm 'PC -1))
;; -------------------------------------------------- ;;
;; ################################################## ;;


;; ########### Applique une fontion a des argument de la MACHINE VIRTUELLE ########### ;;
(defun apply-vm (vm fonction nbArgs)
    ;; Initialisation des variables locales
    (let ((i nbArgs) (args nil))
        ;; Boucle pour recuperer les arguments depuis la pile
        (loop while (> i 0) do
        (progn
            ;; Charger la valeur depuis la pile
            (load-vm vm (+ (- (get-register-value vm 'FP) i) 1) 'R0)
            ;; Ajouter la valeur a la liste des arguments
            (push (if (atom (get-register-value vm 'R0)) 
                    (get-register-value vm 'R0)
                    (cdr (get-register-value vm 'R0)))
                args)
            (setf i (- i 1))))
        ;; Appliquer la fonction avec les arguments
        (when (and (listp args) (car args))
        (move-vm vm (apply fonction (reverse args)) 'R0))
    ))
;; ################################################## ;;


;; ########### Chargeur de la MACHINE VIRTUELLE ########### ;;
(defun chargeur-lisp-vm (vm lisp)
    (let ((asm (LI_TO_ASM (LISP2LI lisp nil) 0)))
    ; Boucle principale de chargement du code dans la machine virtuelle
    (loop
        while (not (atom asm)) ; Tant que asm n'est pas vide
        do
        (progn
            ; Affiche l'instruction actuelle
            (print (car asm))
            ; Deplace l'instruction dans le registre R0
            (move-vm vm (car asm) 'R0)
            ; Stocke la valeur du registre R0 dans le compteur ordinal (PCc)
            (store-vm vm 'R0 'PCc)
            ; Charge la valeur du compteur ordinal dans le registre R1
            (load-vm vm 'PCc 'R1)
            ; Si l'instruction est un LABEL, appelle la fonction vm_label avec l'argument du LABEL
            (if (eq (car (get-register-value vm 'R1)) 'LABEL)
                (label-vm vm (cdr (get-register-value vm 'R1))))
            ; Incremente le compteur ordinal
            (incr-vm vm 'PCc)
            ; Passe a l'instruction suivante
            (setf asm (cdr asm))
        ))
    ; Ajout de l'instruction HALT a la fin
    (move-vm vm '(HALT) 'R0)
    (store-vm vm 'R0 'PCc)
    (incr-vm vm 'PCc)))

(defun chargeur-asm-vm (vm asm)
    ; Boucle principale de chargement du code dans la machine virtuelle
    (loop
        while (not (atom asm)) ; Tant que asm n'est pas un atome
        do
        (progn
            ; Deplace l'instruction dans le registre R0
            (move-vm vm (car asm) 'R0)
            ; Stocke la valeur du registre R0 dans le compteur ordinal du chargeur (PCc)
            (store-vm vm 'R0 'PCc)
            ; Charge la valeur du compteur ordinal dans le registre R1
            (load-vm vm 'PCc 'R1)
            ; Si l'instruction est un LABEL, appelle la fonction vm_label avec l'argument du LABEL
            (if (eq (car (get-register-value vm 'R1)) 'LABEL)
                (label-vm vm (cdr (get-register-value vm 'R1))))
            ; Incremente le compteur ordinal
            (incr-vm vm 'PCc)
            ; Passe a l'instruction suivante
            (setf asm (cdr asm))
        ))
    ; Ajout de l'instruction HALT a la fin
    (move-vm vm '(HALT) 'R0)
    (store-vm vm 'R0 'PCc)
    (incr-vm vm 'PCc))
;; ######################################################## ;;


;; ########### Exectuer la MACHINE VIRTUELLE ########### ;;
;; Definition de la fonction exec-vm qui execute la machine virtuelle
(defun execute-vm (vm exprLisp)
    (move-vm vm 'PCc 'PC)     ; Initialisation du compteur de programme
    (chargeur-asm-vm vm exprLisp)  ; Chargement du programme en memoire
    ;(format t "lisp -> ~s~%" exprLisp)
    ;(format t "PCc -> ~s~%" (get-register-value vm 'PCc))
    ;(format t "PC -> ~s~%" (get-register-value vm 'PC))
    ; Execution du programme
    (loop
        ; Condition de sortie : tant que le drapeau FNIL est OFF
        while (not (= (get-register-value vm 'PC) -1))
        do
        ; Execution de l'instruction a l'adresse PC
        (progn
            (load-vm vm 'PC 'R2)  ; Charger l'instruction dans le registre R2
            ;(format t "PC -> ~s~%" (get-register-value vm 'PC))
            (let ((fonction (car (get-register-value vm 'R2))) ; Extraire le nom de la fonction
                (args (cdr (get-register-value vm 'R2)))) ; Extraire les arguments de la fonction
                ;(format t "Fonction -> {~s}, Arguments -> {~s}~%" fonction args)
            ; Appel de la fonction correspondante a l'instruction
            (cond
                ((equal fonction 'LOAD) 
                    (progn 
                        (load-vm vm (first args) (second args))
                        (incr-vm vm 'PC))) ; LOAD
                ((equal fonction 'STORE) 
                    (progn 
                        (store-vm vm (first args) (second args))
                        (incr-vm vm 'PC))) ; STORE
                ((equal fonction 'MOVE) 
                    (progn 
                        (move-vm vm (first args) (second args))
                        (incr-vm vm 'PC))) ; MOVE
                ((equal fonction 'ADD) 
                    (progn 
                        (add-vm vm (first args) (second args))
                        (incr-vm vm 'PC))) ; ADD
                ((equal fonction 'SUB) 
                    (progn 
                        (sub-vm vm (first args) (second args))
                        (incr-vm vm 'PC))) ; SUB
                ((equal fonction 'MUL) 
                    (progn 
                        (mul-vm vm (first args) (second args))
                        (incr-vm vm 'PC))) ; MUL
                ((equal fonction 'DIV) 
                    (progn 
                        (div-vm vm (first args) (second args))
                        (incr-vm vm 'PC))) ; DIV
                ((equal fonction 'INCR) 
                    (progn 
                        (incr-vm vm fonction)
                        (incr-vm vm 'PC))) ; INCR
                ((equal fonction 'DECR) 
                    (progn 
                        (decr-vm vm fonction)
                        (incr-vm vm 'PC))) ; DECR
                ((equal fonction 'PUSH) 
                    (progn 
                        (push-vm vm fonction)
                        (incr-vm vm 'PC))) ; PUSH
                ((equal fonction 'POP) 
                    (progn 
                        (pop-vm vm fonction)
                        (incr-vm vm 'PC))) ; POP
                ((equal fonction 'LABEL) 
                    (progn 
                        (label-vm vm fonction)
                        (incr-vm vm 'PC))) ; LABEL
                ((equal fonction 'JMP) 
                    (progn 
                        (jump-vm vm fonction)
                        (incr-vm vm 'PC))) ; JMP
                ((equal fonction 'JSR) 
                    (progn 
                        (jsr-vm vm fonction)
                        (incr-vm vm 'PC))) ; JSR
                ((equal fonction 'RTN) 
                    (progn 
                        (rtn-vm vm)
                        (incr-vm vm 'PC))) ; RTN
                ((equal fonction 'CMP) 
                    (progn 
                        (cmp-vm vm (first args) (second args))
                        (incr-vm vm 'PC)
                        ; Afficher les drapeaux
                        ;(format t "FEQ -> ~s~%" (get-flag-value vm 'FEQ))
                        ;(format t "FGT -> ~s~%" (get-flag-value vm 'FGT))
                        ;(format t "FLT -> ~s~%" (get-flag-value vm 'FLT))
                        )) ; CMP
                ((equal fonction 'JGT) 
                    (progn 
                        (jgt-vm vm (first args))
                        (incr-vm vm 'PC))) ; JGT (Plus grand que)
                ((equal fonction 'JGE) 
                    (progn 
                        (jge-vm vm (first args))
                        (incr-vm vm 'PC))) ; JGE (Plus grand ou egal)
                ((equal fonction 'JLT) 
                    (progn 
                        (jlt-vm vm (first args))
                        (incr-vm vm 'PC))) ; JLT (Plus petit que)
                ((equal fonction 'JLE) 
                    (progn 
                        (jle-vm vm (first args))
                        (incr-vm vm 'PC))) ; JLE (Plus petit ou egal)
                ((equal fonction 'JEQ) 
                    (progn 
                        (jeq-vm vm (first args))
                        (incr-vm vm 'PC))) ; JEQ (egal)
                ((equal fonction 'JNE) 
                    (progn 
                        (jne-vm vm (first args))
                        (incr-vm vm 'PC))) ; JNE (Different)
                ((equal fonction 'TEST) 
                    (progn 
                        (test-vm vm (first args))
                        (incr-vm vm 'PC))) ; TEST
                ((equal fonction 'JTRUE) 
                    (progn 
                        (jtrue-vm vm (first args))
                        (incr-vm vm 'PC))) ; JTRUE
                ((equal fonction 'JFALSE) 
                    (progn 
                        (jfalse-vm vm (first args))
                        (incr-vm vm 'PC))) ; JFALSE
                ((equal fonction 'NOP) 
                    (progn 
                        (nop-vm vm)
                        (incr-vm vm 'PC))) ; NOP
                ((equal fonction 'HALT) 
                    (progn 
                        (halt-vm vm))) ; HALT
                ((equal fonction 'APPLY)
                    (progn
                        (apply-vm vm (first args) (second args))
                        (incr-vm vm 'PC)))) ; APPLY
                ))) ; Fin de la boucle principale
        ; Affiche le resultat de l'expression Lisp
        (format t "Le resultat de l'expression Lisp est : ~s~%" (get-register-value vm 'R0)))
;; ######################################################## ;;
