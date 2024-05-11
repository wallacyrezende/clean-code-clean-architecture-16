package cleancodecleanarchitecture16.account.vo;

import cleancodecleanarchitecture16.account.application.exceptions.BusinessException;
import cleancodecleanarchitecture16.account.domain.vo.Cpf;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CpfTest {

    @Test
    void testValidCpf() {
        assertDoesNotThrow(() -> new Cpf("97456321558"));
    }

    @Test
    void testInvalidCpf() {
        assertThrows(BusinessException.class, () -> new Cpf("123.456.789-01"));
    }

    @Test
    void testNullCpf() {
        assertThrows(BusinessException.class, () -> new Cpf(null));
    }

    @Test
    void testEmptyCpf() {
        assertThrows(BusinessException.class, () -> new Cpf(""));
    }

    @Test
    void testNonNumericCpf() {
        assertThrows(BusinessException.class, () -> new Cpf("123abc456def"));
    }

    @Test
    void testCpfWithTwelveNumbers() {
        assertThrows(BusinessException.class, () -> new Cpf("974563215581"));
    }
}