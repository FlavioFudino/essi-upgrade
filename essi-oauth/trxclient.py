import config
import requests


def get_user(username):
  url = f"{config.base_trx_path_url}/usuarios"
  params = {
    "username": username
  }
  ans = requests.get(url, params=params, timeout=config.trx_service_timeout).json()
  return ans['data']


def get_paciente_by_numero(username):
  url = f"{config.base_trx_path_url}/paciente/getPacienteByNumero"
  body = {
    "numeroDocIdent": username
  }
  ans = requests.post(url, json=body, timeout=config.trx_service_timeout).json()
  return ans['data']
