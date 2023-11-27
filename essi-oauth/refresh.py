import json
import jwt
import config
from trxclient import get_user, get_paciente_by_numero
import oauth


def invalid_token_answer(refresh_token, reason):
  return {
    "error": "invalid_token",
    "error_description": f"Invalid refresh token ({reason}): {refresh_token}"
  }


def handle_refresh_token(refresh_token):
  try:
    decoded = jwt.decode(refresh_token, config.config_security_oauth_jwt_key, algorithms=['HS256'])
  except jwt.exceptions.InvalidSignatureError:
    return json.dumps(invalid_token_answer(refresh_token, 'invalid signature')), 401, {
      'Content-Type': 'application/json'}
  except jwt.exceptions.ExpiredSignatureError:
    return json.dumps(invalid_token_answer(refresh_token, 'expired')), 401, {'Content-Type': 'application/json'}

  username = decoded['user_name']

  usuario = get_user(username)
  if usuario is None:
    return '', 401

  paciente = get_paciente_by_numero(username)
  if paciente is None:
    return '', 401

  return json.dumps(oauth.get_access_token(username, usuario, paciente)), 200, {'Content-Type': 'application/json'}
