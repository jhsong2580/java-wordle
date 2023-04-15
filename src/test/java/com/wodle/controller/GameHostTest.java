package com.wodle.controller;


import static org.assertj.core.api.Assertions.assertThat;

import com.wodle.service.InputMangerProxy;
import com.wodle.service.ViewManager;
import com.wodle.service.WordsGenerator;
import com.wodle.testUtils.FileMockUtils;
import com.wodle.testUtils.LocalDateTimeMockUtils;
import com.wodle.testUtils.SystemInOutUtils;
import java.io.OutputStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameHostTest implements FileMockUtils, LocalDateTimeMockUtils, SystemInOutUtils {

    private GameHost gameHost;

    @BeforeEach
    public void init() {
        fileMockUtilsInit();
        localDateTimeMockUtilsInit();

        ViewManager viewManager = new ViewManager();
        InputMangerProxy inputManagerProxy = new InputMangerProxy(viewManager);
        WordsGenerator wordsGenerator = new WordsGenerator();
        gameHost = new GameHost(
            inputManagerProxy,
            viewManager,
            wordsGenerator
        );
    }

    @Test
    public void 게임하기정상동작() {
        //given
        mockingLocalDateTimeUtilsGetBetweenDays(3L);
        mockingFileUtilsGetStreamByFileName("aaaaa", "bbbbb", "ccccc", "hello", "eeeee");

        //when
        inputSetting("pwiee\niejdf\nhello");
        OutputStream out = getOutputStream();
        gameHost.play();

        //then
        assertThat(out.toString()).contains(
            "성공 하셨습니다.\n");
    }

    @Test
    public void 게임하기비정상동작() {
        //given
        mockingLocalDateTimeUtilsGetBetweenDays(3L);
        mockingFileUtilsGetStreamByFileName("aaaaa", "bbbbb", "ccccc", "hello", "eeeee");

        //when
        inputSetting("pwiee\niejdf\neknJe\n32233\nhtioe\nfnsd\neffgr\nffnnh\ncgdeg");
        OutputStream out = getOutputStream();
        gameHost.play();

        //then
        assertThat(out.toString()).contains(
            "실패 하셨습니다.\n오늘의 단어 hello\n");
    }
}