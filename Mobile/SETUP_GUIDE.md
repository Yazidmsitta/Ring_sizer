# Guide d'Installation et de Configuration

## Vue d'ensemble

Le projet "Ring Sizer" est maintenant structuré avec :
- **Application Android** (Kotlin, MVVM, Room, Retrofit)
- **Backend API Laravel** (PHP, MySQL, Sanctum)

## Structure du Projet

```
.
├── android/              # Application Android
│   ├── app/
│   │   ├── build.gradle.kts
│   │   └── src/main/
│   │       ├── java/com/ringsize/app/
│   │       │   ├── data/          # Couche de données
│   │       │   ├── ui/             # Interface utilisateur
│   │       │   └── util/           # Utilitaires
│   │       ├── res/                # Ressources Android
│   │       └── AndroidManifest.xml
│   ├── build.gradle.kts
│   └── settings.gradle.kts
└── laravel/             # Backend API
    ├── app/
    │   ├── Http/Controllers/Api/
    │   └── Models/
    ├── database/migrations/
    ├── routes/api.php
    └── composer.json
```

## Installation Android

### Prérequis
- Android Studio (Hedgehog ou plus récent)
- JDK 17 ou supérieur
- Android SDK (API 26 minimum)

### Étapes

1. **Ouvrir le projet**
   ```bash
   cd android
   # Ouvrir dans Android Studio
   ```

2. **Synchroniser Gradle**
   - Android Studio devrait synchroniser automatiquement
   - Sinon : File → Sync Project with Gradle Files

3. **Configurer l'URL de l'API**
   - Ouvrir `app/src/main/java/com/ringsize/app/data/remote/RetrofitClient.kt`
   - Modifier `BASE_URL` avec l'URL de votre serveur Laravel
   - Exemple : `"http://192.168.1.100:8000/api/"` (pour test local)

4. **Compiler et exécuter**
   - Connecter un appareil Android ou démarrer un émulateur
   - Cliquer sur "Run" (Shift+F10)

## Installation Laravel

### Prérequis
- PHP 8.1 ou supérieur
- Composer
- MySQL 5.7 ou supérieur
- Extension PHP : pdo_mysql, mbstring, openssl, tokenizer, xml, json

### Étapes

1. **Installer les dépendances**
   ```bash
   cd laravel
   composer install
   ```

2. **Configurer l'environnement**
   ```bash
   cp .env.example .env
   php artisan key:generate
   ```

3. **Configurer la base de données**
   - Éditer `.env` :
   ```env
   DB_CONNECTION=mysql
   DB_HOST=127.0.0.1
   DB_PORT=3306
   DB_DATABASE=ring_sizer
   DB_USERNAME=root
   DB_PASSWORD=votre_mot_de_passe
   ```

4. **Créer la base de données**
   ```sql
   CREATE DATABASE ring_sizer;
   ```

5. **Exécuter les migrations**
   ```bash
   php artisan migrate
   ```

6. **Démarrer le serveur**
   ```bash
   php artisan serve
   ```
   Le serveur sera accessible sur `http://localhost:8000`

### Configuration CORS (si nécessaire)

Pour permettre les requêtes depuis l'application Android, configurer CORS dans Laravel :

1. Installer le package CORS :
   ```bash
   composer require fruitcake/laravel-cors
   ```

2. Publier la configuration :
   ```bash
   php artisan config:publish cors
   ```

3. Configurer `config/cors.php` pour autoriser toutes les origines en développement

## Configuration de l'API pour Android

### URL de base
Dans `RetrofitClient.kt`, modifier :
```kotlin
private const val BASE_URL = "http://VOTRE_IP:8000/api/"
```

Pour trouver votre IP locale :
- Windows : `ipconfig` (chercher IPv4)
- Mac/Linux : `ifconfig` ou `ip addr`

### Test de connexion
1. Démarrer le serveur Laravel
2. Tester avec Postman ou curl :
   ```bash
   curl -X POST http://localhost:8000/api/auth/register \
     -H "Content-Type: application/json" \
     -d '{"name":"Test","email":"test@test.com","password":"password123","password_confirmation":"password123"}'
   ```

## Fonctionnalités Implémentées

### ✅ Complètes
- Structure du projet Android (MVVM)
- Base de données Room (mesures, paramètres)
- API Retrofit avec tous les endpoints
- Authentification (inscription, connexion, déconnexion)
- Modèles de données (Room entities, API models)
- Repositories avec synchronisation
- ViewModels pour l'authentification et les mesures
- Fragments UI de base (mesure, historique, marketplace, cours de l'or, paramètres)
- Backend Laravel complet :
  - Migrations de base de données
  - Modèles Eloquent
  - Contrôleurs API
  - Routes API
  - Authentification Sanctum

### ⚠️ À compléter (UI)
- Interface de mesure de bague avec calibration
- Interface de mesure de doigt/bracelet
- Graphique du cours de l'or (MPAndroidChart)
- Liste des produits avec RecyclerView
- Détails des produits
- Filtres avancés
- Paramètres utilisateur complets

### ⚠️ À compléter (Backend)
- Récupération automatique du cours de l'or (cron job)
- Gestion des images (upload/storage)
- Interface admin pour les vendeurs
- Envoi d'emails (réinitialisation de mot de passe)

## Prochaines Étapes

1. **Tester l'authentification**
   - Créer un compte depuis l'app
   - Vérifier la connexion

2. **Implémenter l'UI de mesure**
   - Créer une vue avec calibration par pièce
   - Implémenter le calcul de diamètre depuis l'écran

3. **Ajouter le graphique du cours de l'or**
   - Utiliser MPAndroidChart
   - Récupérer les données depuis l'API

4. **Compléter la marketplace**
   - Liste des produits
   - Détails avec images
   - Filtres

5. **Synchronisation**
   - Tester la synchronisation offline/online
   - Gérer les conflits

## Notes Importantes

- **Sécurité** : En production, utiliser HTTPS
- **Base URL** : Ne pas commiter l'URL de production dans le code
- **Tokens** : Les tokens Sanctum sont stockés dans SharedPreferences
- **Offline-first** : Les mesures sont stockées localement et synchronisées automatiquement

## Dépannage

### Erreur de connexion Android → Laravel
- Vérifier que le serveur Laravel est démarré
- Vérifier l'URL dans RetrofitClient
- Vérifier les permissions INTERNET dans AndroidManifest
- Vérifier CORS si erreur 405/401

### Erreur de base de données
- Vérifier les credentials dans `.env`
- Vérifier que MySQL est démarré
- Exécuter `php artisan migrate:fresh` pour réinitialiser

### Erreur de compilation Android
- Synchroniser Gradle
- Nettoyer le projet : Build → Clean Project
- Invalider les caches : File → Invalidate Caches

