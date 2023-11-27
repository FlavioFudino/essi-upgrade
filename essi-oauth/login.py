import json
import bcrypt
from trxclient import get_user, get_paciente_by_numero
import oauth

invalid_credentials_answer = {
  "error": "invalid_grant",
  "error_description": "Bad credentials"
}
user_not_exists_answer = {
  "error": "unauthorized",
  "error_description": "Error en el login, no existe el usuario &#39;42944634x&#39; en el sistema"
}


def handle_login(username, password):
  usuario = get_user(username)
  if usuario is None:
    return json.dumps(user_not_exists_answer), 401, {'Content-Type': 'application/json'}
  if not bcrypt.checkpw(password.encode('utf-8'), usuario['password'].encode('utf-8')):
    return json.dumps(invalid_credentials_answer), 400, {'Content-Type': 'application/json'}

  paciente = get_paciente_by_numero(username)
  if paciente is None:
    return json.dumps(user_not_exists_answer), 401, {'Content-Type': 'application/json'}

  return json.dumps(oauth.get_access_token(username, usuario, paciente)), 200, {'Content-Type': 'application/json'}
