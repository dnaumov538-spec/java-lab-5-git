public class SqrtDecomposition<T extends Number> {
    private T[] inputArray; // Исходный массив данных
    private Double[] blockSums; // Суммы значений в каждом блоке
    private int elementsPerBlock; // Количество элементов в одном блоке

    public SqrtDecomposition(T[] inputArray) {
        if (inputArray == null || inputArray.length == 0) {
            throw new IllegalArgumentException("Массив не должен быть пустым или null.");
        }

        this.inputArray = inputArray;
        int arraySize = inputArray.length;
        elementsPerBlock = (int) Math.sqrt(arraySize);
        blockSums = new Double[(arraySize + elementsPerBlock - 1) / elementsPerBlock];

        for (int i = 0; i < blockSums.length; i++) {
            blockSums[i] = 0.0;
        }

        for (int i = 0; i < arraySize; i++) {
            blockSums[i / elementsPerBlock] += inputArray[i].doubleValue();
        }
    }

    public void updateValue(int index, T newValue) {
        if (index < 0 || index >= inputArray.length) {
            throw new ArrayIndexOutOfBoundsException("Индекс вне диапазона массива.");
        }
        int blockIndex = index / elementsPerBlock;
        blockSums[blockIndex] += newValue.doubleValue() - inputArray[index].doubleValue();
        inputArray[index] = newValue;
    }

    public void updateRange(int start, int end, T increment) {
        if (start < 0 || end >= inputArray.length || start > end) {
            throw new IllegalArgumentException("Некорректный интервал.");
        }
        for (int i = start; i <= end; i++) {
            int blockIndex = i / elementsPerBlock;
            blockSums[blockIndex] += increment.doubleValue();
            inputArray[i] = (T) Double.valueOf(inputArray[i].doubleValue() + increment.doubleValue());
        }
    }

    public Double querySum(int start, int end) {
        if (start < 0 || end >= inputArray.length || start > end) {
            throw new IllegalArgumentException("Некорректный интервал.");
        }
        double totalSum = 0.0;

        int startBlock = start / elementsPerBlock;
        int endBlock = end / elementsPerBlock;

        if (startBlock == endBlock) {
            for (int i = start; i <= end; i++) {
                totalSum += inputArray[i].doubleValue();
            }
        } else {
            for (int i = start; i < (startBlock + 1) * elementsPerBlock; i++) {
                totalSum += inputArray[i].doubleValue();
            }
            for (int i = startBlock + 1; i < endBlock; i++) {
                totalSum += blockSums[i];
            }
            for (int i = endBlock * elementsPerBlock; i <= end; i++) {
                totalSum += inputArray[i].doubleValue();
            }
        }

        return totalSum;
    }

    public String arrayToString() {
        StringBuilder result = new StringBuilder();
        result.append("[");
        for (int i = 0; i < inputArray.length; i++) {
            result.append(inputArray[i]);
            if (i < inputArray.length - 1) {
                result.append(", ");
            }
        }
        result.append("]");
        return result.toString();
    }
}
