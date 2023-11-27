package gob.pe.essalud.trx.common.util;

import gob.pe.essalud.trx.common.constants.TipoRiesgoDiabetes;

import java.text.DecimalFormat;

public abstract class RiesgoDiabetesUtil {

    public static int getTipoRespuesta(int valor) {
        if (valor >= 1 && valor <= 3) { //opciones posibles (1,2,3)
            return 2;
        }
        return 1;
    }

    public static int getPuntosPregunta(int preguntaNum, int valor, String genero) {
        if (preguntaNum == 1)
            return getPuntosPregunta01(valor);
        else if (preguntaNum == 2)
            return getPuntosPregunta02(valor);
        else if (preguntaNum == 3)
            return getPuntosPregunta03(valor, genero);
        else if (preguntaNum == 4)
            return getPuntosPregunta04(valor);
        else if (preguntaNum == 5)
            return getPuntosPregunta05(valor);
        else if (preguntaNum == 6)
            return getPuntosPregunta06(valor);
        else if (preguntaNum == 7)
            return getPuntosPregunta07(valor);
        else if (preguntaNum == 8)
            return getPuntosPregunta08(valor);
        return 0;
    }

    public static int getTipoRiesgo(int puntos) {
        int tipoRiesgo = 0;
        if (puntos < 7)
            tipoRiesgo = TipoRiesgoDiabetes.BAJO;
        else if (puntos <= 11)
            tipoRiesgo = TipoRiesgoDiabetes.LIGERAMENTE_AUMENTADO;
        else if (puntos <= 14)
            tipoRiesgo = TipoRiesgoDiabetes.MODERADO;
        else if (puntos <= 20)
            tipoRiesgo = TipoRiesgoDiabetes.ALTO;
        else
            tipoRiesgo = TipoRiesgoDiabetes.MUY_ALTO;
        return tipoRiesgo;
    }

    public static double calcularIMC(int peso, int talla) {
        // Convertir la altura a metros (de centímetros a metros)
        double alturaMetros = talla / 100.0;

        // Calcular el IMC
        double imc = peso / (alturaMetros * alturaMetros);

        // Tomar solo 2 decimales
        DecimalFormat df = new DecimalFormat("#.##");
        imc = Double.parseDouble(df.format(imc));

        return imc;
    }

    // -------------------------------------------------------------------------

    /* 1. ¿Cuántos años tiene usted? */
    private static int getPuntosPregunta01(int edad) {
        int puntos = 0;
        if (edad >= 45 && edad <= 54)
            puntos = 2;
        else if (edad >= 55 && edad <= 64)
            puntos = 3;
        else if (edad > 64)
            puntos = 4;
        return puntos;
    }

    /* 2. ¿Cuál es su índice de Masa Corporal (IMC)? */
    private static int getPuntosPregunta02(int imc) {
        int puntos = 0;
        if (imc >= 25 && imc <= 30)
            puntos = 1;
        else if (imc > 30)
            puntos = 3;
        return puntos;
    }

    /* 3. ¿Cuánto mide su cintura? */
    private static int getPuntosPregunta03(int cintura, String genero) {
        int puntos = 0;

        if (genero.equals("M")) {
            if (cintura >= 94 && cintura <= 102)
                puntos = 3;
            else if (cintura > 102)
                puntos = 4;
        }
        else {
            if (cintura >= 80 && cintura <= 88)
                puntos = 3;
            else if (cintura > 88)
                puntos = 4;
        }

        return puntos;
    }

    /* 4. ¿Realiza habitualmente al menos 30 minutos de actividad fisica, en el trabajo y/o en el tiempo libre? */
    private static int getPuntosPregunta04(int opcion) {
        int puntos = 0;
        if (opcion == 2)
            puntos = 2;
        return puntos;
    }

    /* 5. ¿Con qué frecuencia come verduras o frutas? */
    private static int getPuntosPregunta05(int opcion) {
        int puntos = 0;
        if (opcion == 2)
            puntos = 1;
        return puntos;
    }

    /* 6. ¿Toma medicación para la presión alta o padece de Hipertensión Arterial? */
    private static int getPuntosPregunta06(int opcion) {
        int puntos = 0;
        if (opcion == 2)
            puntos = 2;
        return puntos;
    }

    /* 7. ¿Le han encotrado alguna vez valores de glucosa altos( por ejemplo, en un control médico o durante una enfermedad o durante el embarazo? */
    private static int getPuntosPregunta07(int opcion) {
        int puntos = 0;
        if (opcion == 2)
            puntos = 5;
        return puntos;
    }

    /* 8. ¿Se le ha diagnosticado diabetes (tipo 1 o tipo 2) a alguno de sus familiares o parientes? */
    private static int getPuntosPregunta08(int opcion) {
        int puntos = 0;
        if (opcion == 2)
            puntos = 3;
        else if (opcion == 3)
            puntos = 5;
        return puntos;
    }

}
