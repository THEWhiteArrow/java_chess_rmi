package util;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class FENParser {


    public static ArrayList<Piece> createPieces(String notation){
        ArrayList<Piece> pieces = new ArrayList<>();
        char[][] board = parseFEN(notation);
        for(int j=0;j<8;++j){
            for(int i=0;i<8;++i){
                switch (board[i][j]){
                    case '.':
                        break;
                    default:
                        pieces.add(
                                new Piece(
                                        calculateSrc(board[i][j]),
                                        calculateLayoutX(j),
                                        calculateLayoutY(i)
                                        )
                        );
                }

            }
        }
        return pieces;
    }

    public static int  calculateRow(double y){
        return (int)((y-105)/50);
    }

    public static int calculateColumn(double x){
        return (int)((x-155)/50);
    }

    public static String calculateFen(ObservableList<Node> pieces){
        char[][] board = {
                {'.','.','.','.','.','.','.','.',},
                {'.','.','.','.','.','.','.','.',},
                {'.','.','.','.','.','.','.','.',},
                {'.','.','.','.','.','.','.','.',},
                {'.','.','.','.','.','.','.','.',},
                {'.','.','.','.','.','.','.','.',},
                {'.','.','.','.','.','.','.','.',},
                {'.','.','.','.','.','.','.','.',},
        };

        for(Node node : pieces){
            double x = node.getLayoutX();
            double y = node.getLayoutY();
            int row = calculateRow(y);
            int column = calculateColumn(x);

//            System.out.println(row+":"+column);

            String url = ((ImageView)node).getImage().getUrl();
            String pieceName = ""+url.charAt( url.length()-5);

            if( ((ImageView)node).getImage().getUrl().contains("white"))
                board[column][row] = pieceName.toUpperCase().charAt(0);
            else
                board[column][row] = pieceName.toLowerCase().charAt(0);
        }

        String fenPom = "";
        for(int j=0;j<8;++j){
            for(int i=0;i<8;++i){
                fenPom+=board[i][j];
            }

            if(j<7)
                fenPom+="/";

        }

        String fen="";

        int i=0,cnt=0;
        while(i<fenPom.length()){
            if( '.'!= fenPom.charAt(i) ){
                if(cnt!=0){
                    fen+=""+cnt;
                    cnt=0;
                }

                fen+=fenPom.charAt(i);
            }
            else{
                ++cnt;
            }
            ++i;
        }
        if(cnt!=0)
            fen+=""+cnt;



        return fen;

    }

    public static String calculateFieldName(int x, int y){
        String ans = "";
        ans += ""+(char)((int)('a')+((x-155)/50));
        ans += ""+(8-(((y-105)/50)));

        return ans;
    }

    public static String calculateFieldName(double x, double y){
        return calculateFieldName((int)x, (int)y);
    }

    public static String calculateSrc(char c){
        if( (int)(c)>=97 ) return "out/production/game/images/black/"+(char)((int)(c)-32)+".png";
        else return "out/production/game/images/white/"+c+".png";

    }

    public static int calculateLayoutX(int n){
        return (n*50+155);
    }

    public static int calculateLayoutY(int n){
        return (n*50+105);
    }



    // Given a FEN notation string, return a 2D array representing the board

    public static char[][] parseFEN(String fen) {
        // Implementation omitted for brevity

        String[] parts = fen.split(" ");
        String[] rows = parts[0].split("/");
        char[][] board = new char[8][8];
        int row = 0;
        int col = 0;
        for (String r : rows) {
            for (int i = 0; i < r.length(); i++) {
                char c = r.charAt(i);
                if (Character.isDigit(c)) {
                    int numEmptySquares = Character.getNumericValue(c);
                    for (int j = 0; j < numEmptySquares; j++) {
                        board[row][col++] = '.';
                    }
                } else {
                    board[row][col++] = c;
                }
            }
            row++;
            col = 0;
        }
        return board;
//        return new char[][]{
//                {'.','.','.','.','.','.','.','.',},
//                {'.','.','.','.','.','.','.','.',},
//                {'.','.','.','.','.','.','.','.',},
//                {'.','.','.','.','.','.','.','.',},
//                {'.','.','.','.','.','.','.','.',},
//                {'.','.','.','.','.','.','.','.',},
//                {'.','.','.','.','.','.','.','.',},
//                {'.','.','.','.','.','.','.','.',},
//        };
    }

    // Given a field in algebraic notation (e.g. "e4"), return its row and column indices on the board
    public static int[] parseField(String field) {
        // Implementation omitted for brevity
        return new int[]{(int) field.charAt(0) - 97, (int) field.charAt(1) - 52};
    }

    // Given a board, a starting row and column, and a destination row and column,
    // check if the move is legal and return true if so, false otherwise
    public static boolean isLegalMove(char[][] board, int startRow, int startCol, int destRow, int destCol) {
        // Implementation omitted for brevity
        // Check if the destination square is occupied by a piece of the same color
        if (board[destRow][destCol] != '.' && board[destRow][destCol] / 'a' == board[startRow][startCol] / 'a') {
            return false;
        }

        // Check if the move is valid for the piece at the starting square
        char piece = board[startRow][startCol];
        switch (Character.toLowerCase(piece)) {
            case 'p': // Pawn
                // Check if the move is a standard forward move
                if (startCol == destCol && board[destRow][destCol] == '.') {
                    // Check if the pawn is moving one or two squares forward
                    if ((startRow == 1 && destRow == 3) || (startRow == 6 && destRow == 4)) {
                        // Check if the squares in between the starting and destination squares are empty
                        if (board[(startRow + destRow) / 2][destCol] == '.') {
                            return true;
                        }
                    } else if (destRow == startRow + 1) {
                        return true;
                    }
                }

                // Check if the move is a capture
                if (Math.abs(startCol - destCol) == 1 && destRow == startRow + 1 && board[destRow][destCol] != '.') {
                    return true;
                }

                break;
            case 'r': // Rook
                // Check if the move is a horizontal or vertical move
                if (startRow == destRow || startCol == destCol) {
                    // Check if the squares in between the starting and destination squares are empty
                    int min = Math.min(startRow, destRow);
                    int max = Math.max(startRow, destRow);
                    for (int i = min + 1; i < max; i++) {
                        if (board[i][startCol] != '.') {
                            return false;
                        }
                    }
                    min = Math.min(startCol, destCol);
                    max = Math.max(startCol, destCol);
                    for (int i = min + 1; i < max; i++) {
                        if (board[startRow][i] != '.') {
                            return false;
                        }
                    }
                    return true;
                }
                break;
            case 'n': // Knight
                // Check if the move is a valid L-shape
                int diffRow = Math.abs(destRow - startRow);
                int diffCol = Math.abs(destCol - startCol);
                if ((diffRow == 2 && diffCol == 1) || (diffRow == 1 && diffCol == 2)) {
                    return true;
                }
                break;
            case 'b': // Bishop
                // Check if the move is a diagonal move
                if (Math.abs(startRow - destRow) == Math.abs(startCol - destCol)) {
                    // Check if the squares in between the starting and destination squares are empty
                    int rowDir = (destRow - startRow) > 0 ? 1 : -1;
                    int colDir = (destCol - startCol) > 0 ? 1 : -1;
                    int i = startRow + rowDir;
                    int j = startCol + colDir;
                    while (i != destRow && j != destCol) {
                        if (board[i][j] != '.') {
                            return false;
                        }
                        i += rowDir;
                        j += colDir;
                    }
                    return true;
                }
                break;
            case 'q': // Queen
                // Check if the move is a horizontal, vertical, or diagonal move
                if (startRow == destRow || startCol == destCol || Math.abs(startRow - destRow) == Math.abs(startCol - destCol)) {
                    // Check if the squares in between the starting and destination squares are empty
                    if (startRow == destRow) {
                        int min = Math.min(startCol, destCol);
                        int max = Math.max(startCol, destCol);
                        for (int i = min + 1; i < max; i++) {
                            if (board[startRow][i] != '.') {
                                return false;
                            }
                        }
                    } else if (startCol == destCol) {
                        int min = Math.min(startRow, destRow);
                        int max = Math.max(startRow, destRow);
                        for (int i = min + 1; i < max; i++) {
                            if (board[i][startCol] != '.') {
                                return false;
                            }
                        }
                    } else {
                        int rowDir = (destRow - startRow) > 0 ? 1 : -1;
                        int colDir = (destCol - startCol) > 0 ? 1 : -1;
                        int i = startRow + rowDir;
                        int j = startCol + colDir;
                        while (i != destRow && j != destCol) {
                            if (board[i][j] != '.') {
                                return false;
                            }
                            i += rowDir;
                            j += colDir;
                        }
                    }
                    return true;
                }
                break;
            case 'k': // King
                // Check if the move is a valid king move
                diffRow = Math.abs(destRow - startRow);
                diffCol = Math.abs(destCol - startCol);
                if ((diffRow == 1 && diffCol == 0) || (diffRow == 0 && diffCol == 1) || (diffRow == 1 && diffCol == 1)) {
                    return true;
                }

                // Check if the move is a valid castling move
                if (startRow == destRow && Math.abs(startCol - destCol) == 2) {
                    // Check if the king and rook have not moved yet
                    if ((board[startRow][startCol] == 'K' && startRow == 7 && (startCol == 4 || startCol == 6))
                            || (board[startRow][startCol] == 'k' && startRow == 0 && (startCol == 4 || startCol == 2))) {
                        int row = startRow;
                        int col = startCol > destCol ? 0 : 7;
                        if (board[row][col] == Character.toUpperCase(board[startRow][startCol])) {
                            // Check if the squares between the king and rook are empty
                            int min = Math.min(startCol, col);
                            int max = Math.max(startCol, col);
                            for (int i = min + 1; i < max; i++) {
                                if (board[row][i] != '.') {
                                    return false;
                                }
                            }
                            return true;
                        }
                    }
                }

                break;
        }

        return false;
    }

    // Given a FEN notation string and a field in algebraic notation, return a list of all legal moves from that field
    public static ArrayList<String> getLegalMoves(String fen, String field) {
        char[][] board = parseFEN(fen);
        int[] indices = parseField(field);
        int row = indices[0];
        int col = indices[1];

        ArrayList<String> legalMoves = new ArrayList<>();

        // Check all possible destination squares
        for (int destRow = 0; destRow < 8; destRow++) {
            for (int destCol = 0; destCol < 8; destCol++) {
                // Check if the move from the starting square to the destination square is legal
                if (isLegalMove(board, row, col, destRow, destCol)) {
                    // If so, add the move to the list of legal moves
                    legalMoves.add(field + getAlgebraicNotation(destRow, destCol));
                }
            }
        }

        return legalMoves;
    }

    // Given a row and column index, return the algebraic notation for the square (e.g. "e4")
    public static String getAlgebraicNotation(int row, int col) {
        // Implementation omitted for brevity
        return (char)(97+row)+""+col;
    }
}