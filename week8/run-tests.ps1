$ErrorActionPreference = 'Stop'
foreach ($folder in Get-ChildItem -LiteralPath $PSScriptRoot -Directory | Where-Object { $_.Name -match '^0[1-3]-' }) {
    $build = Join-Path $folder.FullName 'build'
    New-Item -ItemType Directory -Path $build -Force | Out-Null
    & javac -encoding UTF-8 --release 11 -d $build (Join-Path $folder.FullName 'Main.java') (Join-Path $folder.FullName 'TestCases.java')
    if ($LASTEXITCODE -ne 0) { throw "Compilation failed: $($folder.Name)" }
    & java -cp $build TestCases
    if ($LASTEXITCODE -ne 0) { throw "Tests failed: $($folder.Name)" }
}
