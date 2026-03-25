# Quita JavaFX del classpath del launcher (jpackage lo pone en classpath y en module-path).
# Si falta --module-path, lo anade (por si se ejecuto jpackage sin las java-options correctas).
param(
    [Parameter(Mandatory = $true)]
    [string] $CfgPath
)

if (-not (Test-Path -LiteralPath $CfgPath)) {
    Write-Error "No se encuentra el .cfg: $CfgPath"
    exit 1
}

$lines = Get-Content -LiteralPath $CfgPath
$modulePathOpt = 'java-options=--module-path=$APPDIR\javafx'

$out = [System.Collections.Generic.List[string]]::new()
$pastHeader = $false
$addedModulePath = $false

foreach ($line in $lines) {
    if ($line -match 'app\.classpath=.*[\\/]javafx[\\/]') {
        continue
    }
    if ($line -match '^\[JavaOptions\]') {
        [void]$out.Add($line)
        $pastHeader = $true
        continue
    }
    if ($pastHeader -and $line -eq $modulePathOpt) {
        [void]$out.Add($line)
        $addedModulePath = $true
        continue
    }
    if ($pastHeader -and $line -match '^java-options=--module-path($|=)') {
        if (-not $addedModulePath) {
            [void]$out.Add($modulePathOpt)
            $addedModulePath = $true
        }
        continue
    }
    if ($pastHeader -and $line -eq 'java-options=$APPDIR\javafx') {
        continue
    }
    [void]$out.Add($line)
}

if (-not $addedModulePath) {
    $inserted = $false
    $final = [System.Collections.Generic.List[string]]::new()
    foreach ($line in $out) {
        [void]$final.Add($line)
        if (-not $inserted -and $line -eq '[JavaOptions]') {
            [void]$final.Add($modulePathOpt)
            $inserted = $true
        }
    }
    if (-not $inserted) {
        Write-Error "No se encontro la seccion [JavaOptions] en: $CfgPath"
        exit 1
    }
    $out = $final
    $addedModulePath = $true
}

Set-Content -LiteralPath $CfgPath -Value $out -Encoding utf8
