# Turtle Game

----
## Uruchomienie aplikacji

### Po uruchomieniu aplikacji dostępne są następujące funkcjonalności:

- Step Forward - Dodaje do sekwencji komend polecenie wykonania kroku w przód
- Turn Left - Dodaje do sekwencji komend polecenie obrócenia żółwia w lewo
- Turn Prawo - Dodaje do sekwencji komend polecenie obrócenia żółwia w prawo
- Remove last command - Usuwa ostatnią komendę z utworzonej sekwencji komend
- Clear - Czyści sekwencję komend
- Execute - Wykonuje sekwencję komend
- Reset - Przywraca grę do stanu początkowego
- Loop - Umożliwia dodanie do sekwencji komend pętli iteracyjnej wykonującej wybrane komendy określoną liczbę razy
- Lista rozwijalna z wyborem poziomu - umożliwia wybór poziomu

----
## Przebieg rozgrywki
### Cel rozgrywki:
Celem rozgrywki jest utworzenie, a następnie uruchomienie takiej sekwencji komend, by wszystkie widoczne na planszy pola zostały odwiedzone przez żółwia. Za porażkę uznaje się wykonanie takiej sekwencji komend, która nie zapewni odwiedzenia wszystkich pól przez żółwia.Porażką jest również próba wykonania żółwiem ruchu poza dostępne pola

----
## Interfejs graficzny
### Oznaczenie pól:
- pole w kolorze czerwonym - pole, które nie zostało odwiedzone przez żółwia
- pole w kolorze niebieskim - pole, które zostało odwiedzone przez żółwia
