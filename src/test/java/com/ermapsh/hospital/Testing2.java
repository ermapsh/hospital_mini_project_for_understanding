package com.ermapsh.hospital;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class Testing2 {

    private int sum(int a, int b){
        return a+b;
    }
    @Test
    public void testingAssertion(){
        assertThat( sum(5,3)).isEqualTo(8);
//        assertThat( sum(5,3)).isEqualTo(9);

//        assertThat("apple").startsWith("ap").endsWith("l").hasSize(3); // will throw error
//    Assertions.assertThatThrownBy(()->  divideTwoNumbers(5,0)).isInstanceOf(NullPointerException.class).hasMessage("divided by 0");

    }

    double divideTwoNumbers(int a, int b){
        try {
            return a/b;
        } catch (Exception e) {
            log.error("error got=" + e.getMessage());
            throw new ArithmeticException(e.getLocalizedMessage());
        }
    }


}
