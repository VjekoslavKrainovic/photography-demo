# Project description

- Potrosio sam oko 25h za cijeli projekt. 
- Cijeli projekt a pogotovo Custom SQL engine se moze jos puno unaprijediti ali mislim da je ovo dovoljno za show off zadatak.
- Ne support-am nested subquery-e. Ako se stave dvije zagrade za redom, endpoint baca gresku ali ako se npr posalje and ( author eq 'Vjeko'() ekstra zagrada ce ignorirati
- Imam validaciju svih keywordova, te izmedu svakog keyworda MORA biti razmak.
- Imam validaciju redosljedi keywordova.
- Ne koristim Swagger za API dokumentaciju, jer objasnjavam kako koristiti u ovom readme-u.

# Kako koristi endpoint-e

- GET `http://localhost:8080/api/v1/photographies/{photohraphy-id}` :
    - `{photohraphy-id}` je id fotografije pomocu koje dohvacamo fotografiju, te ako ne postoji endpoint baca gresku


- POST `http://localhost:8080/api/v1/photographies/` :
    - `{
      "name": "ime2",
      "description": "opis",
      "author": "pero",
      "imageUrl": "image url",
      "width": 22.0,
      "height": 300.1,
      "tags": ["tag3", "tag4"]
      }` POST body pomocu kojeg kreiramo fotograciju, te su svih fieldovi required


- PUT `http://localhost:8080/api/v1/photographies/` :
    - `{
      "name": "ime promjenjen",
      "description": "opis promjenjen",
      "author": "author promjenjen",
      "width": 21.0,
      "height": 301.1,
      "tags": [{
      "id": 2,
      "name": "tag2"}]
      }` PUT body pomocu kojeg update-amo postojecu fotografiju, te su svih fieldovi required.
    - `{photohraphy-id}` je id fotografije  koji koristimo da bih znali koju fotografiju trebamo update-ati, te ako ne postoji endpoint baca gresku
    - Ako ne upisem id u tag, kreira se novi tag.
    - Ako stavimo tag id koji ne postoji u bazi ili pripada drugoj fotografiji dobit cemo gresku na endpointu
    - Ako stavimo postojeci tag id onda ce se update-ati postojeci tag 


- DELETE `http://localhost:8080/api/v1/photographies/{photohraphy-id}` :
    - `{photohraphy-id}` je id fotografije koji koristimo da bih znali koju fotografiju trebamo delete-ati, te ako ne postoji endpoint baca gresku


- GET `http://localhost:8080/api/v1/photographies?filter=tag eq ‘tag1’ or ( author eq ‘pero’ and name eq ‘ime2’ )&sort=createdAt,asc&page=0&size=1` :
    - `filter=tag eq ‘tag1’ or ( author eq ‘pero’ and name eq ‘ime2’ )` filter query parametar, nije required
    - `sort=createdAt,asc` sort query parametar, umjesto `asc` se moze koristiti i `desc`, nije required
    - `page=0` pagination number, nije required
    - `size=1` pagination page size, nije required
    - Pripaziti da se koriste curly qutoes kao sto je u primjeru zadataka :) umjesto signle qutoes za field name.

# Questions & Answers

    Q: Zasto si napravio tag validator ako ga mozes validirati u metodi dok updateas tag?
    A: Cisto da se drzim istog "patterna" kao sto validiram i photography

    Q: Zasto validatori nemaju interface?   
    A: Interface je u velikoj vecini jedino potreban za I/O komponente, za ovakve stvari se uvijek mjenja trenutna impl.

    Q: Sta znaci Dbo sufix na hibernate entiti?
    A: Database object

    Q: Zasto validiras opet photography kad fetchas by id u repositoryu i u TagMapperu?
    A: Defensivno programiranje

    Q: Zasto nisi koristio Gang of Four design patterne?
    A: Potrosio sam puno vremena za projekt, tako da je ovo out of the scope

    Q: Zasto imas puno loop-ova u SearchPhotographyMapper-u?
    A: Bitno je napraviti neku generalnu optimizaciju i ciljati da je kod jako citak. 
        80% vremena provedemo citajuci kod tako da je po meni ovo puno bitnije. 
        Tek kad imamo problema sa optimizacijom onda cemo raditi na njoj. Ne radim `Premature optimization`

    Q: Zasto nisi odvojio Photography i Tag kao 2 Aggregate Roota jer Tag-ova moze biti beskocano?
    A: Zato sto sam odlucio da je maksimalan broj Tag-ova 100 komada tako da nemamo taj problem.

    Q: Zasto nemas sve pokriveno Testovima?
    A: Potrosio sam puno vremena na projektu, tako da je ovo out of the scope.
        No za glavnu logiku su napravljeni testovi.

    Q: Zasto nisi koristio Three-Tier architecture?
    A: Ovo mi je bilo puno zanimljivije za raditi, ali i za Vas da mozete vidjeti kako pisem kod.
    
    Q: Zasto nisi mapirao Filter keywordove u plain SQL?
    A: Ne bi mi bilo izazovan zadatak,a u productionu bi vjerovatno ovako nesto radili jer nemoramo brinuti o SQL injectionima itd...

    