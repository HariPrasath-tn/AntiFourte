package com.example.antifourte.model

import java.util.*
import kotlin.collections.ArrayList


// String to hold all the combinations
private lateinit var resultArray:ArrayList<String>
private lateinit var numArray_:List<Int>;
private var zeroCountOut:Int=0;

class BruteForce {
    companion object {


        /*
         * method to do arithmetic operation
         *  input  ==> String operator, two integer Numbers (num1, num2)      ==>  (*, 3, 2)
         * `output ==> Int         (*, 3, 2) ==> 6
         *
         * operation flow:
         *      when:
         *          operator == "+":
         *              adds given two numbers       (+, 3, 2) ==> 5 (resultant)
         *          operator == "-":
         *              subtract number2 from number1       (-, 3, 2) ==> 1 (resultant)
         *          operator == "*":
         *              multiplies given two number         (*, 3, 2) ==> 6 (resultant)
         *          operator == "/":
         *              divide number1 by number2
         *              return ==> quotient             (/, 3, 2) ==> 1 (resultant)
         *          operator == "JOIN":
         *              joins two numbers        (JOIN, 3, 2)    ==> 32 (resultant)
         *
         * finally      ==> returns resultant
         */
        @Throws(Exception::class)
        private fun doThisOperationOn(operator: String, _number1: Int, number2: Int): Int {
            var number1 = _number1
            when (operator) {
                "+" -> {
                    number1 += number2 // Adding two numbers
                }
                "-" -> {
                    number1 -= number2 // subtracting two numbers
                }
                "*" -> {
                    if (number1 == 0 && zeroCountOut >0) {
                        zeroCountOut--
                    }else if(number1 == 0){
                        throw Exception("err")
                    }

                    if(number2 ==0 && zeroCountOut>0){
                        zeroCountOut--;
                    }else if (number2 == 0) {
                        throw Exception("err")
                    }

                    number1 *= number2 // multiplying two numbers
                }
                "/" -> {
                    if (number1 == 0) {
                        throw Exception("err")
                    } else if (number1.toDouble() / number2 % 1 != 0.0) throw Exception("err")
                    number1 /= number2 // Dividing two numbers
                }
                "Join" -> {
                    number1 = (number2.toString() + "" + number1).toInt() // joining two numbers
                }
            }
            return number1
        }



        /*
         * method to do normal operation (shuffle operator between numbers)  (3, 4, 5, 6) ==> (3+4+5+6) ==> (3+4+5-6) ==>...
         *
         *
         * input ==> Array<Int>(option numbers), targetInteger          ==> ({1, 2, 3, 4}, 10)
         * output ==> Unit
         *
         * iteration:                   ==> i = (0->1->2->3)
         *      iteration:              ==> j = (0->1->2->3)
         *          iteration:          ==> k = (0->1->2->3)
         *              combines array number with different combination operators {1, 2, 3, 4}
         *
         *              ==> num1 + operator[i] + num2 + operator[j] + num3 + operator[k] + num4
         *              ==> result = "1+2+3+4"
         *              if result == target
         *                  append expression
         *
         */
        private fun tryThisWithNormalOperations(numbers: IntArray, target: Int) {
            val operators = arrayOf("+", "-", "*", "/")
            var result = 0
            var resultString=""
            var zeroCount:Int;

            // shuffling operators between numbers
            // 1+2+3+4 ==>  1+2+3-4 ==> 1+2+3*4 ==> 1+2+3/4 ==> 1+2-3+4 ==> 1+2-3-4 ==> ...
            for (i in 0..3) {
                for (j in 0..3) {
                    for (k in 0..3) {
                        zeroCount = Collections.frequency(numArray_, 0)
                        zeroCountOut = Collections.frequency(numArray_, 0)

                        try {
                            // passing first two numbers and operator between them, to function
                            //  expression ==> 1+2+3+4
                            //  1+2 +3+4   ==>     doThisOperation( + , 1, 2)   ==>  returns 3
                            //    3 +3 +4  ==>     doThisOperation( + , 3, 3)   ==>  returns 6
                            //       6 +4  ==>     doThisOperation( + , 6, 4)   ==>  returns 10
                            //
                            // finally      ==> 10 (result)

                            //         op3                                 op2                             op1
                            result = doThisOperationOn(operators[k], doThisOperationOn(operators[j], doThisOperationOn(operators[i], numbers[0], numbers[1]), numbers[2]), numbers[3])


                            // method to format string
                            fun formatString(resultString:String, number:Int, operator:String):String{
                                return if(resultString == "")
                                    ""+number
                                else
                                    "($resultString$operator$number)"
                            }

                            //  if result == target:
                            //          appends expression
                            if (result == target) {
                                resultString = ""

                                // removing unwanted zero in number1 if available
                                if(numbers[0] !=0)
                                    resultString += numbers[0]
                                else if (zeroCount > 0){
                                    resultString += numbers[0]
                                    zeroCount--
                                }

                                // removing unwanted zero in number2 if available
                                if(numbers[1] !=0)
                                    resultString = formatString(resultString, numbers[1], operators[i])
                                else if(zeroCount>0) {
                                    resultString = formatString(resultString, numbers[1], operators[i])
                                    zeroCount--
                                }

                                // removing unwanted zero in number3 if available
                                if(numbers[2] !=0)
                                    resultString = formatString(resultString, numbers[2], operators[j])
                                else if(zeroCount>0) {
                                    resultString = formatString(resultString, numbers[2], operators[j])
                                    zeroCount--
                                }

                                // removing unwanted zero in number12 if available
                                if(numbers[3] !=0)
                                    resultString = formatString(resultString, numbers[3], operators[k])
                                else if(zeroCount>0) {
                                    resultString = formatString(resultString, numbers[3], operators[k])
                                }

//                                resultString += "(((${numbers[0]}$resultString${operators[i]}${numbers[1]})$resultString${operators[j]}${numbers[2]})$resultString${operators[k]}${numbers[3]})"
                                if(resultString.startsWith("(") && resultString.endsWith(")"))
                                    resultString = resultString.substring(1, resultString.length-1)

                                resultString +=" = $target\n\n"

                                if(!resultArray.contains(resultString))
                                    resultArray.add(resultString)

                            }


                        } catch (ignored: java.lang.Exception) {
                        }
                    }
                }
            }
        }


        /*
         * method to try different number combinations
         *  ==> (1,2,3,4) ==> (1,2,4,3) ==> (1,4,2,3) ==> ...
         *
         * input ==> Array<Int>(option numbers), target Integer
         * output ==> Unit
         *
         * Method flow:
         *      iteration:                      ==> i = (0->1->2->3)
         *          iteration:                  ==> j = (0->1->2->3)
         *              iteration:              ==> k = (0->1->2->3)
         *                  iteration:          ==> l = (0->1->2->3)
         *                      if i,j,k,l are not equal:
         *                          shuffle number combinations 
         *                          passes shuffled number combination to   
         *                          @Method"tryThisWithNormalOperations(Array<Int>, target:Int)"
         *                          {1,2,3,4} ==> tryThisWithNormalOperations(1,2,4,3)
         *                          ==> returns String expression       if combination expression result == target
         *                          @repeats with respective to iterations
         *
         */
        private fun tryDifferentNumberCombinations(numbers: IntArray, target: Int) {
            for (i in 0..3) {
                for (j in 0..3) {
                    for (k in 0..3) {
                        var l = 0
                        while (l < 4) {
                            // shuffle numbers corresponding to  (i,j,k,l)
                            // and   i,j,k,l are not equal to avoid duplicate values
                            if (i != j && i != k && i != l && j != k && j != l && k != l)
                                tryThisWithNormalOperations(
                                    intArrayOf(
                                        numbers[i], numbers[j], numbers[k], numbers[l]
                                    ), target
                                )
                            l++
                        }
                    }
                }
            }
        }


        /*
         * method to join two numbers and do operations     
         * ==> {1,2,3,4} ==> (12,3,4,0) ==> (13,2,4,0) ==>(14,2,3,0) ==> (21,3,4,0) ==> (23,1,4,0)....  (combination type 1)
         * ==> {1,2,3,4} ==> (123,4,0,0) ==> (124,3,0,0) ==>(132,4,0,0) ==> (134,2,0,0) ....            (combination type 2)
         * ==> {1,2,3,4} ==> (1234,0,0,0) ==> (1243,0,0) ==> (1324,0,0,0) ==> (1342,0,0,0)....          (combination type 3)
         *
         *  input ==> Array<Int>(option numbers), targetInteger
         *  output ==> Unit
         *
         * iteration:               ==> i = (0-> 1-> 2-> 3)
         *      iteration:          ==> j = (0-> 1-> 2-> 3)
         *          iteration:      ==> k = (0-> 1-> 2-> 3)
         *              iteration:  ==> l = (0-> 1-> 2-> 3)
         *                  if i,j,k,l are not equal:
         *                      pass combinations to tryDifferentCombination() method
         *                      {1,2,3,4} ==> tryDifferentCombinations(12, 3, 4, 0)              (combination type 1)
         *                      @repeats with respective to iterations
         * 
         *                      pass combinations to tryDifferentCombination() method
         *                      {1,2,3,4} ==> tryDifferentCombinations(123, 4, 0, 0)             (combination type 2) 
         *                      @repeats with respective to iterations
         * 
         *                      pass combinations to tryDifferentCombination() method
         *                      {1,2,3,4} ==> tryDifferentCombinations(1234,0,0,0)               (combination type 3)
         *                      @repeats with respective to iterations
         *                                      
         */
        private fun joinAndTry(numbers: IntArray, target: Int) {
            for (i in 0..3) {
                for (j in 0..3) {
                    for (k in 0..3) {
                        var l = 0
                        while (l < 4) {
                            
                            // condition to prevent duplicate elements  
                            if (i != j && i != k && i != l && j != k && j != l && k != l) try {

                                // pass combinations to tryDifferentCombination() method
                                // {1,2,3,4} ==> tryDifferentCombinations(12, 3, 4, 0)              (combination type 1)
                                // @repeats with respective to iterations
                                tryDifferentNumberCombinations(
                                    intArrayOf(
                                        doThisOperationOn(
                                            "Join",
                                            numbers[i], numbers[j]
                                        ), numbers[k], numbers[l], 0
                                    ), target
                                )

                                // pass combinations to tryDifferentCombination() method
                                // {1,2,3,4} ==> tryDifferentCombinations(123, 4, 0, 0)             (combination type 2)
                                // @repeats with respective to iterations
                                tryDifferentNumberCombinations(
                                    intArrayOf(
                                        doThisOperationOn(
                                            "Join", doThisOperationOn(
                                                "Join",
                                                numbers[i], numbers[j]
                                            ),
                                            numbers[k]
                                        ), numbers[l], 0, 0
                                    ), target
                                )

                                
                                //pass combinations to tryDifferentCombination() method
                                //{1,2,3,4} ==> tryDifferentCombinations(1234,0,0,0)               (combination type 3)
                                //@repeats with respective to iterations
                                tryDifferentNumberCombinations(
                                    intArrayOf(
                                        doThisOperationOn(
                                            "Join", doThisOperationOn(
                                                "Join", doThisOperationOn(
                                                    "Join",
                                                    numbers[i], numbers[j]
                                                ),
                                                numbers[k]
                                            ),
                                            numbers[l]
                                        ), 0, 0, 0
                                    ), target
                                )
                            } catch (ignored: java.lang.Exception) {
                            }
                            l++
                        }
                    }
                }
            }
        }

        /*
         * method to find all the possible combination to get the target number
         * input ==> IntArray, target Integer
         * output ==> resultant String
         */
        fun startBruteForceFor(numbers: IntArray, target: Int):String {
            resultArray = ArrayList()
            numArray_ = numbers.asList();


            var resultString = ""

            // try different combination of numbers and operators
            tryDifferentNumberCombinations(numbers, target)

            // join and try different combinations of numbers and operators 
            joinAndTry(numbers, target)
            if(resultArray.size > 0){
                for(expression in resultArray){
                    resultString += expression
                }
            }

            return if(resultString != "")
                resultString
            else
                "No Combination Found"
        }

    }
}


class Combination{
    companion object{

    }
}