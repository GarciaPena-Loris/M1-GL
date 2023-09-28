.data
demande : .asciiz "Ecrivez un entier\n"

.text
main :  	li $v0, 4
		la $a0, demande
		syscall 
		li $v0, 5 
		syscall
		move $a0, $v0
		li $t0, 0
		bgt $a0, $t0, affiche
		neg $a0, $a0
affiche :	li $v0, 1
		syscall
