.data
positive : .asciiz "positive\n"
negative : .asciiz "negative\n"

.text
main :  li $v0, 5 
	syscall
	li $t0, 0
	blt $v0, $t0, neg 
	li $v0, 4
	la $a0, positive
	syscall
	j end 
neg :  li $v0, 4
	la $a0 , negative
	syscall
end: