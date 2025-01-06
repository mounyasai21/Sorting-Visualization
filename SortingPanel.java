import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class SortingPanel extends JPanel {
    private int[] array;
    private int arraySize = 20;  // Smaller array size for wider bars
    private static final int BAR_WIDTH = 30;  // Increased bar width
    private static final int DELAY = 50; // Adjust delay for visualization speed

    private int comparingIndex1 = -1;
    private int comparingIndex2 = -1;
    private int swappingIndex1 = -1;
    private int swappingIndex2 = -1;
    private boolean isSorting = false;
    private Runnable onSortingComplete;
    private String timeComplexity;
    private String spaceComplexity;

    public SortingPanel() {
        array = new int[arraySize];
        shuffleBars();
    }

    public void shuffleBars() {
        if (getHeight() == 0) {
            return;
        }
        Random random = new Random();
        int minValue = 10;
        int maxValue = 200;
        for (int i = 0; i < arraySize; i++) {
            array[i] = random.nextInt(maxValue - minValue + 1) + minValue;
        }
        resetIndices();
        repaint();
    }

    public void sort(String algorithm, Runnable onComplete) {
        if (!isSorting) {
            isSorting = true;
            onSortingComplete = onComplete;
            new Thread(() -> {
                long startTime = System.currentTimeMillis();
                switch (algorithm) {
                    case "Bubble Sort":
                        bubbleSort();
                        timeComplexity = "O(n^2)";
                        spaceComplexity = "O(1)";
                        break;
                    case "Insertion Sort":
                        insertionSort();
                        timeComplexity = "O(n^2)";
                        spaceComplexity = "O(1)";
                        break;
                    case "Selection Sort":
                        selectionSort();
                        timeComplexity = "O(n^2)";
                        spaceComplexity = "O(1)";
                        break;
                    case "Merge Sort":
                        mergeSort(0, array.length - 1);
                        timeComplexity = "O(n log n)";
                        spaceComplexity = "O(n)";
                        break;
                    case "Quick Sort":
                        quickSort(0, array.length - 1);
                        timeComplexity = "O(n log n)";
                        spaceComplexity = "O(log n)";
                        break;
                }
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                resetIndices();
                repaint();
                isSorting = false;
                SwingUtilities.invokeLater(() -> {
                    onSortingComplete.run();
                    JOptionPane.showMessageDialog(null, algorithm + " sorting is completed.\nTime Complexity: " + timeComplexity + "\nSpace Complexity: " + spaceComplexity + "\nExecution Time: " + duration + " ms");
                });
            }).start();
        }
    }

    private void bubbleSort() {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                comparingIndex1 = j;
                comparingIndex2 = j + 1;
                if (array[j] > array[j + 1]) {
                    swap(j, j + 1);
                }
                repaintAndSleep();
            }
        }
    }

    private void insertionSort() {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                comparingIndex1 = j;
                comparingIndex2 = j + 1;
                array[j + 1] = array[j];
                j = j - 1;
                repaintAndSleep();
            }
            array[j + 1] = key;
            resetIndices();
        }
    }

    private void selectionSort() {
        for (int i = 0; i < array.length - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < array.length; j++) {
                comparingIndex1 = j;
                if (array[j] < array[minIdx]) {
                    minIdx = j;
                }
                repaintAndSleep();
            }
            swap(i, minIdx);
            resetIndices();
        }
    }

    private void mergeSort(int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(left, mid);
            mergeSort(mid + 1, right);
            merge(left, mid, right);
        }
    }

    private void merge(int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; ++i)
            L[i] = array[left + i];
        for (int j = 0; j < n2; ++j)
            R[j] = array[mid + 1 + j];

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            comparingIndex1 = left + i;
            comparingIndex2 = mid + 1 + j;
            if (L[i] <= R[j]) {
                array[k] = L[i];
                i++;
            } else {
                array[k] = R[j];
                j++;
            }
            k++;
            repaintAndSleep();
        }

        while (i < n1) {
            comparingIndex1 = left + i;
            array[k] = L[i];
            i++;
            k++;
            repaintAndSleep();
        }

        while (j < n2) {
            comparingIndex1 = mid + 1 + j;
            array[k] = R[j];
            j++;
            k++;
            repaintAndSleep();
        }
        resetIndices();
    }

    private void quickSort(int low, int high) {
        if (low < high) {
            int pi = partition(low, high);
            quickSort(low, pi - 1);
            quickSort(pi + 1, high);
        }
    }

    private int partition(int low, int high) {
        int pivot = array[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            comparingIndex1 = j;
            comparingIndex2 = high;
            if (array[j] < pivot) {
                i++;
                swap(i, j);
            }
            repaintAndSleep();
        }
        swap(i + 1, high);
        resetIndices();
        return i + 1;
    }

    private void swap(int i, int j) {
        swappingIndex1 = i;
        swappingIndex2 = j;
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        repaintAndSleep();
    }

    private void repaintAndSleep() {
        repaint();
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void resetIndices() {
        comparingIndex1 = -1;
        comparingIndex2 = -1;
        swappingIndex1 = -1;
        swappingIndex2 = -1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < array.length; i++) {
            if (i == comparingIndex1 || i == comparingIndex2) {
                g.setColor(Color.RED);
            } else if (i == swappingIndex1 || i == swappingIndex2) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.BLUE);
            }
            int barHeight = array[i];
            int x = i * BAR_WIDTH;
            int y = getHeight() - barHeight;
            g.fillRect(x, y, BAR_WIDTH - 2, barHeight);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(array[i]), x + 5, y - 5);
        }
    }
}
