# Quita JavaFX del classpath del launcher de jpackage y anade --module-path hacia la carpeta javafx.
param(
    [Parameter(Mandatory = $true)]
    [string] $CfgPath
)

if (-not (Test-Path -LiteralPath $CfgPath)) {
    Write-Error "No se encuentra el .cfg: $CfgPath"
    exit 1
}

$lines = Get-Content -LiteralPath $CfgPath
$already = ($lines | Where-Object { $_ -eq 'java-options=--module-path' }).Count -ge 1 `
    -and ($lines | Where-Object { $_ -eq 'java-options=$APPDIR\javafx' }).Count -ge 1
if ($already) {
    exit 0
}

$out = [System.Collections.Generic.List[string]]::new()
$pastHeader = $false

foreach ($line in $lines) {
    if ($line -match 'app\.classpath=.*[\\/]javafx[\\/]') {
        continue
    }
    if ($line -match '^\[JavaOptions\]') {
        [void]$out.Add($line)
        [void]$out.Add('java-options=--module-path')
        [void]$out.Add('java-options=$APPDIR\javafx')
        $pastHeader = $true
        continue
    }
    if ($pastHeader -and $line -match '^java-options=--module-path($|=)') {
        continue
    }
    if ($pastHeader -and $line -eq 'java-options=$APPDIR\javafx') {
        continue
    }
    [void]$out.Add($line)
}

Set-Content -LiteralPath $CfgPath -Value $out -Encoding utf8
