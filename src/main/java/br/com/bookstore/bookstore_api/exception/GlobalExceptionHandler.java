package br.com.bookstore.bookstore_api.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarErrosValidacao(
        MethodArgumentNotValidException exception) {

        Map<String, String> erros = new LinkedHashMap<>();

        exception.getBindingResult()
        .getFieldErrors()
        .forEach(error ->
            erros.put(
                error.getField(),
                error.getDefaultMessage()
        )
    );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erros);
    }

    @ExceptionHandler(LivroNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> tratarLivroNaoEncontrado(
        LivroNaoEncontradoException exception) {

            Map<String, Object> erro = new LinkedHashMap<>();

            erro.put("status", HttpStatus.NOT_FOUND.value());
            erro.put("erro", "Not Found");
            erro.put("mensagem", exception.getMessage());

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(erro);
        }
    
}
