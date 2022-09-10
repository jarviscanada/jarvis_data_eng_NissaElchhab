package ca.jrvs.practice.codingChallenge;

import ca.jrvs.practice.ListNode;

/**
 * Ticket:
 * https://www.notion.so/jarvisdev/Nissa-Coding-Challenges-4-questions-per-week-4f714ddda1c740deb052485e3ddf9576?p=8372f77aea2948a7b8a9892c65af1d42&pm=s
 */
public class ReverseLinkedList {

  /**
   * Definition for singly-linked list.
   * public class ListNode { int val; ListNode next; ListNode()
   * {} ListNode(int val) { this.val = val; } ListNode(int val, ListNode next) { this.val = val;
   * this.next = next; }
   * }
   */
  class Solution {

    /**
     * in O(n):
     * Linked List traversal/search/compare within one iteration level
     * iteration
     * https://leetcode.com/submissions/detail/795937700/
     *
     * @param head
     * @return
     */
    public ListNode reverseList1(ListNode head) {
      // We code empty linkedlist with null head
      if (head == null) {
        return null;
      }

      ListNode reverseHead = new ListNode(head.val, null);
      for (ListNode h = head.next; ; h = h.next) {
        if (h != null) {
          ListNode reverseNode = new ListNode(h.val, reverseHead);
          reverseHead = reverseNode;
        } else {
          break;
        }

      }
      return reverseHead;
    }

    /**
     * recursion
     * @param head
     * @return
     */
    public ListNode reverseList2(ListNode head) {
      return null;
  }

}
}
