import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CircuitTracerGUI extends JFrame {
    private final CircuitBoard board;
    private final List<TraceState> bestPaths;

    private JPanel boardPanel;
    private final JLabel[][] cellLabels;
    private final DefaultListModel<String> pathListModel = new DefaultListModel<>();
    private final JList<String> pathList = new JList<>(pathListModel);
    private final JLabel statusLabel = new JLabel("Select a path to highlight");

    private static final Color START_COLOR = Color.GREEN;
    private static final Color END_COLOR = Color.RED;
    private static final Color OPEN_COLOR = Color.WHITE;
    private static final Color CLOSED_COLOR = Color.BLACK;
    private static final Color TRACE_COLOR = Color.GRAY;
    private static final Font labelFont = new Font(Font.MONOSPACED, Font.BOLD, 18);

    /**
     * Constructs and displays the CircuitTracerGUI window.
     * 
     * @param board the CircuitBoard to display
     * @param bestPaths the list of optimal trace paths found by the search
     */
    public CircuitTracerGUI(CircuitBoard board, ArrayList<TraceState> bestPaths) {
        super("Circuit Tracer");
        this.board = board;
        this.bestPaths = new ArrayList<>(bestPaths);
        this.cellLabels = new JLabel[board.numRows()][board.numCols()];

        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //size window based on board dimensions with a minimum size
        setPreferredSize(new Dimension(Math.max(700, board.numCols() * 55), Math.max(500, board.numRows() * 40)));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Initializes and lays out all GUI components.
     */
    private void initComponents() {
        //build the initial board display with no trace
        boardPanel = new JPanel(new GridLayout(board.numRows(), board.numCols()));
        buildCells(boardPanel, board);

        //populate the solution list with each path's index and length
        for (int i = 0; i < bestPaths.size(); i++) {
            TraceState state = bestPaths.get(i);
            pathListModel.addElement(String.format("Solution %d (length %d)", i + 1, state.pathLength()));
        }

        //redraw the board with the selected path's trace when a solution is chosen
        pathList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pathList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    drawTrace(bestPaths.get(pathList.getSelectedIndex()));
                }
            }
        });

        //wrap the solution list and status label in a panel on the right side
        JPanel listPanel = new JPanel(new BorderLayout(8, 8));
        listPanel.add(new JScrollPane(pathList), BorderLayout.CENTER);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        listPanel.add(statusLabel, BorderLayout.SOUTH);

        //place the board in the center and solution list on the right
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(boardPanel, BorderLayout.CENTER);
        contentPanel.add(listPanel, BorderLayout.EAST);

        setContentPane(contentPanel);
    }

    /**
     * Draws the given trace path onto the board panel by rebuilding its cells
     * using the trace's board state.
     * 
     * @param trace the TraceState whose path should be highlighted on the board
     */
    private void drawTrace(TraceState trace) {
        //rebuild cells using the trace's board state, which has 'T' markers placed
        buildCells(boardPanel, trace.getBoard());
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    /**
     * Populates a JPanel with labeled cells representing the given board state.
     * Clears any existing cells before building.
     * 
     * @param panel the JPanel to populate
     * @param sourceBoard the CircuitBoard whose state should be displayed
     */
    private void buildCells(JPanel panel, CircuitBoard sourceBoard) {
        panel.removeAll();
        for (int row = 0; row < board.numRows(); row++) {
            for (int col = 0; col < board.numCols(); col++) {
                //create a label for each cell with the appropriate text and styling
                JLabel cell = new JLabel(getCellText(row, col, sourceBoard), SwingConstants.CENTER);
                cell.setOpaque(true);
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cell.setFont(labelFont);
                updateCell(row, col, cell, sourceBoard);
                cellLabels[row][col] = cell;
                panel.add(cell);
            }
        }
    }

    /**
     * Updates the background and foreground color of a cell label based on
     * the character at the given position in the source board.
     * 
     * @param row the row of the cell
     * @param col the column of the cell
     * @param cell the JLabel to update
     * @param sourceBoard the CircuitBoard to read the cell character from
     */
    private void updateCell(int row, int col, JLabel cell, CircuitBoard sourceBoard) {
        char cellChar = sourceBoard.charAt(row, col);

        //color each cell based on its type
        switch (cellChar) {
            case '1':
                cell.setBackground(START_COLOR);
                break;
            case '2':
                cell.setBackground(END_COLOR);
                break;
            case 'X':
                cell.setBackground(CLOSED_COLOR);
                cell.setForeground(Color.WHITE);
                break;
            case 'T':
                cell.setBackground(TRACE_COLOR);
                break;
            default:
                cell.setBackground(OPEN_COLOR);
                break;
        }
    }

    /**
     * Returns the string representation of the character at the given position
     * on the given board.
     * 
     * @param row the row of the cell
     * @param col the column of the cell
     * @param cellBoard the CircuitBoard to read from
     * @return a single-character string representing the cell's content
     */
    private String getCellText(int row, int col, CircuitBoard cellBoard) {
        return String.valueOf(cellBoard.charAt(row, col));
    }

    /**
     * Creates and displays a CircuitTracerGUI window on the EDT.
     * 
     * @param board the CircuitBoard to display
     * @param bestPaths the list of optimal trace paths to display
     */
    public static void show(CircuitBoard board, ArrayList<TraceState> bestPaths) {
        //run GUI construction on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new CircuitTracerGUI(board, bestPaths));
    }
}
