
# Configure environment variables
- create .env from .env.example
- create env.json from env.json.example

#  Start essi-oauth
- install dependencies: `pip install -r essi-oauth/requirements-dev.txt -r essi-oauth/requirements.txt`
- run: 'python essi-oauth/app.py'

# Start email-service
- build: `sam build` 
- start: `sam local start-api --env-vars env.json`

# Start essi-client and essi-trx on idea
- first configure environment variables in your IDE
- run redis: `docker-compose up -d rediscache`

# Testing
Test credentials:
* User: 42944634
* Pass: Hola1234!



# Code analisis
- setup sonarqube: `docker-compose up -d sonarqube`
- go to http://localhost:9000 default user and password is *admin*
- generate user token and set on .env in SONAR_LOGIN variable
- run analisis: `docker-compose up -d`
