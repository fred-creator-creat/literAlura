package com.literatura.challenge_2_back_literatura.config.iConfig;

public interface IConverterDados {

    <T> T converterDadosJsonParaJava(String json , Class<T> classe);

}
