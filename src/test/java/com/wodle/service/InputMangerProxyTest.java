package com.wodle.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.wodle.backend.InputMangerProxy;
import com.wodle.domain.Word;
import com.wodle.testUtils.SystemInOutUtils;
import com.wodle.ui.ViewManagerImpl;
import java.io.OutputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class InputMangerProxyTest implements SystemInOutUtils {

    private InputMangerProxy inputMangerProxy;

    @BeforeEach
    public void init() {
        inputMangerProxy = new InputMangerProxy(new ViewManagerImpl());
    }

    @ParameterizedTest
    @CsvSource(
        value = {
            "abcde: :abcde:false",
            "a8weafa:qwert:qwert:true",
            "Abcde:abcde:abcde:true",
            "abcdef:abcde:abcde:true"
        }, delimiter = ':'
    )
    public void inputWordTest(String input1, String input2, String expect, boolean inputInvalid) {
        //given
        inputSetting(input1 + "\n" + input2);
        OutputStream out = getOutputStream();

        //when
        Word word = inputMangerProxy.inputWord();

        //then
        assertAll(
            () -> assertThat(word.getWord()).isEqualTo(expect),
            () -> assertThat(out.toString().contains("단어는 소문자 5개로 구성된 영단어입니다."))
                .isEqualTo(inputInvalid)
        );
    }
}