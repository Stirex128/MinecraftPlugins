Set of plugins for Minecraft which lets you make a lobby server, bungee server and bedwars minigame. docs in czech below

Uživatelská dokumentace
Jak zprovoznit minecraft server:


Plugin lobby
Popis:
Plugin slouží ke správě lobby. Obsahuje několik prvků:
menu na přepínání serverů, zapínání různých kosmetických prvků
ochranu před zničením lobby
nastavení místa, kam se lidé připojí, když se připojí na server a jde se na něj teleportovat

Práva:
lobby.spawn.set
lobby.not.spawn.on.spawn
lobby.edit.inventory
lobby.break.block
lobby.place.block

Příkazy
/spawn - teleportuje hráče na dané souřadnice
/spawn set x y z - nastaví spawn na dané souřadnic
/myperms add/rem/check player permission -přidá/odebere/zobrazí specifikovanou permission
/myperms disable/enable - vypne nebo zapne vnímání pluginu na práva

Yml files - nacházejí se ve stejnojmenné složce ve složce plugins
Spawn jde nastavit i ve složce stejnojmenné jako plugin v souboru settings.yml kde se za x y z dosadí čísla (souřadnice spawnu)

Plugin Bedwars
Popis:
Minihra ve které jde zničit druhému týmu postele a chránit svojí. V této variantě jsou 4 týmy a to v barvách modrá, červená, zelená a žlutá.

Práva:
Bedwars.admin - pro správu a vytváření arén.


Příkazy:
/bw pos1/pos2/save uloží jeden roh arény/druhý roh/poté uloží celou arénu

Použití:
V settings.yml si nastavíte veškeré údaje, které potřebujete(druhy nastavení najdete v modelovém yml souboru). Pozor vše zapisujte do kolonky Bedwars jak je naznačeno v ukázkovém souboru. Povinná nastavení jsou  ty v jedné úrovni po Bedwars a u spawnů i ty souřadnicové. Je potřeba dodržovat typ hodnot, pokud např string přepíšete na číslo plugin nebude správně fungovat.
	Dále si uložíte arénu pomocí příkazu(nezapomeňte přidat postele) a nastavíte věci v obchodech. To funguje tak, ze do shop. yml nastavíte na pozici nabe jmeno dalsich yml s podobchody, které se budou pri kliknuti na danym item otevírat. Poté si vytvoříte nový yml file s názvem podobchodu ve kterém budete mít strukturu jako je v ukázkovém podobchodu.Dobré je vědět, že itemy z dropperů si můžete měnit jak chcete a tak i měny v obchodech a to vše v settings nebo shop souborech.
	Nyní zrestartujte a vaše hra je připravena na lidi.

Plugin MyPerms
Popis: Plugin, který umožňuje hráčům přidávat určitá práva. Plugin funguje lokálně.

Práva:
myperms.set.perms - prvně si toto oprávnění nastavte v OP a poté už budete moci spravovat práva bez OP. Umožňuje celkový přístup k pluginu

Příkazy:
/myperms add/rem/check player permission (true/false) -přidá/odebere/zobrazí specifikovanou v závorce nepovinné

yml files:
Vznikne permissions.yml kam se ukládájí hráči a jejich oprávnění.


Plugin BungeeSwitch
Popis: plugin přidá jeden příkaz na měnění serverů

Práva:
BungeeSwitcher.switch


Plugin VoidWorld
Popis: Plugin generující prázdné světy(do bukkit.yml musíte dát
worlds:
    jmenosveta:
       Generator: VoidWorld
pro správnou funkčnost)

Práva nejsou

