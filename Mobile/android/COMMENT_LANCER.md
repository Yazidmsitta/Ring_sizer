# 🚀 Comment Lancer l'Application Android

## Prérequis

1. **Android Studio** installé (version récente)
2. **JDK 17** ou supérieur
3. **Android SDK** (API 26 minimum)
4. **Émulateur Android** ou **appareil physique** connecté

## Étapes pour Lancer l'Application

### 1. Ouvrir le Projet dans Android Studio

1. Ouvrir **Android Studio**
2. Cliquer sur **"Open"** ou **"File → Open"**
3. Naviguer vers le dossier `android` de votre projet
4. Sélectionner le dossier `android` et cliquer sur **"OK"**

### 2. Attendre la Synchronisation Gradle

- Android Studio va automatiquement synchroniser les dépendances
- Attendre que la barre de progression en bas soit terminée
- Si des erreurs apparaissent, cliquer sur **"Sync Now"**

### 3. Configurer l'URL de l'API

**IMPORTANT** : Avant de lancer, configurer l'URL de votre serveur Laravel.

1. Ouvrir le fichier : `app/src/main/java/com/ringsize/app/data/remote/RetrofitClient.kt`
2. Modifier la ligne :
   ```kotlin
   private const val BASE_URL = "http://VOTRE_IP:8000/api/"
   ```
3. Remplacer `VOTRE_IP` par votre adresse IP locale

**Pour trouver votre IP :**
- Windows : Ouvrir PowerShell et taper `ipconfig` → chercher "IPv4"
- Exemple : `192.168.1.100`

### 4. Démarrer le Serveur Laravel

Dans un terminal, lancer :
```bash
cd laravel
php artisan serve
```

Le serveur doit être accessible sur `http://localhost:8000` ou `http://VOTRE_IP:8000`

### 5. Lancer l'Application

#### Option A : Sur un Émulateur

1. Cliquer sur l'icône **"Device Manager"** (ou **Tools → Device Manager**)
2. Créer un nouvel appareil virtuel si nécessaire :
   - Cliquer sur **"Create Device"**
   - Choisir un modèle (ex: Pixel 5)
   - Choisir une image système (API 26 ou supérieur)
   - Finaliser la création
3. Démarrer l'émulateur
4. Dans Android Studio, cliquer sur le bouton **▶️ Run** (ou **Shift + F10**)
5. Sélectionner l'émulateur dans la liste

#### Option B : Sur un Appareil Physique

1. Activer le **Mode Développeur** sur votre téléphone :
   - Aller dans **Paramètres → À propos du téléphone**
   - Appuyer 7 fois sur **"Numéro de build"**
2. Activer le **Débogage USB** :
   - **Paramètres → Options pour les développeurs → Débogage USB**
3. Connecter le téléphone à l'ordinateur via USB
4. Autoriser le débogage USB sur le téléphone
5. Dans Android Studio, cliquer sur **▶️ Run**
6. Sélectionner votre appareil dans la liste

### 6. Vérifier que ça Fonctionne

1. L'application devrait s'ouvrir
2. Si vous n'êtes pas connecté, vous verrez l'écran de connexion
3. Créer un compte ou se connecter
4. Tester les différentes fonctionnalités :
   - Mesure de bague
   - Historique
   - Cours de l'or
   - Marketplace

## 🐛 Problèmes Courants

### Erreur : "Could not resolve..."
- **Solution** : Synchroniser Gradle (File → Sync Project with Gradle Files)

### Erreur : "Failed to connect to..."
- **Solution** : 
  - Vérifier que le serveur Laravel est démarré
  - Vérifier l'URL dans `RetrofitClient.kt`
  - Vérifier que l'IP est correcte

### Erreur : "INSTALL_FAILED_INSUFFICIENT_STORAGE"
- **Solution** : Libérer de l'espace sur l'appareil/émulateur

### L'application se ferme au lancement (Crash)
- **Solution** :
  - Vérifier les logs dans **Logcat** (en bas d'Android Studio)
  - Vérifier que toutes les dépendances sont installées
  - Nettoyer le projet : **Build → Clean Project**

### L'émulateur est lent
- **Solution** :
  - Augmenter la RAM allouée dans les paramètres de l'émulateur
  - Utiliser un appareil physique si possible

## 📱 Test Rapide

Une fois l'application lancée :

1. **Inscription** : Créer un compte avec email/mot de passe
2. **Mesure** : Tester la mesure d'une bague
3. **Historique** : Vérifier que la mesure apparaît
4. **Cours de l'or** : Voir le graphique (peut être vide si pas de données)
5. **Marketplace** : Voir la liste des produits (peut être vide si pas de produits)

## ✅ Checklist Avant de Lancer

- [ ] Android Studio installé
- [ ] Projet ouvert dans Android Studio
- [ ] Gradle synchronisé sans erreurs
- [ ] URL de l'API configurée dans `RetrofitClient.kt`
- [ ] Serveur Laravel démarré (`php artisan serve`)
- [ ] Émulateur ou appareil connecté
- [ ] Base de données MySQL configurée et migrations exécutées

## 🎯 Commandes Utiles

### Nettoyer le projet
```
Build → Clean Project
```

### Reconstruire le projet
```
Build → Rebuild Project
```

### Voir les logs
```
View → Tool Windows → Logcat
```

### Invalider les caches
```
File → Invalidate Caches → Invalidate and Restart
```

