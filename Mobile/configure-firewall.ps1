# Script pour configurer le pare-feu Windows pour Laravel
Write-Host "=== Configuration du pare-feu Windows ===" -ForegroundColor Green
Write-Host ""

# Vérifier si la règle existe déjà
$existingRule = Get-NetFirewallRule -DisplayName "Laravel Development Server" -ErrorAction SilentlyContinue

if ($existingRule) {
    Write-Host "La règle de pare-feu existe déjà." -ForegroundColor Yellow
    Write-Host "Voulez-vous la supprimer et la recréer? (O/N)" -ForegroundColor Yellow
    $response = Read-Host
    if ($response -eq "O" -or $response -eq "o") {
        Remove-NetFirewallRule -DisplayName "Laravel Development Server"
        Write-Host "Ancienne règle supprimée." -ForegroundColor Green
    } else {
        Write-Host "Configuration annulée." -ForegroundColor Yellow
        exit
    }
}

# Créer la règle de pare-feu
Write-Host "Création de la règle de pare-feu pour le port 8000..." -ForegroundColor Cyan

try {
    New-NetFirewallRule -DisplayName "Laravel Development Server" `
        -Direction Inbound `
        -LocalPort 8000 `
        -Protocol TCP `
        -Action Allow `
        -Profile Private, Domain | Out-Null
    
    Write-Host "✓ Règle de pare-feu créée avec succès!" -ForegroundColor Green
    Write-Host ""
    Write-Host "Le port 8000 est maintenant ouvert pour les connexions entrantes." -ForegroundColor Cyan
} catch {
    Write-Host "Erreur lors de la création de la règle:" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
    Write-Host ""
    Write-Host "Vous pouvez créer la règle manuellement:" -ForegroundColor Yellow
    Write-Host "1. Ouvrez 'Pare-feu Windows Defender avec sécurité avancée'" -ForegroundColor Cyan
    Write-Host "2. Cliquez sur 'Règles de trafic entrant' > 'Nouvelle règle'" -ForegroundColor Cyan
    Write-Host "3. Sélectionnez 'Port' > TCP > Port spécifique: 8000" -ForegroundColor Cyan
    Write-Host "4. Autoriser la connexion > Appliquez à tous les profils" -ForegroundColor Cyan
}

