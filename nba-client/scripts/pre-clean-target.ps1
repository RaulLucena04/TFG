# Borra target/ con reintentos para mitigar bloqueos de Windows (OneDrive, antivirus, IDE, java.exe).
param(
    [Parameter(Mandatory = $true)]
    [string] $TargetDir,

    [int] $Retries = 12,
    [int] $DelayMs = 800
)

$ErrorActionPreference = "Stop"

if (-not (Test-Path -LiteralPath $TargetDir)) {
    exit 0
}

for ($i = 0; $i -lt $Retries; $i++) {
    try {
        Remove-Item -LiteralPath $TargetDir -Recurse -Force -ErrorAction Stop
    }
    catch {
        # Archivo o carpeta aun bloqueada; reintentar tras una pausa breve.
    }

    Start-Sleep -Milliseconds $DelayMs
    if (-not (Test-Path -LiteralPath $TargetDir)) {
        exit 0
    }
}

Write-Host ""
Write-Host "No se pudo eliminar completamente: $TargetDir" -ForegroundColor Red
Write-Host "Suele deberse a: carpeta sincronizada con OneDrive, antivirus, Cursor/VS Code con archivos abiertos," -ForegroundColor Yellow
Write-Host "o un proceso java.exe aun en ejecucion. Cierra la app, el IDE y pausa OneDrive en esta ruta si hace falta." -ForegroundColor Yellow
Write-Host ""
exit 1
