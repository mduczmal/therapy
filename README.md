# Serwis z ogłoszeniami psychoterapeutów

Strona internetowa, na której psychoterapeuci mogą zamieszczać ogłoszenia, a użytkownicy mogą je komentować.

## Jak zacząć

Żeby uniknąć niespodzianek związanych z uruchamianiem aplikacji, umieściłem ją na [Heroku](https://still-river-45705.herokuapp.com). Jeśli jednak zaistnieje potrzeba odpalenia jej lokalnie, poniżej zamieszczam instrukcje.

Do profilu terapeuty można zalogować się używając nazwy użytkownika: TestN i hasła: passN, gdzie N jest liczbą od 1 do 6.

Jest jeden profil moderatora o nazwie użytkownika: admin i haśle: admin.

Przykładowe dane (konta użytkowników, ogłoszenia, komentarze) inicjalizowane są w klasie InitData.
### Prerekwizyty

* IntelliJ

* Gradle 6.3

* Java 11

* Baza danych postgreSQL na domyślnym porcie (5432), zgodna z wartościami z pliku application.properties:

    * Nazwa: therapytestdb

    * Użytkownik: therapy

    * Hasło: test

### Instalacja

Najłatwiej sklonować [repozytorium](https://github.com/mduczmal/therapy). Wrzucony jest tam gradle wrapper, więc ufam, że po otworzeniu projektu w Intellij wszystko się zaimportuje. Wystarczy kliknąć "Run" przy klasie TherapyApp.
Aplikacja uruchomi się na localhost:8080.

## Użyte narzędzia

### Dlaczego Java
Chciałem wybrać język statycznie typowany, bo wtedy łatwiej poruszać się po dużym projekcie i go debugować.

Chciałem wybrać język znany, żeby wszystko gładko chodziło (dlatego Kotlin odpadł - próbowałem kiedyś używać go ze Springiem i naraziłem się przez to na specyficzne problemy), żeby było dużo narzędzi, materiałów, odpowiedzi na Stacku.

Co zostawiło do wyboru C#, C++ lub Javę. C# odpada, bo praca pod Windowsem jest trudniejsza niż pod Linuxem, C++ odpadł, bo jest mniej czytelny niż Java.

### Dlaczego Spring
Chcę się go nauczyć do pracy. Największy framework webowy dla Javy, dużo materiałów.

### Dlaczego Spring Boot
Gotowa konfiguracja wszystkiego, miałem z nim dobre doświadczenia w przeszłości.

### Dlaczego Bootstrap
Gotowe komponenty bez używania frameworku do front-endu. Ostatnio robiłem coś z Vue.js i byłem zawiedziony, że wybrałem Vuetify zamiast Bootstrapa. Teraz chciałem to nadrobić.

### Dlaczego JUnit
Standardowa biblioteka do testów jednostkowych dla Javy. Dobra integracja ze Springiem.


## Testy

Wszystkie testy jednostkowe są w pliku TherapyAppTests. Wszystkie przechodzą, wyniki nie są częścią repo, załączam je na Pegazie.

## Wzorce projektowe

Przy użyciu annotacji Springa wykorzystałem wzorzec projektowy repozytorium w klasach:
Ad-, Comment-, Therapist-, User- i AuthorityRepository. W kontrolerach wykorzystałem wzorzec projektowy dependency injection.

## Autor

**Mikołaj Duczmal**

## Licencja

Wszystkie prawa zastrzeżone.
```
```