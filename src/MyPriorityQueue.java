import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public  class MyPriorityQueue {
    public static int thirdMax(int[] nums) {
        if (nums.length < 3) {
            throw new IllegalArgumentException("List too small");
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int num : nums) {
            if (pq.size() < 3) {
                pq.offer(num);
            } else if (num > pq.peek()) {
                pq.poll();
                pq.offer(num);
            }
        }
        return pq.peek();
    }

    public static int thirdDistinctMax(int[] nums) {
        Set<Integer> unique = new HashSet<>();

        for (int num : nums) {
            unique.add(num);
        }

        if (unique.size() < 3) {
            throw new IllegalArgumentException("Not enough unique values");
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int num : unique) {
            if (pq.size() < 3) {
                pq.offer(num);
            } else if (num > pq.peek()) {
                pq.poll();
                pq.offer(num);
            }
        }
        return pq.peek();
    }
}
