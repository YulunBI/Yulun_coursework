#Yulun Wu
.data
array: .word -5, 1, 4, 8, 11, 65, 100, 101
length: .word 8
str1 .asciiz "Enter value to find:\n"
str2 .asciiz "Result is:\n"
.text
.globl main
# Store a in $t0 # Store b in $t1 # Store c in $t2
main:
	 la $s0,  array	# Store address of array
     	 la $s1, length	# Store address of length
     	 lw $s1, 0(length)	# Store length
     	 
PRINTQ: # Print "Enter value to find:" (address of string to print should be in $a0)
	li $v0, 4
	la $a0, str1
	syscall
	
	# Read number val (result will be in $v0)
	li $v0, 5
	syscall
	move $s2, $v0 # move number in $v0 to $t0
	
PUSHP:	addi $sp, $sp, -12	# Move the top of Stack up by 12
	sw $s0, 8($sp)	# Push address of array to Stack
	sw $s1, 4($sp)	# Push length to Stack
	sw $s2, 0($sp)	# Push val to Stack

	# Call binsearch
	jal binsearch
	
	# Pop result off the Stack
	lw $s3, 0($sp)
	addi $sp, $sp, 4
	
PRINTR:	# Print "Result is:" (address of string to print should be in $a0)
	li $v0, 4
	la $a0, str2
	syscall
	
	li $v0, 1
	move $a0, $s3 # Print result (value of N is stored in $a0)
	syscall
     	 
     	 # End program
	li $v0, 10
	syscall
     	 
binsearch:
POP:	lw $t2, 0($sp)	# Pop val to $t2
	lw $t1, 4($sp)	# Pop length to $t1
	lw $t0, 8($sp)	# Pop address of array to $t0
	
	sw $ra, 8($sp)	# Push current #ra to Stack
	addi $sp, $sp, 8	# Move the top of Stack down by 8

BASE:	blez $t1, NOSOL	# If length <= 0, branch to NOSOL
	addi $t1, -1
	sra $t1, $t1, 1
	
	

NOSOL:	li $t3, -1	# Load -1 to $t3


RETURN:	lw $ra, 0($sp)	# Pop $ra off Stack
	sw $t3, 0($sp)	# Push $t3 to Stack
	jr $ra	# Return to main
	                                                                                                      

