/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode reverseBetween(ListNode head, int left, int right) {

        if (head == null || left == right)
            return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        // Move prev to node before left
        ListNode prev = dummy;
        for (int i = 1; i < left; i++) {
            prev = prev.next;
        }

        ListNode curr = prev.next;
        ListNode next = null;
        ListNode prevNode = null;

        // Reverse right-left+1 nodes
        for (int i = 0; i <= right - left; i++) {
            next = curr.next;
            curr.next = prevNode;
            prevNode = curr;
            curr = next;
        }

        // Connect first part
        prev.next.next = curr;

        // Connect reversed part
        prev.next = prevNode;

        return dummy.next;
    }
}