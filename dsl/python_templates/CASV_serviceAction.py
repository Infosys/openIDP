## Python script to Start/Stop CA Virtualised Service existing in CA DevTest Server
import requests
import os
import ast
import base64
from xml.etree import ElementTree as ET

default_value = 'NA'
# os.environ.get('someENVKey') #returns `None` if key not in env variable
#print(os.getenv('somKey', default_value))
#print(os.environ['someKey']) #gives error if key not in env variable
#url = 'http://dummyHost-23:1505/api/Dcm/VSEs/VSE/LisaBankDemo1/actions/start/'
#serviceName = 'LisaBankDemo1'
# basic authorization Base64 encoding
default_cred = 'YWRtaW46YWRtaW4='
admin='admin'
# Required arguments to set for the script
#os.environ['CA_USER'] = admin
#os.environ['CA_PASS'] = admin
#os.environ['ACTION_STRING'] = 'start'
#os.environ['SERVICES_LIST'] = '["LisaBankDemo1","Bank_555"]'
#os.environ['SERVER_URL'] = 'http://dummyHost-23:1505'

serverUrl = os.getenv('SERVER_URL',default_value)
apiUrl = 'api/Dcm/VSEs/VSE/' if (serverUrl[-1] == '/') else '/api/Dcm/VSEs/VSE/'
#print (apiUrl)
actionsStr = '/actions/'
startStr = 'start'
stopStr = 'stop'
# basic authorization Base64 encoding
user = os.getenv('CA_USER',admin)
passwd = os.getenv('CA_PASS',admin)
cred = user+':'+passwd
cred = base64.b64encode(cred.encode()).decode()
if(cred != default_cred):
	#cred = base64.b64encode(bytes(cred,'utf-8')).decode('ascii')
	cred = base64.b64encode(cred.encode()).decode()
authStr = 'Basic '+cred
#authStr = str(authStr)
#headers = {'Authorization':authStr}
headers = { 'Authorization' : authStr }
#print (headers)
action = os.getenv('ACTION_STRING',default_value)
servicesListStr = os.getenv('SERVICES_LIST',default_value)

print ('Service List str: ',servicesListStr)
#str = "['one','two','three' , 'four']"
servicesList = ast.literal_eval(servicesListStr)
# servicesList = [service.strip() for service in servicesList]
print ('Service List: ',servicesList)



def sendRequest(actStr,serviceName):
	requestURL = serverUrl+apiUrl+serviceName+actionsStr+actStr
	print('Request URL: '+requestURL)
	response = requests.post(requestURL,headers=headers)
	# response = requests.post(requestURL)
	print ("Response [status_code] for Service "+serviceName+": ",response.status_code)
	root = ET.fromstring(response.content)
	#print (root)
	#print (root.tag.split('}', 1)[1])
	# child = root.iterfind('Message')
	# print(child)
	# .iter('*')
	# for child in root.iter('*'):
		# if (action == startStr and child.text.find('virtual service is already running') != -1):
			# print(child.tag, child.attrib, child.text)
			# break
		# elif (action == stopStr and child.text.find('virtual service is not running') != -1):
			# print(child.tag, child.attrib, child.text)
			# break
	return root

def servicesStart(actStr):
	for service in servicesList:
		root = sendRequest(actStr,service)
		rootElementString = root.tag.split('}', 1)[1]
		if(rootElementString == 'VirtualService'):
			for child in root.iter('*'):
				elem = child.tag.split('}', 1)[1]
				txt = str(child.text)
				if (elem == 'ModelName' and txt.find(service) != -1):
					print("Started Service: ",service)
					break
		elif (rootElementString == 'Error'):
			for child in root.iter('*'):
				elem = child.tag.split('}', 1)[1]
				txt = str(child.text)
				if (elem == 'Message' and txt.find('virtual service is already running') != -1):
					print("Service '", service, "' Already Running!")
					break
	return

def servicesStop(actStr):
	for service in servicesList:
		root = sendRequest(actStr,service)
		rootElementString = root.tag.split('}', 1)[1]
		if(rootElementString == 'VirtualService'):
			for child in root.iter('*'):
				elem = child.tag.split('}', 1)[1]
				txt = str(child.text)
				if (elem == 'ModelName' and txt.find(service) != -1):
					print("Stopped Service: ",service)
					break
		elif (rootElementString == 'Error'):
			for child in root.iter('*'):
				elem = child.tag.split('}', 1)[1]
				txt = str(child.text)
				if (elem == 'Message' and txt.find('virtual service is not running') != -1):
					print("Service '", service, "' Already Stopped!")
					break
	return


# Start Execution Point
if(action == startStr):
	servicesStart(action)
elif (action == stopStr):
	servicesStop(action)


