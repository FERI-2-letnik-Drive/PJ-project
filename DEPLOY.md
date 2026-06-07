# Deploy / namestitev

Kratek pregled, kako mobilno aplikacijo zgraditi in namestiti, ter kaj je
potrebno za pravi "deploy".

## Ali je deploy mogoč?

- **Debug APK (za testiranje / demo): da, takoj.** Dovolj je zgraditi APK in ga
  namestiti na napravo.
- **Release APK (podpisan): da, a potrebuje keystore** (podpisni ključ).
- **Pogoj za delovanje:** backend (RAIN) mora biti dosegljiv z naprave. Trenutno
  je `BASE_URL` nastavljen na lokalni IP (`http://192.168.2.51:3001/`), kar
  deluje le v istem omrežju. Za pravi deploy izven LAN-a je treba backend
  gostiti na javnem naslovu (https) in posodobiti `BASE_URL`.

## Debug APK

```bash
cd SmartMailBox
./gradlew assembleDebug
```

Rezultat: `SmartMailBox/app/build/outputs/apk/debug/app-debug.apk`

Namestitev na priklopljeno napravo:

```bash
./gradlew installDebug
# ali
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Release APK (podpisan)

1. Ustvari keystore (enkrat):

```bash
keytool -genkey -v -keystore release.keystore -alias smartmailbox \
  -keyalg RSA -keysize 2048 -validity 10000
```

2. V `SmartMailBox/app/build.gradle.kts` dodaj `signingConfigs` in ga poveži z
   `buildTypes { release { ... } }`. Gesla hrani v `gradle.properties` (ne
   commitaj jih).

3. Zgradi:

```bash
cd SmartMailBox
./gradlew assembleRelease
```

Rezultat: `app/build/outputs/apk/release/app-release.apk`

## Pred deployem preveri

- `BASE_URL` kaže na pravilen, dosegljiv backend.
- Backend (in po potrebi ORV storitev) teče.
- Za produkcijo: backend na `https://` (sicer je potreben `usesCleartextTraffic`).
