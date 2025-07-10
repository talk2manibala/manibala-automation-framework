package com.manibala.test.groq.api;

public class LocalTest {

        // Method to verify palindrome without using reverse
        public static boolean isPalindrome(String input) {
            int left = 0;
            int right = input.length() - 1;

            while (left < right) {
                System.out.println(input.charAt(left) + " = " +input.charAt(right));
                if (input.charAt(left) != input.charAt(right)) {
                    System.out.println(input.charAt(left) + " != " +input.charAt(right));
                    return false; // Not a palindrome
                }
                left++;
                right--;
            }
            return true; // All characters matched
        }

        public static void main(String[] args) {
            String str = "madam";

            if (isPalindrome(str)) {
                System.out.println(str + " is a palindrome.");
            } else {
                System.out.println(str + " is not a palindrome.");
            }
        }
    }
