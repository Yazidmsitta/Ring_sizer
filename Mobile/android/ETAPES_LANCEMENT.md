# 🚀 Étapes pour Lancer l'Application Android

## ⚠️ IMPORTANT : Configuration Avant le Lancement

### 1. Configurer l'URL de l'API

**Pour l'émulateur Android :**
- Utiliser : `http://10.0.2.2:8000/api/` (déjà configuré)
- `10.0.2.2` est l'adresse spéciale qui pointe vers `localhost` de votre PC depuis l'émulateur

**Pour un appareil physique :**
1. Trouver votre IP locale :
   - Windows : Ouvrir PowerShell → `ipconfig` → chercher "IPv4"
   - Exemple : `192.168.1.100`
2. Modifier `RetrofitClient.kt` :
   ```kotlin
   private const val BASE_URL = "http://192.168.1.100:8000/api/"
   ```
3. S'assurer que le téléphone et l'ordinateur sont sur le même réseau WiFi

### 2. Démarrer le Serveur Laravel

Dans un terminal PowerShell :
```powershell
cd laravel
php artisan serve --host=0.0.0.0 --port=8000
```

Le `--host=0.0.0.0` permet d'accepter les connexions depuis le réseau local.

## 📱 Lancer l'Application dans Android Studio

### Étape 1 : Ouvrir le Projet

1. Ouvrir **Android Studio**
2. **File → Open**
3. Naviguer vers le dossier `android` (pas le dossier racine)
4. Sélectionner le dossier `android` et cliquer **OK**

### Étape 2 : Attendre la Synchronisation

- Android Studio va automatiquement télécharger les dépendances
- Attendre que la barre de progression en bas soit terminée
- Si des erreurs, cliquer sur **"Sync Now"**

### Étape 3 : Choisir un Appareil

#### Option A : Émulateur (Recommandé pour débuter)

1. Cliquer sur **Device Manager** (icône téléphone en haut à droite)
2. Si aucun émulateur :
   - Cliquer **Create Device**
   - Choisir **Pixel 5** ou similaire
   - Choisir une image système (API 26+)
   - Finaliser
3. Cliquer sur **▶️ Play** à côté de l'émulateur pour le démarrer
4. Attendre que l'émulateur démarre complètement

#### Option B : Appareil Physique

1. Activer **Mode Développeur** :
   - Paramètres → À propos du téléphone
   - Appuyer 7 fois sur "Numéro de build"
2. Activer **Débogage USB** :
   - Paramètres → Options développeur → Débogage USB
3. Connecter le téléphone via USB
4. Autoriser le débogage sur le téléphone

### Étape 4 : Lancer l'Application

1. En haut d'Android Studio, à côté de la liste des appareils, cliquer sur **▶️ Run** (ou **Shift + F10**)
2. Sélectionner votre appareil/émulateur
3. Attendre la compilation et l'installation
4. L'application devrait s'ouvrir automatiquement

## ✅ Vérification

Une fois l'application lancée :

1. **Écran de connexion** devrait apparaître
2. **Créer un compte** ou **se connecter**
3. Tester les fonctionnalités :
   - Mesure de bague
   - Historique
   - Cours de l'or
   - Marketplace

## 🐛 Problèmes Fréquents

### "Failed to connect to..."
- ✅ Vérifier que Laravel est démarré : `php artisan serve --host=0.0.2.2 --port=8000`
- ✅ Vérifier l'URL dans `RetrofitClient.kt`
- ✅ Pour appareil physique : vérifier que l'IP est correcte et que le téléphone est sur le même WiFi

### "Could not resolve..."
- ✅ Synchroniser Gradle : **File → Sync Project with Gradle Files**

### L'application crash au démarrage
- ✅ Voir les logs dans **Logcat** (en bas d'Android Studio)
- ✅ Nettoyer : **Build → Clean Project**

### L'émulateur ne démarre pas
- ✅ Vérifier que **HAXM** ou **Hyper-V** est activé
- ✅ Augmenter la RAM allouée dans les paramètres de l'émulateur

## 📝 Checklist Rapide

- [ ] Android Studio ouvert avec le projet `android`
- [ ] Gradle synchronisé (barre en bas terminée)
- [ ] URL API configurée dans `RetrofitClient.kt`
- [ ] Serveur Laravel démarré (`php artisan serve --host=0.0.0.0`)
- [ ] Émulateur ou appareil connecté
- [ ] Application lancée avec **▶️ Run**

## 🎯 Commandes Utiles Android Studio

| Action | Raccourci |
|--------|-----------|
| Lancer l'app | **Shift + F10** |
| Arrêter l'app | **Ctrl + F2** |
| Synchroniser Gradle | **Ctrl + Shift + O** |
| Nettoyer le projet | **Build → Clean Project** |
| Voir les logs | **View → Tool Windows → Logcat** |






