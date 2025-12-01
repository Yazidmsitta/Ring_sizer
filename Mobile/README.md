# Ring Sizer - Application Mobile

Application mobile complète pour mesurer la taille de bagues/bracelets, suivre le cours de l'or et accéder à une marketplace de produits en or.

## Architecture

### Application Android (Kotlin)
- **Architecture**: MVVM
- **Base de données locale**: Room (SQLite)
- **Réseau**: Retrofit
- **Coroutines**: Pour les tâches asynchrones
- **Min SDK**: Android 8.0 (Oreo)

### Backend API (Laravel)
- **Framework**: Laravel (PHP)
- **Base de données**: MySQL
- **Authentification**: Laravel Sanctum
- **API**: RESTful

## Structure du Projet

```
.
├── android/                 # Application Android
│   └── app/
│       └── src/main/
│           ├── java/com/ringsize/app/
│           │   ├── data/           # Couche de données
│           │   │   ├── local/      # Room database
│           │   │   ├── remote/     # API service
│           │   │   └── repository/ # Repositories
│           │   ├── ui/              # Interface utilisateur
│           │   │   ├── auth/       # Authentification
│           │   │   ├── measure/    # Mesure
│           │   │   ├── history/    # Historique
│           │   │   ├── goldprice/  # Cours de l'or
│           │   │   ├── marketplace/# Marketplace
│           │   │   └── settings/   # Paramètres
│           │   └── viewmodel/      # ViewModels
│           └── res/                 # Ressources
└── laravel/                # Backend API
    ├── app/
    │   ├── Http/Controllers/Api/
    │   └── Models/
    ├── database/migrations/
    └── routes/api.php
```

## Installation

### Android

1. Ouvrir le projet dans Android Studio
2. Synchroniser les dépendances Gradle
3. Configurer l'URL de l'API dans `RetrofitClient.kt`
4. Compiler et exécuter

### Laravel

1. Installer les dépendances:
```bash
cd laravel
composer install
```

2. Configurer `.env`:
```bash
cp .env.example .env
php artisan key:generate
```

3. Configurer la base de données dans `.env`

4. Exécuter les migrations:
```bash
php artisan migrate
```

5. Démarrer le serveur:
```bash
php artisan serve
```

## Fonctionnalités

### Module 1: Authentification
- Inscription
- Connexion
- Déconnexion
- Réinitialisation du mot de passe

### Module 2: Mesure
- Mesure de bague (avec calibration)
- Mesure de doigt
- Mesure de bracelet
- Calcul automatique des tailles (EU, US, mm)

### Module 3: Historique
- Consultation des mesures sauvegardées
- Modification/Suppression
- Synchronisation avec le serveur

### Module 4: Cours de l'Or
- Graphique d'évolution des prix
- Filtres par période (Jour, Semaine, Mois, Année)

### Module 5: Marketplace
- Catalogue de produits
- Filtres (type, prix, vendeur)
- Détails des produits

## Base de Données

### Tables principales:
- `users`: Utilisateurs (clients et vendeurs)
- `measurements`: Mesures sauvegardées
- `settings`: Préférences utilisateur
- `gold_price_history`: Historique des prix de l'or
- `vendors`: Profils des vendeurs
- `products`: Produits de la marketplace
- `product_images`: Images des produits

## API Endpoints

### Authentification
- `POST /api/auth/register` - Inscription
- `POST /api/auth/login` - Connexion
- `POST /api/auth/logout` - Déconnexion
- `POST /api/auth/forgot-password` - Réinitialisation

### Mesures
- `GET /api/measurements` - Liste des mesures
- `POST /api/measurements` - Créer une mesure
- `PUT /api/measurements/{id}` - Modifier une mesure
- `DELETE /api/measurements/{id}` - Supprimer une mesure

### Cours de l'Or
- `GET /api/gold-prices?karat=18K&period=month` - Prix de l'or

### Produits
- `GET /api/products` - Liste des produits (avec filtres)
- `GET /api/products/{id}` - Détails d'un produit

### Paramètres
- `GET /api/settings` - Récupérer les paramètres
- `PUT /api/settings` - Mettre à jour les paramètres

## Notes de Développement

- L'application fonctionne en mode offline-first: les mesures sont stockées localement et synchronisées au retour de la connexion
- L'authentification utilise Laravel Sanctum avec des tokens
- Les calculs de taille sont basés sur les formules standard EU/US

## Prochaines Étapes (V2)

- Mesure par caméra
- Alertes de prix de l'or
- Gestion des paiements/commandes
- Interface admin pour les vendeurs







