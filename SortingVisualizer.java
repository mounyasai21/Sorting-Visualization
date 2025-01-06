import javax.swing.*;
import java.awt.*;

public class SortingVisualizer extends JFrame {
    private SortingPanel sortingPanel;

    public SortingVisualizer() {
        setTitle("Sorting Visualizer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        sortingPanel = new SortingPanel();
        add(sortingPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JButton bubbleSortButton = new JButton("Bubble Sort");
        JButton insertionSortButton = new JButton("Insertion Sort");
        JButton selectionSortButton = new JButton("Selection Sort");
        JButton mergeSortButton = new JButton("Merge Sort");
        JButton quickSortButton = new JButton("Quick Sort");
        JButton shuffleButton = new JButton("Shuffle");

        bubbleSortButton.addActionListener(e -> {
            sortingPanel.sort("Bubble Sort", () -> bubbleSortButton.setEnabled(true));
            bubbleSortButton.setEnabled(false);
        });

        insertionSortButton.addActionListener(e -> {
            sortingPanel.sort("Insertion Sort", () -> insertionSortButton.setEnabled(true));
            insertionSortButton.setEnabled(false);
        });

        selectionSortButton.addActionListener(e -> {
            sortingPanel.sort("Selection Sort", () -> selectionSortButton.setEnabled(true));
            selectionSortButton.setEnabled(false);
        });

        mergeSortButton.addActionListener(e -> {
            sortingPanel.sort("Merge Sort", () -> mergeSortButton.setEnabled(true));
            mergeSortButton.setEnabled(false);
        });

        quickSortButton.addActionListener(e -> {
            sortingPanel.sort("Quick Sort", () -> quickSortButton.setEnabled(true));
            quickSortButton.setEnabled(false);
        });

        shuffleButton.addActionListener(e -> {
            sortingPanel.shuffleBars();
            sortingPanel.repaint();
            bubbleSortButton.setEnabled(true);
            insertionSortButton.setEnabled(true);
            selectionSortButton.setEnabled(true);
            mergeSortButton.setEnabled(true);
            quickSortButton.setEnabled(true);
        });

        controlPanel.add(bubbleSortButton);
        controlPanel.add(insertionSortButton);
        controlPanel.add(selectionSortButton);
        controlPanel.add(mergeSortButton);
        controlPanel.add(quickSortButton);
        controlPanel.add(shuffleButton);

        add(controlPanel, BorderLayout.SOUTH);

        // Ensure the frame is visible before shuffling bars
        setVisible(true);
        sortingPanel.shuffleBars();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SortingVisualizer::new);
    }
}
