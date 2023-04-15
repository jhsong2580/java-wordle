package com.wodle.controller;

import com.wodle.domain.AnswerWord;
import com.wodle.service.GameMachine;
import com.wodle.domain.Result;
import com.wodle.domain.Word;
import com.wodle.backend.InputManager;
import com.wodle.backend.InputManagerImpl;
import com.wodle.ui.ViewManager;
import com.wodle.backend.WordGenerator;

public class GameHost {

    private static final int START_COIN = 6;
    private final InputManager inputManagerProxy;

    private final ViewManager viewManager;

    private final WordGenerator wordGenerator;

    public GameHost(InputManagerImpl inputManagerProxy, ViewManager viewManager,
        WordGenerator wordGenerator) {
        this.inputManagerProxy = inputManagerProxy;
        this.viewManager = viewManager;
        this.wordGenerator = wordGenerator;
    }

    public void play() {
        AnswerWord word = wordGenerator.getTodayWord();
        GameMachine machine = new GameMachine(word, START_COIN);

        viewManager.printGameStart();

        while (machine.isGameNotEnd()) {
            machine.useCoin();
            Word inputWord = inputManagerProxy.inputWord();
            Result wordCompareResult = machine.compareWord(inputWord);
            viewManager.printCompareResult(wordCompareResult);

            machine.saveGameStatus(wordCompareResult.isGameEnd());
        }

        viewManager.printResult(machine.isGameEnd(), machine.getWord());
    }

}
