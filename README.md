# Serwis z ogłoszeniami psychoterapeutów

Strona internetowa, na której psychoterapeuci mogą zamieszczać ogłoszenia, a użytkownicy mogą je komentować.

## Jak zacząć

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
    
* Geckodriver

### Instalacja

Najłatwiej sklonować [repozytorium](https://github.com/mduczmal/therapy). Wrzucony jest tam gradle wrapper, więc ufam, że po otworzeniu projektu w Intellij wszystko się zaimportuje. Wystarczy kliknąć "Run" przy klasie TherapyApp.
Aplikacja uruchomi się na localhost:8080.

## Testy

### Jednostkowe
Wszystkie testy jednostkowe są w pliku TherapyAppTests. Wszystkie przechodzą, wyniki nie są częścią repo, załączam je na Pegazie.

### Integracyjne

* Lista przypadków testowych, wraz z ich powiązaniami do wymagań znajduje się w folderze
test/resources w plikach o rozszerzeniu *.feature*.

* Logi odpalenia testów automatycznych są częścią pliku *IntegrationTests.html*,
można je rozwinąć po kliknięciu nazwy testu.

* Testy automatyczne są zaimplementowane w klasie AdSteps.

## Autor

**Mikołaj Duczmal**

## Licencja

Wszystkie prawa zastrzeżone.
```
```