# Pametni paketnik – mobilna aplikacija (PJ-project)

Android aplikacija (Kotlin + Jetpack Compose) za upravljanje pametnega paketnika
Direct4me, povezana z lastno spletno storitvijo (RAIN backend) in storitvijo za
prepoznavo obraza (ORV).

## Funkcionalnosti

- Prijava in registracija (RAIN backend)
- Dvostopenjska prijava z obrazom (face verify prek ORV)
- Trajna seja – uporabnik ostane prijavljen po ponovnem zagonu
- Skeniranje QR kode paketnika in odpiranje Direct4me paketnika (predvajanje žetona)
- Preverjanje dovoljenja na RAIN backendu pred odpiranjem
- Profil: pregled, urejanje in sprememba gesla
- Zgodovina odpiranj paketnika
- Odjava

## Zahteve

- Android Studio (novejša verzija) z Android SDK
- Naprava ali emulator z Android 7.0+ (API 24+)
- Delujoč RAIN backend (mapa `SmartMailBox`, Node.js + MongoDB)
- Dostop do interneta (Direct4me sandbox API)

## Konfiguracija

Naslov RAIN backenda je nastavljen v:

`SmartMailBox/app/src/main/java/com/example/smartmailbox/api/AuthRetrofitInstance.kt`

```kotlin
private const val BASE_URL = "http://192.168.2.51:3001/"
```

Spremeni `BASE_URL` na IP/host, kjer teče tvoj backend (npr. IP računalnika v
istem omrežju kot telefon). Ker se uporablja `http://`, je v manifestu vklopljen
`usesCleartextTraffic`.

Direct4me sandbox API in ključ sta nastavljena v `RetrofitInstance.kt`.

## Zagon

1. Zaženi RAIN backend (`SmartMailBox`) in po potrebi ORV storitev.
2. V `AuthRetrofitInstance.kt` nastavi pravilen `BASE_URL`.
3. Odpri mapo `SmartMailBox` v Android Studiu, počakaj na Gradle sync.
4. Izberi napravo/emulator in pritisni **Run**.

## Testi

Enotni testi (JUnit) so v `SmartMailBox/app/src/test`. Zaženi jih z:

```bash
cd SmartMailBox
./gradlew test
```

ali v Android Studiu: desni klik na mapo `test` → **Run Tests**.

## Struktura projekta

- `api/` – Retrofit vmesniki in modeli (RAIN + Direct4me)
- `model/` – stanja zaslonov (UI state)
- `viewmodel/` – logika (ViewModel)
- `view/` – Compose zasloni
- `navigation/` – navigacija med zasloni

## GitHub Flow

Razvoj poteka po strategiji v `github-flow-strategy.md` (feature branchi, PR,
review, merge v `main`).