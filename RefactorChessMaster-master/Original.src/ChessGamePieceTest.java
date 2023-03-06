import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ChessGameBoardTest {
    ChessGameBoard board = new ChessGameBoard();

    @Test
    void testValidateCoordinates() {
        boolean validCoords = board.validateCoordinates(4, 4);
        boolean invalidCoords = board.validateCoordinates(8, 8);
        Assertions.assertTrue(validCoords);
        Assertions.assertFalse(invalidCoords);
    }
    @Test
    void testClearCell() {
        board.clearCell(2, 2);
        Assertions.assertNull(board.getCell(2, 2).getPieceOnSquare());
    }
    @Test
    void testGetAllWhitePieces() {
        List<ChessGamePiece> whitePieces = board.getAllWhitePieces();
        Assertions.assertEquals(16, whitePieces.size());
    }
}
