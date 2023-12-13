(require "VirtualMachine.lisp")

(let (vm '()) (
    (init-vm vm)
    (load-vm '(
        (move-vm (:CONST 5) R0)
        (move-vm (:CONST 3) R1)
        (add-vm R1 R0)
    ))
    (vm-exec vm)
    (print (= (attr-get vm :R0) 8))
))