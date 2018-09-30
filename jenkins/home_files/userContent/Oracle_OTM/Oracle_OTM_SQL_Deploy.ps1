param
(
    [Parameter(Mandatory)]
    $SqlFolder,
	[Parameter(Mandatory)]
    $Username,
	[Parameter(Mandatory)]
    $Password,
	[Parameter(Mandatory)]
    $DbHost,
	[Parameter(Mandatory)]
    $DbPort,
	[Parameter(Mandatory)]
    $SID
   
)

$test=@()

$test= Get-ChildItem -Path $SqlFolder -Filter "*.sql" -Recurse | Select-object -ExpandProperty FullName

$test_length=$test.Length

if ($test[0] -Notlike "*.sql*")
{	
		$file=Write-Output $test
		$filepath="@$file"
		Write-Output $filepath
				
		$check="sql $Username/$Password@${DbHost}:${DbPort}:${SID} $filepath"
		iex $check
}

For ($i=0; $i -lt $test_length; $i++)
{
	if ($test[$i] -like "*.sql*")
	{
		if ($test[$i] -like "*SPEC*")
		{
				$file=Write-Output $test[$i]
				$filepath="@$file"
				Write-Output $filepath
				
				$check="sql $Username/$Password@${DbHost}:${DbPort}:${SID} $filepath"
				iex $check
		}
	}
}
For ($i=0; $i -lt $test_length; $i++)
{
	if ($test[$i] -like "*.sql*")
	{
		if ($test[$i] -like "*BODY*")
		{
				$file=Write-Output $test[$i]
				$filepath="@$file"
				Write-Output $filepath
				$check="sql $Username/$Password@${DbHost}:${DbPort}:${SID} $filepath"
				iex $check
		}
	}
}
For ($i=0; $i -lt $test_length; $i++)
{
	if ($test[$i] -like "*.sql*")
	{
		if ($test[$i] -like "*SYN*")
		{
				$file=Write-Output $test[$i]
				$filepath="@$file"
				Write-Output $filepath
				$check="sql $Username/$Password@${DbHost}:${DbPort}:${SID} $filepath"
				iex $check
		}
	}
}
For ($i=0; $i -lt $test_length; $i++)
{
	if ($test[$i] -like "*.sql*")
	{

		if ($test[$i] -like "*GRANTS*")
		{
				$file=Write-Output $test[$i]
				$filepath="@$file"
				Write-Output $filepath
				$check="sql $Username/$Password@${DbHost}:${DbPort}:${SID} $filepath"
				iex $check
		}
	}
	
}

