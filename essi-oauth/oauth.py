import jwt
import config
import uuid
import time


def get_access_token(username, usuario, paciente):
  jti = str(uuid.uuid4())
  payload = {
    "tipo_documento": paciente['tipoDocIdent'],
    "numero_documento": paciente['numeroDocIdent'],
    "user_name": username,
    "scope": ["read", "write"],
    "exp": int(time.time()) + config.token_access_seconds,
    "authorities": ["ROLE_USER"],
    "jti": jti,
    "client_id": config.config_security_oauth_client_id
  }
  access_token = jwt.encode(payload, config.config_security_oauth_jwt_key, algorithm='HS256')

  payload['exp'] = int(time.time()) + config.token_refresh_seconds
  payload['jti'] = str(uuid.uuid4())
  payload["ati"] = str(uuid.uuid4())
  refresh_token = jwt.encode(payload, config.config_security_oauth_jwt_key, algorithm='HS256')

  return {
    "token_type": "bearer",
    "expires_in": config.token_access_seconds,
    "scope": "read write",
    "tipo_documento": paciente['tipoDocIdent'],
    "numero_documento": paciente['numeroDocIdent'],
    "access_token": access_token,
    "refresh_token": refresh_token,
    "jti": jti
  }
