Set of plugins for Minecraft which lets you make a lobby server, bungee server and bedwars minigame. docs in czech below

# README.md

## Jak zprovoznit Minecraft server

Tento dokument popisuje postup a konfiguraci pro spuštění Minecraft serveru s několika pluginy.

---

## Plugin: Lobby

### Popis

Plugin slouží ke správě lobby. Obsahuje následující funkce:

* Menu pro přepínání serverů a kosmetické funkce
* Ochrana před zničením lobby světa
* Nastavení výchozího místa připojení ("spawn")

### Práva

* `lobby.spawn.set` — nastavení spawnu
* `lobby.not.spawn.on.spawn` — ignorování teleportace na spawn
* `lobby.edit.inventory` — úprava inventáře
* `lobby.break.block` — možnost ničit bloky
* `lobby.place.block` — možnost pokládat bloky

### Příkazy

* `/spawn` — teleportace na spawn
* `/spawn set x y z` — nastavení spawnu
* `/myperms add/rem/check player permission` — správa práv
* `/myperms disable/enable` — zapnutí/vypnutí vnímání práv pluginem

### Konfigurace (YML)

V souboru `settings.yml` ve složce pluginu můžete nastavit spawn pomocí klíčů `x`, `y`, `z`.

---

## Plugin: Bedwars

### Popis

Minihra, kde cílem je zničit postel nepřátelského týmu a chránit svou. K dispozici jsou 4 týmy (modrá, červená, zelená, žlutá).

### Práva

* `Bedwars.admin` — správa a tvorba arén

### Příkazy

* `/bw pos1` — nastavení prvního rohu arény
* `/bw pos2` — nastavení druhého rohu arény
* `/bw save` — uložení arény

### Použití

* Nastavení provádějte v `settings.yml`, všechny položky musí být ve větvi `Bedwars`
* Spawn body a jejich souřadnice jsou povinné
* Obchody nastavujte v `shop.yml` – určujete položky a odkazy na další YAML soubory podobchodů
* Valuty a itemy lze přizpůsobit podle potřeby
* Po uložení arény a nastavení konfigurace restartujte server

---

## Plugin: MyPerms

### Popis

Plugin slouží k lokální správě oprávnění hráčů.

### Práva

* `myperms.set.perms` — nutné pro správu oprávnění bez OP

### Příkazy

* `/myperms add/rem/check player permission (true/false)` — správa práv hráčů

### Konfigurace (YML)

* Oprávnění hráčů se ukládají do souboru `permissions.yml`

---

## Plugin: BungeeSwitch

### Popis

Plugin umožňuje přepínání mezi servery pomocí jednoho příkazu.

### Práva

* `BungeeSwitcher.switch`

---

## Plugin: VoidWorld

### Popis

Plugin generuje prázdné světy.

### Konfigurace

Pro správnou funkčnost je potřeba upravit `bukkit.yml`:

```yaml
worlds:
  jmenosveta:
    generator: VoidWorld
```

### Práva

* Žádná specifická práva nejsou potřeba

---

> Poznámka: Všechny pluginy by měly být umístěny ve složce `plugins/`. Konfigurační soubory se obvykle nachází ve složkách se stejným názvem jako plugin.
