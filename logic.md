% Programozási nyelvek (Java) félévközi vizsga, 2017.01.10.

# Általános tudnivalók

Ebben az ismertetésben az osztályok, valamint a minimálisan szükséges metódusok leírásai fognak szerepelni.  A feladatmegoldás során fontos betartani az elnevezésekre és típusokra vonatkozó megszorításokat, illetve a szövegek formázási szabályait.

Nem publikus láthatóságú segédfüggvények létrehozhatóak, a feladatban nem megkötött adattagok és elnevezéseik is a feladat megoldójára vannak bízva. Törekedjünk arra, hogy az osztályok belső reprezentációját a *lehető legjobban védjük*, tehát csak akkor engedjünk, és csak olyan hozzáférést, amelyre a feladat felszólít, vagy amit azt osztályt használó kódrészlet megkíván!

A beadott megoldásodnak működnie kell a mellékelt tesztprogramokkal, de ez nem elégséges feltétele az elfogadásnak. A megírt forráskód legyen kellően általános és újrafelhasználható!

Az egyes feladatrészeknél két pontszám látható (pl. 5 + 1), ebből az első az alappont, mely a helyességért, a feladatleírásban szereplő követelmények teljesítéséért adható. A második egy pluszpont, mely az oktató megítélése alapján akkor jár, ha a hallgató az adott feladatrészt a félévben tanult alapvető programozási irányelvek betartásával készítette el, így levonandó például optimalizálatlan, kódismétlésekkel teli vagy konvenciókat erősen sértő kód esetén.

Használható segédanyagok: [Java dokumentáció](https://bead.inf.elte.hu/files/java/api/index.html), legfeljebb egy üres lap és toll.  Ha bármilyen kérdés, észrevétel felmerül, azt a felügyelőknek kell jelezni, *NEM* a diáktársaknak!

*Figyelem!* Az a metódus, mely fordítási hibát tartalmaz, automatikusan *nulla* pontot ér!

# Tesztelés

Az egyes részfeladatokhoz tartoznak külön tesztesetek, amelyeket a feladatok végén jelöltünk meg.  Ezek önállóan is fordítható és futtatható `.java` állományok a mellékelt `.jar` segítségével.  Például Windows alatt az első feladathoz tartozó tesztesetek így fordíthatóak és futtathatóak:

~~~~
> javac -cp .;tests-Logic.jar tests/Part1.java
> java -cp .;tests-Logic.jar tests/Part1
~~~~

Ugyanezeket a teszteseteket használja a komplett feladathoz tartozó
tesztelést végző `Test` osztály is. Ezt Windows alatt így lehet
futtatni:

~~~~
> java -cp .;tests-Logic.jar Test
~~~~

Linux alatt mindent ugyanúgy lehet fordítani és futtatni, csak a `-cp`
paraméterében a pontosvesszőt kell kettőspontra cserélni.

# A feladat összefoglaló leírása

A feladatban logikai kapukból álló hálózatokat fogunk modellezni. A logikai kapuk a számítógépek alapvető építőkövei, az őket összekötő vezetékek egy-egy bitet "szállítanak" (folyik áram/nem folyik áram), a kapuk a bemeneteik alapján egyszerű logikai műveleteket hajtanak végre (*és*, *nem*, stb.), majd továbbítják az eredményt:

	1 ---|-----|                 |-----|
	     | AND |--- 1       0 ---| NOT |--- 1
	1 ---|-----|                 |-----|

	1 ---|-----|                 |-----|
	     | AND |--- 0       1 ---| NOT |--- 0
	0 ---|-----|                 |-----|

A programhoz tartozik [egységtesztelő](/files/java/Logic-tests.zip), mintafájlokkal az utolsó feladatrészhez.

# A feladat részletes ismertetéses 

A hálózatban a logikai kapuk mellett a bemenetet biztosító ki- és bekapcsolható áramforrások és az eredményt jelző LED-ek is helyet kapnak, ezeket összefoglalóan csúcsoknak (*node*) fogjuk nevezni, ezeket kötjük majd össze vezetékekkel.

## 1. rész (5 + 1 pont)

### `nodes.base.Node` (1 pont)

Egy interfész, mely a hálózatban szereplő csúcsok ősinterfésze lesz.

 - Deklaráljunk egy `int` visszatérési értékű, paraméter nélküli `getId` (absztrakt) metódusa, mely azért lesz felelős, hogy visszaadjon egy egyedi azonosítót minden egyes csúcshoz.

 - Deklaráljunk egy szöveges visszatérési értékű, paraméter nélküli `getType` metódust, melynek megvalósítása megadja majd a csúcs típusát.

### `flow.Wire`

Egy osztály, mely a csúcsokat összekötő vezetékek típusa lesz. Egyelőre csak hozzuk létre az osztályt, hogy hivatkozhassunk rá, a következő feladatrészben fogjuk kiegészíteni. 

### `nodes.base.Sender` (1 pont)

Egy interfész, mely azokat a csúcsokat fogja össze, melyek rendelkeznek valamilyen ***kimenettel***, mint az áramforrások és kapuk. A belőlük kivezető vezetékeket egész indexszel azonosítjuk.

 - Terjessze ki a `Node` interfészt.

 - Legyen egy `Wire` visszatérési értékű, `int` paraméterű `getOutput` (absztrakt) metódusa, mely az adott egész indexszel azonosított kimenetet fogja visszaadni.

### `nodes.base.Receiver` (1 pont)

Egy interfész, mely azokat a csúcsokat fogja össze, melyek rendelkeznek valamilyen ***bemenettel***, mint a LED-ek és a kapuk. A vezetékeket utólag fogjuk rájuk csatlakoztatni, ezek helyét egész indexszel azonosítjuk.

 - Terjessze ki a `Node` interfészt.

 - Legyen egy visszatérési érték nélküli, `int`  és `Wire` paraméterű `setInput` (absztrakt) metódusa, mellyel az adott indexű helyre fogjuk bekötni a bemenetet hordozó vezetéket.

 - Legyen egy visszatérési érték és paraméter nélküli `update` (absztrakt) metódusa, ennek meghívásával fogjuk jelezni, ha valamelyik bemenet frissült, ezért a csúcs belső állapotát is frissíteni kell.

### `nodes.base.Gate` (2 pont)

Egy interfész, mely a logikai kapuk ősinterfésze.

 - Terjessze ki a `Sender` **és** a `Receiver` interfészt.

 - Deklaráljon egy `boolean` visszatérési értékű, paraméter nélküli `isOperable` metódust, mely megadja, hogy a kapu működőképes-e. Később, a megvalósításnál egy kapu akkor lesz működőképes, ha mindegyik bemenetét bekötöttük.

Tesztelő: `tests.Part1`
Az egyes interfészekhez külön tesztelők vannak a `tests` csomagban, melyeket részletesebb eredményért egyesével is le lehet futtatni, de `tests.Part1` mindegyiket meghívja.

## 2. rész (5 + 1 pont)

### `flow.Bit` (1 pont)

Egy felsorolási típus, mely leírja egy bit lehetséges értékeit.

 - Lehetséges értékei:

		ZERO, ONE

 - Legyen egy `Bit` visszatérési értékű, paraméter nélküli `invert` metódusa. A metódus `ZERO`-n hívva `ONE`-t, `ONE`-on hívva `ZERO`-t adjon vissza.

### `flow.Wire` (4 pont)

Egy osztály, mely a csúcsokat összekötő vezetékek működését írja le.

 - Legyenek a következő nem publikus adattagjai:
	 - `Receiver` típusú `end` (a vezeték vége, ahová bekötjük), 
	 - `Bit` típusú `value` (az éppen hordozott érték).

 - Az `end` adattag kezdeti értéke legyen `null`, a `value` adattagé `Bit.ZERO`. 

 - Legyenek paraméter nélküli publikus getter metódusai: `getEnd`, `getValue`.

 - Legyen egy publikus setValue metódusa, melynek egy Bit paramétere van, és beállítja a vezetékben hordozott bitet (`value` adattag). Másrészt, ha a `value` frissült, azaz az új érték eltér a régitől és az `end` értéke nem `null`, akkor hívja meg az `end` `update()` metódusát, hogy frissülhessen a fogadó.

 - Legyen egy visszatérési érték nélküli `connect` publikus *osztályszintű* metódusa,  mely összeköt egy forrást és egy fogyasztót egy vezetékkel. A metódusnak négy paramétere van:

	- `Sender` típusú `start`,
	- `int` típusú `outputIndex`,
	- `Receiver` típusú `end`,
	- `int` típusú `inputIndex`.

	A metódus kérdezze le a `start` csúcs `outputIndex`edik kimenő vezetékét, majd állítsa be a kapott vezetéket az `end`nél az `inputIndex`edik bemenetként. A vezeték `end` mezőjét is frissítse, és ha mindent beállított, hívja meg az `end` csúcs `update()` metódusát, hogy frissülhessen a csúcs.

 - Legyen egy `toString` metódusa, mely a következő formátumban írja ki a vezeték szöveges reprezentációját:

		Wire to X [ZERO]

	Ha a vezeték vége (`end` adattag) `X`, aktuális értéke pedig `ZERO`. Ha az `end` értéke `null`, `X` helyére írjunk `<>`-t!

Tesztelő: `tests.Part2`
Az egyes osztályokhoz külön tesztelők vannak a `tests` csomagban, melyeket részletesebb eredményért egyesével is le lehet futtatni, de `tests.Part2` mindegyiket meghívja.

## 3. rész (10 + 2 pont)

### `nodes.base.AbstractNode` (2 pont)

Egy absztrakt osztály, mely a későbbi csúcsok megvalósításait fogja segíteni ősosztálya, hogy többször újrahasználhassuk ugyanazt a kódrészletet.

 - Valósítsa meg a `Node` interfészt.

 - Legyen egy `int` típusú osztályszintű privát `count` adattagja, melynek kezdeti értéke legyen `0`.

 - Legyen egy `int` típusú privát `id` adattagja.

 - Legyen egy paraméter nélküli konstruktor is, mely lementi az `id`be a `count` aktuális értékét, és növeli eggyel a `count`ot.

 - A `Node` interfészben deklarált `getId` metódust valósítsuk meg, adja vissza az `id` adattag aktuális érékét.

 - Legyen egy paraméter és visszatérési érték nélküli publikus és *osztályszintű* `resetCount` metódusa, mely ismét `0`-ra állítja a `count` adattagot.

 - Legyen egy `toString` metódusa, mely a következő formátumban írja ki a csúcs szöveges reprezentációját:

		type_id

	Ahol a `type` a `Node` interfészben deklarált `getType` metódus visszatérési értéke. 

*Megjegyzés*: a `getType` metódust nem valósítjuk meg ebben az absztrakt osztályban, ez az alosztályok feladata lesz.

### `exceptions.LogicException` (1 pont)

Hozzunk létre egy `LogicException` kivételt, mely a könnyebb kezelés érdekében legyen nem ellenőrzött kivétel (származzon a `RuntimeException`ből).

### `nodes.base.AbstractGate` (7 pont)

Egy absztrakt osztály, mely a későbbi kapuk megvalósításait fogja segíteni szülőosztályként, hogy többször újrahasználhassuk ugyanazt a kódrészletet.

 - Terjessze ki az `AbstractNode` absztrakt osztályt.

 - Valósítsa meg a `Gate` interfészt (és azon keresztül a `Sender` és `Receiver` interfészeket is).

 - Legyen két privát adattagja, egy `inputs` és egy `outputs`, melyek a bemenő és kimenő vezetékeit fogják tárolni. Az adattagok típusa lehet `Wire` lista vagy `Wire` tömb, ezt az implementálóra bízzuk.

 - Legyen egy publikus konstruktora, mely két `int` paramétert kap, egy `inputCount` és egy `outputCount` változót. Ha valamelyik paramétere negatív vagy nulla, dobjon `LogicException`t. Máskülönben hozza létre az `inputs` és `outputs` listát/tömböt, értelemszerűen `inputCount` és `outputCount` méretekkel. Az `inputs` tartalmazzon csupa `null` értékeket, az `outputs`ot viszont töltsük fel `Wire` példányokkal.

 - A `Receiver` interfészben deklarált `setInput` és a `Sender` interfészben deklarált `getOutput` metódust is valósítsuk meg. Mindkettő először ellenőrizze, hogy a paraméterként kapott index létező-e, és ha nem, dobjanak `LogicException`t. Ha megfelelő az index,  mentse le előbbi a kapott `Wire` példányt az `inputs` megadott indexű helyére, míg utóbbi adja vissza az `outputs` adott indexű elemét.

 - A `Gate` interfészben deklarált `isOperable` metódus megvalósítása menjen végig az `inputs` lista/tömb elemein, és ha bármelyik `null`, adjon vissza hamisat, különben igazat.

 - Hozzunk létre egy védett, *absztrakt* `calculateResult` metódust, mely egy `Bit` tömböt kap paraméterül, és egy darab `Bit`tel tér vissza.

 - A `Receiver` interfészben deklarált `update` metódus megvalósítása tegye a következőket:
	- Ha az `isOperable` metódus aktuálisan hamisat ad vissza, ne tegyünk semmit.
	- Különben hozzunk létre egy, az `inputs` méretével megegyező `Bit` tömböt, és az `inputs` elemein végighaladva sorba másoljuk át ebbe a `Bit` tömbbe a vezetékeken éppen szállított biteket (`Wire` `getValue` metódusa).
	- Ezzel a `Bit` tömbbel mint paraméterrel hívjuk meg az előbb deklarált `calculateResult` metódust.
	- Haladjunk végig az `outputs` listán/tömbön, és minden vezetékre írjuk rá a `calculateResult` eredményeként kapott bitet (`Wire` `setValue` metódusa).

*Megjegyzés*: a `getType` metódust itt sem valósítjuk meg, ez az alosztályok feladata lesz.

Tesztelő: `tests.Part3`
Az egyes osztályokhoz külön tesztelők vannak a `tests` csomagban, melyeket részletesebb eredményért egyesével is le lehet futtatni, de `tests.Part3` mindegyiket meghívja.

## 4. rész (10 + 2 pont)

### `nodes.Not` (2 pont)

Egy *nem* kaput megvalósító osztály.

 - Származzon az `AbstractGate` típusból.

 - Legyen egy publikus, paraméter nélküli konstruktora, ez hívja meg az ősosztály konstruktorát `1`, `1` paraméterekkel (egy *nem* kapunak egy bemenete és egy kimenete van).

 - A `Node` interfészben deklarált `getType` metódus térjen vissza `NOT`-tal.

 - A `calculateResult` metódus kérje le a paraméterben kapott tömb első elemét, és térjen vissza az invertált értékével. (*Tipp*: `Bit` `invert` metódusa.)

### `nodes.And` (2 pont)

Egy *és* kaput megvalósító osztály.

 - Származzon az `AbstractGate` típusból.

 - Legyen egy publikus, `int` paraméteres konstruktora, ez hívja meg az ősosztály konstruktorát a paraméterrel és `1`-gyel (egy általános *és* kapunak tetszőleges számú bemenete, de csak `1` kimenete van).

 - Legyen egy publikus, paraméter nélküli konstruktora is, ez az alapértelmezett `2`, `1` értékekkel hívja meg az őst.
 
 - A `getType` metódus térjen vissza `AND`-del.

 - A `calculateResult` metódus térjen vissza `ONE`-nal, ha a tömb minden eleme `ONE`, és `ZERO`-val különben.

### `nodes.Junction` (2 pont)

Egy csomópontot megvalósító osztály, mely több vezetékre másolja ugyanazt a bemeneti értéket.

 - Származzon az `AbstractGate` típusból.

 - Legyen egy publikus, `int` paraméteres konstruktora, ez hívja meg az ősosztály konstruktorát `1`-gyel és a paraméterrel (egy általános csomópontnak csak `1` bemenete, de tetszőleges számú kimenete van).

 - Legyen egy publikus, paraméter nélküli konstruktora is, ez az alapértelmezett `1`, `2` értékekkel hívja meg az őst.
 
 - A `getType` metódus térjen vissza `JUNCTION`-nel.

 - A `calculateResult` metódus térjen a kapott tömb első elemével.

### `nodes.Source` (2 pont)

Egy áramforrást megvalósító osztály.

 - Tejessze ki az `AbstractNode` típust, és valósítsa meg a `Sender` interfészt.

 - Legyen egy `Wire` típusú `output` paramétere, ezt a (paraméter nélküli) konstruktor töltse fel egy új `Wire` példánnyal.

 - A `getType` metódus térjen vissza `SOURCE`-szal.

 - Legyen két publikus, paraméter és visszatérési érték nélküli metódusa: `switchOn`, mely `ONE`-t és `switchOff`, mely `ZERO`-t ír az `output` adattagban tárolt vezetékre.

 - A `Sender` interfészben deklarált `getOutput` metódus dobjon `LogicException`t, ha a kapott egész paraméter értéke nem `0`, különben adja vissza az `output` adattag értékét.

### `nodes.Led` (2 pont)

Egy LED-et megvalósító osztály.

 - Tejessze ki az `AbstractNode` típust, és valósítsa meg a `Receiver` interfészt.

 - Legyen egy `Wire` típusú `input` paramétere és egy `boolean` típusú `on` adattagja, előbbit a (paraméter nélküli) konstruktor hagyja `null`on, utóbbi legyen hamis.

 - A `getType` metódus térjen vissza `LED`-del.

 - Legyen egy publikus, paraméter nélküli `isOn` metódusa, mely visszaadja az `on` adattag értékét.

 - A `Receiver` interfészben deklarált `setInput` metódus dobjon `LogicException`t, ha a kapott egész paraméter értéke nem `0`, különben a kapott `Wire` paramétert mentse az `input` adattagba.

 - A `Receiver`ből örökölt `update` metódusa állítsa be `on` változó értékét attól függően, hogy az `input`on szállított bit `ONE`-e.

Tesztelő: `tests.Part4`
Az egyes osztályokhoz külön tesztelők vannak a `tests` csomagban, melyeket részletesebb eredményért egyesével is le lehet futtatni, de `tests.Part4` mindegyiket meghívja.

## 5. rész (10 + 2 pont)

### `flow.Circuit` (10 pont)

Készítsük el a fájlból áramforrásokat beolvasó Circuit osztályt. Az áramforrások leírásában mindegyik csúcsot egy névvel (tetszőleges szó) azonosítjuk majd, úgy hivatkozunk rájuk.

 - Legyen három privát adattagja, egy-egy `Map`. Az egyikbe az áramforrásokat, a másikba a LED-eket, a harmadikba a kapukat (*és*, *nem*, csomópont) gyűjtjük majd, mindegyikre a neve alapján akarunk hivatkozni.

 - Legyenek a következő `String` paraméteres, publikus metódusai, melyek kikeresik a megfelelő elemet a neve alapján a megfelelő gyűjteményből: `getSource`, `getLed`, `getGate`, `getSender`, `getReceiver`. Az utóbbi két metódus nyilván az áramforrások és kapuk, illetve kapuk és LED-ek közt is meg kell, hogy nézze, hogy szerepel-e a kívánt elem. Mind az öt metódus adjon vissza `null`t, ha a keresett névvel nem talál megfelelő elemet.

 - A publikus konstruktora fogadjon egy darab `String` paramétert, mely egy megnyitandó szöveges fájl neve. A fájlban szóközzel darabolt sorok szerepelnek, egy (helyes) sor két szóval kezdődhet: `NODE` vagy `WIRE`. A kétféle sor feldolgozására használjunk egy-egy (publikus) metódust, melyeknek visszatérési értéke nincs, és egy-egy `String` tömböt várnak paraméterül (a szavakra tört sort):

	- `createNode`: ellenőrzi, hogy a kapott tömb három elemű-e. Ha igen, akkor a második elem a létrehozandó csúcs típusa (`AND`, `NOT`, `JUNCTION`, `SOURCE` vagy `LED`), a harmadik a neve. Ha a típus ismert, és a név még nem használt (egyik `Map`ben sincs benne), akkor hozzuk létre a megfelelő csúcsot, és tegyük bele a megfelelő `Map`be. `And` és `Junction` esetén a paraméter nélküli konstruktort hívjuk meg.

	- `connectWire`: ez 5 elemű tömböt vár, ebből a 2. és 4. a megfelelő `Sender` és `Receiver` neve, a 3. és 5. a megfelelő indexek. Kössük össze őket. (A bemenet helyessége semmilyen formában nem garantált, a 3. és 5. paraméter nem feltétlen szám, a két név lehet, hogy nem ismert, a vezeték bekötése közben `LogicException` keletkezhet. Bármelyik hiba esetén, egyszerűen ne csináljunk semmit.)

Tesztelő: `tests.Part5`

## Pontozás

A tesztelő által adott értékelés csak becslésnek tekinthető, a gyakorlatvezető ehhez képest levonhat vagy megadhat még pontokat.

  - 0  -- 17: elégtelen (1)
  - 18 -- 24: elégséges (2)
  - 25 -- 31: közepes (3)
  - 32 -- 38: jó (4)
  - 39 -- 48: jeles (5)
