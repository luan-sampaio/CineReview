package com.cinereviewapp.cinereview_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção personalizada lançada quando um recurso (Filme, Review, etc) não é encontrado no banco.
 * <p>
 * A anotação {@code @ResponseStatus} instrui o Spring a retornar automaticamente
 * o status HTTP 404 (Not Found) sempre que essa exceção subir a pilha.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String mensagem) {
        super(mensagem);
    }
}
