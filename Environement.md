

## 🌱 Sujet de TP – API Java : Suivi de Biodiversité

### Objectif pédagogique

Développer une **API REST en Java (Spring Boot)** permettant aux utilisateurs de :

* Signaler la présence d’espèces animales ou végétales dans la nature.
* Visualiser les signalements par zone géographique.
* Filtrer les signalements par espèce, date, ou région.
* De **calculer l’empreinte carbone** liée aux déplacements effectués lors de ces observations.

---

## Spécifications fonctionnelles

### 1. Entités

#### Espèce (`Specie`)

* `id` (Long)
* `commonName` (String)
* `scientificName` (String)
* `category` (Category *enum* )

#### Enum `Category`

```java
public enum Category {
    BIRD, MAMMAL, INSECT, PLANT, OTHER
}
```

#### Observation (`Observation`)

* `id` (Long)
* `specie` (Specie)
* `observerName` (String)
* `location` (String) – ex: "Montpellier", "Forêt de Fontainebleau"
* `latitude` / `longitude` (Double)
* `observationDate` (LocalDate)
* `comment` (String, optional)


Chaque observation est associée à un déplacement.

#### Travellog

* `id` (Long)
* `observation` (Observation) 
* `distanceKm` (Double)
* `mode` (TravelMode *enum* )
* `estimatedCo2Kg` (Double)


#### Enum `TravelMode`

```java
public enum TravelMode {
    WALKING, BIKE, CAR, BUS, TRAIN, PLANE
}
```

### Logique de calcul CO₂ (estimation standardisée)

| Mode de transport | Émission CO₂ approximative |
| ----------------- | -------------------------- |
| Walking / Bike    | 0 kg/km                    |
| Car               | 0.22 kg/km                 |
| Bus               | 0.11 kg/km                 |
| Train             | 0.03 kg/km                 |
| Plane             | 0.259 kg/km                |

[Calculateur du gouvernement](https://agirpourlatransition.ademe.fr/particuliers/bureau/deplacements/calculer-emissions-carbone-trajets#calcul-empreinte-carbonne)

---

### 2. Endpoints REST

#### Espèces

* `GET /species` → Liste des espèces connues
* `POST /species` → Ajouter une espèce
* `GET /species/{id}` → Détails d’une espèce

#### Observations

* `GET /observations` → Toutes les observations (avec filtres possibles)
* `POST /observations` → Ajouter une observation
* `GET /observations/{id}` → Voir une observation
* `GET /observations/by-location?location=Paris` → Filtrer par lieu
* `GET /observations/by-species/{speciesId}` → Filtrer par espèce

####  Déplacement
* `POST /travel-logs`
  Créer un déplacement lié à une observation (inclut le calcul CO₂).
* `GET /travel-logs`
  Liste des déplacements + émissions totales CO₂.
* `GET /travel-logs/stats/{idObservation}`
  Renvoie :

  ```json
  {
    "totalDistanceKm": 45.5,
    "totalEmissionsKg": 8.4,
    "byMode": {
      "CAR": 5.5,
      "TRAIN": 2.9
    }
  }
  ```

---

## Extensions possibles (bonus)

* API pour exporter les données en **CSV** ou **GeoJSON**. [GeoJSON](https://geojson.io/#new&map=2/0/20)
* Swagger UI.
* Connexion à une base externe comme GBIF (Global Biodiversity Information Facility).[Guide de l'Api](https://data-blog.gbif.org/post/gbif-api-beginners-guide/) [Doc Api](https://techdocs.gbif.org/en/openapi/) 
* Dockerisation complète.

* Endpoint `GET /travel-logs/user/{name}` pour filtrer les déplacements d’un utilisateur.
* Utiliser une **API de géolocalisation** pour estimer automatiquement la distance entre deux points.
* Stocker l’historique des émissions **par mois / par utilisateur**.
* Générer un **rapport PDF ou graphique (ex: avec Chart.js en front)**.


---


HttpRequest

```json
###
POST http://localhost:8080/api/observation
Content-Type: application/json

{
  "observerName": "Léo Lhomme",
  "location": "California",
  "latitude": -79.88883256422646,
  "longitude": 40.06793614361021,
  "observationDateStr": "19-06-2025",
  "comment": "just found some bird",
  "specieId": 1,
  "travellogs": [
    {
      "distanceKm": 12,
      "mode": "CAR"
    },
    {
      "distanceKm": 2.5,
      "mode": "WALKING"
    }
  ]
}
```