package cleancodecleanarchitecture16.payment.application.usecase;

public abstract class VoidUseCase<INPUT> {

    // 1. Cada caso de uso tem um Input e um Output próprio. Não retorna a entidade, o agregado, ou objeto de valor.
    // 2. O caso de uso implementa o padrão Command
    public abstract void execute(INPUT input);
}
