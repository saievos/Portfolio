﻿using System;
using System.Collections.Generic;
using static System.Random;
using System.Text;

namespace HangmanGame;

    internal class Program
{
    private static void PrintHangman(int wrong)
    {
        if(wrong == 0)
        {
            Console.WriteLine("\n+---+");
            Console.WriteLine("    |");
            Console.WriteLine("    |");
            Console.WriteLine("    |");
            Console.WriteLine("   ===");
        }
        else if(wrong == 1)
        {
            Console.WriteLine("\n+---+");
            Console.WriteLine(" O  |");
            Console.WriteLine("    |");
            Console.WriteLine("    |");
            Console.WriteLine("   ===");
        }
        else if (wrong == 2)
        {
            Console.WriteLine("\n+---+");
            Console.WriteLine(" O  |");
            Console.WriteLine(" |  |");
            Console.WriteLine("    |");
            Console.WriteLine("   ===");
        }
        else if (wrong == 3)
        {
            Console.WriteLine("\n+---+");
            Console.WriteLine(" O  |");
            Console.WriteLine("/|  |");
            Console.WriteLine("    |");
            Console.WriteLine("   ===");
        }
        else if (wrong == 4)
        {
            Console.WriteLine("\n+----+");
            Console.WriteLine(" O  |");
            Console.WriteLine("/|\\ |");
            Console.WriteLine("    |");
            Console.WriteLine("   ===");
        }
        else if (wrong == 5)
        {
            Console.WriteLine("\n+---+");
            Console.WriteLine(" O  |");
            Console.WriteLine("/|\\ |");
            Console.WriteLine("/   |");
            Console.WriteLine("   ===");
        }
        else if (wrong == 6)
        {
            Console.WriteLine("\n+---+");
            Console.WriteLine(" O  |");
            Console.WriteLine("/|\\ |");
            Console.WriteLine("/ \\ |");
            Console.WriteLine("   ===");
        }
    }

    private static int PrintWord(List<char>guessedLetters, String randomWord)
    {
        int counter = 0;
        int rightLetters = 0;
        Console.Write("\r\n");
        foreach (char c in randomWord)
        {
            if (guessedLetters.Contains(c))
            {
                Console.Write(c + " ");
                rightLetters++;
            }
            else
            {
                Console.Write(" ");
            }
            counter++;
        }
        return rightLetters;
    }

    private static void PrintLines(String randomWord)
    {
        Console.Write("\r");
        foreach (char c in randomWord)
        {
            Console.OutputEncoding = System.Text.Encoding.Unicode;
            Console.Write("\u0305 ");
        }
    }

    static void Main(string[] args)
    {
        Console.WriteLine("Welcome to Hangman :)");
        Console.WriteLine("-------------------------------");

        Random random = new Random();
        List<String> wordDictionary = new List<String>
        {
    "giraffe",
    "bicycle",
    "chimney",
    "captain",
    "problem",
    "victory",
    "mystery",
    "sandals",
    "journey",
    "musical",
    "village",
    "company",
    "garbage",
    "weather",
    "tension",
    "elegant",
    "freight",
    "justice",
    "liberty",
    "organic",
    "package",
    "quicken",
    "quality",
    "shelter",
    "trouble",
    "utility",
    "whisper",
    "diamond",
    "explode",
    "fancier",
    "gateway",
    "hilltop",
    "inflate",
    "jackpot",
    "kitchen",
    "lacking",
    "magnify",
    "necktie",
    "outlook",
    "produce",
    "quietly",
    "reserve",
    "strange",
    "typical",
    "upgrade",
    "vibrate",
    "wireless",
    "yoghurt"
    };
        int index = random.Next(wordDictionary.Count);
        String randomWord = wordDictionary[index];

        foreach (char x in randomWord)
        {
            Console.Write("_ ");
        }

        int lengthOfWordToGuess = randomWord.Length;
        int amountOfTimesWrong = 0;
        List<char> currentLettersGuessed = new List<char>();
        int currentLettersRight = 0;

        while(amountOfTimesWrong != 6 && currentLettersRight != lengthOfWordToGuess)
        {
            Console.Write("\nLetters guessed so far: ");
            foreach(char letter in currentLettersGuessed)
            {
                Console.Write(letter + " ");
            }
            // Prompt user for input
            Console.Write("\nGuess a letter: ");
            char letterGuessed = Console.ReadLine()[0];
            // Check if letter has already been guessed
            if(currentLettersGuessed.Contains(letterGuessed) )
            {
                Console.Write("\r\nYou have already guessed this letter.");
                PrintHangman(amountOfTimesWrong);
                currentLettersRight = PrintWord(currentLettersGuessed, randomWord);
            }
            else
            {
                // Check if letter is in the word
                bool right = false;
                for(int i = 0; i < randomWord.Length; i++)
                {
                    if(letterGuessed == randomWord[i])
                    {
                        right = true;
                    }
                }
                if(right)
                {
                    PrintHangman(amountOfTimesWrong);
                    currentLettersGuessed.Add(letterGuessed);
                    currentLettersRight = PrintWord(currentLettersGuessed, randomWord);
                    Console.Write("\r\n");
                    PrintLines(randomWord);
                }
                else
                {
                    amountOfTimesWrong++;
                    currentLettersGuessed.Add(letterGuessed);
                    PrintHangman(amountOfTimesWrong);
                    currentLettersRight = PrintWord(currentLettersGuessed, randomWord);
                    Console.Write("\r\n");
                    PrintLines(randomWord);
                }
            }
        }
        Console.WriteLine("\r\nGame is over! Thanks for playing :)");
    }
}


