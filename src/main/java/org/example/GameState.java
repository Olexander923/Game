package org.example;

import java.util.*;

/**
 * Основная логика игры, создание вопрослв-ответов, изменение статуса, рестарт
 */
public class GameState {
    private String currentQuestion;
    private final Map<String, List<String>> gameTree;
    private Status status;
    private static final String INITIAL_QUESTION = "Ты потерял память. Принять вызов НЛО?";

    public GameState() {
        this.gameTree = createGameTree();
        this.currentQuestion = INITIAL_QUESTION;
        this.status = Status.IN_PROGRESS;

        if (!gameTree.containsKey(INITIAL_QUESTION)) {
            throw new IllegalStateException("Начальный вопрос отсутствует");
        }
    }

    /**
     * Метод создает дерево мапу "вопрос-ответ", инициализируется в конструкторе
     * @return заполненную мапу
     */
    private Map<String, List<String>> createGameTree() {
        Map<String, List<String>> tree = new LinkedHashMap<>();
        tree.put(INITIAL_QUESTION, Arrays.asList("Принять вызов", "Отклонить вызов"));
        tree.put("Принять вызов", Arrays.asList("Подняться на мостик", "Отказаться подниматься"));
        tree.put("Отклонить вызов", Collections.singletonList("Поражение"));
        tree.put("Подняться на мостик", Arrays.asList("Рассказать правду о себе", "Солгать о себе"));
        tree.put("Отказаться подниматься", Collections.singletonList("Поражение"));
        tree.put("Рассказать правду о себе", Collections.singletonList("Домой. Победа!"));
        tree.put("Солгать о себе", Collections.singletonList("Ложь разоблачена. Поражение"));

        return tree;
    }

    /**
     * Возвращает список доступных ответов для текущего вопроса(currentQuestion) из gameTree
     * @return пустой список, если текущий вопос отсутствует в мапе gameTree
     */
    public String getCurrentQuestion() { return currentQuestion; }


    public List<String> getCurrentOptions() {
        return gameTree.getOrDefault(currentQuestion, Collections.emptyList());
    }

    public void setCurrentQuestion(String currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public Status getStatus() { return status; }

    public boolean isGameOver() {
        return status != Status.IN_PROGRESS;
    }

    /**
     * Обработка ответов игрока, обновление статусов игры
     * @param answer текущий ответ
     */
    public void processAnswer(String answer) {
        List<String> availableAnswers = gameTree.get(currentQuestion);
        if (availableAnswers == null || !availableAnswers.contains(answer)) {
            throw new IllegalArgumentException("Неверный ответ");
        }

        currentQuestion = answer;
        List<String> nextAnswers = gameTree.get(answer);
        if(nextAnswers != null && nextAnswers.contains("Поражение")) {
            status = Status.GAME_OVER;
        } else if(answer.contains("Победа")) {
            status = Status.WINS;
        } else if (answer.contains("Поражение")) {
            status = Status.GAME_OVER;
        }
    }

    public void reset() {
        currentQuestion = INITIAL_QUESTION;
        status = Status.IN_PROGRESS;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}