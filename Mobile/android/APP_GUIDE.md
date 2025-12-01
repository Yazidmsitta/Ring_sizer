# Guide de l'Application Android - Ring Sizer

## 📱 Structure de l'Application

L'application est maintenant complète avec toutes les interfaces fonctionnelles :

### ✅ Interfaces Implémentées

1. **Authentification** (`LoginActivity`)
   - Inscription
   - Connexion
   - Réinitialisation du mot de passe
   - Redirection automatique si non connecté

2. **Mesure** (`MeasureFragment`)
   - Mesure de bague avec calibration par pièce
   - Mesure de doigt avec circonférence
   - Mesure de bracelet avec circonférence
   - Calcul automatique des tailles (EU, US, mm)
   - Sauvegarde des mesures

3. **Historique** (`HistoryFragment`)
   - Liste de toutes les mesures sauvegardées
   - Synchronisation avec le serveur
   - Modification/Suppression

4. **Cours de l'Or** (`GoldPriceFragment`)
   - Graphique interactif avec MPAndroidChart
   - Filtres par période (Jour, Semaine, Mois, Année)
   - Affichage des prix en temps réel

5. **Marketplace** (`MarketplaceFragment`)
   - Liste des produits avec RecyclerView
   - Affichage des images avec Glide
   - Détails des produits
   - Contact avec les vendeurs

6. **Paramètres** (`SettingsFragment`)
   - Informations utilisateur
   - Déconnexion

## 🔧 Configuration

### 1. URL de l'API

Modifiez `RetrofitClient.kt` :
```kotlin
private const val BASE_URL = "http://VOTRE_IP:8000/api/"
```

Pour trouver votre IP locale :
- Windows : `ipconfig` (chercher IPv4)
- Mac/Linux : `ifconfig` ou `ip addr`

### 2. Compilation

1. Ouvrir le projet dans Android Studio
2. Synchroniser Gradle (File → Sync Project with Gradle Files)
3. Compiler et exécuter sur un appareil/émulateur

## 📋 Fonctionnalités Détaillées

### Mesure de Bague
1. Cliquer sur "Mesurer une bague"
2. Entrer le diamètre de la pièce de référence (ex: 25.75 mm pour une pièce de 2€)
3. Placer la bague sur l'écran et mesurer son diamètre
4. Entrer un nom pour la mesure
5. Cliquer sur "Calculer"
6. Voir les résultats (diamètre, circonférence, tailles EU/US)
7. Sauvegarder la mesure

### Mesure de Doigt/Bracelet
1. Cliquer sur "Mesurer un doigt" ou "Mesurer un poignet"
2. Suivre les instructions (ficelle + règle)
3. Entrer la circonférence en mm
4. Entrer un nom
5. Calculer et sauvegarder

### Synchronisation
- Les mesures sont stockées localement (Room)
- Synchronisation automatique au retour de la connexion
- Bouton de synchronisation manuelle dans l'historique

## 🎨 Design

L'application utilise Material Design avec :
- Thème doré (couleurs primaires : #FFD700, #DAA520)
- Navigation par onglets en bas
- Cards Material pour les listes
- Dialogs Material pour les formulaires

## 🐛 Dépannage

### Erreur de connexion à l'API
- Vérifier que le serveur Laravel est démarré
- Vérifier l'URL dans `RetrofitClient.kt`
- Vérifier les permissions INTERNET dans AndroidManifest

### Erreur de compilation
- Synchroniser Gradle
- Nettoyer le projet (Build → Clean Project)
- Invalider les caches (File → Invalidate Caches)

### Mesures non synchronisées
- Vérifier la connexion internet
- Vérifier que l'utilisateur est connecté
- Utiliser le bouton de synchronisation manuelle

## 📚 Architecture

- **MVVM** : ViewModels pour la logique métier
- **Room** : Base de données locale
- **Retrofit** : Communication API
- **Coroutines** : Tâches asynchrones
- **LiveData** : Observation des données
- **Navigation Component** : Navigation entre écrans

## 🚀 Prochaines Étapes (V2)

- Mesure par caméra avec calibration automatique
- Alertes de prix de l'or
- Filtres avancés pour la marketplace
- Paiement intégré
- Notifications push

