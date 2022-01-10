$serviceName = "ServiceName"

# verify if the service already exists, and if yes remove it first
if (Get-Service $serviceName -ErrorAction SilentlyContinue)
{
	# using WMI to remove Windows service because PowerShell does not have CmdLet for this
    $serviceToRemove = Get-WmiObject -Class Win32_Service -Filter "name='$serviceName'"
    $serviceToRemove.delete()
    "service removed"
}
else
{
	# just do nothing
    "service does not exists"
}

"installing service"
# creating credentials which can be used to run my windows service
$secpasswd = ConvertTo-SecureString "MyPa$$word" -AsPlainText -Force
$mycredentials = New-Object System.Management.Automation.PSCredential (".\MyUserName", $secpasswd)
# The below code will opens the dialog box and asks to enter the username and password to create the new service
# $mycredentials = Get-Credential 
$binaryPath = "c:\myServiceBinaries\MyService.exe"
# creating widnows service using all provided parameters
New-Service -name $serviceName -binaryPathName $binaryPath -displayName $serviceName -startupType Automatic -credential $mycredentials

"installation complited"
