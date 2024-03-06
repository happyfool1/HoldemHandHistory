package holdemhandhistory;

/*-  ***********************************************************************************
* A fast method to sort an array of Cards
* There are many advantages to sorting a board or a board combined with hole cards:
* 		Finding draws, straights, flushes, pairs, etc. is much faster and simpler.
* 		Comparing hands
* 		Analysis such ad HML and 1755 flops.
* 		A human looking at the hand
* 
* I chose to write my own sort instead of using something like the Arrays Class 
* for performance reasons. By making the methods Card specific we eliminate
* a lot of overhead.
* 
* @author PEAK_
***************************************************************************************/

public class SortCards {
	private SortCards() {
		throw new IllegalStateException("Utility class");
	}

	/*- *********************************************************************************
	* You'll see that the cards will be sorted from the highest value to the lowest. 
	* A faster sort than quickSort
	***********************************************************************************	*/
	public static void sortCardsValue(Card[] cards, int length) {
		for (int i = 1; i < length; i++) {
			final var key = cards[i];
			int j = i - 1;
			// Move elements that are greater than key to one position ahead
			while (j >= 0 && cards[j].value < key.value) {
				cards[j + 1] = cards[j];
				j -= 1;
			}
			cards[j + 1] = key;
		}
	}

	/*- *********************************************************************************
	Sort bycard 
	***********************************************************************************	*/
	public static void sortCardsCard(Card[] cards, int length) {
		for (int i = 1; i < length; i++) {
			final var key = cards[i];
			int j = i - 1;
			// Move elements that are greater than key to one position ahead
			while (j >= 0 && cards[j].card > key.card) {
				cards[j + 1] = cards[j];
				j -= 1;
			}
			cards[j + 1] = key;
		}
	}

	/*- *********************************************************************************
	Sort by suit. 
	***********************************************************************************	*/
	public static void sortCardsSuit(Card[] cards, int length) {
		for (int i = 1; i < length; i++) {
			final var key = cards[i];
			int j = i - 1;
			// Move elements that are greater than key to one position ahead
			while (j >= 0 && cards[j].suit < key.suit) {
				cards[j + 1] = cards[j];
				j -= 1;
			}
			cards[j + 1] = key;
		}
	}

	/*-  ***********************************************************************************
	 * Sort an array of Card high to low
	 * Parameters: It takes three parameters as can be perceived from the
	 * syntax which is as follows:
	 * Arg0 - An array of Card
	 * Arg1 - lower index, almost always 0 ( needed because it is recursive )
	 * Arg2 - Upper index, xx.length -1
	 * 
	 * This method is recursive.
	 *
	 *  A variation of the quicksort algorithm. 
	 *	This implementation uses the Card class.
	 * The quickSort method takes an array of cards and the right of the array
	 * It chooses a pivot index (in this case, the middle index) and partitions the array 
	 * around the pivot value using the partition method.
	 *
	 * The partition method takes the same array and left and right indices, chooses a pivot value 
	 * (in this case, the value at the right index), and iterates through the array, swapping elements 
	 * to place smaller elements to the left of the pivot and larger elements to the right.
	 * It then swaps the pivot value with the element at the store index 
	 * (which is the index of the first element that is greater than the pivot value), 
	 * so that the pivot value is in its final sorted position. The method returns the store index.
	 *
	 * quickSort(cards, cards.length -1 );
	***************************************************************************************/
	static void quickSortValue(Card[] cards, int left, int right) {
		if (left >= right) {
			return;
		}
		final int pivotIndex = partitionValue(cards, left, right);
		quickSortValue(cards, left, pivotIndex - 1);
		quickSortValue(cards, pivotIndex + 1, right);
	}

	private static int partitionValue(Card[] cards, int left, int right) {
		Card temp;
		final int pivotIndex = (left + right) / 2; // Index into cards array
		final var pivotValue = cards[pivotIndex]; // Card from cards array
		temp = cards[pivotIndex];
		cards[pivotIndex] = cards[right];
		cards[right] = temp;
		int storeIndex = left;

		for (int i = left; i < right; i++) {
			if (cards[i].value > pivotValue.value) {
				temp = cards[i];
				cards[i] = cards[storeIndex];
				cards[storeIndex] = temp;
				storeIndex++;
			}
		}
		temp = cards[storeIndex];
		cards[storeIndex] = cards[right];
		cards[right] = temp;
		return storeIndex;
	}

	static void quickSortCard(Card[] cards, int left, int right) {
		if (left >= right) {
			return;
		}
		final int pivotIndex = partitionCard(cards, left, right);
		quickSortCard(cards, left, pivotIndex - 1);
		quickSortCard(cards, pivotIndex + 1, right);
	}

	private static int partitionCard(Card[] cards, int left, int right) {
		Card temp;
		final int pivotIndex = (left + right) / 2; // Index into cards array
		final var pivotCard = cards[pivotIndex]; // Card from cards array
		temp = cards[pivotIndex];
		cards[pivotIndex] = cards[right];
		cards[right] = temp;
		int storeIndex = left;
		for (int i = left; i < right; i++) {
			if (cards[i].card < pivotCard.card) { // Change comparison operator
				temp = cards[i];
				cards[i] = cards[storeIndex];
				cards[storeIndex] = temp;
				storeIndex++;
			}
		}
		temp = cards[storeIndex];
		cards[storeIndex] = cards[right];
		cards[right] = temp;
		return storeIndex;
	}

	/*-
	 * Version that uses Card.suit for sort
	 *  Suit is 0 - 3
	 */
	static void quickSortSuit(Card[] cards, int left, int right) {
		if (left >= right) {
			return;
		}

		final int pivotIndex = partitionSuit(cards, left, right);
		quickSortSuit(cards, left, pivotIndex - 1);
		quickSortSuit(cards, left, right);
	}

	private static int partitionSuit(Card[] cards, int left, int right) {
		Card temp;
		final int pivotIndex = (right) / 2; // Index into cards array
		final var pivotValue = cards[pivotIndex]; // Card from cards array
		temp = cards[pivotIndex];
		cards[pivotIndex] = cards[right];
		cards[right] = temp;
		int storeIndex = left;

		for (int i = 0; i < right; i++) {
			if (cards[i].suit > pivotValue.suit) {
				temp = cards[i];
				cards[i] = cards[storeIndex];
				cards[storeIndex] = temp;
				storeIndex++;
			}
		}
		temp = cards[storeIndex];
		cards[storeIndex] = cards[right];
		cards[right] = temp;
		return storeIndex;
	}

}
