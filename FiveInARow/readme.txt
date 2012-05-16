Genmodell - FiveInARow

* Bestemt rekkef�lge, f�rste gen er f�rste pri, annet gen er andre pri, osv
* Automatisk random valg av pos hvis flere mulige posisjoner tilfredsstiller genet

F�rste siffer er matrix size: 0 = 1, 1 = 3*3, 2 = 5*5, 3 = 7*7, 4 = 9*9
Andre-Siste siffer er pos i matrix hvor 0 = bryr meg ikke, 1 = ikke satt, 2 = min brikke, 3 = motstanders brikke
Andre-Siste siffer i m=0 er st�y/fyllplass
Andre siffer i m=1 er (x-1,y), 3 siffer i m=1 er (x-1,y-1), 4 siffer i m=1 er (x,y-1) osv til og med siffer 9, resten er st�y/fyllplass
Andre-9 siffer i m=2 er samme som for m=1
10 siffer for m=2 er (x-2, y), 11 siffer for m=2 er (x-2,y-1), 12 siffer for m=2 er (x-2,y-2) osv til og med siffer 25
Siffer 26-49 er reservert for m=3
Siffer 50-81 er reservert for m=4

Med andre ord:
1 gen = 81 siffer
Antall gener er avhengig av ytelsen; jo flere gener jo mer tid trengs til alternative s�k
La oss starte med et lite antall gener, f.eks. 5-10

Iterasjon 2:
1 siffer er ikke n�dvendig, f.eks. 0 i matrix size kan representeres som bare 0'ere i resten av genet
Det gj�r det lettere � parre og mutere. Da holder det med 80 siffer for ett gen

G�r det forresten an � la spillerne starte med et lite antall gener i utgangspunktet,
 men deretter la de f� muligheten til � utvikle flere gener ? Da f�r man samtidig en naturlig kompleksitetsutvikling 



