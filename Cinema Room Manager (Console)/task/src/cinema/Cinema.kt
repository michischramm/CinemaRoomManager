package cinema

const val EIGHT = 8
const val TEN = 10

fun main() {
    var plan = mutableListOf<MutableList<String>>()

    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seatsPerRow = readln().toInt()

    plan = initPlan(rows, seatsPerRow, plan)

    do {
        println("1. Show the seats")
        println("2. Buy a ticket")
        println("3. Statistics")
        println("0. Exit")
        val menuItem = readln().toInt()

        when (menuItem){
            1 -> showPlan(plan)
            2 -> plan = buyTicket(rows, seatsPerRow, plan)
            3 -> showStatistics(rows, seatsPerRow, plan)
            else -> continue
        }

    } while (menuItem != 0)
}

/*
* Function to show statistics
*/
fun showStatistics(rows: Int, seatsPerRow: Int, plan: MutableList<MutableList<String>>) {
    val overallSeats = rows * seatsPerRow
    var totalIncome: Int

    if (overallSeats <= 60) {
        totalIncome = TEN * overallSeats
    } else {
        val half = rows / 2
        totalIncome = half * seatsPerRow * TEN + (rows - half) * seatsPerRow * EIGHT
    }
    var currentIncome = 0
    var purchasedTickets = 0
    if (overallSeats <= 60) {
        for (elm in plan) {
            for (el in elm) {
                if (el == "B") {
                    purchasedTickets++
                    currentIncome += 10
                }
            }
        }
    } else {
        val half = rows / 2
        for (i in 1..half) {
            for (el in plan[i]) {
                if (el == "B") {
                    purchasedTickets++
                    currentIncome += TEN
                }
            }
        }
        for (i in half + 1 .. rows) {
            for (el in plan[i]) {
                if (el == "B") {
                    purchasedTickets++
                    currentIncome += EIGHT
                }
            }
        }
    }


    val percentage = purchasedTickets.toDouble() / overallSeats * 100
    val formatPercentage = "%.2f".format(percentage)
    println("Number of purchased tickets: $purchasedTickets")
    println("Percentage: $formatPercentage%") // 0.00
    println("Current income: $$currentIncome")
    println("Total income: $$totalIncome")
}


/*
* Function to initialize the plan
*/
fun initPlan(rows: Int, seatsPerRow: Int, plan: MutableList<MutableList<String>>): MutableList<MutableList<String>> {
    val row0 = mutableListOf<String>()

    row0.add(0, " ")

    for (i in 1..seatsPerRow) {
        row0.add(i.toString())
    }

    plan.add(row0)

    for (i in 1..rows){
        val temp = mutableListOf<String>()
        temp.add(i.toString())
        for (j in 0 until seatsPerRow) {
            temp.add("S")
        }
        plan.add(temp)
    }
    return plan
}

/*
* Function to show the plan
*/
fun showPlan(plan: MutableList<MutableList<String>>){
    println("Cinema:")
    for (elm in plan) {
        for (el in elm){
            print("$el ")
        }
        print("\n")
    }
}

/*
* Function to buy a ticket
* The bought ticket will be marked with B in the plan
*/
fun buyTicket(rows: Int, seatsPerRow: Int, plan: MutableList<MutableList<String>>): MutableList<MutableList<String>> {
    var row: Int
    var seat: Int
    var askAgain = true
    do {
        println("Enter a row number:")
        row = readln().toInt()
        println("Enter a seat number in that row:")
        seat = readln().toInt()

        if (row > rows || seat > seatsPerRow) {
            println("Wrong input!")
        } else if(plan[row][seat] == "B"){
            println("That ticket has already been purchased!")
        } else {
            askAgain = false
        }
    } while (askAgain)


    val price: Int
    val overallSeats = rows * seatsPerRow

    if (overallSeats <= 60) {
        price = TEN
    } else {
        val half = rows / 2
        if (row <= half) {
            price = TEN
        } else {
            price = EIGHT
        }
    }

    println("Ticket price: $$price")

    plan[row][seat] = "B"

    return plan
}