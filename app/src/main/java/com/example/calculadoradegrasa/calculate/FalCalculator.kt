package com.example.calculadoradegrasa.calculate

import kotlin.math.log10
import kotlin.math.roundToInt

fun fatCalculatorForMale(height: Double, waist: Double, neck: Double, weight: Double){

    FatPercentage = 495 / (1.0324 - 0.19077 * log10((waist - neck)) + 0.15456 * log10(height)) - 450

    FatPercentage = (FatPercentage * 100.0).roundToInt() / 100.0 //1* Just leave two numbers after the comma.

    Fat = (FatPercentage * weight) / 100.0 //amount of fat by weight

    Fat = (Fat * 100.0).roundToInt() / 100.0 //1*

    Muscle = weight - Fat // amount muscle of the weight

    Muscle = (Muscle * 100.0).roundToInt() / 100.0 // 1*

}

fun fatCalculatorForWoman(height: Double, waist: Double, neck: Double, weight: Double, hip: Double){

    FatPercentage = 495 / (1.29579-0.35004 * log10((waist + hip - neck)) + 0.22100 * log10(height)) - 450

    FatPercentage = (FatPercentage * 100.0).roundToInt() / 100.0 //1* Just leave two numbers after the comma.

    Fat = (FatPercentage * weight) / 100.0 //amount of fat by weight

    Fat = (Fat * 100.0).roundToInt() / 100.0 //1*

    Muscle = weight - Fat // amount muscle of the weight

    Muscle = (Muscle * 100.0).roundToInt() / 100.0 // 1*

}