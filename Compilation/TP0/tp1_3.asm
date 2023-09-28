.data
demande : .asciiz "Combien d'entier ?\n"
virgule : .asciiz ", "

.text
main :  
	li $v0, 4
	la $a0, demande
	syscall 
	li $v0, 5 
	syscall
	addi $v0, $v0, -2
	move $t1, $v0
	li $t0, 0
verif:	blt $t1, $t0, fin
	addi $t0, $t0, 1
	li $v0, 1
	move $a0, $t0
	syscall
	move $t0, $a0
	li $v0, 4
	la $a0, virgule
	syscall
	j verif
fin: 	addi $t0, $t0, 1
	li $v0, 1
	move $a0, $t0
	syscall
