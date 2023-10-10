// Écrire une fonction qui teste si un entier est un carré
var n: integer
f(n: integer): integer
    var i: integer
    if n = 0 then
        f := 1
    else
        f := 1;
        i := 1;
        while i <= n do
            f := f * i;
            i := i + 1
n := read();
write(f(n))

var n: integer
f(n: integer)
    var i: integer
    if n = 0 then
        f := 1
    else
        f := 1;
        i := 1;
        while i <= n do
            f := f * i;
            i := i + 1
n := read();
write(f(n))

var n : integer
f(n : integer)
var i: integer
j: integer
k: integer
if n = 0 then
    f := 1
else
    f := f(n-1) * n
n := read();
write(f(n))

var n : integer
f(n : integer)
if n = 0 then
    f := 1
else
    f := f(n-1) * n
n := read();
write(f(n))

var n : integer
f()
n := n + 1
n := read();
write(f(n))

#############################################################################

// Écrire la fonction factorielle de manière récursive
var n : integer
f(n : integer): integer
if n = 0 then
    f := 1
else
    f := f(n-1) * n
n := read();
write(f(n))

#############################################################################

// Écrire un programme qui alloue un tableau d’entiers d’une taille
// demandée à l’utilisateur, et appelle une procédure pour l’initialiser
var n: integer // Taille du tableau

// Procédure pour initialiser le tableau avec des zéros
initTab(tab: array of integer, n: integer)
var i: integer
i := 0; // Initialise i à 0
while i < n do
    tab[i] : = 0; // Initialise chaque élément du tableau à 0
    i : = i + 1

// Fonction pour créer et initialiser le tableau
f(n: integer): array of integer
var tab: array of integer
tab : = new array of integer[n]
f : = initTab (tab, n); // Appel de la procédure pour initialiser le tableau

n : = read(); // Demande à l'utilisateur la taille du tableau
write(f(n)) // Affiche le tableau résultant

#############################################################################

// Écrire une fonction qui teste si tous les éléments d’un tableau d’entiers
// passé en paramètre sont positifs.
var tab: array of integer

f (tab: array of integer) : boolean
var i : integer
f : = true;
while tab[i] != null do
    if tab[i] < 0 then
        f : = false
    else
        i : = i + 1;

tab : = read();
write(f (tab))