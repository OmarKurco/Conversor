package com.example.currencyconverter;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class CurrencyConverter {
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/USD";
    private static final String API_KEY = "969a580d3446d46556b92d0e";

    public static void main(String[] args) {
        try {
            CurrencyConverter converter = new CurrencyConverter();
            double cantidadEnUsd = 100;
            double cantidadEnArs = converter.convertir("USD", "ARS", cantidadEnUsd);
            double cantidadEnBob = converter.convertir("USD", "BOB", cantidadEnUsd);
            double cantidadEnBrl = converter.convertir("USD", "BRL", cantidadEnUsd);

            System.out.println(cantidadEnUsd + " USD en ARS: " + cantidadEnArs);
            System.out.println(cantidadEnUsd + " USD en BOB: " + cantidadEnBob);
            System.out.println(cantidadEnUsd + " USD en BRL: " + cantidadEnBrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double convertir(String desdeMoneda, String haciaMoneda, double cantidad) throws IOException {
        OkHttpClient cliente = new OkHttpClient();

        Request solicitud = new Request.Builder()
                .url(API_URL + "?access_key=" + API_KEY)
                .build();

        Response respuesta = cliente.newCall(solicitud).execute();
        if (!respuesta.isSuccessful()) throw new IOException("Código inesperado " + respuesta);

        String datosRespuesta = respuesta.body().string();
        JSONObject json = new JSONObject(datosRespuesta);
        double tasaDeCambio = json.getJSONObject("rates").getDouble(haciaMoneda);

        return cantidad * tasaDeCambio;
    }
}
