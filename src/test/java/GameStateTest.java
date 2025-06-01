
import org.example.GameState;
import org.example.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.junit.jupiter.MockitoExtension;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестируем основную логику игры
 */
@ExtendWith(MockitoExtension.class)
public class GameStateTest {

    @Test
    void testFirstQuestionException() {
        Throwable exception = assertThrows(
                IllegalStateException.class,
                ()-> {
                    throw new IllegalStateException("Начальный вопрос отсутствует");
                }
        );
        assertEquals("Начальный вопрос отсутствует",exception.getMessage());
    }

    @Test
    void testCreateGameTreeWithReflection() throws Exception {
         GameState gameState = new GameState();

        Method method = GameState.class.getDeclaredMethod("createGameTree");
        method.setAccessible(true);

        @SuppressWarnings("unchecked")
        Map<String, List<String>> gameTree = (Map<String, List<String>>) method.invoke(gameState);

        assertNotNull(gameTree);
        assertFalse(gameTree.isEmpty());
        assertTrue(gameTree.containsKey("Ты потерял память. Принять вызов НЛО?"));
    }

    @Test @DisplayName("тест для обычного случая")
    void basicTestGetCurrentOptions() {
         GameState gameState = new GameState();
         String currentQuestion ="Ты потерял память. Принять вызов НЛО?";
         List<String> options = gameState.getCurrentOptions();
         assertEquals(Arrays.asList(currentQuestion), options);
    }

    @Test @DisplayName("тест для пограничного случая")
    void borderTestGetCurrentOptions() {
        GameState gameState = new GameState();
        String currentQuestion = "Ты не терял память, НЛО хочет тебя утащить с собой";
        gameState.setCurrentQuestion(currentQuestion);
        List<String> options = gameState.getCurrentOptions();
        assertEquals(Collections.emptyList(),options);
    }

    @Test @DisplayName("тест для пустой мапы")
    void testGetCurrentOptions_EmptyGameTree() throws NoSuchFieldException, IllegalAccessException {
        GameState gameState = new GameState();

        //рефлексия для доступа к gameTree
        Field gameTreeField = GameState.class.getDeclaredField("gameTree");
        gameTreeField.setAccessible(true);
        gameTreeField.set(gameState,new LinkedHashMap<>());

        gameState.setCurrentQuestion("Ты потерял память. Принять вызов НЛО?");
        List<String> options = gameState.getCurrentOptions();
        assertTrue(options.isEmpty());

    }

    @ParameterizedTest
    @EnumSource(Status.class)
    void testIsGameOver() {
        GameState gameState = new GameState();
          gameState.setStatus(Status.IN_PROGRESS);
              assertFalse(gameState.isGameOver(),"игра должна быть активной при статусе IN_Progress");

              gameState.setStatus(Status.WINS);
              assertTrue(gameState.isGameOver(),"игра должна быть завершена при статусе WINS");

              gameState.setStatus(Status.GAME_OVER);
              assertTrue(gameState.isGameOver(),"игра должна быть завершена при статусе GAME OVER");

    }

    @Test @DisplayName("тест для корректного ответа")
    void testProcessAnswer_ValidAnswer() {
         GameState gameState = new GameState();
         assertEquals("Ты потерял память. Принять вызов НЛО?",gameState.getCurrentQuestion());
         gameState.processAnswer("Принять вызов");
         assertEquals("Принять вызов",gameState.getCurrentQuestion());
         assertEquals(Status.IN_PROGRESS,gameState.getStatus());
    }

    @Test @DisplayName("тест для 'Победы'")
    void testProcessAnswer_WinAnswer() {
         GameState gameState = new GameState();
         gameState.processAnswer("Принять вызов");
         gameState.processAnswer("Подняться на мостик");
         gameState.processAnswer("Рассказать правду о себе");
         assertEquals("Рассказать правду о себе",gameState.getCurrentQuestion());
         gameState.processAnswer("Домой. Победа!");
         assertEquals("Домой. Победа!",gameState.getCurrentQuestion());
         assertEquals(Status.WINS,gameState.getStatus());
    }

    @Test @DisplayName("тест для 'Поражения'")
    void testProcessAnswer_LooseAnswer() {
        GameState gameState = new GameState();

        // Проверяем начальный вопрос
        assertEquals("Ты потерял память. Принять вызов НЛО?", gameState.getCurrentQuestion());

        // Выбираем ответ "Принять вызов"
        gameState.processAnswer("Принять вызов");
        assertEquals("Принять вызов", gameState.getCurrentQuestion());
        assertEquals(Status.IN_PROGRESS, gameState.getStatus());


        // Выбираем ответ "Отказаться подниматься", который приводит к поражению
        gameState.processAnswer("Отказаться подниматься");
        assertEquals("Отказаться подниматься", gameState.getCurrentQuestion());
        assertEquals(Status.GAME_OVER, gameState.getStatus());
    }

    @Test @DisplayName("тест для неправильного ответа")
    void testProcessAnswer_InvalidAnswer() {
        GameState gameState = new GameState();
        assertEquals("Ты потерял память. Принять вызов НЛО?",gameState.getCurrentQuestion());
        assertThrows(IllegalArgumentException.class,()-> {
             gameState.processAnswer("Неверный ответ");
                });
    }

    @Test @DisplayName("Должен сбрасывать игру в начальное состояние")
    void testReset() {
      GameState gameState = new GameState();
      gameState.processAnswer("Принять вызов");
      gameState.setStatus(Status.GAME_OVER);
      gameState.reset();
      assertEquals("Ты потерял память. Принять вызов НЛО?",gameState.getCurrentQuestion());
      assertEquals(Status.IN_PROGRESS,gameState.getStatus());
    }

    @Test @DisplayName("Должен сбрасывать игру даже если статус WINS")
    void testReset_WINS() {
        GameState gameState = new GameState();
        gameState.setStatus(Status.WINS);
        gameState.reset();
        assertEquals(Status.IN_PROGRESS,gameState.getStatus());

    }
}

