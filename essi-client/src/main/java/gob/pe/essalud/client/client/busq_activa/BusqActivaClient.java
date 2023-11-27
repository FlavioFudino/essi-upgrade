package gob.pe.essalud.client.client.busq_activa;

//@FeignClient(name = "busqactivaservice", url = "${" + Constantes.URL_ENDPOINT_BUSQ_ACTIVA + "}")
public interface BusqActivaClient {

    /*
    @PostMapping(Constantes.URL_ASEGURADO_LOGIN)
    ResponseDto<AseguradoRequestDto> aseguradoLogin(@RequestBody EssiPacienteRequestDto paramInput);

    @PostMapping(Constantes.URL_ASEGURADO)
    ResponseDto<AseguradoRequestDto> saveAseguradoAndSendEssiPaciente(@RequestBody AseguradoRequestDto paramInput);

    @GetMapping(Constantes.URL_ASEGURADO + "/{numDocIdent}")
    AseguradoRequestDto getAseguradoByNumero(@PathVariable String numDocIdent);

    @GetMapping(Constantes.URL_CONDICION)
    Object getCondicion(@Valid @RequestParam String tipoDocumento,
                        @Valid @RequestParam String numeroDocumento);

    @PostMapping(Constantes.URL_CONDICION)
    Object saveCondicion(@RequestBody CondicionHogarRegisterDto data);

    @PostMapping(Constantes.URL_CONDICION_SALUD)
    Object saveCondicionSalud(@RequestBody CondicionSaludRegisterDto data);

    @GetMapping(Constantes.URL_FAMILIAR + "/{idFamiliar}")
    Object getFamiliar(@PathVariable Integer idFamiliar);

    @PostMapping(Constantes.URL_FAMILIAR)
    Object saveFamiliar(@RequestBody Object data);

    @GetMapping(Constantes.URL_FAMILIAR)
    Object familiaList(@Valid @RequestParam String tipoDocumento,
                       @Valid @RequestParam String numeroDocumento);


    @PostMapping(Constantes.URL_FAMILIAR_DELETE)
    Object deleteFamiliar(@Valid @RequestParam String tipoDocumento,
                          @Valid @RequestParam String numeroDocumento,
                          @Valid @RequestParam Integer idFamiliar);

    @GetMapping(Constantes.URL_PARAMETROS)
    Map parametros(@Valid @RequestParam String filters);*/
}
