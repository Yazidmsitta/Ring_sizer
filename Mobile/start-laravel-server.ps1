# Script pour démarrer le serveur Laravel accessible depuis le téléphone
Write-Host "=== Démarrage du serveur Laravel ===" -ForegroundColor Green
Write-Host ""

# Obtenir l'adresse IP locale
$ipAddress = (Get-NetIPAddress -AddressFamily IPv4 | Where-Object { $_.InterfaceAlias -notlike "*Loopback*" -and $_.IPAddress -notlike "169.254.*" } | Select-Object -First 1).IPAddress

if ($null -eq $ipAddress) {
    Write-Host "Impossible de trouver l'adresse IP. Utilisation de 0.0.0.0" -ForegroundColor Yellow
    $ipAddress = "0.0.0.0"
} else {
    Write-Host "Adresse IP locale détectée: $ipAddress" -ForegroundColor Cyan
}

Write-Host ""
Write-Host "Le serveur sera accessible sur:" -ForegroundColor Yellow
Write-Host "  - Local: http://127.0.0.1:8000" -ForegroundColor Cyan
Write-Host "  - Réseau: http://$ipAddress:8000" -ForegroundColor Cyan
Write-Host ""
Write-Host "Assurez-vous que votre téléphone est sur le même réseau WiFi!" -ForegroundColor Yellow
Write-Host ""
Write-Host "Appuyez sur Ctrl+C pour arrêter le serveur" -ForegroundColor Gray
Write-Host ""

# Changer vers le répertoire Laravel
Set-Location -Path "laravel"

# Démarrer le serveur Laravel sur toutes les interfaces
php artisan serve --host=0.0.0.0 --port=8000

