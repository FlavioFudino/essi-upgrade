import os
import requests
import base64

if os.path.exists('.env'):
  from dotenv import load_dotenv

  load_dotenv()

APP_NAME = "essi-oauth"
CONFIG_SERVER_URL = os.environ['CONFIG_SERVER_URL']
PROFILES = os.environ['PROFILES']
CONFIG_SERVER_USER = os.environ.get("CONFIG_SERVER_USER", "")
CONFIG_SERVER_PASSWORD = os.environ.get("CONFIG_SERVER_PASSWORD", "")

CONFIG = {}


def load_config():
  credentials = f"{CONFIG_SERVER_USER}:{CONFIG_SERVER_PASSWORD}"
  credentials = base64.b64encode(credentials.encode('utf-8')).decode('utf-8')
  url = f'{CONFIG_SERVER_URL}/{APP_NAME}/{PROFILES}'
  print("Loading config from:", url)
  config = requests.get(url, headers={"Authorization": f"Basic {credentials}"}).json()
  for profile in config['propertySources']:
    for key, value in profile['source'].items():
      if key in CONFIG:
        continue
      CONFIG[key] = value
  print("Config loaded.")


load_config()

base_trx_path_url = CONFIG["base.trx.path.url"]
token_access_seconds = int(CONFIG["token.access.seconds"])
token_refresh_seconds = int(CONFIG["token.refresh.seconds"])
config_security_oauth_jwt_key = CONFIG["config.security.oauth.jwt.key"]
config_security_oauth_client_id = CONFIG["config.security.oauth.client.id"]
config_security_oauth_client_secret = CONFIG["config.security.oauth.client.secret"]
trx_service_timeout = int(CONFIG.get("trx.service.timeout", "10"))
