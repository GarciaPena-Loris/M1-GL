########################################
#~ définitions
########################################

BIN=bin/Pi  bin/Pconfig

# liste des fichiers sources
SRCS0=Pi.c
SRCS1=Pconfig.c

default: $(BIN)

########################################
#~ regles pour l'executable
########################################

obj/%.o: %.c
	gcc -Wall -Iinclude -c $< -o $@

bin/Pi: $(SRCS0:%.c=obj/%.o)
	gcc -o $@ $+

bin/Pconfig: $(SRCS1:%.c=obj/%.o)
	gcc -o $@ $+

clean:
	rm -f $(BIN) obj/*.o *~