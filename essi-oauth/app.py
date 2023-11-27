import traceback
from flask import request
from flask_lambda import FlaskLambda
import login
import refresh
import base64
import config

app = FlaskLambda(__name__)


@app.route('/', methods=['GET'])
def index():
  return 'Essi-Oauth'


@app.route('/oauth/token', methods=['POST'])
def list_scrappers():
  authorization = request.headers.get('Authorization')
  if authorization is None:
    return '', 401
  if not authorization.startswith('Basic '):
    return '', 401
  authorization = authorization.replace('Basic ', '')
  a, b = base64.b64decode(authorization).decode('utf-8').split(':')
  if a != config.config_security_oauth_client_id or b != config.config_security_oauth_client_secret:
    return '', 401

  data = request.form
  if 'grant_type' not in data:
    return 'grant_type is required', 400
  if data['grant_type'] == 'password':
    if 'username' not in data or 'password' not in data:
      return 'username is required', 400
  elif data['grant_type'] == 'refresh_token':
    if 'refresh_token' not in data:
      return 'refresh_token is required', 400
  else:
    return 'grant_type is invalid', 400

  if data['grant_type'] == 'password':
    return login.handle_login(data['username'], data['password'])

  return refresh.handle_refresh_token(data['refresh_token'])


@app.errorhandler(Exception)
def all_exception_handler(error):
  print(error)
  return 'Error', 200


def handler(event, context):
  try:
    return app(event, context)
  except Exception as e:
    traceback.print_exc()
    return {
      'statusCode': 200,
      'body': ''
    }


if __name__ == '__main__':
  app.run(debug=True)
