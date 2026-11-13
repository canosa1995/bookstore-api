package br.com.bookstore.bookstore_api.application.usecase;

public interface IUseCase<P, R> {

  R execute(P command);
}
