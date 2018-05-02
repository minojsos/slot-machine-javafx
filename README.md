# slot-machine-javafx
<h1>A JavaFX based GUI Application</h1>
<p>
This is a JavaFX based GUI Application - A Slot Machine. <br />
This Application allows the user to bet a certain number of credits and spin the wheels. If atleast two wheels
match, the user wins. The winning credits are calculated as: Value of Item displayed in the Wheel * Number of Credits bet. <br>
</p>

<br>

<h2>The Features</h2>
<ul>
    <li> Bet any number of credits (As long as you have that number of credits). </li>
    <li> Bet 3 credits at once by clicking on the Bet Max button. </li>
    <li> Add Unlimited Credits to your Credits. </li>
    <li> Reset the Bet Amount whenever required. </li>
    <li> Spin the Wheels when you have credits in the Bet Amount. </li>
    <li> Ability to Stop one wheel at an instance. </li>
    <li> View Statistics (Only after playing atleast one game). </li>
    <li> Save Statistics to a file (Name of the File: Date and Time). </li>
</ul>

<br>

<h2>The Symbols</h2>
<ul>
    <li> 
        Cherry | Value: 2 
        <img src='https://raw.githubusercontent.com/minojsos/slot-machine-javafx/master/images/cherry.png' width='64' alt='Cherry Symbol'>
    </li>
    <li> 
        Lemon | Value: 3 
        <img src='https://raw.githubusercontent.com/minojsos/slot-machine-javafx/master/images/lemon.png' width='64' alt='Lemon Symbol'>
    </li>
    <li> 
        Plum | Value: 4 
        <img src='https://raw.githubusercontent.com/minojsos/slot-machine-javafx/master/images/plum.png' width='64' alt='Plum Symbol'>
    </li>
    <li> 
        Melon | Value: 5 
        <img src='https://raw.githubusercontent.com/minojsos/slot-machine-javafx/master/images/melon.png' width='64' alt='Melon Symbol'>
    </li>
    <li> 
        Bell | Value: 6 
        <img src='https://raw.githubusercontent.com/minojsos/slot-machine-javafx/master/images/bell.png' width='64' alt='Bell Symbol'>
    </li>
    <li> 
        RedSeven | Value: 7 
        <img src='https://raw.githubusercontent.com/minojsos/slot-machine-javafx/master/images/redseven.png' width='64' alt='RedSeven Symbol'>
    </li>
</ul>

<br>

<h2>Screenshots of the Application</h2>

<img src='https://raw.githubusercontent.com/minojsos/slot-machine-javafx/master/images/Image_1.png' alt='Main Screen'>
<small>Screenshot of the Main Screen.</small>

<img src='https://raw.githubusercontent.com/minojsos/slot-machine-javafx/master/images/Image_1_ADD_CREDIT_2.png' alt='Add Credits'>
<small>Screenshot after clicking on the Add Credits Button.</small>

<img src='https://raw.githubusercontent.com/minojsos/slot-machine-javafx/master/images/Image_1_BET_MAX.png' alt='Bet Max'>
<small>Screenshot after clicking on the Bet Max Button.</small>

<img src='https://raw.githubusercontent.com/minojsos/slot-machine-javafx/master/images/Image_1_RESET.png' alt='Reset'>
<small>Screenshot after clicking on the Reset Button.</small>

<img src='https://raw.githubusercontent.com/minojsos/slot-machine-javafx/master/images/Image_1_SPIN.png' alt='Spin'>
<small>Screenshot after clicking on the Spin Button. Once the button is clicked, the bet amount is reset but stored to use to calculate winnings if the user wins.</small>

<img src='https://raw.githubusercontent.com/minojsos/slot-machine-javafx/master/images/Image_1_SPIN_LOSE.png' alt='Spin Lose'>
<small>Screenshot after stopping all the three spinning wheels and the user has lost.</small>

<img src='https://raw.githubusercontent.com/minojsos/slot-machine-javafx/master/images/Image_1_SPIN_WIN.png' alt='Spin Win'>
<small>Screenshot after stopping all the three spinning wheels and the user has won. The Winnings is also displayed to the user.</small>

<img src='https://raw.githubusercontent.com/minojsos/slot-machine-javafx/master/images/Image_1_TRYING.png' alt='Spin Trying again'>
<small>Screenshot after clicking on the Spin Button while the wheels are already spinning.</small>

<img src='https://raw.githubusercontent.com/minojsos/slot-machine-javafx/master/images/Image_1_STATS.png' alt='Statistics Window'>
<small>Screenshot of the statistics window which allows the user to view their statistics if they've played atleast one game.</small>

<img src='https://raw.githubusercontent.com/minojsos/slot-machine-javafx/master/images/Image_1_STATS_SAVED_TO_FILE.png' alt='Statistics saved to file'>
<small>Screenshot after clicking on Save Button. Statistics saved to File with the name as current date and time.</small>

<br>

<h2>How does it work?</h2>

<p>
This JavaFX Application is a multithreaded application. Each Wheel runs on a different thread to produce a spinning illusion. The user will be able to stop each wheel individually. Stopping all three wheels ends the game and thus all the three threads stop. The Main thread on which the JavaFX application runs all the time. <br />
The List used to store the symbols that are displayed on the wheels are shuffled everytime to ensure that the Symbols are
displayed in a random order all the time. <br />
</p>
